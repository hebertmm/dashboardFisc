package io.github.***REMOVED***mm.dash;

import io.github.***REMOVED***mm.dash.util.geoutils.Geocode;
import io.github.***REMOVED***mm.dash.util.geoutils.Geoutils;
import io.github.***REMOVED***mm.dash.util.geoutils.Location;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class DashApplication {
	private static final Logger log = LoggerFactory.getLogger(DashApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(DashApplication.class, args);
	}


	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	/*@Bean
	public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
		return args -> {
			Geoutils geo = new Geoutils();
			GeoLocation loc = geo.geocodeForLocation("rua c-21 jardim america, goiania, go");
			log.info("Lat: " + loc.getLat() + " Lng: "+ loc.getLng());
		};
	}*/
}