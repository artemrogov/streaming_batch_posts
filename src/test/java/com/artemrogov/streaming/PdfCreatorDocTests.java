package com.artemrogov.streaming;


import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDMMType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.awt.*;
import java.io.IOException;
import org.vandeseer.easytable.structure.Row;
import org.vandeseer.easytable.structure.Table;
import org.vandeseer.easytable.structure.cell.TextCell;

import org.vandeseer.easytable.TableDrawer;
import org.vandeseer.easytable.settings.BorderStyle;
import org.vandeseer.easytable.settings.HorizontalAlignment;

import static java.awt.Color.*;
import static org.apache.pdfbox.pdmodel.font.PDType1Font.*;
import static org.vandeseer.easytable.settings.HorizontalAlignment.*;
import static org.vandeseer.easytable.settings.VerticalAlignment.TOP;

@SpringBootTest
public class PdfCreatorDocTests {


    private final static Color BLUE_DARK = new Color(76, 129, 190);
    private final static Color BLUE_LIGHT_1 = new Color(186, 206, 230);
    private final static Color BLUE_LIGHT_2 = new Color(218, 230, 242);

    private final static Color GRAY_LIGHT_1 = new Color(245, 245, 245);
    private final static Color GRAY_LIGHT_2 = new Color(240, 240, 240);
    private final static Color GRAY_LIGHT_3 = new Color(216, 216, 216);

    private final static Object[][] DATA = new Object[][]{
            {"Whisky", 134.0, 145.0},
            {"Beer",   768.0, 677.0},
            {"Gin",    456.2, 612.0},
            {"Vodka",  302.3, 467.0}
    };


    private final static Object[][] DATA_A = new Object[][]{
            {"Test 01", 10, "simple text 01","simple 100",100,"yes"},
            {"Test 02", 20, "simple text 02","simple 100",1,"yes"},
            {"Test 03", 30, "simple text 03","simple 100",3,"no"},
    };

    @Test
    public void createSimpleDocTest() throws IOException {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();

        document.addPage(page);
        PDPageContentStream contentStream = new PDPageContentStream(document, page);
        PDImageXObject pdLogoImg = PDImageXObject.createFromFile("/home/oem/learn/streaming/src/main/resources/nasa-logo.png",document);
        ///pdLogoImg.setHeight(50);
        //pdLogoImg.setWidth(100);
        contentStream.beginText();
        contentStream.setFont(PDMMType1Font.TIMES_ROMAN,12);
        contentStream.setLeading(14.5f);

        contentStream.newLineAtOffset(25, 725);

        String text1 = "This is an example of adding text to a page in the pdf document";
        String text2 = "as we want like this using the ShowText()  method of the";

        contentStream.showText(text1);
        contentStream.newLine();
        contentStream.showText(text2);
        contentStream.endText();

        contentStream.drawImage(pdLogoImg,70,500);

        contentStream.close();
        document.save("/home/oem/learn/streaming/src/main/resources/test_pdf/FirstTestDoc.pdf");
        document.close();
    }


    @Test
    public void createSimpleTableTest() throws IOException{

        String pathTableFile = "/home/oem/learn/streaming/src/main/resources/test_pdf/FirstTestTable.pdf";

        try (PDDocument document = new PDDocument()) {
            final PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {

                // Build the table
                Table myTable = Table.builder()
                        .addColumnsOfWidth(200, 200)
                        .padding(2)
                        .addRow(Row.builder()
                                .add(TextCell.builder().text("One One").borderWidth(4).borderColorLeft(Color.MAGENTA).backgroundColor(WHITE).build())
                                .add(TextCell.builder().text("One Two").borderWidth(0).backgroundColor(Color.YELLOW).build())
                                .build())
                        .addRow(Row.builder()
                                .padding(10)
                                .add(TextCell.builder().text("Two One").textColor(Color.RED).build())
                                .add(TextCell.builder().text("Two Two")
                                        .borderWidthRight(1f)
                                        .borderStyleRight(BorderStyle.DOTTED)
                                        .horizontalAlignment(RIGHT)
                                        .build())
                                .build())
                        .build();

                // Set up the drawer
                TableDrawer tableDrawer = TableDrawer.builder()
                        .contentStream(contentStream)
                        .startX(20f)
                        .startY(page.getMediaBox().getUpperRightY() - 20f)
                        .table(myTable)
                        .build();

                // And go for it!
                tableDrawer.draw();
            }

            document.save(pathTableFile);
        }
    }


