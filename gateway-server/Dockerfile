FROM openjdk:11.0.2-oraclelinux7
ADD target/gateway-server.jar /
CMD java -jar -Dspring.profiles.active=$ACTIVE_PROFILE  \
    -Dspring.config.import=$CONFIGURATION_SERVER_URI \
    gateway-server.jar