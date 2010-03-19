package sevendigital.carapace.core.integration.tests;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.coriander.oauth.core.Credential;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import sevendigital.carapace.core.Api;

public class given_a_valid_oauth_consumer {
    Credential validConsumerCredential = null;
    Credential noToken = null;
    String signatureMethod = "HMAC-SHA1";
    String version = "1.0";
    Integer ARTIST_KEANE = 1;
    
    sevendigital.carapace.core.HttpWebResponse _response;
    
    @Before
    public void setUp() throws Exception {
        given_a_valid_consumer();
        _response = null;
    }

    @After
    public void tearDown() throws Exception {}
    
    @Test
    public void then_I_can_read_artist_info_about_keane_from_the_7digital_api() 
    throws URISyntaxException, HttpException, IOException {
        URI uri = new URI(
                "http://api.7digital.com/1.2/artist/details?artistid=" + 
                ARTIST_KEANE.toString()
        );
         
        when_I_fetch(uri);
        
        then_http_response_equals(HttpStatus.SC_OK);
    }
    
    @Test
    public void then_I_can_read_more_details_about_keane_from_the_7digital_api() 
    throws URISyntaxException, HttpException, IOException {
        URI uri = new URI(
                "http://api.7digital.com/1.2/artist/details?artistid=" + 
                ARTIST_KEANE.toString() + 
                "&country=GB"
        );
         
        when_I_fetch(uri);
        
        then_http_response_equals(HttpStatus.SC_OK);
    }
    
    @Test
    public void then_I_can_read_chart_info_from_the_7digital_api() 
    throws URISyntaxException, HttpException, IOException {
        URI uri = new URI(
                "http://api.7digital.com/1.2/artist/chart?" + 
                "period=week&toDate=20090601&country=GB"
        );
         
        when_I_fetch(uri);
        
        then_http_response_equals(HttpStatus.SC_OK);
    }
    
    @Test
    public void and_dom4j_then_I_can_read_the_artist_image_url() 
	    throws 
	    HttpException, 
	    IOException, 
	    ParserConfigurationException, 
	    FactoryConfigurationError, 
	    SAXException, 
	    URISyntaxException, 
	    DocumentException 
	    {
    	URI uri = new URI(
		"http://api.7digital.com/1.2/artist/details?artistid=" + 
		ARTIST_KEANE.toString()
    	);
    	 
    	InputStream inStream = new Api(validConsumerCredential).openGet(uri);
        
        Document result = parse(inStream);
        
        String expectedImageUrl = "http://cdn.7static.com/static/" + 
        	"img/artistimages/00/000/000/0000000001_150.jpg";
        
        String actual = result.selectSingleNode("//response/artist/image").getText();
        
        assertThat(actual, is(equalTo(expectedImageUrl)));
    }
    
    @Test
    public void and_a_valid_token_for_systest_then_I_can_access_my_locker() 
    throws URISyntaxException, HttpException, IOException {
	Credential aValidToken = new Credential(
		"HZvFeX5T7XlRIcJme/EWTg==", "Ao61gCJXIM20aqLDw7+Cow==" 
	);
	
	URI uri = new URI(
		"http://api.7digital.systest/1.2/user/locker"
    	);
	
	_response = new Api(validConsumerCredential, aValidToken).get(uri); 
	
	then_http_response_equals(HttpStatus.SC_OK);
    }
    
    private void given_a_valid_consumer() {
        validConsumerCredential = new Credential("test-api", "8ahe976veCrawrAH");
    }
    
    private void when_I_fetch(URI uri) throws HttpException, IOException {
        _response = new Api(validConsumerCredential).get(uri);
    }
    
    private void then_http_response_equals(Integer expectedStatus) {
        if (_response.getStatus() != expectedStatus) {
            System.out.println(
                    "Unexpected HTTP status returned." + 
                    _response.getResponseText()
            );
        }
        
        assertThat(_response.getStatus(), is(equalTo(expectedStatus)));
    }
    
    private Document parse(InputStream stream) 
    throws ParserConfigurationException, FactoryConfigurationError, SAXException, IOException, DocumentException {
    	SAXReader reader = new SAXReader();
        return reader.read(stream);
    }
}
