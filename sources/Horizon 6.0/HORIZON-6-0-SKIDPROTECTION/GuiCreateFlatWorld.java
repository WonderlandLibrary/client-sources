package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;

public class GuiCreateFlatWorld extends GuiScreen
{
    private final GuiCreateWorld HorizonCode_Horizon_È;
    private FlatGeneratorInfo Â;
    private String Ý;
    private String Ø­áŒŠá;
    private String Âµá€;
    private HorizonCode_Horizon_È Ó;
    private GuiButton à;
    private GuiButton Ø;
    private GuiButton áŒŠÆ;
    private static final String áˆºÑ¢Õ = "CL_00000687";
    
    public GuiCreateFlatWorld(final GuiCreateWorld p_i1029_1_, final String p_i1029_2_) {
        this.Â = FlatGeneratorInfo.Âµá€();
        this.HorizonCode_Horizon_È = p_i1029_1_;
        this.HorizonCode_Horizon_È(p_i1029_2_);
    }
    
    public String Ó() {
        return this.Â.toString();
    }
    
    public void HorizonCode_Horizon_È(final String p_146383_1_) {
        this.Â = FlatGeneratorInfo.HorizonCode_Horizon_È(p_146383_1_);
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
        this.ÇŽÉ.clear();
        this.Ý = I18n.HorizonCode_Horizon_È("createWorld.customize.flat.title", new Object[0]);
        this.Ø­áŒŠá = I18n.HorizonCode_Horizon_È("createWorld.customize.flat.tile", new Object[0]);
        this.Âµá€ = I18n.HorizonCode_Horizon_È("createWorld.customize.flat.height", new Object[0]);
        this.Ó = new HorizonCode_Horizon_È();
        this.ÇŽÉ.add(this.à = new GuiButton(2, GuiCreateFlatWorld.Çªà¢ / 2 - 154, GuiCreateFlatWorld.Ê - 52, 100, 20, String.valueOf(I18n.HorizonCode_Horizon_È("createWorld.customize.flat.addLayer", new Object[0])) + " (NYI)"));
        this.ÇŽÉ.add(this.Ø = new GuiButton(3, GuiCreateFlatWorld.Çªà¢ / 2 - 50, GuiCreateFlatWorld.Ê - 52, 100, 20, String.valueOf(I18n.HorizonCode_Horizon_È("createWorld.customize.flat.editLayer", new Object[0])) + " (NYI)"));
        this.ÇŽÉ.add(this.áŒŠÆ = new GuiButton(4, GuiCreateFlatWorld.Çªà¢ / 2 - 155, GuiCreateFlatWorld.Ê - 52, 150, 20, I18n.HorizonCode_Horizon_È("createWorld.customize.flat.removeLayer", new Object[0])));
        this.ÇŽÉ.add(new GuiButton(0, GuiCreateFlatWorld.Çªà¢ / 2 - 155, GuiCreateFlatWorld.Ê - 28, 150, 20, I18n.HorizonCode_Horizon_È("gui.done", new Object[0])));
        this.ÇŽÉ.add(new GuiButton(5, GuiCreateFlatWorld.Çªà¢ / 2 + 5, GuiCreateFlatWorld.Ê - 52, 150, 20, I18n.HorizonCode_Horizon_È("createWorld.customize.presets", new Object[0])));
        this.ÇŽÉ.add(new GuiButton(1, GuiCreateFlatWorld.Çªà¢ / 2 + 5, GuiCreateFlatWorld.Ê - 28, 150, 20, I18n.HorizonCode_Horizon_È("gui.cancel", new Object[0])));
        final GuiButton à = this.à;
        final GuiButton ø = this.Ø;
        final boolean b = false;
        ø.ˆà = b;
        à.ˆà = b;
        this.Â.Ø­áŒŠá();
        this.à();
    }
    
