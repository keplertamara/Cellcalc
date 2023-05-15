package hu.sandysoft.cellcalc.info;

import hu.sandysoft.cellcalc.model.EcoToxin;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
/**
 * már eleve indulhasson toxinnal
 * 2 féle toxin (t2-izea, t2-zea)
 * zea segít az osztódásban , ha kicsi van
 * az izea gátolja
 * együtt vannak ugyanabban a koncentrációban, akkor kicsit jobban gátol mint önmagában
 *
 * Áttekintő ábra a programhoz!
 * */
public class MeasuredParameters {
    public static int maximumToxinLevelToDivide = -1;
    public static double minimumCellSizeToDivide = -1;
    public static double maximumCellsIzeToDivide = -1;
    public static boolean resetWhenDivide = false;
    public static boolean isFromEmbryo = false;
    public static EcoToxin ecoToxin = new EcoToxin(0,0);
}
