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

                UserDAO userdao = new UserDAO();
                int totalCredits = 0;
                int totalAmount = 0;

                try {
                        totalCredits = userdao.getCredits(studentEmail);
                        session.setAttribute("studentsCredits", totalCredits);
                        totalAmount = totalCredits*100;
                        session.setAttribute("studentTotal", totalAmount);
                } catch (SQLException throwables) {
                        throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                } catch (ParseException e) {
                        e.printStackTrace();
                }

        }

        @Override
        protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        }
}
