package Servlet.Budget;

/**
 * Created by J0463664 on 24/11/2017.
 */
public class CoutPerso {
    private int id;
    private double nbh;
    private double tarif;
    private double total;
    private String service;


    public CoutPerso(int id, double nbh, double tarif, double total, String service) {
        this.id = id;
        this.nbh = nbh;
        this.tarif = tarif;
        this.total = total;
        this.service = service;
    }

    public CoutPerso(double montant, String service) {
        this.tarif = 0.29;
        this.total = montant;
        this.service = service;

    }

    public CoutPerso(String serv, Double nbH, Double tarif, Double total) {
        this.service = serv;
        this.nbh = nbH ;
        this.tarif = tarif;
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getNbh() {
        return nbh;
    }

    public void setNbh(double nbh) {
        this.nbh = nbh;
    }

    public double getTarif() {
        return tarif;
    }

    public void setTarif(double tarif) {
        this.tarif = tarif;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }
}
