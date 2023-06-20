public class NumberTok extends Token {
    public String lexeme;
    public NumberTok(int t, String s) { super(t); lexeme = s;}

    public String toString(){
        return "<" + tag + ", " + lexeme +">";
    }
}
