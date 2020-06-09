package com.github.ternyx.services.impl;

import java.util.Optional;
import com.github.ternyx.models.HospitalUserPrincipal;
import com.github.ternyx.models.User;
import com.github.ternyx.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * HospitalUserDetailsService
 */
@Service
public class HospitalUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepo.findByUsername(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }
        return new HospitalUserPrincipal(user.get());
    }
}
