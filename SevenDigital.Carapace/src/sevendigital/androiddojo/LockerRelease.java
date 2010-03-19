package sevendigital.androiddojo;

import org.dom4j.Node;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: Phil
 * Date: 10-Dec-2009
 * Time: 15:48:14
 * To change this template use File | Settings | File Templates.
 */
public class LockerRelease implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 3802775685439135417L;


	public Release get_release() {
        return _release;
    }

    public void set_release(Release _release) {
        this._release = _release;
    }

    Release _release;


    public LockerRelease(Node node) {
        _release = new Release(node.selectSingleNode("release"));
    }
}
