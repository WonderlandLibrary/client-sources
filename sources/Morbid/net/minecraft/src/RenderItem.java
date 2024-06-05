package net.minecraft.src;

import java.util.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.*;

public class RenderItem extends Render
{
    private static RenderBlocks itemRenderBlocks;
    private Random random;
    public static boolean renderWithColor;
    public static float zLevel;
    public static boolean renderInFrame;
    
    static {
        RenderItem.itemRenderBlocks = new RenderBlocks();
        RenderItem.renderWithColor = true;
        RenderItem.zLevel = 0.0f;
        RenderItem.renderInFrame = false;
    }
    
    public RenderItem() {
        this.random = new Random();
        this.shadowSize = 0.15f;
        this.shadowOpaque = 0.75f;
    }
    
    public void doRenderItem(final EntityItem par1EntityItem, final double par2, final double par4, final double par6, final float par8, final float par9) {
        this.random.setSeed(187L);
        final ItemStack var10 = par1EntityItem.getEntityItem();
        if (var10.getItem() != null) {
            GL11.glPushMatrix();
            final float var11 = MathHelper.sin((par1EntityItem.age + par9) / 10.0f + par1EntityItem.hoverStart) * 0.1f + 0.1f;
            final float var12 = ((par1EntityItem.age + par9) / 20.0f + par1EntityItem.hoverStart) * 57.295776f;
            byte var13 = 1;
            if (par1EntityItem.getEntityItem().stackSize > 1) {
                var13 = 2;
            }
            if (par1EntityItem.getEntityItem().stackSize > 5) {
                var13 = 3;
            }
            if (par1EntityItem.getEntityItem().stackSize > 20) {
                var13 = 4;
            }
            if (par1EntityItem.getEntityItem().stackSize > 40) {
                var13 = 5;
            }
            GL11.glTranslatef((float)par2, (float)par4 + var11, (float)par6);
            GL11.glEnable(32826);
            if (var10.getItemSpriteNumber() == 0 && Block.blocksList[var10.itemID] != null && RenderBlocks.renderItemIn3d(Block.blocksList[var10.itemID].getRenderType())) {
                final Block var14 = Block.blocksList[var10.itemID];
                GL11.glRotatef(var12, 0.0f, 1.0f, 0.0f);
                if (RenderItem.renderInFrame) {
                    GL11.glScalef(1.25f, 1.25f, 1.25f);
                    GL11.glTranslatef(0.0f, 0.05f, 0.0f);
                    GL11.glRotatef(-90.0f, 0.0f, 1.0f, 0.0f);
                }
                this.loadTexture("/terrain.png");
                float var15 = 0.25f;
                final int var16 = var14.getRenderType();
                if (var16 == 1 || var16 == 19 || var16 == 12 || var16 == 2) {
                    var15 = 0.5f;
                }
                GL11.glScalef(var15, var15, var15);
                for (int var17 = 0; var17 < var13; ++var17) {
                    GL11.glPushMatrix();
                    if (var17 > 0) {
                        final float var18 = (this.random.nextFloat() * 2.0f - 1.0f) * 0.2f / var15;
                        final float var19 = (this.random.nextFloat() * 2.0f - 1.0f) * 0.2f / var15;
                        final float var20 = (this.random.nextFloat() * 2.0f - 1.0f) * 0.2f / var15;
                        GL11.glTranslatef(var18, var19, var20);
                    }
                    final float var18 = 1.0f;
                    RenderItem.itemRenderBlocks.renderBlockAsItem(var14, var10.getItemDamage(), var18);
                    GL11.glPopMatrix();
                }
            }
            else if (var10.getItem().requiresMultipleRenderPasses()) {
                if (RenderItem.renderInFrame) {
                    GL11.glScalef(0.5128205f, 0.5128205f, 0.5128205f);
                    GL11.glTranslatef(0.0f, -0.05f, 0.0f);
                }
                else {
                    GL11.glScalef(0.5f, 0.5f, 0.5f);
                }
                this.loadTexture("/gui/items.png");
                for (int var21 = 0; var21 <= 1; ++var21) {
                    this.random.setSeed(187L);
                    final Icon var22 = var10.getItem().getIconFromDamageForRenderPass(var10.getItemDamage(), var21);
                    final float var23 = 1.0f;
                    if (RenderItem.renderWithColor) {
                        final int var17 = Item.itemsList[var10.itemID].getColorFromItemStack(var10, var21);
                        final float var18 = (var17 >> 16 & 0xFF) / 255.0f;
                        final float var19 = (var17 >> 8 & 0xFF) / 255.0f;
                        final float var20 = (var17 & 0xFF) / 255.0f;
                        GL11.glColor4f(var18 * var23, var19 * var23, var20 * var23, 1.0f);
                        this.renderDroppedItem(par1EntityItem, var22, var13, par9, var18 * var23, var19 * var23, var20 * var23);
                    }
                    else {
                        this.renderDroppedItem(par1EntityItem, var22, var13, par9, 1.0f, 1.0f, 1.0f);
                    }
                }
            }
            else {
                if (RenderItem.renderInFrame) {
                    GL11.glScalef(0.5128205f, 0.5128205f, 0.5128205f);
                    GL11.glTranslatef(0.0f, -0.05f, 0.0f);
                }
                else {
                    GL11.glScalef(0.5f, 0.5f, 0.5f);
                }
                final Icon var24 = var10.getIconIndex();
                if (var10.getItemSpriteNumber() == 0) {
                    this.loadTexture("/terrain.png");
                }
                else {
                    this.loadTexture("/gui/items.png");
                }
                if (RenderItem.renderWithColor) {
                    final int var16 = Item.itemsList[var10.itemID].getColorFromItemStack(var10, 0);
                    final float var23 = (var16 >> 16 & 0xFF) / 255.0f;
                    final float var25 = (var16 >> 8 & 0xFF) / 255.0f;
                    final float var18 = (var16 & 0xFF) / 255.0f;
                    final float var19 = 1.0f;
                    this.renderDroppedItem(par1EntityItem, var24, var13, par9, var23 * var19, var25 * var19, var18 * var19);
                }
                else {
                    this.renderDroppedItem(par1EntityItem, var24, var13, par9, 1.0f, 1.0f, 1.0f);
                }
            }
            GL11.glDisable(32826);
            GL11.glPopMatrix();
        }
    }
    
