library(ff)
library(bigmemory)
library(NLP)
library(wordcloud)
library(tm)
library(SnowballC)
library(plyr)
library(stringr)
library(quanteda)
library(FSelector)

input <- read.csv(file="manual_machine.csv",head=TRUE,sep=",") 
CleanTweets<-function(input)
  Text<-input$text
senti<-input$sentiment

text<-gsub("\r?\n|\r|\t", " ", Text)
text<-gsub(" http.*","",Text)
text<- gsub("#\\w+","",Text)
text <- gsub("(RT|via)((?:\\b\\W*@\\w+)+)", "", Text)
text<- gsub("@\\w+", "", Text)
text<- gsub("[[:punct:]]", "", Text)
text<- gsub("[[:digit:]]","",Text)
text<- gsub("[^a-zA-Z0-9]"," ",Text)
text<- gsub("^\\s+|\\s+$","",Text)


cor<- Corpus(VectorSource(text))
cor = tm_map(cor, content_transformer(tolower))
cor<- tm_map(cor, removeWords,c(stopwords("english")))
cor <- tm_map(cor, removePunctuation)
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
               "check","another","actually","gotta","your" )
cor=tm_map(cor,removeWords,mystopwords)
cor <- tm_map(cor, stripWhitespace)
cor<-tm_map(cor, stemDocument)
cor<-tm_map(cor,PlainTextDocument)


dtm <- DocumentTermMatrix(cor)
m<-as.matrix(dtm)
freq<-sort(colSums(m),decreasing=TRUE)
findFreqTerms(dtm,lowfreq = 1000,highfreq =1500 )
d <- data.frame(word = names(freq),freq=freq)
options(max.print = 100000)


cloud<-wordcloud(words = d$word, freq = d$freq, min.freq = 50,
                 max.words=200, width=2000,height=1000,random.order=FALSE, rot.per=0.35, 
                 colors=brewer.pal(8, "Accent"))

barplot(d[1:10,]$freq, las = 2, names.arg = d[1:10,]$word,
        col=c("lightblue", "mistyrose", "lightcyan","lavender", "cornsilk"),
        ylab = "Word frequencies")
library(pander)
library(syuzhet)

mySentiment <- get_nrc_sentiment(text)
angry_items <- which(mySentiment$anger > 0)
text[angry_items]
pander::pandoc.table(mySentiment[, 1:8], split.table = Inf)
barplot(
  sort(colSums(prop.table(mySentiment[, 1:8]))), 
  horiz = TRUE, 
  cex.names = 0.7, 
  las = 1, 
  col=c("darkblue","red","yellow","orange","pink","green","blue"),
  main = "Emotions in Sample text", xlab="Percentage"
)


df<-data.frame(text=unlist(sapply(corp, `[`, "content")), 
               stringsAsFactors=F)

neg <-read.csv(file = "negative.csv",header=FALSE,sep=",",stringsAsFactors = FALSE)
pos <-read.csv(file="positive.csv", head=TRUE,sep=",", comment.char=';',stringsAsFactors = FALSE)
neg <- unlist(neg)
neg <- stemDocument(neg)
pos <- unlist(pos)
pos <- stemDocument(pos)

summa<- function(dat,pos,neg)
{
  Text<- character(nrow(dat))
  Label<- character(nrow(dat))
  Scores<- numeric(nrow(dat))
  poscount=0
  negcount=0
  for (i in 1:nrow(dat))
  {
    one<- dat[i,]
    txt <- strsplit(one, split=" ")
    words <- unlist(txt)
    neg.matches = match(words, neg)
    neg.matches
    pos.matches = match(words, pos)
    pos.matches <- sum(!is.na(pos.matches))
    neg.matches <- sum(!is.na(neg.matches))
    score = sum(pos.matches) - sum(neg.matches)
    if(score>0){
      Text[i]<-dat[i,]
      Label[i]<- "POSITIVE"
      Scores[i]<-score
    }else if(score<0){
      Text[i]<-dat[i,]
      Label[i]<- "NEGATIVE"
      Scores[i]<-score
    }else{
      Text[i]<-dat[i,]
      Label[i]<- "NEUTRAL"
      Scores[i]<-score
    }  
  }
  df2<-data.frame(Text,Label,Scores,stringsAsFactors=FALSE)
  return(df2)
}
m <- summa(df,pos,neg)   
dim(m)   
write.csv(m, file="sentiment.csv")  
count(m)

ggplot(data2, aes(x=id, y=frequency, fill=Group)) + 
  geom_bar(position="dodge",      # prevents overlapping
           stat = "identity",
           colour="black",
           size=0.5
  )