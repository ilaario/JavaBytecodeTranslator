public class IdentifDFA{

    public static boolean scan(String s){
        int state = 0;
        int i = 0;

        while(state>=0 && i<s.length()){
            final int ch = s.charAt(i++);

            switch(state){

                case 0:
                
                if(ch>= 'a' && ch <= 'z' || ch >= 'A' && ch <= 'Z'){
                    state = 1;
                } 
                else if(ch >= '0' && ch <= '9'){
                    state = -1;
                }
                else if(ch == '_'){
                    state = 2;
                }
                else state = -1;
                break;

                case 1:

                if(ch>= 'a' && ch <= 'z' || ch >= '0' && ch <= '9' || ch >= 'A' && ch <= 'Z' || ch == '_'){
                    state = 1;
                }
                else state = -1;
                break;

                case 2:

                if(ch == '_'){
                    state = 2;
                }
                else if(ch>= 'a' && ch <= 'z' || ch >= '0' && ch <= '9' || ch >= 'A' && ch <= 'Z' ){
                    state = 1;
                }
                else state = -1;
                break;
            }
        }
        return state == 1;
    }
    public static void main(String[] args){
        String r1 = "x";
        String r2 = "flag1";
        String r3 = "x2y2";
        String r4 = "x_1";
        String r5 = "lft_lab";
        String r6 = "_temp";
        String r7 = "x_1_y_2";
        String r8 = "x___";
        String r9 = "__5";
        String r10 = "5";
        String r11 = "221B";
        String r12 = "123";
        String r13 = "9_to_5";
        String r14 = "___";
        System.out.println(scan(r1) ? "OK" : "NOPE");
        System.out.println(scan(r2) ? "OK" : "NOPE");
        System.out.println(scan(r3) ? "OK" : "NOPE");
        System.out.println(scan(r4) ? "OK" : "NOPE");
        System.out.println(scan(r5) ? "OK" : "NOPE");
        System.out.println(scan(r6) ? "OK" : "NOPE");
        System.out.println(scan(r7) ? "OK" : "NOPE");
        System.out.println(scan(r8) ? "OK" : "NOPE");
        System.out.println(scan(r9) ? "OK" : "NOPE");
        System.out.println(scan(r10) ? "OK" : "NOPE");
        System.out.println(scan(r11) ? "OK" : "NOPE");
        System.out.println(scan(r12) ? "OK" : "NOPE");
        System.out.println(scan(r13) ? "OK" : "NOPE");
        System.out.println(scan(r14) ? "OK" : "NOPE");
    }
}