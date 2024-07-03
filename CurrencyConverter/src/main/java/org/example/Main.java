package org.example;

import java.util.Scanner;

/**
 * Main Klasse, um das Programm CurrencyConverter auzuführen
 */
public class Main {
    /**
     * Main Methode, in der das Programm ausgeführt wird(Logik befindet sich in der CurrencyConverter Klasse)
     * @param args
     */
    public static void main (String args[]){

        CurrencyConverter converter = new CurrencyConverter();

        converter.loadCurrencies("Currencies.txt");

        Scanner scanner = new Scanner(System.in);
        while (true) {
            converter.printMenu();
            String input = scanner.nextLine();

            switch (input) {
                case "0":
                    converter.setFromCurrency(converter.selectCurrency(scanner, "die Ausgangswährung"));
                    break;
                case "1":
                    converter.setToCurrency(converter.selectCurrency(scanner, "die Zielwährung"));
                    break;
                case "2":
                    System.out.println("Geben Sie den Betrag ein:");
                    converter.setValueFromCurrency(converter.enterAmount(scanner));
                    break;
                case "3":
                    scanner.close();
                    System.out.println("Programm beendet.");
                    return;
                default:
                    System.out.println("Ungültige Eingabe. Bitte versuchen Sie es erneut.");
            }

            if (converter.fromCurrency != null && converter.toCurrency != null && converter.valueFromCurrency != null) {
                converter.convertCurrency();
            }
        }
    }
}
