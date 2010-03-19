package sevendigital.androiddojo;

import org.dom4j.Element;
import org.dom4j.Node;

import java.io.Serializable;
import java.net.URL;
/**
 * Created by IntelliJ IDEA.
 * User: Phil
 * Date: 10-Dec-2009
 * Time: 15:51:10
 * To change this template use File | Settings | File Templates.
 */
public class Release implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 7256037713538212652L;

	public String get_title() {
        return _title;
    }

    public void set_title(String _title) {
        this._title = _title;
    }

    String _title;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    int _id;

    public URL get_imageUrl() {
        return _imageUri;
    }

    public void set_imageUri(URL _imageUri) {
        this._imageUri = _imageUri;
    }

    URL _imageUri;

    public Artist get_artist() {
        return _artist;
    }

    public void set_artist(Artist _artist) {
        this._artist = _artist;
    }

    Artist _artist;

    public Release(Node node) {
        _title = node.selectSingleNode("title").getText();
        _artist = new Artist(node.selectSingleNode("artist"));
        _id = Integer.parseInt(((Element)node).attributeValue("status"));
        try {
        _imageUri = new URL(node.selectSingleNode("image").getText());
        } catch (Exception ex) {}
    }
}
