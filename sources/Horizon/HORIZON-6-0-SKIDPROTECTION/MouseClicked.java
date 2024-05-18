package HORIZON-6-0-SKIDPROTECTION;

public class MouseClicked extends Event
{
    private int HorizonCode_Horizon_È;
    
    public MouseClicked(final int button) {
        this.HorizonCode_Horizon_È = button;
    }
    
    public int Ý() {
        return this.HorizonCode_Horizon_È;
    }
}
