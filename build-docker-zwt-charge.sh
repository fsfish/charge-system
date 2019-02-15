#!/bin/sh

if [ ! -x "./gradlew" ]; then
    chmod +x ./gradlew
fi

VERSION="$(./gradlew printProjectVersion -q)"
if [ -z ${VERSION} ]; then
    echo "ERROR: please specify a version number in build.gradle"
    exit 1
else
    echo "Build version: " ${VERSION}
fi

SERVICE_NAME=zwt-charge
JAR_NAME=${SERVICE_NAME}-${VERSION}.jar
DOCKER_JAR_NAME=${SERVICE_NAME}.jar
DOCKER_NAME=镜像域名/命名空间/${SERVICE_NAME}:v${VERSION}

echo "********************************************************"
echo "Build ${SERVICE_NAME} jar package"
echo "********************************************************"

./gradlew bootJar

echo "********************************************************"
echo "Build ${DOCKER_NAME} docker image"
echo "********************************************************"
cp build/libs/${JAR_NAME} docker/${DOCKER_JAR_NAME}

echo $PATH

echo $(which docker)

docker build -f docker/Dockerfile -t ${DOCKER_NAME} docker

rm -rf docker/*.jar
echo "********************************************************"
echo "Upload ${DOCKER_NAME} docker image to duobei docker hub"
echo "********************************************************"
docker push ${DOCKER_NAME}
