import twittersentiment.WekaML;

public class TSATest {

	public static void main(String[] args) {
        WekaML weka = new WekaML();
        // predict sentiment with loaded model
        weka.predict(args[0], true);
	}

}
