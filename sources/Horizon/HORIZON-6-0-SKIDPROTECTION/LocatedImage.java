package HORIZON-6-0-SKIDPROTECTION;

public class LocatedImage
{
    private Image HorizonCode_Horizon_È;
    private int Â;
    private int Ý;
    private Color Ø­áŒŠá;
    private float Âµá€;
    private float Ó;
    
    public LocatedImage(final Image image, final int x, final int y) {
        this.Ø­áŒŠá = Color.Ý;
        this.HorizonCode_Horizon_È = image;
        this.Â = x;
        this.Ý = y;
        this.Âµá€ = image.ŒÏ();
        this.Ó = image.Çªà¢();
    }
    
    public float HorizonCode_Horizon_È() {
        return this.Ó;
    }
    
    public float Â() {
        return this.Âµá€;
    }
    
    public void HorizonCode_Horizon_È(final float height) {
        this.Ó = height;
    }
    
    public void Â(final float width) {
        this.Âµá€ = width;
    }
    
    public void HorizonCode_Horizon_È(final Color c) {
        this.Ø­áŒŠá = c;
    }
    
    public Color Ý() {
        return this.Ø­áŒŠá;
    }
    
    public void HorizonCode_Horizon_È(final int x) {
        this.Â = x;
    }
    
    public void Â(final int y) {
        this.Ý = y;
    }
    
    public int Ø­áŒŠá() {
        return this.Â;
    }
    
    public int Âµá€() {
        return this.Ý;
    }
    
    public void Ó() {
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(this.Â, this.Ý, this.Âµá€, this.Ó, this.Ø­áŒŠá);
    }
}
