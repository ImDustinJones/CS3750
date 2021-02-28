package com.example.ProjectDemo;

public class Users {
    private int userId;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String birthdate;
    private String bio;
    private String address;
    private String city;
    private String state;
    private String zip;
    private String phonenumber;
    private String link1;
    private String link2;
    private String link3;

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBio() { return bio;}

    public void setBio(String bio) { this.bio = bio; }

    public String getAddress() { return address; }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() { return zip; }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getPhonenumber() { return phonenumber; }

    public void setPhonenumber(String phonenumber) { this.phonenumber = phonenumber; }

    public String getLink1() {
        return link1;
    }

    public String getLink2() {
        return link2;
    }

    public String getLink3() {
        return link3;
    }

    public void setLink1(String link1) {
        this.link1 = link1;
    }

    public void setLink2(String link2) {
        this.link2 = link2;
    }

    public void setLink3(String link3) {
        this.link3 = link3;
    }

    @Override
    public String toString() {
        return "Users{" +
                "userId=" + userId +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", birthdate='" + birthdate + '\'' +
                ", bio='" + bio + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zip='" + zip + '\'' +
                ", phonenumber='" + phonenumber + '\'' +
                '}';
    }
}
