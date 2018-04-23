package Servlet;


import Bea_connect.Bea_mdb;
import Model.Sismage.StudySismage;
import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by J0463664 on 10/04/2018.
 */
@CrossOrigin
@RestController
@EnableWebMvc

public class SismageController {
    //@Autowired


    @GetMapping(value="/leaders")
    @ResponseBody
    public String getAllLeaders(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String content= "";
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with");

        response.setContentType("text/json");
        Bea_mdb mdb = new Bea_mdb();
        ArrayList<String> sismageStudies = mdb.getLeaders();
        content += new Gson().toJson(sismageStudies);

        return content;
    }


    @GetMapping(value="/sismage")
    @ResponseBody
    public String getAllSismageStudies( HttpServletRequest request, HttpServletResponse response) throws IOException{
        String content= "";
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with");

        response.setContentType("text/json");
        Bea_mdb mdb = new Bea_mdb();
        content += new Gson().toJson(mdb.getSismageStudies());
        return content;
    }

    @GetMapping(value="/sismage/getInfo/{pdt}")
    @ResponseBody
    public String getInfosWithPdt(@PathVariable("pdt") String pdt, HttpServletRequest request, HttpServletResponse response) throws IOException{
        String content= "";
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with");

        response.setContentType("text/json");
        Bea_mdb mdb = new Bea_mdb();
        content += new Gson().toJson(mdb.getInfosFromSismage(pdt));
        return content;
    }

    @GetMapping(value="/sismage/remove/{id}")
    @ResponseBody
    public String removeSismageStudy(@PathVariable("id") int id, HttpServletRequest request, HttpServletResponse response) throws IOException{
        String content= "";
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with");

        response.setContentType("text/json");
        Bea_mdb mdb = new Bea_mdb();
        content += new Gson().toJson(mdb.removeSismageStudy(id));
        return content;
    }




    @PostMapping(value="/sismage/add")
    @ResponseBody
    public ResponseEntity<Void> addSismageStudy(@RequestBody StudySismage study, HttpServletRequest request, HttpServletResponse response) throws IOException {;
        String content= "";
        Bea_mdb mdb = new Bea_mdb();

        try {
            mdb.addSismageStudy(study);
            return ResponseEntity.created(new URI("/toto/")).build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PostMapping(value="/sismage/update")
    @ResponseBody
    public ResponseEntity<Void> updateSismageStudy(@RequestBody StudySismage study, HttpServletRequest request, HttpServletResponse response) throws IOException {;
        String content= "";
        Bea_mdb mdb = new Bea_mdb();

        try {
            mdb.updateSismageStudy(study);
            return ResponseEntity.created(new URI("/toto/")).build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }



}
