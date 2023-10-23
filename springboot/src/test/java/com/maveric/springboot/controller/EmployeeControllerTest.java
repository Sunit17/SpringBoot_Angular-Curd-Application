package com.maveric.springboot.controller;

import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maveric.springboot.exception.ResourceNotFound;
import com.maveric.springboot.model.Employee;
import com.maveric.springboot.repository.EmployeeRepo;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {EmployeeController.class})
@ExtendWith(SpringExtension.class)
class EmployeeControllerTest {
    @Autowired
    private EmployeeController employeeController;

    @MockBean
    private EmployeeRepo employeeRepo;

    /**
     * Method under test: {@link EmployeeController#createEmp(Employee)}
     */
    @Test
    void testCreateEmp() throws Exception {
        Employee employee = new Employee();
        employee.setFirst_name("Jane");
        employee.setId(1);
        employee.setLast_name("Doe");
        employee.setMail_id("Mail id");
        when(employeeRepo.save(Mockito.<Employee>any())).thenReturn(employee);

        Employee employee2 = new Employee();
        employee2.setFirst_name("Jane");
        employee2.setId(1);
        employee2.setLast_name("Doe");
        employee2.setMail_id("Mail id");
        String content = (new ObjectMapper()).writeValueAsString(employee2);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/Employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(employeeController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"id\":1,\"first_name\":\"Jane\",\"last_name\":\"Doe\",\"mail_id\":\"Mail id\"}"));
    }

    /**
     * Method under test: {@link EmployeeController#createEmp(Employee)}
     */
    @Test
    void testCreateEmp2() throws Exception {
        when(employeeRepo.save(Mockito.<Employee>any())).thenThrow(new ResourceNotFound("Not all who wander are lost"));

        Employee employee = new Employee();
        employee.setFirst_name("Jane");
        employee.setId(1);
        employee.setLast_name("Doe");
        employee.setMail_id("Mail id");
        String content = (new ObjectMapper()).writeValueAsString(employee);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/Employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(employeeController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * Method under test: {@link EmployeeController#getAllEmployees()}
     */
    @Test
    void testGetAllEmployees() throws Exception {
        when(employeeRepo.findAll()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/Employees");
        MockMvcBuilders.standaloneSetup(employeeController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link EmployeeController#getAllEmployees()}
     */
    @Test
    void testGetAllEmployees2() throws Exception {
        when(employeeRepo.findAll()).thenThrow(new ResourceNotFound("Not all who wander are lost"));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/Employees");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(employeeController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * Method under test: {@link EmployeeController#getByEmpId(int)}
     */
    @Test
    void testGetByEmpId() throws Exception {
        Employee employee = new Employee();
        employee.setFirst_name("Jane");
        employee.setId(1);
        employee.setLast_name("Doe");
        employee.setMail_id("Mail id");
        Optional<Employee> ofResult = Optional.of(employee);
        when(employeeRepo.findById(Mockito.<Integer>any())).thenReturn(ofResult);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/Employees/{id}", 1);
        MockMvcBuilders.standaloneSetup(employeeController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"id\":1,\"first_name\":\"Jane\",\"last_name\":\"Doe\",\"mail_id\":\"Mail id\"}"));
    }

    /**
     * Method under test: {@link EmployeeController#getByEmpId(int)}
     */
    @Test
    void testGetByEmpId2() throws Exception {
        Optional<Employee> emptyResult = Optional.empty();
        when(employeeRepo.findById(Mockito.<Integer>any())).thenReturn(emptyResult);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/Employees/{id}", 1);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(employeeController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * Method under test: {@link EmployeeController#getByEmpId(int)}
     */
    @Test
    void testGetByEmpId3() throws Exception {
        when(employeeRepo.findById(Mockito.<Integer>any()))
                .thenThrow(new ResourceNotFound("Not all who wander are lost"));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/Employees/{id}", 1);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(employeeController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * Method under test: {@link EmployeeController#updated(int, Employee)}
     */
    @Test
    void testUpdated() throws Exception {
        Employee employee = new Employee();
        employee.setFirst_name("Jane");
        employee.setId(1);
        employee.setLast_name("Doe");
        employee.setMail_id("Mail id");
        Optional<Employee> ofResult = Optional.of(employee);

        Employee employee2 = new Employee();
        employee2.setFirst_name("Jane");
        employee2.setId(1);
        employee2.setLast_name("Doe");
        employee2.setMail_id("Mail id");
        when(employeeRepo.save(Mockito.<Employee>any())).thenReturn(employee2);
        when(employeeRepo.findById(Mockito.<Integer>any())).thenReturn(ofResult);

        Employee employee3 = new Employee();
        employee3.setFirst_name("Jane");
        employee3.setId(1);
        employee3.setLast_name("Doe");
        employee3.setMail_id("Mail id");
        String content = (new ObjectMapper()).writeValueAsString(employee3);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/v1/Employees/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(employeeController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"id\":1,\"first_name\":\"Jane\",\"last_name\":\"Doe\",\"mail_id\":\"Mail id\"}"));
    }

    /**
     * Method under test: {@link EmployeeController#updated(int, Employee)}
     */
    @Test
    void testUpdated2() throws Exception {
        Employee employee = new Employee();
        employee.setFirst_name("Jane");
        employee.setId(1);
        employee.setLast_name("Doe");
        employee.setMail_id("Mail id");
        Optional<Employee> ofResult = Optional.of(employee);
        when(employeeRepo.save(Mockito.<Employee>any())).thenThrow(new ResourceNotFound("Not all who wander are lost"));
        when(employeeRepo.findById(Mockito.<Integer>any())).thenReturn(ofResult);

        Employee employee2 = new Employee();
        employee2.setFirst_name("Jane");
        employee2.setId(1);
        employee2.setLast_name("Doe");
        employee2.setMail_id("Mail id");
        String content = (new ObjectMapper()).writeValueAsString(employee2);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/v1/Employees/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(employeeController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * Method under test: {@link EmployeeController#updated(int, Employee)}
     */
    @Test
    void testUpdated3() throws Exception {
        Employee employee = new Employee();
        employee.setFirst_name("Jane");
        employee.setId(1);
        employee.setLast_name("Doe");
        employee.setMail_id("Mail id");
        when(employeeRepo.save(Mockito.<Employee>any())).thenReturn(employee);
        Optional<Employee> emptyResult = Optional.empty();
        when(employeeRepo.findById(Mockito.<Integer>any())).thenReturn(emptyResult);

        Employee employee2 = new Employee();
        employee2.setFirst_name("Jane");
        employee2.setId(1);
        employee2.setLast_name("Doe");
        employee2.setMail_id("Mail id");
        String content = (new ObjectMapper()).writeValueAsString(employee2);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/v1/Employees/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(employeeController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}

