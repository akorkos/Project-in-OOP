import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Αυτή η κλάση αναπαριστά έναν χρήστη που έχει εγγράφει. Έχει κάποια χαρακτηριστικά τα οποία τα κληρονομάει απο την κλάση
 * User, ενω του προσφέρονται κάποιες βασικές δυνατότητες όπως η κράτηση, η ακύρωση και η αναζήτηση.
 */

public class Customer extends User {
    private ArrayList<Room> query; // Αποθήκευση των δωματίων που πληρούν τα κριτήρια αναζήτησης
    private ArrayList<Reservation> reservations; // Αποθήκευση κρατήσεων που έχει πραγματοποιήσει ο χρήστης
    private ArrayList<Room> rooms; // Αποθήκευση δωματίων που υπάρχουν στο σύστημα

    /**
     * Ο παρακάτω κατασκευαστής θα χρησιμοποιείται όταν θα δημιουργείται κάποιος χρήστης για πρώτη φορά.
     */
    public Customer(String username, String password, String email, String name, String address) {
        super(username, password, email, name, address);
        query = new ArrayList<>();
        reservations = new ArrayList<>();
        rooms = new ArrayList<>();
        setType("Πελάτης");
    }

    /**
     * Ο παρακάτω κατασκευαστής θα χρησιμοποιείται όταν θα φορτώνονται οι χρήστες απο τα αρχεία.
     */
    public Customer(String username, String password, String email, String name, String address, String userID, Boolean verified) {
        super(username, password, email, name, address, userID, verified);
        query = new ArrayList<>();
        reservations = new ArrayList<>();
        rooms = new ArrayList<>();
        setType("Πελάτης");
    }

    /**
     * Χρησιμοποιείται για την ενημέρωση της λίστας δωματίων μετά απο την διαδικασία της κράτησης & ακύρωσης.
     * @return Επιστρέφει τα δωμάτια που υπάρχουν στο σύστημα.
     */
    public ArrayList<Room> getRooms(){
        return rooms;
    }

    /**
     * @param rooms Ενημερώνει την λίστα με δωμάτια που υπάρχουν στο σύστημα.
     */
    public void setRooms(ArrayList<Room> rooms){
        this.rooms = rooms;
    }

    /**
     * @return Επιστρέφει τις κρατήσεις του χρήστη.
     */
    public ArrayList<Reservation> getReservations(){
        return reservations;
    }

    /**
     * @return Επιστρέφει τα δωμάτια που πληρούν τα κριτήρια αναζήτησης.
     */
    public ArrayList<Room> getQuery() {
        return query;
    }

    /**
     * Ενημερώνει τις κρατήσεις που υπάρχουν στο σύστημα,
     * @param reservations κρατήσεις του συστήματος.
     */
    public void setReservations(ArrayList<Reservation> reservations){
        this.reservations = reservations;
    }

