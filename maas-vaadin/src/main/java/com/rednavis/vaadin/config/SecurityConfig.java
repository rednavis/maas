package com.rednavis.vaadin.config;

import static com.rednavis.vaadin.util.ConstantUtils.JSESSIONID;
import static com.rednavis.vaadin.util.ConstantUtils.PAGE_LOGIN_URL;
import static com.rednavis.vaadin.util.ConstantUtils.PAGE_ROOT;
import static com.rednavis.vaadin.util.ConstantUtils.PAGE_SIGNOUT;

import com.rednavis.shared.dto.UserRole;
import com.rednavis.shared.util.EnumUtils;
import com.rednavis.vaadin.util.SecurityUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Require login to access internal pages and configure login form.
 */
@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .csrf().disable()
        //.cors().disable()
        //.httpBasic().disable()
        .formLogin().loginPage(PAGE_ROOT + PAGE_LOGIN_URL)
        .and()
        .logout().logoutRequestMatcher(new AntPathRequestMatcher(PAGE_ROOT + PAGE_SIGNOUT))
        .logoutSuccessUrl(PAGE_ROOT + PAGE_LOGIN_URL)
        .invalidateHttpSession(true).deleteCookies(JSESSIONID)
        .and()
        //Register our CustomRequestCache, that saves unauthorized access attempts, so the user is redirected after login.
        .requestCache().requestCache(new CustomRequestCache())
        .and()
        .authorizeRequests()
        // Allow all flow internal requests.
        .requestMatchers(SecurityUtils::isFrameworkInternalRequest).permitAll()
        .mvcMatchers(PAGE_ROOT + PAGE_LOGIN_URL).permitAll()
        // Allow all requests by logged in users.
        .anyRequest().hasAnyAuthority(EnumUtils.getNames(UserRole.class));
  }

  /**
   * Allows access to static resources, bypassing Spring security.
   */
  @Override
  public void configure(WebSecurity web) {
    web.ignoring().antMatchers(
        // Vaadin Flow static resources
        "/VAADIN/**",
        // the standard favicon URI
        "/favicon.ico",
        // the robots exclusion standard
        "/robots.txt",
        // web application manifest
        "/manifest.webmanifest",
        "/sw.js",
        "/offline-page.html",
        // icons and images
        "/icons/**",
        "/images/**",
        // (development mode) static resources
        "/frontend/**",
        // (development mode) webjars
        "/webjars/**",
        // (production mode) static resources
        "/frontend-es5/**",
        "/frontend-es6/**");
  }
}
