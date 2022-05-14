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
        auth.userDetailsService(userDetailedService)
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
                .mvcMatchers(HttpMethod.POST, "api/antifraud/suspicious-ip", "api/antifraud/stolencard").hasRole(Role.SUPPORT.name())
                .mvcMatchers(HttpMethod.GET, "api/antifraud/suspicious-ip", "api/antifraud/stolencard").hasRole(Role.SUPPORT.name())
                .mvcMatchers(HttpMethod.DELETE, "api/antifraud/suspicious-ip/*", "api/antifraud/stolencard/*").hasRole(Role.SUPPORT.name())
                .mvcMatchers(HttpMethod.GET, "/api/antifraud/history", "/api/antifraud/history/*").hasRole(Role.SUPPORT.name())
                .mvcMatchers(HttpMethod.PUT, "/api/antifraud/transaction").hasRole(Role.SUPPORT.name())
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
