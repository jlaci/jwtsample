# How to
## Build

To build the project you'll need:
* JDK 8
* Maven 3.5

Navigate to the root directory and issue:

```
mvn clean install
```

## Start
To start the application first you'll have to create a database. For this example we'll use Docker to start our MySQL DB.

```
docker run --name jwt-sample-mysql -p 3306:3306 -e MYSQL_ROOT_PASSWORD=jwtsample -d mysql:latest
```

This will create and start a docker container named jwt-sample-mysql, anytime from now on when you want to use it, you can start it with:

```
docker start jwt-sample-mysql
```