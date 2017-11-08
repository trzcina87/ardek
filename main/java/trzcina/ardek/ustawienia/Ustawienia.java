package trzcina.ardek.ustawienia;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import trzcina.ardek.MainActivity;

public class Ustawienia {

    //Kazde ustawienie to osobny obiekt
    public static UstawienieString nazwasieci;
    public static UstawienieString adresip;

    public static List<Ustawienie> ustawienia;

    //Tworzymy obiekty z wartosciamy domyslnymi
    private static void utworzUstawienia() {
        ustawienia = new ArrayList<>(20);
        nazwasieci = new UstawienieString("UB2,UB5", null, "nazwasieci", MainActivity.activity.nazwasieci);
        adresip = new UstawienieString("192.168.0.177", null, "adresip", MainActivity.activity.adresip);
        ustawienia.add(nazwasieci);
        ustawienia.add(adresip);
    }

    //Zapisujemy do pamieci wartosci domyslne jesli nie ma w ogole
    private static void wypelnijDomyslneJesliNieMa() {
        for(int i = 0; i < ustawienia.size(); i++) {
            ustawienia.get(i).zapiszDoUstawienDomyslnaJesliNieMa();
        }
    }

    //Wypelniamy wartosci z ustawien z telefonu
    private static void wypelnijWartosc() {
        for(int i = 0; i < ustawienia.size(); i++) {
            ustawienia.get(i).wczytajZUstawien();
        }
    }

    //Inicjujemy ustawienia, wczytujemy z pamieci, ewentlanie ustawiajcac wartosci domyslna
    public static void zainicjujUstawienia() {
        utworzUstawienia();
        wypelnijDomyslneJesliNieMa();
        wypelnijWartosc();
    }

    //Wczytujemy kazde ustawienie do odpowiedniego pola w widoku
    public static void wczytajDoPol() {
        for(int i = 0; i < ustawienia.size(); i++) {
            ustawienia.get(i).uzupelnijPoleWOpcjach();
        }
    }

    //Pobieramy kazde ustawienie z widoku i zapisujemy do telefonu
    public static void zapiszDoUstawien() {
        for(int i = 0; i < ustawienia.size(); i++) {
            ustawienia.get(i).zapiszDoUstawien();
        }
    }

    public static void uzupelnijPoleWOpcjachZDomyslnych() {
        for(int i = 0; i < ustawienia.size(); i++) {
            ustawienia.get(i).uzupelnijPoleWOpcjachZDomyslnych();
        }
    }
}