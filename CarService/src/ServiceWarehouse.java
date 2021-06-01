import java.util.ArrayList;
import java.util.Date;

public class ServiceWarehouse {

    //Miejsce serwisowe

    Pojazd pojazd; //generyczny pojazd, nie precyzujemy jaki pojazd
    Date poczatek;
    Person wlascicielPojazdu;
    ArrayList<String> historiaNapraw = new ArrayList<>();
    static final int kosztDziennySamodzielnejNaprawy = 200;
    static final int kosztDziennyNaprawyPrzezMechanika = 500;
    boolean czyNaprawialMechanik;

    public void zglosNaprawe(Pojazd pojazd, boolean czyNaprawiaMechanik, Person wlascicielPojazdu){
        this.pojazd = pojazd;
        this.czyNaprawialMechanik = czyNaprawiaMechanik;
        this.wlascicielPojazdu = wlascicielPojazdu;
    }

    public void rozpocznijNaprawe(Date poczatek) {
        this.poczatek = poczatek;
    }

    void ZakonczIpoliczKosztNaprawy(Date koniec){
        int iloscdni = (int) ExpireTimer.countDayDiff(this.poczatek,koniec); //countDayDiff zwraca longa

        int kosztNaprawy;

        if(this.czyNaprawialMechanik){
            kosztNaprawy = kosztDziennyNaprawyPrzezMechanika * iloscdni;
        }else{
            kosztNaprawy = kosztDziennySamodzielnejNaprawy * iloscdni;
        }
        historiaNapraw.add("Naprawa pojazdu: " + pojazd + " trwała " + iloscdni + " dni , naprawę wykonał "
                + (czyNaprawialMechanik ? "Mechanik" : "Klient") + " i kosztowała ona " + kosztNaprawy);

        pojazd = null; //wyczyszczenie, puste miejsce gotowa na inne auto
        poczatek = null;

    }

    @Override
    public String toString() {
        return "MiejsceSerwisowe: " +
                "pojazd: " + pojazd +
                ", poczatek: " + poczatek +
                ", wlascicielPojazdu: " + wlascicielPojazdu +
                ", historiaNapraw: " + historiaNapraw +
                ", czyNaprawialMechanik: " + czyNaprawialMechanik;
    }
}
