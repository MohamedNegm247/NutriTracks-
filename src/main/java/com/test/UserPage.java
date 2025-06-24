package com.test;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/UserPage")
public class UserPage extends HttpServlet {


    protected  void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>User Page</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>User Page</h1>"+request.getParameter("uname") );
        out.println("</body>");
        out.println("</html>");
        
    }


}
