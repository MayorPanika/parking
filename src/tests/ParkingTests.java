package tests;
import com.company.*;
import org.junit.*;

import java.util.Calendar;

import static org.junit.Assert.*;
import static java.lang.Math.*;

public class ParkingTests {

    @Test
    public void TestCountMinutesBetweenDates()
    {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(2018, Calendar.FEBRUARY, 27, 12, 0);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(2018, Calendar.FEBRUARY, 27 , 13, 10);
        int minutes = CalendarUtil.countMinutesBetweenDates(calendar2, calendar1);

    }

    @Test
    public void TestEnterParking()
    {
        ParkingManager parkingManager = new ParkingManager(50, new TestTariffLoader());
        Calendar calendar = Calendar.getInstance();
        calendar.set(2020, Calendar.FEBRUARY,15,11,1);
        AppHelper.getInstance().setCurrentDate(calendar);
        ParkingSession session = parkingManager.enterParking("A456GH");
        Calendar after = Calendar.getInstance();
        after.set(2020, Calendar.FEBRUARY, 15, 13,1);
        AppHelper.getInstance().setCurrentDate(after);
        boolean check = parkingManager.TryLeaveParkingWithTicket(session.getTicketNumber());
        assertFalse(check);
    }
    @Test
    public void TestFreeParking()
    {
        ParkingManager parkingManager = new ParkingManager(50, new TestTariffLoader());
        Calendar calendar = Calendar.getInstance();
        calendar.set(2020, Calendar.FEBRUARY,15,11,1);
        AppHelper.getInstance().setCurrentDate(calendar);
        ParkingSession session = parkingManager.enterParking("A456GH");
        Calendar after = Calendar.getInstance();
        after.set(2020, Calendar.FEBRUARY, 15, 11,6);
        AppHelper.getInstance().setCurrentDate(after);
        boolean check = parkingManager.TryLeaveParkingWithTicket(session.getTicketNumber());
        assertTrue(check);
    }
    @Test
    public void TestGetRemainCost()
    {
        ParkingManager parkingManager = new ParkingManager(50, new TestTariffLoader());
        Calendar calendar = Calendar.getInstance();
        calendar.set(2020, Calendar.FEBRUARY, 15, 11, 25);
        AppHelper.getInstance().setCurrentDate(calendar);
        ParkingSession session = parkingManager.enterParking("A2345WE");
        Calendar after = Calendar.getInstance();
        after.set(2020, Calendar.FEBRUARY, 15, 12, 25);
        AppHelper.getInstance().setCurrentDate(after);
        int actualValue = parkingManager.GetRemainingCost(session.getTicketNumber());
        assertEquals(100, actualValue);
    }
}
