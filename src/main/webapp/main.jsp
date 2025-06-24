<%@ page import="java.sql.Connection" %>
<%@ page import="com.website.DatabaseManager" %>
<%@ page import="com.website.Food" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.website.Food" %>
<%@ page import="com.website.DailyData" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Locale" %>




<%
  List<DailyData> weeklyData = (List<DailyData>) session.getAttribute("weeklyData");
  com.website.Food currentFood = (com.website.Food)request.getAttribute("currentFood");



  if(currentFood==null){
    currentFood=new Food();
    currentFood.setName("Lean beef");
    currentFood.setFat(0);
    currentFood.setCarbs(0);
    currentFood.setProtein(0);}



    String[] days = {"Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};

%>
<%
  com.website.User user = (com.website.User) session.getAttribute("user");

%>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>Fitness Tracker</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.5/dist/css/bootstrap.min.css" rel="stylesheet" />
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.5/dist/js/bootstrap.bundle.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
  <style>

    body {
      position: relative;
      min-height: 100vh;
      background: none;
      z-index: 0;
      overflow-x: hidden;
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

    .offcanvas {
      background: #343a40;
      color: #fff;
    }
    .offcanvas .nav-link {
      color: #fff;
    }
    .offcanvas .nav-link.active,
    .offcanvas .nav-link:hover {
      background: #495057;
      color: #ffc107;
    }
    table {
      border-collapse: collapse;
      width: 100%;
      font-family: Arial, sans-serif;
    }
    th, td {
      border: 1px solid #ccc;
      padding: 10px;
      text-align: center;
    }
    th {
      background-color: #d5ede0;
      font-weight: bold;
    }
    tr:nth-child(even) td {
      background-color: #f9f9f9;
    }
  </style>
</head>
<body>


<form action="profile.jsp"  style="position: absolute; top: 10px; right: 20px; margin: 0;">
  <button type="submit"
          style="background-color: #f8f8f8; padding: 10px 20px; border-radius: 8px; font-weight: bold;
                 box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1); font-family: Arial, sans-serif;
                 border: none; cursor: pointer; display: inline-block;">
    👤 Welcome, <span id="username"><%= user.getFirstName() %></span>
  </button>
</form>




<!-- Sidebar Menu Button -->
<button class="btn btn-dark m-3" type="button" data-bs-toggle="offcanvas" data-bs-target="#sidebar" aria-controls="sidebar">
  ☰ Menu
</button>

<!-- Sidebar Menu -->
<div class="offcanvas offcanvas-start" tabindex="-1" id="sidebar" aria-labelledby="sidebarLabel">
  <div class="offcanvas-header">
    <h5 class="offcanvas-title" id="sidebarLabel">Fitness Menu</h5>
    <button type="button" class="btn-close btn-close-white" data-bs-dismiss="offcanvas" aria-label="Close"></button>
  </div>
  <div class="offcanvas-body">
    <div class="d-grid gap-2">
      <button class="btn btn-primary" onclick="location.href='main.jsp'">Home</button>

      <form action="Calculator.html">
        <input type="submit" class="btn btn-secondary" style="width: 375px ; " value="Calculate">
      </form><form action="Logout">
      <input type="submit" class="btn btn-secondary" style="background-color: red ;width: 375px ;" value="SignOut">
    </form>

    </div>
  </div>
</div>

<!-- Nutrition Form -->
<div class="container mt-4">
  <h2 class="mb-4">Add Nutrition Data</h2>


  <form id="nutritionForm" method="POST" action="DailyNut">
    <div class="row mb-3">
      <div class="col-md-4">
        <label for="proteinInput" class="form-label">Food (100g)</label>
        <input type="text" id="proteinInput" name="foodName" class="form-control" placeholder="Enter Food name" required />
        <input type="hidden" name="email" value="<%= user.getEmail() %>">

      </div>
      <div class="col-md-4">
        <label for="carbsInput" class="form-label">quantity </label>
        <input type="number" id="carbsInput" name="quantity" class="form-control" placeholder="enter Grams" required  min="0" />
        <!-- name="carbs" // Carbs Input -->
      </div>

    </div>
    <button type="submit" class="btn btn-primary">Submit</button>
  </form>



  <!-- Nutrition Summary Section -->
  <h2 class="mt-5 mb-4">Daily Nutrition Summary</h2>
  <div class="row g-4">
    <!-- Macronutrient Breakdown Chart -->
    <div class="col-md-6">
      <div class="card shadow-sm">
        <div class="card-header bg-primary text-white">Macronutrient Breakdown</div>
        <div class="card-body">
          <canvas id="nutritionChart" height="200"></canvas>
        </div>
      </div>
    </div>

    <!-- Calories Per Day Chart -->
    <div class="col-md-6">
      <div class="card shadow-sm">
        <div class="card-header bg-success text-white">Calories Per Day</div>
        <div class="card-body" style="max-height: 650px; overflow-y: auto;">
          <div class="d-flex flex-column gap-2">
            <%

              for (String day : days) {
                int carbs = 0, protein = 0, fat = 0;
                boolean found = false;

                if (weeklyData != null) {
                  for (DailyData data : weeklyData) {
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(data.getDate());
                    String currentDay = cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.ENGLISH);

                    if (currentDay.equalsIgnoreCase(day)) {
                      carbs = data.getCarbs();
                      protein = data.getProtein();
                      fat = data.getFat();
                      found = true;
                      break;
                    }
                  }
                }

                int totalCalories = (carbs * 4) + (protein * 4) + (fat * 9);
            %>

            <div style="background: #f9f9f9; border-left: 5px solid #28a745; border-radius: 6px; padding: 10px 15px;">
              <div style="font-size: 0.9rem; font-weight: 600; color: #444;"><%= day %></div>
              <% if (found) { %>
              <div style="font-size: 0.85rem; color: #555; display: flex; gap: 15px; flex-wrap: wrap; margin-top: 4px;">
                <span>🥖 <%= carbs %>g Carbs</span>
                <span>🍗 <%= protein %>g Protein</span>
                <span>🥑 <%= fat %>g Fats</span>
                <span>🔥 <strong><%= totalCalories %></strong> kcal</span>
              </div>
              <% } else { %>
              <div style="font-size: 0.85rem; color: #999; margin-top: 4px;">No data available</div>
              <% } %>
            </div>

            <%
              }
            %>
          </div>
        </div>
      </div>
    </div>

  </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

