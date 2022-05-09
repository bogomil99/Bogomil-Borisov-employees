package bg.csv.reader.model.tables;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Entity
@Table(name = "EMPLOYEE")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Employee implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	@Column(name = "EmpID")
	Long empId;

	@Column(name = "ProjectID")
	Long projectId;
	
	@Column(name = "DateFrom")
	LocalDate dateFrom;
	
	@Column(name = "DateTo")
	LocalDate dateTo;
}
