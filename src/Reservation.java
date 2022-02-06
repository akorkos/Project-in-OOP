import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;
import java.io.Serializable;

/**
 * Αναπαριστά μια κράτηση που πραγματοποιείται στο σύστημα, ενω αποθηκεύει και τις πληροφορίες της κράτησης.
 */
public class Reservation implements Serializable {
    private String reservationID, customerID;
    private LocalDate start, end;

    public Reservation(String customerID, LocalDate start, LocalDate end){
        this.customerID = customerID;
        this.start = start;
        this.end = end;
        this.reservationID = UUID.randomUUID().toString();
    }

    /**
     * Setters & getters της κλάσης.
     */

    public String getReservationID(){
        return reservationID;
    }

    public LocalDate getReservationStart() {
        return start;
    }

    public LocalDate getReservationEnd() {
        return end;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }

    @Override
    public String toString(){
        return "Κωδικός κράτησης: " + reservationID + " Από: " + start + " Εώς: " + end;
    }

}