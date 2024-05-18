package HORIZON-6-0-SKIDPROTECTION;

public class KeyControl implements Control
{
    private int HorizonCode_Horizon_È;
    
    public KeyControl(final int keycode) {
        this.HorizonCode_Horizon_È = keycode;
    }
    
    @Override
    public boolean equals(final Object o) {
        return o instanceof KeyControl && ((KeyControl)o).HorizonCode_Horizon_È == this.HorizonCode_Horizon_È;
    }
    
    @Override
    public int hashCode() {
        return this.HorizonCode_Horizon_È;
    }
}
