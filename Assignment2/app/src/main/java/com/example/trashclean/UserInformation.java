package com.example.trashclean;

public class UserInformation {
    public String name;
    public String surname;
    public String phone;

    public UserInformation(){
    }

    public UserInformation(String name,String surname, String phone){
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
    public String getUserPhone() {
        return phone;
    }
}
