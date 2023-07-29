package com.shishir.gateways.mapper;

import com.shishir.gateways.dto.GatewayDto;
import com.shishir.gateways.entity.Gateway;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GatewayMapper {
    GatewayDto toGatewayDto(Gateway gateway);

    List<GatewayDto> toGatewayDto(List<Gateway> gateway);

    Gateway toGateway(GatewayDto gatewayDto);
}
