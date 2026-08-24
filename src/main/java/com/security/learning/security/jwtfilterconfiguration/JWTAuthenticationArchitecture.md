## JWT Architecture.

### Above all always remember this?
```textmate
Your JWT logic needs to happen before the request reaches the controller:
```
```textmate
    POST /login -> you need to have csrf disabled, 
       |            for this to pass successfully from the authentication gate. 
       |            Just for practice purpose.
       │
       ▼
    Username + Password
       │
       ▼
    Authentication -> AuthenticationManager used here, 
       |               method to be used UsernamePasswordAuthenticationToken
       │
       ├── User found?
       ├── Password correct?
       └── Account valid?
       │
       ▼
    ✅ Authentication SUCCESS
       │
       ▼
    Create JWT/Generate JWT -> to be called jwt token generation service or bean. 
       │
       ▼
    Return JWT to client -> { 
                                "Server, our application will generate jwt token, and then shall it give
                                it to back to the client for jwt filter authentication, 
                                The main questions that now sits is how shall the client, put the bearer
                                token up in the authorization header, we are doing it manually, but 
                                does the client site does it implicitly or automatically.
                                From the authorization header shall the extraction of the token, 
                                claims ---> user information shall happen."
                            }               
                                            |
                                            |
       _____________________________________| 
       |  
       ▼                                  
    [Now from client till the controller the flow goes on like this.]
        CLIENT
          │
          │ Authorization: Bearer eyJ...
          ▼
        ┌─────────────────────────────┐
        │ SecurityFilterChain         │
        │                             │
        │ JwtAuthenticationFilter     │
        └──────────────┬──────────────┘
                       │
                       ▼
                Extract JWT -> eyJ...
                       │
                       ▼
               Validate JWT -> eyJ...  -> Validation, and extraction is the real logic here now.
                       │
               ┌───────┴────────┐
               │                │
            INVALID           VALID -> How does it get valid -> 
               │                │
               ▼                ▼
           401/Reject     Extract claims -> why is claims again extracted?
                               │
                               ▼
                         Create Authentication -> Authentication of what? User is already authenticated here.
                               │
                               ▼
                       SecurityContext -> Type of authentication will be stored in SecurityContext, i know it, but why?
                               │
                               ▼
                      Authorization -> PreAuthorized, postAuthorized, permissions, hell lot of thing is going here?
                               │
                      ┌────────┴────────┐
                      │                 │
                    allowed           denied
                      │                 │
                      ▼                 ▼
                  Controller           403


```