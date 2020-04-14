package com.company;

public class Ticket {

    private int ticket;
    private int minutes;
    private Tariff tariff;


    public Ticket(int ticket, int minutes) {
        this.ticket = ticket;
        this.minutes = minutes;
        tariff.setMinutes(minutes);
    }

    public int getTicket() {
        return ticket;
    }

    public void setTicket(int ticket) {
        this.ticket = ticket;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }



}
