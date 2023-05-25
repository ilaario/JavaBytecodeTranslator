import java.io.*;
public class Translator {
    private final Lexer lex;
    private final BufferedReader pbr;
    private Token look;

    SymbolTable st = new SymbolTable();
    CodeGenerator code = new CodeGenerator();
    int count = 0;
    public Translator(Lexer l, BufferedReader br) {
        lex = l;
        pbr = br;
        move();
    }
    void move() {
        look = lex.lexical_scan(pbr);
        System.out.println("token = " + look);
    }
    void error(String s) {
        throw new Error("Linea n." + Lexer.line + ": " + s);
    }
    void match(int t) {
        if (look.tag == t) {
            if (look.tag != Tag.EOF){
                move();
            }
        } else{
            error(Errors.n190);
        }
    }
    public void prog() {
        statlist();
        match(Tag.EOF);
        try {
            code.toJasmin();
        }
        catch(java.io.IOException e) {
            System.out.println(Errors.n110);
        }
    }

    private void statlist(){
        stat();
        statlistp();

    }
    private void statlistp(){
        switch (look.tag){
            case ';':
                match(';');
                stat();
                statlistp();
                break;

            case Tag.EOF, '}':
                break;

            default:
                error(Errors.n120);
        }
    }
    public void stat() {
        int opc;
        switch(look.tag) {
            case Tag.ASSIGN:
                opc = 0;
                match(Tag.ASSIGN);
                expr();
                if(look.tag == Tag.TO){
                    match(Tag.TO);
                } else {
                    error(Errors.n130);
                }
                idlist(opc);
                break;

            case Tag.PRINT:
                match(Tag.PRINT);
                if(look.tag == '['){
                    match('[');
                } else {
                    error(Errors.n130);
                }
                exprlist();
                code.emit(OpCode.invokestatic,1);
                if(look.tag == ']'){
                    match(']');
                } else {
                    error(Errors.n130);
                }
                break;


            case Tag.READ:
                opc = 1;
                match(Tag.READ);
                if(look.tag == '['){
                    match('[');
                } else {
                    error(Errors.n130);
                }
                if (look.tag==Tag.ID) {
                    idlist(opc);
                    if(look.tag == ']'){
                        match(']');
                    } else {
                        error(Errors.n130);
                    }

                }
                else
                    error(Errors.n131);
                break;


            case Tag.WHILE:
                match(Tag.WHILE);
                if(look.tag == '('){
                    match('(');
                } else {
                    error(Errors.n130);
                }

                int ltrue_while = code.newLabel();
                int lend_while = code.newLabel();

                code.emitLabel(ltrue_while);

                bexpr(lend_while);

                if(look.tag == ')'){
                    match(')');
                } else {
                    error(Errors.n130);
                }

                stat();
                code.emit(OpCode.GOto,ltrue_while);
                code.emitLabel(lend_while);

                break;


            case Tag.COND:
                match(Tag.COND);
                if(look.tag == '[') {
                    match('[');
                } else {
                    error(Errors.n130);
                }

                int lnext_cond = code.newLabel();
                int lfalse_cond = code.newLabel();

                optlist(lfalse_cond, lnext_cond);
                if(look.tag == ']'){
                    match(']');
                } else {
                    error(Errors.n130);
                }
                if(look.tag == Tag.END){
                    code.emitLabel(lnext_cond);
                    match(Tag.END);
                    break;
                } else if(look.tag == Tag.ELSE){
                    match(Tag.ELSE);
                    stat();
                    if(look.tag == Tag.END){
                        code.emitLabel(lnext_cond);
                        match(Tag.END);
                        break;
                    } else {
                        error(Errors.n130);
                    }
                } else {
                    error(Errors.n130);
                }

            case '{':
                if(look.tag == '{'){
                    match('{');
                } else {
                    error(Errors.n130);
                }
                statlist();
                if(look.tag == '}'){
                    match('}');
                } else {
                    error(Errors.n130);
                }
                break;

            case Tag.EOF, '}':
                break;

            default:
                error(Errors.n130);
        }
    }
    private void idlist(int opc) {
        switch(look.tag) {
            case Tag.ID:
                int id_addr = st.lookupAddress(Lexer.getID());
                if (id_addr==-1) {
                    id_addr = count;
                    st.insert(Lexer.getID(),count++);
                }
                switch (opc) {
                    case 0 -> code.emit(OpCode.istore, id_addr);
                    case 1 -> {
                        code.emit(OpCode.invokestatic, 0);
                        code.emit(OpCode.istore, id_addr);
                    }
                    default -> {
                    }
                }

                if(look.tag == Tag.ID){
                    match(Tag.ID);
                } else {
                    error(Errors.n140);
                }

                idlistp(opc, id_addr);
                break;

            case ']':
                break;

            default:
                error(Errors.n140);
        }
    }

