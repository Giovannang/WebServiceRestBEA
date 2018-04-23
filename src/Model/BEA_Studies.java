package Model;

import Model.MTG_disciplines.MTG_disciplines;
import com.google.gson.Gson;

import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by L0463664 on 29/05/2017.
 */

/*

There is 3 lists :
    - studies : a list of MTG studies extracted from BEA
    - studiesCreated : studies extracted from a csv config file
    - mtg_disciplines : a list of MTG disciplines

The two last lists are useful to complete BEA information extracted in SQL with other information witch are not in the DataBase.

 */
public class BEA_Studies implements Serializable{
    public List<BEA_Study> studies;
    public List<String[]> studiesCreated;
    public MTG_disciplines mtg_disciplines;



    public BEA_Studies() {
        this.studies=new ArrayList<BEA_Study>();
        this.studiesCreated = getStudiesCreatedFromCsv();
        this.mtg_disciplines = new MTG_disciplines();
    }

    private List<String[]> getStudiesCreatedFromCsv() {
        String csvFile = "Config/Studies_Created.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ";";
        List<String[]> result = new ArrayList<String[]>();

        try {

            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] datas = line.split(cvsSplitBy);
                result.add(datas);

                //System.out.println("Date :" + datas[0] + " IGG :" + datas[1] + " Path :" + datas[2] +" Ref :" + datas[3]);

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;

    }

    public void  adddStudy(ResultSet rs) throws SQLException {
        BEA_Study study = new  BEA_Study(rs);
        this.studies.add(study);
    }



    public void  addStudy(ResultSet rs) throws SQLException {
        BEA_Study study = new  BEA_Study(rs, this.studiesCreated, this.mtg_disciplines);
        this.studies.add(study);
    }

    public String to_string(){
        String rez = "";
        for (BEA_Study study:studies) {
            rez =rez   + study.to_string();
        }
        return rez;
    }


    public String to_HTML(){
        String rez = "";
        for (BEA_Study study:studies) {
            rez =rez   + "<br>" + study.to_string();
        }
        return rez;
    }

    public String to_Json(){
        return new Gson().toJson(this.studies);
    }



}

