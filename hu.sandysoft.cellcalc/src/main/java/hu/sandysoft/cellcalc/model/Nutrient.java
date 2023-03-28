package hu.sandysoft.cellcalc.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Nutrient {
    private Location location;
    private double value;
}
