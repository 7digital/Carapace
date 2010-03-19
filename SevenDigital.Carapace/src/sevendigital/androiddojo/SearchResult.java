package sevendigital.androiddojo;

import java.net.URI;

public class SearchResult {
	private String _text;
	private URI _imageUri;

	public SearchResult(String text, URI imageUri) {
		_text = text;
		_imageUri = imageUri;
	}

	public URI get_imageUri() {
		return _imageUri;
	}

	public String get_text() {
		return _text;
	}
}
