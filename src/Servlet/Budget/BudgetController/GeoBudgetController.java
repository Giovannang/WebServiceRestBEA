package Servlet.Budget.BudgetController;

import Servlet.Budget.ManageBudget;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by L0463664 on 11/07/2017.
 */

@CrossOrigin
@RestController
@EnableWebMvc

public class GeoBudgetController {
    @GetMapping(value="/budget/details/{year}")
    @ResponseBody
    public String getDetailsForYear(@PathVariable("year") int year, HttpServletRequest request, HttpServletResponse response ){
        ManageBudget bm = new ManageBudget();
        String content = bm.getBudgetDetailForyear(year);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET");
        response.setHeader("Access-Control-lAlow-Headers", "x-requested-with");
        response.setContentType("text/json");
        return content;
    }

    @GetMapping(value="/budget/{year}")
    @ResponseBody
    public String getStudies(@PathVariable("year") int year, HttpServletRequest request, HttpServletResponse response) throws IOException {

        ManageBudget bm = new ManageBudget();
        String content = bm.getBudgetGeographical(year);
        System.out.println(content);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET");
        response.setHeader("Access-Control-lAlow-Headers", "x-requested-with");
        response.setContentType("text/json");
        return content;
    }

    @GetMapping(value="/budget/{year}/{zone}")
    @ResponseBody
    public String getStudiess(@PathVariable("year") int year, @PathVariable("zone") String zone, HttpServletRequest request, HttpServletResponse response) throws IOException {

        ManageBudget bm = new ManageBudget();
        String content = bm.getBudgetGeographicalInZone(year, zone);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET");
        response.setHeader("Access-Control-lAlow-Headers", "x-requested-with");
        response.setContentType("text/json");
        return content;
    }


    @GetMapping(value="/budget/{year}/{zone}/{country}")
    @ResponseBody
    public String getStudiess(@PathVariable("year") int year, @PathVariable("zone") String zone, @PathVariable("country") String country,  HttpServletRequest request, HttpServletResponse response) throws IOException {

        ManageBudget bm = new ManageBudget();
        String content = bm.getBudgetGeographicalInCountry(year, country);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET");
        response.setHeader("Access-Control-lAlow-Headers", "x-requested-with");
        response.setContentType("text/json");
        return content;
    }

    @GetMapping(value="/budgets/{year}")
    @ResponseBody
    public String getStudiess(@PathVariable("year") int year,  HttpServletRequest request, HttpServletResponse response) throws IOException {

        ManageBudget bm = new ManageBudget();
        String content = bm.getBudgetHierarchical(year);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET");
        response.setHeader("Access-Control-lAlow-Headers", "x-requested-with");
        response.setContentType("text/json");
        return content;
    }



   /* @GetMapping(value="/human/{year}/{dpt}")
    @ResponseBody
    public String getStudiess(@PathVariable("year") int year, @PathVariable("dpt") String dpt, HttpServletRequest request, HttpServletResponse response) throws IOException {

        ManageBudget bm = new ManageBudget();
        String content = bm.getHumanRessourcesByYear(year);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET");
        response.setHeader("Access-Control-lAlow-Headers", "x-requested-with");
        response.setContentType("text/json");
        return content;
    }*/




}
