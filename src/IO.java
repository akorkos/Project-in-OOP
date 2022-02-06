import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class IO {
    // Οι παρακάτω λίστες χρησιμοποιούνται για το "φόρτωμα" τον δεδομένων απο τα αρχεία.
    protected ArrayList<Room> rooms;
    protected ArrayList<Reservation> reservations;
    protected ArrayList<User> users;
    protected HashMap<String,String> messages; // Η types χρησιμοποιείται για την αποθήκευση των τύπων δωματίων.

    public IO(){

        // Εδω φορτώνονται τα δεδομένα απο τα αρχεία

        rooms = getRooms();
        reservations = getReservations();
        users = getUsers();
        messages = getMessages();
    }

    /**
     * @return Επιστρέφονται τα μηνύματα που υπάρχουν στο αρχείο, σε έμορφη ενός HashMap.
     */
    protected HashMap<String,String> getMessages(){
        HashMap<String,String> p = new HashMap<>();
        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream("messages.txt"))){
            p = (HashMap<String,String>) in.readObject();
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return p;
    }

    /**
     * @return Επιστρέφονται οι χρήστες του συστήματος, με τη μορφή ενός ArrayList.
     */
    protected ArrayList<User> getUsers(){
        ArrayList<User> p = new ArrayList<>();
        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream("users.txt"))){
            p = (ArrayList<User>) in.readObject();
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return p;
    }

    /**
     * @return Επιστρέφονται τα δωμάτια του συστήματος, με τη μορφή ενός ArrayList.
     */
    protected ArrayList<Room> getRooms(){
        ArrayList<Room> p = new ArrayList<>();
        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream("rooms.txt"))){
            p = (ArrayList<Room>) in.readObject();
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return p;
    }

    /**
     * @return Επιστρέφονται οι κρατήσεις του συστήματος, με τη μορφή ενός ArrayList.
     */
    protected ArrayList<Reservation> getReservations(){
        ArrayList<Reservation> p = new ArrayList<>();
        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream("reservations.txt"))){
            p = (ArrayList<Reservation>) in.readObject();
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return p;
    }
}