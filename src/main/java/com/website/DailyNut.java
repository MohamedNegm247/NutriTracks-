package com.website;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet("/DailyNut")
public class DailyNut extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();




        String foodName = request.getParameter("foodName");
        int quantity = 0;
        String errorMessage = null;

        try {
            quantity = Integer.parseInt(request.getParameter("quantity"));

            try (Connection con = DatabaseManager.getConnection()) {
                PreparedStatement ps = con.prepareStatement(
                        "SELECT name, category, protein, carbs, fat FROM food WHERE name = ?");
                ps.setString(1, foodName);

                ResultSet rs = ps.executeQuery();
                if (rs.next()) {

                    Food currentFood = new Food();
                    currentFood.setName(rs.getString("name"));
                    currentFood.setCategory(rs.getString("category"));
                    currentFood.setProtein((rs.getFloat("protein") * quantity) / 100);
                    currentFood.setCarbs((rs.getFloat("carbs") * quantity) / 100);
                    currentFood.setFat((rs.getFloat("fat") * quantity) / 100);


                    User user = (User) session.getAttribute("user");
                    insertOrUpdateTodayData(user.getEmail(), currentFood);
                    List<DailyData> weeklyData = getLast7DaysData(user.getEmail());
                    session.setAttribute("weeklyData", weeklyData);



                    request.setAttribute("currentFood", currentFood);
                } else {
                    errorMessage = "Food not found: " + foodName;
                }
            } catch (SQLException e) {
                errorMessage = "Database error occurred";
                e.printStackTrace();
            }
        } catch (NumberFormatException e) {
            errorMessage = "Invalid quantity value";
        }

        if (errorMessage != null) {
            request.setAttribute("error", errorMessage);
        }


        request.getRequestDispatcher("/main.jsp").forward(request, response);
    }


    public List<DailyData> getLast7DaysData(String email) {
        List<DailyData> dataList = new ArrayList<>();
        String sql = "SELECT date, protein, carbs, fat FROM user_daily_data WHERE email = ? AND date >= CURDATE() - INTERVAL 6 DAY ORDER BY date";

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

    public void insertOrUpdateTodayData(String email, Food food) {
        String selectSQL = "SELECT * FROM user_daily_data WHERE email = ? AND date = CURDATE()";
        String insertSQL = "INSERT INTO user_daily_data (email, date, protein, carbs, fat) VALUES (?, CURDATE(), ?, ?, ?)";
        String updateSQL = "UPDATE user_daily_data SET protein = protein + ?, carbs = carbs + ?, fat = fat + ? WHERE email = ? AND date = CURDATE()";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement selectStmt = conn.prepareStatement(selectSQL)) {

            selectStmt.setString(1, email);
            ResultSet rs = selectStmt.executeQuery();

            if (rs.next()) {

                try (PreparedStatement updateStmt = conn.prepareStatement(updateSQL)) {
                    updateStmt.setFloat(1, food.getProtein());
                    updateStmt.setFloat(2, food.getCarbs());
                    updateStmt.setFloat(3, food.getFat());
                    updateStmt.setString(4, email);
                    updateStmt.executeUpdate();
                }
            } else {

                try (PreparedStatement insertStmt = conn.prepareStatement(insertSQL)) {
                    insertStmt.setString(1, email);
                    insertStmt.setFloat(2, food.getProtein());
                    insertStmt.setFloat(3, food.getCarbs());
                    insertStmt.setFloat(4, food.getFat());
                    insertStmt.executeUpdate();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
