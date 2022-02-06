import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class LoginTest {
    Login login;
    User p1;
    User p2;
    User c1;
    User c2;
    ArrayList<User> users=new ArrayList<>();
    @Before
    public void setUp(){
        p1 = new Provider("test","test","test@gmail.com","test","test","test",true);
        p2 = new Provider("test3","test3","test3","test3","test3");
        c1 = new Customer("test2","test2","test2@gmail.com","test2","test2","test2",true);
        c2 = new Customer("test4","test4","test4","test4","test4");
        users.add(p1);
        users.add(c1);
        users.add(p2);
        users.add(c2);
        login = new Login(users);
    }

    @Test
    public void validation() {
        assertEquals(p1,login.validation("test","test"));
        assertEquals(c1,login.validation("test2","test2"));
        assertEquals(p2, login.validation("test3", "test3"));
        assertEquals(c2, login.validation("test4", "test4"));
        assertNull(login.validation("alkjdfhgiua", "adkjgf;asdhg"));
    }
}