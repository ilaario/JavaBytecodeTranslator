/*import java.io.*;

public class Parser {
    private Lexer lex;
    private BufferedReader pbr;
    private Token look;

    public Parser(Lexer l, BufferedReader br) {
        lex = l;
        pbr = br;
        move();
    }

    void move() {
        look = lex.lexical_scan(pbr);
        System.out.println("token = " + look);
    }

    void error(String s) {
        throw new Error("near line " + lex.line + ": " + s);
    }

    void match(int t) {
        if (look.tag == t) {
            if (look.tag != Tag.EOF) move();
        } else error("syntax error " + look.tag + " " + t);
    }

    void prog(){
        statlist();
        match(Tag.EOF);
    }

    void statlist(){
        stat();
        statlistp();
    }
    void statlistp(){
        switch (look.tag){
            case ';':
                match(';');
                stat();
                statlistp();
                break;
            case '}', Tag.EOF: //errore 1
                break;
            default:
                error("Errore sintattico in statlistp");
        }
    }
    void stat(){
        switch (look.tag){
            case Tag.ASSIGN:
                match(Tag.ASSIGN);
                expr();
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
                exprlist();
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
                match(Tag.COND); //Errore 2
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

            default:
                error("Errore sintattico in stat");
        }
    }
    void condp(){
        switch (look.tag){
            case Tag.END:
                match(Tag.END);
                break;
            case Tag.ELSE:
                match(Tag.ELSE);
                stat();
                match(Tag.END);
                break;

        }
    }
    void idlist(){
        switch (look.tag){
            case Tag.ID:
                match(Tag.ID);
                idlistp();
                break;

            default:
                error("Errore sintattico in idlist");
        }
    }
    void idlistp(){
        switch (look.tag){
            case ',':
                match(',');
                match(Tag.ID);
                idlistp();
                break;
            case ';', '}', Tag.EOF: //Errore 3
                break;

            default:
                error("Errore sintattico in idlistp");
        }
    }
    void optlist(){
        optitem();
        optlistp();

    }
    void optlistp(){
        switch (look.tag){
            case Tag.OPTION:
                optitem();
                optlistp();
                break;
            case ']':
                break;
            default:
                error("Errore sintattico in optlistp");
        }
    }
    void optitem(){
        switch (look.tag){
            case Tag.OPTION:
                match(Tag.OPTION);
                match('(');
                bexpr();
                match(')');
                match(Tag.DO);
                stat();
                break;
        }
    }
    void bexpr(){
        switch (look.tag){
            case Tag.RELOP:
                match(Tag.RELOP);
                expr();
                expr();
                break;
        }
    }
    void expr(){
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
        }
    }
    void exprlist(){
        expr();
        exprlistp();
    }
    void exprlistp(){
        switch (look.tag){
            case ',':
                match(',');
                expr();
                exprlistp();
                //match(Tag.EOF);
                break;
        }
    }



    public static void main(String[] args) {
        Lexer lex = new Lexer();
        String path = "/Users/ilaario/Desktop/Progetti/ProgettoLFT/Es 3 - Parser/ParserAlegulli/testParser.txt"; // il percorso del file da leggere
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            Parser parser = new Parser(lex, br);
            parser.prog();
            System.out.println("Input OK");
            br.close();
        } catch (IOException e) {e.printStackTrace();}
    }
}*/

import java.io.*;

public class Parser {
    private Lexer lex;
    private BufferedReader pbr;
    private Token look;

    public Parser(Lexer l, BufferedReader br) {
        lex = l;
        pbr = br;
        move();
    }

    void move() {
        look = lex.lexical_scan(pbr);
        System.out.println("token = " + look);
    }

    void error(String s) {
        throw new Error("near line " + lex.line + ": " + s);
    }

    void match(int t) {
        if (look.tag == t) {
            if (look.tag != Tag.EOF) move();
        } else error("syntax error " + look.tag + " " + t);
    }

    void prog (){
        statlist();
        match(Tag.EOF);
    }

    void statlist (){
        stat();
        statlistp();
    }
    void statlistp(){
        switch (look.tag){
            case ';':
                match(';');
                stat();
                statlistp();
                break;
            case '}', Tag.EOF:
                break;
            default:
                error("Errore sintattico in statlistp " + look.tag);
        }
    }
    void stat(){
        switch (look.tag){
            case Tag.ASSIGN:
                match(Tag.ASSIGN);
                expr();
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
                exprlist();
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

            default:
                error("Errore sintattico in stat");
        }
    }
    void condp(){
        switch (look.tag){
            case Tag.END:
                match(Tag.END);
                break;
            case Tag.ELSE:
                match(Tag.ELSE);
                stat();
                match(Tag.END);
                break;

        }
    }
    void idlist(){
        switch (look.tag){
            case Tag.ID:
                match(Tag.ID);
                idlistp();
                break;

            default:
                error("Errore sintattico in idlist");
        }
    }
    void idlistp(){
        switch (look.tag){
            case ',':
                match(',');
                match(Tag.ID);
                idlistp();
                break;
            case ';', '}', Tag.EOF:
                break;

            default:
                error("Errore sintattico in idlistp");
        }
    }
    void optlist(){
        optitem();
        optlistp();

    }
    void optlistp(){
        switch (look.tag){
            case Tag.OPTION:
                optitem();
                optlistp();
                break;
            case ']':
                break;
            default:
                error("Errore sintattico in optlistp");
        }
    }
    void optitem(){
        switch (look.tag){
            case Tag.OPTION:
                match(Tag.OPTION);
                match('(');
                bexpr();
                match(')');
                match(Tag.DO);
                stat();
                break;
        }
    }
    void bexpr(){
        switch (look.tag){
            case Tag.RELOP:
                match(Tag.RELOP);
                expr();
                expr();
                break;
        }
    }
    void expr(){
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
        }
    }
    void exprlist(){
        expr();
        exprlistp();
    }
    void exprlistp(){
        switch (look.tag){
            case ',':
                match(',');
                expr();
                exprlistp();
                break;
        }
    }



    public static void main(String[] args) {
        Lexer lex = new Lexer();
        String path = "/Users/ilaario/Desktop/Progetti/ProgettoLFT/Es 3 - Parser/ParserAlegulli/testParser.txt"; // il percorso del file da leggere
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            Parser parser = new Parser(lex, br);
            parser.prog();
            System.out.println("Input OK");
            br.close();
        } catch (IOException e) {e.printStackTrace();}
    }
}