# Debug encountered.

### 1. 200 OK but Empty Response
```textmate

The end point.
@GetMapping("/hello")
public String greet() {
    System.out.println("🔥 Greet controller executed");
    return "Hello world";
}

Permitted through the security checkpoint -> requestMatcherS("/users/hello")permitAll();

But when hit the api, returns 200 OK, but no response body why?

Reason of bug.
Root cause discovered

Stale compiled classes / stale target artifacts.
The running JVM wasn't executing the **latest version of the controller source.**

Steps to recover.
1. Do mvn clean through mvn plugin window installs -> lifecycle -> clean
2. And then rerun your project.
```

