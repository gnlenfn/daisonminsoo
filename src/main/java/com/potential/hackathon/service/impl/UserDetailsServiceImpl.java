package com.potential.hackathon.service.impl;

import com.potential.hackathon.domain.UserDetailsImpl;
import com.potential.hackathon.entity.Users;
import com.potential.hackathon.exceptions.BusinessLogicException;
import com.potential.hackathon.exceptions.ExceptionCode;
import com.potential.hackathon.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService, com.potential.hackathon.service.UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws BusinessLogicException {
        Users users = userRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));

        return new UserDetailsImpl(users);
    }
}
