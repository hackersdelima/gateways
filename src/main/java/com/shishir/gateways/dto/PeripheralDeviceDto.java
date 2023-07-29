package com.shishir.gateways.dto;

import com.shishir.gateways.enums.DeviceStatus;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Date;

@Getter
@Setter
public class PeripheralDeviceDto {
    private Long uid;
    private String vendor;
    private Date createdDate;
    private DeviceStatus status;
}
