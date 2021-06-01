import java.util.ArrayList;
import java.util.Date;

public class Pomieszczenie {

    //Służy do nadawania id dla obiektu. Statyczna tzn. (jedna dla klasy)
    static int staticPomieszczenieId = 0;

    static ArrayList<Pomieszczenie> pomieszczenia = new ArrayList<>();

    int kosztNajmu;

    Date dataNajmu, dataZakonczeniaNajmu;


    ArrayList<Person> uprawnieni;
    //ma pomieścić dowolny przedmiot
    ArrayList<Przedmiot> przedmoty;

    //id dla kazdego obiektu.
    String pomieszczenieId;

    int powierzchnia;

    public Pomieszczenie(Person p, int powierzchnia) {
        // typ argument przyjmie jakas osobe (pierwsza osoba uprawniona do obslugi pomieszczenia)
        //nadanie id dla obiektu
        this.pomieszczenieId = "Pom" + staticPomieszczenieId;
        //zwiekszenie statycznego id (tak aby kolejne obiekty mialy inne id)
        staticPomieszczenieId++;

        this.kosztNajmu = (int) (Math.random() * 300) + 100;
        this.uprawnieni = new ArrayList<>();
        this.uprawnieni.add(p);
        this.przedmoty = new ArrayList<>();
        this.powierzchnia = powierzchnia;

        pomieszczenia.add(this);
    }

    public Pomieszczenie(int powierzchnia) {
        //nadanie id dla obiektu
        this.pomieszczenieId = "Pom" + staticPomieszczenieId;
        //zwiekszenie statycznego id (tak aby kolejne obiekty mialy inne id)
        staticPomieszczenieId++;

        this.kosztNajmu = (int) (Math.random() * 300) + 100;  // random 0 a 1. do 400 minimum 100
        this.uprawnieni = new ArrayList<>();
        this.przedmoty = new ArrayList<>();
        this.powierzchnia = powierzchnia;

        Pomieszczenie.pomieszczenia.add(this);
    }

    public Pomieszczenie(int wys, int szer, int dlug) {
        //nadanie id dla obiektu
        this.pomieszczenieId = "Pom" + staticPomieszczenieId;
        //zwiekszenie statycznego id (tak aby kolejne obiekty mialy inne id)
        staticPomieszczenieId++;

        this.kosztNajmu = (int) (Math.random() * 300) + 100;  // random 0 a 1. do 400 minimum 100
        this.uprawnieni = new ArrayList<>();
        this.przedmoty = new ArrayList<>();
        this.powierzchnia = wys * szer * dlug;
        Pomieszczenie.pomieszczenia.add(this);
    }

    public Pomieszczenie(int powierzchnia, int kosztNajmu) {
        //nadanie id dla obiektu
        this.pomieszczenieId = "Pom" + staticPomieszczenieId;
        //zwiekszenie statycznego id (tak aby kolejne obiekty mialy inne id)
        staticPomieszczenieId++;

        this.kosztNajmu = kosztNajmu;
        this.uprawnieni = new ArrayList<>();
        this.przedmoty = new ArrayList<>();
        this.powierzchnia = powierzchnia;
        Pomieszczenie.pomieszczenia.add(this);
    }

    public Pomieszczenie(int wys, int szer, int dlug, int kosztNajmu) {
        //nadanie id dla obiektu
        this.pomieszczenieId = "Pom" + staticPomieszczenieId;
        //zwiekszenie statycznego id (tak aby kolejne obiekty mialy inne id)
        staticPomieszczenieId++;

        this.kosztNajmu = kosztNajmu;
        this.uprawnieni = new ArrayList<>();
        this.przedmoty = new ArrayList<>();
        this.powierzchnia = wys * szer * dlug;
        Pomieszczenie.pomieszczenia.add(this);
    }

    public Pomieszczenie(ArrayList<Person> plist, int powierzchnia) {
        this.pomieszczenieId = "Pom" + staticPomieszczenieId;
        staticPomieszczenieId++;

        this.uprawnieni = plist; // lista osob, lista uprawnieni
        this.przedmoty = new ArrayList<>();
        this.powierzchnia = powierzchnia;
        Pomieszczenie.pomieszczenia.add(this);

    }

    public void dodajUprawnienia(Person potencjalnyWlasciciel, Person nowyUzytkownik) {
        if (potencjalnyWlasciciel == this.uprawnieni.get(0)) { //czy ta sama osoba jest na indeksie 0
            this.uprawnieni.add(nowyUzytkownik); //na liscie dodajemy nowego uzytkownika
            System.out.println("Dodano nowego użytkownika " + nowyUzytkownik);
        } else {
            System.out.println("Osoba " + potencjalnyWlasciciel + " nie ma uprawnień do dodania nowych użytkowników."); //jezeli jest ktos kto nie jest na indeksie 0
        }
    }

    public void usunUprawnienia(Person potencjalnyWlasciciel, Person staryUzytkownik) {
        if (potencjalnyWlasciciel == this.uprawnieni.get(0)) {
            this.uprawnieni.remove(staryUzytkownik);
            System.out.println("Usuniętego starego użytkownika +" + staryUzytkownik);
        } else {
            System.out.println("Osoba " + potencjalnyWlasciciel + " nie ma uprawnień do usunięcia starych użytkowników.");
        }
    }

