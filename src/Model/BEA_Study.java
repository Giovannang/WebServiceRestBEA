package Model;

import Model.MTG_disciplines.MTG_disciplines;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
/**
 * Created by L0463664 on 29/05/2017.
 */
public class BEA_Study implements Serializable {
    private String project_leader;
    private String isCreatedInW;
    private String id;
    private String zone;
    private String reference;
    private String libelle_etude;
    private String date_debut;
    private String date_fin;
    private String origine_demande;
    private String prestataires;
    private String dominante_metier;
    private String libelle_service;
    private String libelle_pays;
    private String libelle_permis;
    private String util_matricule;
    private String libelle_puit;
    private String libelle_gisement;
    private String libelle_categorietech;
    private String libelle_filliale;
    private String statut_etude;
    private String montant_total;
    private String type_etude;
    private String pdt_valide;
    private String diffusion_restreinte;
    private String date_envoit_form;
    private String date_envoit_raport;
    private String form_satis_rempli;
    private String no_rfs;
    private String sigle_dptmnt;
    private String demandeur;
    private String bea_link;
    private String tag_discipline;
    private String warning_start;
    private String warning_end;
    private String warning_feedback;






    public BEA_Study(ResultSet rs) throws SQLException {
        this.id = rs.getString("id_bea");
        this.reference = rs.getString("reference");
        this.libelle_etude = rs.getString("libelle");
        this.date_debut = rs.getString("date_debut");
        this.date_fin = rs.getString("date_fin");
        this.libelle_service = rs.getString("zone");
        this.libelle_pays = rs.getString("pays");
        this.libelle_permis = rs.getString("block");
        this.project_leader = rs.getString("nom") + ' ' + rs.getString("prenom");
        this.util_matricule = rs.getString("igg");
        this.libelle_categorietech = rs.getString("catech");
        this.libelle_filliale ="";
        this.statut_etude = rs.getString("status");
        this.montant_total = "";
        this.type_etude = rs.getString("type");
        this.pdt_valide = rs.getString("pdt_valide");
        this.diffusion_restreinte = "";
        this.date_envoit_form = "";
        this.form_satis_rempli = rs.getString("form_satis_rempli");
        this.no_rfs = "";
        this.date_envoit_raport = rs.getString("date_envoit_rapport");
        this.sigle_dptmnt = "";
        this.demandeur = "";
        this.zone = rs.getString("zone");
        this.origine_demande = "";
        this.prestataires = "";
        this.dominante_metier = "";
        this.tag_discipline = rs.getString("discipline");
        this.setWarnings();
        this.isCreatedInW = (rs.getString("path")==null ? "" : rs.getString("path")) ;
        this.bea_link = "http://bea.ep.corp.local/BaseActivites/openUrl.jsp?mode=read&amp;currentModel=etude&amp;idActivite=" + this.id;
    }

