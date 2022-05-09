package bg.csv.reader.dto;

import java.io.Serializable;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeAssoc implements Serializable {

	private static final long serialVersionUID = 1L;
	
	Long empId1;
	Long empId2;
	Long projectId;
	Integer daysWorked;
}
