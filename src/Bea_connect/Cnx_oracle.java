package Bea_connect; /**
 * Created by L0463664 on 24/05/2017.
 */

import Model.BEA_Studies;

import java.sql.*;
import java.util.Calendar;

/*
This class includes connexions strings to the BEA DataBase.
It request the BEA in order to have last 3 years studies of MTG
The request called 'req' has 2 parameters :
    - reference : the year of the study is requested by a condition on the reference format, example '2017%' for the year 2017
    - sigle_departement : corresponding to the static variable dpmnt in order to get only MTG studies from the BEA

The request is sended one time per year (3 times for the 3 years)

The methods called to create a list of studies is in BEA_STUDIES and in BEA_STUDY classes


*/

public class Cnx_oracle extends BEA_Studies{
    final static String url = "jdbc:oracle:thin:@orkney:1521/APBEA";
    final static String user = "BEAMTG";
    final static String pwd ="Be4M|gUs3r";
    final static String dptmnt = "EXPLO/GTS/MTG";
    final static int current_year = Calendar.getInstance().get(Calendar.YEAR);


    private BEA_Studies BEA;
    private static String req = ("SELECT DISTINCT e.ID, vget.ETUDE_DATE_DEMANDE_CLIENT, e.ENVOI_RAPPORT_FINAL ,vget.ORIGINE_DEMANDE_LIBELLE, e.PRESTATAIRES_IMPLIQUES, vget.DOMINANTE_METIER_LIBELLE, vget.ETUDE_DEMANDEUR, vget.DIG_LIBELLE, e.REFERENCE, e.LIBELLE_ETUDE, s.LIBELLE_SERVICE, pa.LIBELLE_PAYS, pe.LIBELLE_PERMIS, u.NOM, u.PRENOM, vget.CATEGORIE_TECHNIQUE_LIBELLE, vget.FILIALE_OPCO_LIBELLE, u.MATRICULE, vget.STATUT_ETUDE_LIBELLE, vget.ETUDE_MONTANT_TOTAL_PDT, vget.TYPE_ETUDE_LIBELLE, vget.UNEPDTVALIDE, e.DIFFUSION_RESTREINTE,  e.DATE_ENVOI_FORMUL, vget.FORMULAIRE_SATISFACTION_REMPLI, vget.NO_RFS, d.SIGLE_DEPARTEMENT, vget.DATE_FIN, vget.DATE_DEBUT    " +
            "            FROM BEA.ETUDE e  " +
            "            LEFT JOIN BEA.V_GRAPH_ETUDE_TEMPO vget  " +
            "            ON vget.ETUDE_ID=e.ID  " +
            "            LEFT JOIN BEA.PERMIS pe  " +
            "            ON e.ID_PERMIS=pe.ID  " +
            "            LEFT JOIN BEA.UTILISATEUR u  " +
            "            ON e.ID_UTILISATEUR=u.ID, " +
            "            BEA.SERVICE s, BEA.DEPARTEMENT d, BEA.PAYS pa" +
            "            WHERE (e.REFERENCE like ?)AND (e.ID_SERVICE_TG=s.ID) AND (s.ID_DEPARTEMENT=d.ID) AND (d.SIGLE_DEPARTEMENT=?) AND(e.ID_PAYS=pa.ID) ORDER BY e.ID DESC");




    public Cnx_oracle() {

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
            stmt.setString(2, dptmnt );

            for (int i = this.current_year; i >=this.current_year-2 ; i--) {
                stmt.setString(1,Integer.toString(i)+"%" );
                ResultSet rs = stmt.executeQuery();
                while (rs.next()){
                    BEA.addStudy(rs);
                }

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

    public BEA_Studies getBEA() {
        return BEA;
    }

    public void setBEA(BEA_Studies BEA) {
        this.BEA = BEA;
    }

    public static String getReq() {
        return req;
    }

    public static void setReq(String req) {
        Cnx_oracle.req = req;
    }
}
