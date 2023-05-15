package hu.sandysoft.cellcalc.usercommunication;

import hu.sandysoft.cellcalc.info.CellCount;
import hu.sandysoft.cellcalc.model.Cell;
import hu.sandysoft.cellcalc.model.CellCycle;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class TimeLapse extends JPanel {

    private static final int CELL_SIZE = 5;
    private static final int WIDTH = 200;
    private static final int HEIGHT = 200;
    private List<Cell> cellList;

    public TimeLapse() {
        this.cellList = CellCount.petriHistory.get(0);
        setPreferredSize(new Dimension(WIDTH * 10, HEIGHT * 10));
    }

    @Override
    protected void paintComponent(Graphics g) {
        //super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(Color.WHITE);
        g2d.fillRect(0,0,getWidth(), getHeight());
        g2d.setBackground(Color.WHITE);
        cellList.forEach(cell -> {
                    if (cell.getState() == CellCycle.S) {
                        g2d.setColor(Color.ORANGE);
                    } else if (cell.getState() == CellCycle.G0 || cell.getState() == CellCycle.G2 || cell.getState() == CellCycle.M) {
                        g2d.setColor(Color.GREEN);
                    } else if (cell.getState() == CellCycle.G1) {
                        g2d.setColor(Color.RED);
                    } else if (cell.getState() == CellCycle.DEAD) {
                        g2d.setColor(Color.BLACK);
                    }
                    g2d.fillOval(cell.getLocation().getX() * 10,
                             cell.getLocation().getY() * 10,
                            (int) (cell.getCellSize() * 3),
                            (int) (cell.getCellSize()) * 3);
                });
    }

//    public void start() {
//        // Initialize cell list
//        List<Cell> cellList1 = CellCount.petriHistory.get(0);
//
//        // Create and set up frame
//        JFrame frame = new JFrame("Cell Simulation");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.getContentPane().add(new TimeLapse());
//        frame.pack();
//        frame.setLocationRelativeTo(null);
//        frame.setVisible(true);
//
//        // Animate the cells
//        while (CellCount.petriHistory.size() > 0) {
//            // Update cell list
//            cellList1 = CellCount.petriHistory.get(0);
//            System.out.println("petriHistory has: " + CellCount.petriHistory.get(0).size());
//
//            // Repaint panel
//            TimeLapse timeLapse = (TimeLapse) frame.getContentPane().getComponent(0);
//            timeLapse.cellList = cellList1;
//            timeLapse.repaint();
//
//            // Wait for a short time before updating again
//            try {
//                Thread.sleep(100);
//            } catch (InterruptedException ex) {}
//            System.out.println("BEFORE: " + CellCount.petriHistory.size());
//            CellCount.petriHistory.remove(0);
//            System.out.println("I tried to remove a list element. List size is: " + CellCount.petriHistory.size());
//        }
//    }
public void start() {
    // Initialize cell list
    List<Cell> cellList1 = CellCount.petriHistory.get(0);

    // Create and set up frame
    JFrame frame = new JFrame("Cell Simulation");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    TimeLapse timeLapse = new TimeLapse();
    frame.getContentPane().add(timeLapse);
    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);

    // Animate the cells using a timer
    int delay = 1000; // in milliseconds
    new javax.swing.Timer(delay, new ActionListener() {
        int index = 0;
        @Override
        public void actionPerformed(ActionEvent e) {
            if (index >= CellCount.petriHistory.size()) {
                ((javax.swing.Timer) e.getSource()).stop();
                return;
            }
            // Update cell list
            List<Cell> cellList1 = CellCount.petriHistory.get(index);
            System.out.println("petriHistory has: " + cellList1.size());

            // Repaint panel
            timeLapse.cellList = cellList1;
            timeLapse.repaint();

            index++;
        }
    }).start();
}

}
