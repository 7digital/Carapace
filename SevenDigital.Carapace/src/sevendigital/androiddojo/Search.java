package sevendigital.androiddojo;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import org.coriander.oauth.core.Credential;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import sevendigital.carapace.core.Api;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class Search extends Activity {
	private final Credential testApi = new Credential("NOT-VALID", "NOT-VALID");
	private ArrayList<String> result;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.search);

		ListView mList = (ListView) findViewById(R.id.list);
		TextView defaultLabel  = (TextView) findViewById(R.id.defaultLabel);

		Intent intent = getIntent();

		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			String query = intent.getStringExtra(SearchManager.QUERY);

			SearchResults searchResults = searchSevenDigital(query);

			ListAdapter listAdapter = new ArrayAdapter<String>(
				this,
				android.R.layout.simple_list_item_1,
				searchResults.get_results()
			);

			mList.setAdapter(listAdapter);

			defaultLabel.setText(
				"Found " + searchResults.get_totalCount() + " results for \"" + query +"\""
			);

			  mList.setOnItemClickListener(new AdapterView.OnItemClickListener()
			  {
				  public void onItemClick(AdapterView<?> adapterView, android.view.View view, int i, long l) {

					  String details = result.get(i);
					  String trackId = details.substring(details.indexOf("{")+1, details.indexOf("}"));

					  
					   Intent myIntent = new Intent(view.getContext(), PlayMusic.class);
					  myIntent.putExtra("sevendigital.androiddojo.trackid", Integer.parseInt(trackId));
					 startActivity(myIntent);
				  }
			  }

			  );
		}
    }

	private SearchResults searchSevenDigital(String query) {
		result = new ArrayList<String>();

		try {
			 Document results = searchFor(query);

			Integer pageSize = Integer.parseInt(results.selectSingleNode(
				 "//response/searchResults/pageSize"
			).getText());

			Integer totalItems = Integer.parseInt(results.selectSingleNode(
				 "//response/searchResults/totalItems"
			).getText());

			 List<Node> tracks = results.selectNodes(
				 "//response/searchResults/searchResult/track"
			 );

			for (Node track : tracks) {

				String trackId = ((org.dom4j.Element)track).attributeValue("status");
				String title = track.selectSingleNode("title").getText();


				String artist = track.selectSingleNode("artist/name").getText();


				result.add(String.format(artist + " - " + title + " {" + trackId + "}"));

			}

	 		return new SearchResults(result, totalItems, pageSize);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

    private Document searchFor(String query) throws URISyntaxException, IOException, DocumentException {

		URI uri = new URI(
			"http://api.7digital.com/1.2/track/search?q=" + java.net.URLEncoder.encode(query)
    	);

		InputStream inStream = new Api(testApi, null).openGet(uri);

		Document result = parse(inStream);

		inStream.close();

		return result;
    }

    private Document parse(InputStream stream) throws DocumentException {
		SAXReader reader = new SAXReader();
		return reader.read(stream);
    }
}