    private void idlistp(int opc, int old_add){
        if(look.tag == ','){
            match(',');
            if(look.tag == Tag.ID){
                int id_addr = st.lookupAddress(Lexer.getID());
                if (id_addr==-1) {
                    id_addr = count;
                    st.insert(Lexer.getID(),count++);
                }
                switch (opc) {
                    case 0 -> {
                        code.emit(OpCode.iload, old_add);
                        code.emit(OpCode.istore, id_addr);
                    }
                    case 1 -> {
                        code.emit(OpCode.invokestatic, 0);
                        code.emit(OpCode.istore, id_addr);
                    }
                    default -> {
                    }
                }
                if(look.tag == Tag.ID){
                    match(Tag.ID);
                } else {
                    error(Errors.n141);
                }
                idlistp(opc, old_add);
            } else {
                error(Errors.n141);
            }
        }
    }

    private void optlist(int lfalse, int lnext){
        optitem(lfalse, lnext);
        switch (look.tag){
            case Tag.OPTION:
                optlistp(lnext);
                break;

            case ']':
                break;

            default:
                error(Errors.n150);
        }
    }

    private void optlistp(int lnext){
        int lfalse_new = code.newLabel();
        optitem(lfalse_new, lnext);
        switch (look.tag){
            case Tag.OPTION:
                optlistp(lnext);
                break;

            case ']':
                break;

            default:
                error(Errors.n151);
        }
    }

    private void optitem(int lfalse, int lnext){
        if(look.tag == Tag. OPTION){
            match(Tag.OPTION);
        } else {
            error(Errors.n160);
        }
        if(look.tag == '(') {
            match('(');
        } else {
            error(Errors.n160);
        }
        bexpr(lfalse);
        if (look.tag == ')'){
            match(')');
        } else {
            error(Errors.n160);
        }
        if(look.tag == Tag.DO){
            match(Tag.DO);
        } else {
            error(Errors.n160);
        }

        stat();
        code.emit(OpCode.GOto,lnext);
        code.emitLabel(lfalse);
    }

