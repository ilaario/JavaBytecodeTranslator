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
            if (look.tag != Tag.EOF) move(); } else error("Errore sintattico durante il match()");
    }
    public void start() {
        if(look.tag == '(' || look.tag == Tag.NUM){
            expr();
            match(Tag.EOF);
        } else {
            error("Errore sintattico in <start>.");
        }
    }
    private void expr() {
        if(look.tag == '(' || look.tag == Tag.NUM){
            term();
            exprp();
        } else {
            error("Errore sintattico in <expr>.");
        }
    }
    private void exprp() {
        switch (look.tag) {
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
            case ')':
            case Tag.EOF:
                break;
            default:
                error("Errore sintattico in <exprp>.");
        }
    }
        private void term () {
            if(look.tag == '(' || look.tag == Tag.NUM){
                fact();
                termp();
            } else {
                error("Errore sintattico in <term>.");
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
                case Tag.EOF:
                case '+':
                case '-':
                case ')':
                    break;
                default:
                    error("Errore sintattico in <termp>.");
            }
        }
        private void fact () {
            switch (look.tag){
                case Tag.NUM:
                    match(Tag.NUM);
                    break;
                case Tag.ID:
                    match(Tag.ID);
                    break;
                default:
                    match('(');
                    expr();
                    if(look.tag == ')'){
                        match(')');
                    } else {
                        error("Errore sintattico in <fact>.");
                    }
                    break;
            }
        }

        public static void main (String[]args){
            Lexer lex = new Lexer();
            String path = "/Users/ilaario/Desktop/Progetti/ProgettoLFT/test.txt"; // il percorso del file da leggere
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