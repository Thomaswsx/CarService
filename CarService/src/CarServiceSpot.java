import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

public class CarServiceSpot {

    //Serwis

    String nazwa;
    int powierzchniaSerwisowa;
    int powierzchniaMagazynowa;
    int powierzchniaMagazynowaSerwisu;
    ArrayList<ParkingSpace> miejscaParkingowe;
    ArrayList<ServiceWarehouse> miejscaSerwisowe;
    ConsumerWarehouse consumerWarehouse;


    public CarServiceSpot(String nazwa, int powierzchniaSerwisowa, int powierzchniaMagazynowa, int powierzchniaMagazynowaSerwisu,
                          int procentMagazynowaDlaSerwisu, int powierzchniaParkingowa, int wielkoscMiejscaParkingowego, int ileMiejscSerwisowych, int wielkoscPomieszcznenia) {
        this.nazwa = nazwa;
        this.powierzchniaSerwisowa = powierzchniaSerwisowa;
        this.powierzchniaMagazynowaSerwisu = powierzchniaMagazynowaSerwisu * procentMagazynowaDlaSerwisu / 100;
        this.powierzchniaMagazynowa = powierzchniaMagazynowa - this.powierzchniaMagazynowaSerwisu;
        this.miejscaParkingowe = new ArrayList<>(); //tworzenie obiektu listy (pustej listy)
        this.miejscaSerwisowe = new ArrayList<>();
        this.consumerWarehouse = new ConsumerWarehouse(powierzchniaMagazynowa, wielkoscPomieszcznenia,false);

        //Zalozenie : miejsce parkingowe ma x m2
        for (int i = 0; i < powierzchniaParkingowa / wielkoscMiejscaParkingowego; i++) { // Jeden obrot petli - > jedno nowe miejsce
            this.miejscaParkingowe.add(
                    new ParkingSpace(wielkoscMiejscaParkingowego) // x - > xm2 powierzchni jednego miejsca
            );
        }

        //Tworzenie miejsc serwisowych

        for (int i = 0; i < ileMiejscSerwisowych; i++) {
            miejscaSerwisowe.add(new ServiceWarehouse());

        }
    }

    //Zapisywanie informacji o odbytych i trwajacych naprawach
    public void  zapiszInformacjeOMiejscachSerwisowych() {

        //Zapisywanie do pliku

        String saveService = "";
        for (ServiceWarehouse ms : this.miejscaSerwisowe) {
            for (String s : ms.historiaNapraw) {
                saveService += s + "\n";
            }

            if (ms.pojazd != null) { //jesli stoi jakis pojazd
                saveService += "Obecnie jest tu naprawiany pojazd " + ms.pojazd + " od dnia: " + ms.pojazd;
            }
        }

        try {
            OutputStream out = new FileOutputStream(new File("Services.txt"));
            out.write(saveService.getBytes());
            out.flush(); // używam by na pewno wszystkie dane wyslac z OutPutStream do pliku!
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}