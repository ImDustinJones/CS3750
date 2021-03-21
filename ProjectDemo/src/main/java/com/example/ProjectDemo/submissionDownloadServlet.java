package com.example.ProjectDemo;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.text.ParseException;

@WebServlet(name = "submissionDownload", value = "/submissionDownload")
public class submissionDownloadServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String path = "C:\\FileSubmissions\\" + request.getParameter("submission");
        File download  = new File(path);
        FileInputStream in = new FileInputStream(download);
        ServletContext context = getServletContext();
        String mimeType = context.getMimeType(path);

        response.setContentType(mimeType);
        response.setContentLength((int) download.length());

        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"", download.getName());
        response.setHeader(headerKey, headerValue);

        OutputStream out = response.getOutputStream();

        byte[] buffer = new byte[4096];
        int bytesRead = -1;

        while ((bytesRead = in.read(buffer)) != -1) {
            out.write(buffer, 0, bytesRead);
        }

        in.close();
        out.close();



        response.sendRedirect("assignment_main.jsp?courseID=" + request.getParameter("courseID")
                +"&assignmentID=" + request.getParameter("assignmentID"));
    }

}
