#! /usr/bin/env sh

if [[ $# -lt 2 ]]; then
  echo "Please provide File path and Jar to execute"
  echo "*****Usage*****"
  echo "./run-application.sh <fat-jar-path> <file-path>"
  echo "./run-application.sh order-matching-system-0.0.1-SNAPSHOT-fatjar.jar orders.txt"
  exit 1;
else
  jar_path=$1
  if [[ -f "$2" ]]; then
    order_file_path=$2
    echo "Java version `java --version`"
    java -jar ${jar_path} ${order_file_path}
  else
    echo "Invalid file path=${$2}"
    exit 2;
  fi
fi
