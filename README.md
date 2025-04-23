mvn archetype:generate -DgroupId=com.sudagoarth -DartifactId=common-lib \
  -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false
cd common-lib
mvn clean install
mvn install:install-file -Dfile=target/common-lib-1.0-SNAPSHOT.jar \
  -DgroupId=com.sudagoarth -DartifactId=common-lib \
  -Dversion=1.0-SNAPSHOT -Dpackaging=jar
mvn deploy:deploy-file -Dfile=target/common-lib-1.0-SNAPSHOT.jar \
  -DgroupId=com.sudagoarth -DartifactId=common-lib \
  -Dversion=1.0-SNAPSHOT -Dpackaging=jar \
  -Durl=http://localhost:8081/repository/maven-releases/ \
  -DrepositoryId=snapshots-repo
mvn deploy:deploy-file -Dfile=target/common-lib-1.0-SNAPSHOT.jar \# spring-microservices
