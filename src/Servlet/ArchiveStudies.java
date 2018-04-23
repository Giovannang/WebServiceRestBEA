package Servlet;

import Bea_connect.ArchiveLast3Years;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * Created by L0463664 on 11/07/2017.
 */

@CrossOrigin
@RestController
@EnableWebMvc

public class ArchiveStudies {
    @GetMapping(value="/archive")
    @ResponseBody
    public String getAllStudies(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ArchiveLast3Years connect= new  ArchiveLast3Years();
        connect.getBEA().to_string();
        String content = connect.getBEA().to_Json();
        String fileName = "Config\\Archive\\studiesArchived.json";
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(fileName), "utf-8"))) {
            writer.write(content);
        }


        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET");
        response.setHeader("Access-Control-lAlow-Headers", "x-requested-with");


        response.setContentType("text/json");
        return connect.getBEA().studies.size()+" Studies archived in "+fileName+" on server side";
    }

}
