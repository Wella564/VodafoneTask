package com.task.Vodafone.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration//Indicates that the class has bean methods that can be used for DI
@EnableWebSecurity//Like autowire but for websecurity so beans get wired to it
public class SpringSecurity {

    @Autowired
    private UserDetailsService userDetailsService;
    //During the login process, Spring Security will use the UserDetailsService (CustomUserDetailsService implementation)
    // to load user details based on the provided username (email in this case).

    @Bean// Marks this method as a creator of beans to be handled by spring
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
     //bean name is PasswordEncoder and bean object is the returned new BCryptPasswordEncoder
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests((authorize) ->

                        authorize
                                .requestMatchers("/register/**","/index","/").permitAll()
                                .requestMatchers("/loggedin/**").authenticated()
                                //

                ).formLogin(
                        form -> form
                                .loginPage("/login")
                                .loginProcessingUrl("/login")
                                .defaultSuccessUrl("/loggedin",true)
                                .permitAll()
                ).logout(
                        logout -> logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .permitAll()
                                .logoutSuccessUrl("/").permitAll()


                );
        return http.build();
    }


}
