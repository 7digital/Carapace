package sevendigital.carapace.core;

import org.coriander.oauth.core.Credential;
import org.junit.Test;

import static org.junit.Assert.*;

public class RequestTokenIntegrationTests {

	@Test
	public void testStartAuthentication() throws Exception {
		AuthenticationHandler testHandler = 
			new AuthenticationHandler(ReferenceCredentials.ConsumerCredentials, "http://api.7digital.com/1.2");
		Credential requestToken = testHandler.startAuthentication();
		
		System.out.println("Token details: key="+requestToken.key()+", secret="+requestToken.secret());
		assertNotNull(requestToken.key());
		assertNotNull(requestToken.secret());
	}
}
