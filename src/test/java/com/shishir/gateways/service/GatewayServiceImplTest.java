package com.shishir.gateways.service;

import com.shishir.gateways.entity.Gateway;
import com.shishir.gateways.entity.GatewayRepository;
import com.shishir.gateways.entity.PeripheralDevice;
import com.shishir.gateways.enums.DeviceStatus;
import com.shishir.gateways.exceptions.DeviceLimitExceededException;
import com.shishir.gateways.exceptions.ResourceNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * This class represents unit test for {@link GatewayServiceImpl}
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class GatewayServiceImplTest {
    @InjectMocks
    private GatewayServiceImpl gatewayService;

    @Mock
    private GatewayRepository gatewayRepository;

    @Before
    public void setUp() {
        gatewayService = new GatewayServiceImpl(gatewayRepository);
    }

    @Test
    @DisplayName("Find Gateway by ID success")
    public void testFindById_should_return_Gateway() {
        // Given
        Long gatewayId = 1L;
        Gateway gateway = mockGateway();
        gateway.setId(gatewayId);

        when(gatewayRepository.findById(gatewayId)).thenReturn(Optional.of(gateway));
        // When
        Optional<Gateway> foundGateway = gatewayService.findById(gatewayId);
        // Then
        assertTrue(foundGateway.isPresent());
        assertEquals(gateway, foundGateway.get());
    }

    @Test
    @DisplayName("Find all Gateways success")
    public void testFindAll_should_return_Gateways() {
        // Given
        List<Gateway> gateways = mockGateways();
        when(gatewayRepository.findAll()).thenReturn(gateways);
        // When
        List<Gateway> foundGateways = gatewayService.findAll();
        // Then
        assertEquals(gateways, foundGateways);
    }

    @Test
    @DisplayName("Save Gateway success")
    public void testSave_should_return_Gateway() {
        // Given
        Gateway gateway = mockGateway();
        when(gatewayRepository.save(gateway)).thenReturn(gateway);
        // When
        Optional<Gateway> savedGateway = gatewayService.save(gateway);
        // Then
        assertTrue(savedGateway.isPresent());
        assertEquals(gateway, savedGateway.get());
    }

    @Test
    @DisplayName("Find Gateway Devices by ID success")
    public void testFindDevicesById_should_return_PeripheralDevices() {
        // Given
        Long gatewayId = 1L;
        Gateway gateway = mockGateway();
        List<PeripheralDevice> peripheralDevices = mockPeripheralDevices(2);
        gateway.setPeripheralDevices(peripheralDevices);
        when(gatewayRepository.findById(gatewayId)).thenReturn(Optional.of(gateway));
        // When
        List<PeripheralDevice> foundDevices = gatewayService.findDevicesById(gatewayId);
        // Then
        assertEquals(peripheralDevices, foundDevices);
    }

    @Test
    @DisplayName("Find Gateway Devices by ID exception")
    public void testFindDevicesById_should_throw_exception() {
        // Given
        Long gatewayId = 1L;
        when(gatewayRepository.findById(gatewayId)).thenReturn(Optional.empty());
        // Then
        assertThrows(ResourceNotFoundException.class, () -> gatewayService.findDevicesById(gatewayId));
    }

    @Test
    @DisplayName("Add Gateway Device success")
    public void testAddDevice_should_add_device() {
        // Given
        Long gatewayId = 1L;
        Gateway gateway = mockGateway();
        gateway.setId(gatewayId);

        PeripheralDevice peripheralDevice = mockPeripheralDevice();
        when(gatewayRepository.findById(gatewayId)).thenReturn(Optional.of(gateway));

        when(gatewayRepository.save(gateway)).thenReturn(mockGatewayWithDevice(peripheralDevice));
        // When
        Optional<Gateway> gatewayWithDeviceOptional = gatewayService.addDevice(gatewayId, peripheralDevice);
        // Then
        assertEquals(peripheralDevice, gatewayWithDeviceOptional.get().getPeripheralDevices().get(0));
    }

    @Test
    @DisplayName("Add Gateway Device exception")
    public void testAddDevice_should_throw_DeviceLimitExceededException() {
        // Given
        int deviceLimit = 10;
        ReflectionTestUtils.setField(gatewayService, "devicesLimit", deviceLimit);

        PeripheralDevice peripheralDevice = mockPeripheralDevice();

        List<PeripheralDevice> peripheralDevices = mockPeripheralDevices(10);
        Long gatewayId = 1L;
        Gateway gateway = mockGateway();
        gateway.setId(gatewayId);
        gateway.setPeripheralDevices(peripheralDevices);

        when(gatewayRepository.findById(gatewayId)).thenReturn(Optional.of(gateway));
        // Then
        assertThrows(DeviceLimitExceededException.class, () -> gatewayService.addDevice(gatewayId, peripheralDevice));
    }

    @Test
    @DisplayName("Remove Gateway Device success")
    public void testRemoveDevice_should_return_Gateway() {
        // Given
        List<PeripheralDevice> peripheralDevices = mockPeripheralDevices(1);
        Long gatewayId = 1L;
        Gateway gateway = mockGateway();
        gateway.setId(gatewayId);
        gateway.setPeripheralDevices(peripheralDevices);

        Gateway gatewayAfterDeviceRemoval = mockGateway();
        when(gatewayRepository.findById(gatewayId)).thenReturn(Optional.of(gateway));
        when(gatewayRepository.save(gateway)).thenReturn(gatewayAfterDeviceRemoval);
        // When
        Optional<Gateway> gatewayOptional = gatewayService.removeDevice(gatewayId, 10L);
        // Then
        assertTrue(gatewayOptional.get().getPeripheralDevices().isEmpty());
    }

    @Test
    @DisplayName("Remove Gateway Device exception")
    public void testRemoveDevice_should_throw_ResourceNotFoundException() {
        when(gatewayRepository.findById(any())).thenReturn(Optional.empty());
        // Then
        assertThrows(ResourceNotFoundException.class, () -> gatewayService.removeDevice(1L, 1L));
    }

    // Mock values
    private Gateway mockGateway() {
        Gateway gateway = new Gateway();
        gateway.setSerialNumber(UUID.randomUUID().toString());
        gateway.setName("Gateway Test");
        gateway.setIpv4Address("255.255.255.255");
        return gateway;
    }

    private Gateway mockGatewayWithDevice(PeripheralDevice peripheralDevice){
        Gateway gatewayWithDevices = mockGateway();
        gatewayWithDevices.setPeripheralDevices(Collections.singletonList(peripheralDevice));
        return gatewayWithDevices;
    }

    private List<Gateway> mockGateways() {
        List<Gateway> gateways = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            Gateway gateway = new Gateway();
            gateway.setSerialNumber(UUID.randomUUID().toString());
            gateway.setId((long) i);
            gateway.setName("Gateway " + i);
            gateway.setIpv4Address("255.255.255.25" + i);
            gateways.add(gateway);
        }
        return gateways;
    }

    private List<PeripheralDevice> mockPeripheralDevices(int count){
        List<PeripheralDevice> devices = new ArrayList<>();

        for(int i=1; i<=count; i++) {
            PeripheralDevice peripheralDevice = new PeripheralDevice();
            peripheralDevice.setId((long) i);
            peripheralDevice.setStatus(DeviceStatus.ONLINE);
            peripheralDevice.setVendor("Vendor Test "+i);
            peripheralDevice.setUid((long) i);
            peripheralDevice.setCreatedDate(new Date());

            devices.add(peripheralDevice);
        }

        return devices;
    }

    private PeripheralDevice mockPeripheralDevice(){
        PeripheralDevice peripheralDevice = new PeripheralDevice();
        peripheralDevice.setStatus(DeviceStatus.ONLINE);
        peripheralDevice.setVendor("Vendor Test ");
        peripheralDevice.setUid((long) 123456789);
        peripheralDevice.setCreatedDate(new Date());
        return peripheralDevice;
    }

}
