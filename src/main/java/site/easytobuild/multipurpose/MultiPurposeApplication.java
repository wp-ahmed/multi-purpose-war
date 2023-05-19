package site.easytobuild.multipurpose;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;


@SpringBootApplication
public class MultiPurposeApplication extends SpringBootServletInitializer {

	public static void main(String[] args)  {
		SpringApplication.run(MultiPurposeApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(MultiPurposeApplication.class);
	}
}
