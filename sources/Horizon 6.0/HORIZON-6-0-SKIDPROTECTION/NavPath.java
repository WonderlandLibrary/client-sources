package HORIZON-6-0-SKIDPROTECTION;

import java.util.ArrayList;

public class NavPath
{
    private ArrayList HorizonCode_Horizon_È;
    
    public NavPath() {
        this.HorizonCode_Horizon_È = new ArrayList();
    }
    
    public void HorizonCode_Horizon_È(final Link link) {
        this.HorizonCode_Horizon_È.add(link);
    }
    
    public int HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È.size();
    }
    
    public float HorizonCode_Horizon_È(final int step) {
        return this.HorizonCode_Horizon_È.get(step).HorizonCode_Horizon_È();
    }
    
    public float Â(final int step) {
        return this.HorizonCode_Horizon_È.get(step).Â();
    }
    
    @Override
    public String toString() {
        return "[Path length=" + this.HorizonCode_Horizon_È() + "]";
    }
    
    public void Ý(final int i) {
        this.HorizonCode_Horizon_È.remove(i);
    }
}
