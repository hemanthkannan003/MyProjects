
library('data.table')
library('tseries')
library('reshape')
library("forecast")

# Reading Dataset

df1=read.csv("Taxi1.csv")
df2=read.csv("Taxi2.csv")


head(df1)
head(df2)

str(df1)
str(df2)

newdataset=rbind(df1,df2)


head(newdataset)
str(newdataset)

newdataset=newdataset[,c(3,4)]

head(newdataset)

str(newdataset)

# Converting the Trip.Start.Timestamp columns to a required datatype
newdataset$Trip.Start.Timestamp<-as.POSIXct(newdataset$Trip.Start.Timestamp, format="%m/%d/%Y %H:%M:%S %p")
newdataset$Trip.Start.Timestamp<-format(newdataset$Trip.Start.Timestamp,"%m/%d/%Y")
newdataset$Trip.Start.Timestamp<-as.Date(newdataset$Trip.Start.Timestamp,"%m/%d/%Y")


str(newdataset)

#Converting the Trip.Total columns to a required datatype
newdataset$Trip.Total= as.numeric(gsub("\\$", "",as.character(newdataset$Trip.Total)))

str(newdataset)

# checking if there is any NA'S present in the dataset
sum(is.na(newdataset))
# there are totally 42 NAS in the dataset

# finding out the rows which has the NAS present 
newdataset[!complete.cases(newdataset),]

# Removing the rows which has NAS in both the columns
ind <- apply(newdataset, 1, function(x) all(is.na(x)))
newdataset <- newdataset[ !ind, ]

# checking if there is any NA'S present in the dataset
sum(is.na(newdataset))

#replacing NAs in price by replacing it with previous available observation since it is a timebased dataset
newdataset=na.locf(newdataset)
newdataset[!complete.cases(newdataset),]


head(newdataset)

str(newdataset)


newdataset$Trip.Total=as.numeric(newdataset$Trip.Total)

newdataset$Trip.Start.Timestamp=as.Date(newdataset$Trip.Start.Timestamp)

str(newdataset)

#Taking year and month
Month=months(newdataset$Trip.Start.Timestamp)
Years=format(newdataset$Trip.Start.Timestamp,"%Y")

#creating newdataset and adding year and month components
tset=newdataset
tset$Months<-Month
tset$Year<-Years

head(tset)

#Making sum of cost every year and every month
data<-aggregate(Trip.Total~Year+Months,tset,sum)
data$Months = factor(data$Months, levels = month.name)

df<-data[order(data$Year,data$Months),]


head(df)# is a df that has total cost for evrery month and year monthly order from 2013 to 2017

taxi_costs=df

head(df)

df[1:12,]

# EXCLUDING 2017

tcost=taxi_costs[1:48,]


plotForecastErrors <- function(forecasterrors)
  {
     # make a histogram of the forecast errors:
     mybinsize <- IQR(forecasterrors)/4
     mysd   <- sd(forecasterrors)
     mymin  <- min(forecasterrors) - mysd*5
     mymax  <- max(forecasterrors) + mysd*3
     # generate normally distributed data with mean 0 and standard deviation mysd
     mynorm <- rnorm(10000, mean=0, sd=mysd)
     mymin2 <- min(mynorm)
     mymax2 <- max(mynorm)
     if (mymin2 < mymin) { mymin <- mymin2 }
     if (mymax2 > mymax) { mymax <- mymax2 }
     # make a red histogram of the forecast errors, with the normally distributed data overlaid:
     mybins <- seq(mymin, mymax, mybinsize)
     hist(forecasterrors, col="red", freq=FALSE, breaks=mybins)
     # freq=FALSE ensures the area under the histogram = 1
     # generate normally distributed data with mean 0 and standard deviation mysd
     myhist <- hist(mynorm, plot=FALSE, breaks=mybins)
     # plot the normal curve as a blue line on top of the histogram of forecast errors:
     points(myhist$mids, myhist$density, type="l", col="blue", lwd=2)
  }

