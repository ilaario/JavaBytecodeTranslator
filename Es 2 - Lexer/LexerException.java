public class LexerException extends Exception{
    public LexerException(final Throwable cause){
        Throwable rootCause = cause;
        while (rootCause.getCause() != null && rootCause.getCause() != rootCause){
            rootCause = rootCause.getCause();
        }
        System.err.print("LexerException:" + rootCause.getMessage() + " in method ");
        System.err.println(rootCause.getStackTrace()[0].getMethodName() + "();");
        for (int i = 0; i < rootCause.getStackTrace().length; i++){
            for (int j = 0; j <= i; j++){
                System.err.print("\t");
            }
            System.err.println("\t\t\t↳ "+ rootCause.getStackTrace()[i] + ";");
        }
        System.exit(0);
    }
}