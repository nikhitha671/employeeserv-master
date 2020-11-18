package com.paypal.bfs.test.employeeserv.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.paypal.bfs.test.employeeserv.api.model.Address;
import com.paypal.bfs.test.employeeserv.api.model.Employee;

@Repository
public class EmployeeDAOImpl {

	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;

	public Employee getEmpById(Integer id) {
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("id", id);
			return jdbcTemplate.queryForObject("select * from employee e join address a on e.id=a.id where e.id=:id",
					params, new EmployeeMapper());
		} catch (DataAccessException e) {
			return null;
		}
	}

	class EmployeeMapper implements RowMapper<Employee> {

		@Override
		public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
			Employee emp = new Employee();
			emp.setId(rs.getInt("id"));
			emp.setFirstName(rs.getString("first_name"));
			emp.setLastName(rs.getString("last_name"));
			emp.setDateOfBirth(rs.getDate("date_of_birth"));
			Address address = new Address();
			address.setCity(rs.getString("city"));
			address.setCountry(rs.getString("country"));
			address.setLine1(rs.getString("line1"));
			address.setLine2(rs.getString("line2"));
			address.setState(rs.getString("state"));
			address.setZipCode(rs.getLong("zip_code"));
			emp.setAddress(address);

			return emp;
		}

	}

	@Transactional
	public String createEmployee(Employee employee) {
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("id", employee.getId());
			params.addValue("first_name", employee.getFirstName());
			params.addValue("last_name", employee.getLastName());
			params.addValue("date_of_birth", employee.getDateOfBirth());
			Address address = employee.getAddress();
			if (address != null) {
				params.addValue("line1", address.getLine1());
				params.addValue("line2", address.getLine2());
				params.addValue("city", address.getCity());
				params.addValue("state", address.getState());
				params.addValue("country", address.getCountry());
				params.addValue("zip_code", address.getZipCode());
				params.addValue("id", employee.getId());
			}

			// insert to employee
			jdbcTemplate.update(
					"insert into employee(id,first_name,last_name,date_of_birth) values(:id,:first_name,:last_name,:date_of_birth)",
					params);
			// insert to address
			jdbcTemplate.update(
					"insert into address(line1,line2,city,state,country,zip_code,id) values(:line1,:line2,:city,:state,:country,:zip_code,:id)",
					params);
			return "true";
		} catch (DataAccessException e) {
			e.printStackTrace();
			return e.getMessage();
		}

	}

}
