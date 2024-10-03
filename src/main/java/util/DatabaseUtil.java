package util;

public class DatabaseUtil {
    public static boolean ok(int rowsAffected) {
        return rowsAffected > 0;
    }
}
