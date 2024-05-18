package HORIZON-6-0-SKIDPROTECTION;

import java.util.Arrays;
import org.apache.commons.lang3.ArrayUtils;

public class GuiKeyBindingList extends GuiListExtended
{
    private final GuiControls HorizonCode_Horizon_È;
    private final Minecraft Â;
    private final GuiListExtended.HorizonCode_Horizon_È[] Šáƒ;
    private int Ï­Ðƒà;
    private static final String áŒŠà = "CL_00000732";
    
    public GuiKeyBindingList(final GuiControls p_i45031_1_, final Minecraft mcIn) {
        super(mcIn, GuiControls.Çªà¢, GuiControls.Ê, 63, GuiControls.Ê - 32, 20);
        this.Ï­Ðƒà = 0;
        this.HorizonCode_Horizon_È = p_i45031_1_;
        this.Â = mcIn;
        final KeyBinding[] var3 = (KeyBinding[])ArrayUtils.clone((Object[])mcIn.ŠÄ.ÇŽØ);
        this.Šáƒ = new GuiListExtended.HorizonCode_Horizon_È[var3.length + KeyBinding.Ý().size()];
        Arrays.sort(var3);
        int var4 = 0;
        String var5 = null;
        final KeyBinding[] var6 = var3;
        for (int var7 = var3.length, var8 = 0; var8 < var7; ++var8) {
            final KeyBinding var9 = var6[var8];
            final String var10 = var9.Âµá€();
            if (!var10.equals(var5)) {
                var5 = var10;
                this.Šáƒ[var4++] = new HorizonCode_Horizon_È(var10);
            }
            final int var11 = mcIn.µà.HorizonCode_Horizon_È(I18n.HorizonCode_Horizon_È(var9.à(), new Object[0]));
            if (var11 > this.Ï­Ðƒà) {
                this.Ï­Ðƒà = var11;
            }
            this.Šáƒ[var4++] = new Â(var9, null);
        }
    }
    
    @Override
    protected int HorizonCode_Horizon_È() {
        return this.Šáƒ.length;
    }
    
    @Override
    public GuiListExtended.HorizonCode_Horizon_È Â(final int p_148180_1_) {
        return this.Šáƒ[p_148180_1_];
    }
    
    @Override
    protected int à() {
        return super.à() + 15;
    }
    
    @Override
    public int o_() {
        return super.o_() + 32;
    }
    
    public class HorizonCode_Horizon_È implements GuiListExtended.HorizonCode_Horizon_È
    {
        private final String Â;
        private final int Ý;
        private static final String Ø­áŒŠá = "CL_00000734";
        
        public HorizonCode_Horizon_È(final String p_i45028_2_) {
            this.Â = I18n.HorizonCode_Horizon_È(p_i45028_2_, new Object[0]);
            this.Ý = GuiKeyBindingList.this.Â.µà.HorizonCode_Horizon_È(this.Â);
        }
        
        @Override
        public void HorizonCode_Horizon_È(final int slotIndex, final int x, final int y, final int listWidth, final int slotHeight, final int mouseX, final int mouseY, final boolean isSelected) {
            final FontRenderer µà = GuiKeyBindingList.this.Â.µà;
            final String â = this.Â;
            final GuiScreen ¥æ = GuiKeyBindingList.this.Â.¥Æ;
            µà.HorizonCode_Horizon_È(â, GuiScreen.Çªà¢ / 2 - this.Ý / 2, y + slotHeight - GuiKeyBindingList.this.Â.µà.HorizonCode_Horizon_È - 1, 16777215);
        }
        
        @Override
        public boolean HorizonCode_Horizon_È(final int p_148278_1_, final int p_148278_2_, final int p_148278_3_, final int p_148278_4_, final int p_148278_5_, final int p_148278_6_) {
            return false;
        }
        
        @Override
        public void Â(final int slotIndex, final int x, final int y, final int mouseEvent, final int relativeX, final int relativeY) {
        }
        
        @Override
        public void HorizonCode_Horizon_È(final int p_178011_1_, final int p_178011_2_, final int p_178011_3_) {
        }
    }
    
