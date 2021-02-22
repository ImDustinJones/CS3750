package com.example.ProjectDemo;

public class Instructors {
    private int instructorId;
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

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public int getUserId() {
        return instructorId;
    }

    public void setUserId(int instructorId) {
        this.instructorId = instructorId;
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

    public void setAddress(String address) { this.address = address; }

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

    @Override
    public String toString() {
        return "instructors{" +
                "instructorId=" + instructorId +
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
