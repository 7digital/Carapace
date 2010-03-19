package sevendigital.androiddojo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Phil
 * Date: 10-Dec-2009
 * Time: 18:40:17
 * To change this template use File | Settings | File Templates.
 */
public class LockerTrackAdapter<T> extends ArrayAdapter {
    private List<LockerTrack> lockerTracks;
    private LayoutInflater layoutInflater;

    public LockerTrackAdapter(Context context, int resource, List<LockerTrack> lockerTracks) {
        super(context, resource, lockerTracks);

        this.lockerTracks = lockerTracks;
        this.layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
         View v = convertView;
                if (v == null) {
                    v = layoutInflater.inflate(R.layout.locker_track_item, null);
                }
                LockerTrack lockerTrack = lockerTracks.get(position);
                if (lockerTrack != null) {
                        TextView trackName = (TextView) v.findViewById(R.id.trackname);
                        if (trackName != null) {
                              trackName.setText(lockerTrack.get_track().get_title());
                        }
                }
                return v;
    }

}
