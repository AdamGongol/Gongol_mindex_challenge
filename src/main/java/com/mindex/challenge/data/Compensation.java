package com.mindex.challenge.data;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import java.time.LocalDate;


public class Compensation {

    //indexed for quicker traversal
    @Indexed(unique = true)
    private String employeeId;  // Business key

    private Employee employee;
    private Double salary;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate effectiveDate;

    public Compensation() {
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
        this.employeeId = employee != null ? employee.getEmployeeId() : null;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public LocalDate getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(LocalDate effectiveDate) {
        this.effectiveDate = effectiveDate;
    }
}