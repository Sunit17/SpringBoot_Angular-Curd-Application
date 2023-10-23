package com.maveric.springboot.controller;

import com.maveric.springboot.Dto.ApiResponse;
import com.maveric.springboot.Dto.EmployeeRequestDTO;
import com.maveric.springboot.Dto.EmployeeResponseDTO;
import com.maveric.springboot.service.EmployeeServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1/")
public class EmployeeController {
    @Autowired
    private EmployeeServiceImpl employeeService;

    //Get All Employees
    @GetMapping("/Employees")
    public ResponseEntity<List<EmployeeResponseDTO>>getAllEmployees(
            @RequestParam(value = "pageNumber",defaultValue = "0",required = false) int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "6",required = false) int pageSize

    ) {

        List<EmployeeResponseDTO> dto = employeeService.getAll(pageNumber,pageSize);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/Employees")
    public ResponseEntity<EmployeeRequestDTO> createEmployee(@Valid @RequestBody EmployeeRequestDTO requestDTO)
    {
        EmployeeRequestDTO emp= this.employeeService.createEmployee(requestDTO);
        return new ResponseEntity<EmployeeRequestDTO>(emp, HttpStatus.CREATED);

    }

    @GetMapping("/Employees/{id}")
    public ResponseEntity<EmployeeRequestDTO> getByEmpId(@PathVariable int id) {
        EmployeeRequestDTO emp=this.employeeService.getByEmployeeId(id);
        return new ResponseEntity<EmployeeRequestDTO>(emp,HttpStatus.OK);
    }

    @PutMapping("/Employees/{id}")
    public ResponseEntity<EmployeeRequestDTO> updated(@Valid @RequestBody EmployeeRequestDTO emp,@PathVariable int id) {

        EmployeeRequestDTO empdto=this.employeeService.updateEmployee(emp,id);
        return new ResponseEntity<EmployeeRequestDTO>(empdto,HttpStatus.OK);
    }

    @DeleteMapping("/Employees/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable int id)
    {
        this.employeeService.deleteEmployee(id);
        return new ResponseEntity<ApiResponse>((new ApiResponse("Successfully Deleted",true)),HttpStatus.OK);
    }


}
