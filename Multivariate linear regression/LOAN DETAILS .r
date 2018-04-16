
install.packages('caret', dependencies = TRUE)

library('caret')

install.packages('dummy')
library('data.table')
library('dummy')
library('magrittr')
library('dplyr')
library('readr')
library('ggplot2')
library('lattice')
library('class') 
library('kknn') 
library('caret') 
library('reshape2') 
library('PerformanceAnalytics')
library('lubridate')
library('mlbench')


dataset=read.csv("MyData.csv")

dim(dataset)

#1 Some of the analysis from the datas 

histogram( ~ loan_amnt | term, data=dataset, xlab="Loan Amount", ylab="Percentage of total data points",layout=c(1,2))
# ANS1: Average loan amount varies as per loan term.

# 2.Box plot to show grade wise loan amount taken
ggplot(data = dataset, aes(x=grade,y=loan_amnt))+geom_boxplot()

# Ans 2: Grade G is having maximum median loan amount while grade A is minimum.

#3 Box plot to show year-wise average recovery and also got the sum of recoveries.
dataset %>% group_by (last_pymnt_d) %>% summarise(Tot_rec = sum(recoveries)) %>% mutate (rec_year = year(mdy(last_pymnt_d))) ->loan_recover
ggplot(data = loan_recover, aes(x=factor(rec_year),y=Tot_rec))+geom_boxplot()

loan_recover %>% group_by(rec_year) %>% summarise(loan_recovered=sum(Tot_rec)) %>% arrange(desc(loan_recovered))
# ANS 3: Maximum loan recovery is done in year 2014

#4 Group data for loan based on issue_d. Box plot on yearwise loan sanction
dataset %>% group_by (issue_d) %>% summarise(Tot_fund_amt = sum(funded_amnt),Tot_loan_amnt= sum(loan_amnt),Tot_fund_amnt_inv = sum(funded_amnt_inv)) %>% mutate (sanc_year = year(mdy(issue_d))) ->loan_sanctioned
loan_sanctioned$sanc_year = as.factor(loan_sanctioned$sanc_year)
loan_sanctioned %>% group_by(sanc_year) %>% summarise (loan_amt = sum(Tot_loan_amnt)) %>% arrange(desc(loan_amt))
ggplot(data = loan_sanctioned, aes(x=sanc_year,y=Tot_loan_amnt))+geom_boxplot()

# ANS 4: Maximum loan was sanctioned in year 2013.

#5 Loan amount Distribution based on Grades assigned by Lending Club
ggplot(dataset, aes(loan_amnt, col = grade)) + geom_histogram(bins = 50) + facet_grid(grade ~ .)
#Ans 5: Those with higher grades (A, B, C and D) have received more loans compared to those with lower grades (E, F and G). 

#6 Density plot on loan_amnt
densityplot(~ loan_amnt, data=dataset, xlab= "Loan Amount",ylab = "Density")

# Here are some columns we want to remove and why:
# index is not needed because it's built-in the dataframe itself
# policy_code is always == 1
# payment_plan has only 100000 n
# url not needed, although it might be useful if it contains extra-data (e.g., payment history)
# id and member_id are all unique, which is a bit misleading. I was expecting to find payment histories, but it seems that every record is a single customer.
# application_type is 'INDIVIDUAL' for 97% of the records
# acc_now_delinq is 0 for 97% of the records
# emp_title not needed here, to much of factor variables
# zip_code not needed for this level of analysis,
# title might be useful with NLP, but let's ignore it for now
# Dates are removed because of the number of levels

dataset[,c('X','id','policy_code','member_id','sub_grade','emp_title','url','title','addr_state','zip_code','application_type','desc','acc_now_delinq')]<-NULL
dataset[,c('earliest_cr_line','issue_d','last_pymnt_d','next_pymnt_d','last_credit_pull_d')]<-NULL

head(dataset)
dim(dataset)


dataset<-dataset[,colSums(!is.na(dataset))>60000]

colSums(is.na(dataset[,c('annual_inc','delinq_2yrs','inq_last_6mths','open_acc','pub_rec','revol_util','total_acc','collections_12_mths_ex_med')]))

boxplot(dataset[,c('delinq_2yrs','inq_last_6mths','open_acc','pub_rec','revol_util','total_acc','collections_12_mths_ex_med')])

dataset$delinq_2yrs[is.na(dataset$delinq_2yrs)]<-0

dataset$inq_last_6mths[is.na(dataset$inq_last_6mths)]<-0

dataset$pub_rec[is.na(dataset$pub_rec)]<-0

dataset$collections_12_mths_ex_med[is.na(dataset$collections_12_mths_ex_med)]<-0


dataset$annual_inc[is.na(dataset$annual_inc)]<-mean(dataset$annual_inc,na.rm=TRUE)
dataset$open_acc[is.na(dataset$open_acc)]<-median(dataset$open_acc,na.rm=TRUE)
dataset$total_acc[is.na(dataset$total_acc)]<-round(mean(dataset$total_acc,na.rm=TRUE))
dataset$revol_util[is.na(dataset$revol_util)]<-median(dataset$revol_util,na.rm=TRUE)


unlist(lapply(dataset, function(dataset) any(is.na(dataset))))

table(dataset$loan_status)

data<-dataset

data$loan_status<-as.character(factor(data$loan_status))
selected<-c("Charged Off","Current","Fully Paid","Does not meet the credit policy. Status:Charged Off"," Does not meet the credit policy. Status:Fully Paid 
","Default","In Grace Period","Late (16-30 days)","Late (31-120 days)")
data<-data[(data$loan_status) %in% selected,]
data$loan_status<-as.factor(data$loan_status)
levels(data$loan_status) <- c('0','1','1','0','1','0','0','0','0')

selected<-c("Current","Does not meet the credit policy. Status:Fully Paid","Fully Paid")
data$loan_status<-ifelse(data$loan_status==selected,1,0)

unique(data$loan_status)

dim(data)

loan_status<-data$loan_status
data$loan_status<-NULL


data <- cbind(data, dummy(data))
data[,c('term','grade','emp_length','home_ownership','verification_status','pymnt_plan','purpose','initial_list_status','application_type')]<-NULL

indx <- sapply(data, is.factor)
data[indx] <- lapply(data[indx], function(x) as.numeric(as.character(x)))


str(data)

full=lm(loan_status~., data=data)

step(full, data=data, direction="backward")

dim(data)

set.seed(2)
ind <- sample(2, nrow(data), replace = TRUE, prob = c(0.7, 
                                0.3))

train <- data[ind == 1, ]
test <- data[ind == 2, ]
dim(train)
dim(test)

grid1 <- expand.grid(k = seq(2, 20, by = 1))
control <- trainControl(method = "cv")

library(kknn)
knn.train <- train.kknn(train$loan_status ~ ., data = train,
                  method = "knn",
                  trControl = control,
                  tuneGrid = grid1,tuneLength = 10)

knn.train

knn.test <- knn(train[, -73], test[, -73], train[, 73], k = 11)

head(knn.test)

confusionMatrix(knn.test,test$loan_status)

model_pred_prob=predict(knn.train,train[,1:72])

head(model_pred_prob)

confusionMatrix(model_pred_prob,train$loan_status)

svm_Linear <- train(train$loan_status~., data = train, method = "svmLinear",
                 trControl=trctrl,
                 preProcess = c("center", "scale"),
                 tuneLength = 10)
