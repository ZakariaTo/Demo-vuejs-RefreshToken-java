package com.mamda.tp.security;

import com.mamda.tp.SpringApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;

public class SecurityCanstants {

	public static final long EXPIRATION_TIME = 18620;
	public static final long REFRESHTOKEN_EXPIRATION_TIME = 864000000;
	public static final String TOKEN_PREFIX = "bearer ";
	public static final String HEADER_STRING = "Authorization";
	public static final String AUTORIZED_URLS = "/api/public/**";
	public static final String REFRESH_TOKEN_STRING = "Refresh-token";
}
