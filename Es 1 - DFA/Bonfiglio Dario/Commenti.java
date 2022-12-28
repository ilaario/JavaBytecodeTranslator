public class Commenti {
    public static boolean scan(String s){
        int stato = 0;
        int i = 0;

        while(i < s.length() && stato >= 0){
            final char c = s.charAt(i++);

            switch (stato){
                case 0:
                    if(c == '/'){
                        stato = 1;
                    } else {
                        stato = -1;
                    }
                    break;

                case 1:
                    if(c == '*'){
                        stato = 2;
                    } else {
                        stato = -1;
                    }
                    break;

                case 2:
                    if(c == '/' || c == 'a'){
                        stato = 2;
                    } else {
                        stato = 3;
                    }
                    break;

                case 3:
                    if(c == '/'){
                        stato = 4;
                    } else if(c == 'a'){
                        stato = 2;
                    } else {
                        stato = 3;
                    }
                    break;

                case 4:
                    if(c == '/' || c == '*' || c == 'a'){
                        stato = -1;
                    } else {
                        stato = 5;
                    }
                    break;

            }
        }

        return stato == 4;
    }

    public static void main(String[] args){
        String t0 = "/****/";
        String t1 = "/*a*a*/";
        String t2 = "/*a/**/";
        String t3 = "/**a///a**/";
        String t4 = "/**/";
        String t5 = "/*/*/";

        String t6 = "/*/";
        String t7 = "/**/***/";

        System.out.println(t0 + "\nATTESO: \tAccettato \nRISULTATO:\t" + (scan(t0) ? "Accettato" : "Errore") + "\n");
        System.out.println(t1 + "\nATTESO: \tAccettato \nRISULTATO:\t" + (scan(t1) ? "Accettato" : "Errore") + "\n");
        System.out.println(t2 + "\nATTESO: \tAccettato \nRISULTATO:\t" + (scan(t2) ? "Accettato" : "Errore") + "\n");
        System.out.println(t3 + "\nATTESO: \tAccettato \nRISULTATO:\t" + (scan(t3) ? "Accettato" : "Errore") + "\n");
        System.out.println(t4 + "\nATTESO: \tAccettato \nRISULTATO:\t" + (scan(t4) ? "Accettato" : "Errore") + "\n");
        System.out.println(t5 + "\nATTESO: \tAccettato \nRISULTATO:\t" + (scan(t5) ? "Accettato" : "Errore") + "\n");

        System.out.println(t6 + "\nATTESO: \tErrore \nRISULTATO:\t" + (scan(t6) ? "Accettato" : "Errore") + "\n");
        System.out.println(t7 + "\nATTESO: \tErrore \nRISULTATO:\t" + (scan(t7) ? "Accettato" : "Errore") + "\n");

    }
}
