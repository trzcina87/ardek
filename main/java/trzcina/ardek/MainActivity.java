package trzcina.ardek;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Watek watek;

    private boolean sprawdzWifi() {
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
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        ClickImage click = new ClickImage();
        findViewById(R.id.polkaon).setTag("puls=196&kod=5330227");
        findViewById(R.id.polkaoff).setTag("puls=196&kod=5330236");
        findViewById(R.id.oknoon).setTag("puls=196&kod=5330371");
        findViewById(R.id.oknooff).setTag("puls=196&kod=5330380");
        findViewById(R.id.szafaon).setTag("puls=196&kod=5330691");
        findViewById(R.id.szafaoff).setTag("puls=196&kod=5330700");
        findViewById(R.id.pytajnikon).setTag("puls=196&kod=5332227");
        findViewById(R.id.pytajnikoff).setTag("puls=196&kod=5332236");
        findViewById(R.id.polkaon).setOnClickListener(click);
        findViewById(R.id.polkaoff).setOnClickListener(click);
        findViewById(R.id.oknoon).setOnClickListener(click);
        findViewById(R.id.oknooff).setOnClickListener(click);
        findViewById(R.id.szafaon).setOnClickListener(click);
        findViewById(R.id.szafaoff).setOnClickListener(click);
        findViewById(R.id.pytajnikon).setOnClickListener(click);
        findViewById(R.id.pytajnikoff).setOnClickListener(click);
        watek = new Watek(this);
        watek.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        watek.interrupt();
        while(watek.isAlive()) {
            try {
                watek.join();
            } catch (InterruptedException e) {
            }
        }
        Toast.makeText(MainActivity.this, "Zakończono Andek!", Toast.LENGTH_SHORT).show();
    }
}