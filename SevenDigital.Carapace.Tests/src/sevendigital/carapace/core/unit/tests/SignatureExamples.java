package sevendigital.carapace.core.unit.tests;

import java.net.URI;
import java.net.URISyntaxException;

import org.coriander.Query;
import org.coriander.QueryParser;
import org.coriander.oauth.core.Credential;
import org.coriander.oauth.core.CredentialSet;
import org.coriander.oauth.core.Options;
import org.coriander.oauth.core.Signature;
import org.coriander.oauth.core.SignatureBaseString;
import org.coriander.oauth.core.SignedUri;
import org.coriander.oauth.core.uri.OAuthURLEncoder;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.core.IsEqual.*;
import static org.hamcrest.core.Is.*;

public class SignatureExamples {
    Credential anyConsumerCredential = new Credential("key", "secret");
    Credential anyToken = new Credential("token_key", "token_secret");
    CredentialSet credentials =  new CredentialSet(
	    anyConsumerCredential, 
	    anyToken
    );
    Options options = new Options("HMAC-SHA1", 1.0);
    
    @Test
    public void can_create_name_value_pair() {
        org.coriander.NameValuePair nvp = new org.coriander.NameValuePair("name", "value");
        
        assertThat(nvp.name(), is(equalTo("name")));
    }
    
    @Test
    public void can_create_signature_base_string() throws URISyntaxException {
        String expected = "GET&http%3A%2F%2Fphotos.example.net%2Fphotos&file%3Dvacation.jpg" +
            "%26oauth_consumer_key%3Ddpf43f3p2l4k3l03%26oauth_nonce%3Dkllo9940pd9333jh%26" +
            "oauth_signature_method%3DHMAC-SHA1%26oauth_timestamp%3D1191242096%26" +
            "oauth_token%3Dnnch734d00sl2jdk%26oauth_version%3D1.0%26size%3Doriginal";
        
        String timestamp = "1191242096";
        String nonce = "kllo9940pd9333jh";
        Credential consumer = new Credential("dpf43f3p2l4k3l03", "kd94hf93k423kf44");
        Credential token = new Credential("nnch734d00sl2jdk", "pfkkdhi9sl3r4s00");
        CredentialSet credentials = new CredentialSet(consumer, token);
        
        URI uri = new URI("http://photos.example.net/photos");
        Query query = new QueryParser().parse("file=vacation.jpg&size=original");

        SignatureBaseString signatureBaseString = new SignatureBaseString(
            uri, 
            query,
            credentials,
            nonce, 
            timestamp
        );

        assertThat(
            signatureBaseString.toString(),
            is(equalTo(expected))
        );
    }
    
    @Test
    public void given_a_signature_base_string_then_can_sign() {
        String baseString = "GET&http%3A%2F%2Fxxx%2F&oauth_consumer_key%3Dkey%26" +
            "oauth_nonce%3Df3df23228e40e2905e305a893895f115%26oauth_signature_method%3DHMAC-SHA1" +
            "%26oauth_timestamp%3D1252657173%26oauth_version%3D1.0";

        String expected = "2/MMtvuImh4H+clAdThQWk916lo=";
        String actual = newSignature(anyConsumerCredential).sign(baseString);

        assertEquals(expected, actual);
    }
    
    @Test
    public void can_create_signed_uri() throws URISyntaxException {
        URI uri = new URI("http://xxx/");
        String timestamp = "1257608197";
        String nonce = "ea757706c42e2b14a7a8999acdc71089";

        CredentialSet credentials = new CredentialSet(anyConsumerCredential); 
        
        SignedUri signedUri = new SignedUri(
            uri,
            credentials,
            timestamp,
            nonce,
            options
        );
        
        String actual = signedUri.value().toString();
        
        assertTrue(actual != null);
    }
    
    private Signature newSignature(Credential consumerCredential) {
        return newSignature(consumerCredential, null);  
    }
    
    private Signature newSignature(Credential consumer, Credential token) {
        return new Signature(
        	new OAuthURLEncoder(), 
        	new CredentialSet(consumer, token)
	);   
    }
}
