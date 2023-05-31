package com.example.signup_signin;

import android.content.Intent;

public class DataHolder {

    String name,age,stream,image;

    public DataHolder(String name, String age, String stream, String image) {
        this.name = name;
        this.age = age;
        this.stream = stream;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getStream() {
        return stream;
    }

    public void setStream(String stream) {
        this.stream = stream;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
