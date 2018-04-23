package Model.Sismage;

/**
 * Created by J0463664 on 18/04/2018.
 */
public class Geo {

    private String pdt;
    private String country;
    private String block;
    private Leader leader;


    /**
     * No args constructor for use in serialization
     *
     */
    public Geo() {
    }

    /**
     *
     * @param leader
     * @param pdt
     * @param block
     * @param country
     */
    public Geo(String pdt, String country, String block, Leader leader) {
        super();
        this.pdt = pdt;
        this.country = country;
        this.block = block;
        this.leader = leader;
    }


    public String getPdt() {
        return pdt;
    }

    public void setPdt(String pdt) {
        this.pdt = pdt;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public Leader getLeader() {
        return leader;
    }

    public void setLeader(Leader leader) {
        this.leader = leader;
    }


}
