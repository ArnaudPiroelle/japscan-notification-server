FROM openjdk:8-jdk

COPY ./build/distributions/notifier-server-1.0-SNAPSHOT.zip /opt
RUN unzip /opt/notifier-server-1.0-SNAPSHOT.zip -d /opt

CMD ["/opt/notifier-server-1.0-SNAPSHOT/bin/notifier-server"]