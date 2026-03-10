package com.practice.application;

import java.security.Security;

import org.bouncycastle.crypto.CryptoServicesRegistrar;
import org.bouncycastle.jcajce.provider.BouncyCastleFipsProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.java.Log;

@SpringBootApplication
@Log
public class PracticeApplication {

	public static void main(String[] args) {
		SpringApplication.run(PracticeApplication.class, args);
	}
	
	/**
	 * The Bouncy Castle provider names
	 */
	private static final String BcProviderName = "BCFIPS";
	
	/**
	 * Flag to ensure we only attempt to enable FIPS mode once
	 */
	private static boolean checked = false;

	public synchronized static boolean enableFipsMode() {
		boolean result = false;
		if (!checked) {
			checked = true;
			// check if provider was already registered, only need to do it once
			if (Security.getProvider(BcProviderName) != null) {
				Security.removeProvider(BcProviderName);
			}
			System.setProperty("org.bouncycastle.fips.approved_only", "true");
			Security.insertProviderAt(new BouncyCastleFipsProvider(), 1);
			result = CryptoServicesRegistrar.isInApprovedOnlyMode();
			log.info("In FIPS (only) mode: " + result);
		} else {
			result = CryptoServicesRegistrar.isInApprovedOnlyMode();
		}
		return result;
	}
/*
	@Bean
    public ConnectionFactory connectionFactory() {
        return new PostgresqlConnectionFactory(
            PostgresqlConnectionConfiguration.builder()
                .username("tnp")
                .password("password")
                .database("tnp")
                .host("localhost")
                .port(5432)
                .build()
        );
    }
*/
}
