/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.contratos.util;

import java.text.SimpleDateFormat;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;

/**
 *
 * @author mi_bo
 */
public class ExcelUtil {

    public static String getCellValue(Row row, int cellIndex) {
        String value = "";
        Cell cell = row.getCell(cellIndex);
        if (cell != null) {
            switch (cell.getCellType()) {
                case STRING:
                    value = cell.getStringCellValue();
                    break;
                case NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        value = sdf.format(cell.getDateCellValue());
                    } else {
                        value = String.valueOf(cell.getNumericCellValue());
                    }
                    break;
                case BOOLEAN:
                    value = String.valueOf(cell.getBooleanCellValue());
                    break;
                case FORMULA:
                    try {
                        value = String.valueOf(cell.getNumericCellValue());
                    } catch (IllegalStateException e) {
                        value = ""; // Retorna string vazia se for erro
                    }
                    break;
                case ERROR:
                    value = ""; // Trata célula de erro como string vazia
                    break;
                default:
                    value = "";
            }
        }
        return value;
    }
}
