import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.time.LocalDate;
import java.util.HashMap;

/**
 * Αυτή η κλάση αναπαριστά έναν πάροχο που έχει εγγράφει. Έχει κάποια χαρακτηριστικά τα οποία τα κληρονομάει απο την κλάση
 * User, ενω του προσφέρονται κάποιες βασικές δυνατότητες όπως η Προσθήκη / επεξεργασία / διαγραφή καταλύματος.
 */
public class Provider extends User {

    private ArrayList<Room> rooms; // Αποθήκευση δωματίων παροχου
    private ArrayList<Reservation> reservations;
    private int sumCancellation;


    /** Καλείτε ο constructor του User και αρχικοποιούνται όλα τα ArrayList
     * @param username του χρήστη
     * @param password του χρήστη
     * @param email  του χρήστη
     * @param name του χρήστη
     * @param address του χρήστη
     */
    public Provider(String username, String password, String email, String name, String address){
        super(username, password, email, name, address);
        rooms = new ArrayList<>();
        reservations=new ArrayList<>();
        sumCancellation=0;
        setType("Πάροχος");
    }

    /**
     * Ο παρακάτω κατασκευαστής θα χρησιμοποιείται όταν θα φορτώνονται οι χρήστες απο τα αρχεία.
     */
    public Provider(String username, String password, String email, String name, String address, String userID, Boolean verified){
        super(username, password, email, name, address, userID, verified);
        rooms = new ArrayList<>();
        reservations=new ArrayList<>();
        sumCancellation=0;
        setType("Πάροχος");
    }

    /** Τοποθετεί το δωμάτιο της εισόδου στη λίστα των δωματίων του παρόχου
     * @param room το δωμάτιο που εισάγετε*/
    public boolean addRoom(Room room){
        if (room == null || room.getType().isBlank() || room.getName().isBlank() || room.getLocation().isBlank() || room.getPrice()<=0 || room.getArea()<=0 || room.getBeds()<=0 || room.getStart().isAfter(room.getEnd()) || !room.getProviderID().equals(getUserID()))
            return false;
        else
            return rooms.add(room);
    }

    /** This function edits the room that is inserted with the following parameters
     * @param room   will edit this room that is already saved in the ArrayList*/
    public boolean editRoom(Room room, String type,String name,String location, int price, int area, int beds, boolean wifi, boolean parking, boolean breakfast, boolean kitchen,boolean ac, LocalDate start, LocalDate end){
        if(room == null || find(room) < 0 )
            return false;
        return rooms.get(find(room)).editRoom(type,name,location,price,area,beds,wifi,parking,breakfast,kitchen,ac,start,end);
    }

    /**deletes the room that is inserted*/
    public boolean deleteRoom(Room room){
        if(room==null || find(room)<0){
            return false;
        }
        return rooms.remove(rooms.get(find(room)));
    }

    /** Finds the room that is inserted and returns its location in the ArrayList else returns -1*/
    public int find(Room r){
        //Set<Map.Entry<String,Integer>> s = m.entrySet();
        for(int i = 0; i < rooms.size(); i++)
        {
            if(String.valueOf(r).equals(String.valueOf(rooms.get(i))))
            {
                return i;
            }
        }
        return -1;
    }

    /** Μέθοδος για την υλοποίησης της αναζήτησης του provider
     * @param type η αναζήτηση στα δωμάτια γίνεται με βάση αυτόν τον τύπο
     * @return  επιστρέφει μια λίστα με όλα τα rooms που έχει ο χρήστης που έχουν τον τύπο του ορίσματος
     */
    public ArrayList<Room> findType(String type){
        ArrayList<Room> temp=new ArrayList<>();
        for(Room room:rooms){
            if(room.getType().equals(type)){
                temp.add(room);
            }
        }
        return temp;
    }
    /** Επιστρέφει τα δωμάτια αυτού του provider*/
    public ArrayList<Room> getRooms(){
        return this.rooms;
    }

    /** Επιστρέφει τις κρατήσεις που έχουν γίνει στα δωμάτια του παρόχου */
    public ArrayList<Reservation> getReservation(){
        return this.reservations;
    }

    /** Ενημερώνει την λίστα με δωμάτια που υπάρχουν στο σύστημα.
     * @param rooms λίστα με όλα τα δωμάτια του συστήματος */
    public void setRooms(ArrayList<Room> rooms){
        ArrayList<Room> temp=new ArrayList<>();
        for(Room room:rooms){
            if(room.getProviderID().equals(this.getUserID())){
                temp.add(room);
            }
        }
        this.rooms = temp;
    }
    /** Ενημερώνει την λίστα με τις κρατήσεις που υπάρχουν στο σύστημα.*/
    public void setReservations(){
        ArrayList<Reservation> providerReservations = new ArrayList<>();
        for (Room room : rooms)
            providerReservations.addAll(room.getReservations());
        reservations = providerReservations;
    }

    /** Getter για το σύνολο των ακυρώσεων
     * @return το σύνολο των ακυρώσεων */
    public int getCancellation(){
        HashMap<String,Integer> p = new HashMap<>();
        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream("cancellations.txt"))){
            p = (HashMap<String,Integer>) in.readObject();
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        if(p.containsKey(this.userID))
            return p.get(this.userID);
        return 0;
    }
}

