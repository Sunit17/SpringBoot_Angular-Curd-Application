package com.maveric.springboot.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class EmployeeRequestDTO {

    @NotEmpty
    @Size(min = 4,message = "Must be 4 Character")
    private String first_name;
    @NotEmpty
    @Size(min = 4,message = "Must be 4 Character")
    private String last_name;
    @Email(message = "Email is Not Valid ")
    @NotEmpty(message = "Email Can't be Empty")
    private String mail_id;


}
