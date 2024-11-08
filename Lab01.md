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
    ```
    spring:
        application.name: Lab01 OpenAI
        main.web-application-type: none     # dont start web server.
    
        ai:
        retry:
            max-attempts: 1
    ```    
1. Check everithing is ok by running main application
1. Due to security risk we will not add OpenAI's API key in application.yml, setting this value in an environment variable is safer (we have alredy done this).
1. Note: The retry settings will override the ChatClient's default settings. We don't want you to experience unnecessary delay or expense.
1. Save your work and check everythig is ok by running application.


## Explore Spring AI's with ChatClient
- Add following code into com.synergetics following code
```
@Component
@Profile("openai")
public class MyClient {
    private final ChatClient client;

    public MyClient(ChatModel model) {
        client = ChatClient.builder(model).build();
    }
    public String call(String input) {
        return client
                .prompt()
                .user(input)
                .call()
                .content();
    }
}
```
- Explanation for above code
    -- Note: the @Profile annotation will be useful later when we want our application to switch between OpenAI, Azure, Ollama, etc
    -- Configure ChatClient
    -- Add a call method.
        --- Define a String parameters for the user input / query.
        --- Use the client to call the Foundational Model.
        --- prompt() creates a prompt to pass
        --- user() sets the "user" message. Pass the given input String.
        --- call() invokes the model. It returns a CallResponse.
        --- content() is a simple means of extracting String content from the response. 
        
