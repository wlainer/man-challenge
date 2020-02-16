## MAN FOTA CHALLENGE

The application is using a instance of h2 database which is created at each run.

The available features is being seeded to db through a script file.

The directory to watch for is configured through a environment variable.

There is unit tests for the business related logic (import data to db and check compatible/incompatible features). 
Also there is configured integration tests with Spring Boot to check the rest endpoints.

The swagger documentation is available [here](http://localhost:8080/swagger-ui.html).

### Instructions
#### Requirements
The application requires the environment variable **FOTA_DIR** to configure its watch dir.

#### Running application as a S.O service
To run the application as a jvm process:
- declare the environment variable **FOTA_DIR** pointing to the folder where the files will be dropped
- run the command according with operation system
    - *./mvnw spring-boot:run*  (linux)
    - *mvnw spring-boot:run*  (windows)

#### Running as a container
To run the application as a container:
- execute command *mvn clean package* to package the application
- create the docker image with: *docker build -t fota .*
- run the container with: docker run --rm -p 8080:8080 -e FOTA_DIR='/tmp' -v ~/temp/volks/:/tmp fota

|  As the application watches a directory, is necessary to mount a local folder inside the container to drop the files.


