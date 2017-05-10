#
# This file defines functions for normalizing and generating vocabulary in
# a training dataset.
#
#
# Authers: Team 10
#      Ziren Wang, zirenw@student.unimelb.edu.au, 720128
#      Xiang Xiang, xxiang2@student.unimelb.edu.au, 720138
#      Yongchul Kim, yongchulk@student.unimelb.edu.au, 750659
#      Own Daghagheleh, odaghagheleh@student.unimelb.edu.au, 816273
#      Wen Pin Wu, w.wu47@student.unimelb.edu.au, 871702
#

import csv, re, sys
from nltk.stem.wordnet import WordNetLemmatizer

#-------------------------------------------------------------------------------

lemmatizer = WordNetLemmatizer()

def lemmatize(word):
    lemma = lemmatizer.lemmatize(word,'v')
    if lemma == word:
        lemma = lemmatizer.lemmatize(word,'n')
    return lemma

def replace_two_or_more(tweet):
    # look for 2 or more repetitions of character
    pattern = re.compile(r"(.)\1{1,}", re.DOTALL)
    return pattern.sub(r"\1\1", tweet)

def process_tweet(tweet):
    # convert to lower case
    tweet = tweet.lower()

    # replace happy
    tweet = re.sub('[;:\',.][-=o]?[)>}d]', ' HAPPY ', tweet)
    # replace unhappy
    tweet = re.sub('[;:\',.][-=o]?[(<{]', ' UNHAPPY ', tweet)
    # replace #word with word
    tweet = re.sub(r'#([^\s]+)', r'\1', tweet)
    # remove www.* or https?://*
    tweet = re.sub('((www\.[^\s]+)|(https?://[^\s]+))', '', tweet)
    # remove @username
    tweet = re.sub('@[^\s]+', '', tweet)
    # remove html tag: ex) &gt
    tweet = re.sub('&[\w]+;', '', tweet)
    # remove additional white spaces
    tweet = re.sub('[\s]+', ' ', tweet)
    # replace special characters to white space
    #tweet = re.sub('[`~!@#$%^&*()\-_=+[{]}\|;:\'",<.>/?]', ' ', tweet)
    tweet = re.sub('[`~!@#$%^&*()\-_=+\[{\]}\\\\|;:\'",<.>/?]', ' ', tweet)
    # remove non-alphabetic
    tweet = re.sub('[^A-Za-z0-9 ]', '', tweet)
    # trim
    tweet = tweet.strip('[\s]+')

    return tweet

def get_stopwords(txt_filename):
    #read the stopwords
    stopwords = []
    fp = open(txt_filename, 'r')
    line = fp.readline()
    while line:
        word = line.strip()
        stopwords.append(word)
        line = fp.readline()
    fp.close()
    return stopwords

def normalize_tweet(tweet, stopwords):
    p_tweet = process_tweet(tweet)
    r_tweet = replace_two_or_more(p_tweet)
    n_tweet = ''
    for word in r_tweet.split(' '):
        if word == '':
            continue
        if word.isdigit() == True:
            continue
        # remove stop words --> it takes too long
        if word in stopwords:
            continue
        word = lemmatize(word)
        n_tweet += word + ' '
    return n_tweet.strip(' ')

#------------------------------------------------------------------------------

def main(csv_filename, voca_filename, stopwords_filename):
    stopwords = get_stopwords(stopwords_filename)
    vocabulary = {}

    # read raw file then construct vocabulary
    i = 0
    with open(csv_filename) as csvfile, \
         open(csv_filename.replace('.csv', '.txt'), 'wb') as outfile:
        reader = csv.reader(csvfile)
        for row in reader:
            n_tweet = normalize_tweet(row[5], stopwords)

            if i % 1000 == 0:
                print ("[%10d] = -->[%s]<--") % (i, n_tweet)

            if n_tweet != '':
                outfile.write(row[0] + '\t' + n_tweet + '\n')

            i += 1
            for word in n_tweet.split(' '):
                vocabulary[word] = vocabulary.get(word, 0) + 1

    # write vocabulary to file for future use
    print len(vocabulary)

    with open(voca_filename, 'wb') as vocafile:
        for k, v in sorted(vocabulary.items(), key=lambda x:x[1]):
            vocafile.write(('%d\t%s\n') % (v, k))

#-------------------------------------------------------------------------------

if __name__ == "__main__":
    csv_filename = sys.argv[1]
    voca_filename = sys.argv[2]
    stopwords_filename = sys.argv[3]

    main(csv_filename, voca_filename, stopwords_filename)
    #print process_tweet(csv_filename)
