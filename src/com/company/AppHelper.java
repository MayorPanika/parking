package com.company;

import java.io.File;
import java.util.Calendar;

public class AppHelper {
    private static AppHelper instance;

    public static AppHelper getInstance()
    {
        if (instance == null)
            instance = new AppHelper();
        return instance;
    }

    private AppHelper(){}

    private Calendar currentDate;


    public Calendar getCurrentDate() {
        if (currentDate == null)
            return Calendar.getInstance();
        return currentDate;
    }

    public void setCurrentDate(Calendar currentDate) {
        this.currentDate = currentDate;
    }
}
