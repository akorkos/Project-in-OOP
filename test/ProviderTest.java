import junit.framework.TestCase;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.assertNotEquals;

public class ProviderTest extends TestCase {
    Provider p;
    Customer c;
    Room testRoom1;
    Room testRoom2;
    Room testRoom3;
    Room testRoom4;
    Room testRoom5;
    ArrayList<Room> rooms = new ArrayList<>();
    ArrayList<Reservation> reservations = new ArrayList<>();

    public void setUp(){
        p = new Provider("test","test","test@gmail.com","test","test","test",true);
        c = new Customer("test2","test2","test2@gmail.com","test2","test2","test2",true);
        testRoom1 = new Room("Σπίτι","Alex", "Λάρισα", 15,38,1,true,false,true,true,false, LocalDate.parse("2021-07-01"),LocalDate.parse("2023-07-31"),"test");
        testRoom2 = new Room("Σπίτι","Anton", "Θεσσαλονίκη", 21,47,4,true,true,true,false,true, LocalDate.parse("2021-07-10"),LocalDate.parse("2023-07-15"),"test");
        testRoom3 = new Room("Διαμέρισμα","Chris", "Αθήνα", 150,130,1,true,false,false,true,false, LocalDate.parse("2021-07-01"),LocalDate.parse("2023-07-31"),"test");
        testRoom4 = new Room("Δωμάτιο Ξενοδοχείου","Πετρος", "Θεσσαλονίκη", 210,100,4,true,false,true,false,true, LocalDate.parse("2021-07-10"),LocalDate.parse("2023-07-15"),"test");
        testRoom5 =  new Room("Σπίτι","Anna", "Θεσσαλονίκη", 44,100,4,false,true,false,true,true, LocalDate.parse("2021-07-01"),LocalDate.parse("2023-07-18"),"test");
        rooms.add(testRoom1);
        rooms.add(testRoom2);
        rooms.add(testRoom3);
        rooms.add(testRoom4);
        rooms.add(testRoom5);
        p.setRooms(rooms);
        c.setRooms(rooms);
        c.reservation(testRoom1,LocalDate.parse("2022-07-13"), LocalDate.parse("2022-07-18"));
        c.reservation(testRoom2,LocalDate.parse("2022-01-11"), LocalDate.parse("2022-01-14"));
        reservations.addAll(testRoom1.getReservations());
        reservations.addAll(testRoom2.getReservations());
    }

    public void testAddRoom() {
        assertTrue(p.addRoom(new Room("Μεζονέτα", "Anna", "Θεσσαλονίκη", 44, 100, 4, false, true, false, true, true, LocalDate.parse("2021-07-01"), LocalDate.parse("2023-07-18"), "test")));
        assertFalse(p.addRoom(null));
        assertFalse(p.addRoom(new Room("", "Anna", "Θεσσαλονίκη", 44, 100, 4, false, true, false, true, true, LocalDate.parse("2021-07-01"), LocalDate.parse("2023-07-18"), "test")));
        assertFalse(p.addRoom(new Room("Μεζονέτα", "", "Θεσσαλονίκη", 44, 100, 4, false, true, false, true, true, LocalDate.parse("2021-07-01"), LocalDate.parse("2023-07-18"), "test")));
        assertFalse(p.addRoom(new Room("Μεζονέτα", "Anna", "", 44, 100, 4, false, true, false, true, true, LocalDate.parse("2021-07-01"), LocalDate.parse("2023-07-18"), "test")));
        assertFalse(p.addRoom(new Room("Μεζονέτα", "Anna", "Θεσσαλονίκη", -5, 100, 4, false, true, false, true, true, LocalDate.parse("2021-07-01"), LocalDate.parse("2023-07-18"), "test")));
        assertFalse(p.addRoom(new Room("Μεζονέτα", "Anna", "Θεσσαλονίκη", 44, -5, 4, false, true, false, true, true, LocalDate.parse("2021-07-01"), LocalDate.parse("2023-07-18"), "test")));
        assertFalse(p.addRoom(new Room("Μεζονέτα", "Anna", "Θεσσαλονίκη", 44, 100, -5, false, true, false, true, true, LocalDate.parse("2021-07-01"), LocalDate.parse("2023-07-18"), "test")));
        assertFalse(p.addRoom(new Room("Μεζονέτα", "Anna", "Θεσσαλονίκη", 44, 100, 4, false, true, false, true, true, LocalDate.parse("2023-07-01"), LocalDate.parse("2021-07-18"), "test")));
        assertFalse(p.addRoom(new Room("Μεζονέτα", "Anna", "Θεσσαλονίκη", 44, 100, 4, false, true, false, true, true, LocalDate.parse("2021-07-01"), LocalDate.parse("2023-07-18"), "@@@")));
    }

