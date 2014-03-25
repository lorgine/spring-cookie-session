spring-cookie-session
=====================

##Maven
At this stage 1.0-SNAPSHOT version is available, so you need to add Sonatype Nexus snapshot repository to your pom.xml:

```xml
<repositories>
	<repository>
		<id>sonatype-nexus-snapshots</id>
		<url>https://oss.sonatype.org/content/repositories/snapshots</url>
	</repository>
</repositories>
```

and then dependency:

```xml
<dependency>
	<groupId>com.oakfusion</groupId>
	<artifactId>spring-cookie-session</artifactId>
	<version>1.0-SNAPSHOT</version>
</dependency>
```

##Example Java configuration

```java
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.authentication.builders.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.savedrequest.NullRequestCache;
import com.oakfusion.security.CookieSecurityContextRepository;
import com.oakfusion.security.SecurityCookieService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth
			.inMemoryAuthentication()
			.withUser("user").password("password").roles("USER");
	}

	@Bean
	public static SecurityContextRepository securityContextRepository() {
		return new CookieSecurityContextRepository(securityCookieService());
	}

	private static SecurityCookieService securityCookieService() {
		return new SecurityCookieService("spring-cookie-session", "jmccu2j73983kjiw88");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable().
			requestCache().disable()
			.securityContext()
				.securityContextRepository(securityContextRepository())
			.and()
				.requestCache().requestCache(new NullRequestCache())
			.and()
				.formLogin()
			.and()
				.authorizeRequests()
				.anyRequest().authenticated();
	}
}

```
