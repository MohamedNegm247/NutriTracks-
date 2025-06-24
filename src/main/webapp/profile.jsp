<%--
  Created by IntelliJ IDEA.
  User: Mohamed
  Date: 5/20/2025
  Time: 3:39 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.website.User" %>
<%  com.website.User user = (com.website.User) session.getAttribute("user");
%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Profile - Fitness Tracker</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.5/dist/css/bootstrap.min.css" rel="stylesheet">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
  <style>
    :root {
      --primary-color: #2c3e50;
      --secondary-color: #3498db;
      --accent-color: #e74c3c;
      --success-color: #2ecc71;
      --text-light: #ecf0f1;
      --text-dark: #2c3e50;
    }

    body {
      position: relative;
      min-height: 100vh;
      background: none;
      overflow-x: hidden;
      font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    }

    body::before {
      content: "";
      position: fixed;
      top: 0;
      left: 0;
      width: 100%;
      height: 100%;
      background-image: url('./cover.jpg');
      background-size: cover;
      background-position: center;
      filter: blur(8px);
      z-index: -1;
    }

    .container {
      background: rgba(255, 255, 255, 0.95);
      padding: 2rem;
      border-radius: 15px;
      box-shadow: 0 8px 32px rgba(0, 0, 0, 0.1);
      backdrop-filter: blur(10px);
      max-width: 600px;
      margin: 5rem auto;
    }

    .profile-card {
      background: white;
      border-radius: 15px;
      padding: 2rem;
      box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
    }

    .form-control, .form-select {
      border-radius: 10px;
      padding: 0.8rem;
      border: 1px solid rgba(0, 0, 0, 0.1);
    }

    .form-control:focus, .form-select:focus {
      border-color: var(--secondary-color);
      box-shadow: 0 0 0 0.25rem rgba(52, 152, 219, 0.25);
    }

    .btn {
      border-radius: 10px;
      padding: 0.8rem;
      font-weight: 600;
    }

    .btn-primary {
      background-color: var(--secondary-color);
      border: none;
    }

    .btn-primary:hover {
      transform: translateY(-2px);
      box-shadow: 0 5px 15px rgba(52, 152, 219, 0.3);
    }

    .offcanvas {
      background: var(--primary-color);
      color: var(--text-light);
    }

    .offcanvas .nav-link {
      color: var(--text-light);
      transition: all 0.3s ease;
    }

    .offcanvas .nav-link.active,
    .offcanvas .nav-link:hover {
      background: var(--secondary-color);
      transform: translateX(5px);
    }
  </style>
</head>
<body>
<!-- Menu Button -->

<!-- Profile Form -->
<div class="container">
  <div class="profile-card">
    <h2 class="mb-4"><i class="fas fa-user me-2"></i>Profile Information</h2>
    <form id="profileForm">
      <div class="mb-3">
        <label class="form-label">Email</label>
        <input type="email" class="form-control" id="email" readonly value="<%= user.getEmail()%>">
      </div>
      <div class="mb-3">
        <label class="form-label">First Name</label>
        <input type="text" class="form-control" id="firstName" readonly value="<%= user.getFirstName()%>">
      </div>
      <div class="mb-3">
        <label class="form-label">Last Name</label>
        <input type="text" class="form-control" id="lastName" readonly value="<%= user.getLastName()%>">
      </div>
      <div class="mb-3">
        <label class="form-label">Gender</label>
        <input type="text" class="form-control"  readonly value="<%= user.getGender()%>">
      </div>

      <div class="mb-3">
        <label class="form-label">Weight</label>
        <input type="text" class="form-control"  readonly value="<%= user.getWeight()%>">
      </div>
      <div class="mb-3">
        <label class="form-label">Height</label>
        <input type="text" class="form-control"  readonly value="<%= user.getHeight()%>">
      </div>
      <div class="mb-3">
        <label class="form-label">Street</label>
        <input type="text" class="form-control"  readonly value="<%= user.getStreet()%>">
      </div>
      <div class="mb-3">
        <label class="form-label">Building</label>
        <input type="text" class="form-control"  readonly value="<%= user.getBuilding()%>">
      </div>

    </form>
  </div>
</div>


</body>
</html>
