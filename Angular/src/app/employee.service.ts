import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import {Observable } from 'rxjs';
import { Employee } from './employee';

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {

  private baseUrl= "http://localhost:8080/api/v1/Employees";

  constructor( private httpclinet :HttpClient) {  
  }
  getEmployeeList(): Observable<Employee[]>{
    return this.httpclinet.get<Employee[]>(`${this.baseUrl}`);
  }
  createEmployee(employee:Employee):Observable<Object>{
    return this.httpclinet.post(`${this.baseUrl}`,employee);
  }

  getByID(id:number):Observable<Employee>
  {
    return this.httpclinet.get<Employee>(`${this.baseUrl}/${id}`);
  }

  updateEmployee(id:number,employee:Employee):Observable<Object>
  {
    return this.httpclinet.put(`${this.baseUrl}/${id}`,employee)
  }

  deleteEmployee(id:number):Observable<Object>
  {
    return this.httpclinet.delete(`${this.baseUrl}/${id}`)
  }

}
