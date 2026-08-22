## Basic Authentication

```textmate
    postman/client -> made http request
            ↓
          Tomcat
            ↓
    Servlet Filter Chain -> Where security filter chain gets registered into servlet filter container, 
            |               why? 
            |               - {
            |               The Servlet container provides the mechanism that runs filters **before reaching dispatcher servlet**} -> 
            |               Within Servlet Container we have --> securityFilterChain --> Security filter chain gets processed due to servlet filter container. 
            |                                   ↓
            |                           httpBasic(Customer.withDefaults()); configuration tells:
            |                            that it is a "BasicAuthenticationFilter".
            ↓
     Spring Security -> Security Filter Chain, here you will defined basic auth implementation, 
            |            and then it will get registered in the servlet filter container.
            ↓
  BasicAuthenticationFilter
            ↓
   AuthenticationManager
            ↓
      ProviderManager
            ↓
    DaoAuthenticationProvider -> {
            |                       UserDetailsService loads the user.
            |                       Loads the user -> loadUserByUsername -> Username -> Find user -> Return UserDetails
            |
            |                       DaoAuthenticationProvider authenticates the user.
            |                       How?
            |                        {
            |                            submitted password
            |                                   ↓
            |                            stored password from UserDetails
            |                                    ↓
            |                            PasswordEncoder.matches()
            |                                    ↓
            |                            TRUE → authenticated
            |                            FALSE → AuthenticationException
            |                       }
            |                   
            |                   
            |                   
            |                   
            ↓
    if Authentication SUCCESS
            ↓
     SecurityContext
            ↓
      Authorization
            ↓
        Controller
```
