package com.servicio.dominio.dto;


import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ObjectRequestDTO {
    private String name;
    private String lastName;
    private String statusReponse;
}
