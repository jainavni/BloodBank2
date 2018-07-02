package com.example.gagan.bloodbank;

public class ListItem {
    String name, city,bloodgroup;

    public ListItem()
    {

    }

    public ListItem(String name, String city,String bloodgroup) {
        this.name = name;
        this.city = city;
        this.bloodgroup = bloodgroup;
        }

    public String getName() {
        return name;
    }



    public String getCity() {
        return city;
    }



    public String getBloodgroup() {
        return bloodgroup;
    }


}
