package hu.sandysoft.cellcalc.model;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Cell {
    private double cellSize;
    private int toxinLevel;
    private int cellCycle;
    private final int maximumToxinLevelToDivide;
    private final double minimumCellSizeToDivide;
    private final double maximumCellsIzeToDivide;

    public void increaseToxinLevel(int i) {
        toxinLevel += i;
    }

    public void decreaseToxinLevel(int i) {
        toxinLevel -= i;
    }

    public void increaseCellSize() {

        cellSize++;
    }

    public CellCycle getState() {
        return calculateState();
    }

    public void divide() {
        if (MeasuredParameters.resetWhenDivide) {
            cellSize = 1;
            toxinLevel = 0;
        }
        else {
            cellSize = cellCycle/2;
            toxinLevel = toxinLevel/2;
        }
        cellCycle = 1;
    }


    private CellCycle calculateState() {
        if(cellCycle == 0) {
            return CellCycle.G0;
        }
        if(cellCycle < 3) {
            return CellCycle.G1;
        }
        if(cellCycle < 5) {
            return CellCycle.S;
        }
        if(cellCycle < 7) {
            return CellCycle.G2;
        }
        return CellCycle.M;
    }
}
