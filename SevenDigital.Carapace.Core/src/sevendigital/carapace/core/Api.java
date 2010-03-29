package sevendigital.carapace.core;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.coriander.oauth.core.Credential;
import org.coriander.oauth.core.CredentialSet;
import org.coriander.oauth.core.Options;
import org.coriander.oauth.core.SignedUri;
import org.coriander.oauth.core.nonce.SystemNonceFactory;
import org.coriander.oauth.core.timestamp.SystemTimestampFactory;

public class Api {
    private final Credential _consumer;
    private final static Credential _noToken = null;
	public static final String SystestDomain = "http://api.7digital.systest/1.2";
	public static final String LiveDomain = "http://api.7digital.com/1.2";
    private final Options _options = new Options("HMAC-SHA1", 1.0);
	private final IHttpTransport _httpTransport;
    
    public Api(Credential consumer) { 
    	this(consumer, new HttpTransport());
    }
    
    public Api(Credential consumer, IHttpTransport httpTransport) {
        _consumer = consumer;
    	_httpTransport = httpTransport;
	}

	public HttpWebResponse get(URI uri) throws IOException {
		URI signed = sign(uri);
	
		System.out.println("[Signed] " + signed.toASCIIString());
	
		return _get(sign(uri));
    }
    
    public InputStream openGet(URI uri) throws IOException {
        return _open(new GetMethod(sign(uri).toString()));
    }

	public InputStream openGet(URI apiUri, Credential token) throws HttpException, IOException {
		URI signedUri = sign(apiUri, token);
		return _httpTransport.get(signedUri);
	}

	private URI sign(URI theUriToSign) {
		return sign(theUriToSign, _noToken);
	}
	
	private URI sign(URI theUriToSign, Credential token) {
        String timestamp = new SystemTimestampFactory().createTimestamp();
        String nonce = new SystemNonceFactory().createNonce();

        CredentialSet credentials = new CredentialSet(_consumer, token);
        
        return new SignedUri(
            theUriToSign, credentials, timestamp, nonce, _options
        ).value();
    }
    
    private HttpWebResponse _get(URI uri) throws IOException {
        return execute(new GetMethod(uri.toString()));
    }
    
    private InputStream _open(HttpMethodBase method) throws IOException {
        new HttpClient().executeMethod(method);
        return method.getResponseBodyAsStream();
     }
    
    private HttpWebResponse execute(HttpMethodBase method) throws IOException {
       int status = new HttpClient().executeMethod(method);

        if (status != HttpStatus.SC_OK)
            return new HttpWebResponse (status, method.getStatusText());

        return new HttpWebResponse (status, method.getResponseBodyAsString());
    }
}
