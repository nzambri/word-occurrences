package com.example.counteachword;
/*
  Word occurrences


  @author Nick Zambri
 * 4/17/2023
 */

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serial;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

    public class gui extends JFrame {
        @Serial
        private static final long serialVersionUID = 1L;
        private final JTextArea textArea;
        private Map<String, Integer> wordCounts;

        public gui() {
            super("Word Occurrences");

            textArea = new JTextArea();
            JButton countButton = new JButton("Count Words");

            JPanel buttonPanel = new JPanel();
            buttonPanel.add(countButton);

            JScrollPane scrollPane = new JScrollPane(textArea);

            getContentPane().add(scrollPane, BorderLayout.CENTER);
            getContentPane().add(buttonPanel, BorderLayout.SOUTH);

            countButton.addActionListener(e -> {
                try {
                    countWords();
                    displayResults();
                } catch (IOException ex) {
                    textArea.setText("Error reading file.");
                }
            });

            setPreferredSize(new Dimension(600, 400));
            pack();
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setVisible(true);
        }

        private void countWords() throws IOException {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("C:\\Users\\nick\\eclipse-workspace\\CountEachWords\\bin\\TheProjectGutenbergeBook.txt"));
            wordCounts = new HashMap<>();
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
            bufferedReader.close();
        }

        private void displayResults() {
            Map<String, Integer> sortedWordCounts = wordCounts.entrySet().stream()
                    .sorted(Collections.reverseOrder(Entry.comparingByValue()))
                    .collect(Collectors.toMap(Entry::getKey, Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
            textArea.setText(String.format("%-20s%15s\n", "Word", "Frequency"));
            textArea.append(String.format("%-20s%15s\n", "====", "========="));

            for (Map.Entry<String, Integer> entry : sortedWordCounts.entrySet()) {
                textArea.append(String.format("%-20s%10s\n", entry.getKey(), entry.getValue()));
            }
        }

        public static void main(String[] args) {
            new gui();
        }
    }
