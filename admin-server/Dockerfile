FROM openjdk:11.0.2-oraclelinux7
ADD target/admin-server.jar /
CMD java -jar -Dserver.port=$PORT admin-server.jar
