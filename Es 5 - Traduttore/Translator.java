import java.io.*;
public class Translator{
    private final Lexer lex;
    private final BufferedReader pbr;
    private Token look;
    private final boolean debugMode;
    int count = 0;

    SymbolTable st = new SymbolTable();
    CodeGenerator code = new CodeGenerator();

    public Translator(Lexer l, BufferedReader br, boolean debug) throws LexerException {
        lex = l;
        pbr = br;
        debugMode = debug;
        move();
    }

    void move() throws LexerException {
        look = lex.lexical_scan(pbr);
        if (debugMode) System.out.println("token = " + look);
    }


    void match(int t) throws TranslatorException, LexerException {
        if (look.tag == t) {
            if (look.tag != Tag.EOF){
                move();
            }
        } else{
            throw new TranslatorException(new Throwable(" Syntax error in line " + lex.line + ": expected " + t + " found " + look.tag));
        }
    }
    public void prog() throws TranslatorException {
        try{
            statlist();
            match(Tag.EOF);
        } catch (Exception e){
            throw new TranslatorException(e);
        }

        try {
            code.toJasmin();
        }
        catch(java.io.IOException e) {
            throw new TranslatorException(e);
        }
    }

    private void statlist() throws TranslatorException{
        try{
            stat();
            statlistp();
        } catch (Exception e){
            throw new TranslatorException(e);
        }
    }
    private void statlistp() throws TranslatorException{
        try{
            if (look.tag == ';') {
                match(';');
                stat();
                statlistp();
            }
        } catch (Exception e){
            throw new TranslatorException(e);
        }
    }
    public void stat() throws TranslatorException{
        try{
            int opc;
            switch(look.tag) {
                case Tag.ASSIGN:
                    opc = 0;
                    match(Tag.ASSIGN);
                    expr();
                    match(Tag.TO);
                    idlist(opc);
                    break;

                case Tag.PRINT:
                    match(Tag.PRINT);
                    match('[');
                    exprlist();
                    code.emit(OpCode.invokestatic,1);
                    match(']');
                    break;


                case Tag.READ:
                    opc = 1;
                    match(Tag.READ);
                    match('[');
                    if (look.tag==Tag.ID) {
                        idlist(opc);
                        match(']');
                    }
                    else
                        throw new TranslatorException(new Throwable("Error in grammar (stat) after read"));
                    break;


                case Tag.WHILE:
                    match(Tag.WHILE);
                    match('(');

                    int ltrue_while = code.newLabel();
                    int lend_while = code.newLabel();

                    code.emitLabel(ltrue_while);

                    bexpr(lend_while);
                    match(')');

                    stat();
                    code.emit(OpCode.GOto,ltrue_while);
                    code.emitLabel(lend_while);

                    break;


                case Tag.COND:
                    match(Tag.COND);
                    match('[');

                    int lnext_cond = code.newLabel();
                    int lfalse_cond = code.newLabel();

                    optlist(lfalse_cond, lnext_cond);
                    match(']');

                    condp(lnext_cond);
                    break;

                case '{':
                    match('{');
                    statlist();
                    match('}');
                    break;

                case Tag.EOF, '}':
                    break;

                default:
                    throw new TranslatorException(new Throwable("Error in grammar (stat)"));
            }
        }
        catch (Exception e){
            throw new TranslatorException(e);
        }
    }

    private void condp(int lnext_cond) throws TranslatorException{
        try{
            if(look.tag == Tag.END){
                code.emitLabel(lnext_cond);
                match(Tag.END);
            } else if(look.tag == Tag.ELSE){
                match(Tag.ELSE);
                stat();
                if(look.tag == Tag.END){
                    code.emitLabel(lnext_cond);
                    match(Tag.END);
                } else {
                    throw new TranslatorException(new Throwable("Error in grammar (stat) after else"));
                }
            } else {
                throw new TranslatorException(new Throwable("Error in grammar (stat) after cond"));
            }
        } catch (Exception e){
            throw new TranslatorException(e);
        }
    }

