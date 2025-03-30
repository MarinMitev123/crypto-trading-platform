package com.crypto.trading.model;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class User {

    private Long id;
    private String username;
    private String password;
    private String email;
}
