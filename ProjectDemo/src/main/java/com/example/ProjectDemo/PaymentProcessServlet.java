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
        String cardHolderName = request.getParameter("CardholderName");
        String payAmount = request.getParameter("AmountToPay");

        //required key, remember to change to your key when testing
        String apiKey = "sk_test_51IXwV3G5AP18m2vGQiiYSXuKFdifjuKMvTS3kbhTMt6kZ8vi6OEwpznXZnjc3WrgvkueIVFeBlWsydg2ooXH3Rdn006l5Qk5m7";

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
        }
        response.sendRedirect("account_balance.jsp");//redirect back to the account balance page

    }

}