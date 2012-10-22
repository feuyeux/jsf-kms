git pull origin master
mvn dependency:sources
mvn clean install -Dmaven.test.skip=true