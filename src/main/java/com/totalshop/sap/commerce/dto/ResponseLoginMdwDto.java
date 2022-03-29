package com.totalshop.sap.commerce.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseLoginMdwDto {
    @JsonProperty("Response")
    public ResultOperationMdwDto resultOperation;

}
