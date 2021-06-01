import java.util.ArrayList;
import java.util.Date;

public class ParkingSpace {

    //Miejsce parkingowe

    static ArrayList<ParkingSpace> parkingSpaces = new ArrayList<>();

    static int staticMiejsceParkingoweId = 0;

    String miejsceParkingoweId;
    int wielkosc;
    int kosztNajmu;
    Person wlasciciel;
    Date dataZajecia;
    Date dataZakonczenia;
    Pojazd pojazd;

    public ParkingSpace(int wielkosc) {
        this.kosztNajmu = (int)(Math.random() * 200) + 50;
        this.miejsceParkingoweId = "Park" + staticMiejsceParkingoweId;
        staticMiejsceParkingoweId++;
        this.wielkosc = wielkosc;
        parkingSpaces.add(this);
    }

    public ParkingSpace(int wielkosc, int kosztNajmu) {
        this.kosztNajmu = kosztNajmu;
        this.miejsceParkingoweId = "Park" + staticMiejsceParkingoweId;
        staticMiejsceParkingoweId++;
        this.wielkosc = wielkosc;
        parkingSpaces.add(this);
    }

    public void wynajmij(Person nowyWlasciciel, Date dataRozpoczecia, Date dataZakonczenia){
        if (nowyWlasciciel.sumaryczneKosztyNajmu + this.kosztNajmu > 1250){
            System.out.println("Nie udało się wynająć miejsca parkingowego - zbyt duży koszt");
        }else{
            nowyWlasciciel.sumaryczneKosztyNajmu += this.kosztNajmu;
            this.wlasciciel = nowyWlasciciel;
            this.dataZajecia = dataRozpoczecia;
            this.dataZakonczenia = dataZakonczenia;

            System.out.println("Udało się wynająć pomieszczenie " + this.miejsceParkingoweId);
        }

    }

    public void oplac(Person wlasciciel){
        // Oplacenie jest jednoznaczne z zakonczeniem umowy
        if (wlasciciel == this.wlasciciel){
            //Usuwanie ostrzezen z dokumentacji wlasciciela
            for (int i = 0; i < wlasciciel.ostrzezenia.size(); i++) {
                try {
                    // Sprwadzenie czy miejsce parkingowe znajduje sie na TenantAlercie jest miejscem parkingowym
                    // w ktorym jestesmy
                    if (wlasciciel.ostrzezenia.get(i).parkingSpace == this) {
                        // Usuniecie ostrzezenia (Tenant AlERT) z listy ostrzezen osoby. Usuwa z indexu i.
                        wlasciciel.ostrzezenia.remove(i);
                        // i-- powoduje zmniejszenie 'i', dzieki czemu w kolejnym obrocie petli 'i' bedzie rowne tyle ile
                        // w momencie usuniecia . Takie rozwiazanie gwarantuje ze nie przegapimy zadnego elementu.
                        i--;
                    }
                } catch (IndexOutOfBoundsException e) {
                    break;
                }
            }

            //Oplacenie miejsca = zakonczenie najmu. Czyscimy wszystkie pola ponizej
            this.dataZajecia = null;
            this.dataZakonczenia = null;
            this.wlasciciel = null;
        }else{
            System.out.println("Ta osoba nie jest właścicielem i nie może opłacić miejsca.");
        }
    }
}
