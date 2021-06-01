public class Przedmiot {

    String nazwa;
    double objetosc;

    public Przedmiot(String nazwa, double objetosc){
        this.nazwa = nazwa;
        this.objetosc = objetosc;


    }

    @Override
    public String toString() {
        return "\t" + this.nazwa + "(" + this.objetosc + ")";
    }
}
