package com.artemrogov.streaming;

import com.artemrogov.streaming.entities.Post;
import com.artemrogov.streaming.repositories.PostDataRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.IndexedColorMap;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@SpringBootTest
public class ExcelCreatorTests {



    private static String FILE_NAME = "temp.xlsx";
    private String fileLocation;


    @Autowired
    private PostDataRepository postDataRepository;

    @Test
    public void createSimpleWorksheetTest() throws IOException {
        File sFileSheet = new File(".");
        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        sheet.setColumnWidth(0,6000);
        sheet.setColumnWidth(0,4000);

        Row header = sheet.createRow(0);

        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        XSSFFont font = workbook.createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 16);
        font.setBold(true);
        headerStyle.setFont(font);

        Cell headerCell = header.createCell(0);

        headerCell.setCellValue("Name");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(1);
        headerCell.setCellValue("Age");

        CellStyle style = workbook.createCellStyle();
        style.setWrapText(true);

        Row row = sheet.createRow(2);
        Cell cell = row.createCell(0);
        cell.setCellValue("John Smith");
        cell.setCellStyle(style);

        cell = row.createCell(1);
        cell.setCellValue(20);
        cell.setCellStyle(style);

        String path = sFileSheet.getAbsolutePath();
        String fileLocation = path.substring(0, path.length() - 1) + "temp.xlsx";

        FileOutputStream outputStream = new FileOutputStream(fileLocation);
        workbook.write(outputStream);
        workbook.close();
    }




    @Test
    public void testPostExcel() throws IOException {

        List<Post> posts = this.postDataRepository.findAll();

        File sFileSheet = new File(".");
        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        sheet.setColumnWidth(0,6000);
        sheet.setColumnWidth(0,4000);

        Row header = sheet.createRow(0);

        CellStyle headerStyle = workbook.createCellStyle();


        headerStyle.setBorderLeft(BorderStyle.HAIR);
        headerStyle.setBorderRight(BorderStyle.HAIR);
        headerStyle.setBorderBottom(BorderStyle.HAIR);

        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());

        headerStyle.setFillPattern(FillPatternType.BIG_SPOTS);

        XSSFFont font = workbook.createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 9);
        font.setBold(true);
        headerStyle.setFont(font);

        Cell headerCell = header.createCell(0);
        headerCell.setCellValue("id");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(1);
        headerCell.setCellStyle(headerStyle);
        headerCell.setCellValue("title");


        headerCell = header.createCell(2);
        headerCell.setCellStyle(headerStyle);
        headerCell.setCellValue("content");

        CellStyle style = workbook.createCellStyle();
        style.setWrapText(true);

        style.setBorderLeft(BorderStyle.HAIR);
        style.setBorderRight(BorderStyle.HAIR);
        style.setBorderBottom(BorderStyle.HAIR);


        int rownum = 1;

        for (Post p : posts){

            Row row = sheet.createRow(rownum++);
            Cell cell = row.createCell(0);
            cell.setCellValue(p.getId());
            cell.setCellStyle(style);

            cell = row.createCell(1);
            cell.setCellValue(p.getTitle());
            cell.setCellStyle(style);

            cell = row.createCell(2);
            cell.setCellValue(p.getContent());
            cell.setCellStyle(style);
        }


        String path = sFileSheet.getAbsolutePath();
        String fileLocation = path.substring(0, path.length() - 1) + "posts2.xlsx";

        FileOutputStream outputStream = new FileOutputStream(fileLocation);
        workbook.write(outputStream);
        workbook.close();
    }
}
