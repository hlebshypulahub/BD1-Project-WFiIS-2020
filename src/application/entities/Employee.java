package application.entities;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class Employee {
    private int id;
    private String username;
    private String password;
    private String role;
    private String firstName;
    private String lastName;
    private LocalDate DOB;
    private String phone;
    private String address;
    private String position;
    private String category;
    private String salary;
    private LocalDate PPE;
    private LocalDate fiveYearStart;
    private LocalDate fiveYearEnd;
    private String courseHoursSum;
    private LocalDate courseDeadline;

//    private final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

//    public Employee(Employee other) {
//        this.id = other.id;
//        this.role = other.role;
//        this.firstName = other.firstName;
//        this.lastName = other.lastName;
//        this.DOB = other.DOB;
//        this.phone = other.phone;
//        this.address = other.address;
//        this.position = other.position;
//        this.category = other.category;
//        this.salary = other.salary;
//        this.PPE = other.PPE;
//        this.fiveYearStart = other.fiveYearStart;
//        this.fiveYearEnd = other.fiveYearEnd;
//        this.courseHoursSum = other.courseHoursSum;
//        this.courseDeadline = other.courseDeadline;
//    }
//
//    public Employee() {
//    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
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

    public String getDOB() {
        return String.valueOf(DOB);
    }

    public void setDOB(LocalDate DOB) {
        this.DOB = DOB;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getPPE() {
        return String.valueOf(PPE);
    }

    public void setPPE(LocalDate PPE) {
        this.PPE = PPE;
    }

    public LocalDate getFiveYearStart() {
        return fiveYearStart;
    }

    public void setFiveYearStart(LocalDate fiveYearStart) {
        this.fiveYearStart = fiveYearStart;
    }

    public LocalDate getFiveYearEnd() {
        return fiveYearEnd;
    }

    public void setFiveYearEnd(LocalDate fiveYearEnd) {
        this.fiveYearEnd = fiveYearEnd;
    }

    public String getCourseHoursSum() {
        return courseHoursSum;
    }

    public void setCourseHoursSum(String courseHoursSum) {
        this.courseHoursSum = courseHoursSum;
    }

    public LocalDate getCourseDeadline() {
        return courseDeadline;
    }

    public void setCourseDeadline(LocalDate courseDeadline) {
        this.courseDeadline = courseDeadline;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public String getRole() {
        return role;
    }
}
