package com.shishir.gateways.mapper;

import com.shishir.gateways.dto.PeripheralDeviceDto;
import com.shishir.gateways.entity.PeripheralDevice;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PeripheralDeviceMapper {
    PeripheralDevice toPeripheralDeviceEntity(PeripheralDeviceDto peripheralDeviceDto);
    PeripheralDeviceDto toPeripheralDeviceDto(PeripheralDevice peripheralDevice);
    List<PeripheralDeviceDto> toPeripheralDeviceDto(List<PeripheralDevice> peripheralDevices);

}
