package com.example.cineapp;

public class Event {
    private String date;
    private String location;
    private String imageUrl;

    public Event(String date, String location, String imageUrl) {
        this.date = date;
        this.location = location;
        this.imageUrl = imageUrl;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
