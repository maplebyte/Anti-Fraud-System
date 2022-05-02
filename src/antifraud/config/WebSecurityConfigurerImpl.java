package antifraud.config;

import antifraud.auth.RestAuthenticationEntryPoint;
import antifraud.auth.UserDetailsServiceImpl;
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
import org.springframework.security.web.AuthenticationEntryPoint;

@EnableWebSecurity
public class WebSecurityConfigurerImpl extends WebSecurityConfigurerAdapter {

    private final UserDetailsServiceImpl userDetailsService;

    @Autowired
    public WebSecurityConfigurerImpl(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)// user store 1
                .passwordEncoder(getEncoder());
    }

    @Bean
    public PasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();
    }

    public void configure(HttpSecurity http) throws Exception {
        http.httpBasic()
                .authenticationEntryPoint(authenticationEntryPoint()) // Handles auth error
                .and()
                .csrf().disable().headers().frameOptions().disable() // for Postman, the H2 console
                .and()
                .authorizeRequests() // manage access
                .antMatchers("/actuator/shutdown").permitAll() // needs to run test
                .antMatchers(HttpMethod.POST,"/api/auth/user").permitAll()
                .antMatchers(HttpMethod.GET, "/api/auth/list").authenticated()
                .antMatchers(HttpMethod.DELETE, "/api/auth/user").authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS); // no session
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new RestAuthenticationEntryPoint();
    }


}

