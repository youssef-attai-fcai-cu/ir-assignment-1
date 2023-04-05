#!/usr/bin/bash

javac -d ./out ./src/com/example/*.java
java -cp ./out Main
