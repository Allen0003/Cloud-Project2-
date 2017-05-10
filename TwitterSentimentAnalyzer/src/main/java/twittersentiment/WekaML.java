/*
 * This file defines prediction process by using Weka.
 *      
 * It first reads vocabulary file used when we built a model.
 * Then it creates a sparse instance from the tweet text after normalizaition
 * and removing stop words and so on.
 * Finally, it predicts sentiment of the given tweet and return for the future use.
 * 
 *
 * Authers: Team 10
 *      Ziren Wang, zirenw@student.unimelb.edu.au, 720128
 *      Xiang Xiang, xxiang2@student.unimelb.edu.au, 720138
 *      Yongchul Kim, yongchulk@student.unimelb.edu.au, 750659
 *      Own Daghagheleh, odaghagheleh@student.unimelb.edu.au, 816273
 *      Wen Pin Wu, w.wu47@student.unimelb.edu.au, 871702
 */

package twittersentiment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import weka.classifiers.Classifier;
import weka.core.Attribute;
import weka.core.Instances;
import weka.core.SparseInstance;

public class WekaML {

    private final static String VOCA_FILE = "t500.arff.voca";
    private final static String STOPWRODS_FILE = "stopwords.txt";
    private final static String MODEL_FILE = "nbm_t500.model";

    // attributes : feature, index
    HashMap<String, Integer> attributes = new HashMap<String, Integer>();
    // feature vector
    ArrayList<Attribute> featureVector = null;
    // classifier
    // SerializedClassifier cls = null;
    Classifier cls = null;
    // stop words
    HashSet<String> stopwords = new HashSet<String>();

    public WekaML() {
        // read vocabulary from the resource
        readVocabulary();
        // read stop words from the resource
        readStopWords();
        // read nbm model from the resource
        loadModel();
    }

    public void readVocabulary() {
        try {
            InputStream is = Lemmatizer.class.getResourceAsStream(VOCA_FILE);
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);

            int index = 0;
            String line;
            while ((line = br.readLine()) != null) {
                attributes.put(line, index++);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadModel() {
        try {
            // save model, if not exist
            File targetFile = new File(MODEL_FILE);
            if (!targetFile.exists()) {
                InputStream is = getClass().getResourceAsStream(MODEL_FILE);

                OutputStream os = new FileOutputStream(targetFile);

                int read = 0;
                byte[] bytes = new byte[1024];

                while ((read = is.read(bytes)) != -1) {
                    os.write(bytes, 0, read);
                }
                os.close();
            }

            // load model
            cls = (Classifier) weka.core.SerializationHelper.read(MODEL_FILE);

            // for numeric attributes
            Attribute[] attr = new Attribute[attributes.size()];
            for (Map.Entry<String, Integer> entry : attributes.entrySet()) {
                String key = entry.getKey();
                Integer value = entry.getValue();

                attr[value] = new Attribute(key);
            }

            // for class attribute
            ArrayList<String> fvClassVal = new ArrayList<String>();
            fvClassVal.add("0");
            fvClassVal.add("2");
            fvClassVal.add("4");
            Attribute classAttribute = new Attribute("sentiment", fvClassVal);

            // feature vector
            featureVector = new ArrayList<Attribute>();
            for (Attribute at : attr) {
                featureVector.add(at);
            }
            featureVector.add(classAttribute);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String predict(String text, boolean print) {
        // arff
        Instances arff = new Instances("twitter_sentiment_analysis", featureVector, 1);
        arff.setClassIndex(arff.numAttributes() - 1);

        // instance for prediction
        SparseInstance sparseInstance = new SparseInstance(featureVector.size());
        for (int i = 0; i < attributes.size(); i++) {
            sparseInstance.setValue(i, 0);
        }

        text = normalizeTweet(text);

        HashMap<String, Integer> bow = get_BOW(text);

        for (Map.Entry<String, Integer> entry : bow.entrySet()) {
            String word = entry.getKey();
            Integer count = entry.getValue();
            if (attributes.containsKey(word)) {
                sparseInstance.setValue(attributes.get(word), count);
            }
        }

        if (sparseInstance.numAttributes() == 0) {
            return "2";
        }

        // do prediction
        arff.add(sparseInstance);

        double pred = 0;
        try {
            pred = cls.classifyInstance(arff.instance(0));
        } catch (Exception e) {
            e.printStackTrace();
        }

        String prediction = arff.instance(0).classAttribute().value((int) pred);
        if (print) {
            System.out.println("The predicted value of instance : " + prediction);
        }

        return prediction;
    }

    public void readStopWords() {
        try {
            InputStream is = Lemmatizer.class.getResourceAsStream(STOPWRODS_FILE);
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);

            String line;
            while ((line = br.readLine()) != null) {
                stopwords.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String normalizeTweet(String tweet) {
        tweet = processTweet(tweet);
        tweet = replaceTwoOrMore(tweet);

        String outStr = "";

        String[] tmpStr = tweet.split(" ");
        for (int i = 0; i < tmpStr.length; i++) {
            if (tmpStr[i].equals("")) {
                continue;
            }
            if (stopwords.contains(tmpStr[i])) {
                continue;
            }
            if (tmpStr[i].matches("[0-9]+")) {
                continue;
            }

            outStr += lemmatize(tmpStr[i]) + " ";
        }

        return outStr.trim();
    }

    // --------------------------------------------------------------------------------

    private HashMap<String, Integer> get_BOW(String string) {
        HashMap<String, Integer> bow = new HashMap<String, Integer>();

        String[] split = string.split(" ");
        for (String s : split) {
            bow.put(s, bow.getOrDefault(s, 0) + 1);
        }

        return bow;
    }

    private String lemmatize(String word) {
        String l = Lemmatizer.lemmatize(word, "VB");
        if (l.equals(word)) {
            l = Lemmatizer.lemmatize(word, "NN");
        }

        return l;
    }

    private String replaceTwoOrMore(String tweet) {
        tweet = tweet.replaceAll("(.)\\1{1,}", "$1$1");
        return tweet;
    }

    private String processTweet(String tweet) {
        // convert to lower case
        tweet = tweet.toLowerCase();

        // replace happy
        tweet = tweet.replaceAll("[;:',\\.][-=o]?[)>}d]", " HAPPY ");
        // replace unhappy
        tweet = tweet.replaceAll("[;:',\\.][-=o]?[(<{]", " UNHAPPY ");
        // replace #word with word
        tweet = tweet.replaceAll("#([^\\s]+)", "$1");
        // remove www.* or https?://*
        tweet = tweet.replaceAll("((www\\.[^\\s]+)|(https?://[^\\s]+))", "");
        // remove @username
        tweet = tweet.replaceAll("@[^\\s]+", "");
        // remove html tag: ex) &gt
        tweet = tweet.replaceAll("&[\\w]+;", "");
        // remove additional white spaces
        tweet = tweet.replaceAll("[\\s]+", " ");
        // replace special characters to white space
        tweet = tweet.replaceAll("[`~!@#$%^&*()\\-_=+[{]}\\|;:'\",<\\.>/?]", " ");
        // remove non-alphabetic
        tweet = tweet.replaceAll("[^A-Za-z0-9 ]", "");
        // trim
        tweet = tweet.trim();

        return tweet;
    }
}
