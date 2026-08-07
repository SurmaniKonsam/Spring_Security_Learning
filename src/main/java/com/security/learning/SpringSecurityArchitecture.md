## Request To Resources, complete architecture on how spring security comes into picture.

```textmate
                 CLIENT
                    │
        Authorization : Bearer JWT -> Client ***sends the HTTP request containing the 
                    |                      JWT in the Authorization header***.
                    │
                    ▼
             Embedded Tomcat
                    │
                    ▼
           Servlet Filter Chain
         (Container Level Filters)
                    │
                    ▼
        Spring SecurityFilterChain
         (Many Security Filters)
                    │
                    ▼
          JwtAuthenticationFilter extends OncePerRequestFilter
                    |
                    ▼
         Extract JWT from Header
                    │
                    ├── Reads the Authorization header from the
                    │   incoming HttpServletRequest.
                    │
                    ├── Verifies it starts with "Bearer ".
                    │
                    ├── Removes "Bearer ".
                    │
                    └── Extracts the JWT.
                             │
                             ▼
                            JWT
                            │
                            ├── Header
                   |--------├── Payload -> why userName is extracted from payload, because payload contains claims, what is claims, claims means information about the user.
                   |        └── Signature
                   |                 |
                   |                 |
                   |                 ▼
                   |             Validate Signature
                   |             → Has the JWT been tampered with?
                   |             → Is the JWT signed using our server's secret/private key?
                   |                 │
                   |                 ▼
                   |             Validate Expiry
                   |             → Has the JWT expired?
                   |             → (Checks the `exp` claim inside the Payload.)
                   ▼
            Extract Username -> Username is extracted from the payload.
                   │
                   ▼
            UserDetailsService
                   │
            Load User from DB -> → Why load the user from the DB when the JWT already contains the username?
                   │
                   ▼
           Password not checked -> Already authenticated, interesting, let's find out how?
          (Already authenticated
                using JWT)
                    │
                    ▼
        Create Authentication Object
                    │
                    ▼
     SecurityContextHolder.getContext() -> so this is the one which authenticates the user, let's find out how?
          .setAuthentication(...)
                    │
         User is now authenticated
                    │
                    ▼
         Remaining Security Filters -> more filters hahahah?
                    │
                    ▼
            AuthorizationFilter
                    │
             hasRole()
             hasAuthority()
                    │
                Allowed ?
                    │
                 ┌──┴────┐
                 │       │
                Yes      No
                 │       │
                 ▼       ▼
     DispatcherServlet   403 Forbidden
            │
        Controller
            │
         Service
            │
        Repository
            │
        Database
            │
        Response
            │
     DispatcherServlet
            │
       HTTP Response
```