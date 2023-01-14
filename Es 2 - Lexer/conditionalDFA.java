package Lexer;

public class conditionalDFA {
    public static boolean scan(String word){
        int state = 0;
        int i = 0;

        while(i < word.length() && state >= 0){
            final char c = word.charAt(i++);

            switch (state){
                case 0:
                    if(c == 'c'){
                        state = 1;
                    } else {
                        state = -1;
                    }
                    break;

                case 1:
                    if(c == 'o'){
                        state = 2;
                    } else {
                        state = -1;
                    }
                    break;

                case 2:
                    if(c == 'n'){
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

                case 4:
                    if(c == 'i'){
                        state = 5;
                    } else {
                        state = -1;
                    }
                    break;

                case 5:
                    if(c == 't'){
                        state = 6;
                    } else {
                        state = -1;
                    }
                    break;

                case 6:
                    if(c == 'i'){
                        state = 7;
                    } else {
                        state = -1;
                    }
                    break;

                case 7:
                    if(c == 'o'){
                        state = 8;
                    } else {
                        state = -1;
                    }
                    break;

                case 8:
                    if(c == 'n'){
                        state = 9;
                    } else {
                        state = -1;
                    }
                    break;

                case 9:
                    if(c == 'a'){
                        state = 10;
                    } else {
                        state = -1;
                    }
                    break;

                case 10:
                    if(c == 'l'){
                        state = 11;
                    }  else {
                        state = -1;
                    }
                    break;
            }
        }
        return state == 11;
    }
}
