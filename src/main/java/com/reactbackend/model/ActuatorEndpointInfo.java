package com.reactbackend.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "actuator_endpoint_info")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class ActuatorEndpointInfo {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String description;
}
