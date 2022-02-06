import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class RegisterTest {
    Register register;
    User p1;
    User p2;
    User c1;
    User c2;
    ArrayList<User> users=new ArrayList<>();
    @Before
    public void setUp() {
        p1 = new Provider("p1","test","p1@gmail.com","provider1","address1","test",true);
        p2 = new Provider("p2","test3","p2","provider2","address2");
        c1 = new Customer("c1","test2","c1@gmail.com","costumer1","address1","test2",true);
        c2 = new Customer("c2","test4","c2","costumer2","address2");
        users.add(p1);
        users.add(c1);
        users.add(p2);
        users.add(c2);
        register = new Register(users);
    }

    @Test
    public void getUsers() {
        assertEquals(4,register.getUsers().size());
        for(int i=0;i<register.getUsers().size();i++){
            assertEquals(users.get(i),register.getUsers().get(i));
        }
    }

    @Test
    public void registration() {
        assertFalse(register.registration("p1","1234","p1@gmail.com","τεστ","τεστ","Ε"));       //Υπάρχει χρήστης με το ίδιο username και email
        assertFalse(register.registration("c1","1234","null","τεστ","τεστ","Ε"));               //Υπάρχει χρήστης με ίδιο όνομα
        assertFalse(register.registration("null","1234","c1@gmail.com","τεστ","τεστ","Ε"));     //Υπάρχει χρήστης με ίδιο email
        assertFalse(register.registration("test","1234","test","test","test","Λάθος"));         //Λάθος τύπος

        assertTrue(register.registration("Christos","3872","cdtsingi@csd.auth.gr","Christos-Alexandros Tsingiropoulos","Θεσσαλονίκη","Πελάτης")); //Δεκτός χρήστης(Πελάτης)
        assertTrue(register.registration("Alexandros","3870","alexkork@csd.auth.gr","Alexandros Korkos","Θεσσαλονίκη","Πάροχος"));                //Δεκτός χρήστης(Εργαζόμενος)
    }
}