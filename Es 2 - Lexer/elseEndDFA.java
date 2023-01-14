package Lexer;

public class elseEndDFA {
    public static int scan(String word){
        int state = 0;
        int i = 0;

        while(i < word.length() && state >= 0){
            final char c = word.charAt(i++);

            switch (state){
                case 0:
                    if(c == 'e'){
                        state = 1;
                    } else {
                        state = -1;
                    }
                    break;

                case 1:
                    if(c == 'l'){
                        state = 2;
                    } else if (c == 'n'){
                        state = 5;
                    } else {
                        state = -1;
                    }
                    break;

                case 2:
                    if(c == 's'){
                        state = 3;
                    } else {
                        state = -1;
                    }
                    break;

                case 3:
                    if(c == 'e'){
                        state = 4;
                    } else {
                        state = -1;
                    }
                    break;

                case 5:
                    if(c == 'd'){
                        state = 6;
                    } else {
                        state = -1;
                    }
                    break;
            }
        }
        if(state == 4){
            return 1;
        } else if(state == 6){
            return 2;
        } else {
            return 0;
        }
    }
}