    private void renderDroppedItem(final EntityItem par1EntityItem, Icon par2Icon, final int par3, final float par4, final float par5, final float par6, final float par7) {
        final Tessellator var8 = Tessellator.instance;
        if (par2Icon == null) {
            par2Icon = this.renderManager.renderEngine.getMissingIcon(par1EntityItem.getEntityItem().getItemSpriteNumber());
        }
        final float var9 = par2Icon.getMinU();
        final float var10 = par2Icon.getMaxU();
        final float var11 = par2Icon.getMinV();
        final float var12 = par2Icon.getMaxV();
        final float var13 = 1.0f;
        final float var14 = 0.5f;
        final float var15 = 0.25f;
        if (this.renderManager.options.fancyGraphics) {
            GL11.glPushMatrix();
            if (RenderItem.renderInFrame) {
                GL11.glRotatef(180.0f, 0.0f, 1.0f, 0.0f);
            }
            else {
                GL11.glRotatef(((par1EntityItem.age + par4) / 20.0f + par1EntityItem.hoverStart) * 57.295776f, 0.0f, 1.0f, 0.0f);
            }
            final float var16 = 0.0625f;
            final float var17 = 0.021875f;
            final ItemStack var18 = par1EntityItem.getEntityItem();
            final int var19 = var18.stackSize;
            byte var20;
            if (var19 < 2) {
                var20 = 1;
            }
            else if (var19 < 16) {
                var20 = 2;
            }
            else if (var19 < 32) {
                var20 = 3;
            }
            else {
                var20 = 4;
            }
            GL11.glTranslatef(-var14, -var15, -((var16 + var17) * var20 / 2.0f));
            for (int var21 = 0; var21 < var20; ++var21) {
                GL11.glTranslatef(0.0f, 0.0f, var16 + var17);
                if (var18.getItemSpriteNumber() == 0 && Block.blocksList[var18.itemID] != null) {
                    this.loadTexture("/terrain.png");
                }
                else {
                    this.loadTexture("/gui/items.png");
                }
                GL11.glColor4f(par5, par6, par7, 1.0f);
                ItemRenderer.renderItemIn2D(var8, var10, var11, var9, var12, par2Icon.getSheetWidth(), par2Icon.getSheetHeight(), var16);
                if (var18 != null && var18.hasEffect()) {
                    GL11.glDepthFunc(514);
                    GL11.glDisable(2896);
                    this.renderManager.renderEngine.bindTexture("%blur%/misc/glint.png");
                    GL11.glEnable(3042);
                    GL11.glBlendFunc(768, 1);
                    final float var22 = 0.76f;
                    GL11.glColor4f(0.5f * var22, 0.25f * var22, 0.8f * var22, 1.0f);
                    GL11.glMatrixMode(5890);
                    GL11.glPushMatrix();
                    final float var23 = 0.125f;
                    GL11.glScalef(var23, var23, var23);
                    float var24 = Minecraft.getSystemTime() % 3000L / 3000.0f * 8.0f;
                    GL11.glTranslatef(var24, 0.0f, 0.0f);
                    GL11.glRotatef(-50.0f, 0.0f, 0.0f, 1.0f);
                    ItemRenderer.renderItemIn2D(var8, 0.0f, 0.0f, 1.0f, 1.0f, 255, 255, var16);
                    GL11.glPopMatrix();
                    GL11.glPushMatrix();
                    GL11.glScalef(var23, var23, var23);
                    var24 = Minecraft.getSystemTime() % 4873L / 4873.0f * 8.0f;
                    GL11.glTranslatef(-var24, 0.0f, 0.0f);
                    GL11.glRotatef(10.0f, 0.0f, 0.0f, 1.0f);
                    ItemRenderer.renderItemIn2D(var8, 0.0f, 0.0f, 1.0f, 1.0f, 255, 255, var16);
                    GL11.glPopMatrix();
                    GL11.glMatrixMode(5888);
                    GL11.glDisable(3042);
                    GL11.glEnable(2896);
                    GL11.glDepthFunc(515);
                }
            }
            GL11.glPopMatrix();
        }
        else {
            for (int var25 = 0; var25 < par3; ++var25) {
                GL11.glPushMatrix();
                if (var25 > 0) {
                    final float var17 = (this.random.nextFloat() * 2.0f - 1.0f) * 0.3f;
                    final float var26 = (this.random.nextFloat() * 2.0f - 1.0f) * 0.3f;
                    final float var27 = (this.random.nextFloat() * 2.0f - 1.0f) * 0.3f;
                    GL11.glTranslatef(var17, var26, var27);
                }
                if (!RenderItem.renderInFrame) {
                    GL11.glRotatef(180.0f - this.renderManager.playerViewY, 0.0f, 1.0f, 0.0f);
                }
                GL11.glColor4f(par5, par6, par7, 1.0f);
                var8.startDrawingQuads();
                var8.setNormal(0.0f, 1.0f, 0.0f);
                var8.addVertexWithUV(0.0f - var14, 0.0f - var15, 0.0, var9, var12);
                var8.addVertexWithUV(var13 - var14, 0.0f - var15, 0.0, var10, var12);
                var8.addVertexWithUV(var13 - var14, 1.0f - var15, 0.0, var10, var11);
                var8.addVertexWithUV(0.0f - var14, 1.0f - var15, 0.0, var9, var11);
                var8.draw();
                GL11.glPopMatrix();
            }
        }
    }
    
