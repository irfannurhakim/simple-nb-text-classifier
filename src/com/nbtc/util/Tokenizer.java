/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nbtc.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author irfannurhakim
 */
public class Tokenizer {

    public static HashMap<String, String> dumpStopWords(String pathToFile) {
        String strings = "";
        HashMap<String, String> stopWords = new HashMap<String, String>();

        try {
            FileInputStream fstream = new FileInputStream(pathToFile);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            while ((strLine = br.readLine()) != null) {
                strings += strLine;
            }
            in.close();
        } catch (Exception e) {//Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }
        String[] arrayString = strings.split("\\s");
        for (int i = 0; i < arrayString.length; i++) {
            if(arrayString[i].length() > 0){
                stopWords.put(arrayString[i], "ok");
            }
        }
        
        return stopWords;
    }

    public static ArrayList<String> doToken(String pathToFile, String pathStopWord) {
        String strings = "";
        HashMap<String, String> stopWords = dumpStopWords(pathStopWord);

        try {
            FileInputStream fstream = new FileInputStream(pathToFile);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            while ((strLine = br.readLine()) != null) {
                strings += strLine;
            }
            in.close();
        } catch (Exception e) {//Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }

        strings.toLowerCase();
        strings.replaceAll("\\p{Punct}", " ");
        String[] ret = strings.split("\\s");
        ArrayList<String> realRet = new ArrayList<String>();
        int idx = 0;
        for (int i = 0; i < ret.length; i++) {
            String key = stopWords.get(ret[i]);
            if(key == null){
                realRet.add(idx, ret[i]);
                idx++;
            }
        }
        return realRet;
    }
}