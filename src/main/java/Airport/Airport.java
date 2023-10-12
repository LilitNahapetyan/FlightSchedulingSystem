package Airport;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.*;

public class Airport {
    private static Airport instance;
    private final List<Gate> gates;
    private final List<Runway> runways;
    @Getter
    private final List<FlightScheduleObserver> observers;
    private final Set<Flight> flightsOnRunways;
    public static final String name = "Armenia";

    private Airport() {
        gates = new ArrayList<>();
        runways = new ArrayList<>();
        observers = new ArrayList<>();
        flightsOnRunways = new HashSet<>();
    }

    // Singleton pattern: Get the instance of the Airport
    public static Airport getInstance() {
        if (instance == null) {
            synchronized (Airport.class) {
                if (instance == null) {
                    instance = new Airport();
                }
            }
        }
        return instance;
    }

    public void addGate(Gate gate) {
        gates.add(gate);
    }

    public void addRunway(Runway runway) {
        runways.add(runway);
    }


    // Schedule a flight based on its state and available resources
    public synchronized void scheduleFlight(Flight flight) throws InsufficientResourcesException {
        Flight.FlightState flightState = flight.getFlightState();
        switch (flightState) {
            case SCHEDULED -> {
                if (flight.getFlightFrom().equals(Airport.name)) {
                    flight.setFlightState(Flight.FlightState.ON_GATE);
                    Gate selectedGate = findAvailableGate(gates);
                    if (selectedGate != null) {
                        selectedGate.update(flight);
                    } else {
                        throw new InsufficientResourcesException("No available gates for flight " + flight.getFlightNumber());
                    }
                } else {
                    Runway selectedRunway = findAvailableRunway(runways);
                    flight.setFlightState(Flight.FlightState.ON_RUNWAY);
                    if (selectedRunway != null) {
                        selectedRunway.update(flight);
                    } else {
                        throw new InsufficientResourcesException("No available runways for flight " + flight.getFlightNumber());
                    }
                }
            }
            case ON_GATE -> {

                if (flight.getFlightFrom().equals(Airport.name)) {
                    flight.setFlightState(Flight.FlightState.ON_RUNWAY);
                    Runway selectedRunway = findAvailableRunway(runways);
                    if (selectedRunway != null) {
                        selectedRunway.update(flight);
                    } else {
                        throw new InsufficientResourcesException("No available runways for flight " + flight.getFlightNumber());
                    }
                } else {
                    flight.setFlightState(Flight.FlightState.ARRIVED);
                }
            }
            case ON_RUNWAY -> {
                if (!flight.getFlightTo().equals(Airport.name)) {
                    flight.setFlightState(Flight.FlightState.FLYING);
                } else {
                    flight.setFlightState(Flight.FlightState.ON_GATE);
                    Gate selectedGate = findAvailableGate(gates);
                    if (selectedGate != null) {
                        selectedGate.update(flight);
                    } else {
                        System.out.println(flight);
                        throw new InsufficientResourcesException("No available gates for flight " + flight.getFlightNumber());
                    }
                }
            }
        }
    }


    private Gate findAvailableGate(List<Gate> gates) {
        for (Gate gate : gates) {
            if (!gate.isOccupied()) {
                return gate;
            }
        }
        return null;
    }

    private Runway findAvailableRunway(List<Runway> runways) {
        for (Runway runway : runways) {
            if (!runway.isOccupied()) {
                return runway;
            }
        }
        return null;
    }

    public synchronized void showGateAndRunwayStatus(LocalDateTime time) {
        System.out.println("Gate and Runway Status at " + time.toString() + ":");
        for (Gate gate : gates) {
            if (gate.isOccupied()) {
                System.out.println("Gate " + gate.getGateNumber() + " is occupied");
            } else {
                System.out.println("Gate " + gate.getGateNumber() + " is free");
            }
        }
        for (Runway runway : runways) {
            if (runway.isOccupied()) {
                System.out.println("Runway " + runway.getRunwayNumber() + " is occupied");
            } else {
                System.out.println("Runway " + runway.getRunwayNumber() + " is free");
            }
        }
    }


    public synchronized List<Flight> getFlightsTakenOff() {
        List<Flight> takenOffFlights = new ArrayList<>();
        for (Flight flight : flightsOnRunways) {
            if (!flight.getFlightTo().equals(Airport.name)) {
                takenOffFlights.add(flight);
            }
        }
        return takenOffFlights;
    }

}



