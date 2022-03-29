package com.totalshop.sap.commerce.service.impl;

import com.totalshop.sap.commerce.constants.Message;
import com.totalshop.sap.commerce.dto.RefreshToken;
import com.totalshop.sap.commerce.entity.MktConfigurationServiceEntity;
import com.totalshop.sap.commerce.exceptions.ServiceException;
import com.totalshop.sap.commerce.repository.MktConfigurationServiceRepository;
import com.totalshop.sap.commerce.service.TokenCommerceService;
import com.totalshop.sap.commerce.vo.ResponseTokenVo;
import com.totalshop.sap.commerce.dto.ResultOperationMdwDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Service
@Log4j2
public class TokenCommerceServiceImpl implements TokenCommerceService {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    MktConfigurationServiceRepository mktConfigurationServiceRepository;

    @Autowired
    private Environment env;

    @Autowired
    public Message message;

    @Override
    public ResponseTokenVo getTokenCommerce() throws ServiceException {
        //Se puede mejorar los parametros estaticos en el response del Token
        //se pueden mejorar las validaciones en el If con un String.IsNullOrEmpty(value) si existe en Java
            ResponseTokenVo resultToken = new ResponseTokenVo();
            try{
                MktConfigurationServiceEntity token = mktConfigurationServiceRepository.getToken();
                MktConfigurationServiceEntity refreshToken = mktConfigurationServiceRepository.getRefreshToken();

                if(token.getValue() != null && !token.getValue().isEmpty()){
                    resultToken.setResultOperation(new ResultOperationMdwDto("","0",Message.EXITO_100));
                    resultToken.setAccessToken(token.getValue());
                    resultToken.setTokenType("bearer");
                    resultToken.setExpiresIn("0");
                    resultToken.setScope("extended");
                }else if(refreshToken.getValue() != null && !refreshToken.getValue().isEmpty()){
                    return getRefreshToken(refreshToken.getValue());
                }else{
                    return resultToken;
                }
            } catch (Exception e){
                log.error(e);
                throw new ServiceException(e.getMessage());
            }
            return resultToken;
        }

        public ResponseTokenVo getRefreshToken(String value) throws ServiceException {
            ResponseTokenVo resultToken = new ResponseTokenVo();
            String url = env.getProperty("ws.ecommerce.refreshToken") + value;
            HttpHeaders headers = new HttpHeaders();
            try{
                headers.setContentType(MediaType.APPLICATION_JSON);
                headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
                HttpEntity<String> entity = new HttpEntity<String>("", headers);
                ResponseEntity<RefreshToken> resultRefreshToken = restTemplate.exchange(url, HttpMethod.POST, entity, RefreshToken.class);
                if(resultRefreshToken.getStatusCode() == HttpStatus.OK){
                    if(resultRefreshToken.getBody().getAccessToken() != null && !resultRefreshToken.getBody().getAccessToken().isEmpty() && resultRefreshToken.getBody().getRefreshToken() != null && !resultRefreshToken.getBody().getRefreshToken().isEmpty()){
                        int updateAccessToken = mktConfigurationServiceRepository.updateAccessToken(resultRefreshToken.getBody().getAccessToken());
                        int updateRefreshToken = mktConfigurationServiceRepository.updateRefreshToken(resultRefreshToken.getBody().getRefreshToken());
                        if(updateAccessToken>0 && updateRefreshToken>0){
                            resultToken.setResultOperation(new ResultOperationMdwDto("","0",Message.EXITO_100));
                            resultToken.setAccessToken(resultRefreshToken.getBody().getAccessToken());
                            resultToken.setTokenType("bearer");
                            resultToken.setExpiresIn("0");
                            resultToken.setScope("extended");
                        }
                    }else{
                        return resultToken;
                    }
                }
            }catch (Exception e){
                log.error(e);
                throw new ServiceException(e.getMessage());
            }
            return resultToken;
        }

        public ResponseTokenVo resetToken() throws ServiceException {
            ResponseTokenVo resultToken = new ResponseTokenVo();
            String url = env.getProperty("ws.ecommerce.resetToken");
            HttpHeaders headers = new HttpHeaders();
            try{
                headers.setContentType(MediaType.APPLICATION_JSON);
                headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
                HttpEntity<String> entity = new HttpEntity<String>("", headers);
                ResponseEntity<RefreshToken> resultResetToken = restTemplate.exchange(url, HttpMethod.POST, entity, RefreshToken.class);
                if(resultResetToken.getStatusCode() == HttpStatus.OK){
                    if(resultResetToken.getBody().getAccessToken() != null && !resultResetToken.getBody().getAccessToken().isEmpty() && resultResetToken.getBody().getTokenType() != null && !resultResetToken.getBody().getTokenType().isEmpty() && resultResetToken.getBody().getExpiresIn() != null && !resultResetToken.getBody().getExpiresIn().isEmpty()) {
                        int updateAccessToken = mktConfigurationServiceRepository.updateAccessToken(resultResetToken.getBody().getAccessToken());
                        int updateRefreshToken = mktConfigurationServiceRepository.updateRefreshToken(resultResetToken.getBody().getRefreshToken());
                        if(updateAccessToken>0 && updateRefreshToken>0){
                            resultToken.setResultOperation(new ResultOperationMdwDto("","0",Message.EXITO_100));
                            resultToken.setAccessToken(resultResetToken.getBody().getAccessToken());
                            resultToken.setTokenType(resultResetToken.getBody().getTokenType());
                            resultToken.setScope(resultResetToken.getBody().getScope());
                            resultToken.setExpiresIn(resultResetToken.getBody().getExpiresIn());
                        }
                    }else{
                        resultToken.setResultOperation(new ResultOperationMdwDto("","1","Error: No se obtuvo el Token, favor de revisar con el administrador"));
                        resultToken.setAccessToken("");
                        resultToken.setTokenType("");
                        resultToken.setScope("");
                        resultToken.setExpiresIn("");
                    }
                }
            }catch (Exception e){
                log.error(e);
                throw new ServiceException(e.getMessage());
            }
            return resultToken;
        }
    }
