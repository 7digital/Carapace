package sevendigital.carapace.core;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.coriander.oauth.core.Credential;

public final class ReferenceCredentials {
	public static final Credential ConsumerCredentials = getConsumerCredentials();
	public static final Credential ReferenceTokenForUser = getReferenceToken();

	private static Credential _referenceCredential;
	private static Credential _referenceToken;
	
	private static Credential getReferenceToken() {
		final String filename = "reference_token.properties";
		if(_referenceToken == null) {
			_referenceToken = createCredentialFromProperties(filename);
		}
		System.out.println("Loaded reference token " + _referenceToken.key());
		return _referenceToken;
	}

	private static Credential getConsumerCredentials() {
		final String filename = "consumer.properties";
		if(_referenceCredential == null) {
			_referenceCredential = createCredentialFromProperties(filename);
		}
		System.out.println("Loaded consumer " + _referenceCredential.key());
		return _referenceCredential;
	}

	private static Credential createCredentialFromProperties(String filename) {
		Properties consumerProperties = new Properties();
		try {
			if(!new File(filename).exists()) {
				throw new RuntimeException("Cannot find file " + filename);
			}
			consumerProperties.load(new FileInputStream(filename));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Unable to load properties from file "+filename);
		}
		return new Credential(
					consumerProperties.getProperty("key"), 
					consumerProperties.getProperty("secret"));
	}
}
