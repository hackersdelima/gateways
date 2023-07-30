package com.shishir.gateways.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;

@Getter
@Setter
public class GatewayDto {
    private Long id;
    private String serialNumber;
    private String name;
    @Pattern(regexp = "^(?:[0-9]{1,3}\\.){3}[0-9]{1,3}$", message = "Invalid IPv4 Address")
    private String ipv4Address;
}
