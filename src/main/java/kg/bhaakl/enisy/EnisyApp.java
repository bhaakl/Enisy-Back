package kg.bhaakl.enisy;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EnisyApp {

	public static void main(String[] args) {
		SpringApplication.run(EnisyApp.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

//	@Bean
//	public WebMvcConfigurer corsConfigurer() {
//		return new WebMvcConfigurer() {
//			@Override
//			public void addCorsMappings(@NonNull CorsRegistry registry) {
//				registry.addMapping("/topics")
//						.allowedOrigins("http://localhost:8081")
//						.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")
//						.allowedHeaders("Authorization", "Cache-Control", "Content-Type");
//				registry.addMapping("/auth/**")
//						.allowedOrigins("http://localhost:8081")
//						.allowedMethods("GET", "POST", "OPTIONS")
//						.allowedHeaders("Authorization", "Cache-Control", "Content-Type");
//				registry.addMapping("/hello")
//						.allowedOrigins("http://localhost:8081")
//						.allowedMethods("GET", "POST")
//						.allowedHeaders("Authorization", "Cache-Control", "Content-Type");
//			}
//		};
//	}
}
