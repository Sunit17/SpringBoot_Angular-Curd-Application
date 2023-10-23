package com.maveric.springboot.service;

import com.maveric.springboot.Dto.EmployeeRequestDTO;
import com.maveric.springboot.Dto.EmployeeResponseDTO;
import com.maveric.springboot.exception.ResourceNotFound;
import com.maveric.springboot.model.Employee;
import com.maveric.springboot.repository.EmployeeRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements IEmployeeService{
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private EmployeeRepo employeeRepo;

    @Override
    public List<EmployeeResponseDTO> getAll(int pageNumber,int pageSize) {
        Pageable p= PageRequest.of (pageNumber,pageSize);

        Page<Employee> pageemp=this.employeeRepo.findAll(p);
        List<Employee> emp=pageemp.getContent();

        return emp.stream().map((emp1) -> this.modelMapper.map(emp1,EmployeeResponseDTO.class))
                .collect(Collectors.toList());

    }

    @Override
    public EmployeeRequestDTO createEmployee(EmployeeRequestDTO employeeRequestDTO) {

        Employee emp1= this.modelMapper.map(employeeRequestDTO,Employee.class);
        Employee saveEmp=this.employeeRepo.save(emp1);
        return this.modelMapper.map(saveEmp,EmployeeRequestDTO.class);
    }

    @Override
    public EmployeeRequestDTO updateEmployee(EmployeeRequestDTO employeeRequestDTO, int id) {

        Employee employee=this.employeeRepo.findById(id).
                orElseThrow(() -> new ResourceNotFound("Employee","Id",id));
        employee.setFirst_name(employeeRequestDTO.getFirst_name());
        employee.setLast_name(employeeRequestDTO.getLast_name());
        employee.setMail_id(employeeRequestDTO.getMail_id());

        Employee UpdateEmp=this.employeeRepo.save(employee);

        return this.modelMapper.map(UpdateEmp,EmployeeRequestDTO.class);
    }

    @Override
    public EmployeeRequestDTO getByEmployeeId(int id) {

        Employee emp= this.employeeRepo.findById(id).orElseThrow(()->
                new ResourceNotFound("Employee","Id",id));

        return this.modelMapper.map(emp,EmployeeRequestDTO.class);
    }

    @Override
  public void deleteEmployee(int id) {
        Employee emp1=this.employeeRepo.findById(id).orElseThrow(()->
                new ResourceNotFound("Employee","Id",id));
        this.employeeRepo.delete(emp1);

    }


}