    public BEA_Study(ResultSet rs, List<String[]> studiesCreated, MTG_disciplines mtg_disciplines) throws SQLException {
        this.id = rs.getString("ID");
        this.reference = rs.getString("REFERENCE").replaceAll(" - ","-");
        this.libelle_etude = rs.getString("LIBELLE_ETUDE");
        this.date_debut = rs.getString("DATE_DEBUT");
        this.date_fin = rs.getString("DATE_FIN");
        this.libelle_service = rs.getString("LIBELLE_SERVICE");
        this.libelle_pays = rs.getString("LIBELLE_PAYS");
        this.libelle_permis = (rs.getString("LIBELLE_PERMIS") == null) ? "UNKNOWN" : rs.getString("LIBELLE_PERMIS");
        this.project_leader = rs.getString("NOM") + ' ' + rs.getString("PRENOM");
        this.util_matricule = rs.getString("MATRICULE");
        this.libelle_categorietech = rs.getString("CATEGORIE_TECHNIQUE_LIBELLE");
        this.libelle_filliale = rs.getString("FILIALE_OPCO_LIBELLE");
        this.statut_etude = rs.getString("STATUT_ETUDE_LIBELLE");
        this.montant_total = rs.getString("ETUDE_MONTANT_TOTAL_PDT");
        this.type_etude = rs.getString("TYPE_ETUDE_LIBELLE");
        this.pdt_valide = rs.getString("UNEPDTVALIDE");
        this.diffusion_restreinte = rs.getString("DIFFUSION_RESTREINTE");
        this.date_envoit_form = rs.getString("DATE_ENVOI_FORMUL");
        this.form_satis_rempli = (rs.getString("DATE_ENVOI_FORMUL") == null) ? "0" : rs.getString("DATE_ENVOI_FORMUL");
        this.no_rfs = rs.getString("NO_RFS");
        this.date_envoit_raport = (rs.getString("ENVOI_RAPPORT_FINAL")==null) ? "0" : rs.getString("ENVOI_RAPPORT_FINAL");
        this.sigle_dptmnt = rs.getString("SIGLE_DEPARTEMENT");
        this.demandeur = rs.getString("ETUDE_DEMANDEUR");
        this.zone = rs.getString("DIG_LIBELLE");
        this.origine_demande = rs.getString("ORIGINE_DEMANDE_LIBELLE");
        this.prestataires = rs.getString("PRESTATAIRES_IMPLIQUES");
        this.dominante_metier = rs.getString("DOMINANTE_METIER_LIBELLE");


        this.bea_link = "http://bea.ep.corp.local/BaseActivites/openUrl.jsp?mode=read&amp;currentModel=etude&amp;idActivite=" + this.id;
        this.isCreatedInW = isStudyCreatedInW(studiesCreated);
        this.transformZone();
        this.setWarnings();
        this.tag_discipline = getTag(mtg_disciplines);



    }

