package com.maveric.springboot.service;

import com.maveric.springboot.Dto.EmployeeRequestDTO;
import com.maveric.springboot.Dto.EmployeeResponseDTO;

import java.util.List;

public interface IEmployeeService {

 List<EmployeeResponseDTO> getAll(int pageNumber,int pageSize);

 EmployeeRequestDTO createEmployee (EmployeeRequestDTO employeeRequestDTO);

EmployeeRequestDTO updateEmployee(EmployeeRequestDTO employeeRequestDTO, int id);

EmployeeRequestDTO getByEmployeeId(int id);

void deleteEmployee(int id);

}
