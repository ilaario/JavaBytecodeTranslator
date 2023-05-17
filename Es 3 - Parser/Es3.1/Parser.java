import java.io.*;

public class Parser {
    private Lexer lex;
    private BufferedReader pbr;
    private Token look;

    public Parser(Lexer l, BufferedReader br) { lex = l;
        pbr = br;
        move();
    }
    void move() {
        look = lex.lexical_scan(pbr); System.out.println("token = " + look);
    }
    void error(String s) {
        throw new Error("Linea n." + lex.line + ": " + s);
    }
    void match(int t) {
        if (look.tag == t) {
            if (look.tag != Tag.EOF) move(); } else error("Errore sintattico durante il match(" + look.tag + " -> " + t +")");
    }
    public void start() {
        switch (look.tag){
            case '(', Tag.NUM:
                expr();
            case Tag.EOF:
                break;
            default:
                error("Errore sintattico in <start>");
        }
    }
    private void expr() {
        term();
        exprp();
    }

    private void term () {
        fact();
        termp();
    }

    private void exprp() {
        switch (look.tag){
            case '+':
                match('+');
                term();
                exprp();
                break;

            case '-':
                match('-');
                term();
                exprp();
                break;

            case ')', Tag.EOF:
                break;

            default:
                error("Errore sintattico");
        }
    }

    private void termp () {
        switch (look.tag){
            case '*':
                match('*');
                fact();
                termp();
                break;

            case '/':
                match('/');
                fact();
                termp();
                break;

            case '+', '-', ')', Tag.EOF:
                break;

            default:
                error("Errore sintattico");
        }
    }
    private void fact () {
        switch (look.tag){
            case '(':
                match('(');
                expr();
                match(')');
                break;

            case Tag.NUM:
                match(Tag.NUM);
                break;

            case Tag.EOF:
                break;

            default:
                error("Errore sintattico in <fact>: " + look.tag);
        }
    }

    public static void main (String[]args){
        Lexer lex = new Lexer();
        String path = "/Users/ilaario/Desktop/Progetti/ProgettoLFT/Es 3 - Parser/Es3.1/testParser"; // il percorso del file da leggere
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            Parser parser = new Parser(lex, br);
            parser.start();
            System.out.println("Input OK");
                br.close();
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}