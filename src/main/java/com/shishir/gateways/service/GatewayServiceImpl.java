package com.shishir.gateways.service;

import com.shishir.gateways.entity.Gateway;
import com.shishir.gateways.entity.GatewayRepository;
import com.shishir.gateways.entity.PeripheralDevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GatewayServiceImpl implements GatewayService {
    @Value("${gateway.devices.limit}")
    private int devicesLimit;
    private final GatewayRepository gatewayRepository;

    @Autowired
    public GatewayServiceImpl(GatewayRepository gatewayRepository) {
        this.gatewayRepository = gatewayRepository;
    }

    @Override
    public Optional<Gateway> findById(Long gatewayId) {
        return gatewayRepository.findById(gatewayId);
    }

    @Override
    public List<Gateway> findAll() {
        return gatewayRepository.findAll();
    }

    @Override
    public Optional<Gateway> save(Gateway gateway) {
        return Optional.of(gatewayRepository.save(gateway));
    }

    @Override
    public Optional<Gateway> addDevice(Long gatewayId, PeripheralDevice peripheralDevice) {
        Optional<Gateway> gatewayOptional = gatewayRepository.findById(gatewayId);
        if (gatewayOptional.isPresent()) {
            Gateway gateway = gatewayOptional.get();

            if (null != gateway.getPeripheralDevices() && gateway.getPeripheralDevices().size() < devicesLimit) {
                gateway.getPeripheralDevices().add(peripheralDevice);
            } else {
                gateway.setPeripheralDevices(Collections.singletonList(peripheralDevice));
            }

            return Optional.of(gatewayRepository.save(gateway));
        }

        return Optional.empty();
    }

    @Override
    public Optional<Gateway> removeDevice(Long gatewayId, Long deviceId) {
        Optional<Gateway> gatewayOptional = gatewayRepository.findById(gatewayId);
        if (gatewayOptional.isPresent()) {
            Gateway gateway = gatewayOptional.get();

            if (null != gateway.getPeripheralDevices() && gateway.getPeripheralDevices().size() > 1) {
                List<PeripheralDevice> devices = gateway.getPeripheralDevices()
                        .stream()
                        .filter(peripheralDevice -> !Objects.equals(peripheralDevice.getId(), deviceId))
                        .collect(Collectors.toList());
                gateway.setPeripheralDevices(devices);
            } else {
                gateway.setPeripheralDevices(null);
            }

            return Optional.of(gatewayRepository.save(gateway));
        }

        return Optional.empty();
    }
}
