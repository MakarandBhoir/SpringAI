# Spring with Azure OpenAI
---
### Pre-requisite

1. **Setup an Azure account**:  Go to https://portal.azure.com/#home (If you already have an account, you can skip to the next steps on signing up for Azure AI services.)

    - The signup process typically asks your a few questions on what you plan to do with Azure.  This is mainly requires your payment details.
    
1. **Signup for Azure AI Services**: Search for "Azure AI Services".

1. **Create an Azure OpenAI _resource_**: Once you have an Azure account and Azure OpenAI is enabled, create an Azure OpenAI resource:

    1. Search for "Azure AI Services" --> "Azure OpenAI" --> "Create Azure OpenAI".
    1. Select any subscription, resource group, and region you like.
    1. Select the pricing plan.
    1. Keep other options as default and create resource.

1. **Obtain endpoint and keys**:

    1. Sype "Azure AI Services" --> "Azure OpenAI" --> choose the resource you just created
    1. From the resource details page, select "click here to view endpoints". 
    1. Click "Show Keys" and copy to clip board

1. Set an environment variable named `SPRING_AI_AZURE_OPENAI_API_KEY` using this value.  On Windows you can run: 
    ```
    setx SPRING_AI_AZURE_OPENAI_API_KEY "KEY-GOES-HERE"
    ```
    On Linux or Mac you can run:
    ```
    export SPRING_AI_AZURE_OPENAI_API_KEY="KEY-GOES-HERE"
    ```
1. Restart your IDE after setting an environment variable this way.


1. **Create a _Deployment_**: Open https://oai.azure.com/portal in a new browser tab.  Go to Resource Management / Deployments / Create new deployment.
    1. Select a model to use. For Example  _gpt-35-turbo_ .
    1.  Choose the default version and standard deployment type.
    1.  Provide a name for the deployment.  We suggest using the same name as the model you've just selected (there is generally a 1 to 1 relationship with model and deployment).  Record the name, you will need it later.
    1. Create. 

---
 
## Generate the Project Structure using Spring Initializer
1. Click [here](https://start.spring.io), to create a new Spring Boot Project.
    - Use Maven for this lab.
    - Use the latest stable releases of Boot and Java.
    - Use JAR packaging.
    - Artifact name as lab02-azureopenai
    - Search for Azure OpenAI dependency and select it
1. Generate. Find the downloaded zip and extract it. Copy the lab02-azureopenai
1. Import project in your IDE
1. Basic Configuration and Test
    - Rename application.properties file to application.yml
    - Add following properties to yaml file
    ```
    spring:
      application.name: Lab02 Azure OpenAI
      main.web-application-type: none     # Do not start a web server.
      
      ai:
        retry:
          max-attempts: 1    # Maximum number of retry attempts.
        azure:
          openai:
            endpoint: https://myazureopenai25111422.openai.azure.com/   # Azure OpenAI endpoint.
            # api-key: 1234567890abcdef1234567890abcdef1234567890abcdef1234567890abcdef  # Azure OpenAI your resource API key.
            chat:
              enabled: true   # Enable chat API. default: true
    ```    
    
  - The `spring.ai.openai.chat.enabled` setting tells Spring Boot to specifically autoconfigure objects supporting OpenAI.
  - SpringAI applications can run as part of a web application, but these exercises are built to avoid that extra step.
  - Note: The retry settings will override the `ChatClient`'s default settings.  You are likely to experience errors while you learn the API's usage, and we don't want you to experience unnecessary delays or expense.  
  - We could store the API key value in this file, but this would be a security risk if we were to ever distribute this file.  Setting this value by environment variable is safer.    
  - Check everithing is ok by running main application

## Explore Spring AI's with ChatClient
- Add following code into com.synergetics following code
```
@Component
@Profile("azureopenai")
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
    - Note: the @Profile annotation will be useful later when we want our application to switch between OpenAI, Azure, Ollama, etc
    - Configure ChatClient
    - Add a call method.
        - Define a String parameters for the user input / query.
        - Use the client to call the Foundational Model.
        - prompt() creates a prompt to pass
        - user() sets the "user" message. Pass the given input String.
        - call() invokes the model. It returns a CallResponse.
        - content() is a simple means of extracting String content from the response. 
        

