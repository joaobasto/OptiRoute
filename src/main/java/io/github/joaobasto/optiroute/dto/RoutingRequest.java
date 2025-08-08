package io.github.joaobasto.optiroute.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public class RoutingRequest {

    @Schema(description = "List of demands. First one is the depot and is expected to be 0",
            example = "[0, 1, 1, 2, 4, 2, 4, 8, 8, 1, 2, 1, 2, 4, 4, 8, 8]")
    private List<Integer> demands;
    @Schema(description = "List of vehicle capacities",
            example = "[15, 15, 15, 15]")
    private List<Integer> vehicleCapacities;
    @Schema(description = "Distance matrix - element in row i, column j, represents the distance from point i to point j. " +
            "Point 0 is the depot.",
            example = "[\n" +
                    "  [0, 548, 776, 696, 582, 274, 502, 194, 308, 194, 536, 502, 388, 354, 468, 776, 662],\n" +
                    "  [548, 0, 684, 308, 194, 502, 730, 354, 696, 742, 1084, 594, 480, 674, 1016, 868, 1210],\n" +
                    "  [776, 684, 0, 992, 878, 502, 274, 810, 468, 742, 400, 1278, 1164, 1130, 788, 1552, 754],\n" +
                    "  [696, 308, 992, 0, 114, 650, 878, 502, 844, 890, 1232, 514, 628, 822, 1164, 560, 1358],\n" +
                    "  [582, 194, 878, 114, 0, 536, 764, 388, 730, 776, 1118, 400, 514, 708, 1050, 674, 1244],\n" +
                    "  [274, 502, 502, 650, 536, 0, 228, 308, 194, 240, 582, 776, 662, 628, 514, 1050, 708],\n" +
                    "  [502, 730, 274, 878, 764, 228, 0, 536, 194, 468, 354, 1004, 890, 856, 514, 1278, 480],\n" +
                    "  [194, 354, 810, 502, 388, 308, 536, 0, 342, 388, 730, 468, 354, 320, 662, 742, 856],\n" +
                    "  [308, 696, 468, 844, 730, 194, 194, 342, 0, 274, 388, 810, 696, 662, 320, 1084, 514],\n" +
                    "  [194, 742, 742, 890, 776, 240, 468, 388, 274, 0, 342, 536, 422, 388, 274, 810, 468],\n" +
                    "  [536, 1084, 400, 1232, 1118, 582, 354, 730, 388, 342, 0, 878, 764, 730, 388, 1152, 354],\n" +
                    "  [502, 594, 1278, 514, 400, 776, 1004, 468, 810, 536, 878, 0, 114, 308, 650, 274, 844],\n" +
                    "  [388, 480, 1164, 628, 514, 662, 890, 354, 696, 422, 764, 114, 0, 194, 536, 388, 730],\n" +
                    "  [354, 674, 1130, 822, 708, 628, 856, 320, 662, 388, 730, 308, 194, 0, 342, 422, 536],\n" +
                    "  [468, 1016, 788, 1164, 1050, 514, 514, 662, 320, 274, 388, 650, 536, 342, 0, 764, 194],\n" +
                    "  [776, 868, 1552, 560, 674, 1050, 1278, 742, 1084, 810, 1152, 274, 388, 422, 764, 0, 798],\n" +
                    "  [662, 1210, 754, 1358, 1244, 708, 480, 856, 514, 468, 354, 844, 730, 536, 194, 798, 0]\n" +
                    "]\n")
    private List<List<Integer>> distanceMatrix;

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

    public List<List<Integer>> getDistanceMatrix() {
        return distanceMatrix;
    }

    public void setDistanceMatrix(List<List<Integer>> distanceMatrix) {
        this.distanceMatrix = distanceMatrix;
    }
}