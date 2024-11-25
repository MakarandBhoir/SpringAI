# Spring AI - Setup for OpenAI
You will need an account on OpenAI to perform any practical

- Click [here](https://platform.openai.com/signup) to Login or signup for an account.
- Click [here](https://platform.openai.com/api-keys) to generate API key or to regenerate API keys.
- Create secret key. COPY the generated key value.
- Set an environment variable named SPRING_AI_OPENAI_API_KEY using this value. 
    - On Windows you can run:
        ```
        *setx SPRING_AI_OPENAI_API_KEY "YOUR-KEY-GOES-HERE"*
        ```
    - On Linux or Mac you can run:
        ``` 
        *export SPRING_AI_OPENAI_API_KEY="YOUR-KEY-GOES-HERE"*
        ```
- Restart your IDE after setting an environment variable.

1. [Open AI lab setup](Lab01.md)
1. [Azure Open AI lab setup](Lab02.md)