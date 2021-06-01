import java.util.ArrayList;
import java.util.Date;

public class Person {

    static ArrayList<Person> persons = new ArrayList<>();
    static int staticPersonId = 0; //liczy ile osob zostalo utworzyonych

    String imie;
    String nazwisko;
    String adresZamieszkania;
    long pesel;
    Date data_urodzenia;
    int sumaryczneKosztyNajmu;
    ArrayList<TenantAlert> ostrzezenia;
    private Date dataPierwszegoNajmu;
    String personId; // String by dodac "Pers" + jakas liczba np Per123



    public Person(String imie, String nazwisko, String adresZamieszkania, long pesel, Date data_urodzenia) throws Exception{ //zabezpieczamy przed wyrzuceniem

        for(Person p : persons){ //przy kazdym obrocie petli będzie wpadała kolejna osoba
            if(p.pesel == pesel){
                throw new Exception("Osoba o takim peselu już istnieje!");
            }
        }

        this.imie = imie;
        this.nazwisko = nazwisko;
        this.adresZamieszkania = adresZamieszkania;
        this.pesel = pesel;
        this.data_urodzenia = data_urodzenia;
        this.sumaryczneKosztyNajmu = 0;
        this.ostrzezenia = new ArrayList<>();
        this.personId = "Pers" + staticPersonId;
        staticPersonId++;

        persons.add(this);
    }

    public Date getDataPierwszegoNajmu() throws NeverRentedException { //z tej metody moze wypasc blad
        if(dataPierwszegoNajmu == null){
            throw  new NeverRentedException("Nigdy nie dokonano wynajmu.");
        }
        return dataPierwszegoNajmu;
    }

    public void setDataPierwszegoNajmu(Date dataPierwszegoNajmu)  {
        this.dataPierwszegoNajmu = dataPierwszegoNajmu;
    }

    @Override
    public String toString() {
        return "Person: " +
                "imie: " + imie + '\'' +
                ", nazwisko: " + nazwisko + '\'' +
                ", adresZamieszkania: " + adresZamieszkania + '\'' +
                ", pesel: " + pesel +
                ", data_urodzenia: " + data_urodzenia;
    }
}
