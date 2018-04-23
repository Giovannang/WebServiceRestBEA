package Servlet.Budget;

import Servlet.Budget.ChartData.DataTable;
import com.google.gson.Gson;

import java.sql.*;
import java.util.*;

/**
 * Created by J0463664 on 24/11/2017.
 */
public class ManageBudget {
    final String url = "jdbc:mysql://localhost:3306/bea_mdb";
    final String user = "root";
    final String pwd = "admin";
    Map<String, String> MTGservices;
    ArrayList<String> world;
    Connection connection;

    public ManageBudget() {
        try {
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            this.connection = DriverManager.getConnection(this.url, this.user, this.pwd);
            this.world =new ArrayList<String>();
            this.world.add("AME");
            this.world.add("AFR");
            this.world.add("APC");
            this.world.add("MENA");
            this.world.add("EAC");
            this.MTGservices = new TreeMap<String, String>();
            this.MTGservices.put("20T2827", "IMA");
            this.MTGservices.put("20T2828", "MPI");
            this.MTGservices.put("20T2820", "EM");
            this.MTGservices.put("20T2826", "IDI");
            this.MTGservices.put("20T2822", "AST");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void deleteStudies(int year) {

        ArrayList<String> reqs = new ArrayList<>();
        reqs.add("DELETE FROM cout_perso");
        reqs.add("DELETE FROM serv_tech");
        reqs.add("DELETE FROM etude_budget");
        reqs.add("ALTER TABLE cout_perso AUTO_INCREMENT = 1");
        reqs.add("ALTER TABLE serv_tech AUTO_INCREMENT = 1");
        reqs.add("ALTER TABLE etude_budget AUTO_INCREMENT = 1");

        for (String reques : reqs) {
            PreparedStatement stmt = null;
            try {
                stmt = this.connection.prepareStatement(reques);
                stmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // Découpage ressourcesHumaines : accès aux données
    //---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    public int getNbHours(ArrayList<Etude> etudes){
        int rez = 0;
        for (Etude e:etudes) {
            for (CoutPerso cp : e.getCoutPersos()) {
                rez += cp.getNbh();
            }
        }
        return rez;
    }

    public String getHumanRessourcesByYear(int year){
        List<DataTable.Column> cols = new ArrayList<DataTable.Column>();
        List<DataTable.Row> rows = new ArrayList<DataTable.Row>();
        ArrayList<String> services = getServicesFromYear(year);


        cols.add(new DataTable.Column("string","service"));
        cols.add(new DataTable.Column("number","nbHours"));

        for (String service : services){
            List<DataTable.Row.Cell> cells = new ArrayList<DataTable.Row.Cell>() ;
            cells.add(new DataTable.Row.Cell(service));
            cells.add(new DataTable.Row.Cell(getNbHours(getEtudesWithYearService(year,service))));
            rows.add(new DataTable.Row(cells));
        }
        DataTable dt = new DataTable(cols,rows);
        return (new Gson().toJson(dt));
    }




    //---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // Découpage hiérarchique : accès aux données
    //---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    public String getBudgetHierarchical(int year){
        List<DataTable.Column> cols = new ArrayList<DataTable.Column>();
        List<DataTable.Row> rows = new ArrayList<DataTable.Row>();
        ArrayList<String> services = getServicesFromYear(year);


        cols.add(new DataTable.Column("string","service"));
        cols.add(new DataTable.Column("number","budget"));

        for (String service : services){
            List<DataTable.Row.Cell> cells = new ArrayList<DataTable.Row.Cell>() ;
            cells.add(new DataTable.Row.Cell(MTGservices.get(service)));
            cells.add(new DataTable.Row.Cell(getNbh(getEtudesWithYearService(2017,service))));
            rows.add(new DataTable.Row(cells));
        }
        DataTable dt = new DataTable(cols,rows);
        return (new Gson().toJson(dt));
    }





    public ArrayList<Etude> getEtudesWithYearService(int year, String service){
        ArrayList<Etude> etudes = new ArrayList<Etude>();
        CallableStatement cStmt = null;
        try {
            cStmt = this.connection.prepareCall("{call getStudiesFromService(?,?)}");
            cStmt.setInt(1, year);
            cStmt.setString(2, service);
            ResultSet rs = cStmt.executeQuery();
            etudes = computeResultSet2(rs, year, service);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return etudes;
    }


    public ArrayList<String> getServicesFromYear(int year){
        ArrayList<String> rez = new ArrayList();
        List<Etude> etudes = new ArrayList<Etude>();
        try {
            CallableStatement cStmt = this.connection.prepareCall("{call getServices(?)}");
            cStmt.setInt(1, year);
            ResultSet rs = cStmt.executeQuery();
            while (rs.next()) {
                String serv = rs.getString("service");
                rez.add(serv);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rez;
    }



    //---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // Dé&coupage géographique : accès aux données
    //---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    public String getBudgetZone(int year, String zone){
        System.out.println("Budget pour la zone "+zone +"  : "+getBudget(getEtudesWithYearZone(year,zone)));
        return "toto";
    }

    public String getBudgetGeographical(int year){
        List<DataTable.Column> cols = new ArrayList<DataTable.Column>();
        List<DataTable.Row> rows = new ArrayList<DataTable.Row>();

        cols.add(new DataTable.Column("string","zone"));
        cols.add(new DataTable.Column("number","budget"));

        for (String zone : world){
            List<DataTable.Row.Cell> cells = new ArrayList<DataTable.Row.Cell>() ;
            cells.add(new DataTable.Row.Cell(zone));
            cells.add(new DataTable.Row.Cell(getBudget(getEtudesWithYearZone(year,zone))));
            rows.add(new DataTable.Row(cells));
        }
        DataTable dt = new DataTable(cols,rows);
        return (new Gson().toJson(dt));
    }


//here
    public String getBudgetGeographicalInCountry(int year, String pays){
        ArrayList<Etude> etudes = putExpfront(getEtudesWithYearCountry(year,pays));
        List<DataTable.Column> cols = new ArrayList<DataTable.Column>();
        List<DataTable.Row> rows = new ArrayList<DataTable.Row>();

        cols.add(new DataTable.Column("string","etude"));
        cols.add(new DataTable.Column("number","budget"));

        for (Etude etude : etudes){
            List<DataTable.Row.Cell> cells = new ArrayList<DataTable.Row.Cell>() ;
            cells.add(new DataTable.Row.Cell(etude.getLibel().replace(".xlsm", "")));
            cells.add(new DataTable.Row.Cell(getStudyBudget(etude)));
            rows.add(new DataTable.Row(cells));
        }
        DataTable dt = new DataTable(cols,rows);
        return (new Gson().toJson(dt));

    }


    public ArrayList<Etude> putExpfront(ArrayList<Etude> etudes){
        ArrayList<Etude> rez = new ArrayList<Etude>();
        for (Etude e: etudes) {
            if (e.getLibel().contains("EXP-")){
                Collections.reverse(rez);
                rez.add(e);
                Collections.reverse(rez);
            }else
                rez.add(e);
        }
        return rez;
    }


    public String getBudgetGeographicalInZone(int year, String zone){
        List<String> countries = getCountriesFromZone(zone);
        List<DataTable.Column> cols = new ArrayList<DataTable.Column>();
        List<DataTable.Row> rows = new ArrayList<DataTable.Row>();

        cols.add(new DataTable.Column("string","pays"));

        cols.add(new DataTable.Column("number","budget"));
        cols.add(new DataTable.Column("string","code_pays"));

        for (String country : countries){
            List<DataTable.Row.Cell> cells = new ArrayList<DataTable.Row.Cell>() ;
            cells.add(new DataTable.Row.Cell(country.split("--")[1]));

            cells.add(new DataTable.Row.Cell(getBudget(getEtudesWithYearCountry(year,country.split("--")[1]))));
            cells.add(new DataTable.Row.Cell(country.split("--")[0]));

            rows.add(new DataTable.Row(cells));
        }
        DataTable dt = new DataTable(cols,rows);
        return (new Gson().toJson(dt));
    }




    public List getCountriesFromZone(String zone){
        List rez = new ArrayList();
        List<Etude> etudes = new ArrayList<Etude>();
        try {
            CallableStatement cStmt = this.connection.prepareCall("{call getCountriesFromZone(?)}");
            cStmt.setString(1, zone);
            ResultSet rs = cStmt.executeQuery();
            while (rs.next()) {
                String cp = rs.getString("code_pays");
                String name = rs.getString("beaName");
                rez.add(cp + "--" + name);
                System.out.println(cp + "--" + name);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rez;
    }

    public int getStudyBudget(Etude etude){
        int rez = 0;
            for (CoutPerso cp : etude.getCoutPersos()) {
                rez += cp.getNbh() * cp.getTarif();
            }

        return rez;
    }


    public int getBudget(ArrayList<Etude> etudes){
        int rez = 0;
        for (Etude etude: etudes) {
            for (CoutPerso cp : etude.getCoutPersos()) {
                rez += cp.getNbh() * cp.getTarif();
            }
        }
        return rez;
    }

    public int getNbh(ArrayList<Etude> etudes){
        int rez = 0;
        for (Etude etude: etudes) {
            for (CoutPerso cp : etude.getCoutPersos()) {
                rez += cp.getNbh();
            }
        }
        return rez;
    }


    public ArrayList<Etude> computeResultSet(ResultSet rs, int year){
        ArrayList<Etude> etudes = new ArrayList<Etude>();
        try {
            while (rs.next()){
                int id = rs.getInt("id");
                String libel = rs.getString("libelle");
                String usr = rs.getString("igg");
                String pdt = rs.getString("reference");
                String rfs = rs.getString("no_rfs");
                String serv = rs.getString("service");
                String status = rs.getString("status");
                String zone = rs.getString("zone");
                String pays = rs.getString("pays");
                String montant = rs.getString("montant");

                String cp = rs.getString("pays");

                CallableStatement ccStmt = this.connection.prepareCall("{call getCoutPersoWithStudyId(?)}");
                ccStmt.setInt(1, id);
                ResultSet rss = ccStmt.executeQuery();
                ArrayList<CoutPerso> coutsPerso = new ArrayList<CoutPerso>();
                boolean fromBEA = false;
                while (rss.next()){
                    String cs = rss.getString("service");
                    double nbH = rss.getInt("nb_heure");
                    double tarif = rss.getDouble("tarif");
                    double total = rss.getDouble("total");
                    fromBEA = rss.getInt("fromBEA")==1;
                    System.out.println(rss.getInt("fromBEA"));
                    System.out.println(fromBEA);
                    coutsPerso.add(new CoutPerso(cs,  nbH, tarif, total));
                }

                //WTF
                etudes.add(new Etude("",cp ,libel, usr, pdt, rfs, year, serv, status, zone, pays,  Double.parseDouble(montant),coutsPerso, fromBEA));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return etudes;

    }



    public ArrayList<Etude> computeResultSet2(ResultSet rs, int year, String service){
        ArrayList<Etude> etudes = new ArrayList<Etude>();
        try {
            while (rs.next()){
                int id = rs.getInt("id");
                String libel = rs.getString("libelle");
                String usr = rs.getString("igg");
                String pdt = rs.getString("reference");
                String rfs = rs.getString("no_rfs");
                String serv = rs.getString("service");
                String status = rs.getString("status");
                String zone = rs.getString("zone");
                String pays = rs.getString("pays");
                String montant = rs.getString("montant");

                String cp = rs.getString("pays");

                CallableStatement ccStmt = this.connection.prepareCall("{call getCoutPersoFromServiceWithStudyId(?,?)}");
                ccStmt.setInt(1, id);
                ccStmt.setString(2, service);
                ResultSet rss = ccStmt.executeQuery();
                ArrayList<CoutPerso> coutsPerso = new ArrayList<CoutPerso>();
                boolean fromBEA = false;
                while (rss.next()){
                    String cs = rss.getString("service");
                    double nbH = rss.getInt("nb_heure");
                    double tarif = rss.getDouble("tarif");
                    double total = rss.getDouble("total");
                    fromBEA = rss.getInt("fromBEA")==1;
                    rss.getInt("fromBEA");
                    System.out.println(fromBEA);

                    coutsPerso.add(new CoutPerso(cs,  nbH, tarif, total));
                }
                //WTF
                etudes.add(new Etude("",cp ,libel, usr, pdt, rfs, year, serv, status, zone, pays,  Double.parseDouble(montant),coutsPerso, fromBEA));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return etudes;

    }

    public ArrayList<Etude> getEtudesWithYearCountry(int year, String country) {
        ArrayList<Etude> etudes = new ArrayList<Etude>();
        CallableStatement cStmt = null;
        try {
            cStmt = this.connection.prepareCall("{call getStudiesFromCountry(?,?)}");
            cStmt.setInt(1, year);
            cStmt.setString(2, country);
            ResultSet rs = cStmt.executeQuery();
            etudes = computeResultSet(rs, year);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return etudes;
    }


    public ArrayList<Etude> getEtudesWithYear(int year) {
        ArrayList<Etude> etudes = new ArrayList<Etude>();
        CallableStatement cStmt = null;
        try {
            cStmt = this.connection.prepareCall("{call getStudiesWithYear(?)}");
            cStmt.setInt(1, year);
            ResultSet rs = cStmt.executeQuery();
            etudes = computeResultSet(rs, year);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return etudes;

    }



    public ArrayList<Etude> getEtudesWithYearZone(int year, String zone) {
        ArrayList<Etude> etudes = new ArrayList<Etude>();
        CallableStatement cStmt = null;
        try {
            cStmt = this.connection.prepareCall("{call getWithYearZone(?,?)}");
            cStmt.setInt(1, year);
            cStmt.setString(2, zone);
            ResultSet rs = cStmt.executeQuery();
            etudes = computeResultSet(rs, year);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return etudes;
    }


    public void save(Etude etude) {
        String req = "SELECT id FROM bea_data WHERE (reference=?)";
        int id = 0;
        try {
            PreparedStatement stmt = this.connection.prepareStatement(req);
            stmt.setString(1, etude.getPdt().replace('_', '-'));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                id = rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        req = "UPDATE bea_data SET flag_budget='1' WHERE (id=?)";
        try {
            PreparedStatement stmt = this.connection.prepareStatement(req);
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (CoutPerso cp : etude.getCoutPersos()) {
            req = "INSERT INTO cout_perso(service, nb_heure, tarif,total, id_bea_data, fromBEA) VALUES(?,?,?,?,?,?) ";
            try {
                PreparedStatement stmtt = this.connection.prepareStatement(req);
                stmtt.setString(1, cp.getService());
                stmtt.setDouble(2, cp.getNbh());
                stmtt.setDouble(3, cp.getTarif());
                stmtt.setDouble(4, cp.getTotal());
                stmtt.setInt(5, id);
                stmtt.setInt(6, 1);
                stmtt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

    }

    public String getBudgetDetailForyear(int year) {
        ArrayList<Etude> etudes = getEtudesWithYear(year);
        return  new Gson().toJson(etudes);

    }


            /*
            for (ServTech servTech: etude.getServTeches()) {
                req = "INSERT INTO serv_tech(code_section, description, uow, nb_uow, tarif, id_etudeBudjet) VALUES(?,?,?,?,?,?) ";
                PreparedStatement stmtt = this.connection.prepareStatement(req);
                stmtt.setString(1, servTech.getCode_section());
                stmtt.setString(2, servTech.getDescription());
                stmtt.setString(3, servTech.getUow());
                stmtt.setDouble(4, servTech.getNbuow());
                stmtt.setDouble(5, servTech.getTarif());
                stmtt.setInt(6, id_etude);
                stmtt.executeUpdate();
            }
*/


}
