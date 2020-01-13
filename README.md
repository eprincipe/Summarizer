# README

This project was developed by Emanuel Principe!
Course: Topics in CS/ NLP
University of Guelph
2019/04/15

## Description

Extractive alternative of document summa-rization inspired by the works of Zhang & Li[7],  and Jo[6].  The idea is to generate a matrix of similarities between sentences, and then cluster them into topics. The center of each set is  then  assigned  into  a  summary andcompared a human-generated one for comparison. See attached report for details.

## Compilation
source files in the src folder
javac preprocess/*.java offline/*.java

## Execution
There are 2 main classes: Scanner and Summarizer.

Scanner in the preprocess directory and takes 2 arguments:
1-the path for the articles folder, ex: "News Article/business"
2-the path for the summaries folder, ex: "Summaries/business"
Scanner will generate a file called bibliography.txt
with the ordenaded articles with:
1-total of summaries sentences
2-total of sentences in the article
3-list of indexes for the summaries in the text
4-a line for each sentence.

Summarizer is inside the offline folder and has the options:
-k will use the estimated number of k by the technique of Ramiz
default is using the same number of summary sentences.
-f output to a file called scores.txt
-s print summary along with its scores. the order of the sentences is
not taken in consideration
The output file will contain at least the mean of the scores
followed by the scores for each article

## Future Improvements

-ROUGE-N would be a great addition for comparison purposes.
-Other similarity measures for testing purposes.
-Word Embeddings for calculating similarity as inverse of distance.
-Classifiers  and  potentially  Deep  Learning  methods  maybe  associatedwith the previous item would be a great field of exploration.
-Different clusterization methods such as DBScan.
-Implementing a ranking between clusters so that order becomes a factorin the calculations of the results.
