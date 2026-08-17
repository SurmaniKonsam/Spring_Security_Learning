## Basic Authentication

```textmate
    postman/client -> made http request
            ↓
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
    DaoAuthenticationProvider -> uses passwordEncoder to verifies the password -> 
            |                   code: daoAuthenticationProvider.setPasswordEncoder(passwordEncoder); 
            |                   userDetailsService, with the help of loadUserByUsername method -> 
            |                            fetches the user from the db, 
            |                   validates it with the user extracted via 
            |                   BasicAuthenticationFilter and then validates it.
            ↓
    if Authentication SUCCESS
            ↓
     SecurityContext
            ↓
      Authorization
            ↓
        Controller

```
