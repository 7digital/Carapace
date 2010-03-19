package sevendigital.carapace.core;

public class HttpWebResponse {
    private final Integer _status;
    private final String _responseText;
    
    public Integer getStatus() {
        return _status;
    }
    
    public String getResponseText() {
        return _responseText;
    }
    
    public HttpWebResponse(Integer status, String responseText) {
        _status = status;
        _responseText = responseText;
    }
}