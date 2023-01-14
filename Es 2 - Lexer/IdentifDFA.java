package Lexer;

public class IdentifDFA {
    public static boolean scan(String s){
        int state = 0;
        int i = 0;
        while(state >= 0 && i < s.length()){
            final int c = s.charAt(i++);

            switch (state){
                case 0:
                    if(c >= 97 && c <= 122){
                        state = 1;
                    } else if(c==95){
                        state = 2;
                    } else {
                        state = -1;
                    }
                    break;

                case 1:
                    if((c >= 97 && c <= 122) || c == 95 || (c >= 48 && c <= 57)){
                        state = 1;
                    } else {
                        state = -1;
                    }
                    break;

                case 2:
                    if((c >= 97 && c <= 122) || (c >= 48 && c <= 57)){
                        state = 1;
                    } else {
                        state = -1;
                    }
                    break;
            }

        }
        return state == 1;
    }
}
