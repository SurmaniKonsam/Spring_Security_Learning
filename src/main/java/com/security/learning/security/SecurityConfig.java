package com.security.learning.security;


import com.security.learning.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authorization.method.PostAuthorizeAuthorizationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Registration, and login must be public, or should we say it shouldn't be authenticated.
     * Be mindful, its HttpSecurity not HttpRequest.
     * csrf is disabled here just to check if state changing request can be made.
     * Yes it does, when csrf was put, our post request was successfully made.
     */
    @Bean
    public SecurityFilterChain basicAuth(HttpSecurity httpSecurity) throws Exception{
        HttpSecurity httpSecurity1 = httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers("/users/registerUser").permitAll()
                                .requestMatchers(("/users/csrf")).permitAll()
                                .requestMatchers(("/users/hello")).permitAll()
                                .anyRequest()
                                .authenticated()
                )
                .addFilterBefore(null, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity1.build();
    }


    /**
     * We need this to have it provided to authentication manager.
     * @return
     */
    @Bean
    public UserDetailsService userDetailsService(){
        PostAuthorizeAuthorizationManager postAuthorizeAuthorizationManager;
        return new UserService();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService,PasswordEncoder passwordEncoder){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(daoAuthenticationProvider);
    }

}
