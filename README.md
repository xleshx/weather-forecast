### What is this repository for? ###
Simple service built on Spring stack which returns average temperature and pressure 

### Run ###
Before build and start OpenWeatherMap API key should be updated in application.yml
````
api.key: {YOUR_KEY_HERE}
````
After key is added, build and run.
Note: sources are build against Java 11 for training purposes, didn't test on earlier JDKs
````
# export JAVA_HOME=/usr/local/jdk-11.jdk/Contents/Home/
export JAVA_HOME={PATH_TO_YOUR_JAVA11_SDK}
./gradlew clean build && java -jar build/libs/weather-forecast-0.0.1.jar

# examle of the call
curl http://localhost:8080/data?city=Berlin
{"threeDaysDailyAverageMinorUnits":286.245,"threeDaysNightlyAverageMinorUnits":282.7031111111111,"threeDaysPressureAverageMinorUnits":1035.7609523809524}
````

### Details ###

* compromised on TimeZone support because of time constraints and no clear way how to map country code/city to TimeZone


### Check the code ###

Project uses Lombok.
In order to comfortable look through the code use IDE plugins  

### Contact ###

alexey.lesh@gmail.com
