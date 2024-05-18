package HORIZON-6-0-SKIDPROTECTION;

public class FlatSlider extends GuiMenuButtonNoSlide
{
    public float HorizonCode_Horizon_È;
    public boolean Â;
    
    public FlatSlider(final int id, final int x, final int y, final String label, final float startingValue) {
        super(id, x, y, 150, 20, label);
        this.HorizonCode_Horizon_È = 1.0f;
        this.Â = false;
        this.HorizonCode_Horizon_È = startingValue;
    }
    
    @Override
    protected int HorizonCode_Horizon_È(final boolean par1) {
        return 0;
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final Minecraft par1Minecraft, final int par2, final int par3) {
        if (this.ˆà) {
            if (this.Â) {
                this.HorizonCode_Horizon_È = (par2 - (this.ˆÏ­ + 4)) / (this.ÂµÈ - 8);
                if (this.HorizonCode_Horizon_È < 0.0f) {
                    this.HorizonCode_Horizon_È = 0.0f;
                }
                if (this.HorizonCode_Horizon_È > 1.0f) {
                    this.HorizonCode_Horizon_È = 1.0f;
                }
            }
            if (this.¥Æ || this.Â) {
                Gui_1808253012.HorizonCode_Horizon_È(this.ˆÏ­ + (int)(this.HorizonCode_Horizon_È * (this.ÂµÈ - 8)), this.£á, this.ˆÏ­ + (int)(this.HorizonCode_Horizon_È * (this.ÂµÈ - 8)) + 5, this.£á + 20, -1);
            }
            else {
                Gui_1808253012.HorizonCode_Horizon_È(this.ˆÏ­ + (int)(this.HorizonCode_Horizon_È * (this.ÂµÈ - 8)), this.£á, this.ˆÏ­ + (int)(this.HorizonCode_Horizon_È * (this.ÂµÈ - 8)) + 5, this.£á + 20, -1862270977);
            }
        }
    }
    
    @Override
    public boolean Â(final Minecraft par1Minecraft, final int par2, final int par3) {
        if (super.Â(par1Minecraft, par2, par3)) {
            this.HorizonCode_Horizon_È = (par2 - (this.ˆÏ­ + 4)) / (this.ÂµÈ - 8);
            if (this.HorizonCode_Horizon_È < 0.0f) {
                this.HorizonCode_Horizon_È = 0.0f;
            }
            if (this.HorizonCode_Horizon_È > 1.0f) {
                this.HorizonCode_Horizon_È = 1.0f;
            }
            return this.Â = true;
        }
        return false;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int par1, final int par2) {
        this.Â = false;
    }
}
