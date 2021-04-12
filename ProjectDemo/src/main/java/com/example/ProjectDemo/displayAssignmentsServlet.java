package com.example.ProjectDemo;

import com.example.ProjectDemo.Courses;
import com.example.ProjectDemo.CoursesDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "displayAssignmentsServlet", value = "/display-assignments")
public class displayAssignmentsServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //System.out.println("Display Instructor Courses is Running");
        AssignmentDAO assignmentDao = new AssignmentDAO();
        UserDAO userdao = new UserDAO();
        HttpSession session = request.getSession();
        List<Assignments> assignmentsList = null;

        String email = (String) session.getAttribute("email");
        String userTypeVariable = (String) session.getAttribute("userType");
        int pageCourseID = Integer.parseInt((String)session.getAttribute("courseID"));
        System.out.println("I am printing the course ID: " + pageCourseID);
        System.out.println("I am in dispAssingServ: ");
        int userID = Integer.parseInt(session.getAttribute("studentID").toString());


        double courseGrade = 0;
        String courseLetterGrade = "";


        try {

            assignmentsList = assignmentDao.getAssignmentsList(pageCourseID);
            session.setAttribute("assignmentList", assignmentsList);
            if(userTypeVariable.equals("student")) {
                courseGrade = userdao.getCourseGrade(userID, pageCourseID);

                session.setAttribute("courseGrade", courseGrade);

                if (courseGrade <= 59 && courseGrade > 0.0) {
                    courseLetterGrade = "F";
                } else if (courseGrade >= 60 && courseGrade < 70) {
                    courseLetterGrade = "D";
                } else if (courseGrade >= 70 && courseGrade < 80) {
                    courseLetterGrade = "C";
                } else if (courseGrade >= 80 && courseGrade < 90) {
                    courseLetterGrade = "B";
                } else {
                    courseLetterGrade = "A";
                }

                session.setAttribute("letterGrade", courseLetterGrade);
            }



        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