    public void testEditRoom() {
        assertTrue(p.editRoom(testRoom1, "Μεζονέτα", "Anna", "Θεσσαλονίκη", 44, 100, 4, false, true, false, true, true, LocalDate.parse("2021-07-01"), LocalDate.parse("2023-07-18")));
        assertFalse(p.editRoom(null, "Μεζονέτα", "Anna", "Θεσσαλονίκη", 44, 100, 4, false, true, false, true, true, LocalDate.parse("2021-07-01"), LocalDate.parse("2023-07-18")));
        assertFalse(p.editRoom(testRoom1, "", "Anna", "Θεσσαλονίκη", 44, 100, 4, false, true, false, true, true, LocalDate.parse("2021-07-01"), LocalDate.parse("2023-07-18")));
        assertFalse(p.editRoom(testRoom1, "Μεζονέτα", "", "Θεσσαλονίκη", 44, 100, 4, false, true, false, true, true, LocalDate.parse("2021-07-01"), LocalDate.parse("2023-07-18")));
        assertFalse(p.editRoom(testRoom1, "Μεζονέτα", "Anna", "", 44, 100, 4, false, true, false, true, true, LocalDate.parse("2021-07-01"), LocalDate.parse("2023-07-18")));
        assertFalse(p.editRoom(testRoom1, "Μεζονέτα", "Anna", "Θεσσαλονίκη", -44, 100, 4, false, true, false, true, true, LocalDate.parse("2021-07-01"), LocalDate.parse("2023-07-18")));
        assertFalse(p.editRoom(testRoom1, "Μεζονέτα", "Anna", "Θεσσαλονίκη", 44, -100, 4, false, true, false, true, true, LocalDate.parse("2021-07-01"), LocalDate.parse("2023-07-18")));
        assertFalse(p.editRoom(testRoom1, "Μεζονέτα", "Anna", "Θεσσαλονίκη", 44, 100, -4, false, true, false, true, true, LocalDate.parse("2021-07-01"), LocalDate.parse("2023-07-18")));
        assertFalse(p.editRoom(testRoom1, "Μεζονέτα", "Anna", "Θεσσαλονίκη", 44, 100, 4, false, true, false, true, true, LocalDate.parse("2023-07-01"), LocalDate.parse("2021-07-18")));
    }

    public void testDeleteRoom() {
        assertTrue(p.deleteRoom(testRoom1));
        assertTrue(p.deleteRoom(new Room("Σπίτι", "Anna", "Θεσσαλονίκη", 44, 100, 4, false, true, false, true, true, LocalDate.parse("2021-07-01"), LocalDate.parse("2023-07-18"), "test")));
        assertFalse(p.deleteRoom(testRoom1));
        assertFalse(p.deleteRoom(new Room("no", "no", "no", 1, 1, 1, true, true, true, true, true, LocalDate.parse("2021-07-01"), LocalDate.parse("2023-07-18"), "test")));
    }

    public void testFind() {
        assertEquals(testRoom1.toString(),p.getRooms().get(p.find(testRoom1)).toString());
        assertEquals(testRoom2.toString(),p.getRooms().get(p.find(testRoom2)).toString());
        assertEquals(-1,p.find(new Room("Σπίτι","Γιάννης", "Αθήνα", 100,100,6,true,true,true,true,true, LocalDate.parse("2022-01-01"),LocalDate.parse("2023-12-31"),"test")));
    }

