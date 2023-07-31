package com.shishir.gateways.service;

import com.shishir.gateways.entity.Gateway;
import com.shishir.gateways.entity.GatewayRepository;
import com.shishir.gateways.entity.PeripheralDevice;
import com.shishir.gateways.exceptions.DeviceLimitExceededException;
import com.shishir.gateways.exceptions.ResourceNotFoundException;
import com.shishir.gateways.util.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class GatewayServiceImpl implements GatewayService {
    private static final Logger logger = LoggerFactory.getLogger(GatewayServiceImpl.class);
    @Value("${gateway.devices.limit}")
    int devicesLimit;
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
    public List<PeripheralDevice> findDevicesById(Long gatewayId) {
        Optional<Gateway> gateway = findById(gatewayId);
        if (gateway.isPresent()) {
            if (gateway.get().getPeripheralDevices() != null && !gateway.get().getPeripheralDevices().isEmpty()) {
                return gateway.get().getPeripheralDevices();
            }
        } else {
            logger.error("Gateway not found for id: {}", gatewayId);
            throw new ResourceNotFoundException("Gateway not found for id: " + gatewayId);
        }

        return new ArrayList<>();
    }

    @Override
    @Transactional
    public Optional<Gateway> addDevice(Long gatewayId, PeripheralDevice peripheralDevice) {
        Optional<Gateway> gatewayOptional = gatewayRepository.findById(gatewayId);
        if (gatewayOptional.isPresent()) {
            Gateway gateway = gatewayOptional.get();
            peripheralDevice.setUid(RandomUtil.genRandomNumber());
            peripheralDevice.setGateway(gateway);

            List<PeripheralDevice> peripheralDevices = gateway.getPeripheralDevices();

            if (peripheralDevices == null || peripheralDevices.isEmpty()) {
                peripheralDevices = new ArrayList<>();
                peripheralDevices.add(peripheralDevice);
            } else {
                if (peripheralDevices.size() == devicesLimit) {
                    logger.error("Device limit exceeded for Gateway {}", gatewayId);
                    throw new DeviceLimitExceededException();
                } else {
                    peripheralDevices.add(peripheralDevice);
                }
            }

            return Optional.of(gatewayRepository.save(gateway));
        } else {
            logger.error("Gateway not found for id: {}", gatewayId);
            throw new ResourceNotFoundException("Gateway not found for id: " + gatewayId);
        }
    }

    @Override
    @Transactional
    public Optional<Gateway> removeDevice(Long gatewayId, Long deviceId) {
        Optional<Gateway> gatewayOptional = gatewayRepository.findById(gatewayId);
        if (gatewayOptional.isPresent()) {
            Gateway gateway = gatewayOptional.get();
            List<PeripheralDevice> devices = gateway.getPeripheralDevices();

            devices.removeIf(device -> Objects.equals(device.getId(), deviceId));
            gateway.setPeripheralDevices(devices);

            return Optional.of(gatewayRepository.save(gateway));
        } else {
            logger.error("Gateway not found for id: {}", gatewayId);
            throw new ResourceNotFoundException("Gateway not found for id: " + gatewayId);
        }
    }
}
