public class VirgolaMobile {
    public static boolean scan(String s){
        int state = 0;
        int i = 0;

        boolean checkE = true;
        boolean checkP = true;

        while (i < s.length() && state >= 0){
            final char c = s.charAt(i++);

            switch (state){
                case 0:
                    if (c >= '0' && c <= '9'){
                        state = 1;
                    } else if (c == '.'){
                        state = 2;
                    } else if (c == '+' || c == '-'){
                        state = 3;
                    } else {
                        state = -1;
                    }
                    break;

                case 1:
                    if(c >= '0' && c <= '9'){
                        state = 1;
                    } else if (c == '.'){
                        state = 2;
                    } else if (c == 'e'){
                        state = 4;
                    } else if (c == '+' || c == '-'){
                        state = 3;
                    } else {
                        state = -1;
                    }
                    break;

                case 2:
                    if(checkP){
                        if(c >= '0' && c <= '9'){
                            state = 1;
                            checkP = false;
                        } else {
                            state = -1;
                        }
                    } else {
                        state = -1;
                    }
                    break;

                case 3:
                    if(c == '.'){
                        state = 2;
                    } else if(c >= '0' && c <= '9'){
                        state = 1;
                    } else {
                        state = -1;
                    }
                    break;

                case 4:
                    if(checkE){
                        if(c >= '0' && c <= '9'){
                            state = 1;
                            checkE = false;
                        } else if(c == '+' || c == '-'){
                            state = 3;
                            checkE = false;
                        } else {
                            state = -1;
                        }
                    } else {
                        state = -1;
                    }
                    break;
            }
        }
        return state == 1;
    }

    public static void main(String[] args){
        String t0 = "123";
        String t1 = "123.5";
        String t2 = ".567";
        String t3 = "+7.5";
        String t4 = "-.7";
        String t5 = "67e10";
        String t8 = "1e-2";
        String t9 = "-.7e2";
        String t10 = "1e2.3";

        String t6 = ".";
        String t7 = "e3";
        String t11 = "123.";
        String t12 = "+e6";
        String t13 = "1.2.3";
        String t14 = "4e5e6";
        String t15 = "++3";


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
        System.out.println(t12 + "\nATTESO: \tErrore \nRISULTATO:\t" + (scan(t12) ? "Accettato" : "Errore") + "\n");
        System.out.println(t13 + "\nATTESO: \tErrore \nRISULTATO:\t" + (scan(t13) ? "Accettato" : "Errore") + "\n");
        System.out.println(t14 + "\nATTESO: \tErrore \nRISULTATO:\t" + (scan(t14) ? "Accettato" : "Errore") + "\n");
        System.out.println(t15 + "\nATTESO: \tErrore \nRISULTATO:\t" + (scan(t15) ? "Accettato" : "Errore") + "\n");
    }
}
