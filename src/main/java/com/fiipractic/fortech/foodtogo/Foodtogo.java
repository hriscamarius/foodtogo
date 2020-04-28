package com.fiipractic.fortech.foodtogo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.RedirectViewControllerRegistration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class Foodtogo implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(Foodtogo.class, args);
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		//registry.addRedirectViewController("/","index.html");
		//registry.addViewController("/").setViewName("forward:/index.html");
		RedirectViewControllerRegistration r = registry.addRedirectViewController("/", "/login");
	}
}
