package sevendigital.androiddojo;

import org.dom4j.Node;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: Phil
 * Date: 10-Dec-2009
 * Time: 17:02:58
 * To change this template use File | Settings | File Templates.
 */
public class Artist implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 8652715105959424362L;
	private String _name;

    public Artist(Node node) {
        _name = node.selectSingleNode("name").getText();
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }
}
