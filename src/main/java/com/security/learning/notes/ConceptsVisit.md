# Concept visit area, if in doubt see this page, and refresh your concepts.

### 1. @Configuration + @Bean -> use via constructor injection or @Autowired
```textmate
@Configuration -> where all beans will sit
      ↓
@Bean method -> Usually its an object returned method
      ↓
Object created
      ↓
IOC Container
      ↓
Bean is now managed by Spring -> Now, all the beans are ready for use. How to use?

Now how they are used?
1. Constructor injection?
    public Controller(UserService userService) {
        this.userService = userService;
    }
        Controller needs UserService
            ↓
        Search IOC Container
                ↓
        Find UserService bean
                ↓
        Inject it
        
2. @Autowired
    private UserService userService;
    

Important caveat
- Let's assume we have registered bean of a child class, which implements in interface.
    - Then by the principle of polymorphism, its is-a relationship.
    - public AuthenticationManager(UserDetailsService service).
        - UserService of which the bean was created, ioc will automatically gets you the bean of UserService
        for the AuthenticationManager because UserDetailsService and UserService have "is-a" relationship.
        - Mean, is-a relationship if is true, child can be created bean.
        
        
- However let's say there are two class which implements the parent class, class A,B implements C,
then the concept of @Qualifier and @Primary comes, which i haven't touched yet.
```