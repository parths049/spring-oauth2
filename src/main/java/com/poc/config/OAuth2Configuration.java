package com.poc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.web.context.request.RequestContextListener;

import com.poc.authenticate.CustomLogoutSuccessHandler;
import com.poc.enums.Role;

/**
 * OAuth2 authentication configuration
 */
@Configuration
public class OAuth2Configuration {

  @Configuration
  @EnableResourceServer
  protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    @Autowired
    private CustomLogoutSuccessHandler customLogoutSuccessHandler;

    @Override
    public void configure(HttpSecurity http) throws Exception {

      http.csrf().disable().exceptionHandling().and().sessionManagement()
          .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().logout()
          .logoutUrl("/oauth/logout").logoutSuccessHandler(customLogoutSuccessHandler).and()
          .authorizeRequests()
          .antMatchers("/api/user/**").access("hasAnyRole('ROLE_USER','ROLE_ADMIN')"); //or hasRole('ROLE_ADMIN')"
    }
  }

  @Bean
  public RequestContextListener requestContextListener() {
    return new RequestContextListener();
  }

  @Configuration
  @EnableAuthorizationServer
  protected static class AuthorizationServerConfiguration
      extends AuthorizationServerConfigurerAdapter {

    private static final String PROP_CLIENTID = "client";
    private static final String PROP_SECRET = "secret";

    static final String REFRESH_TOKEN = "refresh_token";
    static final int REFRESH_TOKEN_VALIDITY_SECONDS = 6*60*60;
    
    // 7 days expire time for access token
    private static final int PROP_TOKEN_VALIDITY_SECONDS = 60 * 60 * 24 * 365;

    @Bean
    public TokenStore tokenStore() {
      return new InMemoryTokenStore();
    }

    @Autowired
    @Qualifier("myAuthenticationManager")
    private AuthenticationManager authenticationManager;

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
      endpoints.tokenStore(tokenStore())
          .authenticationManager(authenticationManager);

      endpoints
          // other endpoints
          .exceptionTranslator(e -> {
              throw e;
          });
    }

    // custom class for return a custom response while login
   
	@Autowired
	private PasswordEncoder passwordEncoder;
	
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
      clients.inMemory().withClient(PROP_CLIENTID).scopes("read", "write", "trust")
          .authorities(Role.ROLE_ADMIN.name(), Role.ROLE_USER.name())
          .authorizedGrantTypes("password", "authorization_code", REFRESH_TOKEN, "implicit")
          .secret(passwordEncoder.encode(PROP_SECRET)).accessTokenValiditySeconds(PROP_TOKEN_VALIDITY_SECONDS).refreshTokenValiditySeconds(REFRESH_TOKEN_VALIDITY_SECONDS);;
    }
  }
}