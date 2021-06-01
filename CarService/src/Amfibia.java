public class Amfibia extends SamochodTerenowy{

    int wypornosc;

    public Amfibia(String kolor, String paliwo, int iloscDrzwi, String markaRadia, String rodzajOpon, boolean czyMaOgrzewanie,int wypornosc) {
        super(kolor, paliwo, iloscDrzwi, markaRadia, rodzajOpon, czyMaOgrzewanie);
        this.wypornosc = wypornosc;

    }
}
