package trzcina.ardek;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class OdbiorNotyfikacji extends BroadcastReceiver {

    public MainActivity activity;

    public OdbiorNotyfikacji(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if(activity.sprawdzWifi()) {
            Komunikat komunikat = new Komunikat((String)intent.getAction(), System.currentTimeMillis());
            activity.watek.dodajDoListy(komunikat);
            activity.watek.interrupt();
        }
    }
}