package com.reactbackend.monitoring;

import org.springframework.boot.actuate.endpoint.annotation.*;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
@Endpoint(id="custom-health")
public class CustomHealthEndPoint {

    Map<String, Object> details = new LinkedHashMap<>();

    @ReadOperation
    public CustomHealth health() {
        details.put("CustomHealthStatus", "Everything looks good");
        CustomHealth health = new CustomHealth();
        health.setHealthDetails(details);
        return health;
    }

    @ReadOperation
    public String customEndPointByName(@Selector String name) {
        return "custom-end-point";
    }

    @WriteOperation
    public void writeOperation(@Selector String name) {
        details.put("CustomHealthStatus2", name);
    }
    @DeleteOperation
    public void deleteOperation(@Selector String name){
        //delete operation
    }
}
