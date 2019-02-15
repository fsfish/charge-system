FROM 镜像域名/命名空间/centos:latest

LABEL description="charge-system image"

WORKDIR /peaner

COPY charge-system.jar /zwt

EXPOSE 18668

CMD java -Xmx${JVM_MAX_MEMORY} -Xss1M -server -Djava.security.egd=file:/dev/./urandom \
    -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/var/peaner/logs \
    -Dspring.profiles.active=${SPRING_PROFILE} -jar charge-system.jar
