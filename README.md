### What is this repository for? ###
Simple service built on Spring stack which returns average temperature and pressure 

### Run ###
API key = f7ebbb02a7a69cb2bf74a9fa08abeb11

````
# export JAVA_HOME=/usr/local/jdk-11.jdk/Contents/Home/
export JAVA_HOME={PATH_TO_YOUR_JAVA11_SDK}
./gradlew clean build && java -jar build/libs/weather-forecast-0.0.1.jar
````

### Details ###

api.openweathermap.org/data/2.5/forecast?q=Berlin,de&APPID={APIKEY}
api.openweathermap.org/data/2.5/forecast?q=Berlin,de&APPID=f7ebbb02a7a69cb2bf74a9fa08abeb11


### Check the code ###

Project uses Lombok.
In order to comfortable look through the code use IDE plugins  

### Contact ###

alexey.lesh@gmail.com
