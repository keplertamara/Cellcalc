package hu.sandysoft.cellcalc.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
public class MeasuredParameters {
    public static int maximumToxinLevelToDivide = -1;
    public static double minimumCellSizeToDivide = -1;
    public static double maximumCellsIzeToDivide = -1;
    public static boolean resetWhenDivide = false;
    public static boolean isFromEmbryo = false;
}
