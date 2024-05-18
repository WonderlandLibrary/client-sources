package HORIZON-6-0-SKIDPROTECTION;

public class BasicCommand implements Command
{
    private String HorizonCode_Horizon_È;
    
    public BasicCommand(final String name) {
        this.HorizonCode_Horizon_È = name;
    }
    
    public String HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È;
    }
    
    @Override
    public int hashCode() {
        return this.HorizonCode_Horizon_È.hashCode();
    }
    
    @Override
    public boolean equals(final Object other) {
        return other instanceof BasicCommand && ((BasicCommand)other).HorizonCode_Horizon_È.equals(this.HorizonCode_Horizon_È);
    }
    
    @Override
    public String toString() {
        return "[Command=" + this.HorizonCode_Horizon_È + "]";
    }
}
