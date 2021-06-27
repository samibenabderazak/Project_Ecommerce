package com.project.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrdersDTO {
    private int id;
    private Double totalprice;
    private String address;
    private String phoneNumber;
    private String nameUser;
    private String orderDate;
    private int status;
    private Integer idUser;
}
