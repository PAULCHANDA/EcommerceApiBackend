package com.paul.ecommerce.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MyUserDetailsService implements UserDetailsService {
    private final JdbcTemplate jdbcTemplate;

    public MyUserDetailsService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        String sql = "SELECT userName, password FROM users WHERE userName =?";
        return jdbcTemplate.queryForObject(sql, new Object[]{userName},  (rs, rowNum) -> {
            String user = rs.getString("userName");
            String password = rs.getString("password");
            return new User(user, password, new ArrayList<>());
        });

    }


}
