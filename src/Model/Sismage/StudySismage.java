package Model.Sismage;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by J0463664 on 18/04/2018.
 */
public class StudySismage {


    public int id;
    private String name;
    private List<Geo> geos = null;
    private String date_debut;
    private String path_unix;
    private String path_segy;
    private String description;
    private String remarques;
    private ArrayList<String> usi_acq = null ;
    private ArrayList<String> usi_pro = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public StudySismage() {
    }

    /**
     *
     * @param pathSegy
     * @param geos
     * @param remarques
     * @param description
     * @param name
     * @param pathUnix
     * @param usiAcq
     * @param usiPro
     * @param date_debut
     */
    public StudySismage(String name, List<Geo> geos, String date_debut, String pathUnix, String pathSegy, String description, String remarques, ArrayList<String> usiAcq, ArrayList<String> usiPro) {
        super();
        this.name = name;
        this.geos = geos;
        this.date_debut = date_debut;
        this.path_unix = pathUnix;
        this.path_segy = pathSegy;
        this.description = description;
        this.remarques = remarques;
        this.usi_acq = usiAcq;
        this.usi_pro = usiPro;
    }

    public StudySismage(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.name = rs.getString("name");
        this.date_debut = rs.getString("date_debut");
        this.path_unix = rs.getString("path_unix");
        this.path_segy = rs.getString("path_segy");
        this.description = rs.getString("description");
        this.remarques = rs.getString("remarques");
        this.usi_acq = new ArrayList<String>();
        this.usi_pro = new ArrayList<String>();
        this.geos = new ArrayList<Geo>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void addGeo(ResultSet rs) throws SQLException {
        this.geos.add(new Geo(rs.getString("pdt"), rs.getString("pays"), rs.getString("block"), new Leader(rs.getString("igg"), rs.getString("nom"), rs.getString("prenom"))));

    }

    public void addUsiPro(ResultSet rs) throws SQLException {
        this.usi_pro.add(rs.getString("usi"));
    }

    public void addUsiAcq(ResultSet rs) throws SQLException {
        this.usi_acq.add(rs.getString("usi"));
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Geo> getGeos() {
        return geos;
    }

    public void setGeos(List<Geo> geos) {
        this.geos = geos;
    }

    public String getDate_debut() {
        return date_debut;
    }

    public void setDate_debut(String date_debut) {
        this.date_debut = date_debut;
    }

    public String getPath_unix() {
        return path_unix;
    }

    public void setPath_unix(String path_unix) {
        this.path_unix = path_unix;
    }

    public String getPath_segy() {
        return path_segy;
    }

    public void setPath_segy(String path_segy) {
        this.path_segy = path_segy;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRemarques() {
        return remarques;
    }

    public void setRemarques(String remarques) {
        this.remarques = remarques;
    }

    public ArrayList<String> getUsi_acq() {
        return usi_acq;
    }

    public void setUsi_acq(ArrayList<String> usi_acq) {
        this.usi_acq = usi_acq;
    }

    public ArrayList<String> getUsi_pro() {
        return usi_pro;
    }

    public void setUsi_pro(ArrayList<String> usi_pro) {
        this.usi_pro = usi_pro;
    }
}

