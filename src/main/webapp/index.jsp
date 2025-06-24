<%@ page import="java.sql.Connection" %>
<%@ page import="com.website.DatabaseManager" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="com.website.User" %>
<%@ page import="javax.servlet.http.Cookie" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<%@ page import="com.website.DailyData" %>

<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.sql.SQLException" %>   <%-- This line is needed --%>


<%
    String email = null;
    String password = null;

    // ✅ Check for existing cookies
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
        for (Cookie cookie : cookies) {
            if ("email".equals(cookie.getName())) {
                email = cookie.getValue();
            } else if ("password".equals(cookie.getName())) {
                password = cookie.getValue();
            }
        }
    }

    // ✅ Proceed only if cookies were found
    if (email != null && password != null) {
        try (Connection con = DatabaseManager.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "SELECT * FROM user WHERE email=? AND password=?"
            );
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // ✅ Extract user info
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String street = rs.getString("street");
                String building = rs.getString("building");
                String apartment = rs.getString("apartment");
                float weight = rs.getFloat("weight");
                float height = rs.getFloat("height");
                String gender = rs.getString("gender");

                // ✅ Create User object
                User user = new User(email, firstName, lastName, password,
                        street, building, apartment,
                        weight, height, gender);


                request.getSession();
                session.setAttribute("user", user);

                List<DailyData> weeklyData = getLast7DaysData(email);
                session.setAttribute("weeklyData", weeklyData);


                response.sendRedirect("main.jsp");
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ❌ If not authenticated, redirect to login
    response.sendRedirect("HomePage.html");

%>

<%! public List<DailyData> getLast7DaysData(String email) {
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
} %>