package com.paypal.bfs.test;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import com.paypal.bfs.test.employeeserv.api.model.Address;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.dao.impl.EmployeeDAOImpl;
import com.paypal.bfs.test.employeeserv.impl.EmployeeResourceImpl;

public class EmployeeResourceTest extends EmployeeAbstractTest {

	@Mock
	EmployeeDAOImpl employeeDAOImpl;

	@InjectMocks
	private EmployeeResourceImpl employeeServiceImpl;

	@Override
	@Before
	public void setUp() {
		super.setUp();
	}

	@Test
	public void getEmployee() throws Exception {
		Mockito.when(employeeDAOImpl.getEmpById(Mockito.anyInt())).thenReturn(new Employee());
		ResponseEntity<Employee> resp = employeeServiceImpl.employeeGetById("123");
		Employee emp = resp.getBody();
		assertNotNull(emp);
	}

	@Test
	public void createEmployee() throws Exception {
		Employee employee = new Employee();
		employee.setId(123);
		employee.setFirstName("test");
		employee.setLastName("tset");
		employee.setDateOfBirth("2020-10-10");
		Address addr = new Address();
		addr.setCity("city");
		addr.setState("state");
		addr.setCountry("country");
		addr.setLine1("addr line 1");
		addr.setZipCode(12345);
		employee.setAddress(addr);
		Mockito.when(employeeDAOImpl.createEmployee(Mockito.anyObject())).thenReturn("true");
		ResponseEntity<String> resp = employeeServiceImpl.createEmployee(employee);
		String response = resp.getBody();
		assertNotNull(response);
	}

}