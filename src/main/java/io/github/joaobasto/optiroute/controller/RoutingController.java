package io.github.joaobasto.optiroute.controller;

import io.github.joaobasto.optiroute.dto.RoutingRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class RoutingController {

    @PostMapping("/solve")
    public String solveCvrp(@RequestBody RoutingRequest request) {
        // Just echo back for now
        return "Demands: " + request.getDemands() +
                ", Capacities: " + request.getVehicleCapacities();
    }
}
