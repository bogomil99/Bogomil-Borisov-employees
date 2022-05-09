package bg.csv.reader.service;

import java.util.List;

import bg.csv.reader.dto.EmployeeAssoc;
import bg.csv.reader.model.tables.Employee;

public interface EmployeeService<T extends Employee> {

	public T save(T entity);
	
	public void delete(T entity);
	
	public T findByEmpId(Long empId);
	
	public List<T> findAll();

	public List<EmployeeAssoc> findPayeredEmployes();
	
}
