package com.shishir.gateways;

import com.shishir.gateways.entity.Gateway;
import com.shishir.gateways.entity.GatewayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootApplication
public class GatewaysApplication {
    @Autowired
    private GatewayRepository gatewayRepository;

    public static void main(String[] args) {
        SpringApplication.run(GatewaysApplication.class, args);
    }

    @PostConstruct
    public void init() {
        List<Gateway> gateways = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            Gateway gateway = new Gateway();
            gateway.setIpv4Address("255.255.255." + i);
            gateway.setSerialNumber(UUID.randomUUID().toString());
            gateway.setName("Gateway " + i);
            gateway.setId((long) i);

            gateways.add(gateway);
        }

        gatewayRepository.saveAll(gateways);
    }
}
