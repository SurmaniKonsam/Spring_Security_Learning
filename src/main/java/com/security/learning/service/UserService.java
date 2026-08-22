package com.security.learning.service;

import com.security.learning.entity.Users;
import com.security.learning.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;


public class UserService implements UserDetailsService {

    /**
     * I  need to study this autoWired thing, make it hard to create a bean out of it.
     */
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void saveUser(Users users){
        Users users1 = new Users();
        users1.setUserName(users.getUserName());
        users1.setPassword(passwordEncoder.encode(users.getPassword()));
        System.out.println("isActive value : "+users.isActive());
        users1.setActive(users.isActive());
        usersRepository.save(users1);
    }


    public Users getUser(String userName,boolean isActive){
        return usersRepository.findUserByUserNameAndIsActive(userName,isActive).orElseThrow(()-> new UsernameNotFoundException("User not found"));
    }


    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Users users = getUser(userName,true);

        return User
                .builder()
                .username(users.getUserName())
                .password(users.getPassword())
                .authorities(Collections.emptyList())
                .build();

    }
}
