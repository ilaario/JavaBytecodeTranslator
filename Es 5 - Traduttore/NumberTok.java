public class NumberTok extends Token {
    /*public String lexeme;
    public NumberTok(int t, String s) { super(t); lexeme = s;}

    public String toString(){
        return "<" + tag + ", " + lexeme +">";
    }*/
    public String lexeme = "";
    public NumberTok(int tag, String s) { super(tag); lexeme=s; }
    public NumberTok(int s) { super(Tag.NUM); lexeme=String.valueOf(s); }
    public String toString() { return "<" + tag + ", " + lexeme + ">"; }
}
