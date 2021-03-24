package com.example.ProjectDemo;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.SQLException;

@WebServlet(name = "PaymentProcessServlet", value = "/PaymentProcessServlet")
public class PaymentProcessServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {



    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        UserDAO usersdao = new UserDAO();
        int userID = Integer.parseInt(session.getAttribute("studentID").toString());
        String cardHolderName = request.getParameter("CardholderName");
        String originalPayAmount = request.getParameter("AmountToPay");
        String payAmount = request.getParameter("AmountToPay") + "00";


        //required key, remember to change to your key when testing
        String apiKey = "sk_test_51IYJVSLmddznJdQDzx99FGYHRbS6QCF2PKyRAkJ7GBsvCfvWSNaj1gRvvfyik5B6MAn78qWgbzIo5KQqShCEQ03u00LcI8pos5";

        //Url the REST request will be sent to
        URL url = new URL("https://api.stripe.com/v1/tokens?key=" + apiKey + "&card[number]=" +
                request.getParameter("CardNumber") + "&card[exp_month]=" + request.getParameter("cardMonth")
                +"&card[exp_year]=" + request.getParameter("cardYear") + "&card[cvc]=" + request.getParameter("CVC"));
        HttpsURLConnection con = (HttpsURLConnection) url.openConnection();//connect
        con.setRequestMethod("POST");//set method
        con.setRequestProperty("content-length", "0");

        int status = con.getResponseCode();
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while((inputLine = in.readLine()) != null) {
            content.append(inputLine);//read in the response, should generate a token
        }
        in.close();
        con.disconnect();

        if(status >= 200 && status <= 299){//if the request was successful
            String contentString = content.toString();
            String tokenId = contentString.substring(contentString.indexOf("\"id\": \"") + 7);
            tokenId = tokenId.substring(0, tokenId.indexOf("\","));//get the token's id for use in charge

            URL chargeUrl = new URL("https://api.stripe.com/v1/charges?key=" + apiKey + "&amount=" +
                    payAmount + "&currency=usd"
                    +"&source=" + tokenId);
            HttpsURLConnection conn = (HttpsURLConnection) chargeUrl.openConnection();//connect
            conn.setRequestMethod("POST");//set method
            conn.setRequestProperty("content-length", "0");

            System.out.println(conn.getResponseCode());
            System.out.println(payAmount.getClass());
            System.out.println(chargeUrl);

            if(conn.getResponseCode() == 200) {
                System.out.println("payment was successful");
                try {
                    usersdao.updatePayTuition(Integer.parseInt(originalPayAmount), userID);
                } catch (SQLException | ClassNotFoundException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
        response.sendRedirect("account_balance.jsp");//redirect back to the account balance page

    }

}