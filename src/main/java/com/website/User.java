package com.website;

public class User {
  private   String firstName;
   private String lastName;
   private String email;
  private   String password;
 private    String code;
  private   String street;
   private String apartment;
  private   double weight;
 private    double height;
  private   String gender;
private String building;
    public User(String firstName, String lastName, String email, String password,  String street,  double weight, double height, String gender, String building) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;


        this.weight = weight;
        this.height = height;
        this.gender = gender;
        this.building = building;
    }

    public User(String email,String firstName, String lastName, String password,  String street, String apartment,double weight, double height, String gender) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.street = street;
        this.weight = weight;
        this.height = height;
        this.gender = gender;
        this.apartment = apartment;





    }

    public User(String email,String firstName, String lastName, String password,  String street,String Building, String apartment,double weight, double height, String gender) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.street = street;
        this.weight = weight;
        this.height = height;
        this.gender = gender;
        this.apartment = apartment;
this.building = Building;




    }

    public User() {

    }


    public User(String email, String lastName, String firstName, String password) {
        this.email = email;
        this.lastName = lastName;
        this.firstName = firstName;
        this.password = password;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getApartment() {
        return apartment;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", code='" + code + '\'' +
                ", street='" + street + '\'' +
                ", apartment='" + apartment + '\'' +
                ", weight=" + weight +
                ", height=" + height +
                ", gender='" + gender + '\'' +
                ", building='" + building + '\'' +
                '}';
    }
}
