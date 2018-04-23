package Model;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by L0463664 on 31/08/2017.
 */
public class StudyCreated {
    private String ref;
    private String path;
    private String dataImport;
    private String dataExport;
    private String usiAcq;
    private String usiPro;
    private String techDoc;
    private String techInfo;
    private String prepData;
    private String sismage;

    public StudyCreated(ResultSet rs) throws SQLException {
        this.ref = rs.getString("reference");
        this.path= rs.getString("path");
        this.dataImport= rs.getString("dataImport");
        this.dataExport= rs.getString("dataExport");
        this.usiAcq= rs.getString("usiAcq");
        this.usiPro= rs.getString("usiPro");
        this.techDoc= rs.getString("techDoc");
        this.techInfo= rs.getString("techInfo");
        this.prepData= rs.getString("prepData");
        this.sismage= rs.getString("sismage");

    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDataImport() {
        return dataImport;
    }

    public void setDataImport(String dataImport) {
        this.dataImport = dataImport;
    }

    public String getDataExport() {
        return dataExport;
    }

    public void setDataExport(String dataExport) {
        this.dataExport = dataExport;
    }

    public String getUsiAcq() {
        return usiAcq;
    }

    public void setUsiAcq(String usiAcq) {
        this.usiAcq = usiAcq;
    }

    public String getUsiPro() {
        return usiPro;
    }

    public void setUsiPro(String usiPro) {
        this.usiPro = usiPro;
    }

    public String getTechDoc() {
        return techDoc;
    }

    public void setTechDoc(String techDoc) {
        this.techDoc = techDoc;
    }

    public String getTechInfo() {
        return techInfo;
    }

    public void setTechInfo(String techInfo) {
        this.techInfo = techInfo;
    }

    public String getPrepData() {
        return prepData;
    }

    public void setPrepData(String prepData) {
        this.prepData = prepData;
    }

    public String getSismage() {
        return sismage;
    }

    public void setSismage(String sismage) {
        this.sismage = sismage;
    }
}
