package Lexer;

public class readDFA {
    public static boolean scan(String word){
        int state = 0;
        int i = 0;

        while(i < word.length() && state >= 0){
            final char c = word.charAt(i++);

            switch (state){
                case 0:
                    if(c == 'r'){
                        state = 1;
                    } else {
                        state = -1;
                    }
                    break;

                case 1:
                    if(c == 'e'){
                        state = 2;
                    } else {
                        state = -1;
                    }
                    break;

                case 2:
                    if(c == 'a'){
                        state = 3;
                    } else {
                        state = -1;
                    }
                    break;

                case 3:
                    if(c == 'd'){
                        state = 4;
                    } else {
                        state = -1;
                    }
                    break;
            }
        }
        return state == 4;
    }
}