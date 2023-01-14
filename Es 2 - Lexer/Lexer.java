package Lexer;
import java.io.*;

public class Lexer {

    public static int line = 1;
    private char peek = ' ';
    public String num;
    
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
                if (Character.isLetter(peek) || peek == '_') {
                    boolean continua = true;
                    StringBuilder wordBuild = new StringBuilder();
                    //String word = "";
                    while(continua){
                        if(Character.isLetter(peek) || Character.isDigit(peek) || peek == '_'){
                            wordBuild.append(peek);
                            //word += peek;
                            readch(br);
                        } else {
                            continua = false;
                        }
                    }
                    String word = wordBuild.toString();

                    if(!IdentifDFA.scan(word)){
                        System.err.println("String not valid");
                        return null;
                    } else {
                        switch (word.charAt(0)){
                            case 'a':
                                if(assignDFA.scan(word)){
                                    return Word.assign;
                                } else {
                                    return new Word(Tag.ID, word);
                                }

                            case 'b':
                                if(beginDFA.scan(word)){
                                    return Word.begin;
                                } else {
                                    return new Word(Tag.ID, word);
                                }

                            case 'c':
                                if(conditionalDFA.scan(word)){
                                    return Word.conditional;
                                } else {
                                    return new Word(Tag.ID, word);
                                }

                            case 'd':
                                if(doDFA.scan(word)){
                                    return Word.dotok;
                                } else {
                                    return new Word(Tag.ID, word);
                                }

                            case 'e':
                                int opt = elseEndDFA.scan(word);
                                if(opt == 1){
                                    return Word.elsetok;
                                } else if(opt == 2){
                                    return Word.end;
                                } else {
                                    return new Word(Tag.ID, word);
                                }

                            case 'o':
                                if(optionDFA.scan(word)){
                                    return Word.option;
                                } else {
                                    return new Word(Tag.ID, word);
                                }

                            case 'p':
                                if(printDFA.scan(word)){
                                    return Word.print;
                                } else {
                                    return new Word(Tag.ID,word);

                                }

                            case 'r':
                                 if(readDFA.scan(word)){
                                    return Word.read;
                                } else {
                                    return new Word(Tag.ID,word);
                                }

                            case 't':
                                if(toDFA.scan(word)){
                                    return Word.to;
                                } else {
                                    return new Word(Tag.ID,word);
                                }

                            case 'w':
                                if(whileDFA.scan(word)){
                                    return Word.whiletok;
                                } else {
                                    return new Word(Tag.ID,word);
                                }

                            default:
                                return new Word(Tag.ID, word);
                        }
                    }
                } else if (Character.isDigit(peek)) {
                    boolean isValid = true, continua = true;
                    StringBuilder wordBuild = new StringBuilder();
                    while(continua){
                        if(Character.isDigit(peek)){
                            wordBuild.append(peek);
                            readch(br);
                        } else if ((Character.isLetter(peek) || peek == '_') && peek != ' ') {
                            wordBuild.append(peek);
                            readch(br);
                            isValid = false;
                        } else {
                            continua = false;
                        }
                    }
                    num = wordBuild.toString();
                    if(!isValid){
                        System.err.println("Erroneous character: "
                                + num );
                        return null;
                    } else {
                        return new NumberTok(Tag.NUM, num);
                    }
                } else {
                        System.err.println("Erroneous character: " 
                                + peek );
                        return null;
                }
         }
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

}
