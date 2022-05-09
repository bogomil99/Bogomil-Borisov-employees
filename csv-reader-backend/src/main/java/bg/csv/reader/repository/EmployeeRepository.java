package bg.csv.reader.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import bg.csv.reader.model.tables.Employee;

@Repository
public interface EmployeeRepository<T extends Employee> extends JpaRepository<T, Long> {

	@Query("SELECT em FROM Employee em "
			+ " WHERE em.empId<>:emplId AND em.projectId=:projectId AND em.dateFrom>=:dateFrom AND em.dateTo<=:dateTo")
	List<T> findPayeredEmployes(@Param("emplId") Long emplId, @Param("projectId") Long projectId,
			@Param("dateFrom") LocalDate dateFrom, @Param("dateTo") LocalDate dateTo);

}
