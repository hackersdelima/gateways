package com.shishir.gateways;

import com.shishir.gateways.entity.Gateway;
import com.shishir.gateways.entity.GatewayRepository;
import com.shishir.gateways.entity.PeripheralDevice;
import com.shishir.gateways.enums.DeviceStatus;
import com.shishir.gateways.util.RandomUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class DataInitializationScript {
    @Value("${gateway.data.init:false}")
    private boolean init;
    private final GatewayRepository gatewayRepository;

    @PostConstruct
    public void init() {
        if (init) {
            List<Gateway> gateways = new ArrayList<>();
            for (int i = 1; i <= 5; i++) {
                Gateway gateway = new Gateway();
                gateway.setIpv4Address("255.255.255." + i);
                gateway.setSerialNumber(UUID.randomUUID().toString());
                gateway.setName("Gateway " + i);
                gateway.setId((long) i);

                PeripheralDevice peripheralDevice = new PeripheralDevice();
                peripheralDevice.setUid(RandomUtil.genRandomNumber());
                peripheralDevice.setStatus(DeviceStatus.ONLINE);
                peripheralDevice.setVendor("Vendor Test");
                peripheralDevice.setGateway(gateway);

                gateway.setPeripheralDevices(Collections.singletonList(peripheralDevice));

                gateways.add(gateway);
            }

            gatewayRepository.saveAll(gateways);
        }
    }
}
