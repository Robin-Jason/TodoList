FROM eclipse-temurin:8-jre

RUN mkdir -p /ssadmin-server
WORKDIR /ssadmin-server

COPY ./target/demo-0.0.1-SNAPSHOT.jar app.jar

ENV TZ=Asia/Shanghai
ENV JAVA_OPTS="-Xms512m -Xmx512m -Djava.security.egd=file:/dev/./urandom"
ENV ARGS=""

EXPOSE 28080

CMD java ${JAVA_OPTS} -jar app.jar $ARGS