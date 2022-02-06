import java.io.Serializable;
import java.util.UUID;
import java.io.Serializable;

public class User implements Serializable {
    protected String username, name, address, password, email;
    protected String type, userID;
    protected Boolean verified;

    /**
     * Ο παρακάτω κατασκευαστής θα χρησιμοποιείται όταν θα δημιουργείται κάποιος χρήστης για πρώτη φορά.
     */
    public User(String username, String password, String email, String name, String address){
        this.address = address;
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.userID = UUID.randomUUID().toString();
        this.verified = false;
    }

    @Override
    public String toString(){
        return "Όνομα: " + name + " Username: " + username + " Password: " + password +" Email: " + email + " Τύπος χρήστη: " + getType() + " UserID: " + userID + " Ενεργοποιημένος: "+ verified;
    }

    /**
     * Ο παρακάτω κατασκευαστής θα χρησιμοποιείται όταν θα φορτώνονται οι χρήστες απο τα αρχεία.
     */
    public User(String username, String password, String email, String name, String address, String userID, Boolean verified){
        this.address = address;
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.userID = userID;
        this.verified = verified;
    }

    /**
     * Setters & getters της κλάσης.
     */

    public String getType() {
        return type;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public boolean isVerified(){return verified;}

    public String getUserID() {
        return userID; }

    protected void setType(String type){
        this.type = type;
    }

    protected void setVerified(boolean verified){this.verified = verified;}
}