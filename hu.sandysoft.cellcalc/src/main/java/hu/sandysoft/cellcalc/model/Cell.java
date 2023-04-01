package hu.sandysoft.cellcalc.model;


import hu.sandysoft.cellcalc.info.MeasuredParameters;
import lombok.AllArgsConstructor;
import lombok.Data;

import static java.lang.Math.max;

@Data
@AllArgsConstructor
public class Cell {
    private double cellSize;
    private int toxinLevel;
    private int cellCycle;
    private final int maximumToxinLevelToDivide;
    private Location location;

    public void increaseToxinLevel(int i) {
        toxinLevel += i;
    }

    public void decreaseToxinLevel(int i) {
        toxinLevel = max(0, toxinLevel-i);
    }
// ne tudjon toxint leadni, ha toxint adunk a médiumhoz, ez osztódáskor feleződik
    //a sejt saját toxinjai más dolog
    public void stepCellCycle() {
        if(cellCycle < 8) {
            cellCycle++;
        }
        else {
            cellCycle = 1;
        }
    }

    public void feeding(double amount) {
        cellSize += amount;
    }

    public boolean canDivide() {
        return toxinLevel < maximumToxinLevelToDivide &&
                CellCycle.M == calculateState() &&
                cellSize < MeasuredParameters.maximumCellsIzeToDivide &&
                cellSize > MeasuredParameters.minimumCellSizeToDivide;
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
            cellSize = cellCycle/2; // teljesen ketté osztódik, azonos utódsejtek!!
            toxinLevel = toxinLevel/2; //1,5 nap alatt duplázódnak, akár napi 2 osztódás
        }
        cellCycle = 0;
    }


    public CellCycle calculateState() {
        if(maximumToxinLevelToDivide * 1.4 < toxinLevel) {
            return CellCycle.DEAD;
        } //legyenek g0 fázisban is
        if(cellCycle == 0) {
            return CellCycle.G0;
        }
        if(cellCycle < 2) {
            return CellCycle.G1; //minél magasabb a toxin szint, annál hosszabb legyen
        }
        if(cellCycle < 3) {
            return CellCycle.S;
        }
        if(cellCycle < 6) {
            return CellCycle.G2;
        }
        return CellCycle.M;
    }
}
