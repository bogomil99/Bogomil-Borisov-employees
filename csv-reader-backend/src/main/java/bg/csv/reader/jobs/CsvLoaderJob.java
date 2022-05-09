package bg.csv.reader.jobs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import bg.csv.reader.model.tables.Employee;
import bg.csv.reader.service.EmployeeService;

@Component
public class CsvLoaderJob {

	@Value("${csv.file.path}")
	private String csvFilePath;
	private final static String COMMA_DELIMITER = ",";

	@Autowired
	EmployeeService<Employee> employeeService;

	@PostConstruct
	public void initJob() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
		executorService.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				try {
					File directoryPath = new File(csvFilePath);
					File filesList[] = directoryPath.listFiles();
					for (File file : filesList) {
						List<List<String>> records = new ArrayList<>();
						try (BufferedReader br = new BufferedReader(new FileReader(file.getAbsolutePath()))) {
							String line;
							while ((line = br.readLine()) != null) {
								String[] values = line.split(COMMA_DELIMITER);
								records.add(Arrays.asList(values));
							}
						}
						if (records.size() > 1) {
							for (int i = 1; i < records.size(); i++) {
								List<String> rowAsList = records.get(i);
								Employee employee = new Employee();
								employee.setEmpId(Long.valueOf(rowAsList.get(0)));
								employee.setProjectId(Long.valueOf(rowAsList.get(1)));
								LocalDate dateFrom = LocalDate.parse(rowAsList.get(2), formatter);
								employee.setDateFrom(dateFrom);
								LocalDate dateTo = null;
								if (rowAsList.get(3).isEmpty() || rowAsList.get(3).toLowerCase().equals("null")) {
									dateTo = LocalDate.now();
								} else {
									dateTo = LocalDate.parse(rowAsList.get(3), formatter);
								}
								employee.setDateTo(dateTo);
								employeeService.save(employee);
							}
						}
						Files.move(Paths.get(file.getAbsolutePath()),
								Paths.get("./src/main/resources/readed-csv/" + file.getName()),
								StandardCopyOption.REPLACE_EXISTING);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, 10000, 3000, TimeUnit.MILLISECONDS);
	}

}