    public void testFindType() {
        //Type = Σπίτι
        p.setRooms(rooms);
        ArrayList<Room> temp = p.findType("Σπίτι");
        int j=0;
        for (Room room : rooms) {
            if (room.getType().equals("Σπίτι")) {
                assertEquals(room.toString(), temp.get(j).toString());
                j++;
            }
        }
    }

    public void testGetRooms() {
        ArrayList<Room> temp = p.getRooms();
        assertEquals(rooms.size(),temp.size());
        for(int i=0;i<rooms.size();i++){
            assertEquals(rooms.get(i).toString(),temp.get(i).toString());
        }
    }

    public void testGetReservation() {
        p.setReservations();
        assertEquals(reservations.size(),p.getReservation().size());
        for(int i=0;i<reservations.size();i++){
            assertEquals(reservations.get(i).toString(),p.getReservation().get(i).toString());
        }
    }

    public void testSetRooms() {
        assertEquals(rooms.size(),p.getRooms().size());
        rooms.clear();
        rooms.add(testRoom1);
        rooms.add(testRoom5);
        p.setRooms(rooms);
        assertEquals(2,p.getRooms().size());
        rooms.add(testRoom2);
        p.setRooms(rooms);
        assertEquals(3,p.getRooms().size());
    }

    public void testSetReservations() {
        p.setReservations();
        assertEquals(2,p.getReservation().size());
        for(int i=0;i<reservations.size();i++){
            assertEquals(reservations.get(i).toString(),p.getReservation().get(i).toString());
        }
        c.reservation(testRoom5,LocalDate.parse("2023-01-11"), LocalDate.parse("2023-01-14"));
        c.reservation(testRoom5,LocalDate.parse("2022-12-14"), LocalDate.parse("2022-12-16"));
        reservations.addAll(testRoom5.getReservations());
        p.setReservations();
        assertEquals(4,p.getReservation().size());
        for(int i=0;i<reservations.size();i++){
            assertEquals(reservations.get(i).toString(),p.getReservation().get(i).toString());
        }
    }

    public void testGetCancellation() {
        initialiseCancellationsForTheTest();
        assertEquals(0,p.getCancellation());
        c.cancellation(0);
        assertEquals(1,p.getCancellation());
        c.cancellation(0);
        assertEquals(2,p.getCancellation());
    }

    /** Αρχικοποιώ τις ακυρώσεις με 0 για τον πάροχος που κάνουμε το test και τις αποθηκεύω στο αρχείο των ακυρώσεων.
     *  Δέν θα χρειαζόταν η αρχικοποίηση αν δέν γινόταν η αποθήκευση των ακυρώσεων στα αρχεία, επειδή γίνεται όμως
     *  κάθε φορά που θα τρέχει αυτό το κομμάτι κώδικα οι ακυρώσεις απλά θα αυξάνονται και θα αποθηκεύονται στο αρχεία.
     *  Η συνάρτηση που ελέγχουμε λαμβάνει τα στοιχεία της απο τα αρχεία οπότε αν δέν είχε γίνει η αρχικοποίηση,
     *  θα έβγαιναν τα αναμενόμενα αποτελέσματα ΜΌΝΟ για την πρώτη φορά που θα έτρεχε το τεστ testGetCancellation().
     *  Τις επόμενες φορές, απλά θα αυξάνονταν οι ακυρώσεις χωρίς να ξέρουμε το ακριβές νούμερο οπότε δέν θα μπορούσε
     *  να γίνει σωστά ο έλεγχος για το test*/
    private void initialiseCancellationsForTheTest(){
        HashMap<String,Integer> temp = new HashMap<>();
        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream("cancellations.txt"))){
            temp = (HashMap<String,Integer>) in.readObject();
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        temp.put(p.userID,0);  // αρχικοποιώ με 0 αν δεν υπάρχει στοιχείο με αυτό το κλειδί αλλιώς κάνω overwrite
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("cancellations.txt"))){
            out.writeObject(temp);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
