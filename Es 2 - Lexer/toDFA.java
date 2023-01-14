public class toDFA {
    public static boolean scan(String word){
        int state = 0;
        int i = 0;

        while(i < word.length() && state >= 0){
            final char c = word.charAt(i++);

            switch (state){
                case 0:
                    if(c == 't'){
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
            }
        }
        return state == 2;
    }
}
