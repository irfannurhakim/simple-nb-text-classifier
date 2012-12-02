/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.nbtc.util.Tokenizer;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author irfannurhakim
 */
public class Learner {

    private double jumSeluruhDok = 0;
    private HashMap<String, Double> jumDokPerKelas = new HashMap<String, Double>();
    private double jumSeluruhToken = 0;
    private HashMap<String, Double> jumTokenPerKelas = new HashMap<String, Double>();
    private HashMap<String, Double> index = new HashMap<String, Double>();
    private HashMap<String, Double> posterior = new HashMap<String, Double>();
    private HashMap<String, String> stopWords = new HashMap<String, String>();
    private HashMap<String, Double> scoreClass = new HashMap<String, Double>();
    private TreeMap<Double, String> scoreClassSort = new TreeMap<Double, String>();

    public Learner() {
    }

    public void doLearning(String path, String TargetClass, String pathStopWord) {
        double counter = 0, counterToken = 0;
        this.stopWords = Tokenizer.dumpStopWords(pathStopWord);


        try {
            FileInputStream fstream = new FileInputStream(path);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            while ((strLine = br.readLine()) != null) {

                jumSeluruhDok++;
                jumDokPerKelas.put(TargetClass, counter);

                //tokenize
                strLine.toLowerCase();
                strLine.replaceAll("\\{Punct}", " ");
                String[] ret = strLine.split(" ");
                for (int i = 0; i < ret.length; i++) {
                    String key = stopWords.get(ret[i]);
                    if (key == null) {
                        Double freq = (Double) index.get(ret[i] + ":" + TargetClass);
                        if (freq == null) {
                            freq = new Double(1);
                        } else {
                            int value = freq.intValue();
                            freq = new Double(value + 1);
                        }
                        index.put(ret[i] + ":" + TargetClass, freq);
                        jumTokenPerKelas.put(TargetClass, counterToken++);
                        jumSeluruhToken++;
                    }
                }
                counter++;
            }
            in.close();
        } catch (Exception e) {//Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }
    }

    public void doClassifier(String document) {
        for (Map.Entry<String, Double> entry : jumDokPerKelas.entrySet()) {
            posterior.put(entry.getKey(), entry.getValue() / jumSeluruhDok);
            scoreClass.put(entry.getKey(), new Double(1));

            document.toLowerCase();
            document.replaceAll("\\{Punct}", " ");
            String[] ret = document.split(" ");
            Double score = Double.valueOf(1);
            for (int i = 0; i < ret.length; i++) {
                String key = stopWords.get(ret[i]);
                if (key == null) {
                    Double freq = (Double) index.get(ret[i] + ":" + entry.getKey());
                    if (freq == null) {
                        freq = new Double(1);
                    } else {
                        freq = freq + 1;
                    }
                    System.out.println(ret[i] + "-" + entry.getKey() + ":" + freq);
                    score = score * (freq / (jumTokenPerKelas.get(entry.getKey()) + jumSeluruhToken));
                }
            }
            //scoreClass.put(entry.getKey(), posterior.get(entry.getKey()) * score);
            scoreClassSort.put( posterior.get(entry.getKey()) * score, entry.getKey());
        }
    }

    public HashMap<String, Double> getJumDokPerKelas() {
        return jumDokPerKelas;
    }

    public void setJumDokPerKelas(HashMap<String, Double> jumDokPerKelas) {
        this.jumDokPerKelas = jumDokPerKelas;
    }

    public double getJumSeluruhDok() {
        return jumSeluruhDok;
    }

    public void setJumSeluruhDok(int jumSeluruhDok) {
        this.jumSeluruhDok = jumSeluruhDok;
    }

    public double getJumSeluruhToken() {
        return jumSeluruhToken;
    }

    public void setJumSeluruhToken(int jumSeluruhToken) {
        this.jumSeluruhToken = jumSeluruhToken;
    }

    public HashMap<String, Double> getJumTokenPerKelas() {
        return jumTokenPerKelas;
    }

    public void setJumTokenPerKelas(HashMap<String, Double> jumTokenPerKelas) {
        this.jumTokenPerKelas = jumTokenPerKelas;
    }

    public HashMap<String, Double> getIndex() {
        return index;
    }

    public void setIndex(HashMap<String, Double> index) {
        this.index = index;
    }

    public HashMap<String, Double> getPosterior() {
        return posterior;
    }

    public void setPosterior(HashMap<String, Double> posterior) {
        this.posterior = posterior;
    }

    public HashMap<String, String> getStopWords() {
        return stopWords;
    }

    public void setStopWords(HashMap<String, String> stopWords) {
        this.stopWords = stopWords;
    }

    public HashMap<String, Double> getScoreClass() {
        return scoreClass;
    }

    public void setScoreClass(HashMap<String, Double> scoreClass) {
        this.scoreClass = scoreClass;
    }

    public TreeMap<Double, String> getScoreClassSort() {
        return scoreClassSort;
    }

    public void setScoreClassSort(TreeMap<Double, String> scoreClassSort) {
        this.scoreClassSort = scoreClassSort;
    }
}
