import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

public class AdminTest {
    Admin admin;
    Provider p;
    Customer c;
    Provider p1;
    Customer c1;
    Room testRoom1;
    Room testRoom2;
    Room testRoom3;
    Room testRoom4;
    Room testRoom5;
    ArrayList<Room> rooms;
    ArrayList<User> users, unverifiedUsers;
    ArrayList<Reservation> reservations = new ArrayList<>();
    @Before
    public void setUp() {
        admin = new Admin("username","password","email@gmail.com","name","address");
        rooms = new ArrayList<>();
        users = new ArrayList<>();
        unverifiedUsers = new ArrayList<>();
        p = new Provider("test","test","test@gmail.com","test","test","test",true);
        c = new Customer("test2","test2","test2@gmail.com","test2","test2","test2",true);
        p1 = new Provider("test3","test3","test3","test3","test3");
        c1 =new Customer("test4","test4","test4","test4","test4");
        users.add(p);
        users.add(c);
        users.add(p1);
        users.add(c1);
        admin.setUsers(users);
        unverifiedUsers.add(p1);
        unverifiedUsers.add(c1);
        testRoom1 = new Room("Σπίτι","Alex", "Λάρισα", 15,38,1,true,false,true,true,false, LocalDate.parse("2021-07-01"),LocalDate.parse("2023-07-31"),"test");
        testRoom2 = new Room("Μεζονέτα","Anton", "Θεσσαλονίκη", 21,47,4,true,true,true,false,true, LocalDate.parse("2021-07-10"),LocalDate.parse("2023-07-15"),"test");
        testRoom3 = new Room("Διαμέρισμα","Chris", "Αθήνα", 150,130,1,true,false,false,true,false, LocalDate.parse("2021-07-01"),LocalDate.parse("2023-07-31"),"test3");
        testRoom4 = new Room("Δωμάτιο Ξενοδοχείου","Πετρος", "Θεσσαλονίκη", 210,100,4,true,false,true,false,true, LocalDate.parse("2021-07-10"),LocalDate.parse("2023-07-15"),"test");
        testRoom5 =  new Room("Σπίτι","Anna", "Θεσσαλονίκη", 44,100,4,false,true,false,true,true, LocalDate.parse("2021-07-01"),LocalDate.parse("2023-07-18"),"test");
        rooms.add(testRoom1);
        rooms.add(testRoom2);
        rooms.add(testRoom3);
        rooms.add(testRoom4);
        rooms.add(testRoom5);
        c.setRooms(rooms);
        c.reservation(testRoom1,LocalDate.parse("2022-07-13"), LocalDate.parse("2022-07-18"));
        c.reservation(testRoom2,LocalDate.parse("2022-01-11"), LocalDate.parse("2022-01-14"));
        c.reservation(testRoom5,LocalDate.parse("2022-12-14"), LocalDate.parse("2022-12-16"));
        c.reservation(testRoom5,LocalDate.parse("2023-01-11"), LocalDate.parse("2023-01-14"));
        admin.setRooms(rooms);
        admin.getUnverifiedUsers();
        admin.setMessages(new HashMap<>());
        reservations.addAll(testRoom1.getReservations());
        reservations.addAll(testRoom2.getReservations());
        reservations.addAll(testRoom5.getReservations());
    }

    @Test
    public void getUnverifiedUsers() {
        ArrayList<User> unv = admin.getUnverifiedUsers();
        assertEquals(unverifiedUsers.size(),unv.size());
        for(int i =0;i<unv.size();i++){
            assertEquals(unverifiedUsers.get(i).toString(),unv.get(i).toString());
        }
    }

    @Test
    public void approveUser() {
        assertTrue(admin.approveUser(1));
        admin.getUnverifiedUsers();
        assertTrue(c1.isVerified());
        assertFalse(admin.approveUser(1));
        admin.getUnverifiedUsers();
        assertTrue(admin.approveUser(0));
        admin.getUnverifiedUsers();
        assertTrue(p1.isVerified());
    }

