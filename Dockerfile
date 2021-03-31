FROM openjdk:11
COPY . /tmp
WORKDIR /tmp
EXPOSE 10000
CMD ["java", "-jar", "bankteller.jar"]