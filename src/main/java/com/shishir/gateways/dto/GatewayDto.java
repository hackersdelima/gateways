package com.shishir.gateways.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GatewayDto {
    private Long id;
    private String serialNumber;
    private String name;
    private String ipv4Address;
}
