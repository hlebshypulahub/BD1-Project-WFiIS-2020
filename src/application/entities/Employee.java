package application.entities;

import java.sql.Date;

public class Employee {
    private int id;
    private String role;
    private String firstName;
    private String lastName;
    private Date DOB;
    private String phone;
    private String address;
    private String position;
    private String category;
    private String salary;
    private Date PPE;
    private Date fiveYearStart;
    private Date fiveYearEnd;

    public Employee(Employee other) {
        this.id = other.id;
        this.role = other.role;
        this.firstName = other.firstName;
        this.lastName = other.lastName;
        this.DOB = other.DOB;
        this.phone = other.phone;
        this.address = other.address;
        this.position = other.position;
        this.category = other.category;
        this.salary = other.salary;
        this.PPE = other.PPE;
        this.fiveYearStart = other.fiveYearStart;
        this.fiveYearEnd = other.fiveYearEnd;
        this.courseHoursSum = other.courseHoursSum;
        this.courseDeadline = other.courseDeadline;
    }

    public Employee() {
    }

    private String courseHoursSum;
    private Date courseDeadline;

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

    public Date getDOB() {
        return DOB;
    }

    public void setDOB(Date DOB) {
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

    public Date getPPE() {
        return PPE;
    }

    public void setPPE(Date PPE) {
        this.PPE = PPE;
    }

    public Date getFiveYearStart() {
        return fiveYearStart;
    }

    public void setFiveYearStart(Date fiveYearStart) {
        this.fiveYearStart = fiveYearStart;
    }

    public Date getFiveYearEnd() {
        return fiveYearEnd;
    }

    public void setFiveYearEnd(Date fiveYearEnd) {
        this.fiveYearEnd = fiveYearEnd;
    }

    public String getCourseHoursSum() {
        return courseHoursSum;
    }

    public void setCourseHoursSum(String courseHoursSum) {
        this.courseHoursSum = courseHoursSum;
    }

    public Date getCourseDeadline() {
        return courseDeadline;
    }

    public void setCourseDeadline(Date courseDeadline) {
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