    public static void renderItemIntoGUI(final FontRenderer par1FontRenderer, final RenderEngine par2RenderEngine, final ItemStack par3ItemStack, final int par4, final int par5) {
        final int var6 = par3ItemStack.itemID;
        final int var7 = par3ItemStack.getItemDamage();
        Icon var8 = par3ItemStack.getIconIndex();
        if (par3ItemStack.getItemSpriteNumber() == 0 && RenderBlocks.renderItemIn3d(Block.blocksList[var6].getRenderType())) {
            par2RenderEngine.bindTexture("/terrain.png");
            final Block var9 = Block.blocksList[var6];
            GL11.glPushMatrix();
            GL11.glTranslatef(par4 - 2, par5 + 3, -3.0f + RenderItem.zLevel);
            GL11.glScalef(10.0f, 10.0f, 10.0f);
            GL11.glTranslatef(1.0f, 0.5f, 1.0f);
            GL11.glScalef(1.0f, 1.0f, -1.0f);
            GL11.glRotatef(210.0f, 1.0f, 0.0f, 0.0f);
            GL11.glRotatef(45.0f, 0.0f, 1.0f, 0.0f);
            final int var10 = Item.itemsList[var6].getColorFromItemStack(par3ItemStack, 0);
            final float var11 = (var10 >> 16 & 0xFF) / 255.0f;
            final float var12 = (var10 >> 8 & 0xFF) / 255.0f;
            final float var13 = (var10 & 0xFF) / 255.0f;
            if (RenderItem.renderWithColor) {
                GL11.glColor4f(var11, var12, var13, 1.0f);
            }
            GL11.glRotatef(-90.0f, 0.0f, 1.0f, 0.0f);
            RenderItem.itemRenderBlocks.useInventoryTint = RenderItem.renderWithColor;
            RenderItem.itemRenderBlocks.renderBlockAsItem(var9, var7, 1.0f);
            RenderItem.itemRenderBlocks.useInventoryTint = true;
            GL11.glPopMatrix();
        }
        else if (Item.itemsList[var6].requiresMultipleRenderPasses()) {
            GL11.glDisable(2896);
            par2RenderEngine.bindTexture("/gui/items.png");
            for (int var14 = 0; var14 <= 1; ++var14) {
                final Icon var15 = Item.itemsList[var6].getIconFromDamageForRenderPass(var7, var14);
                final int var16 = Item.itemsList[var6].getColorFromItemStack(par3ItemStack, var14);
                final float var12 = (var16 >> 16 & 0xFF) / 255.0f;
                final float var13 = (var16 >> 8 & 0xFF) / 255.0f;
                final float var17 = (var16 & 0xFF) / 255.0f;
                if (RenderItem.renderWithColor) {
                    GL11.glColor4f(var12, var13, var17, 1.0f);
                }
                renderIcon(par4, par5, var15, 16, 16);
            }
            GL11.glEnable(2896);
        }
        else {
            GL11.glDisable(2896);
            if (par3ItemStack.getItemSpriteNumber() == 0) {
                par2RenderEngine.bindTexture("/terrain.png");
            }
            else {
                par2RenderEngine.bindTexture("/gui/items.png");
            }
            if (var8 == null) {
                var8 = par2RenderEngine.getMissingIcon(par3ItemStack.getItemSpriteNumber());
            }
            final int var14 = Item.itemsList[var6].getColorFromItemStack(par3ItemStack, 0);
            final float var18 = (var14 >> 16 & 0xFF) / 255.0f;
            final float var11 = (var14 >> 8 & 0xFF) / 255.0f;
            final float var12 = (var14 & 0xFF) / 255.0f;
            if (RenderItem.renderWithColor) {
                GL11.glColor4f(var18, var11, var12, 1.0f);
            }
            renderIcon(par4, par5, var8, 16, 16);
            GL11.glEnable(2896);
        }
        GL11.glEnable(2884);
    }
    