    @Test
    public void testNormalSimpleTable() throws IOException{

        String pathTableFile = "/home/oem/learn/streaming/src/main/resources/test_pdf/FirstNormalSimpleTable.pdf";

        try (PDDocument document = new PDDocument()) {
            final PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {

                final Table.TableBuilder tableBuilder = Table.builder()
                        .addColumnsOfWidth(100, 50, 50, 50)
                        .fontSize(8)
                        .font(HELVETICA)
                        .borderColor(WHITE);

                // Add the header row ...
                tableBuilder.addRow(Row.builder()
                        .add(TextCell.builder().text("Product").horizontalAlignment(LEFT).borderWidth(1).build())
                        .add(TextCell.builder().text("2018").borderWidth(1).build())
                        .add(TextCell.builder().text("2019").borderWidth(1).build())
                        .add(TextCell.builder().text("Total").borderWidth(1).build())
                        .backgroundColor(BLUE_DARK)
                        .textColor(WHITE)
                        .font(HELVETICA_BOLD)
                        .fontSize(9)
                        .horizontalAlignment(CENTER)
                        .build());


                double grandTotal = 0;

                for (int i = 0; i < DATA.length; i++) {
                    final Object[] dataRow = DATA[i];
                    final double total = (double) dataRow[1] + (double) dataRow[2];
                    grandTotal += total;

                    tableBuilder.addRow(Row.builder()
                            .add(TextCell.builder().text(String.valueOf(dataRow[0])).horizontalAlignment(LEFT).borderWidth(1).build())
                            .add(TextCell.builder().text(dataRow[1] + " €").borderWidth(1).build())
                            .add(TextCell.builder().text(dataRow[2] + " €").borderWidth(1).build())
                            .add(TextCell.builder().text(total + " €").borderWidth(1).build())
                            .backgroundColor(i % 2 == 0 ? BLUE_LIGHT_1 : BLUE_LIGHT_2)
                            .horizontalAlignment(RIGHT)
                            .build());
                }

                // Add a final row
                tableBuilder.addRow(Row.builder()
                        .add(TextCell.builder().text("This spans over 3 cells, is right aligned and its text is so long that it even breaks. " +
                                        "Also it shows the grand total in the next cell and furthermore vertical alignment is shown:")
                                .colSpan(3)
                                .lineSpacing(1f)
                                .borderWidthTop(1)
                                .textColor(WHITE)
                                .backgroundColor(BLUE_DARK)
                                .fontSize(6)
                                .font(HELVETICA_OBLIQUE)
                                .borderWidth(1)
                                .build())
                        .add(TextCell.builder().text(grandTotal + " €").backgroundColor(LIGHT_GRAY)
                                .font(HELVETICA_BOLD_OBLIQUE)
                                .verticalAlignment(TOP)
                                .borderWidth(1)
                                .build())
                        .horizontalAlignment(RIGHT)
                        .build());

                TableDrawer tableDrawer = TableDrawer.builder()
                        .contentStream(contentStream)
                        .startX(20f)
                        .startY(page.getMediaBox().getUpperRightY() - 20f)
                        .table(tableBuilder.build())
                        .build();

                // And go for it!
                tableDrawer.draw();
            }

            document.save(pathTableFile);
        }

    }


    @Test
    public void mySimpleTableTest() throws IOException{

        String pathTableFile = "/home/oem/learn/streaming/src/main/resources/test_pdf/FirstMyTable001.pdf";

        try (PDDocument document = new PDDocument()) {

            final PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {

                final Table.TableBuilder tableBuilder = Table.builder()
                        .addColumnsOfWidth(50, 50, 100, 70,50,50)
                        .fontSize(8)
                        .font(HELVETICA);

                // Add the header row ...
                tableBuilder.addRow(Row.builder()
                        .add(TextCell.builder().text("col01").horizontalAlignment(LEFT).borderWidth(1).build())
                        .add(TextCell.builder().text("col02").borderWidth(1).build())
                        .add(TextCell.builder().text("col03").borderWidth(1).build())
                        .add(TextCell.builder().text("col04").borderWidth(1).build())
                        .add(TextCell.builder().text("col05").borderWidth(1).build())
                        .add(TextCell.builder().text("col06").borderWidth(1).build())
                        .backgroundColor(BLUE_DARK)
                        .textColor(WHITE)
                        .font(HELVETICA_BOLD)
                        .fontSize(9)
                        .horizontalAlignment(CENTER)
                        .build());

                for (final Object[] dataRow : DATA_A) {
                    tableBuilder.addRow(Row.builder()
                            .add(TextCell.builder().text(String.valueOf(dataRow[0])).horizontalAlignment(LEFT).borderWidth(1).borderColor(BLACK).build())
                            .add(TextCell.builder().text(String.valueOf(dataRow[1])).borderWidth(1).borderColor(BLACK).build())
                            .add(TextCell.builder().text(String.valueOf(dataRow[2])).borderWidth(1).borderColor(BLACK).build())
                            .add(TextCell.builder().text(String.valueOf(dataRow[3])).borderWidth(1).borderColor(BLACK).build())
                            .add(TextCell.builder().text(String.valueOf(dataRow[4])).borderWidth(1).borderColor(BLACK).build())
                            .add(TextCell.builder().text(String.valueOf(dataRow[5])).borderWidth(1).borderColor(BLACK).build())
                            .build());
                }

                TableDrawer tableDrawer = TableDrawer.builder()
                        .contentStream(contentStream)
                        .startX(80f)
                        .startY(page.getMediaBox().getUpperRightY() - 40f)
                        .table(tableBuilder.build())
                        .build();

                tableDrawer.draw();
            }

            document.save(pathTableFile);
        }
    }
}
