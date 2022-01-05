package cz.cvut.fel.ear.lingo.config;

import cz.cvut.fel.ear.lingo.security.Oauth2AuthenticationSuccessHandler;
import cz.cvut.fel.ear.lingo.security.SecurityConstants;
import cz.cvut.fel.ear.lingo.security.jwt.JwtAuthenticationEntryPoint;
import cz.cvut.fel.ear.lingo.security.jwt.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

@Configuration
@EnableWebSecurity
@PropertySource("classpath:application.properties")
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String[] COOKIES_TO_DESTROY = {
            SecurityConstants.SESSION_COOKIE_NAME,
            SecurityConstants.REMEMBER_ME_COOKIE_NAME
    };

    private final AuthenticationFailureHandler authenticationFailureHandler;
    private final AuthenticationSuccessHandler authenticationSuccessHandler;
    private final LogoutSuccessHandler logoutSuccessHandler;
    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationEntryPoint unauthorizedHandler;
    private final Oauth2AuthenticationSuccessHandler oauth2AuthenticationSuccessHandler;

    @Autowired
    public SecurityConfig(AuthenticationFailureHandler authenticationFailureHandler,
                          @Qualifier("successHandler")AuthenticationSuccessHandler authenticationSuccessHandler,
                          LogoutSuccessHandler logoutSuccessHandler,
                          AuthenticationProvider authenticationProvider,
                          JwtAuthenticationEntryPoint unauthorizedHandler,
                          @Qualifier("oauth2authSuccessHandler") Oauth2AuthenticationSuccessHandler oauth2AuthenticationSuccessHandler) {
        this.authenticationFailureHandler = authenticationFailureHandler;
        this.authenticationSuccessHandler = authenticationSuccessHandler;
        this.logoutSuccessHandler = logoutSuccessHandler;
        this.authenticationProvider = authenticationProvider;
        this.unauthorizedHandler = unauthorizedHandler;
        this.oauth2AuthenticationSuccessHandler = oauth2AuthenticationSuccessHandler;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeRequests()
                .anyRequest().permitAll()
                .and().exceptionHandling()
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                .and().headers().frameOptions().sameOrigin()
                .and().authenticationProvider(authenticationProvider)
                .formLogin()
                .failureUrl("/users/login?error=true")
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)
                .loginProcessingUrl(SecurityConstants.SECURITY_CHECK_URI)
                .usernameParameter(SecurityConstants.USERNAME_PARAM)
                .passwordParameter(SecurityConstants.PASSWORD_PARAM)
                .and().oauth2Login().loginPage("/users/login").successHandler(oauth2AuthenticationSuccessHandler)
                .and().logout()
                .invalidateHttpSession(true)
                .deleteCookies(COOKIES_TO_DESTROY)
                .logoutUrl(SecurityConstants.LOGOUT_URI)
                .logoutSuccessHandler(logoutSuccessHandler)
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS).maximumSessions(1);
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}