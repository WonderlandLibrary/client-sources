package HORIZON-6-0-SKIDPROTECTION;

import java.util.ArrayList;
import java.util.List;
import com.google.common.collect.Lists;
import org.lwjgl.util.glu.Project;
import java.io.IOException;
import java.util.Random;

public class GuiEnchantment extends GuiContainer
{
    private static final ResourceLocation_1975012498 µà;
    private static final ResourceLocation_1975012498 ˆà;
    private static final ModelBook ¥Æ;
    private final InventoryPlayer Ø­à;
    private Random µÕ;
    private ContainerEnchantment Æ;
    public int HorizonCode_Horizon_È;
    public float Â;
    public float Ý;
    public float Ø­áŒŠá;
    public float Âµá€;
    public float Ó;
    public float à;
    ItemStack Ø;
    private final IWorldNameable áƒ;
    private static final String á€ = "CL_00000757";
    
    static {
        µà = new ResourceLocation_1975012498("textures/gui/container/enchanting_table.png");
        ˆà = new ResourceLocation_1975012498("textures/entity/enchanting_table_book.png");
        ¥Æ = new ModelBook();
    }
    
    public GuiEnchantment(final InventoryPlayer p_i45502_1_, final World worldIn, final IWorldNameable p_i45502_3_) {
        super(new ContainerEnchantment(p_i45502_1_, worldIn));
        this.µÕ = new Random();
        this.Ø­à = p_i45502_1_;
        this.Æ = (ContainerEnchantment)GuiEnchantment.á;
        this.áƒ = p_i45502_3_;
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final int mouseX, final int mouseY) {
        this.É.HorizonCode_Horizon_È(this.áƒ.Ý().Ø(), 12, 5, 4210752);
        this.É.HorizonCode_Horizon_È(this.Ø­à.Ý().Ø(), 8, this.ÂµÈ - 96 + 2, 4210752);
    }
    
