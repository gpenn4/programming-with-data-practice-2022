
import java.util.Arrays;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.mllib.rdd.RDDFunctions;
import org.apache.spark.rdd.RDD;

import java.io.*;
import java.util.*;

public class CS1003P4 {

    /**
     * adapted from
     * https://stackoverflow.com/questions/43412523/how-to-implement-jaccard-coefficient-in-java
     * takes in 2 sets of strings and computes the jaccard similarity
     * 
     * @param left  the first set to be compared
     * @param right the second set to be compared
     * @return the jaccard similarity coefficient
     * @throws ClassCastException
     * @throws NullPointerException
     */
    public static double calculateJaccardSimilarity(Set<String> left, Set<String> right) {
        final int sa = left.size();
        final int sb = right.size();

        double emptyJaccardSimilarityCoefficient = 0;

        if ((sa - 1 | sb - 1) < 0)
            return (sa | sb) == 0 ? emptyJaccardSimilarityCoefficient : 0;

        if ((sa + 1 & sb + 1) < 0)
            return calculateJaccardSimilarity(left, right);

        final Set<?> smaller = sa <= sb ? left : right;
        final Set<?> larger = sa <= sb ? right : left;
        int intersection = 0;

        for (final Object element : smaller)
            try {
                if (larger.contains(element))
                    intersection++;
            } catch (final ClassCastException | NullPointerException e) {
            }
        final long sum = (sa + 1 > 0 ? sa : left.stream().count())
                + (sb + 1 > 0 ? sb : right.stream().count());

        return 1d / (sum - intersection) * intersection;
    }

    /**
     * method to create a hashset of character bigrams from a given string
     * 
     * @param text the string used to create the bigrams
     * @return the set of bigrams
     */
    public static HashSet<String> calculateBigrams(String text) {

        HashSet<String> bigrams = new HashSet<>();

        for (int i = 0; i < text.length() - 1; i++) {
            bigrams.add(text.substring(i, i + 2));
        }

        return bigrams;
    }

    /**
     * method to clean a String
     * 
     * @param text the String you want to be cleaned
     * @return the cleaned String
     */
    public static String cleanText(String text) {

        text = text.replaceAll("[^a-zA-Z0-9 ]", "");
        text = text.toLowerCase();
        String[] cleanText = text.split("[ \t\n\r]");
        text = String.join(" ", cleanText);

        return text;
    }

    public static void main(String[] args) {

        // initalizing and cleaning the search phrase from CL
        String searchPhrase = cleanText(args[1]);
        HashSet<String> searchBigrams = calculateBigrams(searchPhrase);

        // initializing similarity threshold from CL
        Double similarityThreshold = Double.parseDouble(args[2]);

        Integer num_words = searchPhrase.split("\\w+").length;

        String appName = "Term-Match Finder";
        String cluster = "local[*]";

        // configure Spark
        SparkConf conf = new SparkConf()
                .setAppName(appName)
                .setMaster(cluster);

        // initialize Spark context
        JavaSparkContext sc = new JavaSparkContext(conf);

        // initializing directory
        File directory = new File(args[0]);

        // filtering out only the files ending with .txt
        FilenameFilter filter = new FilenameFilter() {
            public boolean accept(File f, String name) {
                return name.endsWith("txt");
            }
        };

        File[] listOfFiles = directory.listFiles(filter);

        // hard coded tests for the calculateJaccardSimilarity method
        String testString = "hello twirl";
        String testString2 = "hello world";
        HashSet<String> test = calculateBigrams(testString);
        HashSet<String> test2 = calculateBigrams(testString2);

        if (calculateJaccardSimilarity(test, test2) >= similarityThreshold) {
            System.out.println(calculateJaccardSimilarity(test, test2));
        }

        // loop through txt files
        for (File file : listOfFiles) {
            if (file.isFile()) {

                // initializing javaRDD of the contents of the .txt files
                JavaRDD<String> lines = sc.textFile(file.getPath());

                // clean text
                JavaRDD<String> filteredWords = lines
                        .flatMap(x -> Arrays.asList(
                                x.replaceAll("[^a-zA-Z0-9]", " ")
                                        .toLowerCase()
                                        .split("[ \t\n\r]"))
                                .iterator())
                        .filter(x -> x.length() > 0);

                // implement sliding window
                RDD<Object> r = RDDFunctions.fromRDD(filteredWords.rdd(), filteredWords.classTag()).sliding(num_words);
                JavaRDD<Object> x = new JavaRDD<>(r, r.elementClassTag());

                // Map the object RDD to String RDD
                JavaRDD<String> result = x.map(new Function<Object, String>() {
                    @Override
                    public String call(Object arg0) throws Exception {
                        return Arrays.toString((Object[]) arg0);
                    }
                });

                // making a String of the cleaned text
                result.foreach(listString -> {
                    String str = "";
                    StringTokenizer tokenizer = new StringTokenizer(listString, "[], ");
                    while (tokenizer.hasMoreElements()) {
                        if (str.length() > 0) {
                            str = str + " ";
                        }
                        str = str + tokenizer.nextToken();
                    }

                    // initializing set of bigrams from the text
                    HashSet<String> textBigrams = calculateBigrams(str);

                    if (calculateJaccardSimilarity(textBigrams, searchBigrams) >= similarityThreshold) {
                        System.out.println(str);
                    }
                });
            }
        }
        sc.close();
    }
}
