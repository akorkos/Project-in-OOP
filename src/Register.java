import java.util.ArrayList;

/**
 * Αναπαριστά την εγγραφή του χρήστη (πελάτη ή παρόχου) στο σύστημα.
 */

public class Register {
    private ArrayList<User> users;

    /**
     * Κατασκευαυει ένα περιβάλλον για την εγγραφή του χρήστη,
     * @param users η λίστα των χρηστών που υπάρχουν στο σύστημα.
     */
    public Register(ArrayList<User> users) {
        this.users = users;
    }

    /**
     * Επιστρέφει την λίστα που περιέχει τους χρήστες του συστήματος,
     * @return λίστα των χρηστών του συστήματος.
     */
    public ArrayList<User> getUsers() {
        return users;
    }

    /**
     * Υλοποιείται η διαδικασία εγγραφής κάποιου χρήστη, είτε αυτός είναι πάροχος είτε αυτός είναι χρήστης,
     * αφού πρώτα γίνει ο έλεγχος εάν ο χρήστης είναι παρόν στο σύστημα.
     * @return Επιστρέφει αληθής εάν γίνει η εγγραφή, διαφορετικά ψευδής.
     */
    public boolean registration(String username, String password, String email, String name, String address, String type){
        if (validation(username, email)){
            if (type.equals("Πελάτης")){
                users.add(new Customer(username, password, email, name, address));
                return true;
            } else if (type.equals("Πάροχος")){
                users.add(new Provider(username, password, email, name, address));
                return true;
            } else
                return false;
        }
        return false;
    }

    /**
     * Εδώ υλοποιείται έλεγχος για το εάν υπάρχει χρήστης στο σύστημα με αυτά τα δεδομένα.
     * @return Επιστρέφει αληθής εάν γίνετε να εγγραφεί ο χρήστης, διαφορετικά ψευδής.
     */
    private boolean validation(String username, String email) {
        boolean success = true;
        for (User user : users){
            if (user.getUsername().equals(username) || user.getEmail().equals(email)){
                success = false;
                break;
            }
        }
        return success;
    }
}