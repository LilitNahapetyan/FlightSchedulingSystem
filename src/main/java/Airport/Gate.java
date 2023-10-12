package Airport;

import lombok.Getter;

public class Gate implements Runnable, FlightScheduleObserver {
    @Getter
    private final int gateNumber;
    private boolean isOccupied;
    public static int gateNumberGenerator = 0;

    public Gate() {
        this.gateNumber = gateNumberGenerator;
        this.isOccupied = false;
        gateNumberGenerator++;
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