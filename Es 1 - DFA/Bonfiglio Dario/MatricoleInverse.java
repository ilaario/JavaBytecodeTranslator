public class MatricoleInverse {
    public static boolean scan(String s){
        int state = 0;
        int i = 0;

        while(i < s.length() && state <= 0){
            final char c = s.charAt(i++);

            switch(state) {
                case 0:
                    if((c >= 97 && c <= 107) || ( c >= 65 && c <= 76)){
                        state = 1;
                    } else if((c >= 108 && c <= 122) || ( c >= 77 && c <= 90)) {
                        state = 2;
                    } else {
                        state = -1;
                    }
                    break;

                case 1:
                    if(i==(s.length()-1)){
                        if(c%2!=0){
                            state = 3;
                        } else {
                            state = -1;
                        };
                    } else {
                        state = 1;
                    }
                    break;

                case 2:
                    if(i==(s.length()-1)){
                        if(c%2==0){
                            state = 3;
                        } else {
                            state = -1;
                        };
                    } else {
                        state = 2;
                    }
                    break;
            }
        }

        return state==3;
    }

    public static void main(String[] args){
        String t0 = "Rossi654321";
        String t1 = "Bianchi123456";

        String t6 = "Bianchi654321";
        String t7 = "Rossi123456";

        System.out.println(t0 + "\nATTESO: \tAccettato \nRISULTATO:\t" + (scan(t0) ? "Accettato" : "Errore") + "\n");
        System.out.println(t1 + "\nATTESO: \tAccettato \nRISULTATO:\t" + (scan(t1) ? "Accettato" : "Errore") + "\n");
        System.out.println(t6 + "\nATTESO: \tErrore \nRISULTATO:\t" + (scan(t6) ? "Accettato" : "Errore") + "\n");
        System.out.println(t7 + "\nATTESO: \tErrore \nRISULTATO:\t" + (scan(t7) ? "Accettato" : "Errore") + "\n");
    }
}
