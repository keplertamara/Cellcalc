package hu.sandysoft.cellcalc.usercommunication;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import hu.sandysoft.cellcalc.info.CellCount;
import hu.sandysoft.cellcalc.model.CellCycle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
public class OutputToExcel {

    public void output() {
        // Create a new workbook and sheet
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Sheet1");

        // Write data to the sheet
        int rowNum = 1;
        int colNum1 = 0;

        Row row1 = sheet.createRow(rowNum++);
        Cell cell0 = row1.createCell(colNum1++);
        cell0.setCellValue("Number of the cycle:");
        Cell cell1 = row1.createCell(colNum1++);
        cell1.setCellValue("Number of green cells:");
        Cell cell2 = row1.createCell(colNum1++);
        cell2.setCellValue("Number of red cells:");
        Cell cell3 = row1.createCell(colNum1++);
        cell3.setCellValue("Number of orange cells:");
        Cell cell4 = row1.createCell(colNum1++);
        cell4.setCellValue("Number of all cells:");
        Cell cell5 = row1.createCell(colNum1++);
        cell5.setCellValue("Number of dead cells:");

        for (List<hu.sandysoft.cellcalc.model.Cell> rowData : CellCount.petriHistory) {
            Row row = sheet.createRow(rowNum++);
            int colNum = 0;
            Cell cel0 = row.createCell(colNum++);
            cel0.setCellValue(rowNum -2);
            Cell cel1 = row.createCell(colNum++);
            cel1.setCellValue(rowData.stream()
                                        .filter(c -> c.getState() == CellCycle.G2 || c.getState() == CellCycle.M || c.getState() == CellCycle.G0)
                                        .count());
            Cell cel2 = row.createCell(colNum++);
            cel2.setCellValue(rowData.stream()
                                        .filter(c -> c.getState() == CellCycle.G1)
                                        .count());
            Cell cel3 = row.createCell(colNum++);
            cel3.setCellValue(rowData.stream()
                                        .filter(c -> c.getState() == CellCycle.S)
                                        .count());
            Cell cel4 = row.createCell(colNum++);
            cel4.setCellValue(rowData.stream()
                    .filter(c -> c.getState() != CellCycle.DEAD)
                    .count());
            Cell cel5 = row.createCell(colNum++);
            cel5.setCellValue(rowData.stream()
                    .filter(c -> c.getState() == CellCycle.DEAD)
                    .count());
        }

        // Save the workbook to a file
        try (FileOutputStream outputStream = new FileOutputStream("output.xlsx")) {
            workbook.write(outputStream);
            System.out.println("Excel file created successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

