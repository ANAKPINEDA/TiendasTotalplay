package com.totalshop.sap.commerce.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import lombok.Getter;

@Entity
@Getter
@Table(name = "MKT_CONFIGURATION_SERVICE")
public class MktConfigurationServiceEntity {
    @Id
    @Column(name = "NAME")
    private String name;
    @Column(name = "VALUE")
    private String value;
    @Column(name = "LAST_UPDATE")
    private Date lastUpdate;
}
