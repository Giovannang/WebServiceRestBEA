package Servlet;


import Bea_connect.Bea_mdb;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Created by L0463664 on 24/05/2017.
 */

@CrossOrigin
@RestController
@EnableWebMvc

public class RestApiController {

    //@Autowired


    @GetMapping(value="/api/all")
    @ResponseBody
    public String getAllStudies(HttpServletRequest request, HttpServletResponse response){
        /*Cnx_oracle connect= new Cnx_oracle();
        connect.getBEA().to_string();
        String content = connect.getBEA().to_Json();
        try (FileWriter file = new FileWriter("\\\\main.glb.corp.local\\ep-hq$\\Home\\PAU\\4\\L0463664\\Documents\\studies.json")) {
            file.write(content);
            System.out.println("Studies saved in \"Documents/studies.json\"");
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        Bea_mdb mdb = new Bea_mdb();
        String content = mdb.getAllStudies().to_Json();


        /*
        response.setHeader("Access-Control-Allow-Origin:","*");
        response.setHeader("Access-Control-Allow-Credentials:", "true ");
        response.setHeader("Access-Control-Allow-Methods:", "OPTIONS, GET, POST");
        response.setHeader("Access-Control-Allow-Headers:", "Content-Type, Depth, User-Agent, X-File-Size, X-Requested-With, If-Modified-Since, X-File-Name, Cache-Control");
        */

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with");

        response.setContentType("text/json");
        return content;
    }
}


