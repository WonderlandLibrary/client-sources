package HORIZON-6-0-SKIDPROTECTION;

import java.util.List;

public class GuiResourcePackSelected extends GuiResourcePackList
{
    private static final String Šáƒ = "CL_00000827";
    
    public GuiResourcePackSelected(final Minecraft mcIn, final int p_i45056_2_, final int p_i45056_3_, final List p_i45056_4_) {
        super(mcIn, p_i45056_2_, p_i45056_3_, p_i45056_4_);
    }
    
    @Override
    protected String Ø­áŒŠá() {
        return I18n.HorizonCode_Horizon_È("resourcePack.selected.title", new Object[0]);
    }
}
