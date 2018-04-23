package Servlet;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * Created by L0463664 on 11/08/2017.
 */

@CrossOrigin
@RestController
@EnableWebMvc

public class SandBox {
    private String description;

    @GetMapping(value = "/sandbox")
    @ResponseBody
    public String getAllStudies(HttpServletRequest request, HttpServletResponse response) {

        this.description = "MODULE DE GENERATION DE TEMPLATE DE MEMO au format docx";


        try {
            runSandBox();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }


        return this.description;
    }

    public static void runSandBox() throws IOException, InvalidFormatException {
        XWPFDocument doc= new XWPFDocument();
        // the body content
        XWPFParagraph paragraph = doc.createParagraph();
        XWPFRun run=paragraph.createRun();
        run.setText("Exploration & Production");
        run.setBold(true);

        paragraph = doc.createParagraph();
        run= paragraph.createRun();
        run.setText("MTG/AVO/IMA");

        paragraph = doc.createParagraph();
        run= paragraph.createRun();
        run.setText("13/10/2017");
        run.addBreak();
        run.addBreak();
        run.setText("_____________________________________________________________________________________");

        paragraph = doc.createParagraph();
        run= paragraph.createRun();
        run.setText("Destinataire(To) : "+"SALUUUUUUUU");
        run.addTab();
        run.addTab();
        run.setText("Expéditeur(From) : "+"SALUUUUUUUU");

        paragraph = doc.createParagraph();
        run= paragraph.createRun();
        run.setText("Copie(Copy) : ");
        run.setText("_____________________________________________________________________________________");

        paragraph = doc.createParagraph();
        run= paragraph.createRun();
        run.setText("Nom du projet (Project name) : "+"Etude libelle_loooooooooooooooooooooooooooooooooooooong ");
        run.setText("_____________________________________________________________________________________");

        paragraph = doc.createParagraph();
        run= paragraph.createRun();
        run.setText("N°PDT :"+" ");
        run.addTab();
        run.addTab();
        run.setText("N° Chrono : "+" ");
        run.addTab();
        run.addTab();
        run.setText("Confidentialité(Confidentiality) :"+" ");
        run.setText("_____________________________________________________________________________________");

        paragraph = doc.createParagraph();
        run= paragraph.createRun();
        run.setText("Vérifié par(Verified by) :"+" ");
        run.addTab();
        run.addTab();
        run.addTab();
        run.addTab();
        run.setText("Validé par(Validated by) : "+"");
        run.setText("_____________________________________________________________________________________");

        paragraph = doc.createParagraph();
        run= paragraph.createRun();
        run.setText("Résumé(Executive summary) : "+" ");
        run.addCarriageReturn();                 //separate previous text from break
        run.addBreak(BreakType.PAGE);
        //run.addBreak(BreakType.WORD_WRAPPING);
        // create header start
        CTSectPr sectPr = doc.getDocument().getBody().addNewSectPr();
        XWPFHeaderFooterPolicy headerFooterPolicy = new XWPFHeaderFooterPolicy(doc, sectPr);

        XWPFHeader header = headerFooterPolicy.createHeader(XWPFHeaderFooterPolicy.DEFAULT);

        paragraph = header.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.LEFT);



        run = paragraph.createRun();
        String imgFile="./images/image2.jpeg";
        BufferedImage bimg = ImageIO.read(new File(imgFile));
        int width          = bimg.getWidth();
        int height         = bimg.getHeight();
        double scaling =  0.45;

        run.addPicture(new FileInputStream(imgFile), XWPFDocument.PICTURE_TYPE_PNG, imgFile, Units.toEMU(width*scaling), Units.toEMU(height*scaling));
        run.addTab();
        run.addTab();
        run.addTab();
        run.addTab();


        imgFile="./images/image1.jpeg";
        bimg = ImageIO.read(new File(imgFile));
        width          = bimg.getWidth();
        height         = bimg.getHeight();

        run.addPicture(new FileInputStream(imgFile), XWPFDocument.PICTURE_TYPE_PNG, imgFile, Units.toEMU(width*scaling),Units.toEMU(height*scaling));





        doc.write(new FileOutputStream("test4.docx"));

    }



}
