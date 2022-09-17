FROM adoptopenjdk/openjdk11:alpine-jre
EXPOSE 9190
ADD target/order-service-1.0.0.jar order-service-1.0.0.jar
ENTRYPOINT ["java","-jar","/order-service-1.0.0.jar"]