package io.github.joaobasto.optiroute.dto;

import java.util.List;

public class RoutingRequest {
    private List<Integer> demands;
    private List<Integer> vehicleCapacities;

    public List<Integer> getDemands() {
        return demands;
    }
    public void setDemands(List<Integer> demands) {
        this.demands = demands;
    }

    public List<Integer> getVehicleCapacities() {
        return vehicleCapacities;
    }
    public void setVehicleCapacities(List<Integer> vehicleCapacities) {
        this.vehicleCapacities = vehicleCapacities;
    }
}