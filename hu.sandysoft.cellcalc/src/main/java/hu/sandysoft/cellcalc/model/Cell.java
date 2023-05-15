package hu.sandysoft.cellcalc.model;


import hu.sandysoft.cellcalc.info.MeasuredParameters;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static java.lang.Math.max;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cell {
    private double cellSize;
    private int toxinLevel;
    private int cellCycle;
    private int maximumToxinLevelToDivide;
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
            cellCycle = 0;
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
            cellSize = cellSize/2; // teljesen ketté osztódik, azonos utódsejtek!!
            toxinLevel = toxinLevel/2; //1,5 nap alatt duplázódnak, akár napi 2 osztódás
        }
        cellCycle = 0;
    }


    public CellCycle calculateState() { // 20 % ot the time they are in G0
        if(maximumToxinLevelToDivide * 1.4 < toxinLevel) {
            return CellCycle.DEAD;
        } //legyenek g0 fázisban is
        if(cellCycle < 2) { // gray 0,1
            return CellCycle.G0;
        }
        if(cellCycle < 5) { //red 2,3,4,
            return CellCycle.G1; //minél magasabb a toxin szint, annál hosszabb legyen
        }
        if(cellCycle < 6) { // orange
            return CellCycle.S;
        }
        if(cellCycle < 7) { // green
            return CellCycle.G2;
        }
        return CellCycle.M; //green
    }

    public void move() {
        location.move();
    }
}
