package com.practice.application;

import java.security.Security;

import org.bouncycastle.crypto.CryptoServicesRegistrar;
import org.bouncycastle.jcajce.provider.BouncyCastleFipsProvider;
import org.bouncycastle.jsse.provider.BouncyCastleJsseProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.java.Log;

@SpringBootApplication
@Log
public class PracticeApplication {

	public static void main(String[] args) {
		enableFipsMode();
		SpringApplication.run(PracticeApplication.class, args);
	}
	
	/**
	 * The Bouncy Castle provider names
	 */
	private static final String BcProviderName = "BCFIPS";
	private static final String BcTlsProviderName = "BCJSSE";
	
	/**
	 * Flag to ensure we only attempt to enable FIPS mode once
	 */
	private static boolean checked = false;

	public synchronized static boolean enableFipsMode() {
		boolean result = false;
		if (!checked) {
			checked = true;
			/* check if provider was already registered, 
			 * and remove it to ensure it is the first provider in the list
			 */
			if (Security.getProvider(BcProviderName) != null) {
				Security.removeProvider(BcProviderName);
			}
			if (Security.getProvider(BcTlsProviderName) != null) {
				Security.removeProvider(BcTlsProviderName);
			}
			System.setProperty("org.bouncycastle.fips.approved_only", "true");
			Security.insertProviderAt(new BouncyCastleFipsProvider(), 1);
			Security.insertProviderAt(new BouncyCastleJsseProvider(), 2);
			result = CryptoServicesRegistrar.isInApprovedOnlyMode();
			log.info("In FIPS (only) mode: " + result);
		} else {
			result = CryptoServicesRegistrar.isInApprovedOnlyMode();
		}
		return result;
	}
}
