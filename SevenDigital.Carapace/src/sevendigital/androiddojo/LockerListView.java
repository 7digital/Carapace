package sevendigital.androiddojo;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.view.View;
import android.widget.ListView;
import org.coriander.oauth.core.Credential;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import sevendigital.carapace.core.Api;
import sevendigital.carapace.core.ReferenceCredentials;
import android.app.ListActivity;
import android.os.Bundle;

public class LockerListView extends ListActivity {
	private final Credential testApi = ReferenceCredentials.ConsumerCredentials;
    List<LockerRelease> lockerReleases;

    @Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		lockerReleases = getBiddersTrackNames();

		LockerAdapter<LockerRelease> listAdapter = new LockerAdapter<LockerRelease>(
			this,
			android.R.layout.simple_list_item_1,
            lockerReleases
		);

		setListAdapter(listAdapter);
    }

    @SuppressWarnings("unchecked")
    private List<LockerRelease> getBiddersTrackNames() {
		List<LockerRelease> lockerReleases = new ArrayList<LockerRelease>();
	
		try {
			 Document biddersLocker = getBiddersLocker();

            List<Node> lockerItemNodes = biddersLocker.selectNodes(
				 "//response/locker/lockerReleases/lockerRelease"
			 );

		 	for (Node lockerItemNode : lockerItemNodes) {
				lockerReleases.add(new LockerRelease(lockerItemNode));
		 	}
		} catch (Exception e) {
			e.printStackTrace();
		}
	      
	 	return lockerReleases;
    }
    
    private Document getBiddersLocker() throws URISyntaxException, IOException, DocumentException {
		Credential aValidToken = ReferenceCredentials.ReferenceTokenForUser;
	
		URI uri = new URI(
			Api.SystestDomain + "/user/locker"
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

        int releaseId = ((LockerRelease)view.getAdapter().getItem(position)).get_release().get_id();

        Intent myIntent = new Intent(view.getContext(), LockerTrackListView.class);
        myIntent.putExtra("releaseId", releaseId);

        startActivityForResult(myIntent, 0);
    }
}
