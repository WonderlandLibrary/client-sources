package net.minecraft.src;

import java.util.*;
import net.minecraft.client.*;
import org.lwjgl.opengl.*;
import org.lwjgl.util.glu.*;

public class GuiEnchantment extends GuiContainer
{
    private static ModelBook bookModel;
    private Random rand;
    private ContainerEnchantment containerEnchantment;
    public int field_74214_o;
    public float field_74213_p;
    public float field_74212_q;
    public float field_74211_r;
    public float field_74210_s;
    public float field_74209_t;
    public float field_74208_u;
    ItemStack theItemStack;
    private String field_94079_C;
    
    static {
        GuiEnchantment.bookModel = new ModelBook();
    }
    
    public GuiEnchantment(final InventoryPlayer par1InventoryPlayer, final World par2World, final int par3, final int par4, final int par5, final String par6Str) {
        super(new ContainerEnchantment(par1InventoryPlayer, par2World, par3, par4, par5));
        this.rand = new Random();
        this.containerEnchantment = (ContainerEnchantment)this.inventorySlots;
        this.field_94079_C = par6Str;
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(final int par1, final int par2) {
        this.fontRenderer.drawString((this.field_94079_C == null) ? StatCollector.translateToLocal("container.enchant") : this.field_94079_C, 12, 5, 4210752);
        this.fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
    }
    
    @Override
    public void updateScreen() {
        super.updateScreen();
        this.func_74205_h();
    }
    
    @Override
    protected void mouseClicked(final int par1, final int par2, final int par3) {
        super.mouseClicked(par1, par2, par3);
        final int var4 = (this.width - this.xSize) / 2;
        final int var5 = (this.height - this.ySize) / 2;
        for (int var6 = 0; var6 < 3; ++var6) {
            final int var7 = par1 - (var4 + 60);
            final int var8 = par2 - (var5 + 14 + 19 * var6);
            if (var7 >= 0 && var8 >= 0 && var7 < 108 && var8 < 19 && this.containerEnchantment.enchantItem(Minecraft.thePlayer, var6)) {
                this.mc.playerController.sendEnchantPacket(this.containerEnchantment.windowId, var6);
            }
        }
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(final float par1, final int par2, final int par3) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.renderEngine.bindTexture("/gui/enchant.png");
        final int var4 = (this.width - this.xSize) / 2;
        final int var5 = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(var4, var5, 0, 0, this.xSize, this.ySize);
        GL11.glPushMatrix();
        GL11.glMatrixMode(5889);
        GL11.glPushMatrix();
        GL11.glLoadIdentity();
        final ScaledResolution var6 = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
        GL11.glViewport((ScaledResolution.getScaledWidth() - 320) / 2 * var6.getScaleFactor(), (ScaledResolution.getScaledHeight() - 240) / 2 * var6.getScaleFactor(), 320 * var6.getScaleFactor(), 240 * var6.getScaleFactor());
        GL11.glTranslatef(-0.34f, 0.23f, 0.0f);
        GLU.gluPerspective(90.0f, 1.3333334f, 9.0f, 80.0f);
        final float var7 = 1.0f;
        GL11.glMatrixMode(5888);
        GL11.glLoadIdentity();
        RenderHelper.enableStandardItemLighting();
        GL11.glTranslatef(0.0f, 3.3f, -16.0f);
        GL11.glScalef(var7, var7, var7);
        final float var8 = 5.0f;
        GL11.glScalef(var8, var8, var8);
        GL11.glRotatef(180.0f, 0.0f, 0.0f, 1.0f);
        this.mc.renderEngine.bindTexture("/item/book.png");
        GL11.glRotatef(20.0f, 1.0f, 0.0f, 0.0f);
        final float var9 = this.field_74208_u + (this.field_74209_t - this.field_74208_u) * par1;
        GL11.glTranslatef((1.0f - var9) * 0.2f, (1.0f - var9) * 0.1f, (1.0f - var9) * 0.25f);
        GL11.glRotatef(-(1.0f - var9) * 90.0f - 90.0f, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(180.0f, 1.0f, 0.0f, 0.0f);
        float var10 = this.field_74212_q + (this.field_74213_p - this.field_74212_q) * par1 + 0.25f;
        float var11 = this.field_74212_q + (this.field_74213_p - this.field_74212_q) * par1 + 0.75f;
        var10 = (var10 - MathHelper.truncateDoubleToInt(var10)) * 1.6f - 0.3f;
        var11 = (var11 - MathHelper.truncateDoubleToInt(var11)) * 1.6f - 0.3f;
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
        GL11.glEnable(32826);
        GuiEnchantment.bookModel.render(null, 0.0f, var10, var11, var9, 0.0f, 0.0625f);
        GL11.glDisable(32826);
        RenderHelper.disableStandardItemLighting();
        GL11.glMatrixMode(5889);
        GL11.glViewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
        GL11.glPopMatrix();
        GL11.glMatrixMode(5888);
        GL11.glPopMatrix();
        RenderHelper.disableStandardItemLighting();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.renderEngine.bindTexture("/gui/enchant.png");
        EnchantmentNameParts.instance.setRandSeed(this.containerEnchantment.nameSeed);
        for (int var12 = 0; var12 < 3; ++var12) {
            final String var13 = EnchantmentNameParts.instance.generateRandomEnchantName();
            this.zLevel = 0.0f;
            this.mc.renderEngine.bindTexture("/gui/enchant.png");
            final int var14 = this.containerEnchantment.enchantLevels[var12];
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            if (var14 == 0) {
                this.drawTexturedModalRect(var4 + 60, var5 + 14 + 19 * var12, 0, 185, 108, 19);
            }
            else {
                final String var15 = new StringBuilder().append(var14).toString();
                FontRenderer var16 = this.mc.standardGalacticFontRenderer;
                int var17 = 6839882;
                if (Minecraft.thePlayer.experienceLevel < var14 && !Minecraft.thePlayer.capabilities.isCreativeMode) {
                    this.drawTexturedModalRect(var4 + 60, var5 + 14 + 19 * var12, 0, 185, 108, 19);
                    var16.drawSplitString(var13, var4 + 62, var5 + 16 + 19 * var12, 104, (var17 & 0xFEFEFE) >> 1);
                    var16 = this.mc.fontRenderer;
                    var17 = 4226832;
                    var16.drawStringWithShadow(var15, var4 + 62 + 104 - var16.getStringWidth(var15), var5 + 16 + 19 * var12 + 7, var17);
                }
                else {
                    final int var18 = par2 - (var4 + 60);
                    final int var19 = par3 - (var5 + 14 + 19 * var12);
                    if (var18 >= 0 && var19 >= 0 && var18 < 108 && var19 < 19) {
                        this.drawTexturedModalRect(var4 + 60, var5 + 14 + 19 * var12, 0, 204, 108, 19);
                        var17 = 16777088;
                    }
                    else {
                        this.drawTexturedModalRect(var4 + 60, var5 + 14 + 19 * var12, 0, 166, 108, 19);
                    }
                    var16.drawSplitString(var13, var4 + 62, var5 + 16 + 19 * var12, 104, var17);
                    var16 = this.mc.fontRenderer;
                    var17 = 8453920;
                    var16.drawStringWithShadow(var15, var4 + 62 + 104 - var16.getStringWidth(var15), var5 + 16 + 19 * var12 + 7, var17);
                }
            }
        }
    }
    
    public void func_74205_h() {
        final ItemStack var1 = this.inventorySlots.getSlot(0).getStack();
        if (!ItemStack.areItemStacksEqual(var1, this.theItemStack)) {
            this.theItemStack = var1;
            do {
                this.field_74211_r += this.rand.nextInt(4) - this.rand.nextInt(4);
            } while (this.field_74213_p <= this.field_74211_r + 1.0f && this.field_74213_p >= this.field_74211_r - 1.0f);
        }
        ++this.field_74214_o;
        this.field_74212_q = this.field_74213_p;
        this.field_74208_u = this.field_74209_t;
        boolean var2 = false;
        for (int var3 = 0; var3 < 3; ++var3) {
            if (this.containerEnchantment.enchantLevels[var3] != 0) {
                var2 = true;
            }
        }
        if (var2) {
            this.field_74209_t += 0.2f;
        }
        else {
            this.field_74209_t -= 0.2f;
        }
        if (this.field_74209_t < 0.0f) {
            this.field_74209_t = 0.0f;
        }
        if (this.field_74209_t > 1.0f) {
            this.field_74209_t = 1.0f;
        }
        float var4 = (this.field_74211_r - this.field_74213_p) * 0.4f;
        final float var5 = 0.2f;
        if (var4 < -var5) {
            var4 = -var5;
        }
        if (var4 > var5) {
            var4 = var5;
        }
        this.field_74210_s += (var4 - this.field_74210_s) * 0.9f;
        this.field_74213_p += this.field_74210_s;
    }
}
