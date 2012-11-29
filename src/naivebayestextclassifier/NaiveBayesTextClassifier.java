/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package naivebayestextclassifier;

import controller.Learner;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author irfannurhakim
 */
public class NaiveBayesTextClassifier {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        String path = "/Users/hadipratama/Documents/Kuliah/Machine_Learning/twitter/nonopini.txt";
        String path_pos = "/Users/hadipratama/Documents/Kuliah/Machine_Learning/twitter/positif.txt";
        String path_neg = "/Users/hadipratama/Documents/Kuliah/Machine_Learning/twitter/negatif.txt";

        String stopWord = "/Users/hadipratama/Documents/Kuliah/Machine_Learning/stopword.txt";
        String[] TargetClass = {"pos", "neg", "not"};
        //ArrayList<String> docs = Tokenizer.doToken("/Users/hadipratama/Documents/Kuliah/Machine_Learning/twitter/nonopini_939.txt","/Users/hadipratama/Documents/Kuliah/Machine_Learning/stopword.txt");

        Learner l = new Learner();
        l.doLearning(path, TargetClass[2], stopWord);
        l.doLearning(path_pos, TargetClass[0], stopWord);
        l.doLearning(path_neg, TargetClass[1], stopWord);

        HashMap<String, Double> r = l.getJumDokPerKelas();
        for (Map.Entry<String, Double> entry : r.entrySet()) {
            String string = entry.getKey();
            Double integer = entry.getValue();
        }

        l.doClassifier("daku bisa buka homepagenya indosat pakai bb telkomsel saat xl bisa kesitu perasaan");
        HashMap<String, Double> s = l.getScoreClass();
        for (Map.Entry<String, Double> entry : s.entrySet()) {
            String string = entry.getKey();
            Double double1 = entry.getValue();
            System.out.println(string + ":" + double1);
        }

    }
}
