package hu.sandysoft.cellcalc.info;

import java.util.ArrayList;
import java.util.List;
/** The program calculates only with one well at a time,
 * and assumes that in every well, the same process happens.*/
public class CellCount {
    public static int numberOfWell;
    public static List<Integer> sumOfAllCellsPerCellCycles = new ArrayList<>();
    public static List<Integer> sumOfCellsInOneWell = new ArrayList<>();
    public static int numberOfSimulationDays;

    public void addActualCellSum(int numberOfCellsInWell) {
        sumOfCellsInOneWell.add(numberOfCellsInWell);
        sumOfAllCellsPerCellCycles.add(numberOfWell*numberOfCellsInWell);
    }
    public void separateCells() {
        numberOfWell *= 2;
    }
}