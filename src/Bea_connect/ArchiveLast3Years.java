package Bea_connect; /**
 * Created by L0463664 on 24/05/2017.
 */

import Model.BEA_Studies;

import java.sql.*;
import java.util.Calendar;


/*
This class includes connexions strings to the BEA DataBase.
It request the BEA in order to request studies created 3 years and before
The request called 'req' has 2 parameters :
    - reference : the year of the study is requested by a condition on the reference format, example '2017%' for the year 2017
    - sigle_departement : corresponding to the static variable dpmnt in order to get only MTG studies from the BEA



The methods called to create a list of studies is in BEA_STUDIES and in BEA_STUDY classes


*/

public class ArchiveLast3Years extends BEA_Studies{
    final static String url = "jdbc:oracle:thin:@orkney:1521/APBEA";
    final static String user = "BEAMTG";
    final static String pwd ="Be4M|gUs3r";
    final static String dptmnt = "EXPLO/GTS/MTG";
    final static int current_year = Calendar.getInstance().get(Calendar.YEAR);

    private BEA_Studies BEA;
    private static String req =
            "SELECT DISTINCT e.ID, vget.ETUDE_DATE_DEMANDE_CLIENT, vget.ORIGINE_DEMANDE_LIBELLE, e.PRESTATAIRES_IMPLIQUES, vget.DOMINANTE_METIER_LIBELLE, e.DATE_FIN_TECHNIQUE, vget.ETUDE_DEMANDEUR, vget.DIG_LIBELLE, e.REFERENCE, e.LIBELLE_ETUDE, e.DATE_DEBUT_ETUDE, s.LIBELLE_SERVICE, pa.LIBELLE_PAYS, pe.LIBELLE_PERMIS, u.NOM, u.PRENOM, vget.CATEGORIE_TECHNIQUE_LIBELLE, vget.FILIALE_OPCO_LIBELLE, e.DATE_FIN_ETUDE, e.DATE_FIN_REELLE, u.MATRICULE, vget.STATUT_ETUDE_LIBELLE, vget.ETUDE_MONTANT_TOTAL_PDT, vget.TYPE_ETUDE_LIBELLE, vget.UNEPDTVALIDE, e.DIFFUSION_RESTREINTE, e.DATE_DEBUT_PREVISIONNELLE, e.DATE_FIN_PREVISIONNELLE, e.DATE_ENVOI_FORMUL, vget.FORMULAIRE_SATISFACTION_REMPLI, vget.NO_RFS, d.SIGLE_DEPARTEMENT    " +
    "                FROM BEA.ETUDE e  " +
        "            LEFT JOIN BEA.V_GRAPH_ETUDE_TEMPO vget  " +
        "            ON vget.ETUDE_ID=e.ID  " +
        "            LEFT JOIN BEA.PERMIS pe  " +
        "            ON e.ID_PERMIS=pe.ID  " +
        "            LEFT JOIN BEA.UTILISATEUR u  " +
        "            ON e.ID_UTILISATEUR=u.ID  " +
        "            LEFT JOIN BEA.FILIALE_OPCO f  " +
        "            ON e.ID_FILIALE_OPCO=f.ID,  " +
        "            BEA.SERVICE s, BEA.DEPARTEMENT d, BEA.PAYS pa, BEA.R_CAT_TECH_ETUDE rcatec, BEA.CATEGORIE_TECHNIQUE catec " +
        "            WHERE  (e.ID_SERVICE_TG=s.ID) AND (s.ID_DEPARTEMENT=d.ID) AND (d.SIGLE_DEPARTEMENT=?) AND (rcatec.ID_CATEGORIE_TECHNIQUE=catec.ID_CATEGORIE_TECHNIQUE) AND (rcatec.ID_ETUDE=e.ID) AND (e.ID_PAYS=pa.ID)" +
        "            AND (e.REFERENCE NOT IN (SELECT e.REFERENCE from BEA.ETUDE, BEA.DEPARTEMENT WHERE (SIGLE_DEPARTEMENT=?) AND (e.REFERENCE LIKE ?) ))"+
        "            AND (e.REFERENCE NOT IN (SELECT e.REFERENCE from BEA.ETUDE, BEA.DEPARTEMENT WHERE (SIGLE_DEPARTEMENT=?) AND (e.REFERENCE LIKE ?) ))"+
        "            AND (e.REFERENCE NOT IN (SELECT e.REFERENCE from BEA.ETUDE, BEA.DEPARTEMENT WHERE (SIGLE_DEPARTEMENT=?) AND (e.REFERENCE LIKE ?) ))" +
        "            ORDER BY e.REFERENCE DESC";



    public ArchiveLast3Years() {

        this.BEA=new BEA_Studies();
        try {

            Class.forName("oracle.jdbc.driver.OracleDriver");

        } catch (ClassNotFoundException e) {

            System.out.println("Where is your Oracle JDBC Driver?");
            e.printStackTrace();
            return;

        }
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, pwd);

            PreparedStatement stmt = null;
            stmt = connection.prepareStatement(req);
            stmt.setString(1, dptmnt );
            stmt.setString(2, dptmnt );
            stmt.setString(3,Integer.toString(this.current_year-2)+"%" );
            stmt.setString(4, dptmnt );
            stmt.setString(5,Integer.toString(this.current_year-1)+"%" );
            stmt.setString(6, dptmnt );
            stmt.setString(7,Integer.toString(this.current_year)+"%" );
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                BEA.addStudy(rs);
            }

            System.out.println("Nombre d'études : "+ this.BEA.studies.size());
        } catch (SQLException e) {

            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return;

        }


    }

    public static String getUrl() {
        return url;
    }

    public static String getUser() {
        return user;
    }

    public static String getPwd() {
        return pwd;
    }

    public static String getDptmnt() {
        return dptmnt;
    }

    public static int getCurrent_year() {
        return current_year;
    }

    public BEA_Studies getBEA() {
        return BEA;
    }

    public void setBEA(BEA_Studies BEA) {
        this.BEA = BEA;
    }

}
