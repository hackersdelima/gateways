package com.shishir.gateways.controller;

import com.shishir.gateways.commons.ApiResponse;
import com.shishir.gateways.dto.GatewayDto;
import com.shishir.gateways.entity.Gateway;
import com.shishir.gateways.mapper.GatewayMapper;
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

    @GetMapping("/gateways")
    public ResponseEntity<ApiResponse> getAllGateways() {
        logger.info("Get all gateways requested.");
        List<Gateway> gateways = gatewayService.findAll();
        List<GatewayDto> response = gatewayMapper.toGatewayDto(gateways);
        return ResponseEntity.ok(new ApiResponse().success(response));
    }

    @PostMapping("/gateways")
    public ResponseEntity<ApiResponse> saveGateway(@RequestBody GatewayDto gatewayDto) {
        Optional<Gateway> createdGateway = gatewayService.save(
                gatewayMapper.toGateway(gatewayDto)
        );

        return createdGateway.map(gateway -> ResponseEntity.ok(new ApiResponse().success(
                gatewayMapper.toGatewayDto(gateway)
        ))).orElseGet(() -> ResponseEntity.badRequest().body(
                new ApiResponse().badRequest()
        ));
    }
}
