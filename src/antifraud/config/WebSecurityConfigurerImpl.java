//package antifraud.config;
//
//
//import antifraud.auth.UserDetailsServiceImpl;
//import antifraud.security.RestAuthenticationEntryPoint;
//import antifraud.user.Role;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.AuthenticationEntryPoint;
//
//@EnableWebSecurity
//@Configuration
//@Slf4j
//public class WebSecurityConfigurerImpl extends WebSecurityConfigurerAdapter {
//
//    private final UserDetailsServiceImpl userDetailsService;
//
//    @Autowired
//    public WebSecurityConfigurerImpl(UserDetailsServiceImpl userDetailsService) {
//        this.userDetailsService = userDetailsService;
//        log.info(userDetailsService.toString());
//    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService)// user store 1
//                .passwordEncoder(getEncoder());
//        log.info(auth.toString());
//    }
//
//    @Bean
//    public PasswordEncoder getEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//
////    @Override
////    protected void configure(HttpSecurity http) throws Exception {
////        http.authorizeRequests() // manage access
////                .mvcMatchers("/actuator/shutdown").permitAll() // needs to run test
////                .mvcMatchers(HttpMethod.POST, "/api/auth/user").permitAll()
////                .mvcMatchers(HttpMethod.GET, "/api/auth/list").hasAnyRole(Role.ADMINISTRATOR.name(), Role.SUPPORT.name())
////                .mvcMatchers(HttpMethod.DELETE, "/api/auth/user/*").hasRole(Role.ADMINISTRATOR.name())
////
////                .mvcMatchers(HttpMethod.POST, "/api/antifraud/transaction").hasRole(Role.MERCHANT.name())
////                .mvcMatchers(HttpMethod.PUT, "/api/auth/access").hasRole(Role.ADMINISTRATOR.name())
////                .mvcMatchers(HttpMethod.PUT, "/api/auth/role").hasRole(Role.ADMINISTRATOR.name())
////
////                .and()
////                .httpBasic()
////                .authenticationEntryPoint(authenticationEntryPoint()) // Handles auth error
////                .and()
////                .csrf().disable().headers().frameOptions().disable() // for Postman, the H2 console
////
////                .and()
////                .sessionManagement()
////                .sessionCreationPolicy(SessionCreationPolicy.STATELESS); // no session
////    }
////
//
//    @Override
//    public void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests() // manage access
//                .mvcMatchers(HttpMethod.DELETE, "/api/auth/user/**").hasRole(Role.ADMINISTRATOR.toString())
////                .mvcMatchers(HttpMethod.GET, "/api/auth/list").hasAnyRole(Role.ADMINISTRATOR.getRoleWithPrefix(),
////                        Role.SUPPORT.getRoleWithPrefix())
//                .mvcMatchers(HttpMethod.GET, "/api/auth/list").hasAnyRole(Role.ADMINISTRATOR.name(), Role.SUPPORT.name())
//                .mvcMatchers(HttpMethod.POST, "/api/antifraud/transaction").hasRole(Role.MERCHANT.toString())
//                .mvcMatchers(HttpMethod.PUT, "/api/auth/access").hasRole(Role.ADMINISTRATOR.toString())
//                .mvcMatchers(HttpMethod.PUT, "/api/auth/role").hasRole(Role.ADMINISTRATOR.toString())
//                .mvcMatchers(HttpMethod.POST, "/api/auth/user").permitAll()
//                .mvcMatchers( "/actuator/shutdown").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .httpBasic()
//                .authenticationEntryPoint(authenticationEntryPoint()) // Handles auth error
//                .and()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // no session
//                .and()
//                .csrf().disable().headers().frameOptions().disable(); // for Postman, the H2 console
//    }
//
//
//    @Bean
//    public AuthenticationEntryPoint authenticationEntryPoint() {
//        return new RestAuthenticationEntryPoint();
//    }
//
//
//}
//


