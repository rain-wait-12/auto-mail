package com.auto.mail.utils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
@Component
public class ExcelWriter {


    public void write2OutputStream(List<String> headerList, List<Map<String, Object>> data,
                                   Workbook workbook, String sheetName,
                                   OutputStream outputStream) {
        Sheet sheet = workbook.createSheet(sheetName);

        createHeaderRow(headerList, sheet);

        int rowCount = 0;
        for (Map<String, Object> map : data) {
            Row row = sheet.createRow(++ rowCount);
            setCellValue(map, row);
        }



        try {
            workbook.write(outputStream);
        } catch (IOException exp) {
            exp.printStackTrace();
        }
    }

    private void setCellValue(Map<String, Object> map, Row row) {
        Object[] keys = map.keySet().toArray();
        for (int i = 0; i < keys.length; i++) {
            if(map.get(keys[i]) instanceof Double){
                DecimalFormat df = new DecimalFormat("0.00");
                row.createCell(i)
                        .setCellValue(df.format(((Double)map.get(keys[i])*100))+"%");
            }
            else {
                row.createCell(i)
                        .setCellValue(String.valueOf(map.get(keys[i])));
            }
        }
    }


    private CellStyle createHeaderStyle(Sheet sheet) {
        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();

        Font font = sheet.getWorkbook().createFont();
        font.setColor(IndexedColors.BLACK.getIndex());
        //font.setBold(true);
        font.setFontHeightInPoints((short) 14);

        cellStyle.setFont(font);
        cellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());

       /* cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框*/

        return cellStyle;
    }

    private void createHeaderRow(List<String> headerList, Sheet sheet) {

        Row row = sheet.createRow(0);
        CellStyle cellStyle = createHeaderStyle(sheet);

        //设置表头单元格颜色
        cellStyle.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
        //cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        Cell cell = row.createCell((short) 1);
        cell.setCellStyle(cellStyle);

            for (int i = 0; i < headerList.size(); i++) {
                Cell cellTitle = row.createCell(i);
                cellTitle.setCellStyle(cellStyle);
                cellTitle.setCellValue(headerList.get(i));
            }
    }

    private Workbook getWorkbook(String excelFilePath) {
        Workbook workbook;

        if (excelFilePath.endsWith("xlsx")) {
            workbook = new XSSFWorkbook();
        } else if (excelFilePath.endsWith("xls")) {
            workbook = new HSSFWorkbook();
        } else {
            throw new IllegalArgumentException("The specified file is not Excel file");
        }

        return workbook;
    }



}
