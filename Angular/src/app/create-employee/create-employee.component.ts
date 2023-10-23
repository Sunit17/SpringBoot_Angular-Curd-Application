import { Component } from '@angular/core';
import { Employee } from '../employee';
import { EmployeeService } from '../employee.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-create-employee',
  templateUrl: './create-employee.component.html',
  styleUrls: ['./create-employee.component.css']
})
export class CreateEmployeeComponent {
  employee: Employee = new Employee();
  constructor(private empservice: EmployeeService,
    private route:Router) { }

  saveEmployee() {
    this.empservice.createEmployee(this.employee).subscribe(
      data => {
        console.log(data);
        this.gotoEmployeeList();
      },
      error => {
        console.log(error);
      }
    )
  }
  gotoEmployeeList(){
    this.route.navigate(['/employees'])
  }

  onSubmit() {
    // console.log(this.employee);
    this.saveEmployee();

  };


}
