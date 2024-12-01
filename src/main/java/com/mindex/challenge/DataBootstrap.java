//package com.mindex.challenge;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.mindex.challenge.dao.CompensationRepository;
//import com.mindex.challenge.dao.EmployeeRepository;
//import com.mindex.challenge.data.Compensation;
//import com.mindex.challenge.data.Employee;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import jakarta.annotation.PostConstruct;
//import java.io.IOException;
//import java.io.InputStream;
//
////TODO
//// maybe revert this file back to default depending on response
//
//
//@Component
//public class DataBootstrap {
//    private static final String EMPLOYEE_DATASTORE_PATH = "/static/employee_database.json";
//
//    @Autowired
//    private EmployeeRepository employeeRepository;
//
//    @Autowired
//    private CompensationRepository compensationRepository;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @PostConstruct
//    public void init() {
//        try {
//            // Bootstrap employee data
//            InputStream employeeInputStream = this.getClass().getResourceAsStream(EMPLOYEE_DATASTORE_PATH);
//            Employee[] employees = objectMapper.readValue(employeeInputStream, Employee[].class);
//
//            for (Employee employee : employees) {
//                employeeRepository.insert(employee);
//            }
//
//            // Bootstrap compensation data
//            InputStream compensationInputStream = this.getClass().getResourceAsStream(COMPENSATION_DATASTORE_PATH);
//            Compensation[] compensations = objectMapper.readValue(compensationInputStream, Compensation[].class);
//
//            for (Compensation compensation : compensations) {
//                // Find and set the associated employee
//                Employee employee = employeeRepository.findByEmployeeId(compensation.getEmployeeId());
//                if (employee != null) {
//                    compensation.setEmployee(employee);
//                    compensationRepository.insert(compensation);
//                }
//            }
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//}

package com.mindex.challenge;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
public class DataBootstrap {
    private static final String DATASTORE_LOCATION = "/static/employee_database.json";

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @PostConstruct
    public void init() {
        InputStream inputStream = this.getClass().getResourceAsStream(DATASTORE_LOCATION);

        Employee[] employees = null;

        try {
            employees = objectMapper.readValue(inputStream, Employee[].class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (Employee employee : employees) {
            employeeRepository.insert(employee);
        }
    }
}