    @Test
    public void setUsers() {
        users.clear();
        admin.setUsers(users);
        assertEquals(admin.getUsers().size(),0);
        users.add(p);
        users.add(c);
        admin.setUsers(users);
        assertEquals(admin.getUsers().size(),2);
        for(int i =0;i<admin.getUsers().size();i++){
            assertEquals(admin.getUsers().get(i).toString(),users.get(i).toString());
        }
        users.add(p1);
        users.add(c1);
        admin.setUsers(users);
        assertEquals(admin.getUsers().size(),4);
        for(int i =0;i<admin.getUsers().size();i++){
            assertEquals(admin.getUsers().get(i).toString(),users.get(i).toString());
        }

    }

    @Test
    public void setRooms() {
        rooms.clear();
        admin.setRooms(rooms);
        assertEquals(admin.getRooms().size(),0);
        rooms.add(testRoom1);
        rooms.add(testRoom2);
        admin.setRooms(rooms);
        assertEquals(admin.getRooms().size(),2);
        for(int i =0;i<admin.getRooms().size();i++){
            assertEquals(admin.getRooms().get(i).toString(),rooms.get(i).toString());
        }
        rooms.add(testRoom3);
        rooms.add(testRoom4);
        admin.setRooms(rooms);
        assertEquals(admin.getRooms().size(),4);
        for(int i =0;i<admin.getRooms().size();i++){
            assertEquals(admin.getRooms().get(i).toString(),rooms.get(i).toString());
        }
    }

    @Test
    public void getUsers() {
        for(int i =0;i<admin.getUsers().size();i++){
            assertEquals(admin.getUsers().get(i).toString(),users.get(i).toString());
        }
    }

    @Test
    public void getRooms() {
        for(int i =0;i<admin.getRooms().size();i++){
            assertEquals(admin.getRooms().get(i).toString(),rooms.get(i).toString());
        }
    }

    @Test
    public void getMessages() {
        admin.addMessage("HelloWorld");
        assertEquals("HelloWorld",admin.getMessages().get("all"));
        admin.addMessage("test");
        assertNotEquals("HelloWorld",admin.getMessages().get("all"));
        assertEquals("HelloWorld\ntest",admin.getMessages().get("all"));
    }

    @Test
    public void addMessage() {
        admin.addMessage("HelloWorld");
        assertEquals("HelloWorld",admin.getMessages().get("all"));
        admin.addMessage("test");
        assertNotEquals("HelloWorld",admin.getMessages().get("all"));
        assertEquals("HelloWorld\ntest",admin.getMessages().get("all"));
    }

    @Test
    public void setMessages() {
        admin.addMessage("HelloWorld");
        assertEquals("HelloWorld",admin.getMessages().get("all"));
        admin.addMessage("test");
        assertEquals("HelloWorld\ntest",admin.getMessages().get("all"));

        admin.setMessages(new HashMap<>());
        assertNull(admin.getMessages().get("all"));

        admin.addMessage("Christos");
        assertEquals("Christos",admin.getMessages().get("all"));

    }

    @Test
    public void searchUser() {
        ArrayList<User> temp = admin.searchUser("test");
        assertEquals(4,temp.size());
        for (int i = 0 ;i<temp.size();i++){
            assertEquals(users.get(i).toString(),temp.get(i).toString());
        }
        temp = admin.searchUser("2");
        assertEquals(c.toString(),temp.get(0).toString());
        temp = admin.searchUser("3");
        assertEquals(p1.toString(),temp.get(0).toString());
        temp = admin.searchUser("4");
        assertEquals(c1.toString(),temp.get(0).toString());

    }

    @Test
    public void searchReservation() {
        HashMap<Reservation,Room> temp = admin.searchReservation(LocalDate.parse("2022-01-01"),LocalDate.parse("2022-12-31"));
        assertEquals(testRoom1.toString(),temp.get(reservations.get(0)).toString());
        assertEquals(testRoom2.toString(),temp.get(reservations.get(1)).toString());
        assertEquals(testRoom5.toString(),temp.get(reservations.get(2)).toString());

        temp = admin.searchReservation(LocalDate.parse("2022-01-01"),LocalDate.parse("2024-12-31"));
        assertEquals(testRoom1.toString(),temp.get(reservations.get(0)).toString());
        assertEquals(testRoom2.toString(),temp.get(reservations.get(1)).toString());
        assertEquals(testRoom5.toString(),temp.get(reservations.get(2)).toString());
        assertEquals(testRoom5.toString(),temp.get(reservations.get(3)).toString());
    }

}