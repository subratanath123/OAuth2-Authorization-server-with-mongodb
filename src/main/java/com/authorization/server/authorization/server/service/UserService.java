package com.authorization.server.authorization.server.service;

import com.authorization.server.authorization.server.dao.UserDao;
import com.authorization.server.authorization.server.entity.User;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service("UserDetailsService")
public class UserService implements UserDetailsService {

    private static final Logger LOG = org.slf4j.LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        LOG.info("[Load::User] email {}", email);

        User user = userDao.findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("Email/Password mismatch");
        }

        return user;
    }

    public UserDetails save(User user) throws UsernameNotFoundException {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.save(user);
        return user;
    }

}
