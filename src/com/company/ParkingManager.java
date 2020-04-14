package com.company;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class ParkingManager {

    private int parkingCapacity;
    private LinkedList<ParkingSession> activeSessions = new LinkedList<>();
    private LinkedList<ParkingSession> archiveSessions = new LinkedList<>();
    private int ticket = 1;
    private List<Tariff> tariffs;

    public ParkingManager(int parkingCapacity, TariffLoader tariffLoader) {
        setParkingCapacity(parkingCapacity);
        tariffs = tariffLoader.load();
    }

    public int getParkingCapacity() {
        return parkingCapacity;
    }

    public void setParkingCapacity(int parkingCapacity) {
        this.parkingCapacity = parkingCapacity;
    }



    public ParkingSession enterParking(String carPlateNumber) {
        if (isExist(carPlateNumber) || activeSessions.size() == parkingCapacity) {
            return null;
        }
        ParkingSession parkingSession = new ParkingSession();
        parkingSession.setCarPlateNumber(carPlateNumber);
        parkingSession.setEntryDate(AppHelper.getInstance().getCurrentDate());
        parkingSession.setTicketNumber(ticket);
        ticket++;
        if (ticket == 10000) {
            ticket = 1;
        }else {
            activeSessions.add(parkingSession);
            ticket++;
        }
        return parkingSession;
        /*
         * Advanced task:
         * Link the new parking session to an existing user by car plate number (if such user exists)
         */
    }

    public boolean isExist(String carPlateNumber) {
        for (int i = 0; i < activeSessions.size(); i++)
            if (activeSessions.get(i).getCarPlateNumber().compareTo(carPlateNumber) == 0)
                return true;
        return false;
    }

    private ParkingSession findSession(int ticketNumber) {
        for (int i = 0; i < activeSessions.size(); i++)
            if (activeSessions.get(i).getTicketNumber() == ticketNumber)
                return activeSessions.get(i);
        return null;
    }
    private ParkingSession findSessionArchive(int ticketNumber) {
        for (int i = 0; i < archiveSessions.size(); i++)
            if (archiveSessions.get(i).getTicketNumber() == ticketNumber)
                return archiveSessions.get(i);
        return null;
    }

    public boolean TryLeaveParkingWithTicket(int ticketNumber) {
            ParkingSession parkingSession = findSession(ticketNumber);
            if (parkingSession == null){
                throw new IllegalArgumentException();
            }
            Calendar now = AppHelper.getInstance().getCurrentDate();
            int entryMinutes = CalendarUtil.countMinutesBetweenDates(parkingSession.getEntryDate(), now);
            if (entryMinutes < tariffs.get(0).getMinutes()){
                parkingSession.setExitDate(now);
                activeSessions.remove(parkingSession);
                archiveSessions.add(parkingSession);
                return true;
            }
            else if (parkingSession.getPaymantDate() == null){
                return false;
            }else
            {
                int minutesBeetwen = CalendarUtil.countMinutesBetweenDates(parkingSession.getPaymantDate(), now );
                if (minutesBeetwen > tariffs.get(0).getMinutes()){
                    return false;
                }else{
                    parkingSession.setExitDate(now);
                    activeSessions.remove(parkingSession);
                    archiveSessions.add(parkingSession);
                    return true;
                }
            }

        /*
         * Check that the car leaves parking within the free leave period
         * from the PaymentDt (or if there was no payment made, from the EntryDt)
         * 1. If yes:
         *   1.1 Complete the parking session by setting the ExitDt property
         *   1.2 Move the session from the list of active sessions to the list of past sessions             *
         *   1.3 return true and the completed parking session object in the out parameter
         *
         * 2. Otherwise, return false, session = null
         */
    }

    public int GetRemainingCost(int ticketNumber) {
        ParkingSession parkingSession = findSession(ticketNumber);
        Calendar now = AppHelper.getInstance().getCurrentDate();
        int totalAmount;
        Calendar start;
        if (parkingSession.getPaymantDate() == null)
            start = parkingSession.getEntryDate();
        else
            start = parkingSession.getPaymantDate();

        int totalMinute = CalendarUtil.countMinutesBetweenDates(start , now);
        Optional<Tariff> optionalTariff = tariffs.stream().filter(t -> t.getMinutes() >= totalMinute).findFirst();
        if (optionalTariff.isPresent())
            totalAmount = optionalTariff.get().getRate();
        else
            totalAmount = tariffs.get(tariffs.size()-1).getRate();

        return totalAmount;
    }

    public void PayForParking(int ticketNumber, int amount) {
        ParkingSession parkingSession = archiveSessions.get(ticketNumber);
        parkingSession.setTotalPaymant(amount);
        /*
         * Save the payment details in the corresponding parking session
         * Set PaymentDt to current date and time
         *
         * For simplicity we won't make any additional validation here and always
         * assume that the parking charge is paid in full
         */
    }

    public boolean tryLeaveWithin15minutes( ParkingSession parkingSession){

        Calendar now = Calendar.getInstance();
        int totalMinutes = CalendarUtil.countMinutesBetweenDates(parkingSession.getEntryDate(), now);
        if (totalMinutes <= 15){
            parkingSession.setExitDate(now);
            activeSessions.remove(parkingSession);
            archiveSessions.add(parkingSession);
            return true;
        }
        else{
            return false;
        }
    }
    /* ADDITIONAL TASK 2 */
    public boolean TryLeaveParkingByCarPlateNumber(String carPlateNumber) {

        Optional<ParkingSession> parkingSession = activeSessions.stream().filter(t -> t.getCarPlateNumber().equals(carPlateNumber)).findFirst();
        if (tryLeaveWithin15minutes(parkingSession.get())){
            return true;
        }
        if (( parkingSession).get().getPaymantDate() != null && tryLeaveWithin15minutes((ParkingSession)parkingSession)){
                return true;
        }else{
            parkingSession = null;
            return false;
        }
//        if (((ParkingSession) parkingSession).getPaymantDate() == null){
//        }
            /* There are 3 scenarios for this method:

            1. The user has not made any payments but leaves the parking within the free leave period
            from EntryDt:
               1.1 Complete the parking session by setting the ExitDt property
               1.2 Move the session from the list of active sessions to the list of past sessions             *
               1.3 return true and the completed parking session object in the out parameter

            2. The user has already paid for the parking session (session.PaymentDt != null):
            Check that the current time is within the free leave period from session.PaymentDt
               2.1. If yes, complete the session in the same way as in the previous scenario
               2.2. If no, return false, session = null

            3. The user has not paid for the parking session:
            3a) If the session has a connected user (see advanced task from the EnterParking method):
            ExitDt = PaymentDt = current date time;
            TotalPayment according to the tariff table:

            IMPORTANT: before calculating the parking charge, subtract FreeLeavePeriod
            from the total number of minutes passed since entry
            i.e. if the registered visitor enters the parking at 10:05
            and attempts to leave at 10:25, no charge should be made, otherwise it would be unfair
            to loyal customers, because an ordinary printed ticket could be inserted in the payment
            kiosk at 10:15 (no charge) and another 15 free minutes would be given (up to 10:30)

            return the completed session in the out parameter and true in the main return value

            3b) If there is no connected user, set session = null, return false (the visitor
            has to insert the parking ticket and pay at the kiosk)
            */

    }
}



//JavaCore, JavaColection, JUNIT, Pattern.
// SQL, GitHub,