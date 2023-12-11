package tuneasybook.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.ReactiveDiscoveryClient;
import org.springframework.cloud.gateway.discovery.DiscoveryClientRouteDefinitionLocator;
import org.springframework.cloud.gateway.discovery.DiscoveryLocatorProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}
	/*@Bean
	RouteLocator gatewayRoutes(RouteLocatorBuilder builder){
		return builder.routes()
				.route(r->r.path("/customers/**").uri("http://localhost:8081/"))
				.route(r->r.path("/products/**").uri("http://localhost:8082/"))
				.build();
	}*/



	@Bean
	DiscoveryClientRouteDefinitionLocator dynamicRoutes(ReactiveDiscoveryClient rdc, DiscoveryLocatorProperties dlp){
		return new DiscoveryClientRouteDefinitionLocator(rdc,dlp);
	}
	@Bean
	RouteLocator gatewayRoutes(RouteLocatorBuilder builder){
		return builder.routes()
				.route(r->r.path("/customers/**").uri("lb://customer-service"))
				.route(r->r.path("/products/**").uri("lb://inventory-service"))
				.route(r->r.path("/bills/**").uri("lb://billing-service"))
				.build();
	}

	@Bean
	RouteLocator staticRoutes(RouteLocatorBuilder builder){
		return builder.routes()
				.route(r->r
						.path("/muslim/**")
						.filters(f->f
								.addRequestHeader("x-rapidapi-host","muslimsalat.p.rapidapi.com")
								.addRequestHeader("x-rapidapi-key", "fe5e774996msh4eb6e863d457420p1d2ffbjsnee0617ac5078")
								.rewritePath("/muslimsalat/(?<segment>.*)","/${segment}")
//								. circuitBreaker(c->c.setName("muslim").setFallbackUri("forward:/defaultMuslim"))
						)
						.uri("https://muslimsalat.com")
				)
				.build();
		}


}
