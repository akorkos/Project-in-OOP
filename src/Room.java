import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;


/**
 * Η κλάση αυτή αναπαριστά ένα δωμάτιο και τα χαρακτηριστικά του. Περιέχει μεθόδους για την διαχείριση ενός δωματίου,
 * όπως αυτή της προσθήκης κράτησης σε λίστα όταν το δωμάτιο κρατηθεί, εξετάσει εαν το δωμάτιο είναι διαθέσιμο για κάποιες
 * ημερομηνίες.
 * Επιπλέον, εδω αποθηκεύεται πότε ένα δωμάτιο είναι διαθέσιμο και ένας μοναδικός αριθμός ταυτοποίησης δωματίου.
 */
public class Room implements Serializable {
    /**
     * Χαρακτηρίστηκα που πρέπει να έχει ένα δωμάτιο όταν καταχωρείται στο σύστημα
     */
    private Integer price, area, beds;
    private Boolean wifi, parking, breakfast, kitchen, ac;
    private String type, name, location, roomID; // Μοναδικός αριθμός ταυτοποίησης δωματίου
    private final String providerID;
    private LocalDate start, end; // Ημερομηνίες οπού το δωμάτιο θα είναι διαθέσιμο για κράτηση
    private ArrayList<LocalDate> freeDates; // Αποθήκευση ημερομηνιών όπου το δωμάτιο είναι διαθέσιμο για κράτηση
    private ArrayList<Reservation> reservations; // Αποθήκευση των κρατήσεων που έχουν πραγματοποιηθεί επί του δωματίου

    public Room(String type, String name, String location, int price, int area, int beds, boolean wifi, boolean parking, boolean breakfast, boolean kitchen, boolean ac, LocalDate start, LocalDate end, String providerID){
        freeDates = new ArrayList<>();
        reservations = new ArrayList<>();
        this.area = area;
        this.location = location;
        this.name = name;
        this.kitchen = kitchen;
        this.breakfast = breakfast;
        this.parking = parking;
        this.wifi = wifi;
        this.type = type;
        this.price = price;
        this.ac = ac;
        this.beds = beds;
        this.start = start;
        this.end = end;
        this.providerID = providerID;
        this.roomID = UUID.randomUUID().toString();
        // "Γεμίζει" την λίστα με τις ημερομηνίες που το δωμάτιο θα είναι διαθέσιμο
        freeDates.addAll(calculateDates(start, end));
    }

    /**
     * Καλείται απο την μέθοδο κράτησης του Customer, αποθηκεύει τις ημερομηνίες που το δωμάτιο θα κρατηθεί (εάν είναι ελεύθερο)
     * καθώς και τον μοναδικό αριθμό ταυτοποίησης χρήστη που πραγματοποιεί την κράτηση. Επιπλέον, αφαιρεί της ημέρες κράτησης
     * απο την λίστα διαθεσιμότητας.
     * @param userID Ο μοναδικός αριθμός ταυτοποίησης χρήστη,
     * @param start έναρξη κράτησης,
     * @param end λήξη κράτησης.
     * @return επιστρέφει την κράτηση.
     */
    public Reservation reserve(String userID, LocalDate start, LocalDate end){
        removeFromFreeDates(start, end);

        Reservation reservation = new Reservation(userID, start, end);
        reservations.add(reservation);

        return reservation;
    }

    @Override
    public String toString(){
        return "Τύπος: " + type + ", όνομα: " + name + ", τοποθεσία: " + location + ", τιμή ανά διανυκτέρευση: " + price + ", επιφάνεια: " + area + ", κρεβάτια:" + beds + ", WiFi: " + wifi + ", πάρκινγκ: " + parking + ", πρωινό: " + breakfast + ", κουζίνα: " + kitchen + ", κλιματισμός: " + ac;
    }

    /**
     * @return Επιστρέφει τις ημερομηνίες όπου το δωμάτιο είναι ελεύθερο.
     */
    public ArrayList<LocalDate> getFreeDates(){
        return freeDates;
    }

    /**
     * @return Επιστρέφει τις κρατήσεις του εξής δωματίου.
     */
    public ArrayList<Reservation> getReservations(){
        return reservations;
    }

    /**
     * Ενημερώνει την λίστα κρατήσεων του δωματίου.
     * @param reservations Η νέα λίστα κρατήσεων του δωματίου.
     */
    public void setReservations(ArrayList<Reservation> reservations) {
        for (Reservation reservation : reservations)
            removeFromFreeDates(reservation.getReservationStart(), reservation.getReservationEnd());
        this.reservations = reservations;
    }

    /**
     * Ενημερώνει την λίστα ημερομηνιών προς κράτηση του δωματίου.
     * @param freeDates Η νέα λίστα ημερομηνιών του δωματίου.
     */
    public void setFreeDates(ArrayList<LocalDate> freeDates){
        this.freeDates = freeDates;
        Collections.sort(this.freeDates);
    }

