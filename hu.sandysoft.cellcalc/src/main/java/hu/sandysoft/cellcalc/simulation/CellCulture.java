package hu.sandysoft.cellcalc.simulation;

import hu.sandysoft.cellcalc.model.*;

import java.util.List;
import java.util.Random;

public class CellCulture {

    private List<Cell> cellList;
    private List<Toxin> toxinList;
    private List<Nutrient> nutrientList;

    public CellCulture(int n) {
        for(int i =0; i<n; i++) {
            generateRandomCell();
        }
        nutrientRefill();
    }

    private void generateRandomCell() {
        cellList.add(new Cell(
                generateCellSize(),
                generateToxinLevel(),
                generateCellCycle(),
                generateMaximumToxinLevel(),
                MeasuredParameters.minimumCellSizeToDivide,
                MeasuredParameters.maximumCellsIzeToDivide
        ));
    }

    private int generateMaximumToxinLevel() {
        Random rand = new Random();
        int num = -4 + 8 *rand.nextInt();
        return MeasuredParameters.maximumToxinLevelToDivide + num;
    }
    private void nutrientRefill() {
        nutrientList.clear();
        /** sejtciklus/3 nap * sejtciklus állomások * max sejtek száma a petriben*/
        for(int i = 0; i < 5*7*200; i++) {
            generateNutrient();
        }
    }
    private int generateCellCycle() {
        Random rand = new Random();
        return 7 * rand.nextInt();
    }
    private double generateCellSize() {
        Random rand = new Random();
        return 1 + MeasuredParameters.maximumCellsIzeToDivide * rand.nextDouble();
    }

    private int generateToxinLevel() {
        if(MeasuredParameters.isFromEmbryo) {
            return 0;
        }
        Random rand = new Random();
        return MeasuredParameters.maximumToxinLevelToDivide * rand.nextInt() / 2;
    }

    private void generateNutrient() {
        nutrientList.add(new Nutrient(
                         generateRandomLocation(),
                         generateValue()
        ));
    }

    private Location generateRandomLocation() {
        Random rand = new Random();
        Location location = new Location(rand.nextInt(201), rand.nextInt(201));
        return location;
    }

    private double generateValue() {
        Random rand = new Random();
        return 0.8 + (1.2 - 0.8) * rand.nextDouble();
    }

}
