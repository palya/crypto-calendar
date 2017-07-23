FROM java:8-alpine
MAINTAINER Your Name <you@example.com>

ADD target/uberjar/crypto-calendar.jar /crypto-calendar/app.jar

EXPOSE 3000

CMD ["java", "-jar", "/crypto-calendar/app.jar"]
