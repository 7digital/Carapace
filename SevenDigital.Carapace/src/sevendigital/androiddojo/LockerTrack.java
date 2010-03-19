package sevendigital.androiddojo;

import org.dom4j.Node;

import java.io.Serializable;


/**
 * Created by IntelliJ IDEA.
 * User: Phil
 * Date: 10-Dec-2009
 * Time: 18:32:38
 * To change this template use File | Settings | File Templates.
 */
public class LockerTrack implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -1698575049056732859L;

	public Track get_track() {
        return _track;
    }

    public void set_track(Track _track) {
        this._track = _track;
    }

    private Track _track;

    public LockerTrack(Node trackNodes, Release release) {
        _track = new Track(trackNodes.selectSingleNode("track"), release);
    }
}
