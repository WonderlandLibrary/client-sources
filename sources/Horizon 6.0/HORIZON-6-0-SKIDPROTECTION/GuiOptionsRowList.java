package HORIZON-6-0-SKIDPROTECTION;

import com.google.common.collect.Lists;
import java.util.List;

public class GuiOptionsRowList extends GuiListExtended
{
    private final List HorizonCode_Horizon_È;
    private static final String Â = "CL_00000677";
    
    public GuiOptionsRowList(final Minecraft mcIn, final int p_i45015_2_, final int p_i45015_3_, final int p_i45015_4_, final int p_i45015_5_, final int p_i45015_6_, final GameSettings.HorizonCode_Horizon_È... p_i45015_7_) {
        super(mcIn, p_i45015_2_, p_i45015_3_, p_i45015_4_, p_i45015_5_, p_i45015_6_);
        this.HorizonCode_Horizon_È = Lists.newArrayList();
        this.ˆÏ­ = false;
        for (int var8 = 0; var8 < p_i45015_7_.length; var8 += 2) {
            final GameSettings.HorizonCode_Horizon_È var9 = p_i45015_7_[var8];
            final GameSettings.HorizonCode_Horizon_È var10 = (var8 < p_i45015_7_.length - 1) ? p_i45015_7_[var8 + 1] : null;
            final GuiMenuButtonNoSlide var11 = (GuiMenuButtonNoSlide)this.HorizonCode_Horizon_È(mcIn, p_i45015_2_ / 2 - 155, 0, var9);
            final GuiMenuButtonNoSlide var12 = (GuiMenuButtonNoSlide)this.HorizonCode_Horizon_È(mcIn, p_i45015_2_ / 2 - 155 + 160, 0, var10);
            this.HorizonCode_Horizon_È.add(new HorizonCode_Horizon_È(var11, var12));
        }
    }
    
    private GuiButton HorizonCode_Horizon_È(final Minecraft mcIn, final int p_148182_2_, final int p_148182_3_, final GameSettings.HorizonCode_Horizon_È p_148182_4_) {
        if (p_148182_4_ == null) {
            return null;
        }
        final int var5 = p_148182_4_.Ý();
        return p_148182_4_.HorizonCode_Horizon_È() ? new GuiOptionFlatSlider(var5, p_148182_2_, p_148182_3_, p_148182_4_) : new GuiOptionsFlatButton(var5, p_148182_2_, p_148182_3_, p_148182_4_, mcIn.ŠÄ.Ý(p_148182_4_));
    }
    
    public HorizonCode_Horizon_È Ý(final int p_180792_1_) {
        return this.HorizonCode_Horizon_È.get(p_180792_1_);
    }
    
    @Override
    protected int HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È.size();
    }
    
    @Override
    public int o_() {
        return 400;
    }
    
    @Override
    protected int à() {
        return super.à() + 32;
    }
    
    @Override
    public GuiListExtended.HorizonCode_Horizon_È Â(final int p_148180_1_) {
        return this.Ý(p_148180_1_);
    }
    
    public static class HorizonCode_Horizon_È implements GuiListExtended.HorizonCode_Horizon_È
    {
        private final Minecraft HorizonCode_Horizon_È;
        private final GuiButton Â;
        private final GuiButton Ý;
        private static final String Ø­áŒŠá = "CL_00000678";
        
        public HorizonCode_Horizon_È(final GuiButton p_i45014_1_, final GuiButton p_i45014_2_) {
            this.HorizonCode_Horizon_È = Minecraft.áŒŠà();
            this.Â = p_i45014_1_;
            this.Ý = p_i45014_2_;
        }
        
        @Override
        public void HorizonCode_Horizon_È(final int slotIndex, final int x, final int y, final int listWidth, final int slotHeight, final int mouseX, final int mouseY, final boolean isSelected) {
            if (this.Â != null) {
                this.Â.£á = y;
                this.Â.Ý(this.HorizonCode_Horizon_È, mouseX, mouseY);
            }
            if (this.Ý != null) {
                this.Ý.£á = y;
                this.Ý.Ý(this.HorizonCode_Horizon_È, mouseX, mouseY);
            }
        }
        
        @Override
        public boolean HorizonCode_Horizon_È(final int p_148278_1_, final int p_148278_2_, final int p_148278_3_, final int p_148278_4_, final int p_148278_5_, final int p_148278_6_) {
            if (this.Â.Â(this.HorizonCode_Horizon_È, p_148278_2_, p_148278_3_)) {
                if (this.Â instanceof GuiOptionsFlatButton) {
                    this.HorizonCode_Horizon_È.ŠÄ.HorizonCode_Horizon_È(((GuiOptionsFlatButton)this.Â).Â(), 1);
                    this.Â.Å = this.HorizonCode_Horizon_È.ŠÄ.Ý(GameSettings.HorizonCode_Horizon_È.HorizonCode_Horizon_È(this.Â.£à));
                }
                return true;
            }
            if (this.Ý != null && this.Ý.Â(this.HorizonCode_Horizon_È, p_148278_2_, p_148278_3_)) {
                if (this.Ý instanceof GuiOptionsFlatButton) {
                    this.HorizonCode_Horizon_È.ŠÄ.HorizonCode_Horizon_È(((GuiOptionsFlatButton)this.Ý).Â(), 1);
                    this.Ý.Å = this.HorizonCode_Horizon_È.ŠÄ.Ý(GameSettings.HorizonCode_Horizon_È.HorizonCode_Horizon_È(this.Ý.£à));
                }
                return true;
            }
            return false;
        }
        
        @Override
        public void Â(final int slotIndex, final int x, final int y, final int mouseEvent, final int relativeX, final int relativeY) {
            if (this.Â != null) {
                this.Â.HorizonCode_Horizon_È(x, y);
            }
            if (this.Ý != null) {
                this.Ý.HorizonCode_Horizon_È(x, y);
            }
        }
        
        @Override
        public void HorizonCode_Horizon_È(final int p_178011_1_, final int p_178011_2_, final int p_178011_3_) {
        }
    }
}
