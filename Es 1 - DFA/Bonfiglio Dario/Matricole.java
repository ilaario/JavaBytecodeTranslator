public class Matricole {
    public static boolean scan(String s){
        int state = 0;
        int i = 0;

        while(i < s.length() && state >= 0){
            final int c = s.charAt(i++);

            switch (state){
                case 0:
                    if(c >= 48 && c <= 57){
                        int tmp = s.charAt(i);
                        if((tmp >= 97 && tmp <= 122) || ( tmp >= 65 && tmp <= 90)){
                            if(c%2==0){
                                state = 1;
                            } else {
                                state = 2;
                            }
                        } else {
                            state = 0;
                        }
                    } else {
                        state = -1;
                    }
                    break;

                case 1:
                    if((c >= 97 && c <= 107) || ( c >= 65 && c <= 76)){
                        state = 3;
                    } else {
                        state = -1;
                    }
                    break;

                case 2:
                    if((c >= 108 && c <= 122) || ( c >= 77 && c <= 90)){
                        state = 3;
                    } else {
                        state = -1;
                    }
                    break;
            }
        }
        return state == 3;
    }

    public static void main(String[] args){
        String t0 = "654321Rossi";
        String t1 = "123456Bianchi";

        String t6 = "654321Bianchi";
        String t7 = "123456Rossi";

        System.out.println(t0 + "\nATTESO: \tAccettato \nRISULTATO:\t" + (scan(t0) ? "Accettato" : "Errore") + "\n");
        System.out.println(t1 + "\nATTESO: \tAccettato \nRISULTATO:\t" + (scan(t1) ? "Accettato" : "Errore") + "\n");
        System.out.println(t6 + "\nATTESO: \tErrore \nRISULTATO:\t" + (scan(t6) ? "Accettato" : "Errore") + "\n");
        System.out.println(t7 + "\nATTESO: \tErrore \nRISULTATO:\t" + (scan(t7) ? "Accettato" : "Errore") + "\n");
    }
}
