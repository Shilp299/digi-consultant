package application.configurarions;

/**
 * Created by gipai on 9/26/2017.
 */
import application.auth.roles.RoleRepository;
import application.auth.user.UserRepository;
import application.auth.user.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ComponentScan({"application.auth"})
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final RoleRepository roleRepository;

    public WebSecurityConfig(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }


    @Bean
    public UserDetailsService mongoUserDetails() {
        return new UserServiceImpl(userRepository, roleRepository);
    }

  /*  @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http
                .authorizeRequests()
                .antMatchers("/", "/index").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .permitAll();

        http.csrf().disable().authorizeRequests()
                .anyRequest().authenticated()
                .and().httpBasic();


    }*/

    @Override
    protected void configure(HttpSecurity http) throws Exception {

     /*   http.authorizeRequests()
                .antMatchers("/public").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .permitAll()
                .and()
                .logout()
                .permitAll();*/
        http.csrf().disable();
        http.authorizeRequests()
                .anyRequest().authenticated()
                .and().httpBasic();



    }

  /*  @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("user").password("password").roles("USER");
        auth.inMemoryAuthentication().withUser("admin").password("password").roles("ADMIN");
    }*/

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        UserDetailsService userDetailsService = mongoUserDetails();
        auth.userDetailsService(userDetailsService);
    }

}