    public void setWarnings() {
        LocalDate start = LocalDate.parse(this.date_debut.substring(0, 10));
        LocalDate end = LocalDate.parse(this.date_fin.substring(0, 10));
        LocalDate now = LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()).substring(0, 10));
        Long nbDaysRemaining = ChronoUnit.DAYS.between(end, now);
        this.warning_end = "0";
        if (!(this.statut_etude.equals("Terminée")) && !(this.statut_etude.equals("Refusée"))) {
            if (nbDaysRemaining > 0)
                this.warning_end = "111";
            else if (nbDaysRemaining >= (-7))
                this.warning_end = "11";
            else if (nbDaysRemaining > (-30))
                this.warning_end = "1";
        }

        this.warning_start = "0";
        nbDaysRemaining = ChronoUnit.DAYS.between(now, start);
        if (this.pdt_valide.equals("NON") && !(this.statut_etude.equals("Refusée"))) {
            if (nbDaysRemaining <= 0)
                this.warning_start = "111";
            else if (nbDaysRemaining <= 7)
                this.warning_start = "11";
            else if (nbDaysRemaining <= 30)
                this.warning_start = "1";
        }


        this.warning_feedback = ((this.type_etude.equals("Etude simple ")) && (this.date_envoit_raport != "0") && (this.form_satis_rempli.equals("0"))) ? "1" : "0";

    }

    public void createMemoWordFormat(BEA_Study study) throws IOException {
        //Blank Document
        XWPFDocument document= new XWPFDocument();
        //Write the Document in file system
        FileOutputStream out = new FileOutputStream(new File("createdocument.docx"));
        document.write(out);
        out.close();
        System.out.println("createdocument.docx written successully");

    }


    public String getTag(MTG_disciplines mtg_disciplines) {

        for (String[] discipline_old : mtg_disciplines.old.tags) {
            if (discipline_old[2].equals(this.util_matricule) & (discipline_old[3].equals(this.libelle_service))) {
                if (levenshteinDistance(this.libelle_categorietech, discipline_old[4]) < 7) {
                    return discipline_old[5];
                }
            }

        }

        for (String[] discipline_new : mtg_disciplines.current.tags) {
            if (discipline_new[2].equals(this.util_matricule) & (discipline_new[3].equals(this.libelle_service))) {
                return discipline_new[5];
            }
        }
        return "UNKNOWN";

    }


    public int levenshteinDistance(CharSequence lhs, CharSequence rhs) {
        int len0 = lhs.length() + 1;
        int len1 = rhs.length() + 1;

        // the array of distances
        int[] cost = new int[len0];
        int[] newcost = new int[len0];

        // initial cost of skipping prefix in String s0
        for (int i = 0; i < len0; i++) cost[i] = i;

        // dynamically computing the array of distances

        // transformation cost for each letter in s1
        for (int j = 1; j < len1; j++) {
            // initial cost of skipping prefix in String s1
            newcost[0] = j;

            // transformation cost for each letter in s0
            for (int i = 1; i < len0; i++) {
                // matching current letters in both strings
                int match = (lhs.charAt(i - 1) == rhs.charAt(j - 1)) ? 0 : 1;

                // computing cost for each transformation
                int cost_replace = cost[i - 1] + match;
                int cost_insert = cost[i] + 1;
                int cost_delete = newcost[i - 1] + 1;

                // keep minimum cost
                newcost[i] = Math.min(Math.min(cost_insert, cost_delete), cost_replace);
            }

            // swap cost/newcost arrays
            int[] swap = cost;
            cost = newcost;
            newcost = swap;
        }

        // the distance is the cost for transforming all letters in both strings
        return cost[len0 - 1];
    }

    public String isStudyCreatedInW(List<String[]> studiesCreated) {
        for (String[] study : studiesCreated) {
            if (this.reference.equals(study[3]))
                return study[2];
        }
        return "";
    }


    public void transformZone() {
        if (this.zone == null)
            this.zone = "UNKNOWN";
        else {
            switch (this.zone) {
                case "Moyen-Orient et Afrique du Nord":
                    this.zone = "MENA";
                    break;
                case "Afrique":
                    this.zone = "AFR";
                    break;
                case "Asie Pacifique APC":
                    this.zone = "APC";
                    break;
                case "Amériques":
                    this.zone = "AME";
                    break;
                default:
                    return;

            }
        }
    }

    public String to_string(){
        String result ="";

        result += "---" +  project_leader;
        result += "---" +  isCreatedInW;
        result += "---" +  id;
        result += "---" +  zone;
        result += "---" +  reference;
        result += "---" +  libelle_etude;
        result += "---" +  date_debut;
        result += "---" +  date_fin;
        result += "---" +  origine_demande;
        result += "---" +  prestataires;
        result += "---" +  dominante_metier;
        result += "---" +  libelle_service;
        result += "---" +  libelle_pays;
        result += "---" +  libelle_permis;
        result += "---" +  util_matricule;
        result += "---" +  libelle_puit;
        result += "---" +  libelle_gisement;
        result += "---" +  libelle_categorietech;
        result += "---" +  libelle_filliale;
        result += "---" +  statut_etude;
        result += "---" +  montant_total;
        result += "---" +  type_etude;
        result += "---" +  pdt_valide;
        result += "---" +  diffusion_restreinte;
        result += "---" +  date_envoit_form;
        result += "---" +  date_envoit_raport;
        result += "---" +  form_satis_rempli;
        result += "---" +  no_rfs;
        result += "---" +  sigle_dptmnt;
        result += "---" +  demandeur;
        result += "---" +  bea_link;
        result += "---" +  tag_discipline;
        result += "---" +  warning_start;
        result += "---" +  warning_end;
        result += "---" +  warning_feedback;
        return result;
    }

    public String getProject_leader() {
        return project_leader;
    }

    public void setProject_leader(String project_leader) {
        this.project_leader = project_leader;
    }

    public String getIsCreatedInW() {
        return isCreatedInW;
    }

    public void setIsCreatedInW(String isCreatedInW) {
        this.isCreatedInW = isCreatedInW;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getLibelle_etude() {
        return libelle_etude;
    }

    public void setLibelle_etude(String libelle_etude) {
        this.libelle_etude = libelle_etude;
    }

    public String getDate_debut() {
        return date_debut;
    }

    public void setDate_debut(String date_debut) {
        this.date_debut = date_debut;
    }

    public String getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(String date_fin) {
        this.date_fin = date_fin;
    }

    public String getOrigine_demande() {
        return origine_demande;
    }

    public void setOrigine_demande(String origine_demande) {
        this.origine_demande = origine_demande;
    }

    public String getPrestataires() {
        return prestataires;
    }

    public void setPrestataires(String prestataires) {
        this.prestataires = prestataires;
    }

    public String getDominante_metier() {
        return dominante_metier;
    }

    public void setDominante_metier(String dominante_metier) {
        this.dominante_metier = dominante_metier;
    }

    public String getLibelle_service() {
        return libelle_service;
    }

    public void setLibelle_service(String libelle_service) {
        this.libelle_service = libelle_service;
    }

    public String getLibelle_pays() {
        return libelle_pays;
    }

    public void setLibelle_pays(String libelle_pays) {
        this.libelle_pays = libelle_pays;
    }

    public String getLibelle_permis() {
        return libelle_permis;
    }

    public void setLibelle_permis(String libelle_permis) {
        this.libelle_permis = libelle_permis;
    }

    public String getUtil_matricule() {
        return util_matricule;
    }

    public void setUtil_matricule(String util_matricule) {
        this.util_matricule = util_matricule;
    }

    public String getLibelle_puit() {
        return libelle_puit;
    }

    public void setLibelle_puit(String libelle_puit) {
        this.libelle_puit = libelle_puit;
    }

    public String getLibelle_gisement() {
        return libelle_gisement;
    }

    public void setLibelle_gisement(String libelle_gisement) {
        this.libelle_gisement = libelle_gisement;
    }

    public String getLibelle_categorietech() {
        return libelle_categorietech;
    }

    public void setLibelle_categorietech(String libelle_categorietech) {
        this.libelle_categorietech = libelle_categorietech;
    }

    public String getLibelle_filliale() {
        return libelle_filliale;
    }

    public void setLibelle_filliale(String libelle_filliale) {
        this.libelle_filliale = libelle_filliale;
    }

    public String getStatut_etude() {
        return statut_etude;
    }

    public void setStatut_etude(String statut_etude) {
        this.statut_etude = statut_etude;
    }

    public String getMontant_total() {
        return montant_total;
    }

    public void setMontant_total(String montant_total) {
        this.montant_total = montant_total;
    }

    public String getType_etude() {
        return type_etude;
    }

    public void setType_etude(String type_etude) {
        this.type_etude = type_etude;
    }

    public String getPdt_valide() {
        return pdt_valide;
    }

    public void setPdt_valide(String pdt_valide) {
        this.pdt_valide = pdt_valide;
    }

    public String getDiffusion_restreinte() {
        return diffusion_restreinte;
    }

    public void setDiffusion_restreinte(String diffusion_restreinte) {
        this.diffusion_restreinte = diffusion_restreinte;
    }

    public String getDate_envoit_form() {
        return date_envoit_form;
    }

    public void setDate_envoit_form(String date_envoit_form) {
        this.date_envoit_form = date_envoit_form;
    }

    public String getDate_envoit_raport() {
        return date_envoit_raport;
    }

    public void setDate_envoit_raport(String date_envoit_raport) {
        this.date_envoit_raport = date_envoit_raport;
    }

    public String getForm_satis_rempli() {
        return form_satis_rempli;
    }

    public void setForm_satis_rempli(String form_satis_rempli) {
        this.form_satis_rempli = form_satis_rempli;
    }

    public String getNo_rfs() {
        return no_rfs;
    }

    public void setNo_rfs(String no_rfs) {
        this.no_rfs = no_rfs;
    }

    public String getSigle_dptmnt() {
        return sigle_dptmnt;
    }

    public void setSigle_dptmnt(String sigle_dptmnt) {
        this.sigle_dptmnt = sigle_dptmnt;
    }

    public String getDemandeur() {
        return demandeur;
    }

    public void setDemandeur(String demandeur) {
        this.demandeur = demandeur;
    }

    public String getBea_link() {
        return bea_link;
    }

    public void setBea_link(String bea_link) {
        this.bea_link = bea_link;
    }

    public String getTag_discipline() {
        return tag_discipline;
    }

    public void setTag_discipline(String tag_discipline) {
        this.tag_discipline = tag_discipline;
    }

    public String getWarning_start() {
        return warning_start;
    }

    public void setWarning_start(String warning_start) {
        this.warning_start = warning_start;
    }

    public String getWarning_end() {
        return warning_end;
    }

    public void setWarning_end(String warning_end) {
        this.warning_end = warning_end;
    }

    public String getWarning_feedback() {
        return warning_feedback;
    }

    public void setWarning_feedback(String warning_feedback) {
        this.warning_feedback = warning_feedback;
    }
}