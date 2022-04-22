# Api Micronaut Caprover

## Prerequisites
- [x] Gitlab
- [x] Gitlab-Runner - At least one runner configured for the project. Check the documentation at the link below:
  - https://docs.gitlab.com/runner/register/
- [x] gitlab-ci.yml

## Getting started

**1)** To start, we must create an application in caprover with data persistence. To do this, just give the app a name and select the ***Has Persistent Data*** option.

![sparkles](images/criar-app-java-caprover.png)

**2)** Now we must configure our caprover application. Pay attention to this step because it interferes with the app's deployment. After creating the app, we must click on the name of the app just below the creation part.

![sparkles](images/configurar-app-1.png)

**3)** In the ***HTTP Settings*** tab, we can configure the default port of our container. As our application is using port *8080*, we will put this port in the "**Container HTT Port**" option.

![sparkles](images/configurar-app-2.png)

**3)** In the ***App Configs*** tab, we can configure the mapping of ports, variables and the main thing that in this case is where the application data will be stored. For that click on the option and "**Add Persistent Directory"** and then put the path that will be used inside the container and a description.

![sparkles](images/configurar-app-3.png)
![sparkles](images/configurar-app-4.png)

**4)** On the ***Deployment*** tab, we have several deployment methods, we will use the option to link a git repository. With this feature, any changes sent to gitlab, as soon as the push pipeline is finished, caprover itself will automatically deploy.

![sparkles](images/configurar-app-5.png)

**5)** After clicking ***Save & Update***, caprover will generate a url to be used in webhooks.

![sparkles](images/configurar-app-6.png)


## With our caprover app configured, show me the code!!

In order for the changes made to our JAVA code to be reflected in the caprover, we have to have three files for this to work correctly:
- [x] dockerfile
- [x] captain-definition
- [x] gitlab-ci.yml

**DockerFile**
```
FROM java:8
WORKDIR /app
VOLUME /var/lib/docker/volumes/captain--app-spring/_data:/app
CMD java -jar demo-0.0.1-SNAPSHOT.jar
```

> NOTE: In this file we define ***the image*** to be used by docker, the ***WORKDIR*** which is the application directory, ***the volume*** and ***the command*** to be executed when uploading the container.


**CAPTAIN-DEFINITION**
```
{
	"schemaVersion" :2 ,
	"dockerfileLines" : [
		"FROM java:8",
		"VOLUME /var/lib/docker/volumes/captain--app-spring/_data:/app",
		"WORKDIR /app",
		"CMD java -jar demo-0.0.1-SNAPSHOT.jar"
	]
}
```

> NOTE: Very similar to the dockerfile file, but with some syntax used by caprover itself.


**gitlab-ci.yml**
```
stages:
  - build
  - deploy
  - notify

maven-build:
  image: maven:3.5.3-jdk-8
  stage: build
  script:
      - "mvn clean install"
  artifacts:
    paths:
      - target/demo-0.0.1-SNAPSHOT.jar

update-jar:
  stage: deploy
  when: on_success
  script:
      - cp target/demo-0.0.1-SNAPSHOT.jar /artifacts/demo-0.0.1-SNAPSHOT.jar
      - >
        curl -X POST "http://192.168.15.23:3000/api/v2/user/apps/webhooks/triggerbuild?namespace=captain&token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJkYXRhIjp7InRva2VuVmVyc2lvbiI6IjUwYmVjOGFmLWU2ZTUtNGM0MS04MGY5LWFiODA4Yjc5OTZmOCIsImFwcE5hbWUiOiJkZXBsb3ktc3ByaW5nIiwibmFtZXNwYWNlIjoiY2FwdGFpbiJ9LCJpYXQiOjE2NTA0ODM2ODJ9.FqXool41ttAsWb14H85nOhZ9U3QLQi37U4tLv6a-S4M"

notify-fail-build:
  stage: notify
  when: on_failure
  script:
      - echo "Falha ao buildar o aplicativo"
```

> NOTE: Here we will split the file into two parts. Being them stages and then we will explain each stage separately

> **STAGES:** Here we define what are the stages of our pipeline.

> **MAVEN-BUILD:** At this stage, the application is built. This is where the ".jar" file is generated for our application to run.

> **UPDATE-JAR:** 
 - >  In this stage, the application is deployed if the previous stage has obtained the success status. Note that there is a script that copies the ".jar" file generated in the build to the container volume:
  **cp target/demo-0.0.1-SNAPSHOT.jar /artifacts/demo-0.0.1-SNAPSHOT.jar**
  

 - >  We also have a command that notifies the caprover that a new change has been made or a new deploy in the case:
  ```curl -X POST "http://192.168.15.23:3000/api/v2/user/apps/webhooks/triggerbuild?namespace=captain&token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJkYXRhIjp7InRva2VuVmVyc2lvbiI6IjUwYmVjOGFmLWU2ZTUtNGM0MS04MGY5LWFiODA4Yjc5OTZmOCIsImFwcE5hbWUiOiJkZXBsb3ktc3ByaW5nIiwibmFtZXNwYWNlIjoiY2FwdGFpbiJ9LCJpYXQiOjE2NTA0ODM2ODJ9.FqXool41ttAsWb14H85nOhZ9U3QLQi37U4tLv6a-S4M"```
 
> **NOTIFY-FAIL-BUILD:** 
 - >  At this stage, we define what should be done if the build fails. In this case we just put an "echo", but here you can put a notification via email, tems, slack, etc.
  **echo "Falha ao buildar o aplicativo"**



## Now just push your code to git and the magic happens.
