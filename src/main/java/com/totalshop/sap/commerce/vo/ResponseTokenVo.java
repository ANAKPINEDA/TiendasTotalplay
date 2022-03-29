package com.totalshop.sap.commerce.vo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.totalshop.sap.commerce.dto.ResponseLoginMdwDto;
import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResponseTokenVo extends ResponseLoginMdwDto {
    @JsonProperty("access_token")
    public String accessToken;
    @JsonProperty("token_type")
    public String tokenType;
    @JsonProperty("expires_in")
    public String expiresIn;
    @JsonProperty("scope")
    public String scope;


}
