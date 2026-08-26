## JWT Token generation complete.
```textmate
Most important question
1 -> What does addFilterBefore does, and why did we put jwtFilter reference before the **_"UsernamePasswordAuthenticationFilter"_**? done
2 -> What does filterChain.doFilter() does? done
3 -> What does SecurityContextHolder() does? **_most important_**
4 -> Make a note of how and when to expire our custom generated token using Date ?
    -> Its easy, done -> issue date will be the current date -> new Date()
    -> expiry date will be -> new Date(System.currentMillisec + 1000L*60*60*5); -> 
    -> means 5 hrs, **_you can remember -> 1000L*60*60 -> 1 hr_**
        -> This is 24 hrs -> "1000L * 60 * 60 * 24"
5 -> Why JwtFilter extends OncePerRequestFilter abstract class?

```

### 1. What does filterChain.doFilter() really does?
```textmate
- filterChain.doFilter(request,response) simply says -> "Continue the request through the next filter/ or 
   "filterChain.doFilter() is the mechanism by which the current filter hands the request onward to the rest of the processing pipeline/or remaining spring security filter chain."
        JwtFilter -> jwtFilter method body.
           ↓
        doFilter() -> here is where we will come -> it will tell to continue to the remaining filter process
           ↓
        next filter -> executed
           ↓
        next filter -> executed
           ↓
        DispatcherServlet
           ↓
        Controller -> from here token generated.
        
        
- If the authorization header is null or empty, which we have implemented within the block
    if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
    
    
- "return" statement here says -> "JwtFilter's own doFilterInternal() method is finished. Don't execute any more code inside this method."


- What happens **_without_** filterChain.doFilter()?
    - It would not continue to next filters -> which implies it would never reach dispatcher servlet, and it would never reach controller,
    and hence our token will never get generated.

```


### 2. What does addFilterBefore does, and why did we put jwtFilter reference before the **_"UsernamePasswordAuthenticationFilter"_**?
```textmate
- It literally reads "put my jwtFilter before Spring Security's UsernamePasswordAuthenticationFilter in the security filter chain."
- we want our JWT filter to get the first opportunity to inspect a Bearer token.
- JWT processing should get a chance to happen before that particular filter, why?
    Because, 
            so that when a request already carries: -> token
        
            Authorization: Bearer eyJ...
            
            our JWT filter can:             
                    JWT
                     ↓
                    verify signature
                     ↓
                    extract username
                     ↓
                    create Authentication
                     ↓
                    SecurityContextHolder
                    
            
```


### 3. Why jwtFilter extends OncePerRequestFilter?
```textmate
- Your JWT logic needs to happen before the request reaches the controller.
- OncePerRequestFilter gives you a convenient Spring-provided base implementation that guarantees your jwt 
filter logic runs once per request dispatch.
```


### 4. Why can't we just add all the roles, permissions, required configuration up in the claims, instead of SecurityContextHolder()?

### 4. What does SecurityContextHolder() does? **_most important_**
```textmate
    - Spring Security has its own object for saying:
          Current request
              ↓
          belongs to
              ↓
          claims.getSubject() --> userName/to this user.
        
          That object is: Authentication/UsernamePasswordAuthenticationToken.
    - SecurityContextHolder holds the Authentication object, and Spring Security uses that Authentication object
      during authorization checks..
          
```
