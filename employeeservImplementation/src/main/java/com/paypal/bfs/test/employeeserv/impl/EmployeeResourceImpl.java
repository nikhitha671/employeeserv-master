package com.paypal.bfs.test.employeeserv.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.paypal.bfs.test.employeeserv.api.EmployeeResource;
import com.paypal.bfs.test.employeeserv.api.model.Address;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.dao.impl.EmployeeDAOImpl;

/**
 * Implementation class for employee resource.
 */
@RestController
public class EmployeeResourceImpl implements EmployeeResource {

	@Autowired
	EmployeeDAOImpl employeeDAOImpl;

	@Override
	public ResponseEntity<Employee> employeeGetById(String id) {

		try {
			Employee employee = employeeDAOImpl.getEmpById(Integer.valueOf(id));
			if (employee == null) {
				return new ResponseEntity<>(employee, HttpStatus.NOT_FOUND);
			} else {
				return new ResponseEntity<>(employee, HttpStatus.OK);
			}
		} catch (NumberFormatException e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<String> createEmployee(Employee employee) {
		try {
			Address addr = employee.getAddress();
			if (employee.getFirstName() == null || employee.getLastName() == null || addr == null
					|| addr.getLine1() == null || addr.getCity() == null || addr.getState() == null
					|| addr.getCountry() == null || addr.getZipCode() == null) {
				return new ResponseEntity<>("Required fields are missing ", HttpStatus.PRECONDITION_FAILED);
			}
			String res = employeeDAOImpl.createEmployee(employee);
			if (res.equals("true")) {
				return new ResponseEntity<>("Employee created successfully...!", HttpStatus.OK);
			} else {
				return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("Failed to process request", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
