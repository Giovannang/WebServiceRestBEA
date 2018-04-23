package Servlet.Budget;

/**
 * Created by J0463664 on 24/11/2017.
 */
public class ServTech {
    private String code_section;
    private String description;
    private String uow;
    private double nbuow;
    private double tarif;


    public ServTech(String code_section, String description, String uow, Double nbuow, double tarif) {
        this.code_section = code_section;
        this.description = description;
        this.uow = uow;
        this.nbuow = nbuow;
        this.tarif = tarif;
    }

    public String getCode_section() {
        return code_section;
    }

    public void setCode_section(String code_section) {
        this.code_section = code_section;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUow() {
        return uow;
    }

    public void setUow(String uow) {
        this.uow = uow;
    }

    public double getNbuow() {
        return nbuow;
    }

    public void setNbuow(double nbuow) {
        this.nbuow = nbuow;
    }

    public double getTarif() {
        return tarif;
    }

    public void setTarif(double tarif) {
        this.tarif = tarif;
    }
}
