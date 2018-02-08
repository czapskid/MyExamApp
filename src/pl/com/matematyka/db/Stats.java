package pl.com.matematyka.db;
public class Stats 
{
    private String tresc;
    private int popr;
    private String czas_start;
    private String czas_stop;

    /**
     * @return the tresc
     */
    public String getTresc() {
        return tresc;
    }

    /**
     * @param tresc the tresc to set
     */
    public void setTresc(String tresc) {
        this.tresc = tresc;
    }

    /**
     * @return the popr
     */
    public int getPopr() {
        return popr;
    }

    /**
     * @param popr the popr to set
     */
    public void setPopr(int popr) {
        this.popr = popr;
    }

    /**
     * @return the czas_start
     */
    public String getCzas_start() {
        return czas_start;
    }

    /**
     * @param czas_start the czas_start to set
     */
    public void setCzas_start(String czas_start) {
        this.czas_start = czas_start;
    }

    /**
     * @return the czas_stop
     */
    public String getCzas_stop() {
        return czas_stop;
    }

    /**
     * @param czas_stop the czas_stop to set
     */
    public void setCzas_stop(String czas_stop) {
        this.czas_stop = czas_stop;
    }
}
