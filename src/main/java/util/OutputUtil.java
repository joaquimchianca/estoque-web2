package util;

public class OutputUtil {
    public static final String SEPARADOR = "------------------------------------";

    public static void limpaTela() {
        System.out.println(new String(new char[50]).replace("\0", "\r\n"));
    }
}
