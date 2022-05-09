package bg.csv.reader.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bg.csv.reader.dto.EmployeeAssoc;
import bg.csv.reader.model.tables.Employee;
import bg.csv.reader.repository.EmployeeRepository;
import bg.csv.reader.service.EmployeeService;

@Service
public class EmployeeServiceImpl<T extends Employee> implements EmployeeService<T> {

	@Autowired
	EmployeeRepository<T> employeeRepository;

	@Override
	public T save(T entity) {
		return employeeRepository.save(entity);
	}

	@Override
	public void delete(T entity) {
		employeeRepository.delete(entity);
	}

	@Override
	public T findByEmpId(Long empId) {
		return employeeRepository.findById(empId).orElseThrow(NoSuchElementException::new);
	}

	@Override
	public List<T> findAll() {
		return employeeRepository.findAll();
	}

	@Override
	public List<EmployeeAssoc> findPayeredEmployes() {
		List<EmployeeAssoc> employeeAssocList = new ArrayList<>();
		List<T> employeeList = findAll();
		for (Employee employee : employeeList) {
			for (T payeredEmployee : employeeList) {
				if (employee.getDateFrom().compareTo(payeredEmployee.getDateTo()) <= 0
						&& employee.getDateTo().compareTo(payeredEmployee.getDateFrom()) >= 0
						&& employee.getProjectId().equals(payeredEmployee.getProjectId())
						&& !employee.getEmpId().equals(payeredEmployee.getEmpId())) {
					LocalDate dateFrom = employee.getDateFrom();
					if (employee.getDateFrom().compareTo(payeredEmployee.getDateFrom()) < 0) {
						dateFrom = payeredEmployee.getDateFrom();
					}
					LocalDate dateTo = employee.getDateTo();
					if (employee.getDateTo().compareTo(payeredEmployee.getDateTo()) > 0) {
						dateTo = payeredEmployee.getDateTo();
					}
					Long result = dateTo.toEpochDay() - dateFrom.toEpochDay();

					EmployeeAssoc employeeAssoc = new EmployeeAssoc(payeredEmployee.getEmpId(), employee.getEmpId(),
							employee.getProjectId(), result.intValue());
					if (employeeAssocList.contains(employeeAssoc)) {
						continue;
					}
					employeeAssoc.setEmpId1(employee.getEmpId());
					employeeAssoc.setEmpId2(payeredEmployee.getEmpId());

					employeeAssocList.add(employeeAssoc);
				}
			}
		}

		return employeeAssocList;
	}

}
