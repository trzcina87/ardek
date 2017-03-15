package trzcina.ardek;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.view.WindowManager;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.Wearable;

public class MainActivity extends AppCompatActivity {

    public Watek watek;
    private NotificationManager notificationmanager = null;
    private OdbiorNotyfikacji odbiornotyfikacji = null;
    public GoogleApiClient gac = null;

    public boolean sprawdzWifi() {
        WifiManager manager = (WifiManager)this.getSystemService(Context.WIFI_SERVICE);
        if (manager.isWifiEnabled()) {
            WifiInfo wifiInfo = manager.getConnectionInfo();
            if (wifiInfo != null) {
                NetworkInfo.DetailedState state = WifiInfo.getDetailedStateOf(wifiInfo.getSupplicantState());
                if (state == NetworkInfo.DetailedState.CONNECTED || state == NetworkInfo.DetailedState.OBTAINING_IPADDR) {
                    String nazwa = wifiInfo.getSSID();
                    if(nazwa.toLowerCase().contains("tenda_3fb340")) {
                        return true;
                    } else {
                        Toast.makeText(this, "Połącz z siecią Tenda...!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Połącz z siecią WIFI!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Połącz z siecią WIFI!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Włącz WIFI!", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    private class ClickImage implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if(sprawdzWifi()) {
                Komunikat komunikat = new Komunikat((String) v.getTag(), System.currentTimeMillis());
                watek.dodajDoListy(komunikat);
                watek.interrupt();
            }
        }
    }

    public void wyswietlInfo(final String info) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, info, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ustawEkran();
        ustawTagi();
        ustawClick();
        ustawWatek();
        ustawNotyfikacje();
        ustawApi();
    }

    private void ustawEkran() {
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    private void ustawTagi() {
        findViewById(R.id.polkaon).setTag("puls=196&kod=5330227");
        findViewById(R.id.polkaoff).setTag("puls=196&kod=5330236");
        findViewById(R.id.oknoon).setTag("puls=196&kod=5330371");
        findViewById(R.id.oknooff).setTag("puls=196&kod=5330380");
        findViewById(R.id.szafaon).setTag("puls=196&kod=5330691");
        findViewById(R.id.szafaoff).setTag("puls=196&kod=5330700");
        findViewById(R.id.pytajnikon).setTag("puls=196&kod=5332227");
        findViewById(R.id.pytajnikoff).setTag("puls=196&kod=5332236");
    }

    private void ustawClick() {
        ClickImage click = new ClickImage();
        findViewById(R.id.polkaon).setOnClickListener(click);
        findViewById(R.id.polkaoff).setOnClickListener(click);
        findViewById(R.id.oknoon).setOnClickListener(click);
        findViewById(R.id.oknooff).setOnClickListener(click);
        findViewById(R.id.szafaon).setOnClickListener(click);
        findViewById(R.id.szafaoff).setOnClickListener(click);
        findViewById(R.id.pytajnikon).setOnClickListener(click);
        findViewById(R.id.pytajnikoff).setOnClickListener(click);
    }

    private void ustawWatek() {
        watek = new Watek(this);
        watek.start();
    }

    private void ustawNotyfikacje() {
        String urle[] = {"puls=196&kod=5330227", "puls=196&kod=5330236", "puls=196&kod=5330371", "puls=196&kod=5330380", "puls=196&kod=5330691", "puls=196&kod=5330700"};
        int ikony[] = {R.mipmap.polka, R.mipmap.polkaoff, R.mipmap.okno, R.mipmap.oknooff, R.mipmap.szafa, R.mipmap.szafaoff};
        int imid[] = {R.id.im0, R.id.im1, R.id.im2, R.id.im3, R.id.im4, R.id.im5};
        RemoteViews notyfikacjaview = new RemoteViews(getApplicationContext().getPackageName(), R.layout.notyfikacja);
        for(int i = 0; i < 6; i++) {
            notyfikacjaview.setImageViewResource(imid[i], ikony[i]);
        }
        NotificationCompat.Builder builder = (NotificationCompat.Builder) new NotificationCompat.Builder(getApplicationContext()).setSmallIcon(R.mipmap.ikona);
        builder = (NotificationCompat.Builder)builder.setContent(notyfikacjaview).setOngoing(true).setPriority(Notification.PRIORITY_MAX).setVisibility(Notification.VISIBILITY_PUBLIC);
        Intent intenty[] = new Intent[6];
        PendingIntent pendingintenty[] = new PendingIntent[6];
        for(int i = 0; i < 6; i++) {
            intenty[i] = new Intent(urle[i]);
            pendingintenty[i] = PendingIntent.getBroadcast(this, 0, intenty[i], 0);
            notyfikacjaview.setOnClickPendingIntent(imid[i], pendingintenty[i]);
        }
        odbiornotyfikacji = new OdbiorNotyfikacji(this);
        IntentFilter[] filtry = new IntentFilter[6];
        for(int i = 0; i < 6; i++) {
            filtry[i] = new IntentFilter(urle[i]);
            registerReceiver(odbiornotyfikacji, filtry[i]);
        }
        notificationmanager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification notyfikacja = builder.build();
        notificationmanager.notify(0, notyfikacja);
    }

    private void ustawApi() {
        gac = new GoogleApiClient.Builder(getApplicationContext()).addApi(Wearable.API).build();
        gac.connect();
        Wearable.MessageApi.addListener(gac, new WearListener(this));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(notificationmanager != null) {
            notificationmanager.cancelAll();
        }
        if(odbiornotyfikacji != null) {
            unregisterReceiver(odbiornotyfikacji);
        }
        watek.zakoncz = true;
        watek.interrupt();
        while(watek.isAlive()) {
            try {
                watek.join();
            } catch (InterruptedException e) {
            }
        }
        Toast.makeText(MainActivity.this, "Zakończono Ardek!", Toast.LENGTH_SHORT).show();
    }
}