    /**
     * Εξετάζει εάν το δωμάτιο είναι διαθέσιμο για κάποιες συγκεκριμένες ημερομηνίες.
     * @param start Έναρξη επιθυμητής κράτησης,
     * @param end λήξη επιθυμητής κράτησης,
     * @return αληθής εάν το δωμάτιο είναι ελεύθερο για εκείνες τις ημερομηνίες, ψευδής διαφορετικά.
     */
    public ArrayList<LocalDate> calculateDates(LocalDate start, LocalDate end){
        ArrayList<LocalDate> dates = new ArrayList<>();
        while (!start.isAfter(end)){
            dates.add(start);
            start = start.plusDays(1);
        }
        return dates;
    }

    /** Διαγράφει απο την ArrayList freedates τις μέρες απο την start μέχρι την end
     *  δλδ κάνει το δωμάτιο να μην είναι διαθέσιμο αυτές τις μέρες
     * @param start η πρώτη μέρα που αρχίζει να μήν είναι διαθέσιμο
     * @param end η τελευταία μέρα που δεν είναι διαθέσιμο
     * @return την αναναιωμένη λίστα με τις διαθέσιμες μέρες του δωματίου
     */
    public ArrayList<LocalDate> removeFromFreeDates(LocalDate start, LocalDate end){
        int i, j;
        i = freeDates.indexOf(start);
        j = freeDates.indexOf(end);
        freeDates.subList(i, j).clear();
        return freeDates;
    }

    /** Ελέγχει στις μέρες που εισέρχονται είναι διαθέσιμο το δωμάτιο ελέγχοντας στη λίστα freedates
     * αν περιέχονται οι ημερομηνίες
     * @param start η πρώτη μέρα
     * @param end η τελευταία μέρα
     * @return true αν είναι κενό αυτές τις μέρες, false διαφορετικά
     */
    public boolean isFree(LocalDate start, LocalDate end){
        ArrayList<LocalDate> wantedDates = calculateDates(start, end);

        if (!freeDates.contains(start) || !freeDates.contains(end) || start.isAfter(end))
            return false;

        return freeDates.containsAll(wantedDates);
    }

    /** Μέθοδος που αλλάζει τα χαρακτηριστικά του δωματίου με τις παρακάτω τιμές της εισόδου
     * @param type τύπος του δωματίου()
     * @param name το όνομα που έχει το κάθε δωμάτιο
     * @param location την τοποθεσία του κάθε δωματίου (πχ SKG)
     * @param price την τιμή για την κράτηση
     * @param area τα τετραγωνικά μέτρα του κάθε δωματίου
     * @param beds τα κρεβάτια του κάθε δωματίου
     * @param wifi το άν έχει Wifi το κάθε δωμάτιο
     * @param parking το άν έχει θέση parking το κάθε δωμάτιο
     * @param breakfast το άν έχει συμπεριλαμβάνεται πρωινό για το κάθε δωμάτιο
     * @param kitchen το άν έχει κουζίνα το κάθε δωμάτιο
     * @param ac το άν έχει κλιματισμό το κάθε δωμάτιο
     * @param start Ημερομηνία που ξεκινάει να είναι διαθέσιμο για κράτηση
     * @param end Ημερομηνία που σταματάει να είναι διαθέσιμο για κράτηση
     */
    public boolean editRoom(String type, String name, String location, Integer price, Integer area, Integer beds, Boolean wifi,
                         Boolean parking, Boolean breakfast, Boolean kitchen, Boolean ac, LocalDate start, LocalDate end){
        if (!type.isBlank() && !name.isBlank() && !location.isBlank() && price>0 && area>0 && beds>0 && start.isBefore(end)){
            this.type = type;
            this.name = name;
            this.location = location;
            this.price = price;
            this.area = area;
            this.beds = beds;
            this.wifi = wifi ;
            this.parking = parking;
            this.breakfast = breakfast;
            this.kitchen = kitchen;
            this.ac = ac;
            if (start.isBefore(this.start))
                freeDates.addAll(calculateDates(start, this.start));

            if (end.isAfter(this.end))
                freeDates.addAll(calculateDates(this.end, end));

            Collections.sort(freeDates);
            this.start = start;
            this.end = end;
            return true;
        } else
            return false;
    }

    /**
     * Setters & getters των χαρακτηριστικών του δωματίου.
     */
    public String getType() {return type;}

    public String getName() {return name;}

    public String getLocation() {return location;}

    public int getArea() {return area;}

    public int getBeds() {return beds;}

    public int getPrice() {return price;}

    public boolean hasWifi() {return wifi;}

    public boolean hasBreakfast() {return breakfast;}

    public boolean hasKitchen() {return kitchen;}

    public boolean hasParking() {return parking;}

    public boolean hasAc(){return ac;}

    public LocalDate getStart(){return start;}

    public LocalDate getEnd(){return end;}

    public String getProviderID() {return providerID;}

}