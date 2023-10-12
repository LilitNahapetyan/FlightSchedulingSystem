package Airport;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FlightSchedulingSystem {
    List<Flight> flights = new ArrayList<>();


    public void start() {
        Airport airport = Airport.getInstance();

        Gate gate1 = new Gate();
        Gate gate2 = new Gate();

        Runway runway1 = new Runway();
        Runway runway2 = new Runway();

        airport.addGate(gate1);
        airport.addGate(gate2);

        airport.addRunway(runway1);
        airport.addRunway(runway2);

        LocalDateTime now = LocalDateTime.now();
        Flight flight1 = new Flight("F123", Airport.name, "USA", now, now.plusMinutes(2));
        Flight flight2 = new Flight("F124", Airport.name, "USA", now, now.plusMinutes(1));
        Flight flight3 = new Flight("F125", "USA", Airport.name, now, now.plusMinutes(1));
        Flight flight4 = new Flight("F126", "USA", Airport.name, now, now.plusMinutes(1));
        flights.add(flight1);
        flights.add(flight2);
        flights.add(flight3);

        cancelFlight(flight4, flights);

        LocalDateTime currentTime;

        while (!flights.isEmpty()) {

            System.out.println("Count of flights is "  + flights.size());
            currentTime = LocalDateTime.now();
            try {
                scheduleFlights(currentTime, airport);
            } catch (InsufficientResourcesException e) {
                System.out.println(e.getMessage());
            }

            for (String string : flightStates()) {
                System.out.println(string);
            }
            simulateAirportOperation();

        }
    }

    private void scheduleFlights(LocalDateTime currentTime, Airport airport) throws InsufficientResourcesException {
        Iterator<Flight> iterator = flights.iterator();
        while (iterator.hasNext()) {

            Flight flight = iterator.next();
            Flight.FlightState flightState = flight.getFlightState();
            if (flightState == Flight.FlightState.ARRIVED || flightState == Flight.FlightState.FLYING) {
                System.out.println(flight.getFlightNumber() + "is removed");
                iterator.remove();
            } else {
                if ((flight.getFlightEndTime().isBefore(currentTime) && flight.getFlightTo().equals(Airport.name)) ||
                        (flight.getFlightStartTime().isBefore(currentTime) && flight.getFlightFrom().equals(Airport.name))) {
                    airport.scheduleFlight(flight);
                }
            }
        }
    }

    private static void simulateAirportOperation() {
        try {
            Thread.sleep(10_000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static void displayTakenOffFlights(Airport airport) {
        List<Flight> takenOffFlights = airport.getFlightsTakenOff();
        for (Flight flight : takenOffFlights) {
            System.out.println("Flight " + flight.getFlightNumber() + " to " + flight.getFlightTo() + " at " + flight.getFlightStartTime());
        }
    }

    private static void cancelFlight(Flight flight, List<Flight> flights) {
        for (Flight f : flights) {
            if (f.getFlightNumber().equals(flight.getFlightNumber())) {
                if (flight.getFlightState() == Flight.FlightState.SCHEDULED) {
                    flights.remove(flight);
                    System.out.println("Flight " + flight.getFlightNumber() + " has been canceled.");
                    return;
                } else {
                    System.out.println("Cannot cancel the flight. Invalid state: " + flight.getFlightState());
                }
            }
        }
    }

    public List<String> flightStates() {
        List<String> flightStates = new ArrayList<>();

        for (Flight flight : flights) {
            String flightInfo = "Flight " + flight.getFlightNumber() +
                    " from " + flight.getFlightFrom() +
                    " to " + flight.getFlightTo() +
                    " is currently in state: " + flight.getFlightState();
            flightStates.add(flightInfo);
        }

        return flightStates;
    }

}