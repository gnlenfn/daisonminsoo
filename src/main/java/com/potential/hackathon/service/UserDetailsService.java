package com.potential.hackathon.service;

import com.potential.hackathon.exceptions.BusinessLogicException;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserDetailsService {
    UserDetails loadUserByUsername(String email) throws BusinessLogicException;
}