    public static void renderItemAndEffectIntoGUI(final FontRenderer par1FontRenderer, final RenderEngine par2RenderEngine, final ItemStack par3ItemStack, final int par4, final int par5) {
        if (par3ItemStack != null) {
            renderItemIntoGUI(par1FontRenderer, par2RenderEngine, par3ItemStack, par4, par5);
            if (par3ItemStack.hasEffect()) {
                GL11.glDepthFunc(516);
                GL11.glDisable(2896);
                GL11.glDepthMask(false);
                par2RenderEngine.bindTexture("%blur%/misc/glint.png");
                RenderItem.zLevel -= 50.0f;
                GL11.glEnable(3042);
                GL11.glBlendFunc(774, 774);
                GL11.glColor4f(0.5f, 0.25f, 0.8f, 1.0f);
                renderGlint(par4 * 431278612 + par5 * 32178161, par4 - 2, par5 - 2, 20, 20);
                GL11.glDisable(3042);
                GL11.glDepthMask(true);
                RenderItem.zLevel += 50.0f;
                GL11.glEnable(2896);
                GL11.glDepthFunc(515);
            }
        }
    }
    
    private static void renderGlint(final int par1, final int par2, final int par3, final int par4, final int par5) {
        for (int var6 = 0; var6 < 2; ++var6) {
            if (var6 == 0) {
                GL11.glBlendFunc(768, 1);
            }
            if (var6 == 1) {
                GL11.glBlendFunc(768, 1);
            }
            final float var7 = 0.00390625f;
            final float var8 = 0.00390625f;
            final float var9 = Minecraft.getSystemTime() % (3000 + var6 * 1873) / (3000.0f + var6 * 1873) * 256.0f;
            final float var10 = 0.0f;
            final Tessellator var11 = Tessellator.instance;
            float var12 = 4.0f;
            if (var6 == 1) {
                var12 = -1.0f;
            }
            var11.startDrawingQuads();
            var11.addVertexWithUV(par2 + 0, par3 + par5, RenderItem.zLevel, (var9 + par5 * var12) * var7, (var10 + par5) * var8);
            var11.addVertexWithUV(par2 + par4, par3 + par5, RenderItem.zLevel, (var9 + par4 + par5 * var12) * var7, (var10 + par5) * var8);
            var11.addVertexWithUV(par2 + par4, par3 + 0, RenderItem.zLevel, (var9 + par4) * var7, (var10 + 0.0f) * var8);
            var11.addVertexWithUV(par2 + 0, par3 + 0, RenderItem.zLevel, (var9 + 0.0f) * var7, (var10 + 0.0f) * var8);
            var11.draw();
        }
    }
    
