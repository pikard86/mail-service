gradlew build
gradlew assemble 
docker build -t pikard86/mail-service:0.0.4 .
docker push pikard86/mail-service:0.0.4
