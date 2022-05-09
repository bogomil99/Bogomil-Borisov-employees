import { UploadService } from './upload.service';
import { Component } from '@angular/core';


export interface EmployeeAssoc {
	empId1: number;
	empId2: number;
	projectId: number;
	daysWorked: number;
}

var ELEMENT_DATA: EmployeeAssoc[] = [];


@Component({
	selector: 'app-root',
	templateUrl: './app.component.html',
	styleUrls: ['./app.component.css']
})
export class AppComponent {
	title = 'Pair of employees who have worked together';
	displayedColumns: string[] = ['empId1', 'empId2', 'projectId', 'daysWorked'];
	dataSource = ELEMENT_DATA;
	file = null;
	constructor(
		private uploadService: UploadService
	) {
		this.setDelay();
	}

	onFilechange(event: any) {
		this.file = event.target.files[0]
		if (this.file) {
			console.log('test: ' + this.file)
			this.uploadService.uploadFile(this.file).subscribe(resp => {
				alert("Uploaded")
			})
		} else {
			alert("Please select a file first")
		}
	}

	loadItemsInTable() {
		this.uploadService.getEmployeeAss()!.subscribe(resp => {
			console.log(ELEMENT_DATA);
			console.log(resp);
			if (resp != null && resp.length > 0) {
				if(ELEMENT_DATA.length > 0 && ELEMENT_DATA.every((val, index) => val === resp[index])) {
					return;
				}
				ELEMENT_DATA = resp;
				this.dataSource = ELEMENT_DATA;
			}
		});
	}

	setDelay() {
		setInterval(() => {
			this.loadItemsInTable();
		}, 3000);
	}


}