    public static void renderItemOverlayIntoGUI(final FontRenderer par1FontRenderer, final RenderEngine par2RenderEngine, final ItemStack par3ItemStack, final int par4, final int par5) {
        renderItemOverlayIntoGUI(par1FontRenderer, par2RenderEngine, par3ItemStack, par4, par5, null);
    }
    
    public static void renderItemOverlayIntoGUI(final FontRenderer par1FontRenderer, final RenderEngine par2RenderEngine, final ItemStack par3ItemStack, final int par4, final int par5, final String par6Str) {
        if (par3ItemStack != null) {
            if (par3ItemStack.stackSize > 1 || par6Str != null) {
                final String var7 = (par6Str == null) ? String.valueOf(par3ItemStack.stackSize) : par6Str;
                GL11.glDisable(2896);
                GL11.glDisable(2929);
                par1FontRenderer.drawStringWithShadow(var7, par4 + 19 - 2 - par1FontRenderer.getStringWidth(var7), par5 + 6 + 3, 16777215);
                GL11.glEnable(2896);
                GL11.glEnable(2929);
            }
            if (par3ItemStack.isItemDamaged()) {
                final int var8 = (int)Math.round(13.0 - par3ItemStack.getItemDamageForDisplay() * 13.0 / par3ItemStack.getMaxDamage());
                final int var9 = (int)Math.round(255.0 - par3ItemStack.getItemDamageForDisplay() * 255.0 / par3ItemStack.getMaxDamage());
                GL11.glDisable(2896);
                GL11.glDisable(2929);
                GL11.glDisable(3553);
                final Tessellator var10 = Tessellator.instance;
                final int var11 = 255 - var9 << 16 | var9 << 8;
                final int var12 = (255 - var9) / 4 << 16 | 0x3F00;
                renderQuad(var10, par4 + 2, par5 + 13, 13, 2, 0);
                renderQuad(var10, par4 + 2, par5 + 13, 12, 1, var12);
                renderQuad(var10, par4 + 2, par5 + 13, var8, 1, var11);
                GL11.glEnable(3553);
                GL11.glEnable(2896);
                GL11.glEnable(2929);
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            }
        }
    }
    
    private static void renderQuad(final Tessellator par1Tessellator, final int par2, final int par3, final int par4, final int par5, final int par6) {
        par1Tessellator.startDrawingQuads();
        par1Tessellator.setColorOpaque_I(par6);
        par1Tessellator.addVertex(par2 + 0, par3 + 0, 0.0);
        par1Tessellator.addVertex(par2 + 0, par3 + par5, 0.0);
        par1Tessellator.addVertex(par2 + par4, par3 + par5, 0.0);
        par1Tessellator.addVertex(par2 + par4, par3 + 0, 0.0);
        par1Tessellator.draw();
    }
    
    public static void renderIcon(final int par1, final int par2, final Icon par3Icon, final int par4, final int par5) {
        final Tessellator var6 = Tessellator.instance;
        var6.startDrawingQuads();
        var6.addVertexWithUV(par1 + 0, par2 + par5, RenderItem.zLevel, par3Icon.getMinU(), par3Icon.getMaxV());
        var6.addVertexWithUV(par1 + par4, par2 + par5, RenderItem.zLevel, par3Icon.getMaxU(), par3Icon.getMaxV());
        var6.addVertexWithUV(par1 + par4, par2 + 0, RenderItem.zLevel, par3Icon.getMaxU(), par3Icon.getMinV());
        var6.addVertexWithUV(par1 + 0, par2 + 0, RenderItem.zLevel, par3Icon.getMinU(), par3Icon.getMinV());
        var6.draw();
    }
    
    @Override
    public void doRender(final Entity par1Entity, final double par2, final double par4, final double par6, final float par8, final float par9) {
        this.doRenderItem((EntityItem)par1Entity, par2, par4, par6, par8, par9);
    }
}
