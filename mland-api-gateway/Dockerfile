
FROM alpine:latest as build
ENV RELEASE=17
ENV CUSTOM_MODULES=jdk.crypto.ec,jdk.crypto.cryptoki

RUN apk --no-cache add openjdk17 maven binutils

WORKDIR /build
COPY pom.xml pom.xml
COPY src src
RUN mvn clean package dependency:go-offline -DskipTests

RUN `cp /build/target/*.jar application.jar`
RUN java -Djarmode=layertools -jar application.jar extract
RUN jlink \
         --verbose \
         --add-modules ${CUSTOM_MODULES},`jdeps --ignore-missing-deps -q -recursive --multi-release ${RELEASE} --print-module-deps --class-path 'dependencies/BOOT-INF/lib/*':'snapshot-dependencies/BOOT-INF/lib/*' application.jar` \
         --strip-debug \
         --no-man-pages \
         --no-header-files \
         --compress=2 \
         --output jdk


FROM alpine:latest

ARG BUILD_PATH=/build
ENV JAVA_HOME=/opt/jdk
ENV PATH "${JAVA_HOME}/bin:${PATH}"
ENV JAVA_OPTS=""

WORKDIR /application

COPY --from=build $BUILD_PATH/jdk $JAVA_HOME
COPY --from=build $BUILD_PATH/spring-boot-loader/ ./
COPY --from=build $BUILD_PATH/dependencies/ ./
COPY --from=build $BUILD_PATH/snapshot-dependencies/ ./
COPY --from=build $BUILD_PATH/application/ ./

EXPOSE 8080
ENTRYPOINT java $JAVA_OPTS org.springframework.boot.loader.JarLauncher