FROM maven:3-openjdk-11

RUN mkdir -p /run/secrets/bankteller-mongodb
RUN echo "mongodb.username=yourmom\nmongodb.password=yourmom" > /run/secrets/bankteller-mongodb/auth
COPY . /tmp
WORKDIR /tmp
EXPOSE 8080
CMD ["mvn", "spring-boot:run"]