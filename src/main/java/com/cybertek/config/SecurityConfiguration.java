package com.cybertek.config;

import com.cybertek.implementation.SecurityService;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

     /*
     Notes
        AuthenticationManager --- authenticate the user (just think it is similar with formLogin)


    */

    private final SecurityService securityService;

    public SecurityConfiguration(SecurityService securityService) {
        this.securityService = securityService;
    }



    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    private static final String[] permittedUrls ={
            "/authenticate",        // custom
            "/create-user",         // custom
            "/api/p1/**",
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/configuration/security",
            "/forget",               // custom
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/webjars/**",            // external library
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception{

        http
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers(permittedUrls)
                .permitAll()
                //this part is open for all users
                .anyRequest()
                .authenticated();
                // required flow for this part

        /*       and()
                .exceptionHandling()
                .and()
                .sessionManagement()
                // Stateless is common one.Default is always
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        */

       // http.addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);


    }
}
