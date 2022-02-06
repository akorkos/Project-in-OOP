import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class RoomTest {
    Customer c;
    Provider p;
    Room room1,room2;
    @Before
    public void setUp(){
        c = new Customer("c","c","c@gmail.com","c","c","c",true);
        p = new Provider("test","test","test@gmail.com","test","test","test",true);
        room1=new Room("Σπίτι","Χρήστος","Θεσσαλονίκη",100,30,3,true,true,true,true,true, LocalDate.parse("2021-12-31"),LocalDate.parse("2023-12-31"),p.getUserID());
        room2 = new Room("Διαμέρισμα","Αλέξανδρος","Λάρισα",1,1,1,false,false,false,false,false,LocalDate.parse("2022-01-01"),LocalDate.parse("2022-12-31"),"CHRIS");
    }

    @Test
    public void reserve() {
        Reservation reservation = room1.reserve(c.userID,LocalDate.parse("2022-03-05"),LocalDate.parse("2022-03-08"));
        assertEquals(LocalDate.parse("2022-03-05"),reservation.getReservationStart());
        assertEquals(LocalDate.parse("2022-03-08"),reservation.getReservationEnd());
        assertEquals(c.userID,reservation.getCustomerID());
    }

    @Test
    public void testToString() {
        String temp = "Τύπος: " + "Σπίτι" + ", όνομα: " + "Χρήστος" + ", τοποθεσία: " + "Θεσσαλονίκη" + ", τιμή ανά διανυκτέρευση: " + 100 + ", επιφάνεια: " + 30 + ", κρεβάτια:" + 3 + ", WiFi: " + "true" + ", πάρκινγκ: " + "true" + ", πρωινό: " + "true" + ", κουζίνα: " + "true" + ", κλιματισμός: " + "true";
        assertEquals(temp,room1.toString());        //Είναι ίδια
        room1.editRoom("Σπίτι","Αλέξανδρος","Θεσσαλονίκη",100,30,3,true,true,true,true,true, LocalDate.parse("2021-12-31"),LocalDate.parse("2023-12-31"));
        assertNotEquals(temp, room1.toString());    //Έχει γίνει edit το δωμάτιο οπότε έχει αλλάξει το room1.toString() αλλά όχι το temp
        temp = "Τύπος: " + "Σπίτι" + ", όνομα: " + "Αλέξανδρος" + ", τοποθεσία: " + "Θεσσαλονίκη" + ", τιμή ανά διανυκτέρευση: " + 100 + ", επιφάνεια: " + 30 + ", κρεβάτια:" + 3 + ", WiFi: " + "true" + ", πάρκινγκ: " + "true" + ", πρωινό: " + "true" + ", κουζίνα: " + "true" + ", κλιματισμός: " + "true";
        assertEquals(temp,room1.toString());        //Είναι ίδια αφού έγιναν οι κατάλληλες αλλαγές στο temp
    }

    @Test
    public void removeFromFreeDates() {
        //Room1
        assertTrue(room1.isFree(LocalDate.parse("2021-12-31"),LocalDate.parse("2023-12-31"))); //room 1 is Free for all the initialized days
        ArrayList<LocalDate> temp = room1.removeFromFreeDates(LocalDate.parse("2022-12-31"),LocalDate.parse("2023-01-02"));  //remove days
        room1.setFreeDates(temp);
        assertFalse(room1.isFree(LocalDate.parse("2021-12-31"),LocalDate.parse("2023-12-31")));     //room1 is NOT Free for all the initialized days anymore
        assertTrue(room1.isFree(LocalDate.parse("2021-12-31"),LocalDate.parse("2022-12-30")));      //room1 is Free between the start and the removed days
        assertTrue(room1.isFree(LocalDate.parse("2023-01-02"),LocalDate.parse("2023-12-31")));      //room1 is Free between the removed days and the end
        assertFalse(room1.isFree(LocalDate.parse("2022-12-31"),LocalDate.parse("2022-01-01")));     //room1 is NOT Free for the deleted days

        //Room2
        assertTrue(room2.isFree(LocalDate.parse("2022-01-01"),LocalDate.parse("2022-12-31")));   //room2 is Free for all the days
        temp = room2.removeFromFreeDates(LocalDate.parse("2022-03-05"),LocalDate.parse("2022-03-06"));  //remove day
        room2.setFreeDates(temp);
        temp = room2.removeFromFreeDates(LocalDate.parse("2022-03-07"),LocalDate.parse("2022-03-08"));  //remove day
        room2.setFreeDates(temp);
        assertFalse(room2.isFree(LocalDate.parse("2022-01-01"),LocalDate.parse("2022-12-31")));   //room2 is NOT Free for all the days
        assertTrue(room2.isFree(LocalDate.parse("2022-01-01"),LocalDate.parse("2022-03-04")));    //room2 is Free between the start and the first removed day
        assertTrue(room2.isFree(LocalDate.parse("2022-03-06"),LocalDate.parse("2022-03-06")));    //room2 is Free between the two removed days
        assertTrue(room2.isFree(LocalDate.parse("2022-03-08"),LocalDate.parse("2022-12-31")));    //room2 is Free between the second removed day and the end

        assertFalse(room2.isFree(LocalDate.parse("2022-03-05"),LocalDate.parse("2022-03-05")));   // first removed day so it is NOT Free
        assertFalse(room2.isFree(LocalDate.parse("2022-03-07"),LocalDate.parse("2022-03-07")));   //second removed day so it is NOT Free
    }

    @Test
    public void isFree() {
        assertTrue(room1.isFree(LocalDate.parse("2021-12-31"),LocalDate.parse("2023-12-31"))); //initialized days
        assertTrue(room2.isFree(LocalDate.parse("2022-01-01"),LocalDate.parse("2022-12-31")));
        assertTrue(room1.isFree(LocalDate.parse("2021-12-31"),LocalDate.parse("2021-12-31"))); //First day for room1

        assertFalse(room1.isFree(LocalDate.parse("2021-12-30"),LocalDate.parse("2021-12-30"))); //1 day before the First day of room1
        assertFalse(room2.isFree(LocalDate.parse("2021-12-31"),LocalDate.parse("2021-12-31"))); //1 day before the First day of room2

        assertFalse(room1.isFree(LocalDate.parse("2022-12-31"),LocalDate.parse("2021-12-31"))); //Start is AFTER the END
        assertFalse(room2.isFree(LocalDate.parse("2022-12-31"),LocalDate.parse("2021-12-31")));
    }

    @Test
    public void editRoom() {
        assertTrue(room1.editRoom("Μεζονέτα","Anna", "Θεσσαλονίκη", 44,100,4,false,true,false,true,true, LocalDate.parse("2021-07-01"),LocalDate.parse("2023-07-18")));
        assertEquals("Μεζονέτα",room1.getType());
        assertEquals("Anna",room1.getName());
        assertEquals(LocalDate.parse("2021-07-01"),room1.getStart());
        assertEquals(LocalDate.parse("2023-07-18"),room1.getEnd());
        assertFalse(room1.editRoom("","Anna", "Θεσσαλονίκη", 44,100,4,false,true,false,true,true, LocalDate.parse("2021-07-01"),LocalDate.parse("2023-07-18")));
        assertFalse(room1.editRoom("Μεζονέτα","", "Θεσσαλονίκη", 44,100,4,false,true,false,true,true, LocalDate.parse("2021-07-01"),LocalDate.parse("2023-07-18")));
        assertFalse(room1.editRoom("Μεζονέτα","Anna", "", 44,100,4,false,true,false,true,true, LocalDate.parse("2021-07-01"),LocalDate.parse("2023-07-18")));
        assertFalse(room1.editRoom("Μεζονέτα","Anna", "Θεσσαλονίκη", -44,100,4,false,true,false,true,true, LocalDate.parse("2021-07-01"),LocalDate.parse("2023-07-18")));
        assertFalse(room1.editRoom("Μεζονέτα","Anna", "Θεσσαλονίκη", 44,-100,4,false,true,false,true,true, LocalDate.parse("2021-07-01"),LocalDate.parse("2023-07-18")));
        assertFalse(room1.editRoom("Μεζονέτα","Anna", "Θεσσαλονίκη", 44,100,-4,false,true,false,true,true, LocalDate.parse("2021-07-01"),LocalDate.parse("2023-07-18")));
        assertFalse(room1.editRoom("Μεζονέτα","Anna", "Θεσσαλονίκη", 44,100,4,false,true,false,true,true, LocalDate.parse("2023-07-01"),LocalDate.parse("2021-07-18")));
    }

    @Test
    public void getType() {
        assertEquals("Σπίτι",room1.getType());
        assertEquals("Διαμέρισμα",room2.getType());
    }

    @Test
    public void getName() {
        assertEquals("Χρήστος",room1.getName());
        assertEquals("Αλέξανδρος",room2.getName());
    }

    @Test
    public void getLocation() {
        assertEquals("Θεσσαλονίκη",room1.getLocation());
        assertEquals("Λάρισα",room2.getLocation());
    }

    @Test
    public void getArea() {
        assertEquals(30,room1.getArea());
        assertEquals(1,room2.getArea());
    }

    @Test
    public void getBeds() {
        assertEquals(3,room1.getBeds());
        assertEquals(1,room2.getBeds());
    }

    @Test
    public void getPrice() {
        assertEquals(100,room1.getPrice());
        assertEquals(1,room2.getPrice());
    }

    @Test
    public void hasWifi() {
        assertTrue(room1.hasWifi());
        assertFalse(room2.hasWifi());
    }

    @Test
    public void hasBreakfast() {
        assertTrue(room1.hasBreakfast());
        assertFalse(room2.hasBreakfast());
    }

    @Test
    public void hasKitchen() {
        assertTrue(room1.hasKitchen());
        assertFalse(room2.hasKitchen());
    }

    @Test
    public void hasParking() {
        assertTrue(room1.hasParking());
        assertFalse(room2.hasParking());
    }

    @Test
    public void hasAc() {
        assertTrue(room1.hasAc());
        assertFalse(room2.hasAc());
    }

    @Test
    public void getStart() {
        assertEquals(LocalDate.parse("2021-12-31"),room1.getStart());
        assertEquals(LocalDate.parse("2022-01-01"),room2.getStart());
    }

    @Test
    public void getEnd() {
        assertEquals(LocalDate.parse("2023-12-31"),room1.getEnd());
        assertEquals(LocalDate.parse("2022-12-31"),room2.getEnd());
    }

    @Test
    public void getProviderID() {
        assertEquals("test",room1.getProviderID());
        assertEquals("CHRIS",room2.getProviderID());
    }
}