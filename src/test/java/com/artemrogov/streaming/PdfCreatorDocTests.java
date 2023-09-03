package com.artemrogov.streaming;


import com.artemrogov.streaming.domain.Post;
import com.artemrogov.streaming.dao.PostDataRepository;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.pdmodel.encryption.StandardProtectionPolicy;
import org.apache.pdfbox.pdmodel.font.PDMMType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.awt.*;
import java.io.IOException;
import java.util.List;

import org.vandeseer.easytable.structure.Row;
import org.vandeseer.easytable.structure.Table;
import org.vandeseer.easytable.structure.cell.TextCell;
import org.vandeseer.easytable.TableDrawer;

import static java.awt.Color.*;
import static org.apache.pdfbox.pdmodel.font.PDType1Font.*;
import static org.vandeseer.easytable.settings.HorizontalAlignment.*;

@SpringBootTest
public class PdfCreatorDocTests {

    private final static Color BLUE_DARK = new Color(76, 129, 190);


    @Autowired
    private PostDataRepository postDataRepository;


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

                // add simple userpass

                AccessPermission accessPermission = new AccessPermission();
                /**
                 * AccessPermission, устанавливает права пользователю, может ли он модифицировать документ, извлекать документ
                 */

                accessPermission.setCanPrint(false);
                accessPermission.setCanModify(false);

                /**
                 * Пароль пользователя позволяет пользователю открыть файл с примененными правами доступа,
                 * а пароль владельца не имеет ограничений для файла:
                 */
                StandardProtectionPolicy standardProtectionPolicy
                        = new StandardProtectionPolicy("test01", "test123", accessPermission);

                document.protect(standardProtectionPolicy); // set document protection

            }

            document.save(pathTableFile);
        }
    }


    @Test
    public void createTablePostsTest() throws IOException{

        List<Post> posts = this.postDataRepository.findAll();

        String pathTableFile = "/home/oem/learn/streaming/src/main/resources/test_pdf/FirstMyTable002.pdf";

        try (PDDocument document = new PDDocument()) {

            final PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {

                final Table.TableBuilder tableBuilder = Table.builder()
                        .addColumnsOfWidth(50, 50, 50, 100)
                        .fontSize(8)
                        .font(HELVETICA);

                tableBuilder.addRow(Row.builder()
                        .add(TextCell.builder().text("id").horizontalAlignment(LEFT).borderWidth(1).build())
                        .add(TextCell.builder().text("title").borderWidth(1).build())
                        .add(TextCell.builder().text("active").borderWidth(1).build())
                        .add(TextCell.builder().text("content").borderWidth(1).build())
                        .backgroundColor(BLUE_DARK)
                        .textColor(WHITE)
                        .font(HELVETICA_BOLD)
                        .fontSize(9)
                        .horizontalAlignment(CENTER)
                        .build());

                posts.forEach(p-> tableBuilder.addRow(Row.builder()
                        .add(TextCell.builder().text(String.valueOf(p.getId())).horizontalAlignment(LEFT).borderWidth(1).borderColor(BLACK).build())
                        .add(TextCell.builder().text(String.valueOf(p.getTitle())).horizontalAlignment(LEFT).borderWidth(1).borderColor(BLACK).build())
                        .add(TextCell.builder().text(String.valueOf(p.getActive())).horizontalAlignment(LEFT).borderWidth(1).borderColor(BLACK).build())
                        .add(TextCell.builder().text(String.valueOf(p.getContent())).horizontalAlignment(LEFT).borderWidth(1).borderColor(BLACK).build())
                        .build())
                );

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
