package sevendigital.androiddojo;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import org.coriander.oauth.core.Credential;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import sevendigital.carapace.core.Api;
import sevendigital.carapace.core.ReferenceCredentials;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Phil
 * Date: 10-Dec-2009
 * Time: 15:14:15
 * To change this template use File | Settings | File Templates.
 */
public class LockerTrackListView extends ListActivity {
    private final Credential testApi = ReferenceCredentials.ConsumerCredentials;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        List<LockerTrack> trackNames = getLockerTracks();

		LockerTrackAdapter listAdapter = new LockerTrackAdapter<LockerTrack>(
			this,
			android.R.layout.simple_list_item_1,
			trackNames
		);

		setListAdapter(listAdapter);
    }

    @SuppressWarnings("unchecked")
    private List<LockerTrack> getLockerTracks() {
		List<LockerTrack> lockerTracks = new ArrayList<LockerTrack>();

		try {
			 Document biddersLocker = getBiddersLocker();

			 Node release = biddersLocker.selectSingleNode(
				 "//response/locker/lockerReleases/lockerRelease[1]/release"
			 );
            
			 List<Node> tracks = biddersLocker.selectNodes(
				 "//response/locker/lockerReleases/lockerRelease[1]/lockerTracks/lockerTrack"
			 );

		 	for (Node track : tracks) {
				lockerTracks.add(new LockerTrack(track, new Release(release)));
		 	}
		} catch (Exception e) {
			e.printStackTrace();
		}

	 	return lockerTracks;
    }

    private Document getBiddersLocker() throws URISyntaxException, IOException, DocumentException {
		Credential aValidToken = ReferenceCredentials.ReferenceTokenForUser;

        Intent intent = this.getIntent();

		URI uri = new URI(
			Api.SystestDomain + "/user/locker?releaseId=" + intent.getExtras().get("releaseId")
    	);


		InputStream inStream = new Api(testApi).openGet(uri, aValidToken);

		Document result = parse(inStream);

		inStream.close();

		return result;
    }

    private Document parse(InputStream stream) throws DocumentException {
		SAXReader reader = new SAXReader();
		return reader.read(stream);
    }

    @Override
    public void onListItemClick(ListView view, View v, int position, long id) {
        Object o = view.getAdapter().getItem(position);

        if (null == o)
            throw new IllegalArgumentException("Missing item at position " + position);
        
        LockerTrack track = (LockerTrack)o;

        Intent myIntent = new Intent(view.getContext(), PlayMusic.class);

        myIntent.putExtra("sevendigital.androiddojo.trackid", track.get_track().get_id());
        myIntent.putExtra("sevendigital.androiddojo.cover", track.get_track().get_release().get_imageUrl());

        startActivityForResult(myIntent, 0);
    }
}