//
//import antifraud.auth.RestAuthenticationEntryPoint;
//import antifraud.auth.UserDetailsServiceImpl;
//import antifraud.utils.Role;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//@EnableWebSecurity
//public class WebSecurityConfigurerImpl extends WebSecurityConfigurerAdapter {
//    @Autowired
//    private UserDetailsServiceImpl userDetailedService;
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//                .userDetailsService(userDetailedService)
//                .passwordEncoder(getEncoder());
//    }
////
////    @Override
////    public void configure(HttpSecurity http) throws Exception {
////        http.authorizeRequests() // manage access
////                .mvcMatchers(HttpMethod.DELETE, "/api/auth/user/**")
////                .hasRole(Role.ADMINISTRATOR.toString())
////
////                .mvcMatchers(HttpMethod.GET, "/api/auth/list")
////                .hasAnyRole(Role.ADMINISTRATOR.toString(), Role.SUPPORT.toString())
////
////                .mvcMatchers(HttpMethod.POST, "/api/antifraud/transaction")
////                .hasRole(Role.MERCHANT.toString())
////
////                .mvcMatchers(HttpMethod.PUT, "/api/auth/access")
////                .hasRole(Role.ADMINISTRATOR.toString())
////
////                .mvcMatchers(HttpMethod.PUT, "/api/auth/role")
////                .hasRole(Role.ADMINISTRATOR.toString())
////
////                .mvcMatchers("api/antifraud/suspicious-ip/**")
////                .hasRole(Role.SUPPORT.toString())
////
////                .mvcMatchers("api/antifraud/stolencard/**")
////                .hasRole(Role.SUPPORT.toString())
////
////                .mvcMatchers(HttpMethod.PUT, "/api/antifraud/transaction")
////                .hasRole(Role.SUPPORT.toString())
////
////                .mvcMatchers(HttpMethod.GET, "/api/antifraud/history")
////                .hasRole(Role.SUPPORT.toString())
////
////                .mvcMatchers(HttpMethod.GET, "/api/antifraud/history/**")
////                .hasRole(Role.SUPPORT.toString())
////
////                .mvcMatchers(HttpMethod.POST, "/api/auth/user").permitAll()
////                .mvcMatchers( "/actuator/shutdown").permitAll()
////                .mvcMatchers("/h2").permitAll()
////                .anyRequest().authenticated()
////                .and()
////                .httpBasic()
////                .authenticationEntryPoint(new RestAuthenticationEntryPoint()) // Handles auth error
////                .and()
////                .sessionManagement()
////                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // no session
////                .and()
////                .csrf().disable().headers().frameOptions().disable(); // for Postman, the H2 console
////    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests() // manage access
//                .mvcMatchers("/actuator/shutdown").permitAll() // needs to run test
//                .mvcMatchers(HttpMethod.POST, "/api/auth/user").permitAll()
//                .mvcMatchers(HttpMethod.GET, "/api/auth/list").hasAnyRole(Role.ADMINISTRATOR.name(), Role.SUPPORT.name())
//                .mvcMatchers(HttpMethod.DELETE, "/api/auth/user/*").hasRole(Role.ADMINISTRATOR.name())
//
//                .mvcMatchers(HttpMethod.POST, "/api/antifraud/transaction").hasRole(Role.MERCHANT.name())
//                .mvcMatchers(HttpMethod.PUT, "/api/auth/access").hasRole(Role.ADMINISTRATOR.name())
//                .mvcMatchers(HttpMethod.PUT, "/api/auth/role").hasRole(Role.ADMINISTRATOR.name())
//
//                .and()
//                .httpBasic()
//                .authenticationEntryPoint(authenticationEntryPoint()) // Handles auth error
//                .and()
//                .csrf().disable().headers().frameOptions().disable() // for Postman, the H2 console
//
//                .and()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS); // no session
//    }
//
//    @Bean
//    public RestAuthenticationEntryPoint authenticationEntryPoint() {
//        return new RestAuthenticationEntryPoint();
//    }
//
//
//    @Bean
//    public PasswordEncoder getEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//}


/////////////////////

package antifraud.config;

import antifraud.auth.RestAuthenticationEntryPoint;
import antifraud.auth.UserDetailsServiceImpl;
import antifraud.utils.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class WebSecurityConfigurerImpl extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsServiceImpl userDetailedService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailedService)
                .passwordEncoder(getEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests() // manage access
                .mvcMatchers("/actuator/shutdown").permitAll() // needs to run test
                .mvcMatchers(HttpMethod.POST, "/api/auth/user").permitAll()
                .mvcMatchers(HttpMethod.GET, "/api/auth/list").hasAnyRole(Role.ADMINISTRATOR.name(), Role.SUPPORT.name())
                .mvcMatchers(HttpMethod.DELETE, "/api/auth/user/*").hasRole(Role.ADMINISTRATOR.name())
                .mvcMatchers(HttpMethod.POST, "/api/antifraud/transaction").hasRole(Role.MERCHANT.name())
                .mvcMatchers(HttpMethod.PUT, "/api/auth/access").hasRole(Role.ADMINISTRATOR.name())
                .mvcMatchers(HttpMethod.PUT, "/api/auth/role").hasRole(Role.ADMINISTRATOR.name())
                .and()
                .httpBasic()
                .authenticationEntryPoint(authenticationEntryPoint()) // Handles auth error
                .and()
                .csrf().disable().headers().frameOptions().disable() // for Postman, the H2 console
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS); // no session
    }

    @Bean
    public RestAuthenticationEntryPoint authenticationEntryPoint() {
        return new RestAuthenticationEntryPoint();
    }


    @Bean
    public PasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();
    }
}
