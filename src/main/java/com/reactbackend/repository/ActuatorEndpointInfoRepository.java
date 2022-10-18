package com.reactbackend.repository;

import com.reactbackend.model.ActuatorEndpointInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ActuatorEndpointInfoRepository extends JpaRepository<ActuatorEndpointInfo, Long> {

    Optional<ActuatorEndpointInfo> findByName(String name);
}