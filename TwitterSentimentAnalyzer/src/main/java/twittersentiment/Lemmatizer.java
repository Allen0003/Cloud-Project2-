
/*
 * https://github.com/mikelewis0/easyccg/blob/master/src/uk/ac/ed/easyccg/lemmatizer/Lemmatizer.java
 *
 * This file defines lemmatization process.
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
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import edu.mit.jwi.item.POS;
import edu.mit.jwi.morph.SimpleStemmer;

/**
 * Wordnet-based lemmatizer. Morpha seems to work better, so I use that instead.
 */
public class Lemmatizer {

    private final static SimpleStemmer stemmer = new SimpleStemmer();
    private final static Map<POS, Map<String, String>> exceptions = new HashMap<POS, Map<String, String>>();
    private final static Map<String, POS> synsetTypeForPOS = new ConcurrentHashMap<String, POS>();

    static void loadExceptions(POS type, String name) {
        try {
            InputStream is = Lemmatizer.class.getResourceAsStream("/twittersentiment/" + name + ".exc");
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line;
            Map<String, String> map = new HashMap<String, String>();
            exceptions.put(type, map);
            while ((line = br.readLine()) != null) {
                String[] split = line.split(" ");
                map.put(new String(split[0]), new String(split[1]));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static {
        loadExceptions(POS.NOUN, "noun");
        loadExceptions(POS.ADJECTIVE, "adj");
        loadExceptions(POS.ADVERB, "adv");
        loadExceptions(POS.VERB, "verb");

        exceptions.get(POS.VERB).put("'m", "be");
        exceptions.get(POS.VERB).put("'re", "be");
        exceptions.get(POS.VERB).put("'ll", "be");
        exceptions.get(POS.VERB).put("'s", "be");
    }

    public static String lemmatize(String word, String pos) {
        if (pos.startsWith("NNP"))
            return word;
        // ZZZ we don't need to make it lower case
        // word = word.toLowerCase();
        POS type = getSynsetType(pos);
        if (type == null)
            return word;
        String exception = exceptions.get(type).get(word);
        if (exception != null)
            return exception;

        List<String> stems = stemmer.findStems(word, type);
        if (stems.size() == 0) {
            return word;
        } else {
            return stems.get(0);
        }
    }

    private static POS getSynsetType(String pos) {
        if (!synsetTypeForPOS.containsKey(pos)) {
            POS result = null;

            if (pos.startsWith("NN")) {
                result = POS.NOUN;
            } else if (pos.startsWith("VB")) {
                result = POS.VERB;
            } else if (pos.startsWith("RB")) {
                result = POS.ADVERB;
            } else if (pos.startsWith("JJ")) {
                result = POS.ADJECTIVE;
            }

            if (result != null) {
                synsetTypeForPOS.put(pos, result);
            }
        }

        return synsetTypeForPOS.get(pos);
    }
}