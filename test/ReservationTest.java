import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class ReservationTest {
    Customer c;
    Reservation reservation;
    @Before
    public void setUp() {
        c = new Customer("test2","test2","test2@gmail.com","test2","test2","test2",true);
        reservation = new Reservation(c.userID, LocalDate.parse("2022-01-01"),LocalDate.parse("2022-01-10"));
    }

    @Test
    public void setStart() {
        assertEquals("2022-01-01",String.valueOf(reservation.getReservationStart()));
        reservation.setStart(LocalDate.parse("2022-01-08"));
        assertEquals("2022-01-08",String.valueOf(reservation.getReservationStart()));
    }

    @Test
    public void setEnd() {
        assertEquals("2022-01-10",String.valueOf(reservation.getReservationEnd()));
        reservation.setEnd(LocalDate.parse("2022-01-15"));
        assertEquals("2022-01-15",String.valueOf(reservation.getReservationEnd()));
    }
}