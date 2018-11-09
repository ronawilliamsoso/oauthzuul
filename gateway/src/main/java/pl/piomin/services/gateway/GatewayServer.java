package pl.piomin.services.gateway;

import javax.sql.DataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@SpringBootApplication
@EnableZuulProxy
// @EnableOAuth2Sso
@EnableDiscoveryClient
@EnableRedisHttpSession // This tells Spring to replace the baseline Apache Tomcat HttpSession object with Spring Session Redis. adding a Servlet Filter before anything that uses the HttpSession
@EnableWebSecurity // Without this annotation, session management would not work.
public class GatewayServer {
	public static void main(String[] args) {
		SpringApplication.run(GatewayServer.class, args);
	}

	@Bean
	public DataSource dataSource() {
		return DataSourceBuilder.create().url(
				"jdbc:mysql://127.0.0.1:3306/wangwei?characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC")
				.username("wangwei").password("wangwei").driverClassName("com.mysql.jdbc.Driver").build();
	}
}
