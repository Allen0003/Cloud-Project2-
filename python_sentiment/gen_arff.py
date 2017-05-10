#
# This file defines functions for generating ARFF file for WEKA.
#
#
# Authers: Team 10
#      Ziren Wang, zirenw@student.unimelb.edu.au, 720128
#      Xiang Xiang, xxiang2@student.unimelb.edu.au, 720138
#      Yongchul Kim, yongchulk@student.unimelb.edu.au, 750659
#      Own Daghagheleh, odaghagheleh@student.unimelb.edu.au, 816273
#      Wen Pin Wu, w.wu47@student.unimelb.edu.au, 871702
#

import sys, re
import bisect
from nltk.stem.wordnet import WordNetLemmatizer

#-------------------------------------------------------------------------------

def bsearch(my_list, word):
    idx = bisect.bisect_left(my_list, word)
    if idx != len(my_list) and my_list[idx] == word:
        return idx
    else:
        return -1

def get_BOW(text):
    BOW = {}
    for word in text.split(' '):
        BOW[word] = BOW.get(word,0) + 1
    return BOW

#-------------------------------------------------------------------------------

def read_vocabulary(target, target_filename, num_filter):
    print "reading vocabulary.."

    vocabulary = set()
    # read raw file then construct vocabulary
    fp = open(target_filename, 'r')
    line = fp.readline()
    while line:
        if target == 'voca':
            token = line.strip('\n').split('\t')
            if len(token) == 2 and \
                int(token[0]) >= num_filter and \
                token[1] != '':
                vocabulary.add(token[1])
        elif target == 'target':
            line = re.sub('[\s]+', ' ', line).strip('\n')
            token = line.strip(' ').split(' ')
            if len(token) == 3 and \
                float(token[0]) > num_filter and \
                token[2] != '':
                vocabulary.add(token[2])
        line = fp.readline()
    fp.close()

    return vocabulary

def gen_arff(txt_filename, out_filename, vocabulary):
    print len(vocabulary)

    print 'saving header..'

    vocalist = sorted(vocabulary)
    vocasize = len(vocalist)

    # save vocabulary which will be used
    fp_voca = open(out_filename + '.voca', 'wb')
    # save arff header
    fp1 = open(out_filename, 'wb')
    fp1.write('@RELATION twitter-sentiment-analysis\n')
    for voca in vocalist:
        fp1.write(('@ATTRIBUTE %s NUMERIC\n') % (voca.strip()))
        fp_voca.write(voca + '\n')
    fp1.write('@ATTRIBUTE sentiment {0,2,4}\n')
    fp1.write('@DATA\n')
    fp_voca.close()

    print 'saving data..'

    i = 0
    saved = 0
    fp = open(txt_filename, 'rb')
    line = fp.readline()
    while line:
        token = line.strip('\n').split('\t')
        tweet = get_BOW(token[1])

        if i % 10000 == 0:
            print ("[%10d] = -->[%s]<--") % (i, tweet)

        # check
        has_voca = False
        for k, v in tweet.items():
            if k in vocabulary:
                has_voca = True
                break
        # save
        if has_voca == True:
            #print ("[%10d] = -->[%s]<--") % (i, tweet)
            saved += 1
            fp1.write('{')
            for word in sorted(tweet):
                idx = bsearch(vocalist, word)
                if idx != -1:
                    fp1.write(str(idx) + ' ' + str(tweet.get(word)) + ', ')
            fp1.write(str(vocasize) + ' ' + token[0] + '}\n')

        i += 1
        line = fp.readline()

    fp1.close()
    fp.close()

    print ('arff saved..(%d)') % (saved)

#-------------------------------------------------------------------------------

if __name__ == "__main__":
    target = sys.argv[1]
    txt_filename = sys.argv[2]
    out_filename = sys.argv[3]
    target_filename = sys.argv[4]
    if target == 'voca':
        num_filter = int(sys.argv[5])
    elif target == 'target':
        num_filter = float(sys.argv[5])

    vocabulary = read_vocabulary(target, target_filename, num_filter)
    gen_arff(txt_filename, out_filename, vocabulary)
