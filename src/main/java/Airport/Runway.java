package Airport;

import lombok.Getter;

public class Runway implements Runnable, FlightScheduleObserver {
    @Getter
    private final int runwayNumber;
    private boolean isOccupied;
    public static int runwayNumberGenerator = 0;

    public Runway() {
        this.runwayNumber = runwayNumberGenerator;
        this.isOccupied = false;
        runwayNumberGenerator++;
    }

    public void occupy() {
        isOccupied = true;
    }

    public void release() {
        isOccupied = false;
    }

    @Override
    public void run() {
        try {
            // Simulate passengers boarding or deboarding
            Thread.sleep(1000); // Wait for 1 second
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        release();
    }

    @Override
    public void update(Flight flight) {
        occupy();
        Thread gateThread = new Thread(this);
        gateThread.start();
    }

    public boolean isOccupied() {
        return isOccupied;
    }

}