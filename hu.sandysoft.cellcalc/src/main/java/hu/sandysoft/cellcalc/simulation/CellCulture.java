package hu.sandysoft.cellcalc.simulation;

import hu.sandysoft.cellcalc.RandomValues;
import hu.sandysoft.cellcalc.info.CellCount;
import hu.sandysoft.cellcalc.info.MeasuredParameters;
import hu.sandysoft.cellcalc.model.*;
import hu.sandysoft.cellcalc.usercommunication.DiagramDrawer;

import java.util.ArrayList;
import java.util.List;

public class CellCulture {

    private final List<Cell> cellList = new ArrayList<>();
    private final List<Toxin> toxinList = new ArrayList<>();
    private final List<Nutrient> nutrientList = new ArrayList<>();
    private RandomValues randomValues = new RandomValues();
    private int numberOfCyclesPassed = 1;
    private List<Cell> dividingCells = new ArrayList<>();

    public void simulate(int daysOfSimulation) {
        setUpCellCulture();
        for(int i = 0; i < Math.floor(1 + daysOfSimulation * 1.6 * 7); i++) {
            stepOneCycle();
        }
        outputToUser();
    }

    private void setUpCellCulture() {
        CellCount.numberOfWell = MeasuredParameters.isFromEmbryo ? 1 : 50;
        addCells();
        changeMedium();
    }
    private void stepOneCycle() {
        if(numberOfCyclesPassed % 22 == 0) {
            changeMedium();
            if(numberOfCyclesPassed % 132 == 0) {
                separateCells();
            }
        }
        getNextStateOfCells();
        saveCellNumber();
    }
    private void outputToUser() {
        System.out.println("Simulation is over");
        DiagramDrawer diagramDrawer = new DiagramDrawer();
        diagramDrawer.drawBarChart();
    }

    private void addCells() {
        int n = randomValues.generateStartCellNumber();
        for(int i = 0; i < n; i++) {
            cellList.add(randomValues.generateRandomCell());
        }
    }
    private void changeMedium() {
        nutrientList.clear();
        for(int i = 0; i <  7*1500;i++) {
            generateNutrient();
        }
        System.out.println("Medium Changed");
    }
    private void separateCells() {
        CellCount.numberOfWell *= 2;
        cellList.subList(0, cellList.size()/2).clear();
        removeDeadCells();

        System.out.println("We got separated");
    }
    private void getNextStateOfCells() {
        cellList.forEach(cell -> vitalProcesses(cell));
        makeChildCells();
        numberOfCyclesPassed++;
    }
    private void saveCellNumber() {
        CellCount.sumOfCellsInOneWell.add(cellList.size());
        CellCount.sumOfAllCellsPerCellCycles.add(CellCount.numberOfWell * cellList.size());
        System.out.println(numberOfCyclesPassed + ". cycle: ");
        System.out.println(cellList.size());
    }

    private void generateNutrient() {
        nutrientList.add(new Nutrient(
                         randomValues.generateRandomLocation(),
                         randomValues.generateValue()
        ));
    }
    private void removeDeadCells() {
        cellList.removeIf(c -> c.calculateState() == CellCycle.DEAD);
    }
    private void vitalProcesses(Cell cell) {
        if(cell.calculateState() != CellCycle.DEAD) {
            eat(cell);
            toxinProcess(cell);
            cell.stepCellCycle();
            divideIfPossible(cell);
        }
    }
    private void makeChildCells() {
        while(!dividingCells.isEmpty()) {
            cellList.add( new Cell(
                    dividingCells.get(0).getCellSize() / 2,
                    dividingCells.get(0).getToxinLevel() / 2,
                    0,
                    randomValues.generateMaximumToxinLevel(),
                    randomValues.generateCloseLocation(dividingCells.get(0).getLocation())
            ));
            dividingCells.remove(0);
        }
    }

    private void divideIfPossible(Cell cell) {
        if(cell.canDivide()) {
            dividingCells.add(cell);
            cell.divide();
        }
    }

    private void eat(Cell cell) {
        Nutrient food = findNutrient(cell);
        cell.feeding(food.getValue());
        nutrientList.removeIf(n -> n == food);
    }
    private void toxinProcess(Cell cell) {
        Toxin toxin = findToxin(cell);
        if(toxin.getLocation().getX() != -1) {
            cell.increaseToxinLevel(1);
        }
        else {
            cell.decreaseToxinLevel(1);
        }
        toxinList.add(new Toxin(randomValues.generateCloseLocation(cell.getLocation())));
        toxinList.removeIf(t -> t == toxin);
    }

    private Toxin findToxin(Cell cell) {
        if(isToxic(cell)) {
            return toxinList.parallelStream()
                            .filter(t -> t.getLocation().isClose(cell.getLocation()))
                            .findFirst()
                            .get();
        }
        return new Toxin(new Location(-1,-1));
    }
    private Nutrient findNutrient(Cell cell) {
        if(canEat(cell)) {
            return nutrientList.parallelStream()
                    .filter(n -> n.getLocation().isClose(cell.getLocation()))
                    .findFirst()
                    .get();
        }
        return new Nutrient(new Location(0,0), 0);
    }

    private boolean isToxic(Cell cell) {
        return toxinList.parallelStream()
                .anyMatch(t -> t.getLocation().isClose(cell.getLocation()));
    }

    private boolean canEat(Cell cell) {
        return nutrientList.parallelStream()
                .anyMatch(n -> n.getLocation().isClose(cell.getLocation()));
    }
}