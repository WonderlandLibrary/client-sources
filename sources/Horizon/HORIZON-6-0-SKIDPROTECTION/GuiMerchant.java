package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;
import io.netty.buffer.Unpooled;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GuiMerchant extends GuiContainer
{
    private static final Logger HorizonCode_Horizon_È;
    private static final ResourceLocation_1975012498 Â;
    private IMerchant Ý;
    private HorizonCode_Horizon_È Ø­áŒŠá;
    private HorizonCode_Horizon_È Âµá€;
    private int Ó;
    private IChatComponent à;
    private static final String Ø = "CL_00000762";
    
    static {
        HorizonCode_Horizon_È = LogManager.getLogger();
        Â = new ResourceLocation_1975012498("textures/gui/container/villager.png");
    }
    
    public GuiMerchant(final InventoryPlayer p_i45500_1_, final IMerchant p_i45500_2_, final World worldIn) {
        super(new ContainerMerchant(p_i45500_1_, p_i45500_2_, worldIn));
        this.Ý = p_i45500_2_;
        this.à = p_i45500_2_.Ý();
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
        super.HorizonCode_Horizon_È();
        final int var1 = (GuiMerchant.Çªà¢ - this.áˆºÑ¢Õ) / 2;
        final int var2 = (GuiMerchant.Ê - this.ÂµÈ) / 2;
        this.ÇŽÉ.add(this.Ø­áŒŠá = new HorizonCode_Horizon_È(1, var1 + 120 + 27, var2 + 24 - 1, true));
        this.ÇŽÉ.add(this.Âµá€ = new HorizonCode_Horizon_È(2, var1 + 36 - 19, var2 + 24 - 1, false));
        this.Ø­áŒŠá.µà = false;
        this.Âµá€.µà = false;
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final int mouseX, final int mouseY) {
        final String var3 = this.à.Ø();
        this.É.HorizonCode_Horizon_È(var3, this.áˆºÑ¢Õ / 2 - this.É.HorizonCode_Horizon_È(var3) / 2, 6, 4210752);
        this.É.HorizonCode_Horizon_È(I18n.HorizonCode_Horizon_È("container.inventory", new Object[0]), 8, this.ÂµÈ - 96 + 2, 4210752);
    }
    
    @Override
    public void Ý() {
        super.Ý();
        final MerchantRecipeList var1 = this.Ý.Â(GuiMerchant.Ñ¢á.á);
        if (var1 != null) {
            this.Ø­áŒŠá.µà = (this.Ó < var1.size() - 1);
            this.Âµá€.µà = (this.Ó > 0);
        }
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final GuiButton button) throws IOException {
        boolean var2 = false;
        if (button == this.Ø­áŒŠá) {
            ++this.Ó;
            final MerchantRecipeList var3 = this.Ý.Â(GuiMerchant.Ñ¢á.á);
            if (var3 != null && this.Ó >= var3.size()) {
                this.Ó = var3.size() - 1;
            }
            var2 = true;
        }
        else if (button == this.Âµá€) {
            --this.Ó;
            if (this.Ó < 0) {
                this.Ó = 0;
            }
            var2 = true;
        }
        if (var2) {
            ((ContainerMerchant)GuiMerchant.á).Ø­áŒŠá(this.Ó);
            final PacketBuffer var4 = new PacketBuffer(Unpooled.buffer());
            var4.writeInt(this.Ó);
            GuiMerchant.Ñ¢á.µÕ().HorizonCode_Horizon_È(new C17PacketCustomPayload("MC|TrSel", var4));
        }
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final float partialTicks, final int mouseX, final int mouseY) {
        GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
        GuiMerchant.Ñ¢á.¥à().HorizonCode_Horizon_È(GuiMerchant.Â);
        final int var4 = (GuiMerchant.Çªà¢ - this.áˆºÑ¢Õ) / 2;
        final int var5 = (GuiMerchant.Ê - this.ÂµÈ) / 2;
        this.Â(var4, var5, 0, 0, this.áˆºÑ¢Õ, this.ÂµÈ);
        final MerchantRecipeList var6 = this.Ý.Â(GuiMerchant.Ñ¢á.á);
        if (var6 != null && !var6.isEmpty()) {
            final int var7 = this.Ó;
            if (var7 < 0 || var7 >= var6.size()) {
                return;
            }
            final MerchantRecipe var8 = var6.get(var7);
            if (var8.Ø()) {
                GuiMerchant.Ñ¢á.¥à().HorizonCode_Horizon_È(GuiMerchant.Â);
                GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
                GlStateManager.Ó();
                this.Â(this.ˆÏ­ + 83, this.£á + 21, 212, 0, 28, 21);
                this.Â(this.ˆÏ­ + 83, this.£á + 51, 212, 0, 28, 21);
            }
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int mouseX, final int mouseY, final float partialTicks) {
        super.HorizonCode_Horizon_È(mouseX, mouseY, partialTicks);
        final MerchantRecipeList var4 = this.Ý.Â(GuiMerchant.Ñ¢á.á);
        if (var4 != null && !var4.isEmpty()) {
            final int var5 = (GuiMerchant.Çªà¢ - this.áˆºÑ¢Õ) / 2;
            final int var6 = (GuiMerchant.Ê - this.ÂµÈ) / 2;
            final int var7 = this.Ó;
            final MerchantRecipe var8 = var4.get(var7);
            final ItemStack var9 = var8.HorizonCode_Horizon_È();
            final ItemStack var10 = var8.Â();
            final ItemStack var11 = var8.Ø­áŒŠá();
            GlStateManager.Çªà¢();
            RenderHelper.Ý();
            GlStateManager.Ó();
            GlStateManager.ŠÄ();
            GlStateManager.à();
            GlStateManager.Âµá€();
            this.ŒÏ.HorizonCode_Horizon_È = 100.0f;
            this.ŒÏ.Â(var9, var5 + 36, var6 + 24);
            this.ŒÏ.HorizonCode_Horizon_È(this.É, var9, var5 + 36, var6 + 24);
            if (var10 != null) {
                this.ŒÏ.Â(var10, var5 + 62, var6 + 24);
                this.ŒÏ.HorizonCode_Horizon_È(this.É, var10, var5 + 62, var6 + 24);
            }
            this.ŒÏ.Â(var11, var5 + 120, var6 + 24);
            this.ŒÏ.HorizonCode_Horizon_È(this.É, var11, var5 + 120, var6 + 24);
            this.ŒÏ.HorizonCode_Horizon_È = 0.0f;
            GlStateManager.Ó();
            if (this.Ý(36, 24, 16, 16, mouseX, mouseY) && var9 != null) {
                this.HorizonCode_Horizon_È(var9, mouseX, mouseY);
            }
            else if (var10 != null && this.Ý(62, 24, 16, 16, mouseX, mouseY) && var10 != null) {
                this.HorizonCode_Horizon_È(var10, mouseX, mouseY);
            }
            else if (var11 != null && this.Ý(120, 24, 16, 16, mouseX, mouseY) && var11 != null) {
                this.HorizonCode_Horizon_È(var11, mouseX, mouseY);
            }
            else if (var8.Ø() && (this.Ý(83, 21, 28, 21, mouseX, mouseY) || this.Ý(83, 51, 28, 21, mouseX, mouseY))) {
                this.HorizonCode_Horizon_È(I18n.HorizonCode_Horizon_È("merchant.deprecated", new Object[0]), mouseX, mouseY);
            }
            GlStateManager.Ê();
            GlStateManager.Âµá€();
            GlStateManager.áˆºÑ¢Õ();
            RenderHelper.Â();
        }
    }
    
    public IMerchant Ó() {
        return this.Ý;
    }
    
    static class HorizonCode_Horizon_È extends GuiButton
    {
        private final boolean HorizonCode_Horizon_È;
        private static final String Â = "CL_00000763";
        
        public HorizonCode_Horizon_È(final int p_i1095_1_, final int p_i1095_2_, final int p_i1095_3_, final boolean p_i1095_4_) {
            super(p_i1095_1_, p_i1095_2_, p_i1095_3_, 12, 19, "");
            this.HorizonCode_Horizon_È = p_i1095_4_;
        }
        
        @Override
        public void Ý(final Minecraft mc, final int mouseX, final int mouseY) {
            if (this.ˆà) {
                mc.¥à().HorizonCode_Horizon_È(GuiMerchant.Â);
                GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
                final boolean var4 = mouseX >= this.ˆÏ­ && mouseY >= this.£á && mouseX < this.ˆÏ­ + this.ÂµÈ && mouseY < this.£á + this.á;
                int var5 = 0;
                int var6 = 176;
                if (!this.µà) {
                    var6 += this.ÂµÈ * 2;
                }
                else if (var4) {
                    var6 += this.ÂµÈ;
                }
                if (!this.HorizonCode_Horizon_È) {
                    var5 += this.á;
                }
                this.Â(this.ˆÏ­, this.£á, var6, var5, this.ÂµÈ, this.á);
            }
        }
    }
}
