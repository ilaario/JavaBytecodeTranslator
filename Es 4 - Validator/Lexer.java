import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Lexer {

    public static int line = 1;
    private char peek = ' ';
    static String num;
    
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
                readch(br);
                if (peek == '=') {
                    peek = ' ';
                    return Word.neq;
                } else {
                    peek = ' ';
                    return Token.not;
                }

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

            case '/':
                readch(br);
                if(peek == '/'){
                    while(peek != '\n'){
                        readch(br);
                    }
                    return lexical_scan(br);
                } else if(peek == '*'){
                    boolean continua = true;
                    while(continua){
                        readch(br);
                        if(peek == '*'){
                            readch(br);
                            if(peek == '/'){
                                continua = false;
                            }
                        }
                    }
                    readch(br);
                    return lexical_scan(br);
                } else {
                    peek = ' ';
                    return Token.div;
                }

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
                if(peek == '|'){
                    peek = ' ';
                    return Word.or;
                } else {
                    System.err.println("Erroneous character"
                            + " after | :" + peek);
                    return null;
                }

            case '<':
                readch(br);
                if(peek == '='){
                    peek = ' ';
                    return Word.le;
                } else if(peek == '>'){
                    peek = ' ';
                    return Word.ne;
                } else {
                    peek = ' ';
                    return Word.lt;
                }

            case '>':
                readch(br);
                if(peek == '='){
                    peek = ' ';
                    return Word.ge;
                } else {
                    peek = ' ';
                    return Word.gt;
                }

            case '=':
                readch(br);
                if(peek == '='){
                    peek = ' ';
                    return Word.eq;
                } else {
                    System.err.println("Erroneous character"
                            + " after = :" + peek);
                    return null;
                }
          
            case (char)-1:
                return new Token(Tag.EOF);

            default:
                if (lettera(peek)) {
                    int state = 0;
                    String s="";
                    while (state >= 0 && (lettera(peek)||(peek >= '0' && peek <= '9'))) {
                        switch (state) {
                            case 0:
                                if (lettera(peek))
                                    state = 1;
                                else if (peek >= '0' && peek <= '9')
                                    state = -1;
                                else state = -1;
                                break;
                            case 1:
                                if ((peek >= '0' && peek <= '9') || lettera(peek))
                                    state = 1;
                                break;
                        }
                        s=s+peek;
                        readch(br);
                    }
                    if(state==1){
                        if(s.compareTo("assign")==0)
                            return Word.assign;
                        else if(s.compareTo("to")==0)
                            return Word.to;
                        else if(s.compareTo("conditional")==0)
                            return Word.conditional;
                        else if(s.compareTo("option")==0)
                            return Word.option;
                        else if(s.compareTo("do")==0)
                            return Word.dotok;
                        else if(s.compareTo("else")==0)
                            return Word.elsetok;
                        else if(s.compareTo("while")==0)
                            return Word.whiletok;
                        else if(s.compareTo("begin")==0)
                            return Word.begin;
                        else if(s.compareTo("end")==0)
                            return Word.end;
                        else if(s.compareTo("print")==0)
                            return Word.print;
                        else  if(s.compareTo("read")==0)
                            return Word.read;
                        else return new Word(Tag.ID,s);
                    }


                    // ... gestire il caso degli identificatori FATTO e delle parole chiave //

                } else if (Character.isDigit(peek)) {
                    boolean isValid = true, continua = true;
                    StringBuilder wordBuild = new StringBuilder();
                    while(continua){
                        if(Character.isDigit(peek)){
                            wordBuild.append(peek);
                            readch(br);
                        } else {
                            continua = false;
                        }
                    }
                    num = wordBuild.toString();
                    if(!isValid){
                        return new Word(Tag.ID, num);
                    } else {
                        return new NumberTok(Tag.NUM, num);
                    }
                } else {
                    System.err.println("Erroneous character: "
                            + peek);
                    return null;
                }
        }
        return null;
    }

    public static int getNUM(){
        return Integer.parseInt(num);
    }
		
    public static void main(String[] args) {
        Lexer lex = new Lexer();
        String path = "/Users/ilaario/Desktop/Progetti/ProgettoLFT/Es 2 - Lexer/Bonfiglio Dario/testLexer.txt"; // il percorso del file da leggere
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

    public static boolean lettera(char ch){
        if(ch>='a' && ch<='z') return true;
        else if(ch>='A' && ch<='Z') return true;
        else return false;
    }

}