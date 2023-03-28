package hu.sandysoft.cellcalc.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Random;

@AllArgsConstructor
@Getter
public class Location {
    int x,y;

    public void move() {
        Random rand = new Random();

        x += rand.nextInt(21) - 10;
        y += rand.nextInt(21) - 10;

        x = Math.min(200, Math.max(0, x));
        y = Math.min(200, Math.max(0, y));
    }

    public boolean isClose(Location other) {
        return Math.sqrt(Math.pow(other.x - x, 2) + Math.pow(other.y - y, 2)) <= 7;
    }
}
