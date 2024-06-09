package HORIZON-6-0-SKIDPROTECTION;

import java.util.List;

public abstract class GuiResourcePackList extends GuiListExtended
{
    protected final Minecraft HorizonCode_Horizon_È;
    protected final List Â;
    private static final String Šáƒ = "CL_00000825";
    
    public GuiResourcePackList(final Minecraft mcIn, final int p_i45055_2_, final int p_i45055_3_, final List p_i45055_4_) {
        super(mcIn, p_i45055_2_, p_i45055_3_, 32, p_i45055_3_ - 55 + 4, 36);
        this.HorizonCode_Horizon_È = mcIn;
        this.Â = p_i45055_4_;
        this.ˆÏ­ = false;
        this.HorizonCode_Horizon_È(true, (int)(mcIn.µà.HorizonCode_Horizon_È * 1.5f));
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final int p_148129_1_, final int p_148129_2_, final Tessellator p_148129_3_) {
        final String var4 = new StringBuilder().append(EnumChatFormatting.Ø­à).append(EnumChatFormatting.ˆà).append(this.Ø­áŒŠá()).toString();
        this.HorizonCode_Horizon_È.µà.HorizonCode_Horizon_È(var4, p_148129_1_ + this.Ø­áŒŠá / 2 - this.HorizonCode_Horizon_È.µà.HorizonCode_Horizon_È(var4) / 2, Math.min(this.Ó + 3, p_148129_2_), 16777215);
    }
    
    protected abstract String Ø­áŒŠá();
    
    public List Âµá€() {
        return this.Â;
    }
    
    @Override
    protected int HorizonCode_Horizon_È() {
        return this.Âµá€().size();
    }
    
    public ResourcePackListEntry Ý(final int p_148180_1_) {
        return this.Âµá€().get(p_148180_1_);
    }
    
    @Override
    public int o_() {
        return this.Ø­áŒŠá;
    }
    
    @Override
    protected int à() {
        return this.Ø - 6;
    }
}
