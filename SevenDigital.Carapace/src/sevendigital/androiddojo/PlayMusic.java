package sevendigital.androiddojo;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
import java.net.URL;

/**
 * Created by IntelliJ IDEA.
 * User: benh
 * Date: 10-Dec-2009
 * Time: 15:42:31
 * To change this template use File | Settings | File Templates.
 */
public class PlayMusic extends Activity {
   private final Credential testApi = ReferenceCredentials.ConsumerCredentials;
    public String preview_url = Api.LiveDomain + "/track/preview?redirect=false&trackid=";

    MediaPlayer mp;

     @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play);

         final Button next = (Button) findViewById(R.id.Button01);
        final int trackId = this.getIntent().getExtras().getInt("sevendigital.androiddojo.trackid");
        try {
            Track track = ((Track)this.getIntent().getExtras().get("track"));
            showArt();
        } catch (Exception e) {
            e.printStackTrace();
        }

        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if(mp == null) {
                    mp = new MediaPlayer();
                    try {
                        Uri uri = getSignedPreviewStream(trackId);

                        mp.setDataSource(view.getContext(), uri);
                        mp.prepare();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

               performMusic(view, next);
            }
        });
    }

    private void showArt() throws URISyntaxException, IOException {
        final ImageView trackArt = (ImageView) findViewById(R.id.trackArt);
        Object o = this.getIntent().getExtras().get("sevendigital.androiddojo.cover");
        if(o != null)
        {
            URL url = (URL)o;
            InputStream stream = url.openStream();

            Bitmap bmp = BitmapFactory.decodeStream(stream);
            stream.close();
            trackArt.setImageBitmap(bmp);
        }
    }

    private Uri getSignedPreviewStream(int trackId) throws IOException, URISyntaxException, DocumentException {
        String link = preview_url + trackId;
        InputStream response = new Api(testApi).openGet(new URI(link));
        Document doc = parse(response);

        Node url = doc.selectSingleNode("//response/url");
        return Uri.parse(url.getText());
    }

    private Document parse(InputStream stream) throws DocumentException {
		SAXReader reader = new SAXReader();
		return reader.read(stream);
    }

    private void performMusic(View view, Button button) {
       if(mp.isPlaying())
       {
           mp.pause();
           button.setText("Play");
       }
       else
       {
           mp.start();
           button.setText("Pause");
       }
    }
}
