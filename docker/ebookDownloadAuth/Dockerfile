FROM openjdk:8-jre
WORKDIR /apps
COPY ./ebook-download-auth-1.0-SNAPSHOT.jar ./ebook-download-auth.jar
EXPOSE 8775
ENTRYPOINT ["java","-jar"]
CMD ["ebook-download-auth.jar"]