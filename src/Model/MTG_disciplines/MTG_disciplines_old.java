package Model.MTG_disciplines;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by L0463664 on 04/07/2017.
 */
public class MTG_disciplines_old {
    protected final String path = "path";
    public List<String[]> tags;

    public MTG_disciplines_old() {
        String csvFile = "Config/Tag_discipline/MTG_disciplines.csv";
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
        this.tags = result;

    }
}
