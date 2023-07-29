package com.shishir.gateways.mapper;

import com.shishir.gateways.dto.GatewayResponseDto;
import com.shishir.gateways.entity.Gateway;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GatewayMapper {
    GatewayResponseDto gatewayToGatewayResponseDto(Gateway gateway);

    List<GatewayResponseDto> gatewayToGatewayResponseDto(List<Gateway> gateway);
}
