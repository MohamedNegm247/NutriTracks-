package com.test;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/Validation")
public class Validation extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException , IOException {
        response.getWriter().append("Served at: ").append(request.getContextPath());
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException , IOException{
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
   String uname =request.getParameter("uname");

   String password=request.getParameter("pass");
   String account =request.getParameter("account");
   if(account.equals("a")){
   if(uname.equals("1")&&password.equals("1") ){
       request.getRequestDispatcher("AdminPage").forward(request, response);

   }
   else{

       response.setContentType("text/html");
       PrintWriter w = response.getWriter();
       w.print("Invalid");
       // using "include" lets the current page (Validation) be responsible for the response
       request.getRequestDispatcher("index.html").include(request, response);
       w.print("Re-enter data");



   }

   }else{

request.getRequestDispatcher("UserPage").forward(request, response);

   }

    }

}
