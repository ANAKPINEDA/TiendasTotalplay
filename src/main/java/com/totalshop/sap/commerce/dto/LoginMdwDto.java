package com.totalshop.sap.commerce.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginMdwDto {
    @JsonProperty("User")
    public String user;
    @JsonProperty("Password")
    public String password;
    @JsonProperty("Ip")
    public String ip;
}
