#!/bin/bash

killAll() {
    kill -9 $(netstat -nlp | grep :8080 | awk -F/ '{print $1}' | awk '{print $7}') 2> /dev/null
    exit
}

trap killAll INT

cd ../back
./mvnw spring-boot:run