<h1 align="center">
  :computer: Back-end :memo:
</h1>

## How To Use

To run this application, you'll need: 
  * [Git](https://git-scm.com)
  * [Java 11](https://openjdk.java.net/projects/jdk/11/)
  * [Maven](http://maven.apache.org/download.cgi)

```
# Clone this repository
$ git clone https://github.com/dericksm/softplan-desafio-fullstack-derick-souza

# Go into the folder "back-end"
$ cd back-end

# Database config
If you want to run with a MySQL database you'll need to change the profile to "dev" and set the database configuration at the file src/main/java/resources/application-dev.properties

# Install Maven dependencies
$ mvn package

# To Build with docker
docker build -f back-end .
```
