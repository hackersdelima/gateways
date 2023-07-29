package com.shishir.gateways.controller;

import com.shishir.gateways.commons.ApiResponse;
import com.shishir.gateways.dto.GatewayDto;
import com.shishir.gateways.dto.PeripheralDeviceDto;
import com.shishir.gateways.entity.Gateway;
import com.shishir.gateways.entity.PeripheralDevice;
import com.shishir.gateways.mapper.GatewayMapper;
import com.shishir.gateways.mapper.PeripheralDeviceMapper;
import com.shishir.gateways.service.GatewayService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("impl/api/v1")
public class GatewayController {
    private static final Logger logger = LoggerFactory.getLogger(GatewayController.class);
    private final GatewayService gatewayService;
    private final GatewayMapper gatewayMapper;
    private final PeripheralDeviceMapper peripheralDeviceMapper;

    @GetMapping("/gateways")
    public ResponseEntity<ApiResponse> getAllGateways() {
        logger.info("Get all gateways requested.");
        List<Gateway> gateways = gatewayService.findAll();
        List<GatewayDto> response = gatewayMapper.toGatewayDto(gateways);
        return ResponseEntity.ok(new ApiResponse().success(response));
    }

    @GetMapping("/gateways/{id}")
    public ResponseEntity<ApiResponse> getOneGateway(@PathVariable long id) {
        logger.info("Get gateway by id {} requested.", id);
        Optional<Gateway> gateway = gatewayService.findById(id);
        if (gateway.isPresent()) {
            GatewayDto response = gatewayMapper.toGatewayDto(gateway.get());
            return ResponseEntity.ok(new ApiResponse().success(response));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/gateways")
    public ResponseEntity<ApiResponse> saveGateway(@RequestBody GatewayDto gatewayDto) {
        logger.info("Save gateway requested.");
        Optional<Gateway> createdGateway = gatewayService.save(
                gatewayMapper.toGateway(gatewayDto)
        );

        return createdGateway.map(gateway -> ResponseEntity.ok(new ApiResponse().success(
                gatewayMapper.toGatewayDto(gateway)
        ))).orElseGet(() -> ResponseEntity.badRequest().body(
                new ApiResponse().badRequest()
        ));
    }

    @GetMapping("/gateways/{id}/devices")
    public ResponseEntity<ApiResponse> getGatewayDevices(@PathVariable long id) {
        logger.info("Get devices requested for gateway id {}", id);
        List<PeripheralDevice> devices = gatewayService.findDevicesById(id);
        return ResponseEntity.ok(new ApiResponse().success(
                peripheralDeviceMapper.toPeripheralDeviceDto(devices)
        ));
    }


    @PostMapping("/gateways/{id}/devices")
    public ResponseEntity<ApiResponse> addGatewayDevice(@PathVariable long id, @RequestBody PeripheralDeviceDto peripheralDeviceDto) {
        logger.info("Add device for gateway {} requested.", id);
        PeripheralDevice peripheralDevice = peripheralDeviceMapper.toPeripheralDeviceEntity(peripheralDeviceDto);
        Optional<Gateway> gateway = gatewayService.addDevice(id, peripheralDevice);
        if (gateway.isPresent()) {
            return ResponseEntity.ok(
                    new ApiResponse().success(
                            peripheralDevice
                    )
            );
        } else {
            return ResponseEntity.badRequest().body(
                    new ApiResponse().badRequest()
            );
        }
    }


    @DeleteMapping("/gateways/{id}/devices/{deviceId}")
    public ResponseEntity<ApiResponse> removeGatewayDevices(@PathVariable long id, @PathVariable long deviceId) {
        logger.info("Remove device {} for gateway {} requested.", deviceId, id);
        Optional<Gateway> gateway = gatewayService.removeDevice(id, deviceId);
        if (gateway.isPresent()) {
            return ResponseEntity.ok(
                    new ApiResponse().success(null)
            );
        } else {
            return ResponseEntity.badRequest().body(
                    new ApiResponse().badRequest()
            );
        }
    }
}
