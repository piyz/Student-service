package by.matrosov.studentservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final DataSource dataSource;

    @Autowired
    public SecurityConfiguration(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .usersByUsernameQuery("select username, password, enabled from students where username=?")
                .authoritiesByUsernameQuery("select s.username, r.role_name from students s" +
                        " inner join" +
                        " students_roles sr on s.student_id = sr.student_id" +
                        " inner join roles r on sr.role_id = r.role_id" +
                        " where s.username=?")
                .dataSource(dataSource)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/student/**").hasAnyAuthority("ADMIN", "USER", "MODERATOR")
                .antMatchers("/admin/**").hasAuthority("ADMIN")
                //.antMatchers("/groups/**").hasAuthority("ADMIN")
                .and()
                .formLogin().loginPage("/login").defaultSuccessUrl("/students")
                .and()
                .csrf().disable();
    }
}
