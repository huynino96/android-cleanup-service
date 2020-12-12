package com.example.firebaseauthproject;

public class Userinformation {

    public String name;
    public String surname;
    public String phone;

    public Userinformation(){
    }

    public Userinformation(String name,String surname, String phone){
        this.name = name;
        this.surname = surname;
        this.phone = phone;
    }
    public String getUserName() {
        return name;
    }
    public String getUserSurname() {
        return surname;
    }
    public String getUserPhoneno() {
        return phone;
    }
}
