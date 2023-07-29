package com.shishir.gateways.controller;

import com.shishir.gateways.commons.ApiResponse;
import com.shishir.gateways.dto.GatewayResponseDto;
import com.shishir.gateways.entity.Gateway;
import com.shishir.gateways.mapper.GatewayMapper;
import com.shishir.gateways.service.GatewayService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
        List<GatewayResponseDto> response = gatewayMapper.gatewayToGatewayResponseDto(gateways);
        return ResponseEntity.ok(new ApiResponse().success(response));
    }
}
