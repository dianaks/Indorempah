package com.blibli.future.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

/**
 * Created by Fransiskus A K on 23/10/2016.
 */

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/register")
                    .permitAll()
                .antMatchers("/user/**")
                    .access("hasRole('ROLE_USER')")
                .antMatchers("/merchant/**")
                    .access("hasRole('ROLE_MERCHANT')")
                .anyRequest()
                    .permitAll()
                    .and()
                .formLogin().loginPage("/login")
                    .successForwardUrl("/login/process")
                    .permitAll()
                    .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .permitAll()
                    .and()

        ;

    }
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery("select username, password, enabled from indorempah_user where username=?")
                .authoritiesByUsernameQuery("select username, role from user_role where username=? ") ;
    }
}

