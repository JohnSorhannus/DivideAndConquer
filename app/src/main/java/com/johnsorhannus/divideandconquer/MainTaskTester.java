package com.johnsorhannus.divideandconquer;

import java.text.DateFormat;
import java.util.Calendar;

public class MainTaskTester {
    public static void main(String[] args) {
        /* Calendar date1 = Calendar.getInstance();
        date1.clear();
        date1.set(2019, 0, 12);

        MainTask m = new MainTask("Read You Are Here", 0xFF00CC00, date1);
        System.out.println(m.toString());

        Calendar date2 = Calendar.getInstance();
        date2.clear();
        date2.set(2019, 0, 7);
        SubTask s1 = new SubTask("Read Ch 1", date2);
        m.addSubTask(s1);

        Calendar date3 = Calendar.getInstance();
        date3.clear();
        date3.set(2019, 0, 8);
        SubTask s2 = new SubTask("Read Ch 2", date3);
        m.addSubTask(s2);
        System.out.println(m.toString());

        Calendar date4 = Calendar.getInstance();
        date4.clear();
        date4.set(2019, 0, 5);
        SubTask s3 = new SubTask("Read forward", date4);
        m.addSubTask(s3);
        System.out.println(m.toString());

        Calendar date5 = Calendar.getInstance();
        date4.clear();
        date5.set(2019, 0, 4);
        SubTask s4 = new SubTask("Burn book", date5);
        m.addSubTask(s4);
        System.out.println(m.toString());

        s4.changeState();
        System.out.println(m.toString());

        m.removeSubTask(s3);
        System.out.println(m.toString());

        s1.changeState();
        System.out.println(m.toString());

        s2.changeState();
        System.out.println(m.toString());

        Calendar date6 = Calendar.getInstance();
        date6.clear();
        date6.set(2019, 0, 8);
        SubTask s5 = new SubTask("Something...", date6);
        m.addSubTask(s5);
        System.out.println(m.toString());

        s1.changeState();
        System.out.println(m.toString());

        Calendar date7 = Calendar.getInstance();
        date7.set(2018, 11, 31);
        SubTask s6 = new SubTask("Celebrate the new year", date7);
        m.addSubTask(s6);
        System.out.println(m.toString());

        Calendar date8 = Calendar.getInstance();
        date8.set(2020, 0, 1);
        SubTask s7 = new SubTask("Drink Demarest Hill Winery Champaign", date8);
        m.addSubTask(s7);
        System.out.println(m.toString());

        Calendar date9 = Calendar.getInstance();
        date9.set(2019, 0, 10);
        SubTask s8 = new SubTask("Do nothing", date9);
        m.addSubTask(s8);
        System.out.println(m.toString());

        System.out.println("=====================================");
        MainTask dessert = new MainTask("Get Christmas Desserts", "#ff0000", Calendar.set(2018, 12, 25));
        SubTask d1 = new SubTask("Get sfogliatella", Calendar.set(2018, 12, 24));
        SubTask d2 = new SubTask("Get baltazar bread", Calendar.set(2018, 12, 24));
        SubTask d3 = new SubTask("Get Sant Ambroeus Cake", Calendar.set(2018, 12, 23));
        SubTask d4 = new SubTask("Get Palermo cake", Calendar.set(2018, 12, 24));
        dessert.addSubTask(d1);
        dessert.addSubTask(d2);
        dessert.addSubTask(d3);
        dessert.addSubTask(d4);
        System.out.println(dessert.toString());

        d1.ChangeCompleted();
        d2.ChangeCompleted();
        d4.ChangeCompleted();
        System.out.println(dessert.toString());

        d3.ChangeCompleted();
        System.out.println(dessert.toString());

        Calendar date31 = Calendar.getInstance();
        date31.set(2019, 11, 31);
        System.out.println(DateFormat.getDateInstance().format(date31.getTime()));
        date31.add(Calendar.DAY_OF_YEAR, 1);
        System.out.println(DateFormat.getDateInstance().format(date31.getTime())); */

        Calendar date1 = Calendar.getInstance();
        date1.set(Calendar.HOUR_OF_DAY, 0); //11 or 23 or 0?
        date1.set(Calendar.MINUTE, 0);
        date1.set(Calendar.SECOND, 0);
        date1.set(Calendar.MILLISECOND, 0);
        System.out.println(date1.getTimeInMillis());

        Calendar date2 = Calendar.getInstance();
        date2.set(Calendar.HOUR_OF_DAY, 11); //11 or 23 or 0?
        date2.set(Calendar.MINUTE, 0);
        date2.set(Calendar.SECOND, 0);
        date2.set(Calendar.MILLISECOND, 0);
        System.out.println(date2.getTimeInMillis());

        Calendar date3 = Calendar.getInstance();
        date3.set(Calendar.HOUR_OF_DAY, 23); //11 or 23 or 0?
        date3.set(Calendar.MINUTE, 0);
        date3.set(Calendar.SECOND, 0);
        date3.set(Calendar.MILLISECOND, 0);
        System.out.println(date3.getTimeInMillis());
    }

}
