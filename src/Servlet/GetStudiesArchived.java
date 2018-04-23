package Servlet;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;


/**
 * Created by L0463664 on 24/05/2017.
 */

@CrossOrigin
@RestController
@EnableWebMvc

public class GetStudiesArchived {

    //@Autowired


    @GetMapping(value="/api/archived")
    @ResponseBody
    public String getAllStudies(HttpServletRequest request, HttpServletResponse response){
        String fileName = "Config\\Archive\\studiesArchived.json";
        String content="";
        File file = new File(fileName);

        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            byte[] data = new byte[(int) file.length()];
            try {
                fis.read(data);
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                String str = new String(data, "UTF-8");
                content += str;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with");


        response.setContentType("text/json");
        return content;
    }
}


