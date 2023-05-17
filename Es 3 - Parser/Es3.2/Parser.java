import javax.swing.plaf.TableHeaderUI;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

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
        throw new Error("Linea n." + lex.line + ": " + s);
    }
    void match(int t) {
        if (look.tag == t) {
            if (look.tag != Tag.EOF) move();
        } else {
            error("Errore sintattico durante il match("+ look.tag +"->"+ (char)t+")");
        }
    }

    void prog() {
        switch (look.tag){
            case Tag.ASSIGN, Tag.COND, Tag.PRINT, Tag.READ, Tag.WHILE:
                statlist();
                if(look.tag == Tag.EOF){
                    match(Tag.EOF);
                } else {
                    error("Errore sintattico in <prog>.");
                }
                break;
            default:
                error("Errore sintattico in <prog>.");
        }
    }

    void statlist(){
        stat();
        statlistp();
    }

    void stat() {
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
                if(look.tag == '['){
                    match('[');
                } else {
                    error("Errore sintaticco in <stat>, comando PRINT");
                }
                exprlist();
                if(look.tag == ']'){
                    match(']');
                } else {
                    error("Errore sintattico in <stat>, comando PRINT");
                }
                break;

            case Tag.READ:
                match(Tag.READ);
                if(look.tag == '[') {
                    match('[');
                } else {
                    error("Errore sintattico in <stat>, comando READ");
                }
                idlist();
                if(look.tag == ']'){
                    match(']');
                } else {
                    error("Errore sintattico in <stat>, comando READ");
                }
                break;

            case Tag.WHILE:
                match(Tag.WHILE);
                if(look.tag == '(') {
                    match('(');
                } else {
                    error("Errore sintattico in <stat>, comando WHILE");
                }
                bexpr();
                if (look.tag == ')'){
                    match(')');
                } else {
                    error("Errore sintattico in <stat>, comando WHILE");
                }
                stat();
                break;

            case Tag.COND:
                match(Tag.COND);
                if(look.tag == '[') {
                    match('[');
                } else {
                    error("Errore sintattico in <stat>, comando COND");
                }
                optlist();
                if(look.tag == ']'){
                    match(']');
                } else {
                    error("Errore sintattico in <stat>, comando COND");
                }
                if(look.tag == Tag.END){
                    match(Tag.END);
                    break;
                } else if(look.tag == Tag.ELSE){
                    match(Tag.ELSE);
                    stat();
                    if(look.tag == Tag.END){
                        match(Tag.END);
                        break;
                    } else {
                        error("Errore sintattico in <stat>, comando COND");
                    }
                } else {
                    error("Errore sintattico in <stat>, comando COND");
                }

            case '{':
                match('{');
                statlist();
                match('}');
                break;

            case Tag.EOF, '}':
                break;

            default:
                error("Errore sintattico in <stat>, codice di errore n. 2");
        }
    }

    void statlistp(){
        switch (look.tag){
            case ';':
                match(';');
                stat();
                statlistp();
                break;
            case Tag.EOF, '}':
                break;
            default:
                error("Errore sintattico in <statlistp>");
        }
    }

    void idlist() {
        if(look.tag == Tag.ID){
            match(Tag.ID);
            idlistp();
        } else {
            error("Errore sintattico in <idlist>");
        }
    }

    void idlistp() {
        if(look.tag == ','){
            match(',');
            if(look.tag == Tag.ID){
                match(Tag.ID);
                idlistp();
            } else {
                error("Errore sintattico in <idlistp>");
            }
        }
    }

    void optlist() {
        optitem();
        if(look.tag == ';'){
            match(';');
            optlistp();
        }
    }

    void optlistp() {
        optitem();
        if(look.tag == ';'){
            match(';');
            optlistp();
        }
    }

    void optitem() {
        if(look.tag == Tag. OPTION){
            match(Tag.OPTION);
        } else {
            error("Errore sintattico in <optitem>, codice di errore n. 1; look.tag trovato = " + look.tag + ", look.tag richiesto = " + Tag.OPTION);
        }
        if(look.tag == '(') {
            match('(');
        } else {
            error("Errore sintattico in <optitem>, codice di errore n. 2; look.tag trovato = " + look.tag + ", look.tag richiesto = " + '(');
        }
        bexpr();
        if (look.tag == ')'){
            match(')');
        } else {
            error("Errore sintattico in <optitem>, codice di errore n. 3");
        }
        if(look.tag == Tag.DO){
            match(Tag.DO);
        } else {
            error("Errore sintattico in <optitem>, codice di errore n. 4");
        }
        stat();
    }

    void bexpr() {
        if(look.tag == Tag.RELOP){
            match(Tag.RELOP);
        } else {
            error("Errore sintattico in <bexpr>");
        }
        expr();
        expr();
    }

    /*
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
    } */

    void expr(){
        switch (look.tag){
            case '+':
                match('+');
                if(look.tag == '('){
                    match('(');
                } else {
                    error("Errore sintattico in <expr>, codice di errore n. 1");
                }
                exprlist();
                if(look.tag == ')'){
                    match(')');
                } else {
                    error("Errore sintattico in <expr>, codice di errore n. 2");
                }
                break;
            case '-':
                match('-');
                if(look.tag == '('){
                    match('(');
                } else {
                    error("Errore sintattico in <expr>, codice di errore n. 3");
                }
                exprlist();
                if(look.tag == ')'){
                    match(')');
                } else {
                    error("Errore sintattico in <expr>, codice di errore n. 4");
                }
                break;
            case '*':
                match('*');
                if(look.tag == '('){
                    match('(');
                } else {
                    error("Errore sintattico in <expr>, codice di errore n. 5");
                }
                exprlist();
                if(look.tag == ')'){
                    match(')');
                } else {
                    error("Errore sintattico in <expr>, codice di errore n. 6");
                }
                break;
            case '/':
                match('/');
                if(look.tag == '('){
                    match('(');
                } else {
                    error("Errore sintattico in <expr>, codice di errore n. 7");
                }
                exprlist();
                if(look.tag == ')'){
                    match(')');
                } else {
                    error("Errore sintattico in <expr>, codice di errore n. 8");
                }
                break;
            case Tag.NUM:
                match(Tag.NUM);
                break;
            case Tag.ID:
                match(Tag.ID);
                break;
            default:
                //error("Errore sintattico in <expr>, codice di errore n. 5");
                break;
        }
    }

    void exprlist() {
        expr();
        exprlistp();
    }

    void exprlistp() {
        if(look.tag == ','){
            match(',');
            expr();
            exprlistp();
        }
    }

    public static void main (String[]args){
            Lexer lex = new Lexer();
            String path = "/Users/ilaario/Desktop/Progetti/ProgettoLFT/Es 3 - Parser/Es3.2/testParser.txt"; // il percorso del file da leggere
            try {
                BufferedReader br = new BufferedReader(new FileReader(path));
                Parser parser = new Parser(lex, br);
                parser.prog();
                System.out.println("Input OK");
                br.close();
            } catch(IOException e){
                e.printStackTrace();
        }
    }
}