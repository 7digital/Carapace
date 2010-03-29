package sevendigital.androiddojo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by IntelliJ IDEA.
 * User: benh
 * Date: 10-Dec-2009
 * Time: 17:28:01
 * To change this template use File | Settings | File Templates.
 */
public class Splash extends Activity {
     private static final int STOPSPLASH = 0;
     //time in milliseconds
     private static final long SPLASHTIME = 3000;

    private ImageView splash;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        
        splash = (ImageView) findViewById(R.id.splashscreen);
        Message msg = new Message();
        msg.what = STOPSPLASH; 
        splashHandler.sendMessageDelayed(msg, SPLASHTIME);
    }
    private Handler splashHandler = new Handler() {
          @Override
          public void handleMessage(Message msg) {
               switch (msg.what) {
               case STOPSPLASH:
                    //remove SplashScreen from view
                    splash.setVisibility(View.GONE);
                    Intent mainIntent = new Intent(Splash.this, LockerListView.class);
                   Splash.this.startActivity(mainIntent); 
                    break;
               }
               super.handleMessage(msg);
          }
     };

}
