package com.test.userapi.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class User {

    private UUID id;
    private String email;
    private String name;
    private String phone;
    @JsonIgnore
    private String password;
    private String taxId;
    private LocalDateTime createdAt;
    private List<Address> addresses;

}