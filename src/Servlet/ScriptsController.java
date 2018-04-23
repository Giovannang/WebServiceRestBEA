package Servlet;


import Bea_connect.Bea_mdb;
import com.google.gson.Gson;
import org.apache.commons.io.FileUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * Created by L0463664 on 24/05/2017.
 */

@CrossOrigin
@RestController
@EnableWebMvc
public class ScriptsController {

    //@Autowired


    @GetMapping(value="/scripts/create/{zone}/{pays}/{licence}/{libel}/{igg}/{ref}/{dataImport}/{usiAcq}/{usiPro}/{techDoc}/{techInfo}/{dataExport}/{prepData}/{sismageData}/{tag}")
    @ResponseBody
    public String getAllStudies(@PathVariable("zone") String zone, @PathVariable("pays") String pays, @PathVariable("licence") String licence, @PathVariable("libel") String libel, @PathVariable("igg") String igg, @PathVariable("ref") String ref, @PathVariable("dataImport") String dataImport, @PathVariable("usiAcq") String usiAcq, @PathVariable("usiPro") String usiPro, @PathVariable("techDoc") String techDoc, @PathVariable("techInfo") String techInfo,  @PathVariable("dataExport") String dataExport, @PathVariable("prepData") String prepData, @PathVariable("sismageData") String sismageData,  @PathVariable("tag") String tag , HttpServletRequest request, HttpServletResponse response) throws IOException {
        String countryCode ="";
                Bea_mdb mdb = new Bea_mdb();
        countryCode = mdb.getCountryCodeFromCountry(pays);
        String path = "W:/Group/EXPLO/"+zone+"/"+countryCode+"/$GEOPHYSICS/$MTG_Study/"+licence+'/'+libel+'/';

        String content="Exit code : ";


        String pathBis="";
        switch (tag.replaceAll("-", "/")){
            case "MPI/AVO" :
                pathBis = "Config/Model/01_AVO_ModelStudy";
                break;
            case "AST/ACQ" :
                pathBis = "Config/Model/01_AS_ModelStudy";
                break;
            default :
                pathBis = "Config/Model/01_MTG_ModelStudy";
        }


        File file =new File(path);

        if (file.mkdirs()) {
            content += "SUCCES : Directory "+ file.getAbsolutePath() +" has been created.\n" ;

            try {
                File srcDir = new File(pathBis);
                File destDir = new File(path);
                FileUtils.copyDirectory(srcDir, destDir);
                content += "SUCCESS : copiing files in created folder\n ";
                content += mdb.createW(ref, path, dataImport, usiAcq, usiPro, techDoc, techInfo, dataExport, prepData, sismageData);
            } catch (IOException e) {
                content += "ERROR : copiing files in created folder\n ";
            }

        } else if (file.isDirectory()) {
            content += "ERROR : Directory "+ file.getAbsolutePath() +" has already been created.\n";

        } else {
            content += "ERROR : Directory "+ file.getAbsolutePath() +" could not be created.\n";
        }



        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with");

        response.setContentType("text/json");
        return new Gson().toJson(content);
    }



    public static void writeGeoInformationConfig(String filename, String ref,  String bool1, String bool2, String bool3, String bool4, String bool5, String bool6, String bool7, String bool8) throws IOException {
        Date actuelle = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        String dat = dateFormat.format(actuelle);
        BufferedWriter bufWriter = null;
        FileWriter fileWriter = null;
        fileWriter = new FileWriter(filename, true);
        bufWriter = new BufferedWriter(fileWriter);
        bufWriter.newLine();
        bufWriter.write(ref+";"+dat+";"+bool1+";"+bool2+";"+bool3+";"+bool4+";"+bool5+";"+bool6+";"+bool7+";"+bool8);
        bufWriter.close();

    }

    public static void writeStudyCreatedInConfigFile(String filename, String igg, String path, String ref) throws IOException {
        Date actuelle = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        String dat = dateFormat.format(actuelle);
        BufferedWriter bufWriter = null;
        FileWriter fileWriter = null;
        fileWriter = new FileWriter(filename, true);
        bufWriter = new BufferedWriter(fileWriter);
        bufWriter.newLine();
        bufWriter.write(dat+";"+igg+";"+path+";"+ref);
        bufWriter.close();

    }




    public static String getCountryCode(String zone, String pays) throws ParserConfigurationException, IOException, SAXException {
        //TODO : changer en SQL resquest sur  MariaDB
        String content = "";
        String file="Config/" ;
        ArrayList<String> returningLicences= new ArrayList<String>();
        switch (zone){
            case "AFR" : file += "AFR.xml";
                break;
            case "EAC" : file += "EAC.xml";
                break;
            case "AME" : file += "AME.xml";
                break;
            case "APC" : file += "APC.xml";
                break;
            case "MENA" : file += "MENA.xml";
                break;
        }

         /*
         * Etape 1 : récupération d'une instance de la classe "DocumentBuilderFactory"
         */
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            /*
             * Etape 2 : création d'un parseur
             */
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document= builder.parse(new File(file));
            Element racine = document.getDocumentElement();
            NodeList countries = racine.getChildNodes();
            int nbRacineNoeuds = countries.getLength();
            for (int i=0 ; i< nbRacineNoeuds; i++) {
                Element country = (Element) countries.item(i);
                if(pays.toUpperCase().equals(country.getAttribute("beaName"))){
                    content = country.getAttribute("code");
                }
            }
        return content;
    }
}







