FROM openjdk:11
COPY . /tmp
WORKDIR /tmp
EXPOSE 10000
RUN chmod 777 bankteller.jar
CMD ["java", "-jar", "bankteller.jar"]