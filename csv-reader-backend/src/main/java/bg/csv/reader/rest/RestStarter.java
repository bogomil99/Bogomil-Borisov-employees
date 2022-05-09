package bg.csv.reader.rest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;

import bg.csv.reader.dto.EmployeeAssoc;
import bg.csv.reader.model.tables.Employee;
import bg.csv.reader.service.EmployeeService;

@RestController
public class RestStarter {

	@Value("${csv.file.path}")
	private String csvFilePath;

	@Autowired
	private EmployeeService<Employee> employeeService;

	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	public void uploadFile(@RequestBody MultipartFile file) {
		String uploadedFilePath = csvFilePath + file.getOriginalFilename();
		File fileCsv = new File(uploadedFilePath);
		try {
			try (OutputStream os = new FileOutputStream(fileCsv)) {
				os.write(file.getBytes());
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	@RequestMapping(value = "/getPayeredEmployees")
	public String getPayeredEmployees() {
		List<EmployeeAssoc> employeeAssocList = employeeService.findPayeredEmployes();
		Gson gson = new Gson();
		return gson.toJson(employeeAssocList);
	}

}
