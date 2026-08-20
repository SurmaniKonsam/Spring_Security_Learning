## JWT Architecture.

```textmate
    POST /login
       │
       ▼
    Username + Password
       │
       ▼
    Authentication
       │
       ├── User found?
       ├── Password correct?
       └── Account valid?
       │
       ▼
    ✅ Authentication SUCCESS
       │
       ▼
    Create JWT 
       │
       ▼
    Return JWT to client -> { 
                                Yes, jwt will be first given to client, 
                                from their the series of authentication will be handled, 
                                until the jwt is expired or the user has logged out.
                            }
                        
    Now from client till the controller the flow goes on like this.
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