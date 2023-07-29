package com.shishir.gateways.service;

import com.shishir.gateways.entity.Gateway;
import com.shishir.gateways.entity.PeripheralDevice;

import java.util.List;
import java.util.Optional;

public interface GatewayService {
    Optional<Gateway> findById(Long gatewayId);

    List<Gateway> findAll();

    Optional<Gateway> save(Gateway gateway);

    List<PeripheralDevice> findDevicesById(Long gatewayId);

    Optional<Gateway> addDevice(Long gatewayId, PeripheralDevice peripheralDevice);

    Optional<Gateway> removeDevice(Long gatewayId, Long deviceId);
}
