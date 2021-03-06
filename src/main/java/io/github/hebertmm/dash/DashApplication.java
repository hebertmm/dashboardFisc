package io.github.hebertmm.dash;

import io.github.hebertmm.dash.util.geoutils.Geocode;
import io.github.hebertmm.dash.util.geoutils.Geoutils;
import io.github.hebertmm.dash.util.geoutils.Location;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import static org.springframework.boot.SpringApplication.run;

@SpringBootApplication
public class DashApplication {
	private static final Logger log = LoggerFactory.getLogger(DashApplication.class);

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(DashApplication.class, args);

	}


	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}


}