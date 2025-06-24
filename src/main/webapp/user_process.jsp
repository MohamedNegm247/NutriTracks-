<%@ page import="com.website.DatabaseManager" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.PreparedStatement" %><%--
  Created by IntelliJ IDEA.
  User: User
  Date: 5/10/2025
  Time: 2:30 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Title</title>
</head>
<body>
<%
  try (Connection con = DatabaseManager.getConnection()) {
    String formName = request.getParameter("formName");

    if ("Add_user".equals(formName)) {

      String email = request.getParameter("email");
      String password = request.getParameter("password");
      String gender = request.getParameter("gender");
      String firstName = request.getParameter("first_name");
      String lastName = request.getParameter("last_name");
      String street = request.getParameter("street");
      String building = request.getParameter("building");
      String apartment = request.getParameter("apartment");
      double weight = Double.parseDouble(request.getParameter("weight"));
      double height = Double.parseDouble(request.getParameter("height"));
      String insertSql = "INSERT INTO user (email, password, gender, first_name," +
              " last_name, street, building, weight, height,apartment) " +
              "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
      PreparedStatement insertStmt = con.prepareStatement(insertSql);
      insertStmt.setString(1, email);
      insertStmt.setString(2, password);
      insertStmt.setString(3, gender);
      insertStmt.setString(4, firstName);
      insertStmt.setString(5, lastName);
      insertStmt.setString(6, street);
      insertStmt.setString(7, building);
      insertStmt.setDouble(8, weight);
      insertStmt.setDouble(9, height);
      insertStmt.setString(10, apartment);
      int rowsInserted = insertStmt.executeUpdate();
      if (rowsInserted > 0) {

        response.getWriter().println("<script>alert('user added ');</script>");

      } else {
        response.getWriter().println("<script>alert('error');</script>");
      }
      request.getRequestDispatcher("/Admin_users.html").include(request, response);


    } else if ("Delete_user".equals(formName)) {
      String email = request.getParameter("email");

      String deletesql = "Delete from user where email=?";
      PreparedStatement deleteStmt = con.prepareStatement(deletesql);
      deleteStmt.setString(1,email);
      int rowdeleted = deleteStmt.executeUpdate();
      if (rowdeleted > 0) {

        response.getWriter().println("<script>alert('user deleted ');</script>");

      } else {
        response.getWriter().println("<script>alert('error');</script>");
      }
      request.getRequestDispatcher("/Admin_users.html").include(request, response);


    } else if ("Edit_user".equals(formName)) {
      String email = request.getParameter("email");
      String pasword=request.getParameter("password");

      String updateSql = "UPDATE user SET password = ? WHERE email = ?";
      PreparedStatement updatestmt = con.prepareStatement(updateSql);
      updatestmt.setString(1, pasword);
      updatestmt.setString(2,email);
      int rowupdated = updatestmt.executeUpdate();
      if (rowupdated > 0) {
        response.getWriter().println("<script>alert('password update ');</script>");

      } else {

        response.getWriter().println("<script>alert('error');</script>");
      }
      request.getRequestDispatcher("/Admin_users.html").include(request, response);


    }

  } catch (Exception e) {
    e.printStackTrace();
    response.getWriter().println("<script>alert('Error: " + e.getMessage().replace("'", "\\'") + "');</script>");
  }




%>
</body>
</html>