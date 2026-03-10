package com.practice.demo;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.security.Provider;
import java.security.Security;
import java.util.Arrays;

import javax.crypto.Cipher;

import org.bouncycastle.jcajce.provider.BouncyCastleFipsProvider;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.practice.application.PracticeApplication;

import lombok.SneakyThrows;
import lombok.extern.java.Log;

@SpringBootTest
@Log
class DemoApplicationTests {

	@Test
	void contextLoads() {
	}

	@SneakyThrows
	private void getAesProvider() {
	    Cipher cipher = Cipher.getInstance("AES");
	    Provider provider = cipher.getProvider();
	    log.info("Default Provider for AES: " + provider.getName());		
	}
	
	@Test
	void aesProvider() {
		getAesProvider();
		System.setProperty("org.bouncycastle.fips.approved_only", "true");
		Security.addProvider(new BouncyCastleFipsProvider());
		Arrays.asList(Security.getProviders()).stream().forEach( provider -> log.info(provider.getName()));
		getAesProvider();
		boolean fipsMode = PracticeApplication.enableFipsMode();
		assertTrue(fipsMode);
		Arrays.asList(Security.getProviders()).stream().forEach( provider -> log.info(provider.getName()));
		getAesProvider();
	}
	
	@Test
	void fipsMode() {
		boolean fipsMode = PracticeApplication.enableFipsMode();
		assertTrue(fipsMode);
	}
}
