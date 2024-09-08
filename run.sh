#!/bin/sh

JAVA_OPTS_DEFAULT=""
JAVA_OPTS=${JAVA_OPTS:-${JAVA_OPTS_DEFAULT}}

echo
echo "java ${JAVA_OPTS} -jar /app.war"
echo
java ${JAVA_OPTS} -Djava.security.egd=file:/dev/./urandom -jar /app.war