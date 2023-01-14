import java.io.*;
public class Translator {
    private Lexer lex;
    private BufferedReader pbr;
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
        throw new Error("Linea n." + lex.line + ": " + s);
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
        int lnext_prog = code.newLabel();
        statlist(lnext_prog);
        code.emitLabel(lnext_prog);
        match(Tag.EOF);
        try {
            code.toJasmin();
        }
        catch(java.io.IOException e) {
            System.out.println(Errors.n110);
        };
    }

    private void statlist(int lnext_statlist){
        stat(lnext_statlist);
        statlistp(lnext_statlist);

    }

    private void statlistp(int lnext_statlistp){
        switch (look.tag){
            case ';':
                match(';');
                stat(lnext_statlistp);
                statlistp(lnext_statlistp);
                break;

            case Tag.EOF, '}':
                break;

            default:
                error(Errors.n120);
        }
    }
    public void stat(int lnext_stat) {
        switch(look.tag) {
            case Tag.ASSIGN:
                if(look.tag == Tag.ASSIGN){
                    match(Tag.ASSIGN);
                } else {
                    error(Errors.n130);
                }
                expr();
                if(look.tag == Tag.TO){
                    match(Tag.TO);
                } else {
                    error(Errors.n130);
                }
                if(look.tag == Tag.ID){
                    match(Tag.ID);
                } else {
                    error(Errors.n130);
                }
                int m_val = st.lookupAddress(Lexer.getID());
                if (m_val == -1) {
                    code.emit(OpCode.istore, count);
                    st.insert(Lexer.getID(), count++);
                } else {
                    code.emit(OpCode.istore, m_val);
                }
                break;

            case Tag.PRINT:
                if(look.tag == Tag.PRINT){
                    match(Tag.PRINT);
                } else {
                    error(Errors.n130);
                }
                if(look.tag == '['){
                    match('[');
                } else {
                    error(Errors.n130);
                }
                expr();
                code.emit(OpCode.invokestatic,1);
                if(look.tag == ']'){
                    match(']');
                } else {
                    error(Errors.n130);
                }
                break;


            case Tag.READ:
                if(look.tag == Tag.READ){
                    match(Tag.READ);
                } else {
                    error(Errors.n130);
                }
                if(look.tag == '['){
                    match('[');
                } else {
                    error(Errors.n130);
                }
                if (look.tag==Tag.ID) {
                    int read_id_addr = st.lookupAddress(Lexer.getID());
                    if (read_id_addr==-1) {
                        read_id_addr = count;
                        st.insert(Lexer.getID(),count++);
                    }
                    if(look.tag == Tag.ID){
                        match(Tag.ID);
                    } else {
                        error(Errors.n130);
                    }
                    if(look.tag == ']'){
                        match(']');
                    } else {
                        error(Errors.n130);
                    }
                    code.emit(OpCode.invokestatic,0);
                    code.emit(OpCode.istore,read_id_addr);
                }
                else
                    error(Errors.n131);
                break;


            case Tag.WHILE:
                if(look.tag == Tag.WHILE){
                    match(Tag.WHILE);
                } else {
                    error(Errors.n130);
                }
                if(look.tag == '('){
                    match('(');
                } else {
                    error(Errors.n130);
                }
                int ltrue_stat_while = code.newLabel();
                int lnext_stat_while = code.newLabel();
                int end_while = code.newLabel();
                if(ltrue_stat_while!=0) {
                    code.emit(OpCode.GOto, ltrue_stat_while);
                }
                code.emitLabel(ltrue_stat_while);
                bexpr(lnext_stat_while, end_while);
                if(look.tag == ')'){
                    match(')');
                } else {
                    error(Errors.n130);
                }
                code.emitLabel(lnext_stat_while);
                stat(ltrue_stat_while);
                code.emit (OpCode.GOto, ltrue_stat_while);
                code.emitLabel(end_while);
                break;


            case Tag.COND:
                match(Tag.COND);
                if(look.tag == '[') {
                    match('[');
                } else {
                    error(Errors.n130);
                }
                int ltrue_cond = code.newLabel();
                int lfalse_cond = code.newLabel();
                optlist(ltrue_cond, lfalse_cond, lnext_stat);
                if(look.tag == ']'){
                    match(']');
                } else {
                    error(Errors.n130);
                }
                if(look.tag == Tag.END){
                    match(Tag.END);
                    break;
                } else if(look.tag == Tag.ELSE){
                    match(Tag.ELSE);
                    stat(lnext_stat);
                    if(look.tag == Tag.END){
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
                statlist(lnext_stat);
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
    private void idlist(int lnext_idlist) {
        switch(look.tag) {
            case Tag.ID:
                int id_addr = st.lookupAddress(Lexer.getID());
                if (id_addr==-1) {
                    id_addr = count;
                    st.insert(Lexer.getID(),count++);
                }
                if(look.tag == Tag.ID){
                    match(Tag.ID);
                } else {
                    error(Errors.n140);
                }

                idlistp(lnext_idlist);

                break;

            case ']':
                break;

            default:
                error(Errors.n140);
        }
    }

    private void idlistp(int lnext_idlistp){
        if(look.tag == ','){
            match(',');
            if(look.tag == Tag.ID){
                int id_addr = st.lookupAddress(((Word)look).lexeme);
                if (id_addr==-1) {
                    id_addr = count;
                    st.insert(((Word)look).lexeme,count++);
                }
                if(look.tag == Tag.ID){
                    match(Tag.ID);
                } else {
                    error(Errors.n141);
                }
                idlistp(lnext_idlistp);
            } else {
                error(Errors.n141);
            }
        }
    }

    private void optlist(int ltrue, int lfalse, int lnext){
        optitem(ltrue, lfalse, lnext);
        switch (look.tag){
            case ';':
                if(look.tag == ';'){
                    match(';');
                } else {
                    error(Errors.n150);
                }
                optlistp(ltrue, lfalse, lnext);
                break;

            case ']':
                break;

            default:
                error(Errors.n150);
        }
    }

    private void optlistp(int ltrue, int lfalse, int lnext){
        optitem(ltrue, lfalse, lnext);
        switch (look.tag){
            case ';':
                if(look.tag == ';'){
                    match(';');
                } else {
                    error(Errors.n151);
                }
                optlistp(ltrue, lfalse, lnext);
                break;

            case ']':
                break;

            default:
                error(Errors.n151);
        }
    }

    private void optitem(int ltrue, int lfalse, int lnext){
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
        bexpr(ltrue, lfalse);
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
        code.emitLabel(ltrue);
        stat(lnext);
        code.emit(OpCode.GOto,lfalse);
        code.emitLabel(lfalse);
    }

    private void bexpr(int ltrue, int lfalse){
        if(look == Word.eq){
            if(look.tag == Tag.RELOP){
                match(Tag.RELOP);
            } else {
                error(Errors.n170);
            }
            expr();
            expr();
            code.emit(OpCode.if_icmpeq, ltrue);
            code.emit(OpCode.GOto, lfalse);
        } else if(look == Word.ne){
            if(look.tag == Tag.RELOP){
                match(Tag.RELOP);
            } else {
                error(Errors.n170);
            }
            expr();
            expr();
            code.emit(OpCode.if_icmpne, ltrue);
            code.emit(OpCode.GOto, lfalse);
        } else if(look == Word.le) {
            if(look.tag == Tag.RELOP){
                match(Tag.RELOP);
            } else {
                error(Errors.n170);
            }
            expr();
            expr();
            code.emit(OpCode.if_icmple, ltrue);
            code.emit(OpCode.GOto, lfalse);
        } else if(look == Word.ge) {
            if(look.tag == Tag.RELOP){
                match(Tag.RELOP);
            } else {
                error(Errors.n170);
            }
            expr();
            expr();
            code.emit(OpCode.if_icmpge, ltrue);
            code.emit(OpCode.GOto, lfalse);
        } else if(look == Word.lt) {
            if(look.tag == Tag.RELOP){
                match(Tag.RELOP);
            } else {
                error(Errors.n170);
            }
            expr();
            expr();
            code.emit(OpCode.if_icmplt, ltrue);
            code.emit(OpCode.GOto, lfalse);
        } else if(look == Word.gt) {
            if(look.tag == Tag.RELOP){
                match(Tag.RELOP);
            } else {
                error(Errors.n170);
            }
            expr();
            expr();
            code.emit(OpCode.if_icmpgt, ltrue);
            code.emit(OpCode.GOto, lfalse);
        }

    }
    private void expr() {
        switch(look.tag) {
            case '+':
                if(look.tag == '+'){
                    match('+');
                } else {
                    error(Errors.n180);
                }
                if(look.tag == '('){
                    match('(');
                } else {
                    error(Errors.n180);
                }
                exprlist();
                if(look.tag == ')'){
                    match(')');
                } else {
                    error(Errors.n180);
                }

                code.emit(OpCode.iadd);
                break;

            case '-':
                if(look.tag == '-'){
                    match('-');
                } else {
                    error(Errors.n180);
                }
                expr();
                expr();
                code.emit(OpCode.isub);
                break;

            case '*':
                if(look.tag == '+'){
                    match('+');
                } else {
                    error(Errors.n180);
                }
                if(look.tag == '('){
                    match('(');
                } else {
                    error(Errors.n180);
                }
                exprlist();
                if(look.tag == ')'){
                    match(')');
                } else {
                    error(Errors.n180);
                }

                code.emit(OpCode.imul);
                break;

            case '/':
                if(look.tag == '+'){
                    match('+');
                } else {
                    error(Errors.n180);
                }
                expr();
                expr();
                code.emit(OpCode.idiv);
                break;

            case Tag.NUM:
                int num_val = Lexer.getNUM();
                code.emit(OpCode.ldc, num_val);
                if(look.tag == Tag.NUM){
                    match(Tag.NUM);
                } else {
                    error(Errors.n180);
                }
                break;

            case Tag.ID:
                Token id = look;
                if(look.tag == Tag.ID){
                    match(Tag.ID);
                } else {
                    error(Errors.n180);
                }
                code.emit(OpCode.iload, st.lookupAddress(((Word)id).lexeme));
                break;
        }
    }

    private void exprlist(){
        expr();
        exprlistp();
    }

    private void exprlistp(){
        if(look.tag == ','){
            if(look.tag == ','){
                match(',');
            } else {
                error(Errors.n181);
            }
            expr();
            exprlistp();
        }
    }

    public static void main(String[] args) {
        Lexer lex = new Lexer();

        String path = "/Users/ilaario/Desktop/Progetti/ProgettoLFT/Es 5 - Traduttore/test.lft"; // il percorso del file da leggere
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            Translator translator = new Translator(lex, br);
            translator.prog();
            br.close();
        } catch (IOException e) {e.printStackTrace();}
    }
}

