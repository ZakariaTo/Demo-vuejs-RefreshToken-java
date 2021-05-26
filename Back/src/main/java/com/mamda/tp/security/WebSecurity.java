package com.mamda.tp.security;

import javax.annotation.Resource;

import com.mamda.tp.service.UserService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

@EnableWebSecurity
@EnableGlobalMethodSecurity(
		prePostEnabled = true,
		securedEnabled = true,
		jsr250Enabled = true)
public class WebSecurity extends WebSecurityConfigurerAdapter {

	private final Log logger = LogFactory.getLog(WebSecurity.class);

	private final UserService userDetailsService;

	private final PasswordEncoder bCryptPasswordEncoder;

	private final CustomAccessDeniedHandler accessDeniedHandler;

	private final AppProperties appProperties;

	private final JWTProvider jwtProvider;

	@Resource(name = "corsConfigurationSource")
	CorsConfigurationSource corsConfig;

	public WebSecurity(UserService userDetailsService, PasswordEncoder bCryptPasswordEncoder,CustomAccessDeniedHandler accessDeniedHandler,AppProperties appProperties,JWTProvider jwtProvider) {
		super();
		this.userDetailsService = userDetailsService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.accessDeniedHandler = accessDeniedHandler;
		this.appProperties = appProperties;
		this.jwtProvider = jwtProvider;
	}

	// IF this method is overide it will be injected in the AuthenticationManager Instance
//	 @Override
//	 protected void configure(AuthenticationManagerBuilder auth) throws Exception
//	 {
//	 this.logger.info("*************--------------------hhhhhh------------------------------******************");
//	 auth.inMemoryAuthentication()
//			 .withUser("zakaria")
//			 .password("{noop}123")
//			 .roles("USER");
//	 //auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
//	 }

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().configurationSource(corsConfig);
		http.csrf().disable().authorizeRequests()
				.antMatchers(SecurityCanstants.AUTORIZED_URLS).permitAll()
				.anyRequest()
				.authenticated()
				.and()
				.exceptionHandling()
				.accessDeniedHandler(accessDeniedHandler)
				.and()
				.addFilter(getAuthenticationFilter());
				//.addFilter(new AuthorizationFilter(authenticationManager(),this.jwtProvider));
		http.addFilterBefore(new AuthorizationFilter(authenticationManager(),this.jwtProvider), UsernamePasswordAuthenticationFilter.class);

	}

	public AuthenticationFilter getAuthenticationFilter() throws Exception {

		final AuthenticationFilter authenticationFilter = new AuthenticationFilter(authenticationManager(),this.jwtProvider);
		authenticationFilter.setFilterProcessesUrl("/api/public/login");

		return authenticationFilter;
	}

}
