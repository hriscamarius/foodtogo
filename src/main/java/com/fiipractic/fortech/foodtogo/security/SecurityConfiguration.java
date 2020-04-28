package com.fiipractic.fortech.foodtogo.security;

import com.fiipractic.fortech.foodtogo.entity.Role;
import com.fiipractic.fortech.foodtogo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {

        String loginPage = "/login";


        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/resources/**", "/registerVendor", "/registerCustomer").permitAll()
                .antMatchers("/admin/**").hasAuthority(Role.ADMIN)
                .antMatchers("/vendor/**").hasAuthority(Role.VENDOR)
                .antMatchers("/customer/**").hasAuthority(Role.CUSTOMER)
                .anyRequest().permitAll()
                .and()
                .formLogin().loginPage(loginPage)
                .failureUrl("/login?error=true")
                .and()
                .exceptionHandling().accessDeniedPage("/403");

    }

   /* @Override
    public void configure(WebSecurity web){
        web
                .ignoring()
                .antMatchers("/resource/**","/static/**", "/css/**", "/js/**", "/images/**");
    }
*/
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}