package com.example.ProjectDemo;

public class Regestrations {
    private int courseID;
    private String studentEmail;

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }


    @Override
    public String toString() {
        return "Registrations{" +
                ", courseID=" + courseID +
                ", studentEmail='" + studentEmail +
                '}';
    }
}
