package com.shishir.gateways.dto;

import com.shishir.gateways.enums.DeviceStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class PeripheralDeviceRequestDto {
    private Long uid;
    private String vendor;
    private DeviceStatus status = DeviceStatus.ONLINE;
}
