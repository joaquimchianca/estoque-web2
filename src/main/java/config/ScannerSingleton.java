package config;

import java.util.Locale;
import java.util.Scanner;

public class ScannerSingleton {
    private static Scanner instance;

    private ScannerSingleton() {}

    public static Scanner getInstance() {
        if (instance == null) {
            instance = new Scanner(System.in);
            instance.useLocale(new Locale("pt", "BR"));
        }
        return instance;
    }
}