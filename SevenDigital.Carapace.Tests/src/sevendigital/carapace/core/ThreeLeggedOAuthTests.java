package sevendigital.carapace.core;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URI;

import static org.junit.Assert.*;

import org.coriander.oauth.core.Credential;
import org.junit.Test;


public class ThreeLeggedOAuthTests {

	private StubHttpTransport _stubHttpInterface;

	private final class StubHttpTransport implements IHttpTransport {

		private InputStream _returnStream;
		private URI _requestedUri;

		public StubHttpTransport(InputStream expectedStream) {
			_returnStream = expectedStream;
		}

		public URI requestedUri() {
			return _requestedUri;
		}
		
		public InputStream get(URI uriToGet) {
			_requestedUri = uriToGet;
			return _returnStream;
		}
	}

	@Test
	public void test_that_I_am_able_to_make_a_request_with_a_particular_token() throws Exception {
		String expectedResult = "Success";
		Api testApi = setUpApi(expectedResult);
		
		Credential stubToken = new Credential("token-key", "token-secret");
		InputStream result = testApi.openGet(new URI("http://localhost/user/resource"), stubToken);
		
		byte[]  expectedBytes = new byte[expectedResult.length()];
		result.read(expectedBytes);
		
		assertEquals(new String(expectedBytes), expectedResult);
	}
	
	@Test
	public void test_that_the_url_gets_signed() throws Exception {
		String expectedResult = "Success";
		Api testApi = setUpApi(expectedResult);
		
		Credential stubToken = new Credential("token-key", "token-secret");
		testApi.openGet(new URI("http://localhost/user/resource"), stubToken);
		
		String theUriUsed = _stubHttpInterface.requestedUri().toString();
		
		assertTrue(theUriUsed.indexOf("oauth_consumer_key=") > -1);
	}

	@Test
	public void test_that_the_signed_url_includes_the_token() throws Exception {
		String expectedResult = "Success";
		Api testApi = setUpApi(expectedResult);
		
		Credential stubToken = new Credential("token-key", "token-secret");
		testApi.openGet(new URI("http://localhost/user/resource"), stubToken);
		
		String theUriUsed = _stubHttpInterface.requestedUri().toString();
		
		assertTrue(theUriUsed.indexOf("oauth_token=") > -1);
	}

	private Api setUpApi(String expectedResult) {
		Credential stubCredential = ReferenceCredentials.ConsumerCredentials; 
		_stubHttpInterface = new StubHttpTransport(new ByteArrayInputStream(expectedResult.getBytes()));
		Api testApi = new Api(stubCredential, _stubHttpInterface);
		return testApi;
	}
}
