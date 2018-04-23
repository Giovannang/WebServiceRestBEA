package Servlet.Budget;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by J0463664 on 24/11/2017.
 */
public class Etude {
        private String date;
        private String code_pays;
        private String libel;
        private String user;
        private String pdt;
        private String rfs;
        private int annee;
        private boolean fromBEA;
        private double montant;
        private String service;
        private String disc;
        private List<CoutPerso> coutPersos;
        private String pays;
        private String zone;
        private String status;


        public Etude(String date,String code_pays,String libel, String user, String pdt, String rfs, int annee, String service, String status, String zone, String pays, Double montant, List<CoutPerso> coutPersos, boolean flag) {
            this.code_pays = code_pays;
            this.libel = libel;
            this.user = user;
            this.pdt = pdt;
            this.rfs = rfs;
            this.annee = annee;
            this.service = service;
            this.disc = disc;
            this.coutPersos = coutPersos;
            this.date= date;
            this.montant = montant;
            this.pays = pays;
            this.zone = zone;
            this.status = status;
            this.fromBEA = flag;
        }

    public static boolean isOk(String path){
        String pattern = "[A-Z]{2}[-][0-9]{4}[_][a-zA-Z0-9]{1,5}[-][a-zA-Z0-9_]+(?:[-][A][1])*(?:[-][A][2])*(?:[-][A][3])*[-][V][A][L][-]";
        Pattern r = Pattern.compile(pattern);

        // Now create matcher object.
        Matcher m = r.matcher(path);
        return m.find();
    }

    public boolean isFromBEA() {
        return fromBEA;
    }

    public void setFromBEA(boolean fromBEA) {
        this.fromBEA = fromBEA;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCode_pays() {
        return code_pays;
    }

    public void setCode_pays(String code_pays) {
        this.code_pays = code_pays;
    }

    public String getLibel() {
        return libel;
    }

    public void setLibel(String libel) {
        this.libel = libel;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPdt() {
        return pdt;
    }

    public void setPdt(String pdt) {
        this.pdt = pdt;
    }

    public String getRfs() {
        return rfs;
    }

    public void setRfs(String rfs) {
        this.rfs = rfs;
    }

    public int getAnnee() {
        return annee;
    }

    public void setAnnee(int annee) {
        this.annee = annee;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getDisc() {
        return disc;
    }

    public void setDisc(String disc) {
        this.disc = disc;
    }

    public List<CoutPerso> getCoutPersos() {
        return coutPersos;
    }

    public void setCoutPersos(List<CoutPerso> coutPersos) {
        this.coutPersos = coutPersos;
    }


}
