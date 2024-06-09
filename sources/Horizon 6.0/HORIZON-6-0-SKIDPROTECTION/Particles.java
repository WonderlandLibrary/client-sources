package HORIZON-6-0-SKIDPROTECTION;

import java.awt.Color;
import java.util.Random;

public class Particles
{
    public float HorizonCode_Horizon_È;
    public float Â;
    public int Ý;
    public double Ø­áŒŠá;
    public boolean Âµá€;
    public boolean Ó;
    public boolean à;
    public boolean Ø;
    public double áŒŠÆ;
    public TimeHelper áˆºÑ¢Õ;
    
    public Particles(final int x, final int y, final double size, final double speed) {
        this.áˆºÑ¢Õ = new TimeHelper();
        this.Ý = 200;
        this.Ø­áŒŠá = size;
        this.Â = y;
        this.HorizonCode_Horizon_È = x;
        this.áŒŠÆ = speed;
        this.Â(new Random().nextBoolean());
    }
    
    public void HorizonCode_Horizon_È() {
        if (this.Âµá€()) {
            return;
        }
        GuiUtils.HorizonCode_Horizon_È().HorizonCode_Horizon_È((int)this.HorizonCode_Horizon_È, (int)this.Â, (double)(float)this.Ø­áŒŠá, new Color(1.0f, 1.0f, 1.0f, this.Ý / 255.0f).getRGB());
    }
    
    public void Â() {
        if (this.Âµá€()) {
            return;
        }
        if (this.Ø­áŒŠá <= 0.0 || this.Ý <= 0) {
            this.HorizonCode_Horizon_È(true);
            return;
        }
        this.Ø­áŒŠá -= 0.0011;
        if (this.Ý()) {
            this.HorizonCode_Horizon_È += 0.5f;
        }
        else {
            this.HorizonCode_Horizon_È -= 0.5f;
        }
        this.Â += (float)(this.áŒŠÆ / 1.0);
        this.áˆºÑ¢Õ.Ø­áŒŠá();
    }
    
    public void HorizonCode_Horizon_È(final boolean dead) {
        this.à = dead;
    }
    
    public void Â(final boolean right) {
        this.Âµá€ = right;
    }
    
    public boolean Ý() {
        return this.Âµá€;
    }
    
    public void Ý(final boolean up) {
        this.Ó = up;
    }
    
    public boolean Ø­áŒŠá() {
        return this.Ó;
    }
    
    public boolean Âµá€() {
        return this.à;
    }
    
    public boolean Ó() {
        return this.Ø;
    }
    
    public void Ø­áŒŠá(final boolean triangle) {
        this.Ø = triangle;
    }
}