    @Override
    public void n_() throws IOException {
        super.n_();
        this.Ó.Ø();
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final GuiButton button) throws IOException {
        final int var2 = this.Â.Ý().size() - this.Ó.HorizonCode_Horizon_È - 1;
        if (button.£à == 1) {
            GuiCreateFlatWorld.Ñ¢á.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È);
        }
        else if (button.£à == 0) {
            this.HorizonCode_Horizon_È.HorizonCode_Horizon_È = this.Ó();
            GuiCreateFlatWorld.Ñ¢á.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È);
        }
        else if (button.£à == 5) {
            GuiCreateFlatWorld.Ñ¢á.HorizonCode_Horizon_È(new GuiFlatPresets(this));
        }
        else if (button.£à == 4 && this.Ø()) {
            this.Â.Ý().remove(var2);
            this.Ó.HorizonCode_Horizon_È = Math.min(this.Ó.HorizonCode_Horizon_È, this.Â.Ý().size() - 1);
        }
        this.Â.Ø­áŒŠá();
        this.à();
    }
    
    public void à() {
        final boolean var1 = this.Ø();
        this.áŒŠÆ.µà = var1;
        this.Ø.µà = var1;
        this.Ø.µà = false;
        this.à.µà = false;
    }
    
    private boolean Ø() {
        return this.Ó.HorizonCode_Horizon_È > -1 && this.Ó.HorizonCode_Horizon_È < this.Â.Ý().size();
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int mouseX, final int mouseY, final float partialTicks) {
        this.£á();
        this.Ó.HorizonCode_Horizon_È(mouseX, mouseY, partialTicks);
        this.HorizonCode_Horizon_È(this.É, this.Ý, GuiCreateFlatWorld.Çªà¢ / 2, 8, 16777215);
        final int var4 = GuiCreateFlatWorld.Çªà¢ / 2 - 92 - 16;
        Gui_1808253012.Â(this.É, this.Ø­áŒŠá, var4, 32, 16777215);
        Gui_1808253012.Â(this.É, this.Âµá€, var4 + 2 + 213 - this.É.HorizonCode_Horizon_È(this.Âµá€), 32, 16777215);
        super.HorizonCode_Horizon_È(mouseX, mouseY, partialTicks);
    }
    
    class HorizonCode_Horizon_È extends GuiSlot
    {
        public int HorizonCode_Horizon_È;
        private static final String Šáƒ = "CL_00000688";
        
        public HorizonCode_Horizon_È() {
            super(GuiCreateFlatWorld.Ñ¢á, GuiCreateFlatWorld.Çªà¢, GuiCreateFlatWorld.Ê, 43, GuiCreateFlatWorld.Ê - 60, 24);
            this.HorizonCode_Horizon_È = -1;
        }
        
        private void HorizonCode_Horizon_È(final int p_148225_1_, final int p_148225_2_, final ItemStack p_148225_3_) {
            this.Âµá€(p_148225_1_ + 1, p_148225_2_ + 1);
            GlStateManager.ŠÄ();
            if (p_148225_3_ != null && p_148225_3_.HorizonCode_Horizon_È() != null) {
                RenderHelper.Ý();
                GuiCreateFlatWorld.this.ŒÏ.HorizonCode_Horizon_È(p_148225_3_, p_148225_1_ + 2, p_148225_2_ + 2);
                RenderHelper.HorizonCode_Horizon_È();
            }
            GlStateManager.Ñ¢á();
        }
        
        private void Âµá€(final int p_148226_1_, final int p_148226_2_) {
            this.Ø­áŒŠá(p_148226_1_, p_148226_2_, 0, 0);
        }
        
        private void Ø­áŒŠá(final int p_148224_1_, final int p_148224_2_, final int p_148224_3_, final int p_148224_4_) {
            GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
            this.Ý.¥à().HorizonCode_Horizon_È(Gui_1808253012.Ï­Ðƒà);
            final float var5 = 0.0078125f;
            final float var6 = 0.0078125f;
            final boolean var7 = true;
            final boolean var8 = true;
            final Tessellator var9 = Tessellator.HorizonCode_Horizon_È();
            final WorldRenderer var10 = var9.Ý();
            var10.Â();
            var10.HorizonCode_Horizon_È(p_148224_1_ + 0, p_148224_2_ + 18, GuiCreateFlatWorld.ŠÄ, (p_148224_3_ + 0) * 0.0078125f, (p_148224_4_ + 18) * 0.0078125f);
            var10.HorizonCode_Horizon_È(p_148224_1_ + 18, p_148224_2_ + 18, GuiCreateFlatWorld.ŠÄ, (p_148224_3_ + 18) * 0.0078125f, (p_148224_4_ + 18) * 0.0078125f);
            var10.HorizonCode_Horizon_È(p_148224_1_ + 18, p_148224_2_ + 0, GuiCreateFlatWorld.ŠÄ, (p_148224_3_ + 18) * 0.0078125f, (p_148224_4_ + 0) * 0.0078125f);
            var10.HorizonCode_Horizon_È(p_148224_1_ + 0, p_148224_2_ + 0, GuiCreateFlatWorld.ŠÄ, (p_148224_3_ + 0) * 0.0078125f, (p_148224_4_ + 0) * 0.0078125f);
            var9.Â();
        }
        
        @Override
        protected int HorizonCode_Horizon_È() {
            return GuiCreateFlatWorld.this.Â.Ý().size();
        }
        
        @Override
        protected void HorizonCode_Horizon_È(final int slotIndex, final boolean isDoubleClick, final int mouseX, final int mouseY) {
            this.HorizonCode_Horizon_È = slotIndex;
            GuiCreateFlatWorld.this.à();
        }
        
        @Override
        protected boolean HorizonCode_Horizon_È(final int slotIndex) {
            return slotIndex == this.HorizonCode_Horizon_È;
        }
        
        @Override
        protected void Â() {
        }
        
        @Override
        protected void HorizonCode_Horizon_È(final int p_180791_1_, final int p_180791_2_, final int p_180791_3_, final int p_180791_4_, final int p_180791_5_, final int p_180791_6_) {
            final FlatLayerInfo var7 = GuiCreateFlatWorld.this.Â.Ý().get(GuiCreateFlatWorld.this.Â.Ý().size() - p_180791_1_ - 1);
            final IBlockState var8 = var7.Â();
            final Block var9 = var8.Ý();
            Item_1028566121 var10 = Item_1028566121.HorizonCode_Horizon_È(var9);
            ItemStack var11 = (var9 != Blocks.Â && var10 != null) ? new ItemStack(var10, 1, var9.Ý(var8)) : null;
            String var12 = (var11 == null) ? "Air" : var10.à(var11);
            if (var10 == null) {
                if (var9 != Blocks.ÂµÈ && var9 != Blocks.áˆºÑ¢Õ) {
                    if (var9 == Blocks.ˆÏ­ || var9 == Blocks.á) {
                        var10 = Items.¥Ä;
                    }
                }
                else {
                    var10 = Items.ˆÓ;
                }
                if (var10 != null) {
                    var11 = new ItemStack(var10, 1, var9.Ý(var8));
                    var12 = var9.ŒÏ();
                }
            }
            this.HorizonCode_Horizon_È(p_180791_2_, p_180791_3_, var11);
            GuiCreateFlatWorld.this.É.HorizonCode_Horizon_È(var12, p_180791_2_ + 18 + 5, p_180791_3_ + 3, 16777215);
            String var13;
            if (p_180791_1_ == 0) {
                var13 = I18n.HorizonCode_Horizon_È("createWorld.customize.flat.layer.top", var7.HorizonCode_Horizon_È());
            }
            else if (p_180791_1_ == GuiCreateFlatWorld.this.Â.Ý().size() - 1) {
                var13 = I18n.HorizonCode_Horizon_È("createWorld.customize.flat.layer.bottom", var7.HorizonCode_Horizon_È());
            }
            else {
                var13 = I18n.HorizonCode_Horizon_È("createWorld.customize.flat.layer", var7.HorizonCode_Horizon_È());
            }
            GuiCreateFlatWorld.this.É.HorizonCode_Horizon_È(var13, p_180791_2_ + 2 + 213 - GuiCreateFlatWorld.this.É.HorizonCode_Horizon_È(var13), p_180791_3_ + 3, 16777215);
        }
        
        @Override
        protected int à() {
            return this.Ø­áŒŠá - 70;
        }
    }
}
