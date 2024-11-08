# Spring with OpenAI
## Generate the Project Structure using Spring Initializer
1. Click [here](https://start.spring.io), to create a new Spring Boot Project.
    1. Use Maven for this lab.
    1. Use the latest stable releases of Boot and Java.
    1. Use JAR packaging.
    1. Artifact name as lab01-openai
    1. Search for OpenAI dependency and select it
1. Generate. Find the downloaded zip and extract it. Copy the lab01-openai
1. Import project in your IDE
1. Basic Configuration and Test
    1. Rename application.properties file to application.yml
    1. Add following properties to yaml file

spring:
  application.name: Lab01 OpenAI
  main.web-application-type: none     # Do not start a web server.

  ai:
    retry:
      max-attempts: 1           # Maximum number of retry attempts.
