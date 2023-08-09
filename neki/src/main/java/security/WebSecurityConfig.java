package security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import security.services.UserDetailsServiceImpl;


@Configuration
@EnableMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class WebSecurityConfig  {
	
	@Autowired
	UserDetailsServiceImpl userDetailsService;

	@Autowired
	private AuthEntryPointJwt unauthorizedHandler;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .cors(Customizer.withDefaults()) //habilita o cors
            .csrf(csrf -> csrf.disable()) //desabilita o csrf
            .exceptionHandling(handling -> handling.authenticationEntryPoint(unauthorizedHandler)) //configura a classe para tratamento da excecao de autenticacao
            .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //define a politica de sessao
            //Essas linhas que definimos quais rotas serão publicas e quais privadas
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/**" ,"/roles", "/signin**" , "/swagger-ui/**",
                        "/swagger-resources/*",
                        "/v3/api-docs/**").permitAll()
            .requestMatchers(HttpMethod.GET, "/skill/**", "/funcionarios/**",  "/funcionarios/*/skills/" ).hasAnyRole("ADM", "USER")
            .requestMatchers(HttpMethod.POST, "/funcionarios/*/skills/associar-skills**","/skill"  ).hasAnyRole("ADM", "USER")
            .requestMatchers(HttpMethod.DELETE, "/funcionarios/*/skills/excluir**" ).hasAnyRole("ADM", "USER")
            
            .anyRequest().authenticated())
            ;		
		
		http.authenticationProvider(authenticationProvider()); //define o provedor de autenticacao

		http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class); //define o filtro a ser aplicado no ciclo de vida da requisicao
		return http.build();
	}
	
	
	  @Bean
	  CorsConfigurationSource corsConfigurationSource() {
	      CorsConfiguration configuration = new CorsConfiguration();
	      configuration.setAllowedOrigins(Arrays.asList("*")); // Permitir origens de qualquer lugar (Isso pode ser ajustado para segurança mais rígida)
	      configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT" ,"DELETE")); // Permitir métodos HTTP
	      configuration.setAllowedHeaders(Arrays.asList("Content-Type", "Authorization")); // Permitir cabeçalhos necessários, incluindo Authorization
	      UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	      source.registerCorsConfiguration("/**", configuration);
	      return source;
	  }

	  
	@Bean
	public AuthTokenFilter authenticationJwtTokenFilter() {
		return new AuthTokenFilter();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder());

		return authProvider;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
