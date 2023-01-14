public class beginDFA {
    public static boolean scan(String word){
        int state = 0;
        int i = 0;

        while(i < word.length() && state >= 0){
            final char c = word.charAt(i++);

            switch (state){
                case 0:
                    if(c == 'b'){
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
                    if(c == 'g'){
                        state = 3;
                    } else {
                        state = -1;
                    }
                    break;

                case 3:
                    if(c == 'i'){
                        state = 4;
                    } else {
                        state = -1;
                    }
                    break;

                case 4:
                    if(c == 'n'){
                        state = 5;
                    } else {
                        state = -1;
                    }
                    break;
            }
        }
        return state == 5;
    }
}
