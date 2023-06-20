import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Parser {
    private Lexer lex;
    private BufferedReader pbr;
    private Token look;

    public Parser(Lexer l, BufferedReader br) throws ParserException {
        lex = l;
        pbr = br;
        move();
    }
    void move() throws ParserException {
        try {
            look = lex.lexical_scan(pbr);
            System.out.println("token = " + look);
        } catch (LexerException e) {
            throw new ParserException(new Throwable("Syntax error: " + e.getMessage()));
        }
    }
    void error(String s) {
        throw new Error("Linea n." + lex.line + ": " + s);
    }
    void match(int t) throws ParserException{
        if (look.tag == t) {
            if (look.tag != Tag.EOF) move();
        } else {
            throw new ParserException(new Throwable(" Syntax error: expected " + t + " found " + look.tag));
        }
    }

    void prog() {
        try {
            statlist();
            match(Tag.EOF);
        } catch (ParserException e) {
            System.err.println(e.getMessage());
        }
    }

    void statlist(){
        stat();
        statlistp();
    }

    void stat() {
        try{
            switch (look.tag){
                case Tag.ASSIGN:
                    match(Tag.ASSIGN);
                    if(look.tag == Tag.NUM || look.tag == '+' || look.tag == '-' || look.tag == '*' || look.tag == '/') {
                        expr();
                    }
                    match(Tag.TO);
                    idlist();
                    break;

                case Tag.PRINT:
                    match(Tag.PRINT);
                    match('[');
                    exprlist();
                    match(']');
                    break;

                case Tag.READ:
                    match(Tag.READ);
                    match('[');
                    idlist();
                    match(']');
                    break;

                case Tag.WHILE:
                    match(Tag.WHILE);
                    match('(');
                    bexpr();
                    match(')');
                    stat();
                    break;

                case Tag.COND:
                    match(Tag.COND);
                    match('[');
                    optlist();
                    match(']');
                    condp();
                    break;

                case '{':
                    match('{');
                    statlist();
                    match('}');
                    break;

                case Tag.EOF, '}':
                    break;

                default:
                    throw new ParserException(new Throwable("Syntax error: error in method <stat>"));
            }
        } catch (ParserException e) {
            System.err.println(e.getMessage());
        }
    }

    void condp() {
        try {
            switch (look.tag){
                case Tag.ELSE:
                    match(Tag.ELSE);
                    stat();
                case Tag.END:
                    match(Tag.END);
                    break;
                case Tag.EOF, '}':
                    break;
                default:
                    throw new ParserException(new Throwable("Syntax error: error in method <condp>"));
            }
        } catch (ParserException e) {
            System.err.println(e.getMessage());
        }
    }

    void statlistp(){
        try{
            switch (look.tag){
                case ';':
                    match(';');
                    stat();
                    statlistp();
                    break;
                case Tag.EOF, '}':
                    break;
                default:
                    throw new ParserException(new Throwable("Syntax error: error in method "));
            }
        } catch (ParserException e) {
            System.err.println(e.getMessage());
        }
    }

    void idlist() {
        try {
            switch (look.tag){
                case Tag.ID:
                    match(Tag.ID);
                    idlistp();
                    break;
                default:
                    throw new ParserException(new Throwable("Syntax error: error in method <idlist>"));
            }
        } catch (ParserException e) {
            System.err.println(e.getMessage());
        }
    }

    void idlistp() {
        try {
            switch (look.tag){
                case ',':
                    match(',');
                    match(Tag.ID);
                    idlistp();
                    break;
                case ']', ';':
                    break;
                default:
                    throw new ParserException(new Throwable("Syntax error: error in method <idlistp>"));
            }
        } catch (ParserException e) {
            System.err.println(e.getMessage());
        }
    }

    void optlist() {
        try {
            optitem();
            if(look.tag == ';'){
                match(';');
                optlistp();
            }
        } catch (ParserException e) {
            System.err.println(e.getMessage());
        }
    }

    void optlistp() {
        try {
            optitem();
            if(look.tag == ';'){
                match(';');
                optlistp();
            }
        } catch (ParserException e) {
            System.err.println(e.getMessage());
        }
    }

    void optitem() {
        try {
            match(Tag.OPTION);
            match('(');
            bexpr();
            match(')');
            match(Tag.DO);
            stat();
        } catch (ParserException e) {
            System.err.println(e.getMessage());
        }
    }

    void bexpr() {
        try {
            match(Tag.RELOP);
            expr();
            expr();
        } catch (ParserException e) {
            System.err.println(e.getMessage());
        }
    }

    void expr(){
        try {
            switch (look.tag){
                case '+':
                    match('+');
                    match('(');
                    exprlist();
                    match(')');
                    break;
                case '-':
                    match('-');
                    expr();
                    expr();
                    break;
                case '*':
                    match('*');
                    match('(');
                    exprlist();
                    match(')');
                    break;
                case '/':
                    match('/');
                    expr();
                    expr();
                    break;
                case Tag.NUM:
                    match(Tag.NUM);
                    break;
                case Tag.ID:
                    match(Tag.ID);
                    break;
                default:
                    break;
            }
        } catch (ParserException e) {
            System.err.println(e.getMessage());
        }
    }

    void exprlist() {
        expr();
        exprlistp();
    }

    void exprlistp() {
        try {
            if(look.tag == ','){
                match(',');
                expr();
                exprlistp();
            }
        } catch (ParserException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void main (String[]args){
            Lexer lex = new Lexer();
            String path = "/Users/ilaario/Desktop/Progetti/2° Anno/Linguaggi Formali e Traduttori/ProgettoLFT/Es 3 - Parser/Es3.2/testParser.txt"; // il percorso del file da leggere
            try {
                BufferedReader br = new BufferedReader(new FileReader(path));
                Parser parser = new Parser(lex, br);
                parser.prog();
                System.out.println("Input OK");
                br.close();
            } catch(IOException e) {
                e.printStackTrace();
            } catch (ParserException e) {
                System.err.println(e.getMessage());
        }
    }
}