package Model.Sismage;

/**
 * Created by J0463664 on 18/04/2018.
 */
public class Leader {

    private String igg;
    private String nom;
    private String prenom;

    /**
     * No args constructor for use in serialization
     *
     */
    public Leader() {
    }

    /**
     *
     * @param prenom
     * @param nom
     * @param igg
     */
    public Leader(String igg, String nom, String prenom) {
        super();
        this.igg = igg;
        this.nom = nom;
        this.prenom = prenom;
    }

    public String getIgg() {
        return igg;
    }

    public void setIgg(String igg) {
        this.igg = igg;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

}

