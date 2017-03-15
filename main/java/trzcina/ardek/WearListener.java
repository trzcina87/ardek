package trzcina.ardek;

import android.widget.Toast;

import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.MessageEvent;

public class WearListener implements MessageApi.MessageListener {

    MainActivity activity;

    public WearListener(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        showToast(messageEvent.getPath());
    }

    private void showToast(String message) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
    }

}
