package hu.sandysoft.cellcalc.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EcoToxin {
    private double izeaAmount = 0;
    private double zeaAmount = 0;

    public boolean helpDivision() {
        return (zeaAmount < 0.15 && izeaAmount == 0) && !(zeaAmount == 0);
    }
    public boolean blockDivision() {
        return  !(izeaAmount == 0 && zeaAmount == 0) || (zeaAmount < 0.15 && izeaAmount == 0);
    }
}