    public class Â implements GuiListExtended.HorizonCode_Horizon_È
    {
        private final KeyBinding Â;
        private final String Ý;
        private final GuiButton Ø­áŒŠá;
        private final GuiButton Âµá€;
        private static final String Ó = "CL_00000735";
        
        private Â(final KeyBinding p_i45029_2_) {
            this.Â = p_i45029_2_;
            this.Ý = I18n.HorizonCode_Horizon_È(p_i45029_2_.à(), new Object[0]);
            this.Ø­áŒŠá = new GuiButton(0, 0, 0, 75, 18, I18n.HorizonCode_Horizon_È(p_i45029_2_.à(), new Object[0]));
            this.Âµá€ = new GuiButton(0, 0, 0, 50, 18, I18n.HorizonCode_Horizon_È("controls.reset", new Object[0]));
        }
        
        @Override
        public void HorizonCode_Horizon_È(final int slotIndex, final int x, final int y, final int listWidth, final int slotHeight, final int mouseX, final int mouseY, final boolean isSelected) {
            final boolean var9 = GuiKeyBindingList.this.HorizonCode_Horizon_È.Â == this.Â;
            GuiKeyBindingList.this.Â.µà.HorizonCode_Horizon_È(this.Ý, x + 90 - GuiKeyBindingList.this.Ï­Ðƒà, y + slotHeight / 2 - GuiKeyBindingList.this.Â.µà.HorizonCode_Horizon_È / 2, 16777215);
            this.Âµá€.ˆÏ­ = x + 190;
            this.Âµá€.£á = y;
            this.Âµá€.µà = (this.Â.áŒŠÆ() != this.Â.Ø());
            this.Âµá€.Ý(GuiKeyBindingList.this.Â, mouseX, mouseY);
            this.Ø­áŒŠá.ˆÏ­ = x + 105;
            this.Ø­áŒŠá.£á = y;
            this.Ø­áŒŠá.Å = GameSettings.HorizonCode_Horizon_È(this.Â.áŒŠÆ());
            boolean var10 = false;
            if (this.Â.áŒŠÆ() != 0) {
                for (final KeyBinding var14 : GuiKeyBindingList.this.Â.ŠÄ.ÇŽØ) {
                    if (var14 != this.Â && var14.áŒŠÆ() == this.Â.áŒŠÆ()) {
                        var10 = true;
                        break;
                    }
                }
            }
            if (var9) {
                this.Ø­áŒŠá.Å = EnumChatFormatting.£à + "> " + EnumChatFormatting.Å + this.Ø­áŒŠá.Å + EnumChatFormatting.£à + " <";
            }
            else if (var10) {
                this.Ø­áŒŠá.Å = EnumChatFormatting.ˆÏ­ + this.Ø­áŒŠá.Å;
            }
            this.Ø­áŒŠá.Ý(GuiKeyBindingList.this.Â, mouseX, mouseY);
        }
        
        @Override
        public boolean HorizonCode_Horizon_È(final int p_148278_1_, final int p_148278_2_, final int p_148278_3_, final int p_148278_4_, final int p_148278_5_, final int p_148278_6_) {
            if (this.Ø­áŒŠá.Â(GuiKeyBindingList.this.Â, p_148278_2_, p_148278_3_)) {
                GuiKeyBindingList.this.HorizonCode_Horizon_È.Â = this.Â;
                return true;
            }
            if (this.Âµá€.Â(GuiKeyBindingList.this.Â, p_148278_2_, p_148278_3_)) {
                GuiKeyBindingList.this.Â.ŠÄ.HorizonCode_Horizon_È(this.Â, this.Â.Ø());
                KeyBinding.Â();
                return true;
            }
            return false;
        }
        
        @Override
        public void Â(final int slotIndex, final int x, final int y, final int mouseEvent, final int relativeX, final int relativeY) {
            this.Ø­áŒŠá.HorizonCode_Horizon_È(x, y);
            this.Âµá€.HorizonCode_Horizon_È(x, y);
        }
        
        @Override
        public void HorizonCode_Horizon_È(final int p_178011_1_, final int p_178011_2_, final int p_178011_3_) {
        }
        
        Â(final GuiKeyBindingList list, final KeyBinding p_i45030_2_, final Object p_i45030_3_) {
            this(list, p_i45030_2_);
        }
    }
}
