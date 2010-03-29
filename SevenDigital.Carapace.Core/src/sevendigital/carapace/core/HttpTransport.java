package sevendigital.carapace.core;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.methods.GetMethod;

public class HttpTransport implements IHttpTransport {

	@Override
	public InputStream get(URI urlToGet) throws HttpException, IOException {
		HttpMethodBase method = new GetMethod(urlToGet.toString());
        new HttpClient().executeMethod(method);
        return method.getResponseBodyAsStream();
	}
}
