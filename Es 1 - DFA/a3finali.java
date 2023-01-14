public class
a3finali {
    public static boolean scan(String s){
        int state = 0;
        int i = 0;

        while(i < s.length() && state <=0){
            final char c = s.charAt(i++);

            switch (state){
                case 0:
                    if(c=='b'){
                        state = 1;
                    } else if(c=='a'){
                        state = 0;
                    } else {
                        state = -1;
                    }
                    break;

                case 1:
                    if(c=='b'){
                        state = 2;
                    } else if(c=='a'){
                        state = 0;
                    } else {
                        state = -1;
                    }
                    break;

                case 2:
                    if(c=='b'){
                        state = 3;
                    } else if(c=='a'){
                        state = 0;
                    } else {
                        state = -1;
                    }
                    break;

                case 3:
                    if(c=='b'){
                        state = 3;
                    } else if(c=='a'){
                        state = 0;
                    } else {
                        state = -1;
                    }
                    break;
            }
        }

        return state == 0;
    }

    public static void main(String[] args){
        String t0 = "abb";
        String t1 = "bbaba";
        String t2 = "baaaaaaaa";
        String t3 = "aaaaaaa";
        String t4 = "a";
        String t5 = "ba";
        String t8 = "bba";
        String t9 = "aa";
        String t10 = "bbbababab";

        String t6 = "abbbbbb";
        String t7 = "bbabbbbbbbb";
        String t11 = "b";

        System.out.println(t0 + "\nATTESO: \tAccettato \nRISULTATO:\t" + (scan(t0) ? "Accettato" : "Errore") + "\n");
        System.out.println(t1 + "\nATTESO: \tAccettato \nRISULTATO:\t" + (scan(t1) ? "Accettato" : "Errore") + "\n");
        System.out.println(t2 + "\nATTESO: \tAccettato \nRISULTATO:\t" + (scan(t2) ? "Accettato" : "Errore") + "\n");
        System.out.println(t3 + "\nATTESO: \tAccettato \nRISULTATO:\t" + (scan(t3) ? "Accettato" : "Errore") + "\n");
        System.out.println(t4 + "\nATTESO: \tAccettato \nRISULTATO:\t" + (scan(t4) ? "Accettato" : "Errore") + "\n");
        System.out.println(t5 + "\nATTESO: \tAccettato \nRISULTATO:\t" + (scan(t5) ? "Accettato" : "Errore") + "\n");
        System.out.println(t8 + "\nATTESO: \tAccettato \nRISULTATO:\t" + (scan(t8) ? "Accettato" : "Errore") + "\n");
        System.out.println(t9 + "\nATTESO: \tAccettato \nRISULTATO:\t" + (scan(t9) ? "Accettato" : "Errore") + "\n");
        System.out.println(t10 + "\nATTESO: \tAccettato \nRISULTATO:\t" + (scan(t10) ? "Accettato" : "Errore") + "\n");

        System.out.println(t6 + "\nATTESO: \tErrore \nRISULTATO:\t" + (scan(t6) ? "Accettato" : "Errore") + "\n");
        System.out.println(t7 + "\nATTESO: \tErrore \nRISULTATO:\t" + (scan(t7) ? "Accettato" : "Errore") + "\n");
        System.out.println(t11 + "\nATTESO: \tErrore \nRISULTATO:\t" + (scan(t11) ? "Accettato" : "Errore") + "\n");
    }

}
