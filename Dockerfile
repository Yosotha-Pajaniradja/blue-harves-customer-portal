FROM openjdk:8
ADD customer-information-api/target/customer-account-api-1.0.0-SNAPSHOT.jar customer-account-api.jar
ENTRYPOINT ["java", "custoemr-account-api.jar"]
