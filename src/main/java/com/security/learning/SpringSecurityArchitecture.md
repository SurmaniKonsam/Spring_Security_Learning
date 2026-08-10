## Request To Resources, complete architecture on how spring security comes into picture.

```textmate
       CLIENT
          │
          │ Authorization: Bearer <JWT>
          │ → Sends HTTP request containing JWT
          │   in the Authorization header.
          │
          │ → HTTP Request contains:
          │   Method + URI + Headers + optional Body + HTTP Version
          │
          ▼
        Embedded Tomcat
          │
          │ → Receives HTTP request
          │ → Creates/prepares HttpServletRequest
          │
          ▼
        Servlet Filter Chain
        (Container-level filter pipeline)
          │
          ▼
        Spring SecurityFilterChain
          │
          ├── CORS
          │      → Is this browser origin allowed?
          │
          ├── CSRF
          │      → Is this request protected against CSRF?
          │      → Especially important for session/cookie auth
          │
          ├── JwtAuthenticationFilter ⭐
          │      (extends OncePerRequestFilter)
          │      |
          │      ▼
          │   Extract JWT from Header
          │      │
          │      ├── Read Authorization header
          │      │   from HttpServletRequest
          │      ├── Check "Bearer "
          │      ├── Remove "Bearer "
          │      └── Extract JWT
          │      |
          │      ▼
          │     JWT
          │      │
          │      ├── Header
          │      ├── Payload
          │      │     → Contains claims
          │      │     → sub → username
          │      │     → exp → expiry
          │      │
          │      └── Signature
          │      |
          │      ▼
          │   Validate JWT
          │      │
          │      ├── Validate Signature
          │      │     → Has JWT been tampered with?
          │      │     → Does signature validate with
          │      │       expected key?
          │      │
          │      └── Validate Expiry
          │      |      → Has JWT expired?
          │      |
          │      ▼
          │   Extract Username → Read `sub` from Payload
          │      |
          │      |
          │      ▼
          │   UserDetailsService                               
          │      │
          │      ▼
          │   Load User from DB
          │      |
          │      |
          │      ▼
          │   Create Authentication Object
          │      |
          │      ▼
          │   SecurityContextHolder
          │      └── setAuthentication(...)
          │      |
          │      ▼
          │   ✅ USER IS AUTHENTICATED
          │
          ├── AnonymousAuthenticationFilter
          │      → Handles requests where authentication
          │        was not established
          │
          ├── ExceptionTranslationFilter
          │      → Handles security exceptions
          │        and translates them to HTTP responses
          │        such as 401 / 403
          │
          └── AuthorizationFilter ⭐
                 │
                 │ → Reads Authentication from
                 │   SecurityContext
                 │
                 ├── hasRole()
                 └── hasAuthority()
                        │
                        ▼
                  AUTHORIZATION
                  → Is this authenticated user
                    allowed to access this resource?
                        │
                    ┌───┴───-------------|           
                    │                    │
                   YES                   NO
                    │                    │
                    ▼                    ▼   
             DispatcherServlet      403 Forbidden
                    │
                    ▼
                Controller
                    │
                    ▼
                 Service
                    │
                    ▼
                Repository
                    │
                    ▼
                 Database
                    │
                    ▼
                 Response
                    │
                    ▼
             DispatcherServlet
                    │
                    ▼
               HTTP Response
```