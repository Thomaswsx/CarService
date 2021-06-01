import java.util.Date;

public class TenantAlert {

    String tresc;
    ParkingSpace parkingSpace; //albo ten wypelniony
     Pomieszczenie pomieszczenie; //albo ten wypelniony
    Date dataNadania;

    public TenantAlert(String tresc) {
        this.tresc = tresc;
        this.dataNadania = new Date(); //data dziesiejsza
    }

    public TenantAlert(String tresc, ParkingSpace parkingSpace, Date dataNadania) {
        this.tresc = tresc;
        this.parkingSpace = parkingSpace;
        this.dataNadania = dataNadania; //data dziesiejsza
    }

    public TenantAlert(String tresc, Pomieszczenie pomieszczenie, Date dataNadania) {
        this.tresc = tresc;
        this.pomieszczenie = pomieszczenie;
        this.dataNadania = dataNadania; //data dziesiejsza
    }

    @Override
    public String toString() {
        return "Tenant Alert: " + tresc + " " + (pomieszczenie == null ? parkingSpace : pomieszczenie) + " dataNadania " + dataNadania;
    }
}

