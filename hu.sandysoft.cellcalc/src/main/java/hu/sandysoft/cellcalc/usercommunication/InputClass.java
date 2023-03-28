package hu.sandysoft.cellcalc.usercommunication;
import hu.sandysoft.cellcalc.info.CellCount;
import hu.sandysoft.cellcalc.info.MeasuredParameters;
import hu.sandysoft.cellcalc.simulation.CellCulture;

import javax.swing.*;
import java.awt.*;

public class InputClass {
    private JLabel toxinLabel, minSizeLabel, maxSizeLabel, resetLabel, embryoLabel, daysLabel;
    private JTextField toxinField, minSizeField, maxSizeField, daysField;
    private JCheckBox resetCheck, embryoCheck;
    private JButton submitButton;

    public InputClass() {
        JFrame frame = new JFrame("Measured Parameters");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 2));

        toxinLabel = new JLabel("Maximum toxin level to divide: ");
        toxinField = new JTextField();
        minSizeLabel = new JLabel("Minimum cell size to divide: ");
        minSizeField = new JTextField();
        maxSizeLabel = new JLabel("Maximum cell size to divide: ");
        maxSizeField = new JTextField();
        resetLabel = new JLabel("Reborn after divide: ");
        resetCheck = new JCheckBox();
        embryoLabel = new JLabel("Is from embryo: ");
        embryoCheck = new JCheckBox();
        daysLabel = new JLabel("How many days would you like to simulate? ");
        daysField = new JTextField();
        submitButton = new JButton("Submit");

        panel.add(toxinLabel);
        panel.add(toxinField);
        panel.add(minSizeLabel);
        panel.add(minSizeField);
        panel.add(maxSizeLabel);
        panel.add(maxSizeField);
        panel.add(resetLabel);
        panel.add(resetCheck);
        panel.add(embryoLabel);
        panel.add(embryoCheck);
        panel.add(daysLabel);
        panel.add(daysField);
        panel.add(submitButton);

        frame.add(panel);
        frame.pack();
        frame.setVisible(true);

        submitButton.addActionListener(e -> {
            try {
                MeasuredParameters.maximumToxinLevelToDivide = Integer.parseInt(toxinField.getText());
                MeasuredParameters.minimumCellSizeToDivide = Double.parseDouble(minSizeField.getText());
                MeasuredParameters.maximumCellsIzeToDivide = Double.parseDouble(maxSizeField.getText());
                MeasuredParameters.resetWhenDivide = resetCheck.isSelected();
                MeasuredParameters.isFromEmbryo = embryoCheck.isSelected();
                CellCount.numberOfSimulationDays = Integer.parseInt(daysField.getText());

                frame.dispose();

                JOptionPane.showMessageDialog(frame, "Parameters set successfully");

                CellCulture cellCulture = new CellCulture();
                cellCulture.simulate(CellCount.numberOfSimulationDays);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Please enter valid numbers");
            }
        });
    }
}
