package com.totalshop.sap.commerce.controller;

import com.totalshop.sap.commerce.exceptions.ServiceException;
import com.totalshop.sap.commerce.service.TokenCommerceService;
import com.totalshop.sap.commerce.dto.RequestLoginMdwDto;
import com.totalshop.sap.commerce.vo.ResponseTokenVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/TiendasTotalplay")
public class TokenCommerceController {

    @Autowired
    TokenCommerceService tokenCommerceService;

    @PostMapping(value = "/Commerce/getToken")
    public ResponseTokenVo getToken(@RequestBody RequestLoginMdwDto request) throws ServiceException {
        //Falta validar Request
        //Falta validar el Login de mdw puede ser un user/pass estatico si despues se agrega a DB solo se registra
        return tokenCommerceService.getTokenCommerce();
    }
}
