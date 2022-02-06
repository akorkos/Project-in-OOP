import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Αναπαριστά έναν διαχειριστή του συστήματος και τις λειτουργίες που μπορεί να πραγματοποιήσει.
 */
public class Admin extends User{
    private ArrayList<Room> rooms;
    private ArrayList<User> users, unverifiedUsers;
    private HashMap<Reservation, Room> query;
    private HashMap<String,String> messages;

    public Admin(String username, String password, String email, String name, String address){
        super(username, password, email, name, address, "ADMIN", true);
        setType("Admin");
        rooms = new ArrayList<>();
        query = new HashMap<>();
        users = new ArrayList<>();
        messages = new HashMap<>();
        unverifiedUsers = new ArrayList<>();
    }

    /**
     * Ψάχνει όλους τους χρήστες ανάλογα με το username που εισάγεται
     * @param username αναζήτηση με βάση το Username
     * @return  μια λίστα που περιέχει users που έχουν το ίδιο username με αυτό της εισόδου
     */
    public ArrayList<User> searchUser(String username){
        ArrayList<User> user = new ArrayList<>();
        for(User x : users) {
            if(x.getUsername().contains(username)) //να ψαχνει τα usernames που περιεχουν την συμβολοσειρα ακομα κ οχι ακριβως
                user.add(x);
        }
        return user;
    }

    /**
     * Γίνετε αναζήτηση με βάση κάποιου χρονικού διαστήματος για το ποιες κρατήσεις έχουν πραγματοποιηθεί.
     * @param start Αρχική ημερομηνία χρονικού διαστήματος,
     * @param end τελική ημερομηνία χρονικού διαστήματος,
     * @return επιστρέφει ένα HashMap με κλειδί τον μοναδικό αριθμό ταυτοποίησης χρήστη και ως περιεχόμενο το δωμάτιο το οποίο έχει κρατηθεί.
     */
    public HashMap<Reservation, Room> searchReservation(LocalDate start, LocalDate end){
        for (Room room : rooms){
            ArrayList <Reservation> temp = room.getReservations();
            for (Reservation reservation : temp){
                if ((reservation.getReservationStart().isEqual(start) || reservation.getReservationStart().isAfter(start))
                        && (reservation.getReservationEnd().isEqual(end) || reservation.getReservationEnd().isBefore(end)))
                    query.put(reservation, room);
            }
        }
        return query;
    }

    /**
     * Μέθοδος που επιστρέφει όλους τους μη-επιβεβαιωμένους χρήστες του συστήματος,
     * @return λίστα (κλάσης User) με όλους μη-επιβεβαιωμένους χρήστες.
     */
    public ArrayList<User> getUnverifiedUsers(){
        unverifiedUsers = new ArrayList<>();
        for (User user : users){
            if (!user.isVerified())
                unverifiedUsers.add(user);
        }
        return unverifiedUsers;
    }

    /**
     * Μέθοδος που πραγματοποιεί την επιβεβαίωσή κάποιου χρήστη (μη-επιβεβαιωμένου) του συστήματος,
     * @param choice η θέση του χρήστη μέσα στήν λίστα των μη-επιβεβαιωμένων χρηστών.
     * @return true αν γίνει η αποδοχή false αν υπάρξει κάποιο πρόβλημα
     */
    public boolean approveUser(int choice){
        if (choice<0||choice>=unverifiedUsers.size())
            return false;
        User temp = unverifiedUsers.get(choice);
        temp.setVerified(true);
        users.set(users.indexOf(unverifiedUsers.get(choice)), temp);
        return true;
    }

    /**
     * Ενημερώνει την λίστα με τους χρήστες του συστήματος,
     * @param users λίστα των χρηστών.
     */
    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    /**
     * Ενημερώνει την λίστα των δωματίων.
     * @param rooms ο πίνακας που αποθηκεύεται
     */
    public void setRooms(ArrayList<Room> rooms) {
        this.rooms = rooms;
    }

    /**
     * @return Επιστρέφει την (ενημερωμένη) λίστα των χρηστών του συστήματος.
     */
    public ArrayList<User> getUsers() {
        return users;
    }

    /**
     * @return Επιστρέφει την (ενημερωμένη) λίστα των δωματίων του συστήματος.
     */
    public ArrayList<Room> getRooms() {
        return rooms;
    }

    /**
     * @return Επιστρέφει την (ενημερωμένη) λίστα των μηνυμάτων που έχει στείλει ο admin στους χρήστες .
     */
    public HashMap<String,String> getMessages() {
        return messages;
    }

    /** Στο HashMap με όλα τα μηνύματα προστίθεται ένα μήνυμα με κλειδί "all" που στέλνεται σε όλους τους
     *  χρήστες. Αν υπάρχει ήδη μήνυμα με τέτοιο κλειδί απλά προσθέτουμε το νέο μήνυμα στο παλίο.
     * @param message Το μήνυμα που προστίθεται
     */
    public void addMessage(String message){
        if(messages.containsKey("all"))
            messages.put("all",messages.get("all")+"\n"+message);
        else
            messages.put("all",message);
    }

    /**
     * @param messages Ενημερώνει τα μηνύματα προς όλους τους χρήστες.
     */
    public void setMessages(HashMap<String,String> messages) {
        this.messages = messages;
    }
}
