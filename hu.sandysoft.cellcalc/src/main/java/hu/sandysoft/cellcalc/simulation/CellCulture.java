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
        System.out.println("Simulation is over");
        DiagramDrawer diagramDrawer = new DiagramDrawer();
        diagramDrawer.drawBarChart();
    }

    private void setUpCellCulture() {
        CellCount.numberOfWell = MeasuredParameters.isFromEmbryo ? 1 : 50;
        for(int i =0; i<100; i++) {
            generateRandomCell();
        }
        changeMedium();
    }
    private void generateRandomCell() {
        cellList.add(new Cell(
                randomValues.generateCellSize(),
                randomValues.generateToxinLevel(),
                randomValues.generateCellCycle(),
                randomValues.generateMaximumToxinLevel(),
                randomValues.generateRandomLocation()
        ));
    }
    private void changeMedium() {
        nutrientList.clear();
        /** number of cell cycles in 3 days * cell cycle stages * max cell count in the petri*/
        for(int i = 0; i < 5*7*200; i++) {
            generateNutrient();
        }
        removeDeadCells();
        System.out.println("Medium Changed");
    }
    private void stepOneCycle() {
        if(numberOfCyclesPassed % 33 == 0) {
            changeMedium();
        }
        if(numberOfCyclesPassed % 78 == 0) {
            changeMedium();
            separateCells();
        }
        getNextStateOfCells();
        numberOfCyclesPassed++;

        saveAliveCellNumber();
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
    private void getNextStateOfCells() {
        cellList.forEach(this::divideIfPossible);
        makeChildCells();
        something();
    }

    private void saveAliveCellNumber() {
        int count =  countAliveCells();
        CellCount.sumOfCellsInOneWell.add(count);
        CellCount.sumOfAllCellsPerCellCycles.add(CellCount.numberOfWell * count);
        System.out.println(numberOfCyclesPassed + ". cycle: ");
        System.out.println(CellCount.numberOfWell * count);
    }

    private int countAliveCells() {
        return (int) cellList.stream()
                .filter(c->CellCycle.DEAD != c.calculateState())
                .count();
    }

    private void separateCells() {
        CellCount.numberOfWell *= 2;
        cellList.subList(0, cellList.size()/2).clear();
        System.out.println("We got separated");
    }
    private void divideIfPossible(Cell cell) {
        if(cell.canDivide()) {
            cell.divide();
            dividingCells.add(cell);
        }
    }
    private void makeChildCells() {
        while(!dividingCells.isEmpty()) {
            cellList.add( new Cell(
                    1,
                    0,
                    0,
                    randomValues.generateMaximumToxinLevel(),
                    randomValues.generateCloseLocation(dividingCells.get(0).getLocation())
            ));
            dividingCells.remove(0);
        }
    }
    private void something() {
        cellList.forEach(cell -> {
            eat(cell);
            toxinProcess(cell);
            cell.stepCellCycle();
        });
    }
    private void eat(Cell cell) {
        Nutrient food = findFirstNutrient(cell);
        cell.feeding(food.getValue());
        nutrientList.removeIf(n -> n == food);
    }
    private void toxinProcess(Cell cell) {
        if(getToxinAmountAroundCell(cell) > 0) {
            cell.increaseToxinLevel(1);
        }
        else {
            cell.decreaseToxinLevel(1);
        }
        toxinList.add(new Toxin(randomValues.generateCloseLocation(cell.getLocation())));
    }
    private int getToxinAmountAroundCell(Cell cell) {
        return (int) toxinList.stream()
                              .filter(t-> t.getLocation().isClose(cell.getLocation()))
                              .count();
    }
    private Nutrient findFirstNutrient(Cell cell) {
        if(cellCanEat(cell)) {
            return nutrientList.stream()
                    .filter(n -> n.getLocation().isClose(cell.getLocation()))
                    .findFirst()
                    .get();
        }
        return new Nutrient(new Location(0,0), 0);
    }

    private boolean cellCanEat(Cell cell) {
        return nutrientList.stream()
                .anyMatch(n -> n.getLocation().isClose(cell.getLocation()));
    }
}
