package sevendigital.carapace.core;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.coriander.oauth.core.Credential;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

public class AuthenticationHandler {
	static final String RequestTokenUrl = "/oauth/requesttoken";

	private static final String AccessTokenUrl = "/oauth/accesstoken";

	private Credential _requestToken;

	private final Credential _consumerCredentials;

	private String _apiRoot;
	
	public AuthenticationHandler(Credential consumerCredentials) {
		this(consumerCredentials, Api.LiveDomain);
	}
	
	public AuthenticationHandler(Credential consumerCredentials,
			String apiRoot) {
		_consumerCredentials = consumerCredentials;
		_apiRoot = apiRoot;
	}

	public Credential startAuthentication() throws URISyntaxException, IOException, DocumentException {
		InputStream requestStream = makeRequest(_apiRoot+RequestTokenUrl+"?token=test1");
		Document tokenResult = readXml(requestStream);
		
		String token = tokenResult.selectSingleNode("//response/oauth_request_token/oauth_token").getText();
		String tokenSecret = tokenResult.selectSingleNode("//response/oauth_request_token/oauth_token_secret").getText();
		
		_requestToken = new Credential(token, tokenSecret);
		return _requestToken;
	}
	
	public Credential makeAccessTokenExchange() throws IOException, URISyntaxException, DocumentException {
		InputStream requestStream = makeRequestWithToken(_apiRoot+AccessTokenUrl, _requestToken);
		Document tokenResult = readXml(requestStream);
		
		String accessToken = tokenResult.selectSingleNode("//response/oauth_access_token/oauth_token").getText();
		String accessTokenSecret = tokenResult.selectSingleNode("//response/oauth_access_token/oauth_token_secret").getText();
		
		return new Credential(accessToken, accessTokenSecret);
	}

	/**
	 * This method should not be used other than for a manual demonstration of the 3-legged OAuth
	 * authentication process.  
	 */
	@Deprecated 
	public Credential makeAccessTokenExchange(Credential requestToken) throws IOException, URISyntaxException, DocumentException {
		InputStream requestStream = makeRequestWithToken(_apiRoot+AccessTokenUrl, requestToken);
		Document tokenResult = readXml(requestStream);
		
		String accessToken = tokenResult.selectSingleNode("//response/oauth_access_token/oauth_token").getText();
		String accessTokenSecret = tokenResult.selectSingleNode("//response/oauth_access_token/oauth_token_secret").getText();
		
		return new Credential(accessToken, accessTokenSecret);
	}
	
	private Document readXml(InputStream requestStream) throws DocumentException, IOException {
		try {
			return parse(requestStream);
		} finally {
			requestStream.close();
		}
	}
	
	private InputStream makeRequestWithToken(String requestTokenUrl, Credential token) throws IOException, URISyntaxException {
		return new Api(_consumerCredentials).openGet(new URI(requestTokenUrl), token);
	}
	
	public InputStream makeRequest(String requestTokenUrl) throws IOException, URISyntaxException {
		return new Api(_consumerCredentials).openGet(new URI(requestTokenUrl));
	}

    private Document parse(InputStream stream) throws DocumentException, IOException {
		return new SAXReader().read(stream);
    }
}
