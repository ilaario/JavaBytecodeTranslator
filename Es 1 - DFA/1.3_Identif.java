public class Identif {
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

    public static void main(String[] args){
        String t0 = "x";
        String t1 = "flag1";
        String t2 = "x2y2";
        String t3 = "x_1";
        String t4 = "lft_lab";
        String t5 = "_temp";
        String t6 = "x_1_y_2";
        String t7 = "x___";

        String t9 = "5";
        String t10 = "221B";
        String t11 = "9_to_5";
        String t12 = "___";

        System.out.println(t0 + "\nATTESO: \tAccettato \nRISULTATO:\t" + (scan(t0) ? "Accettato" : "Errore") + "\n");
        System.out.println(t1 + "\nATTESO: \tAccettato \nRISULTATO:\t" + (scan(t1) ? "Accettato" : "Errore") + "\n");
        System.out.println(t2 + "\nATTESO: \tAccettato \nRISULTATO:\t" + (scan(t2) ? "Accettato" : "Errore") + "\n");
        System.out.println(t3 + "\nATTESO: \tAccettato \nRISULTATO:\t" + (scan(t3) ? "Accettato" : "Errore") + "\n");
        System.out.println(t4 + "\nATTESO: \tAccettato \nRISULTATO:\t" + (scan(t4) ? "Accettato" : "Errore") + "\n");
        System.out.println(t5 + "\nATTESO: \tAccettato \nRISULTATO:\t" + (scan(t5) ? "Accettato" : "Errore") + "\n");
        System.out.println(t6 + "\nATTESO: \tAccettato \nRISULTATO:\t" + (scan(t6) ? "Accettato" : "Errore") + "\n");
        System.out.println(t7 + "\nATTESO: \tAccettato \nRISULTATO:\t" + (scan(t7) ? "Accettato" : "Errore") + "\n");

        System.out.println(t9 + "\nATTESO: \tErrore \nRISULTATO:\t" + (scan(t9) ? "Accettato" : "Errore") + "\n");
        System.out.println(t10 + "\nATTESO: \tErrore \nRISULTATO:\t" + (scan(t10) ? "Accettato" : "Errore") + "\n");
        System.out.println(t11 + "\nATTESO: \tErrore \nRISULTATO:\t" + (scan(t11) ? "Accettato" : "Errore") + "\n");
        System.out.println(t12 + "\nATTESO: \tErrore \nRISULTATO:\t" + (scan(t12) ? "Accettato" : "Errore") + "\n");
    }
}
