package com.website;

import java.util.Date;

public class DailyData {

        private Date date;
        private int protein;
        private int carbs;
        private int fat;
private int calories;
        public DailyData(Date date, int protein, int carbs, int fat) {
            this.date = date;
            this.protein = protein;
            this.carbs = carbs;
            this.fat = fat;
        }

        public void calculateCalories(){

            calories =(protein*4+carbs*4+fat*9);
        }

        // Getters
        public Date getDate() { return date; }
        public int getProtein() { return protein; }
        public int getCarbs() { return carbs; }
        public int getFat() { return fat; }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setProtein(int protein) {
        this.protein = protein;
    }

    public void setCarbs(int carbs) {
        this.carbs = carbs;
    }

    public void setFat(int fat) {
        this.fat = fat;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getCalories() { return calories; }
}
