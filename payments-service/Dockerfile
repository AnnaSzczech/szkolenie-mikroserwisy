FROM openjdk:11.0.2-oraclelinux7
ADD target/payments-service.jar /
CMD java -jar -Dspring.profiles.active=$ACTIVE_PROFILE  \
    -Dspring.config.import=$CONFIGURATION_SERVER_URI \
    payments-service.jar
