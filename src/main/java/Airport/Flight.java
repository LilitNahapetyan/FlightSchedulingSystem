package Airport;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
public class Flight {
    private final String flightNumber;
    private final String flightFrom;
    private final String flightTo;
    private final LocalDateTime flightStartTime;
    private final LocalDateTime flightEndTime;
    private FlightState flightState;


    // Enum representing the possible states of a flight
    public enum FlightState {
        ON_RUNWAY, // Flight is on the runway, preparing for takeoff
        ON_GATE, // Flight is at a gate, passengers are boarding or deboarding
        SCHEDULED, // Flight is scheduled but not yet in any state
        ARRIVED, // Flight has arrived at its destination
        FLYING // Flight is in the air
    }

    public Flight(String flightNumber, String flightFrom, String flightTo, LocalDateTime flightStartTime, LocalDateTime flightEndTime) {
        this.flightNumber = flightNumber;
        this.flightFrom = flightFrom;
        this.flightTo = flightTo;
        this.flightStartTime = flightStartTime;
        this.flightEndTime = flightEndTime;
        this.flightState = FlightState.SCHEDULED;
    }

    public void setFlightState(FlightState flightState) {
        this.flightState = flightState;
    }
}