import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Comparator;

public class ConsumerWarehouse {

    //Magazyn

    ArrayList<Pomieszczenie> pomieszczenia;
    int wielkosc;

    public ConsumerWarehouse(int powierzchnia, int powierzchniaPomieszczenia, boolean czyGenerowacPomieszczenia) {
        //powierzchnia - cała powierzchnia magazynu
        //powierzchniaPomieszczenia - cała powierzchnia

        this.wielkosc = powierzchnia;
        //powierzchniaPomieszczenia = x
        //Zakladamy ze kazde pomieszczenie ma x m3

        this.pomieszczenia = new ArrayList<>(); // inicjalizujemy liste
        if (czyGenerowacPomieszczenia) {
            //Jezli zostanie mniej niz xm3 powierzchni, jest ono ignorowane
            for (int i = 0; i < powierzchnia / powierzchniaPomieszczenia; i++) { //dzielimy na x m2
               this.pomieszczenia.add(
                        new Pomieszczenie(powierzchniaPomieszczenia)
                );
            }
        }
    }


    public void zapiszInformacje() {
        this.pomieszczenia.sort(new Comparator<Pomieszczenie>() {
            @Override
            //Metoda zwraca liczbe dodatnia jezeli pierwsze pomieszczenie jest wieksze, ujemna jezeli jest mniejsze,
            // 0 jezeli sa sobie rowne. Potem sortujemy
            public int compare(Pomieszczenie o1, Pomieszczenie o2) {
                if (o1.powierzchnia > o2.powierzchnia) {
                    return 1;
                } else if (o1.powierzchnia < o2.powierzchnia) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });

        for (Pomieszczenie p : this.pomieszczenia) {
            p.przedmoty.sort(new Comparator<Przedmiot>() {
                @Override
                public int compare(Przedmiot o1, Przedmiot o2) {
                    if (o1.objetosc > o2.objetosc) {
                        return 1;
                    } else if (o1.objetosc < o2.objetosc) {
                        return -1;
                    } else {
                        if (o1.nazwa.length() > o2.nazwa.length()) {
                            return 1;
                        } else if (o1.nazwa.length() < o2.nazwa.length()) {
                            return -1;
                        } else {
                            return 0;
                        }
                    }
                }
            });
        }

        //Tworzenie tresci pliku
        String saveWarehouse = "";

        for (Pomieszczenie pomieszczenie : this.pomieszczenia) {
            saveWarehouse += pomieszczenie.toString() + ":\n"; // toString dla czytelnosci
            //używam pętli for each by iterowanie po liscie przedmiotach pomieszczenia, znajdujacego sie pod zmienna "pomieszcenie". Co iteracje powyzej
            // petli, wartosc "pomieszczenia" bedzie sie zmieniac.
            for (Przedmiot przedmiot : pomieszczenie.przedmoty) {
                saveWarehouse += przedmiot.toString() + ":\n";
            }

        }

        //Zapisywanie do pliku

        try {
            OutputStream out = new FileOutputStream(new File("Warehouse.txt"));
            out.write(saveWarehouse.getBytes());
            out.flush(); // używam by na pewno wszystkie dane wyslac z OutPutStream do pliku
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void dodajPomieszczenie(Pomieszczenie pomieszczenie) {
        int zajetaPowierzchnia = 0;
        for (Pomieszczenie p : this.pomieszczenia) {
            zajetaPowierzchnia += p.powierzchnia;
        }
        if (this.wielkosc - zajetaPowierzchnia > pomieszczenie.powierzchnia) {
            this.pomieszczenia.add(pomieszczenie);
            System.out.println("Udało się dodać pomieszczenie");
        } else {
            System.out.println("Zabrakło miejsca na to pomieszczenie");
        }
    }
}
