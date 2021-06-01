import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {

        //Wątek obslugujący inteakcje z użytkownikiem, tak by nie kolidował z wątkiem mierzącym czas.
        Thread userInterface = new Thread() { //Anonimowa deklracja wątku.

            @Override
            public void run() {
                Scanner sc = new Scanner(System.in);
                Person currentPerson = null;
                CarServiceSpot carServiceSpot = new CarServiceSpot("Tomek Cars", 300, 500, 50, 20, 10, 2, 10, 8);
                ExpireTimer ex = new ExpireTimer();
                ex.start();
                System.out.println("Witaj w " + carServiceSpot.nazwa + "!");

                //Tworzenie bazy osób
                try {
                    Person.persons.add(new Person("Adam", "Zabłocki", "Kraków", 123456789, new Date()));
                    Person.persons.add(new Person("Ania", "Kowalska", "Warszawa", 113456789, new Date()));
                    Person.persons.add(new Person("Bob", "Hess", "Radom", 123956789, new Date()));
                    Person.persons.add(new Person("Grażyna", "Jerzyna", "Kraków", 923456789, new Date()));
                    Person.persons.add(new Person("Janusz", "Dzik", "Kraków", 923456781, new Date()));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                carServiceSpot.consumerWarehouse.dodajPomieszczenie(new Pomieszczenie(10));
                carServiceSpot.consumerWarehouse.dodajPomieszczenie(new Pomieszczenie(20));
                carServiceSpot.consumerWarehouse.dodajPomieszczenie(new Pomieszczenie(8));
                carServiceSpot.consumerWarehouse.dodajPomieszczenie(new Pomieszczenie(3));
                carServiceSpot.consumerWarehouse.dodajPomieszczenie(new Pomieszczenie(12));
                carServiceSpot.consumerWarehouse.dodajPomieszczenie(new Pomieszczenie(15));
                carServiceSpot.consumerWarehouse.dodajPomieszczenie(new Pomieszczenie(9));
                carServiceSpot.consumerWarehouse.dodajPomieszczenie(new Pomieszczenie(7));
                carServiceSpot.consumerWarehouse.dodajPomieszczenie(new Pomieszczenie(11));
                carServiceSpot.consumerWarehouse.dodajPomieszczenie(new Pomieszczenie(13));
                carServiceSpot.consumerWarehouse.dodajPomieszczenie(new Pomieszczenie(5));

                mainloop:
                while (true) {
                    System.out.println("Co chcesz zrobić?\nDostępne opcje:\n\t" +
                            "-koniec\n\t" +
                            "-zaloguj\n\t" +
                            "-mojedane\n\t" +
                            "-wolnepomieszczenia\n\t" +
                            "-wynajmij\n\t" +
                            "-podejrzyjzawartoscpomieszczenia\n\t" +
                            "-zaparkuj\n\t" +
                            "-dodajprzedmiot\n\t" +
                            "-usunprzedmiot\n\t" +
                            "-zglosnaprawe\n\t" +
                            "-rozpocznijnaprawe\n\t" +
                            "-zapisz");
                    switch (sc.next().toLowerCase()) {
                        case "koniec":
                            System.out.println("Program zamknięty. Do widzenia.");
                            System.exit(0);

                        case "zaloguj":
                            System.out.println("Podaj swoje ID");
                            String userid = sc.next();
                            for (Person p : Person.persons) {
                                if (p.personId.equals(userid)) {
                                    currentPerson = p;
                                    System.out.println("Użytkownik " + p.imie + " " + p.nazwisko + " zalogowany.");
                                    continue mainloop;
                                }
                            }

                            System.out.println("Nie znaleziono użytkownika. Użytkownik nie zalogowany.");
                            break;


                        case "mojedane":
                            if (currentPerson == null) {
                                System.out.println("Żaden użytkownik nie jest zalogowany");
                                continue;
                            }
                            System.out.println("mojedane:\nImie:" + currentPerson.imie + "\nNaziwsko:" + currentPerson.nazwisko +
                                    "\nPesel:" + currentPerson.pesel + "\nDataurodzenia:" + currentPerson.data_urodzenia +
                                    "\nID:" + currentPerson.personId);

                            String wynajetePomieszczenia = "Wynajete pomieszczenia:\n";
                            for (Pomieszczenie p : carServiceSpot.consumerWarehouse.pomieszczenia) {

                                if (p.uprawnieni.size() != 0 && p.uprawnieni.get(0) == currentPerson) {
                                    wynajetePomieszczenia += p + "\n";
                                }
                            }

                            System.out.println(
                                    wynajetePomieszczenia.equals("Wynajete pomieszczenia:\n") ? "Brak wynajetych pomieszczen" : wynajetePomieszczenia
                            );

                            if (currentPerson.ostrzezenia.size() != 0) {
                                System.out.println("Ostrzeżenia (TenantAlerts):");
                                for (TenantAlert ta : currentPerson.ostrzezenia) {
                                    System.out.println(ta);
                                }
                            }

                            break;

                        case "wolnepomieszczenia":
                            String wolnePomieszczenia = "Wolne pomieszczenia:\n ";


                            for (Pomieszczenie p : carServiceSpot.consumerWarehouse.pomieszczenia) {
                                if (p.uprawnieni.size() == 0) {
                                    wolnePomieszczenia += "\t-" + p.pomieszczenieId + "\n";
                                }
                            }

                            System.out.println(wolnePomieszczenia.equals("Wolne pomieszczenia:\n ") ? "Brak wolnych pomieszczeń" : wolnePomieszczenia);

                            break;

                        case "wynajmij":

                            if (currentPerson == null) {
                                System.out.println("Żaden użytkownik nie jest zalogowany");
                                continue;
                            }

                            System.out.println("Podaj ID pomieszczenia do wynajecia");
                            String pomieszczenieID = sc.next();
                            //sprawdzenie czy ID istnieje
                            Pomieszczenie potencjalnyNajem = null;
                            for (Pomieszczenie p : carServiceSpot.consumerWarehouse.pomieszczenia) {
                                if (p.pomieszczenieId.equals(pomieszczenieID)) {
                                    potencjalnyNajem = p;
                                    break;
                                }
                            }

                            if (potencjalnyNajem == null) {
                                System.out.println("Nie znaleziono pomieszczenia o tym ID");
                                continue;
                            }

                            if (potencjalnyNajem.uprawnieni.size() != 0) {
                                System.out.println("To pomieszczenie jest już zajęte.");
                                continue;
                            }

                            System.out.println("Na ile dni chcesz wynająć pomieszczenie?");


                            try {
                                int ileDni = Integer.parseInt(sc.next());
                                potencjalnyNajem.wynajmij(currentPerson, ExpireTimer.data, ExpireTimer.daysLater(ileDni));
                            } catch (NeverRentedException e) {
                                e.printStackTrace();
                            } catch (ProblematicTenantException e) {
                                e.printStackTrace();
                            } catch (NumberFormatException nfe) {
                                System.out.println("Podano nieprawidłową liczbę dni.");
                            }
                            break;

                        case "podejrzyjzawartoscpomieszczenia":
                            podejrzyjZawartoscPomieszczenia(carServiceSpot, currentPerson, sc);
                            break;

                        case "zaparkuj":
                            if (currentPerson == null) {
                                System.out.println("Żaden użytkownik nie jest zalogowany");
                                continue;
                            }
                            Pojazd p = budowaPojazdu(sc);
                            System.out.println("Podaj ilosć dni:");
                            int iloscDni = 0;
                            try {
                                iloscDni = Integer.parseInt(sc.next());
                            } catch (NumberFormatException e) {
                                System.out.println("Podano niewlasciwą ilość dni");
                                continue;
                            }
                            for (ParkingSpace mp : carServiceSpot.miejscaParkingowe) {
                                if (mp.wlasciciel == null) {
                                    mp.wynajmij(currentPerson, ExpireTimer.daysLater(iloscDni), ExpireTimer.data);
                                    continue mainloop;
                                }
                            }
                            System.out.println("Niestety brakuje wolnych miejsc.");
                            break;

                        case "usunprzedmiot":
                            Pomieszczenie wybranePomieszczenie = podejrzyjZawartoscPomieszczenia(carServiceSpot, currentPerson, sc);
                            if (wybranePomieszczenie == null) {
                                continue;
                            }

                            System.out.println("Ktory przemiot usunąć (podaj index)?");
                            try {
                                wybranePomieszczenie.przedmoty.remove(Integer.parseInt(sc.next()));
                                System.out.println("Udalo sie usunąć przedmiot");
                            } catch (NumberFormatException e) {
                                System.out.println("Podano nieprawidłową liczbe");
                            } catch (IndexOutOfBoundsException ie) {
                                System.out.println("Nie ma takiego indeksu");

                            }
                            break;

                        case "dodajprzedmiot":
                            Pomieszczenie wybranePomieszczenieDodaj = podejrzyjZawartoscPomieszczenia(carServiceSpot, currentPerson, sc);
                            if (wybranePomieszczenieDodaj == null) {
                                continue;
                            }
                            System.out.println("Podaj nazwe przedmiotu");
                            String nazwa = sc.next();
                            System.out.println("Podaj objetosc przedmiotu: " + nazwa);
                            try {
                                double obj = Double.parseDouble(sc.next());
                                wybranePomieszczenieDodaj.dodajPrzedmiot(currentPerson, new Przedmiot(nazwa, obj));
                            } catch (NumberFormatException nfe) {
                                System.out.println("Podano nieprawidłową liczbę.");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            break;

                        case "zglosnaprawe":

                            if (currentPerson == null) {
                                System.out.println("Nie można zgłosić naprawy - brazk zalogowanego użytkownika.");
                                continue;
                            }

                           ServiceWarehouse wybraneMs = null;
                            //Szukanie pierwszego wolnego miejsca serwisowego.
                            for (ServiceWarehouse ms : carServiceSpot.miejscaSerwisowe) {
                                if (ms.pojazd == null) {
                                    wybraneMs = ms;
                                    break;
                                }
                            }
                            //Sprawdzenie czy znaleziono miejsce
                            if (wybraneMs == null) {
                                System.out.println("Niestety wszystkie miejsca serwisowe są zajęte");
                                continue;
                            }

                            Pojazd pojazd = budowaPojazdu(sc);
                            if (pojazd == null) {
                                //Czyli z jakiegoś powdou nie udalo się utworzyć pojazdu.
                                continue;
                            }
                            System.out.println("Dodawanie auta zakończone sukcesem.");

                            System.out.println("Czy naprawa będzie wykonywana przez mechanika?");
                            boolean czyNaprawiaMechanik = sc.next().toLowerCase().equals("tak");
                            wybraneMs.zglosNaprawe(pojazd, czyNaprawiaMechanik, currentPerson);
                            break;


                        case "rozpocznijnaprawe":
                            ArrayList<ServiceWarehouse> dostepneMiejscaLista = new ArrayList<>();
                            String dostepneMiejsca = "Dostępne miejsca:\n";
                            for (ServiceWarehouse ms : carServiceSpot.miejscaSerwisowe) {
                                if (ms.wlascicielPojazdu == currentPerson) {
                                    dostepneMiejsca += ms.toString();
                                    dostepneMiejscaLista.add(ms);
                                }
                            }

                            if (dostepneMiejscaLista.size() == 0) {
                                System.out.println("Niestety żadne z miejsc serwisowych nie posiada twojego auta.");
                                continue;
                            }
                            System.out.println(dostepneMiejsca);
                            System.out.println("Podaj index miejsca, na którym chcesz rozpocząć naprawę");
                            try {
                                int index = Integer.parseInt(sc.next());
                                dostepneMiejscaLista.get(index).rozpocznijNaprawe(ExpireTimer.data);
                                System.out.println("Udało się rozpocząć naprawę.");
                            } catch (NumberFormatException nfe) {
                                System.out.println("Podaną nieprawidłowy numer");

                            } catch (IndexOutOfBoundsException iob) {
                                System.out.println("Podano nieistniejący index");
                            }
                            break;

                        case "zapisz":
                            carServiceSpot.zapiszInformacjeOMiejscachSerwisowych();
                            carServiceSpot.consumerWarehouse.zapiszInformacje();
                            System.out.println("Udało się zapisać.");
                            break;

                        default:
                            System.out.println("Nieznane polecenie");

                    }
                }


            }
        };


        userInterface.start();

    }


    public static Pomieszczenie podejrzyjZawartoscPomieszczenia(CarServiceSpot carServiceSpot, Person currentPerson, Scanner sc) {
        String wynajetePomieszczenia = "Wynajęte pomieszczenia:\n";
        ArrayList<Pomieszczenie> pomieszczeniaOsoby = new ArrayList<>();
        for (Pomieszczenie p : carServiceSpot.consumerWarehouse.pomieszczenia) {
            if (p.uprawnieni.size() != 0 && p.uprawnieni.get(0) == currentPerson) {
                pomieszczeniaOsoby.add(p);
                wynajetePomieszczenia += p + "\n";
            }
        }

        if (pomieszczeniaOsoby.size() == 0) {
            System.out.println("Nie znaleziono wynajętych pomieszczeń");
            return null;
        }
        System.out.println(wynajetePomieszczenia);
        System.out.println("Wybierz ID:");
        String wybranePomieszczenie = sc.next();

        for (Pomieszczenie p : pomieszczeniaOsoby) {
            if (p.pomieszczenieId.equals(wybranePomieszczenie)) {
                System.out.println("Zawartość pomieszczenia:\n");
                for (Przedmiot przedmiot : p.przedmoty) {
                    System.out.println("\t-" + przedmiot + "\n");
                }
                return p;
            }
        }

        return null;
    }

    static Pojazd budowaPojazdu(Scanner sc) {

        System.out.println("Podaj Kolor");
        String kolor = sc.next();
        System.out.println("Podaj rodzaj paliwa");
        String paliwo = sc.next();
        System.out.println("Wybierz rodzaj pojazdu:\n\t-Auto\n\t-Motocykl");
        switch (sc.next().toLowerCase()) {
            case "auto":
                System.out.println("Ile drzwi?");
                int iledrzwi;
                try {
                    iledrzwi = Integer.parseInt(sc.next());
                } catch (NumberFormatException nfe) {
                    System.out.println("Podana liczba drzwi jest niepoprawna. Tworzenie auta zakończone porażką");
                    return null;
                }
                System.out.println("Podaj markę radia");
                String markaRadia = sc.next();
                System.out.println("Podaj czy chcesz dodać auto miejskie, terenowe czy amfibie?");
                switch (sc.next().toLowerCase()) {
                    case "terenowe":
                        System.out.println("Podaj rodzaj opon");
                        String rodzajOpon = sc.next();
                        System.out.println("Czy auto ma ogrzewanie?");
                        boolean czyMaOgrzewanie = sc.next().toLowerCase().equals("tak");
                        return new SamochodTerenowy(kolor, paliwo, iledrzwi, markaRadia, rodzajOpon, czyMaOgrzewanie);

                    case "miejskie":
                        System.out.println("Czy jest elektryczne?");
                        boolean czyJestElektryczny = sc.next().toLowerCase().equals("tak");
                        System.out.println("Czy jest w automacie?");
                        boolean czyJestWAutomacie = sc.next().toLowerCase().equals("tak");
                        return new SamochodMiejski(kolor, paliwo, iledrzwi, markaRadia, czyJestElektryczny, czyJestWAutomacie);

                    case "amfibia":
                        System.out.println("Podaj rodzaj opon");
                        String rodzajOponAmf = sc.next();
                        System.out.println("Czy auto ma ogrzewanie?");
                        boolean czyMaOgrzewanieAmf = sc.next().toLowerCase().equals("tak");
                        System.out.println("Podaj wyporność (w kg)");
                        int wypornosc;
                        try {
                            wypornosc = Integer.parseInt(sc.next());
                        } catch (NumberFormatException nfe) {
                            System.out.println("Podana wyporność nie jest poprawną liczbą. Dodawanie auta zakończone" +
                                    "porażką.");
                            return null;
                        }
                        return new Amfibia(kolor, paliwo, iledrzwi, markaRadia, rodzajOponAmf, czyMaOgrzewanieAmf, wypornosc);

                    default:
                        System.out.println("Podano niepoprawną opcję. Dodawanie auta zakończone porażką");
                        return null;
                }


            case "motocykl":
                System.out.println("Podaj typ motocykla");
                String typMotocykla = sc.next();
                System.out.println("Czy motocykl jest głośny?");
                boolean czyGlosny = sc.next().toLowerCase().equals("tak");
                return new

                        Motocykl(kolor, paliwo, typMotocykla, czyGlosny);


            default:
                System.out.println("Nieznany rodzaj pojazdu.");
                return null;


        }
    }
}
