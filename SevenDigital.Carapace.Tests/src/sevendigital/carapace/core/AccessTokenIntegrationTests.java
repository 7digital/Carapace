package sevendigital.carapace.core;

import static org.junit.Assert.*;

import org.coriander.oauth.core.Credential;
import org.junit.Test;

public class AccessTokenIntegrationTests {

	/**
	 * This test won't pass consistently on it's own.  It relies on a new request token being
	 * created before each run of the test.  The request token details should be inserted as
	 * token-key and token-secret.
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	@Test
	public void testMakeAccessTokenExchange() throws Exception {
		AuthenticationHandler testHandler = 
			new AuthenticationHandler(ReferenceCredentials.ConsumerCredentials, "http://api.7digital.com/1.2");
		Credential accessToken = 
			testHandler.makeAccessTokenExchange(new Credential("token-key", "token-secret"));
		
		System.out.println("Token details: key="+accessToken.key()+", secret="+accessToken.secret());
		assertNotNull(accessToken.key());
		assertNotNull(accessToken.secret());
	}
}
