## Interview grade question

```textmate
1. Handle missing/malformed Authorization header
2. Handle expired/invalid JWT cleanly
3. Understand 401 vs 403
4. Understand authorities/roles instead of Collections.emptyList()
5. Avoid authenticating the same request unnecessarily
6. Test the complete flow end-to-end
7. What is an API Key?
```


### 1. What is API Key?
```textmate
- An API key is a secret credential issued to a client/application, which the client sends with its API request to 
identify/authenticate itself.
- Difference between api key and jwt?
    API Key:
            
            Client
              ↓
            same secret key repeatedly
              ↓
            API
    
    JWT: 
        username/password
               ↓
        authentication
               ↓
        JWT issued
               ↓
        client sends JWT
               ↓
        server verifies signature + claims
        
        JWT can carry information such as:
                                            {
                                              "sub": "surmani",
                                              "exp": "...",
                                              "roles": ["USER"]
                                            }
    
    
```