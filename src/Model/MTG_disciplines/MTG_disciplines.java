package Model.MTG_disciplines;

/**
 * Created by L0463664 on 04/07/2017.
 */
public class MTG_disciplines {
    public MTG_disciplines_old old;
    public MTG_disciplines_new current;

    public MTG_disciplines() {
        this.old = new MTG_disciplines_old();
        this.current = new MTG_disciplines_new();
    }
}
