package com.controle_estoque_back;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.controle_estoque_back.service.ProductService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class ControleEstoqueBackApplication {
	private static final Logger logger = LoggerFactory.getLogger(ProductService.class);
	public static void main(String[] args) {
		logger.info("Sistema iniciado...");
		SpringApplication.run(ControleEstoqueBackApplication.class, args);
	}

}
