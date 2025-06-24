<%@ page import="java.sql.Connection" %>
<%@ page import="com.website.DatabaseManager" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Food Operation</title>
</head>
<body>
<%
  try (Connection con = DatabaseManager.getConnection()) {
    String formName = request.getParameter("formName");

    if ("Delete_food".equals(formName)) {
      String Sfood_id = request.getParameter("food_id");
      int Food_ID = Integer.parseInt(Sfood_id);
      PreparedStatement prstmt = con.prepareStatement("DELETE FROM food WHERE food_id=?");
      prstmt.setInt(1, Food_ID);
      int rowsAffected = prstmt.executeUpdate();

      if (rowsAffected > 0) {
        response.getWriter().println("<script>alert('Food Deleted Successfully');</script>");
      } else {
        response.getWriter().println("<script>alert('No food item found with that ID');</script>");
      }
      request.getRequestDispatcher("/Admin_food.html").include(request, response);


    } else if ("Add_food".equals(formName)) {
      String FoodName = request.getParameter("name");
      String category = request.getParameter("category");
      String protein=request.getParameter("protein");

      String carbs=request.getParameter("carbs");

      String fat=request.getParameter("fat");
      PreparedStatement prstmt = con.prepareStatement("INSERT INTO food (name, category,protein,carbs,fat) VALUES (?, ?,?,?,?)");
      prstmt.setString(1, FoodName);
      prstmt.setString(2, category);
      prstmt.setString(3, protein);
      prstmt.setString(4, carbs);
      prstmt.setString(5, fat);

      int rowsInserted = prstmt.executeUpdate();

      if (rowsInserted > 0) {
        response.getWriter().println("<script>alert('Food Added Successfully');</script>");
      } else {
        response.getWriter().println("<script>alert('Failed to Add Food');</script>");
      }
      request.getRequestDispatcher("/Admin_food.html").include(request, response);
    }
  } catch (Exception e) {
    e.printStackTrace();
    response.getWriter().println("<script>alert('Error: " + e.getMessage().replace("'", "\\'") + "');</script>");
  }
%>
</body>
</html>