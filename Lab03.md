# Spring with Azure OpenAI
---
### Pre-requisite

Note that the exact screen flow to perform these steps may vary over time as AWS modifies their user interface.

1. Sign-in to an AWS Account as root user or user who has admin privelege

    3. Create an "IAM user" within this account. 
        - Give your user the `AmazonBedrockFullAccess` policy.  This follows the _grant least privilege_ security approach to allow only what is needed for this lab.
        - Be sure to "Download .csv file" containing the user's password.  You may need this later to sign into the console.
    4. Select the new user from the user list and access the security credentials.  Create an access key.  Choose any use case, but ignore the recommended alternatives.  Be sure to download the resulting CSV file; this contains the Access Key ID and Secret access key "credentials" that we need.  
    - If you loose this file don't worry; just repeat the steps to create a new key.

1. Set Credentials

    Next, provide the access key ID and secret key to our software.  
    1. Set the following environment variables using the values obtained from the CSV file.  On Windows you can run: 
        ```
        setx SPRING_AI_BEDROCK_AWS_SECRET_KEY "YOUR_SECRET_KEY"
        ```
        On Linux or Mac you can run:   
        ```
        export SPRING_AI_BEDROCK_AWS_SECRET_KEY="YOUR_SECRET_KEY"
        ```

1. Enable Bedrock Models

    When using Amazon Bedrock, you must _enable_ the models you wish to use. This is easily done via the AWS Management Console.

    Note that the exact steps may vary over time as AWS modifies their user interface.

    5. From the AWS Management Console main page, select the region you wish to use from the drop-down list in the upper right.  The solution code will use _us-west_2_, but feel free to choose another. 
    1. In the search box on the top of the console, type **bedrock** and select the Amazon Bedrock service.
    1. Open the left menu if it is closed (click icon with three horizontal lines). From the menu select **Model access**.
    1. Click **Modify model access**.
    1. For the labs presented in this course, we recommend you select and enable the following models:
    * **Amazon - Titan Text G1 - Express**
    * **Amazon - Titan Embeddings G1 - Text**
    * **Anthropic - Claude V2**
    * **Anthropic - Claude 3 Sonnet**  (anthropic.claude-3-sonnet-20240229-v1:0)
    - Note: you may enable other models if you like.  Generally there is little or no charge to enable a specific model, only acceptance of a license agreement.  The _Titan_ models from Amazon are relatively inexpensive.
    - Over time, the available Bedrock models and the defaults used in Spring AI change.  You may encounter cases where you need to enable and specify models not on this list.
    10. Click **Submit**.  It may take a few moments for the model to become active.

    Note:  [Amazon Bedrock pricing](https://aws.amazon.com/bedrock/pricing/) for chat/text is based on input and output tokens, and varies depending on the model chosen.  Tokens currently cost between $0.00015 and $0.02 per thousand input tokens, $0.00125 and $0.024 per thousand output tokens.  It is always a good idea to double-check the pricing page when using a cloud provider.


---
 
## Generate the Project Structure using Spring Initializer
1. Click [here](https://start.spring.io), to create a new Spring Boot Project.
    - Use Maven for this lab.
    - Use the latest stable releases of Boot and Java.
    - Use JAR packaging.
    - Artifact name as lab03-bedrockai
    - Search for Bedrock AI dependency and select it
1. Generate. Find the downloaded zip and extract it. Copy the lab03-bedrockai
1. Import project in your IDE
1. Basic Configuration and Test
    - Rename application.properties file to application.yml
    - Add following properties to yaml file
    ```
    spring:
      application.name: Lab03 AWS BedrockAI
      main.web-application-type: none     # Do not start a web server.
      
      ai:
        retry:
          max-attempts: 1    # Maximum number of retry attempts.
        bedrock:
          aws.region: us-west-2
          titan:
            chat:
              enabled: true
    ```    
    
  - The `bedrock.titan.chat.enabled` setting tells Spring Boot to specifically autoconfigure objects supporting _Titan_ model.
  - SpringAI applications can run as part of a web application, but these exercises are built to avoid that extra step.
  - Note: The retry settings will override the `ChatClient`'s default settings.  You are likely to experience errors while you learn the API's usage, and we don't want you to experience unnecessary delays or expense.  
  - We could store the API key value in this file, but this would be a security risk if we were to ever distribute this file.  Setting this value by environment variable is safer.    
  - Check everithing is ok by running main application

## Explore Spring AI's with ChatClient
- Add following code into com.synergetics following code
```
@Component
//@Profile("bedrockai")
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
    - Note: the @Profile annotation will be useful later when we want our application to switch between OpenAI, AWS, Ollama, etc
    - Configure ChatClient
    - Add a call method.
        - Define a String parameters for the user input / query.
        - Use the client to call the Foundational Model.
        - prompt() creates a prompt to pass
        - user() sets the "user" message. Pass the given input String.
        - call() invokes the model. It returns a CallResponse.
        - content() is a simple means of extracting String content from the response. 
        

