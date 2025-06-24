package com.website;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet("/Login")
public class Login extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String remember = request.getParameter("remember");
        if (email.equals("admin@gmail.com") && password.equals("admin")) {
            request.getRequestDispatcher("/Admin.html").forward(request, response);
        } else {

            try (Connection con = DatabaseManager.getConnection()) {
                PreparedStatement ps = con.prepareStatement("SELECT * FROM user WHERE email=? AND password=?");
                ps.setString(1, email);
                ps.setString(2, password);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    String firstName = rs.getString("first_name");
                    String lastName = rs.getString("last_name");
                    String street = rs.getString("street");
                    String building = rs.getString("building");
                    String apartment = rs.getString("apartment");
                    float weight = rs.getFloat("weight");
                    float height = rs.getFloat("height");
                    String gender = rs.getString("gender");
                    User user = new User(email, firstName, lastName, password, street, apartment, weight, height, gender);

                    HttpSession session = request.getSession();
                    session.setAttribute("user", user);
                    List<DailyData> weeklyData = getLast7DaysData(email);
                    session.setAttribute("weeklyData", weeklyData);
                    if(emailExists(user.getEmail())) {
                        if ("on".equals(remember)) {
                            Cookie emailCookie = new Cookie("email", email);
                            Cookie passwordCookie = new Cookie("password", password);
                            emailCookie.setMaxAge(7 * 24 * 60 * 60);
                            passwordCookie.setMaxAge(7 * 24 * 60 * 60);
                            response.addCookie(emailCookie);
                            response.addCookie(passwordCookie);
                        }




                        request.getRequestDispatcher("/main.jsp").forward(request, response);
                    } else{


                        request.getRequestDispatcher("/payment.html").forward(request, response);
                    }




                } else {
                    response.setContentType("text/html");
                    PrintWriter out = response.getWriter();
                    out.println("<script type='text/javascript'>");
                    out.println("alert('Invalid email or password');");

                    out.println("</script>");

                    request.getRequestDispatcher("/Login.html").include(request, response);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public List<DailyData> getLast7DaysData(String email) {
        List<DailyData> dataList = new ArrayList<>();
        String sql = "SELECT date, protein, carbs, fat " +
                "FROM user_daily_data " +
                "WHERE email = ? " +
                "AND date >= CURDATE() - INTERVAL 6 DAY " +
                "ORDER BY date;";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Date date = rs.getDate("date");
                int protein = rs.getInt("protein");
                int carbs = rs.getInt("carbs");
                int fat = rs.getInt("fat");

                DailyData data = new DailyData(date, protein, carbs, fat);
                dataList.add(data);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dataList;
    }

    public static boolean emailExists(String email) {


        String sql = "SELECT * FROM Payment WHERE email = ? LIMIT 1";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            return rs.next();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


}