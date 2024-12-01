package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger LOG = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Employee create(Employee employee) {
        LOG.debug("Creating employee [{}]", employee);

        employee.setEmployeeId(UUID.randomUUID().toString());

        /**
         * enhanced the basic create functionality to now include
         * creating direct reports if provided with main employee
         */

        if(employee.getDirectReports() != null) {
            List<Employee> directReports = new ArrayList<>();
            if (!employee.getDirectReports().isEmpty()) {
                for (Employee report : employee.getDirectReports()) {
                    Employee directReport = create(report);
                    directReports.add(directReport);
                }
                employee.setDirectReports(directReports);
            }
        }
        employeeRepository.insert(employee);

        return employee;
    }

    @Override
    public Employee read(String id) {
        LOG.debug("Creating employee with id [{}]", id);

        Employee employee = employeeRepository.findByEmployeeId(id);

        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }
        /**
         * enhanced the basic read functionality to now include
         * the other data associated with direct reports rather
         * than just their ID and null fields
         * this supports the getReportingStructure() service as well
         * there may be security reasons to consider if this change
         * would be needed/necessary regarding PI
         */
        if(employee.getDirectReports() != null) {
            List<Employee> directReports = new ArrayList<>();
            if (!employee.getDirectReports().isEmpty()) {
                for (Employee report : employee.getDirectReports()) {
                    String reportId = report.getEmployeeId();
                    Employee directReport = read(reportId);
                    directReports.add(directReport);
                }
                employee.setDirectReports(directReports);
            }
        }
        return employee;
    }

    @Override
    public Employee update(Employee employee) {
        LOG.debug("Updating employee [{}]", employee);

        return employeeRepository.save(employee);
    }



    @Override
    public ReportingStructure getReportingStructure(String id) {
        LOG.debug("Calculating reporting structure for employee with id [{}]", id);

        Employee employee = read(id);

        //utilize a private helper method to do the actual calculation to help with maintainability/complexity
        //additionally, we don't need to verify the validity of employee data since that check is done in read()
        int numberOfReports = calculateNumberOfReports(employee);

        return new ReportingStructure(employee, numberOfReports);
    }

    private int calculateNumberOfReports(Employee employee) {

        if (employee.getDirectReports() == null || employee.getDirectReports().isEmpty()) {
            return 0;
        }

        //grab the direct reports first and set the running total to that first
        int totalReports = employee.getDirectReports().size();

        //recursively call the helper method to continue adding to the total if a direct report has their own reports ...
        for (Employee directReport : employee.getDirectReports()) {
            Employee fullDirectReport = read(directReport.getEmployeeId());
            totalReports += calculateNumberOfReports(fullDirectReport);
        }
        return totalReports;
    }
}

