package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CompensationServiceImplTest {

    @Mock
    private CompensationRepository compensationRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private CompensationServiceImpl compensationService;

    private Employee testEmployee;
    private Compensation testCompensation;

    @Before
    public void setup() {
        // Setup test employee
        testEmployee = new Employee();
        testEmployee.setEmployeeId("test-id");
        testEmployee.setFirstName("John");
        testEmployee.setLastName("Doe");
        testEmployee.setPosition("Developer");
        testEmployee.setDepartment("Engineering");

        // Setup test compensation
        testCompensation = new Compensation();
        testCompensation.setEmployeeId(testEmployee.getEmployeeId());
        testCompensation.setEmployee(testEmployee);
        testCompensation.setSalary(100000.00);
        testCompensation.setEffectiveDate(LocalDate.now());
    }

    @Test
    public void testCreateCompensation_Success() {
        // Setup
        when(employeeRepository.findByEmployeeId(testEmployee.getEmployeeId())).thenReturn(testEmployee);
        when(compensationRepository.save(any(Compensation.class))).thenReturn(testCompensation);

        // Execute
        Compensation result = compensationService.create(testCompensation);

        // Assert
        assertNotNull(result);
        assertEquals(testCompensation.getEmployeeId(), result.getEmployeeId());
        assertEquals(testCompensation.getSalary(), result.getSalary(), 0.01);
        assertEquals(testCompensation.getEffectiveDate(), result.getEffectiveDate());
        assertNotNull(result.getEmployee());
    }

    @Test(expected = RuntimeException.class)
    public void testCreateCompensation_EmployeeNotFound() {
        // Setup
        when(employeeRepository.findByEmployeeId(any(String.class))).thenReturn(null);

        // Execute
        compensationService.create(testCompensation);
    }

    @Test
    public void testReadCompensation_Success() {
        // Setup
        when(compensationRepository.findByEmployeeId(testEmployee.getEmployeeId())).thenReturn(testCompensation);

        // Execute
        Compensation result = compensationService.read(testEmployee.getEmployeeId());

        // Assert
        assertNotNull(result);
        assertEquals(testCompensation.getEmployeeId(), result.getEmployeeId());
        assertEquals(testCompensation.getSalary(), result.getSalary(), 0.01);
        assertEquals(testCompensation.getEffectiveDate(), result.getEffectiveDate());
    }

    @Test(expected = RuntimeException.class)
    public void testReadCompensation_NotFound() {
        // Setup
        when(compensationRepository.findByEmployeeId(any(String.class))).thenReturn(null);

        // Execute
        compensationService.read("invalid-id");
    }
}


