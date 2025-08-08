package io.github.joaobasto.optiroute.controller;

import com.google.ortools.Loader;
import com.google.ortools.constraintsolver.*;
import com.google.protobuf.Duration;
import io.github.joaobasto.optiroute.dto.RoutingRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class RoutingController {
    private static final int DEPOT_INDEX = 0;

    @PostMapping("/solve")
    public String solveCvrp(@RequestBody RoutingRequest request) {
        Loader.loadNativeLibraries();

        // Create Routing Index Manager
        RoutingIndexManager manager =
                new RoutingIndexManager(request.getDistanceMatrix().size(), request.getVehicleCapacities().size(), DEPOT_INDEX);

        // Create Routing Model.
        RoutingModel routing = new RoutingModel(manager);

        // Create and register a transit callback.
        final int transitCallbackIndex =
                routing.registerTransitCallback((long fromIndex, long toIndex) -> {
                    // Convert from routing variable Index to user NodeIndex.
                    int fromNode = manager.indexToNode(fromIndex);
                    int toNode = manager.indexToNode(toIndex);
                    return request.getDistanceMatrix().get(fromNode).get(toNode);
                });

        // Define cost of each arc.
        routing.setArcCostEvaluatorOfAllVehicles(transitCallbackIndex);

        // Add Capacity constraint.
        final int demandCallbackIndex = routing.registerUnaryTransitCallback((long fromIndex) -> {
            // Convert from routing variable Index to user NodeIndex.
            int fromNode = manager.indexToNode(fromIndex);
            return request.getDemands().get(fromNode);
        });
        boolean unused = routing.addDimensionWithVehicleCapacity(demandCallbackIndex,
                0, // null capacity slack
                request.getVehicleCapacities().stream().mapToLong(Integer::longValue).toArray(), // vehicle maximum capacities
                true, // start cumul to zero
                "Capacity");

        // Setting first solution heuristic.
        RoutingSearchParameters searchParameters =
                main.defaultRoutingSearchParameters()
                        .toBuilder()
                        .setFirstSolutionStrategy(FirstSolutionStrategy.Value.PATH_CHEAPEST_ARC)
                        .setLocalSearchMetaheuristic(LocalSearchMetaheuristic.Value.GUIDED_LOCAL_SEARCH)
                        .setTimeLimit(Duration.newBuilder().setSeconds(1).build())
                        .build();

        // Solve the problem.
        Assignment solution = routing.solveWithParameters(searchParameters);

        // Print solution on console.
        return printSolution(request, routing, manager, solution);
    }

    private String printSolution(
            RoutingRequest routingRequest, RoutingModel routing, RoutingIndexManager manager, Assignment solution) {
        StringBuilder stringBuilder = new StringBuilder();
        // Solution cost.
        stringBuilder.append("Objective: ");
        stringBuilder.append(solution.objectiveValue());
        stringBuilder.append(System.lineSeparator());

        // Inspect solution.
        long totalDistance = 0;
        long totalLoad = 0;
        for (int i = 0; i < routingRequest.getVehicleCapacities().size(); ++i) {
            if (!routing.isVehicleUsed(solution, i)) {
                continue;
            }
            long index = routing.start(i);
            stringBuilder.append("Route for Vehicle ").append(i).append(":");
            stringBuilder.append(System.lineSeparator());
            long routeDistance = 0;
            long routeLoad = 0;
            String route = "";
            while (!routing.isEnd(index)) {
                long nodeIndex = manager.indexToNode(index);
                routeLoad += routingRequest.getDemands().get((int) nodeIndex);
                route += nodeIndex + " Load(" + routeLoad + ") -> ";
                long previousIndex = index;
                index = solution.value(routing.nextVar(index));
                routeDistance += routing.getArcCostForVehicle(previousIndex, index, i);
            }
            route += manager.indexToNode(routing.end(i));
            stringBuilder.append(route);
            stringBuilder.append(System.lineSeparator());
            stringBuilder.append("Distance of the route: ").append(routeDistance).append("m");
            stringBuilder.append(System.lineSeparator());
            totalDistance += routeDistance;
            totalLoad += routeLoad;
        }
        stringBuilder.append("Total distance of all routes: " + totalDistance + "m");
        stringBuilder.append(System.lineSeparator());
        stringBuilder.append("Total load of all routes: " + totalLoad);
        stringBuilder.append(System.lineSeparator());

        return stringBuilder.toString();
    }
}
