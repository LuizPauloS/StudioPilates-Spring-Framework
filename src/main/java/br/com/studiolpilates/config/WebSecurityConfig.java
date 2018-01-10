package br.com.studiolpilates.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private DataSource dataSource;
    //private UserDetailsService userDetailsService;

    @Autowired
    public WebSecurityConfig(BCryptPasswordEncoder bCryptPasswordEncoder, DataSource dataSource, UserDetailsService userDetailsService) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.dataSource = dataSource;
        //this.userDetailsService = userDetailsService;
    }

//	 public void configAuthentication(AuthenticationManagerBuilder auth)throws Exception {
//	 			auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
//	 }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .usersByUsernameQuery("select username, password, status from usuario where username=?")
                .authoritiesByUsernameQuery("select u.username, g.descricao from usuario u inner join "
                        + "grupo_usuario ur on(u.id=ur.id_usuario) inner join grupo g on(ur.id_grupo=g.id) where u.username=?")
                .dataSource(dataSource).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/layout/**", "/css/**", "/js/**", "/images/**", "/fonts/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/pacientes/novo", "/pagamentos/novo", "/pacientes/paciente/**", "/pagamentos/pagamento/**")
                .hasRole("ADMIN").antMatchers("/relatorios/**").hasRole("ADMIN").anyRequest().authenticated().and()
                .formLogin().loginPage("/login").usernameParameter("username").passwordParameter("password").permitAll()
                .and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).invalidateHttpSession(true)
                .permitAll();
    }

}