<script>
  const carbs = Number('<%= (currentFood.getCarbs()) %>');
  const protein = Number('<%= (currentFood.getProtein()) %>');
  const fat = Number('<%= (currentFood.getFat()) %>');

  console.log('Chart data:', carbs, protein, fat); // <-- Add this

  const chartData = {
    labels: ['Carbs', 'Proteins', 'Fats'],
    datasets: [{
      label: 'Macronutrient Breakdown',
      data: [carbs, protein, fat],
      backgroundColor: ['#36A2EB', '#4BC0C0', '#FF6384'],
      borderWidth: 1
    }]
  };

  const config = {
    type: 'doughnut',
    data: chartData,
    options: {
      responsive: true,
      plugins: {
        legend: {
          position: 'bottom'
        }
      }
    }
  };

  new Chart(document.getElementById('nutritionChart'), config);
</script>



<script>
  document.addEventListener('DOMContentLoaded', () => {
    const nutritionChartCtx = document.getElementById('nutritionChart').getContext('2d');
    const caloriesChartCtx = document.getElementById('caloriesChart').getContext('2d');

    // Fetch Nutrition Data from Backend
    const fetchNutritionData = async () => {
      try {
        const response = await fetch('/api/nutrition-summary'); // Replace with your backend API endpoint
        const data = await response.json();

        // Update Macronutrient Breakdown Chart
        new Chart(nutritionChartCtx, {
          type: 'pie',
          data: {
            labels: ['Protein', 'Carbs', 'Fat'],
            datasets: [{
              data: [data.protein, data.carbs, data.fat],
              backgroundColor: ['#007bff', '#ffc107', '#28a745']
            }]
          }
        });

        // Update Calories Per Day Chart
        new Chart(caloriesChartCtx, {
          type: 'bar',
          data: {
            labels: data.days,
            datasets: [{
              label: 'Calories',
              data: data.calories,
              backgroundColor: '#17a2b8'
            }]
          }
        });
      } catch (error) {
        console.error('Error fetching nutrition data:', error);
      }
    };

    fetchNutritionData();
  });

</script>
</body>
</html>