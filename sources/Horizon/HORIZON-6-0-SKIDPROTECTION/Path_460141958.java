package HORIZON-6-0-SKIDPROTECTION;

import java.util.ArrayList;
import java.io.Serializable;

public class Path_460141958 implements Serializable
{
    private static final long HorizonCode_Horizon_È = 1L;
    private ArrayList Â;
    
    public Path_460141958() {
        this.Â = new ArrayList();
    }
    
    public int HorizonCode_Horizon_È() {
        return this.Â.size();
    }
    
    public HorizonCode_Horizon_È HorizonCode_Horizon_È(final int index) {
        return this.Â.get(index);
    }
    
    public int Â(final int index) {
        return this.HorizonCode_Horizon_È(index).Â;
    }
    
    public int Ý(final int index) {
        return this.HorizonCode_Horizon_È(index).Ý;
    }
    
    public void HorizonCode_Horizon_È(final int x, final int y) {
        this.Â.add(new HorizonCode_Horizon_È(x, y));
    }
    
    public void Â(final int x, final int y) {
        this.Â.add(0, new HorizonCode_Horizon_È(x, y));
    }
    
    public boolean Ý(final int x, final int y) {
        return this.Â.contains(new HorizonCode_Horizon_È(x, y));
    }
    
    public class HorizonCode_Horizon_È implements Serializable
    {
        private int Â;
        private int Ý;
        
        public HorizonCode_Horizon_È(final int x, final int y) {
            this.Â = x;
            this.Ý = y;
        }
        
        public int HorizonCode_Horizon_È() {
            return this.Â;
        }
        
        public int Â() {
            return this.Ý;
        }
        
        @Override
        public int hashCode() {
            return this.Â * this.Ý;
        }
        
        @Override
        public boolean equals(final Object other) {
            if (other instanceof HorizonCode_Horizon_È) {
                final HorizonCode_Horizon_È o = (HorizonCode_Horizon_È)other;
                return o.Â == this.Â && o.Ý == this.Ý;
            }
            return false;
        }
    }
}
