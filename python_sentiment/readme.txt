# all test were conducted in a "NeCTAR Ubuntu 16.04 LTS (Xenial) amd64".

-------------------------------------------------------------------------------

#
# install unzip
#

sudo apt install unzip

#
# install nltk, numpy
#

sudo apt-get update && sudo apt-get -y upgrade
echo y | sudo apt-get install python-pip
pip install --upgrade pip

sudo pip install -U nltk
#sudo pip install -U numpy

# download wordnet for lemmatizer
python wordnet_download.py

#
# install java
#

sudo apt-get update
echo y | sudo apt-get install default-jdk

#
# install weka
#

wget http://prdownloads.sourceforge.net/weka/weka-3-8-1.zip
unzip weka-3-8-1.zip
export WEKAHOME=./weka-3-8-1

# download default package
java -cp $WEKAHOME/weka.jar java weka.core.WekaPackageManager

-------------------------------------------------------------------------------

#
# download training data and unzip
#

wget http://cs.stanford.edu/people/alecmgo/trainingandtestdata.zip
unzip trainingandtestdata.zip

#
# download stop words
#

wget https://github.com/ravikiranj/twitter-sentiment-analyzer/blob/master/data/feature_list/stopwords.txt

--------------------------------------------------------------------------------

#
# normalize twitter training dataset and generate vocabulary
#

python gen_voca.py training.1600000.processed.noemoticon.csv voca.txt stopwords.txt

--------------------------------------------------------------------------------

#
# feature selection
#

# generate arff for feature selection
# ----> tokens used over 1000 times in the corpus

python gen_arff.py voca training.1600000.processed.noemoticon.txt 1000.arff voca.txt 1000

# feature selection by WEKA

java -cp $WEKAHOME/weka.jar weka.attributeSelection.InfoGainAttributeEval -s "weka.attributeSelection.Ranker -T -1.7976931348623157E308 -N -1" -i 1000.arff > fs_1000.txt

# create target_1000.txt by removing unnecessary parts in fs_1000.txt



=== Attribute Selection on all input data ===

Search Method:
        Attribute ranking.

Attribute Evaluator (supervised, Class (nominal): 1504 sentiment):
        Information Gain Ranking Filter

Ranked attributes:

------->[ ONLY NEED THIS PART ]<----------

Selected attributes: 655,1275

# farget_1000.txt file

0.01601858      655 i
0.0157289      1275 thank
0.01289662     1492 you
0.01279918      840 miss
0.01223423     1086 sad
0.01168523     1254 t
0.00787122      784 love
0.00741566      903 not

    ....

0              1181 son
0               201 button
0              1197 sport
0              1190 south
0              1187 sort
0              1189 soup
0              1240 super

--------------------------------------------------------------------------------

#
# training
#

# generate arff for training data : top 500 token

python gen_arff.py target training.1600000.processed.noemoticon.txt t500.arff target_1000.txt 0.00010873 <-- check this value in target_1000.txt

# training classifier by WEKA ----> NaiveByesMultinomial

java -cp $WEKAHOME/weka.jar weka.classifiers.bayes.NaiveBayesMultinomial -d nbm_t500.model -t t500.arff

# training classifier by WEKA ----> J48

java -cp $WEKAHOME/weka.jar weka.classifiers.trees.J48 -d j48_t500.model -t t500.arff

-------------------------------------------------------------------------------

#
# classify new instances
#

# use t500.arff, remove all instances then add some samples
#
# @DATA
# {0 1, 2 1, 39 1, 67 1, 109 1, 185 1, 248 1, 424 1, 493 1, 500 ?}
# {46 1, 69 1, 309 1, 411 1, 455 1, 492 2, 500 ?}

java -cp $WEKAHOME/weka.jar weka.classifiers.bayes.NaiveBayesMultinomial -l nbm_t500.model -T test_t500.arff -p 0
