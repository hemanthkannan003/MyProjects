setwd("/Users/sree/Desktop/Semester2/datamining/DataMining/KNN/Bag of Words/Fourth Fold")

#Libraries required to perform the classification operation including the preprocessing operations, and also for the accuracy operations


library(NLP)
library(wordcloud)
library(tm)
library(SnowballC)
library(plyr)
library(stringr)
library(class)
LIBRARY(rtext)
Library(RtextTools)

# Reading the input file or importing the input file containing the Twitter data

input <- read.csv(file="/Users/sree/Desktop/Semester2/data mining/DataMining/KNN/Bag of Words/Fourth Fold/Fourth fold.csv",head=TRUE,sep=",")

# converting the input data into a vector source

input.corp<-Corpus(VectorSource(input$text))

#function to read the inpult file and the input under the label text

CleanTweets<-function(input)
  input.corp<-input$text

# the below ode is run for preprocessing the input data which includes removal of https tages, stemming , removal of stops words, punctuation, alphanumeric letters

input.corp<-gsub("\\.","",input.corp)
input.corp<-gsub(" http.* *","",input.corp)
input.corp<-gsub("(f|ht)tp(s?) *://(.*)[.][a-z]+", "", input.corp)
input.corp<- gsub("#\\w+","",input.corp)
input.corp <- gsub("(RT|via)((?:\\b\\W*@\\w+)+)", "", input.corp)
input.corp<- gsub("@\\w+", "", input.corp)
input.corp<- gsub("[[:punct:]]", "", input.corp)
#input.corp<- gsub("[[:cntrl:]]", "", input.corp)
input.corp<- gsub("[[:digit:]]","",input.corp)
input.corp<- gsub("[^a-zA-Z0-9]"," ",input.corp)
input.corp<- gsub("^\\s+", "", input.corp)
input.corp<- gsub("[\r\eval]", "", input.corp)
input.corp<-gsub("\\.","",input.corp)


# Convereting the preprocessed data into vector corpuse source which is used again for further processing and text transformation if in case if there is any extra

input.corp<- VCorpus(VectorSource(input.corp))
input.corp <- tm_map(input.corp,content_transformer(function(x) iconv(x, to='UTF-8-MAC', sub='byte')),mc.cores=1)
input.corp <- tm_map(input.corp, content_transformer(tolower), mc.cores=1)
input.corp <- tm_map(input.corp, removeNumbers)
input.corp <- tm_map(input.corp, stripWhitespace)
input.corp <- tm_map(input.corp, removePunctuation, mc.cores=1)
input.corp <- tm_map(input.corp, stemDocument, language = "english")
input.corp <- tm_map(input.corp, function(x)removeWords(x,stopwords("english")), mc.cores=1)

#user entered stop words which we found less frequently appearing in the input data

#input.corp<- tm_map(input.corp, removeWords,c(stopwords("english")))
mystopwords<-c("a","able","about","across","after","all","almost","also","am","among",
               "an","and","any","are","as","at","be","because","been","but","by","can",
               "cannot","could","dear","did","do","does","either","else","ever","every","for",
               "from","get","got","had","has","have","he","her","hers","him","his","how","however","i","if",
               "in","into","is","it","its","just","least","let","like","likely","may","me","might","most","must",
               "my","neither","nor","of","off","often","on","only","or","other","our","own","rather","said",
               "say","says","she","should","since","so","some","than","that","the","their","them","then","there","these",
               "they","this","tis","to","too","twas","us","wants","was","we","were","what","when","where","which","while",
               "who","whom","why","will","with","would","yet","you","your","last","amp","night","fox","gop","one","can","amp","just",
               "get","going","still","term","now","httpstcobhvimxjew","even","anything","back","done","gonna","keep","know","make", "much",
               "nothing","rep","right","see","thats","really","yall","thats","want","pass", "two","thing","things","though","today","tonight",
               "take","rep","run","running","ryan","scotus","remember","potus", "please","next","needs","made","makes","many","looking","lot",
               "look","lets","gets","give","goes","happen","hes","forget","end","everyone","everything","dems","day","delaware","dem", "come",
               "check","another","actually","gotta")

input.corp=tm_map(input.corp,removeWords,mystopwords)
input.corp
input.corp<-tm_map(input.corp,PlainTextDocument)
input.corp[[1019]]$content
nb <-ncol(input)

# retrieve the id column
idsnos <- input[,1]
# retrieve the class column
classcol <-as.character(input[,nb])

#creating the matrix of the input
input.corp <- create_matrix(input[,1])
matrt <-as.matrix(input.corp)
matrt

# creating a container of variables and dividing the rwation into 80 and 20 percent

crecontain <- create_container(corp,as.numeric(as.factor(input[,2])),trainSize = 1:5000,testSize = 5001:6000, virgin=FALSE)
crecontain
#creating a model for the algorithm
trmodel = train_model(crecontain ,algorithm = c("SVM"))
trmodel
#classifying the models based on the container models and the models that is used for training
finres = classify_model(crecontain, trmodel)
finres
write.csv(finres,file="BagofWordsSecondFold.csv")

# function to classify the labels into maximum entropy classifier
t<-table(as.numeric(as.factor(input[4001:5000, 2])), results[,"SVM_LABEL" ])
t
typeof(t)
recall_accuracy(as.numeric(as.factor(input[4001:5000, 2])), results[,"SVM_LABEL"])

#function to exhibit the evaluation metrics such as precision, recall and f1 score
analytics = create_analytics(crecontain, results)
summary(analytics)

n = sum(conf.mat) # number of instances
nc = nrow(conf.mat) # number of classes
diag = diag(conf.mat) # number of correctly classified instances per class 
rowsums = apply(conf.mat, 1, sum) # number of instances per class
colsums = apply(conf.mat, 2, sum) # number of predictions per class
p = rowsums / n # distribution of instances over the actual classes
q = colsums / n # distribution of instances over the predicted classes
accuracy = sum(diag) / n 
accuracy
precision = diag / colsums 
recall = diag / rowsums 
f1 = 2 * precision * recall / (precision + recall) 
data.frame(precision, recall, f1)