cost_series=ts(tcost$Trip.Total,frequency = 12,start = c(2013,1))
plot(cost_series)

# Ploting the cost series to find out whether they have seasonality and trend
costseriescomponents<-decompose(cost_series)
plot(costseriescomponents)

acf(cost_series)
# From this For a stationary time series, the ACF will drop to zero relatively quickly, 
# while the ACF of non-stationary data decreases slowly, Hence we could say that it is not stationary 

adf.test(log(cost_series),alternative="stationary")

diffcostseries<-diff(log(cost_series),differences = 1)
plot(diffcostseries)

adf.test(diffcostseries,alternative = "stationary")

acf(diffcostseries)
pacf(diffcostseries)

#Differencing for Trend and Seasonality: When both trend and seasonality are present, 
#we may need to apply both a non-seasonal first difference and a seasonal difference.

(fitcost1 <- arima(cost_series, c(1, 1, 0),seasonal = list(order = c(2, 1, 3), period = 12)))

predcost1=forecast(fitcost1,h=12)

plot(predcost1)

#box Lujung test 
#Box.test(predicted$residuals, lag=20, type="Ljung-Box")

Box.test(predcost1$residuals, lag=20, type="Ljung-Box")


acf(predcost1$residuals, lag.max=20)
pacf(predcost1$residuals, lag.max=20)
qqnorm(predcost1$residuals)
qqline(predcost1$residuals)

#ts.plot(forecast(fit,h=12))
a=forecast(fitcost1,h=12)

a

plot(cost_series,col="green")
lines(fitted(fitcost1),col="red")

plotForecastErrors(predcost1$residuals)

## Trying Holtwinters

hcost=HoltWinters(cost_series)


hcost

summary(hcost)

hcost$fitted

plot(hcost)

fcst=forecast(hcost,12)
plot(fcst)
fcst

#box Lujung test 
#Box.test(predicted$residuals, lag=20, type="Ljung-Box")

Box.test(fcst$residuals, lag=20, type="Ljung-Box")


fcst$residuals

acf(fcst$residuals, lag.max=20)
pacf(fcst$residuals, lag.max=20)
qqnorm(fcst$residuals)
qqline(fcst$residuals)

plot.ts(fcst$residuals)            # making a residual plot

plotForecastErrors(fcst$residuals)

auto.arima(cost_series)

fitcost2 <- arima(cost_series, c(0, 1, 1),seasonal = list(order = c(0, 1, 0), period = 12))

fitcost2

predcost2=forecast(fitcost2,h=12)

predcost2;predcost1

#box Lujung test 
#Box.test(predicted$residuals, lag=20, type="Ljung-Box")

Box.test(predcost2$residuals, lag=20, type="Ljung-Box")


acf(predcost2$residuals, lag.max=20)
pacf(predcost2$residuals, lag.max=20)
qqnorm(predcost2$residuals)
qqline(predcost2$residuals)

plot(cost_series,col="green")
lines(fitted(fitcost2),col="red")

plot(predcost2)

plotForecastErrors(predcost2$residuals)

a=data.frame(predcost1$mean)
b=data.frame(predcost2$mean)
c=data.frame(fcst)
c=data.frame(c$Point.Forecast)
e=data.frame(df[49:(nrow(df)-1),3])

Model1=a[1:5,]
Model2=b[1:5,]
Model3=c[1:5,]
Actual_Values=e[,]

Comparison_table=data.frame(c(Model1))


Comparison_table$Model2=Model2

Comparison_table$Holtzwinter=Model3

Comparison_table$Actual_values=Actual_Values

Comparison_table
C_T=Comaparison_table


colnames(Comparison_table)[1]="Arima(1,1,0)(2,1,3)"
colnames(Comparison_table)[2]="Auto_Arima"

Comparison_table
