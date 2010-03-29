package sevendigital.carapace.core;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import org.apache.commons.httpclient.HttpException;

public interface IHttpTransport {
	InputStream get(URI urlToGet) throws HttpException, IOException;
}
