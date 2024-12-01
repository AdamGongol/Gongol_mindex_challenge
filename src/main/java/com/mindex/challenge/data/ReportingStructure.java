package com.mindex.challenge.data;

public class ReportingStructure {

    private Employee employee;
    private long numberOfReports;


    public ReportingStructure(Employee employee, int numberOfReports) {
        this.employee = employee;
        this.numberOfReports = numberOfReports;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public long getNumberOfReports() {
        return numberOfReports;
    }

    public void setNumberOfReports(Integer numberOfReports) {
        this.numberOfReports = numberOfReports;
    }

}



