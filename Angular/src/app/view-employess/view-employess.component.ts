import { Component, OnInit } from '@angular/core';
import { Employee } from '../employee';
import { ActivatedRoute } from '@angular/router';
import { EmployeeService } from '../employee.service';

@Component({
  selector: 'app-view-employess',
  templateUrl: './view-employess.component.html',
  styleUrls: ['./view-employess.component.css']
})
export class ViewEmployessComponent implements OnInit{

  id:number |any
  employee:Employee | undefined

  constructor(private activateRoue:ActivatedRoute, private service:EmployeeService){}

  ngOnInit(): void {
      this.id=this.activateRoue.snapshot.params['id'];
      this.employee=new Employee();
      this.service.getByID(this.id).subscribe(data=>
        this.employee=data)
  }


}
