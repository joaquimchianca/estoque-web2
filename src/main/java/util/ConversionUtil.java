package util;

import model.Papel;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ConversionUtil {
    public static double stringToDouble(String input) {
        return Double.parseDouble(input.replace(",", "."));
    }

    public static int stringToInt(String input) {
        return Integer.parseInt(input);
    }

    public static LocalDateTime stringToLocalDateTime(String input) {
        LocalDate localDate = LocalDate.parse(input);
        return localDate.atStartOfDay();
    }

    public static Papel stringToPapel (String input) {
        if (input.equalsIgnoreCase("administrador")) {
            return Papel.ADMINISTRADOR;
        } else if (input.equalsIgnoreCase("gerente")) {
            return Papel.GERENTE;
        } else if (input.equalsIgnoreCase("funcionario")) {
            return Papel.FUNCIONARIO;
        }
        return Papel.SEM_PAPEL;
    }
}
