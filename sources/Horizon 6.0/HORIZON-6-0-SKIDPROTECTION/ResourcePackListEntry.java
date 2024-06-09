package HORIZON-6-0-SKIDPROTECTION;

import java.util.List;

public abstract class ResourcePackListEntry implements GuiListExtended.HorizonCode_Horizon_È
{
    private static final ResourceLocation_1975012498 Ý;
    protected final Minecraft HorizonCode_Horizon_È;
    protected final GuiScreenResourcePacks Â;
    private static final String Ø­áŒŠá = "CL_00000821";
    
    static {
        Ý = new ResourceLocation_1975012498("textures/gui/resource_packs.png");
    }
    
    public ResourcePackListEntry(final GuiScreenResourcePacks p_i45051_1_) {
        this.Â = p_i45051_1_;
        this.HorizonCode_Horizon_È = Minecraft.áŒŠà();
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int slotIndex, final int x, final int y, final int listWidth, final int slotHeight, final int mouseX, final int mouseY, final boolean isSelected) {
        this.Ý();
        GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
        Gui_1808253012.HorizonCode_Horizon_È(x, y, 0.0f, 0.0f, 32, 32, 32.0f, 32.0f);
        if ((this.HorizonCode_Horizon_È.ŠÄ.ÂµÕ || isSelected) && this.Ø­áŒŠá()) {
            this.HorizonCode_Horizon_È.¥à().HorizonCode_Horizon_È(ResourcePackListEntry.Ý);
            Gui_1808253012.HorizonCode_Horizon_È(x, y, x + 32, y + 32, -1601138544);
            GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
            final int var9 = mouseX - x;
            final int var10 = mouseY - y;
            if (this.Âµá€()) {
                if (var9 < 32) {
                    Gui_1808253012.HorizonCode_Horizon_È(x, y, 0.0f, 32.0f, 32, 32, 256.0f, 256.0f);
                }
                else {
                    Gui_1808253012.HorizonCode_Horizon_È(x, y, 0.0f, 0.0f, 32, 32, 256.0f, 256.0f);
                }
            }
            else {
                if (this.Ó()) {
                    if (var9 < 16) {
                        Gui_1808253012.HorizonCode_Horizon_È(x, y, 32.0f, 32.0f, 32, 32, 256.0f, 256.0f);
                    }
                    else {
                        Gui_1808253012.HorizonCode_Horizon_È(x, y, 32.0f, 0.0f, 32, 32, 256.0f, 256.0f);
                    }
                }
                if (this.à()) {
                    if (var9 < 32 && var9 > 16 && var10 < 16) {
                        Gui_1808253012.HorizonCode_Horizon_È(x, y, 96.0f, 32.0f, 32, 32, 256.0f, 256.0f);
                    }
                    else {
                        Gui_1808253012.HorizonCode_Horizon_È(x, y, 96.0f, 0.0f, 32, 32, 256.0f, 256.0f);
                    }
                }
                if (this.Ø()) {
                    if (var9 < 32 && var9 > 16 && var10 > 16) {
                        Gui_1808253012.HorizonCode_Horizon_È(x, y, 64.0f, 32.0f, 32, 32, 256.0f, 256.0f);
                    }
                    else {
                        Gui_1808253012.HorizonCode_Horizon_È(x, y, 64.0f, 0.0f, 32, 32, 256.0f, 256.0f);
                    }
                }
            }
        }
        String var11 = this.Â();
        final int var10 = this.HorizonCode_Horizon_È.µà.HorizonCode_Horizon_È(var11);
        if (var10 > 157) {
            var11 = String.valueOf(this.HorizonCode_Horizon_È.µà.HorizonCode_Horizon_È(var11, 157 - this.HorizonCode_Horizon_È.µà.HorizonCode_Horizon_È("..."))) + "...";
        }
        this.HorizonCode_Horizon_È.µà.HorizonCode_Horizon_È(var11, x + 32 + 2, (float)(y + 1), 16777215);
        final List var12 = this.HorizonCode_Horizon_È.µà.Ý(this.HorizonCode_Horizon_È(), 157);
        for (int var13 = 0; var13 < 2 && var13 < var12.size(); ++var13) {
            this.HorizonCode_Horizon_È.µà.HorizonCode_Horizon_È(var12.get(var13), x + 32 + 2, (float)(y + 12 + 10 * var13), 8421504);
        }
    }
    
    protected abstract String HorizonCode_Horizon_È();
    
    protected abstract String Â();
    
    protected abstract void Ý();
    
    protected boolean Ø­áŒŠá() {
        return true;
    }
    
    protected boolean Âµá€() {
        return !this.Â.HorizonCode_Horizon_È(this);
    }
    
    protected boolean Ó() {
        return this.Â.HorizonCode_Horizon_È(this);
    }
    
    protected boolean à() {
        final List var1 = this.Â.Â(this);
        final int var2 = var1.indexOf(this);
        return var2 > 0 && var1.get(var2 - 1).Ø­áŒŠá();
    }
    
    protected boolean Ø() {
        final List var1 = this.Â.Â(this);
        final int var2 = var1.indexOf(this);
        return var2 >= 0 && var2 < var1.size() - 1 && var1.get(var2 + 1).Ø­áŒŠá();
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final int p_148278_1_, final int p_148278_2_, final int p_148278_3_, final int p_148278_4_, final int p_148278_5_, final int p_148278_6_) {
        if (this.Ø­áŒŠá() && p_148278_5_ <= 32) {
            if (this.Âµá€()) {
                this.Â.Â(this).remove(this);
                this.Â.à().add(0, this);
                this.Â.Ø();
                return true;
            }
            if (p_148278_5_ < 16 && this.Ó()) {
                this.Â.Â(this).remove(this);
                this.Â.Ó().add(0, this);
                this.Â.Ø();
                return true;
            }
            if (p_148278_5_ > 16 && p_148278_6_ < 16 && this.à()) {
                final List var7 = this.Â.Â(this);
                final int var8 = var7.indexOf(this);
                var7.remove(this);
                var7.add(var8 - 1, this);
                this.Â.Ø();
                return true;
            }
            if (p_148278_5_ > 16 && p_148278_6_ > 16 && this.Ø()) {
                final List var7 = this.Â.Â(this);
                final int var8 = var7.indexOf(this);
                var7.remove(this);
                var7.add(var8 + 1, this);
                this.Â.Ø();
                return true;
            }
        }
        return false;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int p_178011_1_, final int p_178011_2_, final int p_178011_3_) {
    }
    
    @Override
    public void Â(final int slotIndex, final int x, final int y, final int mouseEvent, final int relativeX, final int relativeY) {
    }
}
