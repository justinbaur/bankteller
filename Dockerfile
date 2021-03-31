FROM openjdk:11

RUN mkdir -p /run/secrets/bankteller-mongodb
RUN echo "mongodb.username=yourmom\nmongodb.password=yourmom" > /run/secrets/bankteller-mongodb/auth

COPY . /tmp
WORKDIR /tmp
EXPOSE 10000
RUN chmod 777 /tmp/bankteller.jar
RUN ls -al
CMD ["java", "-jar", "/tmp/bankteller.jar"]