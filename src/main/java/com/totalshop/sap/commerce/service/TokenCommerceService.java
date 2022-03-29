package com.totalshop.sap.commerce.service;

import com.totalshop.sap.commerce.exceptions.ServiceException;
import com.totalshop.sap.commerce.vo.ResponseTokenVo;

public interface TokenCommerceService {
    public ResponseTokenVo getTokenCommerce() throws ServiceException;
}
