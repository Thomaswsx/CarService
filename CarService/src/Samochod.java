public abstract class Samochod extends Pojazd{

    int iloscDrzwi;
    String markaRadia;

    public Samochod(String kolor, String paliwo, int iloscDrzwi, String markaRadia) {
        super(kolor, paliwo);


        this.iloscDrzwi = iloscDrzwi;
        this.markaRadia = markaRadia;

    }

    @Override
    public String toString() {
        return "Samochod: " +
                "kolor: " + kolor + '\'' +
                ", paliwo: " + paliwo + '\'' +
                ", iloscDrzwi: " + iloscDrzwi +
                ", markaRadia: " + markaRadia + '\'';
    }
}
