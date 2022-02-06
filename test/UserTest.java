import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {
    User user1;
    User user2;
    @Before
    public void setUp() {
        user1 = new Provider("u user1","p user1","e user1@gmail.com","n user1","a user1","ID user1",true);
        user2 = new Customer("username","password","email@gmail.com","name","address");
    }

    @Test
    public void testToString() {
        String temp = "Όνομα: " + "n user1" + " Username: " + "u user1" + " Password: " + "p user1" +" Email: " + "e user1@gmail.com" + " Τύπος χρήστη: " + "Πάροχος" + " UserID: " + "ID user1" + " Ενεργοποιημένος: "+ "true";
        assertEquals(temp,user1.toString());
        temp = "Όνομα: " + "name" + " Username: " + "username" + " Password: " + "password" +" Email: " + "email@gmail.com" + " Τύπος χρήστη: " + "Πελάτης" + " UserID: " + user2.getUserID() + " Ενεργοποιημένος: "+ "false";
        assertEquals(temp,user2.toString());
    }

    @Test
    public void getType() {
        assertEquals("Πάροχος",user1.getType());
        assertEquals("Πελάτης",user2.getType());
    }

    @Test
    public void getEmail() {
        assertEquals("e user1@gmail.com",user1.getEmail());
        assertEquals("email@gmail.com",user2.getEmail());
    }

    @Test
    public void getAddress() {
        assertEquals("a user1",user1.getAddress());
        assertEquals("address",user2.getAddress());
    }

    @Test
    public void getName() {
        assertEquals("n user1",user1.getName());
        assertEquals("name",user2.getName());
    }

    @Test
    public void getPassword() {
        assertEquals("p user1",user1.getPassword());
        assertEquals("password",user2.getPassword());
    }

    @Test
    public void getUsername() {
        assertEquals("u user1",user1.getUsername());
        assertEquals("username",user2.getUsername());
    }

    @Test
    public void isVerified() {
        assertTrue(user1.isVerified());
        assertFalse(user2.isVerified());
    }

    @Test
    public void getUserID() {
        assertEquals("ID user1",user1.getUserID());
    }
}