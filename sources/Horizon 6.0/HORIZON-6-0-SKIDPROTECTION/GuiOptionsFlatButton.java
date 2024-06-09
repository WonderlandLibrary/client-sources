package HORIZON-6-0-SKIDPROTECTION;

public class GuiOptionsFlatButton extends GuiMenuButtonNoSlide
{
    private final GameSettings.HorizonCode_Horizon_È HorizonCode_Horizon_È;
    private static final String Â = "CL_00000676";
    
    public GuiOptionsFlatButton(final int p_i45011_1_, final int p_i45011_2_, final int p_i45011_3_, final String p_i45011_4_) {
        this(p_i45011_1_, p_i45011_2_, p_i45011_3_, null, p_i45011_4_);
    }
    
    public GuiOptionsFlatButton(final int p_i45012_1_, final int p_i45012_2_, final int p_i45012_3_, final int p_i45012_4_, final int p_i45012_5_, final String p_i45012_6_) {
        super(p_i45012_1_, p_i45012_2_, p_i45012_3_, p_i45012_4_, p_i45012_5_, p_i45012_6_);
        this.HorizonCode_Horizon_È = null;
    }
    
    public GuiOptionsFlatButton(final int p_i45013_1_, final int p_i45013_2_, final int p_i45013_3_, final GameSettings.HorizonCode_Horizon_È p_i45013_4_, final String p_i45013_5_) {
        super(p_i45013_1_, p_i45013_2_, p_i45013_3_, 150, 20, p_i45013_5_);
        this.HorizonCode_Horizon_È = p_i45013_4_;
    }
    
    public GameSettings.HorizonCode_Horizon_È Â() {
        return this.HorizonCode_Horizon_È;
    }
}
