package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import java.util.Collection;

public abstract class InventoryEffectRenderer extends GuiContainer
{
    private boolean HorizonCode_Horizon_È;
    private static final String Â = "CL_00000755";
    
    public InventoryEffectRenderer(final Container p_i1089_1_) {
        super(p_i1089_1_);
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
        super.HorizonCode_Horizon_È();
        this.Ø();
    }
    
    protected void Ø() {
        if (!InventoryEffectRenderer.Ñ¢á.á.ÇŽÈ().isEmpty()) {
            this.ˆÏ­ = 160 + (InventoryEffectRenderer.Çªà¢ - this.áˆºÑ¢Õ - 200) / 2;
            this.HorizonCode_Horizon_È = true;
        }
        else {
            this.ˆÏ­ = (InventoryEffectRenderer.Çªà¢ - this.áˆºÑ¢Õ) / 2;
            this.HorizonCode_Horizon_È = false;
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int mouseX, final int mouseY, final float partialTicks) {
        super.HorizonCode_Horizon_È(mouseX, mouseY, partialTicks);
        if (this.HorizonCode_Horizon_È) {
            this.Ó();
        }
    }
    
    private void Ó() {
        final int var1 = this.ˆÏ­ - 124;
        int var2 = this.£á;
        final boolean var3 = true;
        final Collection var4 = InventoryEffectRenderer.Ñ¢á.á.ÇŽÈ();
        if (!var4.isEmpty()) {
            GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.Ó();
            int var5 = 33;
            if (var4.size() > 5) {
                var5 = 132 / (var4.size() - 1);
            }
            for (final PotionEffect var7 : InventoryEffectRenderer.Ñ¢á.á.ÇŽÈ()) {
                final Potion var8 = Potion.HorizonCode_Horizon_È[var7.HorizonCode_Horizon_È()];
                GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
                InventoryEffectRenderer.Ñ¢á.¥à().HorizonCode_Horizon_È(InventoryEffectRenderer.áŒŠÆ);
                this.Â(var1, var2, 0, 166, 140, 32);
                if (var8.Âµá€()) {
                    final int var9 = var8.Ó();
                    this.Â(var1 + 6, var2 + 7, 0 + var9 % 8 * 18, 198 + var9 / 8 * 18, 18, 18);
                }
                String var10 = I18n.HorizonCode_Horizon_È(var8.Ø­áŒŠá(), new Object[0]);
                if (var7.Ý() == 1) {
                    var10 = String.valueOf(var10) + " " + I18n.HorizonCode_Horizon_È("enchantment.level.2", new Object[0]);
                }
                else if (var7.Ý() == 2) {
                    var10 = String.valueOf(var10) + " " + I18n.HorizonCode_Horizon_È("enchantment.level.3", new Object[0]);
                }
                else if (var7.Ý() == 3) {
                    var10 = String.valueOf(var10) + " " + I18n.HorizonCode_Horizon_È("enchantment.level.4", new Object[0]);
                }
                this.É.HorizonCode_Horizon_È(var10, var1 + 10 + 18, (float)(var2 + 6), 16777215);
                final String var11 = Potion.HorizonCode_Horizon_È(var7);
                this.É.HorizonCode_Horizon_È(var11, var1 + 10 + 18, (float)(var2 + 6 + 10), 8355711);
                var2 += var5;
            }
        }
    }
}
