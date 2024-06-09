package HORIZON-6-0-SKIDPROTECTION;

public class IntArray
{
    private int[] HorizonCode_Horizon_È;
    private int Â;
    private int Ý;
    
    public IntArray(final int size) {
        this.HorizonCode_Horizon_È = null;
        this.Â = 0;
        this.Ý = 0;
        this.HorizonCode_Horizon_È = new int[size];
    }
    
    public void HorizonCode_Horizon_È(final int x) {
        this.HorizonCode_Horizon_È[this.Â] = x;
        ++this.Â;
        if (this.Ý < this.Â) {
            this.Ý = this.Â;
        }
    }
    
    public void HorizonCode_Horizon_È(final int pos, final int x) {
        this.HorizonCode_Horizon_È[pos] = x;
        if (this.Ý < pos) {
            this.Ý = pos;
        }
    }
    
    public void Â(final int pos) {
        this.Â = pos;
    }
    
    public void HorizonCode_Horizon_È(final int[] ints) {
        for (int len = ints.length, i = 0; i < len; ++i) {
            this.HorizonCode_Horizon_È[this.Â] = ints[i];
            ++this.Â;
        }
        if (this.Ý < this.Â) {
            this.Ý = this.Â;
        }
    }
    
    public int Ý(final int pos) {
        return this.HorizonCode_Horizon_È[pos];
    }
    
    public int[] HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È;
    }
    
    public void Â() {
        this.Â = 0;
        this.Ý = 0;
    }
    
    public int Ý() {
        return this.Ý;
    }
    
    public int Ø­áŒŠá() {
        return this.Â;
    }
}
