package sevendigital.androiddojo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Phil
 * Date: 10-Dec-2009
 * Time: 16:00:14
 * To change this template use File | Settings | File Templates.
 */
public class LockerAdapter<T> extends ArrayAdapter {
    private List<LockerRelease> lockerReleases;
    private LayoutInflater layoutInflater;

    public LockerAdapter(Context context, int resource, List<LockerRelease> lockerReleases) {
        super(context, resource, lockerReleases);

        this.lockerReleases = lockerReleases;
        this.layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
         View v = convertView;
                if (v == null) {
                    v = layoutInflater.inflate(R.layout.locker_release_item, null);
                }
                LockerRelease lockerRelease = lockerReleases.get(position);
                if (lockerRelease != null) {
                        TextView releaseName = (TextView) v.findViewById(R.id.releasename);
                        TextView artistName = (TextView) v.findViewById(R.id.artistname);
                        if (releaseName != null) {
                              releaseName.setText(lockerRelease.get_release().get_title());                            }
                        if(artistName != null){
                              artistName.setText(lockerRelease.get_release().get_artist().get_name());
                        }
                        ImageView imgView = (ImageView)v.findViewById(R.id.icon);
                        try {
                        InputStream stream = lockerRelease.get_release().get_imageUrl().openStream();
                        Bitmap bmp = BitmapFactory.decodeStream(stream);
                        stream.close();
                        imgView.setImageBitmap(bmp);
                        } catch (Exception ex) {}
                }
                return v;
    }
}
