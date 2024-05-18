package wwe;

public class aRkr
{
    private String oBYy;
    private String HObA;
    private boolean tOID;
    
    public aRkr(final String obYy, final String hObA) {
        super();
        this.tOID = true;
        this.oBYy = obYy;
        this.HObA = hObA;
    }
    
    public aRkr(final String obYy) {
        super();
        this.tOID = false;
        this.oBYy = obYy;
        this.HObA = "N/A";
    }
    
    public String poph() {
        if (this.tOID) {
            return this.oBYy.concat(":").concat(this.HObA);
        }
        return this.oBYy;
    }
    
    public String dcDV() {
        return this.oBYy;
    }
    
    public String WLJz() throws ZqPV {
        if (this.tOID) {
            return this.HObA;
        }
        throw new ZqPV("Non-Premium accounts do not have passwords!");
    }
    
    public boolean RZeI() {
        return this.tOID;
    }
}
