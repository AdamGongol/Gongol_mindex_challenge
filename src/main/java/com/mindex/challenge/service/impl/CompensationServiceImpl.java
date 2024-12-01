package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.CompensationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CompensationServiceImpl implements CompensationService {
    private static final Logger LOG = LoggerFactory.getLogger(CompensationServiceImpl.class);

    @Autowired
    private CompensationRepository compensationRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    /**
     * Similar enhancements that were done to create and read in EmployeeServiceImpl
     * could be done to the compensation create and read requirements indicate the
     * use case is a single employee at this time
     */

    @Override
    public Compensation create(Compensation compensation) {
        LOG.debug("Creating compensation for employee [{}]", compensation.getEmployeeId());

        //find the employee based on ID
        Employee employee = employeeRepository.findByEmployeeId(compensation.getEmployeeId());
        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + compensation.getEmployeeId());
        }

        //set the Employee field on compensation object so that salary is now associated with given employee
        compensation.setEmployee(employee);
        compensationRepository.save(compensation);
        return compensation;
    }

    @Override
    public Compensation read(String id) {
        LOG.debug("Reading compensation for employee with id [{}]", id);

        Compensation compensation = compensationRepository.findByEmployeeId(id);
        if (compensation == null) {
            throw new RuntimeException("No compensation found for employeeId: " + id);
        }

        return compensation;
    }
}
