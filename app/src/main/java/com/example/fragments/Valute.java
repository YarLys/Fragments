package com.example.fragments;

import android.graphics.Bitmap;

public class Valute {
    public String Name;

    public String getName() {
        return Name;
    }

    public String getValue() {
        return Value;
    }

    public Bitmap getPicture() {
        return picture;
    }

    public String Value;
    public Bitmap picture;

    public Valute(String name, String value, Bitmap picture) {
        Name = name;
        Value = value;
        this.picture = picture;
    }
}