    private void idlist(int opc) throws TranslatorException, LexerException {
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

                match(Tag.ID);
                idlistp(opc, id_addr);
                break;

            case ']':
                break;

            default:
                throw new TranslatorException(new Throwable("Error in grammar (idlist)"));
        }
    }

    private void idlistp(int opc, int old_add) throws TranslatorException, LexerException {
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
                match(Tag.ID);
                idlistp(opc, old_add);
            } else {
                throw new TranslatorException(new Throwable("Error in grammar (idlistp)"));
            }
        }
    }

    private void optlist(int lfalse, int lnext) throws TranslatorException, LexerException {
        optitem(lfalse, lnext);
        switch (look.tag) {
            case Tag.OPTION:
                optlistp(lnext);
                break;

            case ']':
                break;

            default:
                throw new TranslatorException(new Throwable("Error in grammar (optlist)"));
        }
    }

    private void optlistp(int lnext) throws TranslatorException, LexerException {
        int lfalse_new = code.newLabel();
        optitem(lfalse_new, lnext);
        switch (look.tag){
            case Tag.OPTION:
                optlistp(lnext);
                break;

            case ']':
                break;

            default:
                throw new TranslatorException(new Throwable("Error in grammar (optlistp)"));
        }
    }

    private void optitem(int lfalse, int lnext) throws TranslatorException, LexerException {
        match(Tag.OPTION);
        match('(');
        bexpr(lfalse);
        match(')');
        match(Tag.DO);
        stat();
        code.emit(OpCode.GOto,lnext);
        code.emitLabel(lfalse);
    }

    private void bexpr(int lfalse) throws TranslatorException, LexerException {
        if(look == Word.eq){
            match(Tag.RELOP);
            expr();
            expr();
            code.emit(OpCode.if_icmpne , lfalse);

        } else if(look == Word.ne){
            match(Tag.RELOP);
            expr();
            expr();
            code.emit(OpCode.if_icmpeq, lfalse);

        } else if(look == Word.le) {
            match(Tag.RELOP);
            expr();
            expr();
            code.emit(OpCode.if_icmpgt, lfalse);

        } else if(look == Word.ge) {
            match(Tag.RELOP);
            expr();
            expr();
            code.emit(OpCode.if_icmplt, lfalse);

        } else if(look == Word.lt) {
            match(Tag.RELOP);
            expr();
            expr();
            code.emit(OpCode.if_icmpge, lfalse);

        } else if(look == Word.gt) {
            match(Tag.RELOP);
            expr();
            expr();
            code.emit(OpCode.if_icmple, lfalse);

        } else if(look == Word.or){
            match(Tag.OR);
            int lv = code.newLabel();
            bexpr(lv);
            bexpr(lv);
            code.emit(OpCode.GOto, lfalse);
            code.emitLabel(lv);
        } else if(look == Word.and){
            match(Tag.AND);
            bexpr(lfalse);
            bexpr(lfalse);
        } else if(look == Word.not){
            match('!');
            int lv = code.newLabel();

            bexpr(lv);
            code.emit(OpCode.GOto, lfalse);
            code.emitLabel(lv);
        } else if(look == Word.vero){
            match(Tag.TRUE);
            int lv = code.newLabel();
            code.emitLabel(lv);

        } else if(look == Word.falso){
            match(Tag.FALSE);
            int lv = code.newLabel();
            code.emit(OpCode.GOto, lv);
            code.emitLabel(lv);
        }

    }
    private void expr() throws TranslatorException, LexerException {
        switch (look.tag) {
            case '+' -> {
                match('+');
                match('(');
                exprlist();
                match(')');
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
                match('(');
                exprlist();
                match(')');
                code.emit(OpCode.imul);
            }
            case '/' -> {
                match('/');
                expr();
                expr();
                code.emit(OpCode.idiv);
            }
            case Tag.NUM -> {
                code.emit(OpCode.ldc, Lexer.getNUM());
                match(Tag.NUM);

            }
            case Tag.ID -> {
                Token id = look;
                match(Tag.ID);
                code.emit(OpCode.iload, st.lookupAddress(((Word) id).lexeme));
            }
        }
    }

    private void exprlist() throws TranslatorException, LexerException {
        expr();
        exprlistp();
    }

    private void exprlistp() throws TranslatorException, LexerException {
        if(look.tag == ','){
            match(',');
            expr();
            exprlistp();
        }
    }

    public static void main(String[] args) {
        Lexer lex = new Lexer();
        if (args.length == 0) {
            System.err.println("main(): File not found! \nUsage: java Translator <input file>");
        } else if (args.length == 1) {
            String path = args[0]; // il percorso del file da leggere
            try {
                System.out.println("Compiling " + path + "...");
                BufferedReader br = new BufferedReader(new FileReader(path));
                Translator translator = new Translator(lex, br, false);
                try{
                    try {
                        translator.prog();
                        br.close();
                    } catch (TranslatorException e) {
                        br.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (LexerException | FileNotFoundException e) {
                e.printStackTrace();
            }
            System.out.println("Done!");
        } else if (args.length == 2) {
            String path = args[0]; // il percorso del file da leggere
            try {
                System.out.println("Compiling " + path + "...");
                BufferedReader br = new BufferedReader(new FileReader(path));
                Translator translator = new Translator(lex, br, Boolean.parseBoolean(args[1]));
                try{
                    try {
                        translator.prog();
                        br.close();
                    } catch (TranslatorException e) {
                        br.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (LexerException | FileNotFoundException e) {
                e.printStackTrace();
            }
            System.out.println("Done!");
        } else {
            System.out.println("Too many arguments");
        }
    }
}

//Ringrazio PolPiantina per l'aiuto nello sviluppo del Translator
