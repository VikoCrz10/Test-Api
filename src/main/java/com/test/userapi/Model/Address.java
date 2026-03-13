package com.test.userapi.Model;

import lombok.Data;

@Data
public class Address {

    private Long id;
    private String name;
    private String street;
    private String countryCode;

}