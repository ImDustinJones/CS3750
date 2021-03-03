package com.example.ProjectDemo;

public class Assignments {
    private int assignmentID;
    private String assignmentName;
    private int courseID;
    private String instructorEmail;
    private String instructorLastName;
    private String assignmentDescription;
    private int points;
    private String dueDate;
    private String submissionType;

    public int getAssignmentID() {
        return assignmentID;
    }

    public void setAssignmentID(int assignmentID) {
        this.assignmentID = assignmentID;
    }

    public String getAssignmentName() {
        return assignmentName;
    }

    public void setAssignmentName(String assignmentName) {
        this.assignmentName = assignmentName;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public String getInstructorEmail() {
        return instructorEmail;
    }

    public void setInstructorEmail(String instructorEmail) {
        this.instructorEmail = instructorEmail;
    }

    public String getInstructorLastName() {
        return instructorLastName;
    }

    public void setInstructorLastName(String instructorLastName) {
        this.instructorLastName = instructorLastName;
    }

    public String getAssignmentDescription() {
        return assignmentDescription;
    }

    public void setAssignmentDescription(String assignmentDescription) {
        this.assignmentDescription = assignmentDescription;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getSubmissionType() {
        return submissionType;
    }

    public void setSubmissionType(String submissionType) {
        this.submissionType = submissionType;
    }

    @Override
    public String toString() {
        return "Assignments{" +
                "assignmentID=" + assignmentID +
                ", assignmentName='" + assignmentName + '\'' +
                ", courseID=" + courseID +
                ", instructorEmail='" + instructorEmail + '\'' +
                ", instructorLastName='" + instructorLastName + '\'' +
                ", assignmentDescription='" + assignmentDescription + '\'' +
                ", points=" + points +
                ", dueDate='" + dueDate + '\'' +
                ", submissionType='" + submissionType + '\'' +
                '}';
    }
}
