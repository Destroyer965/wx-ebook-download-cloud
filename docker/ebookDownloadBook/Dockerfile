FROM openjdk:8-jre
WORKDIR /apps
COPY ./ebook-download-book-1.0-SNAPSHOT.jar ./ebook-download-book.jar
EXPOSE 8750
ENTRYPOINT ["java","-jar"]
CMD ["ebook-download-book.jar"]