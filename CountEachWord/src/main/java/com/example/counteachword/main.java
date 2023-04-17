package com.example.counteachword;
/**
 * Word occurrences
 *
 *
 * @author Nick Zambri
 * 4/17/2023
 */
import java.util.HashMap;
import java.util.Map;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.stream.Collectors;


public class main{

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader("C:\\Users\\nick\\eclipse-workspace\\CountEachWords\\bin\\TheProjectGutenbergeBook.txt"));
        Map<String, Integer> wordCounts = new HashMap<>();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            String[] words = line.split("[\\s.;,?:!()\"]+");
            for (String word : words) {
                word = word.trim();
                if (word.length() > 0) {
                    if (wordCounts.containsKey(word)) {
                        wordCounts.put(word, wordCounts.get(word) + 1);
                    } else {
                        wordCounts.put(word, 1);
                    }
                }
            }
        }
        Map<String, Integer> sortedWordCounts = wordCounts.entrySet().stream()
                .sorted(Collections.reverseOrder(Entry.comparingByValue()))
                .collect(Collectors.toMap(Entry::getKey, Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        System.out.printf("%-20s%15s\n", "Word", "Frequency");
        System.out.printf("%-20s%15s\n", "====", "=========");

        for (Map.Entry<String, Integer> entry : sortedWordCounts.entrySet()) {
            System.out.printf("%-20s%10s\n", entry.getKey(), entry.getValue());
        }
        bufferedReader.close();
    }
}