    public void dodajPrzedmiot(Person potencjalnyUzytkownik, Przedmiot nowyPrzedmiot) throws TooManyThingsException {

//        jesli zwroci true sprawdza czy potencjalny uzytkwonik zawiera sie w liscie uprawnionych
        if (this.uprawnieni.contains(potencjalnyUzytkownik)) { //na pozycji 0 znajduje sie wlascicel

            if(policzWolnaObjetosc() < nowyPrzedmiot.objetosc){
                throw new TooManyThingsException("Ten obiekt nie zmiescil sie do pomieszczenia");
            }
            this.przedmoty.add(nowyPrzedmiot);
            System.out.println("Udało się dołożyć obiekt: " + nowyPrzedmiot);
        } else {
            System.out.println("Ten użytkownik nie jest uprawniony do dodania przedmiotu.");
        }
    }

    public double policzWolnaObjetosc(){
        double objetosc = 0.0;
        //Do zmiennej "objetosc" dodajemy objetosc kazdego z przedmiotu w pomieszczeniu
        for (Przedmiot p : this.przedmoty){
            objetosc += p.objetosc;
        }
        return this.powierzchnia - objetosc; //od calkowitej powierzchni odejmujemy objetosc zajeta
    }

    public void usunPrzedmiot(Person potencjalnyUzytkownik, Przedmiot o) {
        //zwracamy z listy od razu

        //jesli zwroci true sprawdza czy potencjalny uzytkwonik zawiera sie w liscie uprawnionych
        if (this.uprawnieni.contains(potencjalnyUzytkownik)) { //na pozycji 0 znajduje sie wlascicel
            this.przedmoty.remove(o);
            System.out.println("Udało się usunąć obiekt: " + o);
        } else {
            System.out.println("Ten użytkownik nie jest uprawniony do usuwania przedmiotu.");
        }
    }

    public void wynajmij(Person nowyWlasciciel, Date dataNajmu, Date dataZakonczeniaNajmu) throws NeverRentedException, ProblematicTenantException {

        if (nowyWlasciciel.ostrzezenia.size() > 3) {

            //Iteracja po wszystkich TentAlerts tej osoby
            int sumaZadluzenia = 0;
            String listaZadluzonychObiektow = ""; //pusty by dodac
            for (TenantAlert ta : nowyWlasciciel.ostrzezenia) {

                //Jezeli pole "pomieszczenie" tego obiektu TenantAlert nie jest nullem, to znaczy ze ten TenantAlert
                //dotyczy pomieszczenia a nie miejsca parkingowego
                if (ta.pomieszczenie != null) {
                    sumaZadluzenia += ta.pomieszczenie.kosztNajmu;
                    listaZadluzonychObiektow += ta.pomieszczenie.pomieszczenieId + ", ";
                } else {
                    //Jezeli pole "pomieszczenie" było puste, to znaczy ze ten TenantAlert dotyczy miejscaParkingowego
                    sumaZadluzenia += ta.parkingSpace.kosztNajmu;
                    listaZadluzonychObiektow += ta.parkingSpace.miejsceParkingoweId + ", ";
                }
            }

            throw new ProblematicTenantException("Nie można wynająć ponieważ ta osoba miała już następujące wynajmy: "
                    + listaZadluzonychObiektow + " - " + sumaZadluzenia + " zł.");
        }

        if (nowyWlasciciel.sumaryczneKosztyNajmu + this.kosztNajmu > 1250) {
            System.out.println("Nie udało się wynająć pomieszczenia - zbyt duży koszt");
        } else {
            try{
                nowyWlasciciel.getDataPierwszegoNajmu();
                /// jak usuwamy z czyjegos portofolio miejsce parkingowe to usuwamy z jego kosztow
            }catch (NeverRentedException nre){
                nowyWlasciciel.setDataPierwszegoNajmu(dataNajmu);
            }
            nowyWlasciciel.sumaryczneKosztyNajmu += this.kosztNajmu;
            this.dataNajmu = dataNajmu;
            this.uprawnieni.add(nowyWlasciciel);
            this.dataZakonczeniaNajmu = dataZakonczeniaNajmu;
            System.out.println("Udało się wynająć pomieszczenie " + this.pomieszczenieId);
        }

    }

    public void oplac(Person wlasciciel) {
        // Oplacenie jest jednoznaczne z zakonczeniem umowy
        if (wlasciciel == this.uprawnieni.get(0)) {
            //Usuwanie ostrzezen z dokumentacji wlasciciela
            for (int i = 0; i < wlasciciel.ostrzezenia.size(); i++) {
                try {
                    // Sprwadzenie czy miejsce parkingowe znajduje sie na TenantAlercie jest miejscem parkingowym
                    // w ktorym jestesmy
                    if (wlasciciel.ostrzezenia.get(i).pomieszczenie == this) {
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
            this.dataZakonczeniaNajmu = null;
            this.dataNajmu = null;
            this.uprawnieni.clear(); //czyscimy bo blista, oznacza ze jest pusta
            this.przedmoty.clear();

        } else {
            System.out.println("Ta osoba nie jest właścicielem i nie może opłacić miejsca.");
        }
    }

    @Override
    public String toString() {
        return "Pomieszczenie " + this.pomieszczenieId + '(' +
                this.powierzchnia + ')';
    }
}

