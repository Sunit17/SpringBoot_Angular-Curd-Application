import { Component, OnInit } from '@angular/core';
import { Employee } from '../employee';
import { EmployeeService } from '../employee.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-update-employee',
  templateUrl: './update-employee.component.html',
  styleUrls: ['./update-employee.component.css']
})
export class UpdateEmployeeComponent implements OnInit {
  id: number | any;
  employee: Employee = new Employee();

  constructor(private service: EmployeeService,
    private route: ActivatedRoute,
    private router:Router) { }

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];
    this.service.getByID(this.id).subscribe(data => {
      this.employee = data;
    }, error => console.log(error));

  }

  onSubmit() {
    this.service.updateEmployee(this.id,this.employee).subscribe(data =>
      {this.gotoEmployeeList()},
      error =>console.log(error));
      
  }

  gotoEmployeeList(){
    this.router.navigate(['/employees'])
  }
}
