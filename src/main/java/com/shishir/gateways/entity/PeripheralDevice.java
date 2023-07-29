package com.shishir.gateways.entity;

import com.shishir.gateways.enums.DeviceStatus;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "peripheral_device")
public class PeripheralDevice {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "uid", length = 10)
    private Long uid;

    @Column(name = "vendor", length = 50)
    private String vendor;

    @Column(name = "created_date", length = 10, updatable = false, columnDefinition = "DATE")
    @CreationTimestamp
    private Date createdDate;

    @Column(name = "status", length = 7)
    @Enumerated(EnumType.STRING)
    private DeviceStatus status;

    @ManyToOne
    @JoinColumn(name = "gateway_id")
    private Gateway gateway;
}
