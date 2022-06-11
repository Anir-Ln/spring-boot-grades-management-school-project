package com.gsnotes.security.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import com.gsnotes.services.impl.CustomUserDetailsService;

@Configuration // Car cette classe contient des beans (annotées par @bean) qui seront crée
				// automatiquement par Spring
@EnableWebSecurity // Car c'est notre classe de gestion de sécurité donc on active Spring Security
public class AppSecurityConfig extends WebSecurityConfigurerAdapter { // Il faut hériter de WebSecurityConfigurerAdapter

	// Logger
	Logger LOGGER = LoggerFactory.getLogger(getClass().getName());

	@Bean
	public AuthenticationSuccessHandler authenticationSuccessHandler() {
		return new CustomAuthenticationSuccessHandler();
	}

	@Bean
	public AuthenticationFailureHandler authenticationFailureHandler() {
		return new CustomAuthenticationFailureHandler();
	}

	//User details
	@Bean
	public UserDetailsService userDetailsService() {
		return new CustomUserDetailsService();
	}

	// Configurer AuthenticationProvider
	@Bean
	public AuthenticationProvider authProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
	
		authProvider.setPasswordEncoder(bCryptPasswordEncoder());
		return authProvider;
	}
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authProvider());

	}

	//Password encoder
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
	return new BCryptPasswordEncoder();
	}

	//permet de ne pas bloquer les fichier css, js,... par spring security
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}



	@Override
	protected void configure(HttpSecurity http) throws Exception {

		// TODO : Configurer la securité de votre application

		http.authorizeRequests().antMatchers("/student/**").hasRole("STUDENT") // Le role USER accèdent aux requete
																				// commençant par /user/
				.antMatchers("/cadreadmin/**").hasRole("CADRE_ADMIN") // Le role ADMIN accèdent aux requete commençant
																		// par /admin/
				.antMatchers("/prof/**").hasRole("PROF") // Le role ADMIN accèdent aux requete commençant par /admin/
				.antMatchers("/admin/**").hasRole("ADMIN") // Le role ADMIN accèdent aux requete commençant par /admin/
				.antMatchers("/biblio/**").hasRole("BIBLIO") // Le role BIBLIO accèdent aux requete commençant par
																// /biblio/

				// TODO : Vous pouvez ajouter les configurations nécessaires si vous avez
				// d'autres rôles
				.and()

				.formLogin().loginPage("/showMyLoginPage") // Indiquer le mapping affichant la page de login Form
				.loginProcessingUrl("/authenticateTheUser") // Meme valeur à mettre dans l'attribut action dans le form
															// de login. Ceci redirigera au bon filtre spring qui
															// s'occupe de l'authentification
				.failureHandler(authenticationFailureHandler())

				.successHandler(authenticationSuccessHandler()).and().logout() // Configurer le logout

//		.logoutUrl("/perform_logout")   //Nous avons utiliser la valeur par défaut qui est /logout
				.deleteCookies("JSESSIONID") // effacer le cookie de session après deconnection
				.and().csrf().disable().exceptionHandling().accessDeniedPage("/access-denied") // Indiquer le mapping
																								// que Spring utilisera
																								// pour
																								// rediriger à la page
																								// d'accès non autorisé
		;

	}

}
