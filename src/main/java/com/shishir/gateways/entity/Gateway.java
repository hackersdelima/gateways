package com.shishir.gateways.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "gateway")
public class Gateway {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "serial_number", length = 36)
    private String serialNumber;

    @Column(name = "name", length = 50)
    private String name;

    @Column(name = "ipv4_address", length = 15)
    private String ipv4Address;

    @OneToMany(mappedBy = "gateway", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PeripheralDevice> peripheralDevices = new ArrayList<>();
}
