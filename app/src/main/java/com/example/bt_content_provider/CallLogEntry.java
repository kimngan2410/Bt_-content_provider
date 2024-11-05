package com.example.bt_content_provider;

public class CallLogEntry {
    private String number;
    private String type;
    private String date;

    public CallLogEntry(String number, String type, String date) {
        this.number = number;
        this.type = type;
        this.date = date;
    }

    public String getNumber() {
        return number;
    }

    public String getType() {
        return type;
    }

    public String getDate() {
        return date;
    }
}
