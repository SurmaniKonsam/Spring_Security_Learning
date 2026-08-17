### Raw Data, shall be beautified later
```textmate
SecurityFilterChain
    contains
        many Filters

OncePerRequestFilter
    JWT filter usually extends OncePerRequestFilter
```

### Only, required security filter chain for interview grade.
```textmate
SecurityFilterChain
│
├── CorsFilter
│      → CORS handling
│
├── CsrfFilter
│      → CSRF protection
│
├── UsernamePasswordAuthenticationFilter
│      → username/password authentication
│
├── **BasicAuthenticationFilter**
│      → HTTP Basic authentication
│
├── JwtAuthenticationFilter ⭐
│      → JWT authentication
│
├── ExceptionTranslationFilter
│      → security exception handling
│
└── AuthorizationFilter ⭐
       → authorization / permissions
```

### What does security filter chain really does?
```text
SecurityFilterChain

SecurityFilterChain is not something the request is "called into" like a controller method.
- Spring Security registers its filters which is “Security filter chain” with the Servlet container's filter infrastructure.
So Tomcat effectively says:
- “Before I give this request to the DispatcherServlet, I have these filters that must process it."

And the filter is basic authentication type filter, so now spring knows which filter to process.
    - Tomcat receives the HTTP request → 
    - the request passes through the Servlet Filter Chain → 
    - Spring Security's filters process it. httpBasic() tells Spring Security to enable Basic Authentication. 
            BasicAuthenticationFilter extracts the credentials and starts authentication. 
    - The AuthenticationManager our ProviderManager gives the authentication job to DaoAuthenticationProvider. 
        - AuthenticationManager's job is to coordinate authentication by giving the authentication request to an appropriate AuthenticationProvider.
    - DaoAuthenticationProvider uses UserDetailsService to fetch the user's details and PasswordEncoder to verify the password. 
        How, is it being verified, via the extracted credentials from BasicAuthenticationFilter.
        - If authentication succeeds, Spring stores the Authentication in the SecurityContext, and the request can proceed through authorization to the Controller.

Complete Flow.
Tomcat
  ↓
Servlet Filter Chain
  ↓
Spring Security
  ↓
BasicAuthenticationFilter
  ↓
AuthenticationManager
  ↓
ProviderManager
  ↓
DaoAuthenticationProvider -> uses passwordEncoder to verifies the password -> code: daoAuthenticationProvider.setPasswordEncoder(passwordEncoder); fetches the user from the db, validates it with the user extracted via BasicAuthenticationFilter and then validates it. 
  ↓
if Authentication SUCCESS
  ↓
SecurityContext
  ↓
Authorization
  ↓
Controller

```

### special privileges -> requestMatchers + permitAll -> its like special check in request in the airport.
```textmate
Be mindful, vip is just my way of analogy.
-  vip/very important high class request endpoints -> 
- gets passed with id card containing -> requestMatchers + permitAll() privilege.

requestMatchers is where you will defined the endpoint which requires special privilege or pass
    - httpRequest.authorizeHttpRequest(auth -> auth.requestMatchers("/yourEndpoint/**").permitAll());
    - The requestMatchers endpoint must always start with "/", and you want all the associates of yourEndpoint
        or other remaining endpoints to be passed through after the requestMatcher defined, just add "/**" after the specified endpoint.
        Eg -> requestMatchers("/users/**").permitAll(); -> that's it.
```

### Before jumping into no-auth, basic-auth or other authentication type, know this!!!
```textmate
- Spring Security decides what the server requires; the client decides what it sends.
- the client sends the http request, the credentials required for the authentication form, then spring
security checks for whether the server requires it or not.
```

### What does "basic-auth" authentication type do?
```textmate
    HTTP Basic Authentication is an authentication mechanism where the client sends a "username and 
    password" in the Authorization header using the Basic scheme. Spring Security's 
    BasicAuthenticationFilter extracts those credentials and passes them to the 
    authentication system for verification.
```