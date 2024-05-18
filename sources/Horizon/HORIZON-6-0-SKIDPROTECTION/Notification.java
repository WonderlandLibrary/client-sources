package HORIZON-6-0-SKIDPROTECTION;

import java.util.ArrayList;
import java.util.List;

public class Notification
{
    public List<String> HorizonCode_Horizon_È;
    public List<Integer> Â;
    public int Ý;
    public int Ø­áŒŠá;
    public boolean Âµá€;
    
    public Notification() {
        this.HorizonCode_Horizon_È = new ArrayList<String>();
        this.Â = new ArrayList<Integer>();
        this.Ý = 0;
        this.Ø­áŒŠá = 0;
        this.Âµá€ = false;
    }
    
    public void HorizonCode_Horizon_È() {
        if (this.HorizonCode_Horizon_È.isEmpty()) {
            return;
        }
        final String message = this.HorizonCode_Horizon_È.get(0);
        if (this.Âµá€) {
            ++this.Ø­áŒŠá;
            if (this.Ø­áŒŠá >= 60) {
                this.Ý -= 3;
                if (this.Ý <= 0) {
                    this.Âµá€ = false;
                    this.Ý = 0;
                    this.Ø­áŒŠá = 0;
                    this.HorizonCode_Horizon_È.remove(0);
                    this.Â.remove(0);
                }
            }
        }
        else {
            this.Ý += 3;
            if (this.Ý >= UIFonts.Ý.HorizonCode_Horizon_È(message) + 10) {
                this.Ý = UIFonts.Ý.HorizonCode_Horizon_È(message) + 10;
                this.Âµá€ = true;
            }
        }
        try {
            final int color = ColorUtil.HorizonCode_Horizon_È(200000000L, 1.0f).getRGB();
            GuiUtils.HorizonCode_Horizon_È().HorizonCode_Horizon_È(0.0f, 0.0f, this.Ý, 2.0f, color);
            GuiUtils.HorizonCode_Horizon_È().HorizonCode_Horizon_È(0.0f, 2.0f, this.Ý, 22.0f, -552529647);
            UIFonts.Ý.HorizonCode_Horizon_È(message, this.Ý - UIFonts.Ý.HorizonCode_Horizon_È(message) - 5, 6, 16777215);
        }
        catch (Exception ex) {}
    }
    
    public void HorizonCode_Horizon_È(final String message) {
        this.HorizonCode_Horizon_È.add(message);
        this.Â.add(-9581273);
    }
    
    public void HorizonCode_Horizon_È(final String message, final int color) {
        this.HorizonCode_Horizon_È.add(message);
        this.Â.add(color);
    }
}
