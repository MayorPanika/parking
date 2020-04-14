package com.company;

import java.math.BigDecimal;
import java.util.Calendar;

public class ParkingSession {
    private Calendar entryDate;
    private Calendar exitDate;
    private Calendar paymantDate;
    private int totalPaymant;
    private String carPlateNumber;
    private int ticketNumber;

    public Calendar getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Calendar entryDate) {
        this.entryDate = entryDate;
    }

    public Calendar getExitDate() {
        return exitDate;
    }

    public void setExitDate(Calendar exitDate) {
        this.exitDate = exitDate;
    }

    public Calendar getPaymantDate() {
        return paymantDate;
    }

    public void setPaymantDate(Calendar paymantDate) {
        this.paymantDate = paymantDate;
    }

    public int getTotalPaymant() {
        return totalPaymant;
    }

    public void setTotalPaymant(int totalPaymant) {
        this.totalPaymant = totalPaymant;
    }

    public String getCarPlateNumber() {
        return carPlateNumber;
    }

    public void setCarPlateNumber(String carPlateNumber) {
        this.carPlateNumber = carPlateNumber;
    }

    public int getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(int ticketNumber) {
        this.ticketNumber = ticketNumber;
    }
}
