package HORIZON-6-0-SKIDPROTECTION;

public class MouseButtonControl implements Control
{
    private int HorizonCode_Horizon_È;
    
    public MouseButtonControl(final int button) {
        this.HorizonCode_Horizon_È = button;
    }
    
    @Override
    public boolean equals(final Object o) {
        return o instanceof MouseButtonControl && ((MouseButtonControl)o).HorizonCode_Horizon_È == this.HorizonCode_Horizon_È;
    }
    
    @Override
    public int hashCode() {
        return this.HorizonCode_Horizon_È;
    }
}
