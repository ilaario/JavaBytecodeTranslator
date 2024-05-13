import java.io.*;

public class Lexer_5 {

    public int line = 1;
    private char peek = ' ';
    static String parola = "";
    static int n = 0;

    private void readch(BufferedReader br) {
        try {
            peek = (char) br.read();
        } catch (IOException exc) {
            peek = (char) -1; // ERROR
        }
    }

    public Token lexical_scan(BufferedReader br) {
        while (peek == ' ' || peek == '\t' || peek == '\n'  || peek == '\r') {
            if (peek == '\n') line++;
            readch(br);
        }

        switch (peek) {
            case '!':
                peek = ' ';
                return Token.not;

            case '(':
                peek = ' ';
                return Token.lpt;

            case ')':
                peek = ' ';
                return Token.rpt;

            case '[':
                peek = ' ';
                return Token.lpq;

            case ']':
                peek = ' ';
                return Token.rpq;

            case '{':
                peek = ' ';
                return Token.lpg;

            case '}':
                peek = ' ';
                return Token.rpg;

            case '+':
                peek = ' ';
                return Token.plus;

            case '-':
                peek = ' ';
                return Token.minus;

            case '*':
                peek = ' ';
                return Token.mult;
//se trovo due slash continuo a leggere, fino a quando non trovo \n o fine file
            case '/':
                readch(br);
                if(peek == '/'){
                    while(peek != '\n' && peek != (char)-1){
                        readch(br);
                    }
                    return lexical_scan(br);
                }
                if (peek == '*') {
                    int state = 0;
                    do {
                        readch(br);
                        switch (state) {
                            case 0:
                                if (peek == '*') state = 1;
                                else if (peek == (char)-1) state = -1;
                                break;
                            case 1:
                                if (peek == '/') state = 2;
                                else if(peek == '*') state = 1;
                                else state = 0;
                                break;
                        }
                    } while (state != 2 && state != -1);
                    peek = ' ';
                    return state == 2 ? lexical_scan(br) : null;
                }
                return Token.div;

            case ';':
                peek = ' ';
                return Token.semicolon;

            case ',':
                peek = ' ';
                return Token.comma;


            case '&':
                readch(br);
                if (peek == '&') {
                    peek = ' ';
                    return Word.and;
                } else {
                    System.err.println("Erroneous character"
                            + " after & : "  + peek );
                    return null;
                }

            case '|':
                readch(br);
                if (peek == '|') {
                    peek = ' ';
                    return Word.or;
                } else {
                    System.err.println("Erroneous character"
                            + " after | : "  + peek );
                    return null;
                }

            case '<':
                readch(br);
                if (peek == '=') {
                    peek = ' ';
                    return Word.le;
                } else if(peek == '>') {
                    peek = ' ';
                    return Word.ne;
                } else return Word.lt;

            case '>':
                readch(br);
                if (peek == '=') {
                    peek = ' ';
                    return Word.ge;
                } else return Word.gt;


            case '=':
                readch(br);
                if (peek == '=') {
                    peek = ' ';
                    return Word.eq;
                } else {
                    System.err.println("Erroneous character"
                            + " after = : "  + peek );
                    return null;
                }

            case (char)-1:
                return new Token(Tag.EOF);

            default:
                if (Character.isLetter(peek) || peek == '_') {
                    parola = "";
                    boolean continua = true;
                    while(continua){
                        if(Character.isLetter(peek) || Character.isDigit(peek) || peek == '_'){
                            parola += peek;
                            readch(br);
                        }
                        else continua = false;
                    }
                    switch(parola){
                        case "assign": return Word.assign;
                        case "to": return Word.to;
                        case "conditional": return Word.conditional;
                        case "option": return Word.option;
                        case "do": return Word.dotok;
                        case "else": return Word.elsetok;
                        case "while": return Word.whiletok;
                        case "begin": return Word.begin;
                        case "end": return Word.end;
                        case "print": return Word.print;
                        case "read": return Word.read;
                        default:
                            if(IdentifDFA.scan(parola)){
                                return new Word(Tag.ID, parola);
                            } else {
                                System.err.println("identificatore non valido");
                                return null;
                            }

                    }

                } else if (Character.isDigit(peek)) {
                    n = 0;
                    if(peek == '0') {
                        readch(br);
                        if(Character.isDigit(peek))  {
                            System.err.println("Erroneous character"
                                    + " after 0 : "  + peek );
                            return null;
                        } else return new NumberTok(0);

                    } else {
                        //converto il numero carattere per carattere da ASCII a intero
                        do {
                            n = n*10 + (int)peek - '0';
                            readch(br);
                        } while(Character.isDigit(peek));

                        return new NumberTok(n);
                    }

                }else {
                    System.err.println("Identificatore non valido");
                    return null;
                }
        }


    }

    public static int getNum(){
        return n;
    }

    public static String getID(){
        return parola;
    }



    public static void main(String[] args) {
        Lexer_5 lex = new Lexer_5();
        String path = "C:\\Users\\giopi\\Downloads\\Progetto LFT\\Progetto LFT\\Es 5 - Traduttore\\test.lft";
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            Token tok;
            do {
                tok = lex.lexical_scan(br);
                System.out.println("Scan: " + tok);
            } while (tok.tag != Tag.EOF);
            br.close();
        } catch (IOException e) {e.printStackTrace();}
    }

}
