package trzcina.ardek;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;

public class Watek extends Thread {

    private MainActivity activity;
    private LinkedList<Komunikat> lista;

    synchronized void dodajDoListy(Komunikat komunikat) {
        lista.addLast(komunikat);
    }

    synchronized Komunikat pobierzZListy() {
        try {
            return lista.removeFirst();
        } catch (Exception e) {
            return null;
        }
    }

    public Watek (MainActivity activity) {
        this.activity = activity;
        this.lista = new LinkedList<>();
    }

    private void bladpolaczenia() {
        activity.wyswietlInfo("Błąd połączenia!");
    }

    public void run() {
        while (! isInterrupted()) {
            Komunikat pierwszy = this.pobierzZListy();
            while(pierwszy != null) {
               if(! isInterrupted()) {
                   if (System.currentTimeMillis() - pierwszy.datakomunikatu <= 5000) {
                       try {
                           URL url = new URL("http://192.168.0.177/" + pierwszy.url);
                           HttpURLConnection polaczenie = (HttpURLConnection) url.openConnection();
                           polaczenie.setInstanceFollowRedirects(false);
                           polaczenie.setConnectTimeout(3000);
                           polaczenie.setReadTimeout(3000);
                           int kod = polaczenie.getResponseCode();
                           if (kod != 302) {
                               this.bladpolaczenia();
                           }
                       } catch (Exception e) {
                           this.bladpolaczenia();
                       }
                   }
               }
               pierwszy = this.pobierzZListy();
            }
            try {
                sleep(10);
            } catch (InterruptedException e) {
                return;
            }
        }
    }
}