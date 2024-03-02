#!/bin/bash

sudo dnf install -y -q git
git --version

sudo dnf install java-17-amazon-corretto
java -version

wget https://dlcdn.apache.org/maven/maven-3/3.9.5/binaries/apache-maven-3.9.5-bin.tar.gz
tar -xf apache-maven-3.9.5-bin.tar.gz
export PATH=$PATH:/home/ec2-user/apache-maven-3.9.5/bin
sudo ln -s /home/ec2-user/apache-maven-3.9.5/bin/mvn /usr/local/bin/mvn
mvn --version

git clone https://github.com/jkanclerz/computer-programming-4.git
cd computer-programming-4
mvn package -DskipTests
sudo java -Dserver.port=80 -jar target/my-ecommerce-0.1.jar
