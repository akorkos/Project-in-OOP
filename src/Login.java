import java.util.ArrayList;

/**
 * Αναπαριστά μια κλάση η οποία θα χρησιμοποιείται κατά την ταυτοποίησή του χρήστη στο σύστημα.
 */

public class Login {
    private ArrayList<User> users;

    /**
     * Κατασκευαυει ένα περιβάλλον για την ταυτοποίηση του χρήστη που θέλει να συνδεθεί,
     * @param users η λίστα των χρηστών που υπάρχουν στο σύστημα, για να γίνει η ταυτοποίηση.
     */
    public Login(ArrayList<User> users){
        this.users = users;
    }

    /**
     * Πραγματοποιεί την ταυτοποίηση του χρήστη στο σύστημα (επιτρέπεται η σύνδεση μόνο των επιβεβαιωμένων χρηστών),
     * @param username όνομα χρήστη,
     * @param password κωδικός,
     * @return το αντικείμενο του χρήστη που προσπαθεί να συνδεθεί.
     */
    public User validation(String username, String password){
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password))
                return user;
        }
        return null;
    }
}
