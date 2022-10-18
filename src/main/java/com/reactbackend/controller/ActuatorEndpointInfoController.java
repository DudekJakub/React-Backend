package com.reactbackend.controller;

import com.reactbackend.model.ActuatorEndpointInfo;
import com.reactbackend.model.Post.Post;
import com.reactbackend.repository.ActuatorEndpointInfoRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@CrossOrigin("http://localhost:3000")
@Slf4j
public class ActuatorEndpointInfoController {

    ActuatorEndpointInfoRepository actuatorEndpointInfoRepository;

    @GetMapping("/actuator-info/{name}")
    public ActuatorEndpointInfo showActuatorEndpointInfo(@PathVariable String name) {
        return actuatorEndpointInfoRepository.findByName(name)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/actuator-info")
    public List<ActuatorEndpointInfo> showAll() {
        var all = actuatorEndpointInfoRepository.findAll();
        log.info("ALL ACTUATOR INFOS = {}", all);
        return all;
    }
}
