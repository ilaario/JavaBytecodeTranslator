public class TreZeri {
    public static boolean scan(String s){
        int state = 0;
        int i = 0;
        while (state >= 0 && i < s.length()){
            final char ch = s.charAt(i++);

            switch (state){
                case 0:
                    if(ch=='0'){
                        state = 1;
                    } else if (ch =='1') {
                        state = 0;
                    } else {
                        state = -1;
                    }
                    break;

                case 1:
                    if(ch == '0'){
                        state = 2;
                    } else if (ch == '1'){
                        state = 0;
                    } else {
                        state = -1;
                    }
                    break;

                case 2:
                    if(ch == '0'){
                        state = 3;
                    } else if (ch == '1'){
                        state = 0;
                    } else {
                        state = -1;
                    }
                    break;

                case 3:
                    if(ch=='0'||ch=='1'){
                        state = 3;
                    } else {
                        state = -1;
                    }
            }
        }
        return state == 3;
    }

    public static void main(String[] args){
        String t0 = "1100011001";

        String t6 = "010101";
        String t7 = "10064";

        System.out.println(t0 + "\nATTESO: \tAccettato \nRISULTATO:\t" + (scan(t0) ? "Accettato" : "Errore") + "\n");

        System.out.println(t6 + "\nATTESO: \tErrore \nRISULTATO:\t" + (scan(t6) ? "Accettato" : "Errore") + "\n");
        System.out.println(t7 + "\nATTESO: \tErrore \nRISULTATO:\t" + (scan(t7) ? "Accettato" : "Errore") + "\n");
    }
}