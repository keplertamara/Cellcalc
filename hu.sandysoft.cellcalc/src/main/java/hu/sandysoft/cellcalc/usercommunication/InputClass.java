package hu.sandysoft.cellcalc.usercommunication;
import hu.sandysoft.cellcalc.info.CellCount;
import hu.sandysoft.cellcalc.info.MeasuredParameters;
import hu.sandysoft.cellcalc.model.EcoToxin;
import hu.sandysoft.cellcalc.simulation.CellCulture;

import javax.swing.*;
import java.awt.*;

public class InputClass {
    private JLabel toxinLabel, minSizeLabel, maxSizeLabel, embryoLabel, daysLabel, izeaLabel, zeaLabel;
    private JTextField toxinField, minSizeField, maxSizeField, daysField, izeaField, zeaField;
    private JCheckBox embryoCheck;
    private JButton submitButton;

    public InputClass() throws Exception {
        JFrame frame = new JFrame("Measured Parameters");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2));

        toxinLabel = new JLabel("Maximum toxin level to divide: ");
        toxinField = new JTextField("20");
        minSizeLabel = new JLabel("Minimum cell size to divide: ");
        minSizeField = new JTextField("5");
        maxSizeLabel = new JLabel("Maximum cell size to divide: ");
        maxSizeField = new JTextField("15");
        embryoLabel = new JLabel("Is from embryo: ");
        embryoCheck = new JCheckBox();
        daysLabel = new JLabel("How many days would you like to simulate? ");
        daysField = new JTextField("8");
        izeaLabel = new JLabel("IZEA concentration (0-1)");
        izeaField = new JTextField("0");
        zeaLabel = new JLabel("ZEA concentration (0-1)");
        zeaField = new JTextField("0");
        submitButton = new JButton("Submit");

        panel.add(toxinLabel);
        panel.add(toxinField);
        panel.add(minSizeLabel);
        panel.add(minSizeField);
        panel.add(maxSizeLabel);
        panel.add(maxSizeField);
        panel.add(embryoLabel);
        panel.add(embryoCheck);
        panel.add(daysLabel);
        panel.add(daysField);
//        panel.add(izeaLabel);
//        panel.add(izeaField);
//        panel.add(zeaLabel);
//        panel.add(zeaField);
        panel.add(submitButton);

        frame.add(panel);
        frame.pack();
        frame.setVisible(true);

        submitButton.addActionListener(e -> {
            try {
                MeasuredParameters.maximumToxinLevelToDivide = Integer.parseInt(toxinField.getText());
                MeasuredParameters.minimumCellSizeToDivide = Double.parseDouble(minSizeField.getText());
                MeasuredParameters.maximumCellsIzeToDivide = Double.parseDouble(maxSizeField.getText());
                MeasuredParameters.isFromEmbryo = embryoCheck.isSelected();
                EcoToxin ecoToxin = new EcoToxin(
                        Double.parseDouble(izeaField.getText()),
                        Double.parseDouble(zeaField.getText()));
                MeasuredParameters.ecoToxin = ecoToxin;
                CellCount.numberOfSimulationDays = Integer.parseInt(daysField.getText());

                frame.dispose();

                JOptionPane.showMessageDialog(frame, "Parameters set successfully");

                CellCulture cellCulture = new CellCulture();
                cellCulture.simulate(CellCount.numberOfSimulationDays);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Please enter valid numbers");
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
    }
}
