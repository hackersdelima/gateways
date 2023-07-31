package com.shishir.gateways.dto;

import com.shishir.gateways.enums.DeviceStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PeripheralDeviceRequestDto {
    private String vendor;
    private DeviceStatus status = DeviceStatus.ONLINE;
}