    /**
     * Δέχεται την επιλογή δωματίου προς κράτηση και τις ημερομηνίες που θα πρέπει να γίνει η κράτηση. Ελέγχει εαν είναι
     * ελεύθερο το δωμάτιο επιλογής και προχωράει στην κράτηση ενω επιπλέον την αποθηκεύει στην κρατήσεων του χρήστη.
     * @param choice Επιλογή δωματίου απο τη λίστα δωματίων,
     * @param start έναρξη κράτησης,
     * @param end λήξη κράτησης,
     * @return αληθής εάν η διαδικασία ήταν επιτυχής, ψευδής εάν το δωμάτιο δεν είναι διαθέσιμο.
     * Επιπλέον, ενημερώνει τον χρήστη για την κράτηση στέλνοντας του ενα μήνυμα.
     */
    public boolean reservation(Room choice, LocalDate start, LocalDate end) {
        if (choice.isFree(start, end) && !start.isEqual(end)){
            Reservation reservation;
            reservation = choice.reserve(getUserID(),start,end);
            rooms.set(rooms.indexOf(choice), choice);
            reservations.add(reservation);
            try(ObjectInputStream in = new ObjectInputStream(new FileInputStream("messages.txt"))){
                HashMap<String,String> p = (HashMap<String,String>) in.readObject();
                if(p.containsKey(choice.getProviderID()))
                    p.put(choice.getProviderID(),p.get(choice.getProviderID())+"\nΈγινε κράτηση σε ένα απο τα καταλύματα σας με κωδικό "+reservation.getReservationID());
                else
                    p.put(choice.getProviderID(),"Έγινε κράτηση σε ένα απο τα καταλύματα σας με κωδικό "+reservation.getReservationID());
                if(p.containsKey(getUserID()))
                    p.put(getUserID(),p.get(getUserID())+"\n"+"Η κράτηση σας αποθηκεύτηκε με επιτυχία με κωδικό "+reservation.getReservationID());
                else
                    p.put(getUserID(),"Η κράτηση σας αποθηκεύτηκε με επιτυχία με κωδικό "+reservation.getReservationID());

                try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("messages.txt"))){
                    out.writeObject(p);
                }catch (IOException e){
                    e.printStackTrace();
                }
            }catch (IOException | ClassNotFoundException e){
                e.printStackTrace();
            }
            return true;
        } else
            return false;
    }

    /**
     * Δέχεται ποια από τις διαθέσιμες κρατήσεις πρέπει να ακυρωθεί και ξανά τοποθετεί ως διαθέσιμες τις ημερομηνίες που
     * το δωμάτιο ήταν κρατημένο ενώ αφαιρεί και από την λίστα κρατήσεων του costumer την κράτηση αυτή. Επιπλέον, ενημερώνει
     * τον χρήστη για την ακύρωση στέλνοντας του ενα μήνυμα.
     * @param choice Αναπαριστά την κράτηση που θα διαγραφεί,
     * @return αληθής εάν η διαδικασία ήταν επιτυχής, ψευδής εάν έχει προκύψει κάποιο πρόβλημα.
     */
    public boolean cancellation(int choice) {
        boolean found = false;
        Room temp;
        if (choice > reservations.size())
            return false;
        for (int i = 0; i < rooms.size(); i++){
            for (Iterator<Reservation> reservationIterator = rooms.get(i).getReservations().iterator(); reservationIterator.hasNext();) {
                Reservation current = reservationIterator.next();
                if (current.getReservationID().equals(reservations.get(choice).getReservationID())) {
                    found = true;
                    LocalDate reservationToBeDeletedStarts = current.getReservationStart();
                    LocalDate reservationToBeDeletedEnds = current.getReservationEnd();
                    ArrayList<LocalDate> freeDates = rooms.get(i).getFreeDates();
                    while (reservationToBeDeletedStarts.isBefore(reservationToBeDeletedEnds)){
                        freeDates.add(reservationToBeDeletedStarts);
                        reservationToBeDeletedStarts = reservationToBeDeletedStarts.plusDays(1);
                    }
                    temp = rooms.get(i);

                    try(ObjectInputStream in = new ObjectInputStream(new FileInputStream("cancellations.txt"))){
                        HashMap<String,Integer> p = (HashMap<String,Integer>) in.readObject();
                        if(p.containsKey(temp.getProviderID()))
                            p.put(temp.getProviderID(),p.get(temp.getProviderID())+1);
                        else
                            p.put(temp.getProviderID(),1);
                        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("cancellations.txt"))){
                            out.writeObject(p);
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }catch (IOException | ClassNotFoundException e){
                        e.printStackTrace();
                    }

                    try(ObjectInputStream in = new ObjectInputStream(new FileInputStream("messages.txt"))){
                        HashMap<String,String> p = (HashMap<String,String>) in.readObject();

                        if(p.containsKey(temp.getProviderID()))
                            p.put(temp.getProviderID(),p.get(temp.getProviderID())+"\nΑκυρώθηκε η κράτηση με κωδικό " + reservations.get(choice).getReservationID());
                        else
                            p.put(temp.getProviderID(),"Ακυρώθηκε η κράτηση με κωδικό " + reservations.get(choice).getReservationID());

                        if(p.containsKey(getUserID()))
                            p.put(getUserID(), p.get(getUserID()) + "\nΗ κράτηση σας ακυρώθηκε με επιτυχία με κωδικό " + reservations.get(choice).getReservationID());
                        else
                            p.put(getUserID(),"Η κράτηση σας ακυρώθηκε με επιτυχία με κωδικό " + reservations.get(choice).getReservationID());

                        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("messages.txt"))){
                            out.writeObject(p);
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }catch (IOException | ClassNotFoundException e){
                        e.printStackTrace();
                    }
                    temp.setFreeDates(freeDates);
                    rooms.set(i, temp);
                    reservationIterator.remove();
                }
            }
        }
        if (found) {
            reservations.remove(choice);
            return true;
        }
        return false;
    }

    /**
     * Πραγματοποιεί αναζήτηση κάποιου δωματίου σύμφωνα με κάποια κριτήρια. Μπορεί να δεχθεί δυο είδους φίλτρα:
     * Το πρώτο φίλτρο είναι αυτό που πραγματοποιεί την κυρία αναζήτηση (τοποθεσία, ημερομηνίες, αριθμός ατόμων)
     * ενω το δεύτερο για πιο συγκεκριμένα χαρακτηριστικά κάποιου δωματίου.
     * Τέλος, αποθηκεύει το αποτέλεσμα της αναζήτησης στη λίστα query.
     */
    public void search(Filter filter, boolean flag) {
        query.clear();
        for (Room room : rooms) {
            if (filter.verify(room) && !flag)
                query.add(room);
            else if (filter.extVerify(room) && flag)
                query.add(room);
        }
    }
}