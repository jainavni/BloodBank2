package com.example.gagan.bloodbank;

public class UserInfo {
    String name,email,address,city,gender,bloodgroup,mob,donor;

    public UserInfo(String name, String email, String address, String city, String gender, String bloodgroup, String mob,String donor) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.city = city;
        this.gender = gender;
        this.bloodgroup = bloodgroup;
        this.mob = mob;
        this.donor=donor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBloodgroup() {
        return bloodgroup;
    }

    public void setBloodgroup(String bloodgroup) {
        this.bloodgroup = bloodgroup;
    }

    public String getMob() {
        return mob;
    }

    public void setMob(String mob) {
        this.mob = mob;
    }

    public String getDonor() {
        return donor;
    }

    public void setDonor(String donor) {
        this.donor = donor;
    }
}
