package com.cybertek.implementation;

import com.cybertek.entity.User;
import com.cybertek.exception.EcommerceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class SecurityService implements UserDetailsService {

    private final UserServiceImpl userServiceImpl;

    public SecurityService(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        User user = null;
        try {
            user = currentUser(s);
        } catch (EcommerceException e) {
            e.printStackTrace();
        }

        if (user == null){
            throw new UsernameNotFoundException("User not found!");
        }

        return org.springframework.security.core.userdetails.User.withUsername(user.getUserName())  // you should use this part inside your JWT
                .password(user.getPassword()).authorities(new ArrayList<>())
                .build();

    }

    private User currentUser(String s) throws EcommerceException {

        return s.contains("@") ? userServiceImpl.readByEmail(s) : userServiceImpl.readByUsername(s);

    }
}
