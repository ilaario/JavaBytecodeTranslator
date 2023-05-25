import java.io.*;
public class Valutatore {
    private final Lexer lex;
    private final BufferedReader pbr;
    private Token look;

    public Valutatore(Lexer l, BufferedReader br) {
        lex = l;
        pbr = br;
        move();
    }

    void move() {
        look = lex.lexical_scan(pbr);
        System.out.println("token = " + look);    }

    void error(String s) {
        throw new Error("Linea n." + Lexer.line + ": " + s);
    }

    void match(int t) {
        if (look.tag == t) {
            if (look.tag != Tag.EOF) move();
        } else {
            error("Errore sintattico durante il match("+ look.tag +" -> " + t +")");
        }    }

    public void start() {
        int expr_val;
        switch (look.tag) {
            case Tag.NUM, '(' -> {
                expr_val = expr();
                System.out.println(expr_val);
            }
            case Tag.EOF -> match(Tag.EOF);
            default -> error("Errore in <start>");
        }
    }

    private int expr() {
        int term_val, expr_val, exprp_val;
        term_val = term();
        exprp_val = exprp(term_val);
        expr_val = exprp_val;
        return expr_val;
    }

    private int term() {
        int termp_val;
        termp_val = termp(fact());
        return termp_val;
    }

    private int exprp(int exprp_i) {
        int term_val, exprp_val;

        switch (look.tag) {
            case '+' -> {
                match('+');
                term_val = term();
                exprp_val = exprp(exprp_i + term_val);
                return exprp_val;
            }
            case '-' -> {
                match('-');
                term_val = term();
                exprp_val = exprp(exprp_i - term_val);
                return exprp_val;
            }
            default -> {
                exprp_val = exprp_i;
                return exprp_val;
            }
        }
    }

    private int termp(int termp_i) {
        int fact_val, termp_val;

        switch (look.tag) {
            case '*' -> {
                match('*');
                fact_val = fact();
                termp_val = termp(termp_i * fact_val);
                return termp_val;
            }
            case '/' -> {
                match('/');
                fact_val = fact();
                termp_val = termp(termp_i / fact_val);
                return termp_val;
            }
            default -> {
                termp_val = termp_i;
                return termp_val;
            }
        }
    }

    private int fact() {
        int fact_val, expr_val;
        switch(look.tag){
            case '(':
                match('(');
                expr_val = expr();
                if(look.tag == ')'){
                    match(')');
                    fact_val = expr_val;
                    break;
                } else {
                    error("Errore sintattico");
                }


            case Tag.NUM:
                fact_val = Lexer.getNUM();
                match(Tag.NUM);
                break;

            default:
                fact_val = 0;
                break;
        }
        return fact_val;
    }
    public static void main(String[] args) {
        Lexer lex = new Lexer();
        String path = "/Users/ilaario/Desktop/Progetti/ProgettoLFT/Es 4 - Validator/testValidator.txt"; // il percorso del file da leggere
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            Valutatore valutatore = new Valutatore(lex, br);
            valutatore.start();
            br.close();
        } catch (IOException e) {e.printStackTrace();}
    }
}

