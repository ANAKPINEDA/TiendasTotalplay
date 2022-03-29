package com.totalshop.sap.commerce.repository;

import com.totalshop.sap.commerce.entity.MktConfigurationServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;


@Repository
public interface MktConfigurationServiceRepository extends JpaRepository<MktConfigurationServiceEntity,String> {

    @Query(value = "SELECT NAME, VALUE, LAST_UPDATE FROM MKT_CONFIGURATION_SERVICE WHERE NAME = 'access_token' AND LAST_UPDATE >= (SYSDATE)",nativeQuery = true)
    abstract MktConfigurationServiceEntity getToken();

    @Query(value = "SELECT NAME, VALUE, LAST_UPDATE FROM MKT_CONFIGURATION_SERVICE WHERE NAME = 'refresh_token'", nativeQuery = true)
    MktConfigurationServiceEntity getRefreshToken();

    @Modifying
    @Transactional
    @Query(value = "UPDATE MKT_CONFIGURATION_SERVICE SET VALUE =:accessToken , LAST_UPDATE =  (SYSDATE + INTERVAL '4' HOUR) WHERE NAME = 'access_token'", nativeQuery = true)
    public int updateAccessToken(@Param("accessToken") String accessToken);

    @Modifying
    @Transactional
    @Query(value = "UPDATE MKT_CONFIGURATION_SERVICE SET VALUE =:refreshToken WHERE NAME = 'refresh_token'", nativeQuery = true)
    public int updateRefreshToken(@Param("refreshToken") String refreshToken);
}
