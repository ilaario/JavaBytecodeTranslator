public class NomeVariante {
    public static boolean scan(String s){
        int state = 0;
        int i = 0;
        boolean altraLettera = true;

        while(i < s.length() && state >= 0){
            final char c = s.charAt(i++);

            switch (state){
                case 0:
                    if(c == 'D'){
                        state = 1;
                    } else if(c >= 33 && c <= 126){
                        state = 6;
                    } else {
                        state = -1;
                    }
                    break;

                case 1:
                    if(c == 'a'){
                        state = 2;
                    } else if(c >= 33 && c <= 126){
                        state = 7;
                    } else {
                        state = -1;
                    }
                    break;

                case 2:
                    if(c == 'r'){
                        state = 3;
                    } else if(c >= 33 && c <= 126){
                        state = 8;
                    } else {
                        state = -1;
                    }
                    break;

                case 3:
                    if(c == 'i'){
                        state = 4;
                    } else if(c >= 33 && c <= 126){
                        state = 9;
                    } else {
                        state = -1;
                    }
                    break;

                case 4:
                    if(!altraLettera){
                        state = -1;
                    } else {
                        if(c >= 33 && c <= 126){
                            state = 5;
                            altraLettera = false;
                        } else {
                            state = -1;
                        }
                    }
                    break;

                case 6:
                    if(c == 'a'){
                        state = 2;
                        altraLettera = false;
                    } else {
                        state = -1;
                    }
                    break;

                case 7:
                    if(!altraLettera){
                        state = -1;
                    } else {
                        if(c == 'r'){
                            state = 3;
                            altraLettera = false;
                        } else {
                            state = -1;
                        }
                    }
                    break;

                case 8:
                    if(!altraLettera){
                        state = -1;
                    } else {
                        if(c == 'i'){
                            state = 4;
                            altraLettera = false;
                        } else {
                            state = -1;
                        }
                    }
                    break;

                case 9:
                    if(!altraLettera){
                        state = -1;
                    } else {
                        if(c == 'o'){
                            state = 5;
                            altraLettera = false;
                        } else {
                            state = -1;
                        }
                    }
                    break;

            }
        }
        return state == 5;
    }

    public static void main(String[] args){
        String t0 = "Dario";
        String t1 = "Mario";
        String t2 = "Dadio";
        String t3 = "D4rio";
        String t4 = "Dari0";
        String t5 = "Dar*o";

        String t6 = "Luca";
        String t7 = "D*r*o";

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
