FROM openjdk:11.0.2-oraclelinux7
ADD target/configuration-server.jar /
CMD java -jar -Dspring.profiles.active=$ACTIVE_PROFILE  \
    -Dspring.cloud.config.server.git.uri=$GIT_CONFIG_URI \
    -Dspring.cloud.stream.kafka.binder.brokers=$KAFKA_SERVER \
    configuration-server.jar
