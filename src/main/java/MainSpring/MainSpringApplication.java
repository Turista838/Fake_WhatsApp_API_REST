package MainSpring;

import MainSpring.Security.AuthorizationFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableScheduling
@SpringBootApplication
public class MainSpringApplication {


	public static void main(String[] args) {

		SpringApplication.run(MainSpringApplication.class, args);
	}

	@EnableWebSecurity
	@Configuration
	class WebSecurityConfig extends WebSecurityConfigurerAdapter
	{
		@Override
		protected void configure(HttpSecurity http) throws Exception //definir segurança em todos os métodos
		{
			//HttpSecurity allows configuring web based security for specific http requests
			http.
					exceptionHandling()
					.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)).and()
					.csrf().disable()
					.addFilterAfter(new AuthorizationFilter(),
							UsernamePasswordAuthenticationFilter.class)
					.authorizeRequests()
					.antMatchers(HttpMethod.POST, "/session").permitAll() //excepto este, porque é para login
					.anyRequest().authenticated().and()
					.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		}
	}

}
	//http.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
