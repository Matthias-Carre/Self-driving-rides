package rides;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
class Vehicle {
    int vehicleId;
    int currentRow;
    int currentCol;
    int availableTime;
    List<Integer> coursesAssi;

    public Vehicle(int vehicleId) {
        this.vehicleId = vehicleId;
        this.currentRow = 0;
        this.currentCol = 0;
        this.availableTime = 0;
        this.coursesAssi = new ArrayList<>();
    }
}