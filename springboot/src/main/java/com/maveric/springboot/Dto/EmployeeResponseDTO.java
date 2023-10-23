package com.maveric.springboot.Dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class EmployeeResponseDTO {

    private int id;
    private String first_name;
    private String last_name;
    private String mail_id;
}
