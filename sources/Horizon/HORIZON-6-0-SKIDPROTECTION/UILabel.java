package HORIZON-6-0-SKIDPROTECTION;

public class UILabel extends Item
{
    private String HorizonCode_Horizon_È;
    private FontRenderer à;
    
    public UILabel(final FontRenderer fr, final String text) {
        this.HorizonCode_Horizon_È = text;
        this.à = fr;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int i, final int j, final float k) {
        if (Horizon.à.equalsIgnoreCase("Arial")) {
            UIFonts.Ý.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È, this.Â(), this.Ý(), -1249039);
        }
        else if (Horizon.à.equalsIgnoreCase("SegoeUI")) {
            UIFonts.µà.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È, this.Â(), this.Ý() - 1, -1249039);
        }
        else if (Horizon.à.equalsIgnoreCase("Helvetica")) {
            UIFonts.Ø­à.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È, this.Â(), this.Ý() + 2, -1249039);
        }
        else if (Horizon.à.equalsIgnoreCase("Comfortaa")) {
            UIFonts.µÕ.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È, this.Â(), this.Ý() + 1, -1249039);
        }
        else if (Horizon.à.equalsIgnoreCase("Vibes")) {
            UIFonts.Æ.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È, this.Â(), this.Ý() + 3, -1249039);
        }
    }
    
    @Override
    public int Âµá€() {
        return this.à.HorizonCode_Horizon_È + 2;
    }
}
