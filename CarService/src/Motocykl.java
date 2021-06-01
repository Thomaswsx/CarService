public class Motocykl extends Pojazd{

    String typMotocykla;
    boolean czyGlosny;

    public Motocykl(String kolor, String paliwo, String typMotocykla, boolean czyGlosny) {
        super(kolor, paliwo);

        this.typMotocykla = typMotocykla;
        this.czyGlosny = czyGlosny;


    }
}
