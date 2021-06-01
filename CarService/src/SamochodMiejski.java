public class SamochodMiejski extends Samochod {

    boolean czyElektryczny;
    boolean czyAutomat;

    public SamochodMiejski(String kolor, String paliwo, int iloscDrzwi, String markaRadia, boolean czyElektryczny, boolean czyAutomat) {
        super(kolor, paliwo, iloscDrzwi, markaRadia);

        this.czyAutomat = czyAutomat;
        this.czyElektryczny = czyElektryczny;

    }
}
