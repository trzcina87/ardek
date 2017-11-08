package trzcina.ardek.ustawienia;

public interface Ustawienie {

    boolean sprawdzCzyJestZapisane();
    void wczytajZUstawien();
    void uzupelnijPoleWOpcjach();
    void uzupelnijPoleWOpcjachZDomyslnych();
    void zapiszDoUstawien();
    void zapiszWartoscDoUstawien();
    void zapiszDoUstawienDomyslnaJesliNieMa();

}
