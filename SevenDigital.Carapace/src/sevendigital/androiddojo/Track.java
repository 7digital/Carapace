package sevendigital.androiddojo;

import org.dom4j.Element;
import org.dom4j.Node;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: Phil
 * Date: 10-Dec-2009
 * Time: 15:51:10
 * To change this template use File | Settings | File Templates.
 */
public class Track implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 4300936094699126467L;
	private Release _release;

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

    public Artist get_artist() {
        return _artist;
    }

    public void set_artist(Artist _artist) {
        this._artist = _artist;
    }

    Artist _artist;

    public Track(Node trackNode, Release release) {
        _release = release;
        _title = trackNode.selectSingleNode("title").getText();
        _artist = new Artist(trackNode.selectSingleNode("artist"));
        _id = Integer.parseInt(((Element)trackNode).attributeValue("status"));
    }

    public Release get_release() {
        return _release;
    }

    public void set_release(Release _release) {
        this._release = _release;
    }
}
