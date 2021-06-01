public class SamochodTerenowy extends Samochod {

    String rodzajOpon;
    boolean czyMaOgrzewanie;


    public SamochodTerenowy(String kolor, String paliwo, int iloscDrzwi, String markaRadia, String rodzajOpon, boolean czyMaOgrzewanie) {
        super(kolor, paliwo, iloscDrzwi, markaRadia);

        this.rodzajOpon = rodzajOpon;
        this.czyMaOgrzewanie = czyMaOgrzewanie;


    }
}
