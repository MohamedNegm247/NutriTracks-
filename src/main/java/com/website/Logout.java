package com.website;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/Logout")
public class Logout extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("email".equals(cookie.getName())) {
                    String  email = cookie.getValue();
                } else if ("password".equals(cookie.getName())) {
                    String password = cookie.getValue();
                }
            }
        }
        String contextPath = request.getContextPath();


        Cookie emailCookie = new Cookie("email", "");
        Cookie passwordCookie = new Cookie("password", "");

     emailCookie.setPath(contextPath);
        passwordCookie.setPath(contextPath);

   emailCookie.setMaxAge(0);
        passwordCookie.setMaxAge(0);

       response.addCookie(emailCookie);
        response.addCookie(passwordCookie);


        request.getRequestDispatcher("index.jsp").forward(request, response);

    }

}
