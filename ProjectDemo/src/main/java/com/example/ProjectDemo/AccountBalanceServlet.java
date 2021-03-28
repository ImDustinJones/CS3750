package com.example.ProjectDemo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

@WebServlet(name = "accountBalance", value = "/account-balance")
public class AccountBalanceServlet extends HttpServlet {
        @Override
        public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
                HttpSession session = request.getSession();
                String studentEmail = (String)session.getAttribute("email");
                int userID = Integer.parseInt(session.getAttribute("studentID").toString());

                UserDAO userdao = new UserDAO();
                int totalCredits = 0;
                int remainingAmount = 0;
                String latestPay = "";

                try {
                        totalCredits = userdao.getCredits(studentEmail);
                        session.setAttribute("studentsCredits", totalCredits);



                        remainingAmount = userdao.getRemainingTuition(userID);
                        session.setAttribute("remainingAmount", remainingAmount);

                        System.out.println("remaining amount is: "+ remainingAmount);

                        latestPay = userdao.getLatestPayment(userID);
                        session.setAttribute("latestPayment", latestPay);

                } catch (SQLException | ClassNotFoundException | ParseException throwables) {
                        throwables.printStackTrace();
                }

        }

        @Override
        protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        }
}
