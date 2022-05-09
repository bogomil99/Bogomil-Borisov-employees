import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
	providedIn: 'root'
})
export class UploadService {

	constructor(
		private httpClient: HttpClient,
	) { }

	public uploadFile(file: File) {
		const formData = new FormData();
		formData.append('file', file);
		return this.httpClient.post<any>("http://localhost:8080/uploadFile", formData);
	}

	public getEmployeeAss() {
		try {
			return this.httpClient.get<any>("http://localhost:8080/getPayeredEmployees");
		} catch (e) {
		}
		return null;
	}
}