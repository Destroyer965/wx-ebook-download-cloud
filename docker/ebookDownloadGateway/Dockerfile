FROM openjdk:8-jre
WORKDIR /apps
COPY ./ebook-download-gateway-1.0-SNAPSHOT.jar ./ebook-download-gateway.jar
EXPOSE 8989
ENTRYPOINT ["java","-jar"]
CMD ["ebook-download-gateway.jar"]