package hu.sandysoft.cellcalc;

import hu.sandysoft.cellcalc.info.MeasuredParameters;
import hu.sandysoft.cellcalc.model.Cell;
import hu.sandysoft.cellcalc.model.Location;

import java.util.Random;

public class RandomValues {
    private Random random = new Random();
    public double generateCellSize() {
        return 1 + (MeasuredParameters.maximumCellsIzeToDivide * random.nextDouble())/2;
    }
    public int generateToxinLevel() {
        if(MeasuredParameters.isFromEmbryo) {
            return 0;
        }
        return (int) Math.floor((MeasuredParameters.maximumToxinLevelToDivide * random.nextDouble()));
    }
    public int generateCellCycle() {
        return random.nextInt(7);
    }
    public int generateMaximumToxinLevel() {
        int num = -4 + random.nextInt(8);
        return MeasuredParameters.maximumToxinLevelToDivide + num;
    }
    public Location generateRandomLocation() {
        Location location = new Location(random.nextInt(201), random.nextInt(201));
        return location;
    }
    public double generateValue() {
        return (0.8 + 0.4 *  random.nextDouble()) / 5;
    }

    public int generateStartCellNumber() {
        return 80 + random.nextInt(40);
    }

    public Location generateCloseLocation(Location l) {
        double angle = random.nextDouble() * Math.PI * 2;
        double distance = random.nextDouble() * 4;

        int newX = (int) Math.round(l.getX() + distance * Math.cos(angle));
        int newY = (int) Math.round(l.getY() + distance * Math.sin(angle));

        newX = Math.min(200, Math.max(0, newX));
        newY = Math.min(200, Math.max(0, newY));

        return new Location(newX, newY);
    }

    public Cell generateRandomCell() {
        return new Cell(
                generateCellSize(),
                generateToxinLevel(),
                generateCellCycle(),
                generateMaximumToxinLevel(),
                generateRandomLocation()
        );
    }
}
