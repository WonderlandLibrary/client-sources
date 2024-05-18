package HORIZON-6-0-SKIDPROTECTION;

public class IRCMessage
{
    private String HorizonCode_Horizon_È;
    private String Â;
    private boolean Ý;
    private int Ø­áŒŠá;
    
    public IRCMessage(final String by, final String message, final int color) {
        this.HorizonCode_Horizon_È = by;
        this.Â = message;
        this.Ø­áŒŠá = color;
    }
    
    public IRCMessage(final String by, final String message) {
        this.HorizonCode_Horizon_È = by;
        this.Â = message;
        this.Ø­áŒŠá = -39889;
    }
    
    public IRCMessage(final String by, final String message, final int color, final boolean rechtsRadikale) {
        this.HorizonCode_Horizon_È = by;
        this.Â = message;
        this.Ø­áŒŠá = color;
        this.Ý = rechtsRadikale;
    }
    
    public IRCMessage(final String by, final String message, final boolean rechtsRadikale) {
        this.HorizonCode_Horizon_È = by;
        this.Â = message;
        this.Ø­áŒŠá = -39889;
        this.Ý = rechtsRadikale;
    }
    
    public String HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È;
    }
    
    public int Â() {
        return this.Ø­áŒŠá;
    }
    
    public String Ý() {
        return this.Â;
    }
    
    public boolean Ø­áŒŠá() {
        return this.Ý;
    }
}
