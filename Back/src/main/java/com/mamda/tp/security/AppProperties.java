package com.mamda.tp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class AppProperties {

	@Autowired
	Environment env;

	public Environment getEnv(){
		return this.env;
	}

	public String getSecretToken() {
		return env.getProperty("SecretToken");
	}

}