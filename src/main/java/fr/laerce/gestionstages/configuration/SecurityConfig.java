package fr.laerce.gestionstages.configuration;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/webjars/**","/index").permitAll()
                    .antMatchers("/discipline/**","/division/**","/individu/**","/niveau/**").hasRole("USER")
                    .antMatchers("/admin/**","/upload").hasRole("ADMIN")

                .and()

                .formLogin()
                    .loginPage("/login").failureUrl("/login-error");
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                    .withUser("user").password("{noop}password").roles("USER")
                .and()
                    .withUser("admin").password("{noop}mdp").roles("ADMIN","USER");
    }
}


