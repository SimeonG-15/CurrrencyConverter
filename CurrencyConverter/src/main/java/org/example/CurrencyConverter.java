package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Klasse CurrencyConverter.
 */
public class CurrencyConverter {

    private Map<String, Double> currencyMap = new HashMap<>();
    public String fromCurrency;
    public String toCurrency;
    public Double valueFromCurrency;
    public boolean currencyConverted;
    public Double valueToCurrency;

    /**
     * Leerer Konstruktor, da alle Eigenschaften im laufe des Programmes vom User befüllt werden.
     */
    public CurrencyConverter() {

    }

    /**
     * Setter für fromCurrency.
     * @param fromCurrency
     */
    public void setFromCurrency(String fromCurrency){
        this.fromCurrency = fromCurrency;
    }

    /**
     * Setter für toCurrency.
     * @param toCurrency
     */
    public void setToCurrency(String toCurrency){
        this.toCurrency = toCurrency;
    }

    /**
     * Setter fürvalueFromCurrency.
     * @param valueFromCurrency
     */
    public void setValueFromCurrency(Double valueFromCurrency){
        this.valueFromCurrency = valueFromCurrency;
    }

    /**
     * Hier wird die .txt Datei ausgelesem von dem Pfad, der der Methode mitgegeben wurde.
     * @param fileName
     */
    public void loadCurrencies(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    String currency = parts[0];
                    double sdr = Double.parseDouble(parts[1]);
                    currencyMap.put(currency, sdr);
                }
            }
        } catch (IOException e) {
            System.err.println("Fehler beim Laden der Währungsdaten: " + e.getMessage());
        }
    }

    /**
     * Hier wird das "Hauptmenü" des Programmes in der Konsole ausgegeben.
     */
    public void printMenu() {
        System.out.println("Wählen Sie eine Aktion:");
        System.out.println("0. Ausgangswährung auswählen");
        System.out.println("1. Zielwährung auswählen");
        System.out.println("2. Betrag eingeben");
        System.out.println("3. Beenden");
        System.out.println("Aktuelle Einstellungen:");
        System.out.println("Ausgangswährung: " + (fromCurrency != null ? fromCurrency : "Nicht festgelegt"));
        System.out.println("Zielwährung: " + (toCurrency != null ? toCurrency : "Nicht festgelegt"));
        System.out.println("Betrag: " + (valueFromCurrency != null ? valueFromCurrency : "Nicht festgelegt"));
        if(currencyConverted)
            System.out.println(valueFromCurrency + " " + fromCurrency + " entspricht " + Math.round(valueToCurrency*100.0)/100.0 + " " + toCurrency);
    }

    /**
     * Hier wird zuerst der User nach der Währung gefragt, die ausgegeben werden soll.
     * Danach wird in der Hashmap nach passenden Werten gesucht, diese dann in der Konsole ausgegeben und
     * der User kann das die entsprechende Währung auswählen.
     * @param scanner
     * @param currencyType
     * @return Ausgewählte Währung
     */
    public String selectCurrency(Scanner scanner, String currencyType) {
        System.out.println("Geben Sie " + currencyType + " ein:");
        while (true) {
            String input = scanner.nextLine().toLowerCase();
            List<String> matchedCurrencies = currencyMap.keySet().stream()
                    .filter(currency -> currency.toLowerCase().contains(input))
                    .collect(Collectors.toList());

            if (matchedCurrencies.isEmpty()) {
                System.out.println("Keine passenden Währungen gefunden. Bitte versuchen Sie es erneut:");
            } else {
                System.out.println("Gefundene Währungen:");
                for (int i = 0; i < matchedCurrencies.size(); i++) {
                    String currency = matchedCurrencies.get(i);
                    System.out.println(i + ": " + currency);
                }

                System.out.println("Bitte geben Sie die Zahl der gewünschten Währung oder das genaue Kürzel ein:");
                String selectedInput = scanner.nextLine().toUpperCase();
                try {
                    int selectedIndex = Integer.parseInt(selectedInput);
                    if (selectedIndex >= 0 && selectedIndex < matchedCurrencies.size()) {
                        return matchedCurrencies.get(selectedIndex);
                    } else {
                        System.out.println("Ungültige Zahl. Bitte versuchen Sie es erneut.");
                    }
                } catch (NumberFormatException e) {
                    if (currencyMap.containsKey(selectedInput)) {
                        return selectedInput;
                    } else {
                        System.out.println("Ungültiges Währungskürzel. Bitte versuchen Sie es erneut.");
                    }
                }
            }
        }
    }

    /**
     * Hier wird der Betrag der vom User eingegeben wurde angefragt und wieder zurück gegeben.
     * @param scanner
     * @return Eingegebener Betrag
     */
    public Double enterAmount(Scanner scanner) {
        while (true) {
            try {
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Ungültige Eingabe. Bitte geben Sie eine Zahl ein:");
            }
        }
    }
    /**
     * Hier wird, wenn sowohl fromCurrency, toCurrency und valueToCurrency ausgewählt wurde die Betrag in die neue Währung umgerechnet.
     */
    public void convertCurrency() {
        currencyConverted = true;
        double fromRate = currencyMap.get(fromCurrency);
        double toRate = currencyMap.get(toCurrency);
        valueToCurrency = valueFromCurrency * fromRate / toRate;
    }
}