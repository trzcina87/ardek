package trzcina.ardek;

import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.MessageEvent;

public class WearListener implements MessageApi.MessageListener {

    MainActivity activity;

    public WearListener(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        if(activity.sprawdzWifi()) {
            Komunikat komunikat = new Komunikat((String) messageEvent.getPath(), System.currentTimeMillis());
            activity.watek.dodajDoListy(komunikat);
            activity.watek.interrupt();
        }
    }

}