    @Override
    public void Ý() {
        super.Ý();
        this.Ó();
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        super.HorizonCode_Horizon_È(mouseX, mouseY, mouseButton);
        final int var4 = (GuiEnchantment.Çªà¢ - this.áˆºÑ¢Õ) / 2;
        final int var5 = (GuiEnchantment.Ê - this.ÂµÈ) / 2;
        for (int var6 = 0; var6 < 3; ++var6) {
            final int var7 = mouseX - (var4 + 60);
            final int var8 = mouseY - (var5 + 14 + 19 * var6);
            if (var7 >= 0 && var8 >= 0 && var7 < 108 && var8 < 19 && this.Æ.Â(GuiEnchantment.Ñ¢á.á, var6)) {
                GuiEnchantment.Ñ¢á.Âµá€.HorizonCode_Horizon_È(this.Æ.Ø­áŒŠá, var6);
            }
        }
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final float partialTicks, final int mouseX, final int mouseY) {
        GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
        GuiEnchantment.Ñ¢á.¥à().HorizonCode_Horizon_È(GuiEnchantment.µà);
        final int var4 = (GuiEnchantment.Çªà¢ - this.áˆºÑ¢Õ) / 2;
        final int var5 = (GuiEnchantment.Ê - this.ÂµÈ) / 2;
        this.Â(var4, var5, 0, 0, this.áˆºÑ¢Õ, this.ÂµÈ);
        GlStateManager.Çªà¢();
        GlStateManager.á(5889);
        GlStateManager.Çªà¢();
        GlStateManager.ŒÏ();
        final ScaledResolution var6 = new ScaledResolution(GuiEnchantment.Ñ¢á, GuiEnchantment.Ñ¢á.Ó, GuiEnchantment.Ñ¢á.à);
        GlStateManager.Â((var6.HorizonCode_Horizon_È() - 320) / 2 * var6.Âµá€(), (var6.Â() - 240) / 2 * var6.Âµá€(), 320 * var6.Âµá€(), 240 * var6.Âµá€());
        GlStateManager.Â(-0.34f, 0.23f, 0.0f);
        Project.gluPerspective(90.0f, 1.3333334f, 9.0f, 80.0f);
        final float var7 = 1.0f;
        GlStateManager.á(5888);
        GlStateManager.ŒÏ();
        RenderHelper.Â();
        GlStateManager.Â(0.0f, 3.3f, -16.0f);
        GlStateManager.HorizonCode_Horizon_È(var7, var7, var7);
        final float var8 = 5.0f;
        GlStateManager.HorizonCode_Horizon_È(var8, var8, var8);
        GlStateManager.Â(180.0f, 0.0f, 0.0f, 1.0f);
        GuiEnchantment.Ñ¢á.¥à().HorizonCode_Horizon_È(GuiEnchantment.ˆà);
        GlStateManager.Â(20.0f, 1.0f, 0.0f, 0.0f);
        final float var9 = this.à + (this.Ó - this.à) * partialTicks;
        GlStateManager.Â((1.0f - var9) * 0.2f, (1.0f - var9) * 0.1f, (1.0f - var9) * 0.25f);
        GlStateManager.Â(-(1.0f - var9) * 90.0f - 90.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.Â(180.0f, 1.0f, 0.0f, 0.0f);
        float var10 = this.Ý + (this.Â - this.Ý) * partialTicks + 0.25f;
        float var11 = this.Ý + (this.Â - this.Ý) * partialTicks + 0.75f;
        var10 = (var10 - MathHelper.Â((double)var10)) * 1.6f - 0.3f;
        var11 = (var11 - MathHelper.Â((double)var11)) * 1.6f - 0.3f;
        if (var10 < 0.0f) {
            var10 = 0.0f;
        }
        if (var11 < 0.0f) {
            var11 = 0.0f;
        }
        if (var10 > 1.0f) {
            var10 = 1.0f;
        }
        if (var11 > 1.0f) {
            var11 = 1.0f;
        }
        GlStateManager.ŠÄ();
        GuiEnchantment.¥Æ.HorizonCode_Horizon_È(null, 0.0f, var10, var11, var9, 0.0f, 0.0625f);
        GlStateManager.Ñ¢á();
        RenderHelper.HorizonCode_Horizon_È();
        GlStateManager.á(5889);
        GlStateManager.Â(0, 0, GuiEnchantment.Ñ¢á.Ó, GuiEnchantment.Ñ¢á.à);
        GlStateManager.Ê();
        GlStateManager.á(5888);
        GlStateManager.Ê();
        RenderHelper.HorizonCode_Horizon_È();
        GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
        EnchantmentNameParts.HorizonCode_Horizon_È().HorizonCode_Horizon_È(this.Æ.Ó);
        final int var12 = this.Æ.HorizonCode_Horizon_È();
        for (int var13 = 0; var13 < 3; ++var13) {
            final int var14 = var4 + 60;
            final int var15 = var14 + 20;
            final byte var16 = 86;
            final String var17 = EnchantmentNameParts.HorizonCode_Horizon_È().Â();
            GuiEnchantment.ŠÄ = 0.0f;
            GuiEnchantment.Ñ¢á.¥à().HorizonCode_Horizon_È(GuiEnchantment.µà);
            final int var18 = this.Æ.à[var13];
            GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
            if (var18 == 0) {
                this.Â(var14, var5 + 14 + 19 * var13, 0, 185, 108, 19);
            }
            else {
                final String var19 = new StringBuilder().append(var18).toString();
                FontRenderer var20 = GuiEnchantment.Ñ¢á.ˆà;
                int var21 = 6839882;
                if ((var12 < var13 + 1 || GuiEnchantment.Ñ¢á.á.áŒŠÉ < var18) && !GuiEnchantment.Ñ¢á.á.áˆºáˆºáŠ.Ø­áŒŠá) {
                    this.Â(var14, var5 + 14 + 19 * var13, 0, 185, 108, 19);
                    this.Â(var14 + 1, var5 + 15 + 19 * var13, 16 * var13, 239, 16, 16);
                    var20.HorizonCode_Horizon_È(var17, var15, var5 + 16 + 19 * var13, var16, (var21 & 0xFEFEFE) >> 1);
                    var21 = 4226832;
                }
                else {
                    final int var22 = mouseX - (var4 + 60);
                    final int var23 = mouseY - (var5 + 14 + 19 * var13);
                    if (var22 >= 0 && var23 >= 0 && var22 < 108 && var23 < 19) {
                        this.Â(var14, var5 + 14 + 19 * var13, 0, 204, 108, 19);
                        var21 = 16777088;
                    }
                    else {
                        this.Â(var14, var5 + 14 + 19 * var13, 0, 166, 108, 19);
                    }
                    this.Â(var14 + 1, var5 + 15 + 19 * var13, 16 * var13, 223, 16, 16);
                    var20.HorizonCode_Horizon_È(var17, var15, var5 + 16 + 19 * var13, var16, var21);
                    var21 = 8453920;
                }
                var20 = GuiEnchantment.Ñ¢á.µà;
                var20.HorizonCode_Horizon_È(var19, var15 + 86 - var20.HorizonCode_Horizon_È(var19), (float)(var5 + 16 + 19 * var13 + 7), var21);
            }
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int mouseX, final int mouseY, final float partialTicks) {
        super.HorizonCode_Horizon_È(mouseX, mouseY, partialTicks);
        final boolean var4 = GuiEnchantment.Ñ¢á.á.áˆºáˆºáŠ.Ø­áŒŠá;
        final int var5 = this.Æ.HorizonCode_Horizon_È();
        for (int var6 = 0; var6 < 3; ++var6) {
            final int var7 = this.Æ.à[var6];
            final int var8 = this.Æ.Ø[var6];
            final int var9 = var6 + 1;
            if (this.Ý(60, 14 + 19 * var6, 108, 17, mouseX, mouseY) && var7 > 0 && var8 >= 0) {
                final ArrayList var10 = Lists.newArrayList();
                if (var8 >= 0 && Enchantment.HorizonCode_Horizon_È(var8 & 0xFF) != null) {
                    final String var11 = Enchantment.HorizonCode_Horizon_È(var8 & 0xFF).Ø­áŒŠá((var8 & 0xFF00) >> 8);
                    var10.add(String.valueOf(EnumChatFormatting.£à.toString()) + EnumChatFormatting.µÕ.toString() + I18n.HorizonCode_Horizon_È("container.enchant.clue", var11));
                }
                if (!var4) {
                    if (var8 >= 0) {
                        var10.add("");
                    }
                    if (GuiEnchantment.Ñ¢á.á.áŒŠÉ < var7) {
                        var10.add(String.valueOf(EnumChatFormatting.ˆÏ­.toString()) + "Level Requirement: " + this.Æ.à[var6]);
                    }
                    else {
                        String var11 = "";
                        if (var9 == 1) {
                            var11 = I18n.HorizonCode_Horizon_È("container.enchant.lapis.one", new Object[0]);
                        }
                        else {
                            var11 = I18n.HorizonCode_Horizon_È("container.enchant.lapis.many", var9);
                        }
                        if (var5 >= var9) {
                            var10.add(String.valueOf(EnumChatFormatting.Ø.toString()) + var11);
                        }
                        else {
                            var10.add(String.valueOf(EnumChatFormatting.ˆÏ­.toString()) + var11);
                        }
                        if (var9 == 1) {
                            var11 = I18n.HorizonCode_Horizon_È("container.enchant.level.one", new Object[0]);
                        }
                        else {
                            var11 = I18n.HorizonCode_Horizon_È("container.enchant.level.many", var9);
                        }
                        var10.add(String.valueOf(EnumChatFormatting.Ø.toString()) + var11);
                    }
                }
                this.HorizonCode_Horizon_È(var10, mouseX, mouseY);
                break;
            }
        }
    }
    
    public void Ó() {
        final ItemStack var1 = GuiEnchantment.á.HorizonCode_Horizon_È(0).HorizonCode_Horizon_È();
        if (!ItemStack.Â(var1, this.Ø)) {
            this.Ø = var1;
            do {
                this.Ø­áŒŠá += this.µÕ.nextInt(4) - this.µÕ.nextInt(4);
            } while (this.Â <= this.Ø­áŒŠá + 1.0f && this.Â >= this.Ø­áŒŠá - 1.0f);
        }
        ++this.HorizonCode_Horizon_È;
        this.Ý = this.Â;
        this.à = this.Ó;
        boolean var2 = false;
        for (int var3 = 0; var3 < 3; ++var3) {
            if (this.Æ.à[var3] != 0) {
                var2 = true;
            }
        }
        if (var2) {
            this.Ó += 0.2f;
        }
        else {
            this.Ó -= 0.2f;
        }
        this.Ó = MathHelper.HorizonCode_Horizon_È(this.Ó, 0.0f, 1.0f);
        float var4 = (this.Ø­áŒŠá - this.Â) * 0.4f;
        final float var5 = 0.2f;
        var4 = MathHelper.HorizonCode_Horizon_È(var4, -var5, var5);
        this.Âµá€ += (var4 - this.Âµá€) * 0.9f;
        this.Â += this.Âµá€;
    }
}
