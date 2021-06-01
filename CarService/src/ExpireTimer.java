import java.util.Calendar;
import java.util.Date;

public class ExpireTimer extends Thread {

    static Date data = new Date();

    static boolean czyJuzIstnieje = false;

    ExpireTimer(){
        if(czyJuzIstnieje){
            System.out.println("Instancja ExpireTimer już istnieje");
            return; //zeby nie zwrocic obiektu
        }
        czyJuzIstnieje = true; //zwroci obiekt

        }
    public void checkExpire() {
        // Iterowanie po miejscach parkingowych
        for (ParkingSpace mp : ParkingSpace.parkingSpaces) { // z klasy ParkingSpace wyciagam zmienna statyczna
            if (mp.dataZakonczenia != null) { // jezeli jest data zakonczenie
                if (mp.dataZakonczenia.before(data)) {

                    if (countDayDiff(ExpireTimer.data, mp.dataZakonczenia) > 30) {
                        if (mp.pojazd != null){
                            //Odcholowanie
                            mp.pojazd = null;
                        }

                        //Czyszczenie miejsca parkingowego
                        mp.wlasciciel.ostrzezenia.add(new TenantAlert("Zostałeś usunięty z miejsca parkingowego.", mp, data));
                        mp.wlasciciel = null;
                        mp.dataZakonczenia = null;
                        mp.dataZajecia = null;

                    } else {
                        mp.wlasciciel.ostrzezenia.add(new TenantAlert("Twoje wynajęte miejsce parkingowe jest przeterminowawne.", mp, data));
                    }
                }
            }

        }

        for (Pomieszczenie p : Pomieszczenie.pomieszczenia) {
            if (p.dataZakonczeniaNajmu != null) { // jezeli jest data zakonczenie
                if (p.dataZakonczeniaNajmu.before(data)) {

                    if (countDayDiff(ExpireTimer.data, p.dataZakonczeniaNajmu) > 30) {
                        //Czyszczenie miejsca parkingowego
                        p.uprawnieni.get(0).ostrzezenia.add(new TenantAlert("Zostałeś usunięty z miejsca magazynowego.", p, data));
                        p.uprawnieni.clear();
                        p.dataZakonczeniaNajmu = null;
                        p.dataNajmu = null;

                    } else {
                        p.uprawnieni.get(0).ostrzezenia.add(new TenantAlert("Twój wynajem magazynu jest przterminowany.", p, data));
                    }
                }
            }

        }
    }



    @Override
    public void run() {

        for (int i = 0; i < 100; i++) {
            checkExpire();

            if(i%7==0){
                System.out.println("Minął tydzień: " + data);
            }
            try {
                sleep(5000);
            } catch (InterruptedException e) {
                //Nastąpi po wywołaniu metody ".interrupt()" na tym obiekcie}
                break; // break zakonczy petle;
            }
        }
    }

    public static long countDayDiff(Date startDate, Date endData){

        return Math.abs(endData.getTime() - startDate.getTime()) / 86_400_000;
    }

    public static Date daysLater(int iloscDni) {
        Calendar c = Calendar.getInstance();
        c.setTime(data);
        c.add(Calendar.DATE, iloscDni); // o jeden dzien do przodu
        return c.getTime();
    }

}
