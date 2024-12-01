package com.mindex.challenge.controller;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.service.CompensationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CompensationController {
    private static final Logger LOG = LoggerFactory.getLogger(CompensationController.class);

    @Autowired
    private CompensationService compensationService;

    /**
     * POST creates a Compensation object that will be associated with a given employee ID
     * @param compensation an input JSON request body to represnt the compensation object
     * @return the compensation object that was just created
     */
    @PostMapping("/compensation")
    public Compensation create(@RequestBody Compensation compensation) {
        LOG.debug("Received compensation create request for employee [{}]", compensation.getEmployeeId());
        return compensationService.create(compensation);
    }

    /**
     * GET returns the compensation object for a given employee id
     * @param id String id that represents the employee you want to retrieve compensation data for
     * @return Compensation Object
     */
    @GetMapping("/compensation/{id}")
    public Compensation read(@PathVariable String id) {
        LOG.debug("Received compensation read request for employee id [{}]", id);
        return compensationService.read(id);
    }
}
