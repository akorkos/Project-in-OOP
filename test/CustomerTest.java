import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class CustomerTest {
    private Customer user;
    private Room firstRoom, secondRoom;
    private Reservation firstReservation, secondReservation, thirdReservation;
    private ArrayList<Room> rooms;
    private ArrayList<Reservation> reservationsOfFirstRoom, reservationsOfSecondRoom;
    private ArrayList<Reservation> reservations;

    @Before
    public void init() {
        user = new Customer("bmye","12345678","bmye@gmail.com","Panagiotis","Papakiriati 23", "1", true);

        firstReservation = new Reservation("1",LocalDate.parse("2022-01-01"), LocalDate.parse("2022-01-09"));
        secondReservation = new Reservation("1",LocalDate.parse("2022-01-12"), LocalDate.parse("2022-01-19"));
        thirdReservation = new Reservation("1",LocalDate.parse("2022-01-28"), LocalDate.parse("2022-02-01"));

        reservationsOfFirstRoom = new ArrayList<>();
        reservationsOfSecondRoom = new ArrayList<>();

        reservationsOfFirstRoom.add(firstReservation);
        reservationsOfFirstRoom.add(secondReservation);
        reservationsOfSecondRoom.add(thirdReservation);

        reservations = new ArrayList<>();

        reservations.addAll(reservationsOfFirstRoom);
        reservations.addAll(reservationsOfSecondRoom);

        firstRoom = new Room("Σπίτι", "AnnaMaria BB","Λάρισα",150, 80, 3, true, true, true, false, true, LocalDate.parse("2021-12-13"), LocalDate.parse("2022-05-25"),"2");
        secondRoom = new Room("Μεζονέτα", "Petra House","Λάρισα",59, 24, 2, true, true, false, false, true, LocalDate.parse("2021-01-01"), LocalDate.parse("2022-12-25"),"4");

        firstRoom.setReservations(reservationsOfFirstRoom);
        secondRoom.setReservations(reservationsOfSecondRoom);

        rooms = new ArrayList<>();

        rooms.add(firstRoom);
        rooms.add(secondRoom);

        user.setRooms(rooms);
        user.setReservations(reservations);
    }

    @Test
    public void getRooms(){
        assertEquals(rooms, user.getRooms());
    }

    @Test
    public void getReservations(){
        assertEquals(reservations, user.getReservations());
    }

    @Test
    public void setReservations(){
        ArrayList<Reservation> reservations = new ArrayList<>();
        reservations.add(new Reservation("1",LocalDate.parse("2022-01-01"), LocalDate.parse("2022-01-09")));
        user.setReservations(reservations);
        assertEquals(reservations, user.getReservations());
    }

    @Test
    public void setRooms(){
        ArrayList<Room> rooms = new ArrayList<>();
        rooms.add(new Room("Σπίτι", "Stav's Apartments","Αθήνα",150, 80, 3, true, false, true, false, true, LocalDate.parse("2021-12-13"), LocalDate.parse("2022-05-25"),"2"));
        user.setRooms(rooms);
        assertEquals(rooms, user.getRooms());
    }

    @Test
    public void reservation(){
        assertTrue(user.reservation(secondRoom, LocalDate.parse("2021-07-10"), LocalDate.parse("2021-07-11")));
        assertFalse(user.reservation(secondRoom, LocalDate.parse("2021-07-10"), LocalDate.parse("2021-07-11")));
        user.cancellation(3);
        assertTrue(user.reservation(secondRoom, LocalDate.parse("2021-07-10"), LocalDate.parse("2021-07-11")));
    }

    @Test
    public void cancellation(){
        user.reservation(firstRoom, LocalDate.parse("2021-07-10"), LocalDate.parse("2021-07-11"));
        assertTrue(user.cancellation(user.getReservations().size() - 1));
        assertFalse(user.cancellation(100));
    }

    @Test
    public void search() {
        user.search(new Filter("Λάρισα", LocalDate.parse("2022-02-12"), LocalDate.parse("2022-02-18"), 1, "Σπίτι", 41, 91, 174, true, true, true, false, true), true);
        assertEquals(user.getQuery().get(0), firstRoom);

        user.search(new Filter("Λάρισα", LocalDate.parse("2022-02-12"), LocalDate.parse("2022-02-18"), 1), false);
        assertEquals(user.getQuery().get(0), firstRoom);

        user.search(new Filter("Παρίσι", LocalDate.parse("2022-02-12"), LocalDate.parse("2022-02-18"), 1), false);
        assertTrue(user.getQuery().isEmpty());

        user.search(new Filter("Λάρισα", LocalDate.parse("2022-02-12"), LocalDate.parse("2022-02-18"), 1, "Σπίτι", 259, 1405, 1587, true, true, true, false, true), true);
        assertTrue(user.getQuery().isEmpty());
    }
}
