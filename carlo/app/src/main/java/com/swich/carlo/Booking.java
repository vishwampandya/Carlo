package com.swich.carlo;

import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Booking {
    String carID;
    String carMODEL;
    String carComp;
    String price;
    String drop;
    String pickUp;
    String bookID;
    String userID;
    String path;


    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Booking(String carID, String carMODEL, String carComp, String price, String drop, String pickUp, String bookID) {
        this.carID = carID;
        this.carMODEL = carMODEL;
        this.carComp = carComp;
        this.price = price;
        this.drop = drop;
        this.pickUp = pickUp;
        this.bookID = bookID;
    }

    public String getCarID() {
        return carID;
    }

    public void setCarID(String carID) {
        this.carID = carID;
    }

    public String getCarMODEL() {
        return carMODEL;
    }

    public void setCarMODEL(String carMODEL) {
        this.carMODEL = carMODEL;
    }

    public String getCarComp() {
        return carComp;
    }

    public void setCarComp(String carComp) {
        this.carComp = carComp;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDrop() {
        return drop;
    }

    public void setDrop(String drop) {
        this.drop = drop;
    }

    public String getPickUp() {
        return pickUp;
    }

    public void setPickUp(String pickUp) {
        this.pickUp = pickUp;
    }

    public String getBookID() {
        return bookID;
    }

    public void setBookID(String bookID) {
        this.bookID = bookID;
    }

    public String timeToDate(String timestamp) {
        Date date = new Date(Long.valueOf(timestamp)*1000L);
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));
        String formatted = format.format(date);
        Log.d("here-----", formatted);
       return formatted;
    }
    public String getpickDate(){
        return timeToDate(this.pickUp);
    }

    public String getdropDate(){
        return timeToDate(this.drop);
    }

}
