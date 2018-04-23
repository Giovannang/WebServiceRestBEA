package Bea_connect;

import Model.BEA_Studies;
import Model.Sismage.StudySismage;
import Model.StudyCreated;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by L0463664 on 17/08/2017.
 */
public class Bea_mdb {
    final String url = "jdbc:mysql://localhost:3306/bea_mdb";
    final String user = "root";
    final String pwd = "admin";
    Connection connection;

    public Bea_mdb() {
        try {
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            this.connection = DriverManager.getConnection(this.url, this.user, this.pwd);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getCountries(){
        String req = "SELECT beaName FROM pays ";
        PreparedStatement stmt = null;
        ResultSet result = null;
        ArrayList<String> returningList = new ArrayList<String>();
        try {
            stmt = this.connection.prepareStatement(req);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                returningList.add(rs.getString("beaName"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return returningList;
    }

    public ArrayList<StudySismage> getSismageStudies(){
        String req = "SELECT s.id, s.name, s.date_debut, s.date_fin, s.description, s.remarques, s.path_unix, s.path_segy, s.confidencialite FROM sismage s;";
        PreparedStatement stmt = null;
        ArrayList<StudySismage> studies = new ArrayList<StudySismage>();
        try {
            stmt = this.connection.prepareStatement(req);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                studies.add(new StudySismage(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (StudySismage study: studies) {
            req = "SELECT usi FROM sismage_usi WHERE (id_sismage=?) AND (type='acq')";
            try {
                stmt = this.connection.prepareStatement(req);
                stmt.setInt(1, study.id);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()){
                    study.addUsiAcq(rs);
                }
            }catch (SQLException e){
                e.printStackTrace();
            }

            req = "SELECT usi FROM sismage_usi WHERE (id_sismage=?) AND (type='pro')";
            try {
                stmt = this.connection.prepareStatement(req);
                stmt.setInt(1, study.id);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()){
                    study.addUsiPro(rs);
                }
            }catch (SQLException e){
                e.printStackTrace();
            }

            req = "SELECT s.pdt, s.pays, s.block, s.igg, u.nom, u.prenom FROM sismage_beadata s, user u WHERE (id_sismage=?) AND (u.igg=s.igg) ";
            try {
                stmt = this.connection.prepareStatement(req);
                stmt.setInt(1, study.id);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()){
                    study.addGeo(rs);
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }

        return studies;
    }


    public String addSismageStudy(StudySismage study) throws SQLException {
        String req = "INSERT INTO sismage(name, date_debut, description, remarques, path_unix, path_segy) VALUES(?,?,?,?,?,?) ";
        ResultSet result;
        int id_sismageStudy=0;
        PreparedStatement stmtt = this.connection.prepareStatement(req, Statement.RETURN_GENERATED_KEYS);
        try {

            stmtt.setString(1, study.getName());
            stmtt.setString(2, study.getDate_debut());
            stmtt.setString(3, study.getDescription());
            stmtt.setString(4, study.getRemarques());
            stmtt.setString(5, study.getPath_unix());
            stmtt.setString(6, study.getPath_segy());
            stmtt.executeUpdate();
            result = stmtt.getGeneratedKeys();
            if(result.next() && result != null){
                id_sismageStudy = result.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (int i = 0; i <study.getUsi_acq().size() ; i++) {
            req = "INSERT INTO sismage_usi(id_sismage, usi, type) VALUES(?,?,'acq')";
            stmtt = this.connection.prepareStatement(req);
            stmtt.setInt(1, id_sismageStudy);
            stmtt.setString(2, study.getUsi_acq().get(i));
            stmtt.executeUpdate();
        }
        for (int j = 0; j <study.getUsi_pro().size() ; j++) {
            req = "INSERT INTO sismage_usi(id_sismage, usi, type) VALUES(?,?,'pro')";
            stmtt = this.connection.prepareStatement(req);
            stmtt.setInt(1, id_sismageStudy);
            stmtt.setString(2, study.getUsi_pro().get(j));
            stmtt.executeUpdate();
        }

        for (int k = 0; k <study.getGeos().size() ; k++) {
            req = "INSERT INTO sismage_beadata(id_sismage, pdt, pays, block, igg) VALUES(?,?,?,?,?)";
            stmtt = this.connection.prepareStatement(req);
            stmtt.setInt(1, id_sismageStudy);
            stmtt.setString(2, study.getGeos().get(k).getPdt());
            stmtt.setString(3, study.getGeos().get(k).getCountry());
            stmtt.setString(4, study.getGeos().get(k).getBlock());
            stmtt.setString(5, study.getGeos().get(k).getLeader().getIgg());
            stmtt.executeUpdate();
        }
        return "OK";
    }

    public String removeSismageStudy(int id) {
        ArrayList<String> reqs = new ArrayList<String>() ;
        reqs.add("DELETE FROM sismage WHERE id=?");
        reqs.add("DELETE FROM sismage_usi WHERE id_sismage=?");
        reqs.add("DELETE FROM sismage_beadata WHERE id_sismage=?");
        ResultSet rs;
        PreparedStatement stmt ;
        for (String req: reqs) {
            try {
                stmt = this.connection.prepareStatement(req);
                stmt.setInt(1,id);
                stmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return "OK";
    }


    public String updateSismageStudy(StudySismage sismage) throws SQLException {
        removeSismageStudy(sismage.getId());
        addSismageStudy(sismage);
        return "OK";
    }


    public void saveStudySismage(StudySismage study){

    }

    public String getInfosFromSismage(String pdt){
        String req = "SELECT b.pays, b.block, b.igg, b.libelle FROM bea_data b WHERE (b.reference=?)";
        PreparedStatement stmt = null;
        ResultSet result = null;
        String returningList ="";
        try {
            stmt = this.connection.prepareStatement(req);
            stmt.setString(1,pdt);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                returningList = (rs.getString("pays")+';'+rs.getString("block")+';'+rs.getString("igg")+';'+rs.getString("libelle"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return returningList;
    }





    public ArrayList<String> getLicences(String pays){
        String req = "SELECT b.libel FROM block b, pays p WHERE (b.idPays=p.id) AND (p.beaName=?)";
        PreparedStatement stmt = null;
        ResultSet result = null;
        ArrayList<String> returningList = new ArrayList<String>();
        try {
            stmt = this.connection.prepareStatement(req);
            stmt.setString(1,pays);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                returningList.add(rs.getString("libel"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    return returningList;
    }


    public BEA_Studies getAllStudies(){
        String req = "SELECT e.discipline, ec.path, bea.id_bea, bea.reference, bea.libelle, bea.status, bea.type, bea.DATE_DEBUT, bea.DATE_FIN, bea.zone, bea.pays, bea.block, bea.service, bea.catech, bea.igg, u.nom, u.prenom, bea.date_envoit_rapport, bea.pdt_valide, bea.form_satis_rempli FROM bea_data bea, user u, etude e, etudecree ec WHERE (u.igg=bea.igg) AND (bea.id=e.bea_data) AND (e.etudeCree=ec.id) ";
        PreparedStatement stmt = null;
        ResultSet result = null;
        BEA_Studies studies = new BEA_Studies();
        try {
            stmt = this.connection.prepareStatement(req);
            //stmt.setString(1,pays);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                rs = stmt.executeQuery();
                while (rs.next()){
                    studies.adddStudy(rs);

                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


    return studies;
    }

    public String getCountryCodeFromCountry(String country){
        String req = "SELECT p.code_pays FROM pays p WHERE (p.beaName=?)";
        PreparedStatement stmt = null;
        String rez="";
        ResultSet result = null;
        try {
            stmt = connection.prepareStatement(req);
            stmt.setString(1,country);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                rez = rs.getString("code_pays");

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    return rez;
    }

    public ArrayList<StudyCreated> getStudiesCreated(){
        ArrayList<StudyCreated> studiesCreated = new ArrayList<StudyCreated>();

        String req = "SELECT ec.path, bea.reference, q.dataImport, q.dataExport, q.prepData, q.sismage, q.techDoc, q.techInfo, q.usiPro, q.usiAcq FROM bea_data bea, etude e, etudecree ec, questionnaire q WHERE (q.id=e.questionnaire) AND (bea.id=e.bea_data) AND (e.etudeCree=ec.id) AND (ec.path!='') ";
        PreparedStatement stmt = null;
        ResultSet result = null;
        BEA_Studies studies = new BEA_Studies();
        try {
            stmt = this.connection.prepareStatement(req);
            //stmt.setString(1,pays);
            ResultSet rs = stmt.executeQuery();
                while (rs.next()){
                    studiesCreated.add(new StudyCreated(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return studiesCreated;
    }



    public String createW(String ref, String path, String dataImport, String dataExport, String usiAcq, String usiPro, String techDoc, String techInfo, String prepData, String sismage){
        //LocalDate now = LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new java.util.Date()).substring(0, 10));
        String req = "SELECT e.questionnaire, e.etudecree FROM etude e, bea_data b WHERE (e.bea_data=b.id) AND (b.reference=?)";
        PreparedStatement stmt = null;
        String rez="";
        ResultSet result = null;
        int id_questionnaire=0, id_create=0;
        try {
            stmt = this.connection.prepareStatement(req);
            stmt.setString(1,ref);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                id_create = rs.getInt("etudecree");
                id_questionnaire = rs.getInt("questionnaire");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        //_____________________________________________________________________________________________________________________________
        req = "UPDATE etudecree SET path=?, date='121212' WHERE (id=?)";
        try {
            stmt = this.connection.prepareStatement(req);
            stmt.setString(1,path);
            //stmt.setString(2,"12/12/12");
            stmt.setInt(2,id_create);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        req = "UPDATE questionnaire SET dataImport=?,  dataExport=?,  usiAcq=?,  usiPro=?,  techDoc=?,  techInfo=?,  prepData=?,  sismage=? WHERE (id=?)";
        try {
            stmt = this.connection.prepareStatement(req);
            stmt.setString(1,dataImport);
            stmt.setString(2,dataExport);
            stmt.setString(3,usiAcq);
            stmt.setString(4,usiPro);
            stmt.setString(5,techDoc);
            stmt.setString(6,techInfo);
            stmt.setString(7,prepData);
            stmt.setString(8,sismage);
            stmt.setInt(9,id_questionnaire);
            stmt.executeUpdate();
            rez += "SUCCES WRITING PATH IN MARIA DB";

        } catch (SQLException e) {
            e.printStackTrace();
            rez += "FAIL";
        }


        return rez;
    }

    public ArrayList<String> getLeaders() {
    String req = "SELECT u.igg, u.nom, u.prenom FROM user u ";
    PreparedStatement stmt = null;
    ResultSet result = null;
    ArrayList<String> returningList = new ArrayList<String>();
        try {
        stmt = this.connection.prepareStatement(req);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()){
            returningList.add(rs.getString("igg")+';'+rs.getString("nom")+';'+rs.getString("prenom"));
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return returningList;
}
}
