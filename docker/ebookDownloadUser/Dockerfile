FROM openjdk:8-jre
WORKDIR /apps
COPY ./ebook-download-user-1.0-SNAPSHOT.jar ./ebook-download-user.jar
EXPOSE 8780
ENTRYPOINT ["java","-jar"]
CMD ["ebook-download-user.jar"]