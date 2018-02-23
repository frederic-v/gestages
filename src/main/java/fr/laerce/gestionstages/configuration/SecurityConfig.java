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
                    //.antMatchers("/discipline/**","/division/**","/individu/**","/niveau/**").hasRole("USER") user all access
                    .antMatchers("/discipline/liste","/division/liste","/individu/liste","/niveau/liste").hasRole("USER") // on met le plus restreint en premier
                    .antMatchers("/admin/**","/upload","/discipline/**","/division/**","/individu/**","/niveau/**").hasRole("ADMIN") // on met le moins restreint en dernier



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


