package com.artemrogov.streaming;


import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDMMType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.vandeseer.easytable.structure.cell.paragraph.ParagraphCell;

import java.io.File;
import java.io.IOException;

@SpringBootTest
public class PdfCreatorDocTests {


    @Test
    public void createSimpleDocTest() throws IOException {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();

        document.addPage(page);
        PDPageContentStream contentStream = new PDPageContentStream(document, page);
        PDImageXObject pdLogoImg = PDImageXObject.createFromFile("/home/oem/learn/streaming/src/main/resources/nasa-logo.png",document);

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

        contentStream.drawImage(pdLogoImg,70,250);

        contentStream.close();
        document.save("/home/oem/learn/streaming/src/main/resources/test_pdf/FirstTestDoc.pdf");
        document.close();
    }
}