    private void bexpr(int lfalse){
        if(look == Word.eq){
            if(look.tag == Tag.RELOP){
                match(Tag.RELOP);
            } else {
                error(Errors.n170);
            }
            expr();
            expr();
            code.emit(OpCode.if_icmpne , lfalse);

        } else if(look == Word.neq){
            if(look.tag == Tag.RELOP){
                match(Tag.RELOP);
            } else {
                error(Errors.n170);
            }
            expr();
            expr();
            code.emit(OpCode.if_icmpeq, lfalse);

        } else if(look == Word.le) {
            if(look.tag == Tag.RELOP){
                match(Tag.RELOP);
            } else {
                error(Errors.n170);
            }
            expr();
            expr();
            code.emit(OpCode.if_icmpgt, lfalse);

        } else if(look == Word.ge) {
            if(look.tag == Tag.RELOP){
                match(Tag.RELOP);
            } else {
                error(Errors.n170);
            }
            expr();
            expr();
            code.emit(OpCode.if_icmplt, lfalse);

        } else if(look == Word.lt) {
            if(look.tag == Tag.RELOP){
                match(Tag.RELOP);
            } else {
                error(Errors.n170);
            }
            expr();
            expr();
            code.emit(OpCode.if_icmpge, lfalse);

        } else if(look == Word.gt) {
            if(look.tag == Tag.RELOP){
                match(Tag.RELOP);
            } else {
                error(Errors.n170);
            }
            expr();
            expr();
            code.emit(OpCode.if_icmple, lfalse);

        } else if(look == Word.or){
            if(look.tag == Tag.OR){
                match(Tag.OR);
            } else {
                error(Errors.n170);
            }

            int lv = code.newLabel();
            bexpr(lv);
            bexpr(lv);
            code.emit(OpCode.GOto, lfalse);
            code.emitLabel(lv);
        } else if(look == Word.and){
            if(look.tag == Tag.AND){
                match(Tag.AND);
            } else {
                error(Errors.n170);
            }

            bexpr(lfalse);
            bexpr(lfalse);
        } else if(look == Word.not){
            if(look.tag == '!'){
                match('!');
            } else {
                error(Errors.n170);
            }

            int lv = code.newLabel();

            bexpr(lv);
            code.emit(OpCode.GOto, lfalse);
            code.emitLabel(lv);
        } else if(look == Word.vero){
            if(look.tag == Tag.TRUE){
                match(Tag.TRUE);
            } else {
                error(Errors.n170);
            }

            int lv = code.newLabel();
            code.emitLabel(lv);

        } else if(look == Word.falso){
            if(look.tag == Tag.FALSE){
                match(Tag.FALSE);
            } else {
                error(Errors.n170);
            }

            int lv = code.newLabel();
            code.emit(OpCode.GOto, lv);
            code.emitLabel(lv);
        }

    }
    private void expr() {
        switch (look.tag) {
            case '+' -> {
                match('+');
                if (look.tag == '(') {
                    match('(');
                } else {
                    error(Errors.n180);
                }
                exprlist();
                if (look.tag == ')') {
                    match(')');
                } else {
                    error(Errors.n180);
                }
                code.emit(OpCode.iadd);
            }
            case '-' -> {
                match('-');
                expr();
                expr();
                code.emit(OpCode.isub);
            }
            case '*' -> {
                match('*');
                if (look.tag == '(') {
                    match('(');
                } else {
                    error(Errors.n180);
                }
                exprlist();
                if (look.tag == ')') {
                    match(')');
                } else {
                    error(Errors.n180);
                }
                code.emit(OpCode.imul);
            }
            case '/' -> {
                error(Errors.n180);
                expr();
                expr();
                code.emit(OpCode.idiv);
            }
            case Tag.NUM -> {
                code.emit(OpCode.ldc, Lexer.getNUM());
                if (look.tag == Tag.NUM) {
                    match(Tag.NUM);
                } else {
                    error(Errors.n180);
                }
            }
            case Tag.ID -> {
                Token id = look;
                match(Tag.ID);
                code.emit(OpCode.iload, st.lookupAddress(((Word) id).lexeme));
            }
        }
    }

    private void exprlist(){
        expr();
        exprlistp();
    }

    private void exprlistp(){
        if(look.tag == ','){
            match(',');
            expr();
            exprlistp();
        }
    }

    public static void main(String[] args) {
        Lexer lex = new Lexer();

        String path = "/Users/ilaario/Desktop/Progetti/2° Anno/Linguaggi Formali e Traduttori/ProgettoLFT/Es 5 - Traduttore/test.lft"; // il percorso del file da leggere
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            Translator translator = new Translator(lex, br);
            translator.prog();
            br.close();
        } catch (IOException e) {e.printStackTrace();}
    }
}

//Ringrazio PolPiantina per l'aiuto nello sviluppo del Translator
