package com.shishir.gateways.mapper;

import com.shishir.gateways.dto.PeripheralDeviceDto;
import com.shishir.gateways.entity.PeripheralDevice;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PeripheralDeviceMapper {
    List<PeripheralDeviceDto> toPeripheralDeviceDto(List<PeripheralDevice> peripheralDevices);

}
