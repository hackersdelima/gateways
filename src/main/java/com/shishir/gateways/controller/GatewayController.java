package com.shishir.gateways.controller;

import com.shishir.gateways.commons.ApiResponse;
import com.shishir.gateways.dto.GatewayDto;
import com.shishir.gateways.dto.PeripheralDeviceRequestDto;
import com.shishir.gateways.entity.Gateway;
import com.shishir.gateways.entity.PeripheralDevice;
import com.shishir.gateways.exceptions.ResourceNotFoundException;
import com.shishir.gateways.mapper.GatewayMapper;
import com.shishir.gateways.mapper.PeripheralDeviceMapper;
import com.shishir.gateways.service.GatewayService;
import com.shishir.gateways.util.ValidationCheckUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("impl/api/v1")
@Api(tags = "Gateway REST APIs")
public class GatewayController {
    private static final Logger logger = LoggerFactory.getLogger(GatewayController.class);
    private final GatewayService gatewayService;
    private final GatewayMapper gatewayMapper;
    private final PeripheralDeviceMapper peripheralDeviceMapper;

    @GetMapping("/gateways")
    @ApiOperation("Fetch all available Gateways")
    @ApiResponses(value = {
            @io.swagger.annotations.ApiResponse(code = 200, message = "Successful response"),
            @io.swagger.annotations.ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<ApiResponse> getAllGateways() {
        logger.info("Get all gateways requested.");
        List<Gateway> gateways = gatewayService.findAll();
        List<GatewayDto> response = gatewayMapper.toGatewayDto(gateways);
        return ResponseEntity.ok(new ApiResponse().success(response));
    }

    @GetMapping("/gateways/{id}")
    @ApiOperation("Fetch one Gateway by ID")
    @ApiResponses(value = {
            @io.swagger.annotations.ApiResponse(code = 200, message = "Successful response"),
            @io.swagger.annotations.ApiResponse(code = 404, message = "Gateway Not Found"),
            @io.swagger.annotations.ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<ApiResponse> getOneGateway(@PathVariable long id) {
        logger.info("Get gateway by id {} requested.", id);
        Optional<Gateway> gateway = gatewayService.findById(id);
        if (gateway.isPresent()) {
            GatewayDto response = gatewayMapper.toGatewayDto(gateway.get());
            return ResponseEntity.ok(new ApiResponse().success(response));
        } else {
            throw new ResourceNotFoundException("Gateway not found for id: " + id);
        }
    }

    @PostMapping("/gateways")
    @ApiOperation("Create one Gateway")
    @ApiResponses(value = {
            @io.swagger.annotations.ApiResponse(code = 200, message = "Successful response"),
            @io.swagger.annotations.ApiResponse(code = 400, message = "Invalid Request / Validation Error"),
            @io.swagger.annotations.ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<ApiResponse> addOneGateway(@Valid @RequestBody GatewayDto gatewayDto, BindingResult bindingResult) {
        logger.info("Save gateway requested.");

        ValidationCheckUtil.checkBindingResultAndThrow(bindingResult);

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
    @ApiOperation("Fetch all available devices for Gateway")
    @ApiResponses(value = {
            @io.swagger.annotations.ApiResponse(code = 200, message = "Successful Response"),
            @io.swagger.annotations.ApiResponse(code = 404, message = "Gateway Not Found"),
            @io.swagger.annotations.ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<ApiResponse> getGatewayDevices(@PathVariable long id) {
        logger.info("Get devices requested for gateway id {}", id);
        List<PeripheralDevice> devices = gatewayService.findDevicesById(id);
        return ResponseEntity.ok(new ApiResponse().success(
                peripheralDeviceMapper.toPeripheralDeviceDto(devices)
        ));
    }


    @PostMapping("/gateways/{id}/devices")
    @ApiOperation("Add a device to Gateway")
    @ApiResponses(value = {
            @io.swagger.annotations.ApiResponse(code = 200, message = "Successful response"),
            @io.swagger.annotations.ApiResponse(code = 400, message = "Invalid Request"),
            @io.swagger.annotations.ApiResponse(code = 404, message = "Gateway Not Found"),
            @io.swagger.annotations.ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<ApiResponse> addGatewayDevice(@PathVariable long id, @RequestBody PeripheralDeviceRequestDto peripheralDeviceRequestDto) {
        logger.info("Add device for gateway {} requested.", id);
        PeripheralDevice peripheralDevice = peripheralDeviceMapper.toPeripheralDeviceEntity(peripheralDeviceRequestDto);
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
    @ApiOperation("Remove device from a Gateway")
    @ApiResponses(value = {
            @io.swagger.annotations.ApiResponse(code = 200, message = "Successful response"),
            @io.swagger.annotations.ApiResponse(code = 400, message = "Invalid Request"),
            @io.swagger.annotations.ApiResponse(code = 404, message = "Gateway Not Found"),
            @io.swagger.annotations.ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<ApiResponse> removeGatewayDevice(@PathVariable long id, @PathVariable long deviceId) {
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
