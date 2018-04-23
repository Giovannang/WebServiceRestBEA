package Servlet;


import Bea_connect.Bea_mdb;
import com.google.gson.Gson;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;


/**
 * Created by L0463664 on 24/05/2017.
 */

@CrossOrigin
@RestController
@EnableWebMvc

public class LicencesController {

    @GetMapping(value="/pays")
    @ResponseBody
    public String getCountries(HttpServletRequest request, HttpServletResponse response) throws IOException{


        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET");
        response.setHeader("Access-Control-lAlow-Headers", "x-requested-with");


        response.setContentType("text/json");
        Bea_mdb mdb = new Bea_mdb();

        return (new Gson().toJson(mdb.getCountries()));
    }


    @GetMapping(value="/licences/{pays}")
@ResponseBody
    public String getAllStudies(@PathVariable("pays") String pays, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET");
        response.setHeader("Access-Control-lAlow-Headers", "x-requested-with");


        response.setContentType("text/json");
        ArrayList<String> returningLicences= new ArrayList<String>();

        Bea_mdb mdb = new Bea_mdb();
        returningLicences = mdb.getLicences(pays);
        String content = new Gson().toJson(returningLicences);
        return content;



        /*String content = "";
        String file=".\\Config\\" ;
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





        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {

            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document= builder.parse(new File(file));
            Element racine = document.getDocumentElement();
            NodeList countries = racine.getChildNodes();
            int nbRacineNoeuds = countries.getLength();
            for (int i=0 ; i< nbRacineNoeuds; i++) {
                Element country = (Element) countries.item(i);
                if(pays.toUpperCase().equals(country.getAttribute("beaName"))){
                    NodeList licences = country.getChildNodes();
                    int nbLicences = licences.getLength();
                    for (int j = 0; j < nbLicences; j++) {
                        Element licence = (Element) licences.item(j);
                        returningLicences.add(licence.getAttribute("name"));
                    }
                }

            }


        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        content = new Gson().toJson(returningLicences);




        response.setHeader("Access-Control-Allow-Origin:","*");
        response.setHeader("Access-Control-Allow-Credentials:", "true ");
        response.setHeader("Access-Control-Allow-Methods:", "OPTIONS, GET, POST");
        response.setHeader("Access-Control-Allow-Headers:", "Content-Type, Depth, User-Agent, X-File-Size, X-Requested-With, If-Modified-Since, X-File-Name, Cache-Control");


        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET");
        response.setHeader("Access-Control-lAlow-Headers", "x-requested-with");


        response.setContentType("text/json");
        return content;*/
    }
}


