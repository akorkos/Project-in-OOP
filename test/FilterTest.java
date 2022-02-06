import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class FilterTest {
    Filter filter1;
    Filter filter2;
    Room testRoom1;
    Room testRoom2;
    @Before
    public void setUp(){
        filter1 = new Filter("location", LocalDate.parse("2022-01-01"),LocalDate.parse("2023-12-31"),3,"Σπίτι",30,50,100,true,true,true,true,true);
        filter2 = new Filter("location", LocalDate.parse("2023-01-01"),LocalDate.parse("2024-12-31"),3,"Σπίτι",30,50,200,false,false,false,false,false);
        testRoom1 = new Room("Σπίτι","Alex", "location", 75,38,1,true,true,true,true,true, LocalDate.parse("2021-12-31"),LocalDate.parse("2025-12-31"),"test");
        testRoom2 = new Room("Σπίτι","Anton", "location", 75,47,4,false,false,false,false,false, LocalDate.parse("2021-12-31"),LocalDate.parse("2029-12-31"),"test");

    }

    @Test
    public void extVerify() {
        assertFalse(filter1.extVerify(testRoom1));
        assertFalse(filter1.extVerify(new Room("Σπίτι","Anton", "Λάθος", 75,47,4,true,true,true,true,true, LocalDate.parse("2021-12-31"),LocalDate.parse("2029-12-31"),"test")));
        assertTrue(filter2.extVerify(testRoom2));
    }

    @Test
    public void getStart() {
        assertEquals(LocalDate.parse("2022-01-01"),filter1.getStart());
        assertEquals(LocalDate.parse("2023-01-01"),filter2.getStart());
    }

    @Test
    public void getEnd() {
        assertEquals(LocalDate.parse("2023-12-31"),filter1.getEnd());
        assertEquals(LocalDate.parse("2024-12-31"),filter2.getEnd());
    }
}