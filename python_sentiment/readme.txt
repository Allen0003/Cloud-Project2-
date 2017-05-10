#
# normalize twitter training dataset and generate vocabulary
#

python gen_voca.py training.1600000.processed.noemoticon.csv voca.txt stopwords.txt

--------------------------------------------------------------------------------

#
# feature selection
#

# generate arff for feature selection
# => tokens used over 1000 times in the corpus

python gen_arff.py voca training.1600000.processed.noemoticon.txt 1000.arff voca.txt 1000

# feature selection by WEKA

screen -S do1000 java -cp ~/ccc/weka-3-8-1/weka.jar weka.attributeSelection.InfoGainAttributeEval -s "weka.attributeSelection.Ranker -T -1.7976931348623157E308 -N -1" -i 1000.arff > fs_1000.txt


--------------------------------------------------------------------------------

#
# training
#

# generate arff for training data : value greater than 0.0

python gen_arff.py target training.1600000.processed.noemoticon.txt t1000.arff target_1000.txt 0.0

# training classifier by WEKA -> NaiveByesMultinomial

java -cp ~/ccc/weka-3-8-1/weka.jar weka.classifiers.bayes.NaiveBayesMultinomial -d nbm_t1000.model -t t1000.arff

# generate arff for training data : top 500 token

python gen_arff.py target training.1600000.processed.noemoticon.txt t500.arff target_1000.txt 0.00007411

# training classifier by WEKA -> NaiveByesMultinomial

java -cp ~/ccc/weka-3-8-1/weka.jar weka.classifiers.bayes.NaiveBayesMultinomial -d nbm_t500.model -t t500.arff

-------------------------------------------------------------------------------

#
# test new instances
#

java -cp ~/ccc/weka-3-8-1/weka.jar weka.classifiers.bayes.NaiveBayesMultinomial -l nbm_t500.model -T test_t500.arff -p 0

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
sudo pip install -U numpy

#
# install java
#

sudo apt-get update
sudo apt-get install default-jre
sudo apt-get install default-jdk

#
# install weka
#

wget http://prdownloads.sourceforge.net/weka/weka-3-8-1.zip
unzip weka-3-8-1.zip

UPLOAD wekafiles.tgz
cd <- move to home directory
tar cvzf wekafiles.tgz

#
# install training data
#

wget http://cs.stanford.edu/people/alecmgo/trainingandtestdata.zip
unzip trainingandtestdata.zip
