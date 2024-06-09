package mcleaks;

public class RedeemResponse
{
    private String session;
    private String mcName;
    
    public String getSession() {
        return this.session;
    }
    
    public void setSession(final String session) {
        this.session = session;
    }
    
    public String getMcName() {
        return this.mcName;
    }
    
    public void setMcName(final String mcName) {
        this.mcName = mcName;
    }
}
