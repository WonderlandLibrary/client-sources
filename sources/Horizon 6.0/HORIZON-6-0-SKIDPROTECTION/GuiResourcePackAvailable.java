package HORIZON-6-0-SKIDPROTECTION;

import java.util.List;

public class GuiResourcePackAvailable extends GuiResourcePackList
{
    private static final String Šáƒ = "CL_00000824";
    
    public GuiResourcePackAvailable(final Minecraft mcIn, final int p_i45054_2_, final int p_i45054_3_, final List p_i45054_4_) {
        super(mcIn, p_i45054_2_, p_i45054_3_, p_i45054_4_);
    }
    
    @Override
    protected String Ø­áŒŠá() {
        return I18n.HorizonCode_Horizon_È("resourcePack.available.title", new Object[0]);
    }
}
