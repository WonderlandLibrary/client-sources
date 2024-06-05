package net.minecraft.src;

import net.minecraft.client.*;
import org.lwjgl.opengl.*;

public class ItemRenderer
{
    private Minecraft mc;
    private ItemStack itemToRender;
    private float equippedProgress;
    private float prevEquippedProgress;
    private RenderBlocks renderBlocksInstance;
    public final MapItemRenderer mapItemRenderer;
    private int equippedItemSlot;
    
    public ItemRenderer(final Minecraft par1Minecraft) {
        this.itemToRender = null;
        this.equippedProgress = 0.0f;
        this.prevEquippedProgress = 0.0f;
        this.renderBlocksInstance = new RenderBlocks();
        this.equippedItemSlot = -1;
        this.mc = par1Minecraft;
        this.mapItemRenderer = new MapItemRenderer(par1Minecraft.fontRenderer, par1Minecraft.gameSettings, par1Minecraft.renderEngine);
    }
    
    public void renderItem(final EntityLiving par1EntityLiving, final ItemStack par2ItemStack, final int par3) {
        GL11.glPushMatrix();
        if (par2ItemStack.getItemSpriteNumber() == 0 && Block.blocksList[par2ItemStack.itemID] != null && RenderBlocks.renderItemIn3d(Block.blocksList[par2ItemStack.itemID].getRenderType())) {
            this.mc.renderEngine.bindTexture("/terrain.png");
            this.renderBlocksInstance.renderBlockAsItem(Block.blocksList[par2ItemStack.itemID], par2ItemStack.getItemDamage(), 1.0f);
        }
        else {
            final Icon var4 = par1EntityLiving.getItemIcon(par2ItemStack, par3);
            if (var4 == null) {
                GL11.glPopMatrix();
                return;
            }
            if (par2ItemStack.getItemSpriteNumber() == 0) {
                this.mc.renderEngine.bindTexture("/terrain.png");
            }
            else {
                this.mc.renderEngine.bindTexture("/gui/items.png");
            }
            final Tessellator var5 = Tessellator.instance;
            final float var6 = var4.getMinU();
            final float var7 = var4.getMaxU();
            final float var8 = var4.getMinV();
            final float var9 = var4.getMaxV();
            final float var10 = 0.0f;
            final float var11 = 0.3f;
            GL11.glEnable(32826);
            GL11.glTranslatef(-var10, -var11, 0.0f);
            final float var12 = 1.5f;
            GL11.glScalef(var12, var12, var12);
            GL11.glRotatef(50.0f, 0.0f, 1.0f, 0.0f);
            GL11.glRotatef(335.0f, 0.0f, 0.0f, 1.0f);
            GL11.glTranslatef(-0.9375f, -0.0625f, 0.0f);
            renderItemIn2D(var5, var7, var8, var6, var9, var4.getSheetWidth(), var4.getSheetHeight(), 0.0625f);
            if (par2ItemStack != null && par2ItemStack.hasEffect() && par3 == 0) {
                GL11.glDepthFunc(514);
                GL11.glDisable(2896);
                this.mc.renderEngine.bindTexture("%blur%/misc/glint.png");
                GL11.glEnable(3042);
                GL11.glBlendFunc(768, 1);
                final float var13 = 0.76f;
                GL11.glColor4f(0.5f * var13, 0.25f * var13, 0.8f * var13, 1.0f);
                GL11.glMatrixMode(5890);
                GL11.glPushMatrix();
                final float var14 = 0.125f;
                GL11.glScalef(var14, var14, var14);
                float var15 = Minecraft.getSystemTime() % 3000L / 3000.0f * 8.0f;
                GL11.glTranslatef(var15, 0.0f, 0.0f);
                GL11.glRotatef(-50.0f, 0.0f, 0.0f, 1.0f);
                renderItemIn2D(var5, 0.0f, 0.0f, 1.0f, 1.0f, 256, 256, 0.0625f);
                GL11.glPopMatrix();
                GL11.glPushMatrix();
                GL11.glScalef(var14, var14, var14);
                var15 = Minecraft.getSystemTime() % 4873L / 4873.0f * 8.0f;
                GL11.glTranslatef(-var15, 0.0f, 0.0f);
                GL11.glRotatef(10.0f, 0.0f, 0.0f, 1.0f);
                renderItemIn2D(var5, 0.0f, 0.0f, 1.0f, 1.0f, 256, 256, 0.0625f);
                GL11.glPopMatrix();
                GL11.glMatrixMode(5888);
                GL11.glDisable(3042);
                GL11.glEnable(2896);
                GL11.glDepthFunc(515);
            }
            GL11.glDisable(32826);
        }
        GL11.glPopMatrix();
    }
    
    public static void renderItemIn2D(final Tessellator par0Tessellator, final float par1, final float par2, final float par3, final float par4, final int par5, final int par6, final float par7) {
        par0Tessellator.startDrawingQuads();
        par0Tessellator.setNormal(0.0f, 0.0f, 1.0f);
        par0Tessellator.addVertexWithUV(0.0, 0.0, 0.0, par1, par4);
        par0Tessellator.addVertexWithUV(1.0, 0.0, 0.0, par3, par4);
        par0Tessellator.addVertexWithUV(1.0, 1.0, 0.0, par3, par2);
        par0Tessellator.addVertexWithUV(0.0, 1.0, 0.0, par1, par2);
        par0Tessellator.draw();
        par0Tessellator.startDrawingQuads();
        par0Tessellator.setNormal(0.0f, 0.0f, -1.0f);
        par0Tessellator.addVertexWithUV(0.0, 1.0, 0.0f - par7, par1, par2);
        par0Tessellator.addVertexWithUV(1.0, 1.0, 0.0f - par7, par3, par2);
        par0Tessellator.addVertexWithUV(1.0, 0.0, 0.0f - par7, par3, par4);
        par0Tessellator.addVertexWithUV(0.0, 0.0, 0.0f - par7, par1, par4);
        par0Tessellator.draw();
        final float var8 = par5 * (par1 - par3);
        final float var9 = par6 * (par4 - par2);
        par0Tessellator.startDrawingQuads();
        par0Tessellator.setNormal(-1.0f, 0.0f, 0.0f);
        for (int var10 = 0; var10 < var8; ++var10) {
            final float var11 = var10 / var8;
            final float var12 = par1 + (par3 - par1) * var11 - 0.5f / par5;
            par0Tessellator.addVertexWithUV(var11, 0.0, 0.0f - par7, var12, par4);
            par0Tessellator.addVertexWithUV(var11, 0.0, 0.0, var12, par4);
            par0Tessellator.addVertexWithUV(var11, 1.0, 0.0, var12, par2);
            par0Tessellator.addVertexWithUV(var11, 1.0, 0.0f - par7, var12, par2);
        }
        par0Tessellator.draw();
        par0Tessellator.startDrawingQuads();
        par0Tessellator.setNormal(1.0f, 0.0f, 0.0f);
        for (int var10 = 0; var10 < var8; ++var10) {
            final float var11 = var10 / var8;
            final float var12 = par1 + (par3 - par1) * var11 - 0.5f / par5;
            final float var13 = var11 + 1.0f / var8;
            par0Tessellator.addVertexWithUV(var13, 1.0, 0.0f - par7, var12, par2);
            par0Tessellator.addVertexWithUV(var13, 1.0, 0.0, var12, par2);
            par0Tessellator.addVertexWithUV(var13, 0.0, 0.0, var12, par4);
            par0Tessellator.addVertexWithUV(var13, 0.0, 0.0f - par7, var12, par4);
        }
        par0Tessellator.draw();
        par0Tessellator.startDrawingQuads();
        par0Tessellator.setNormal(0.0f, 1.0f, 0.0f);
        for (int var10 = 0; var10 < var9; ++var10) {
            final float var11 = var10 / var9;
            final float var12 = par4 + (par2 - par4) * var11 - 0.5f / par6;
            final float var13 = var11 + 1.0f / var9;
            par0Tessellator.addVertexWithUV(0.0, var13, 0.0, par1, var12);
            par0Tessellator.addVertexWithUV(1.0, var13, 0.0, par3, var12);
            par0Tessellator.addVertexWithUV(1.0, var13, 0.0f - par7, par3, var12);
            par0Tessellator.addVertexWithUV(0.0, var13, 0.0f - par7, par1, var12);
        }
        par0Tessellator.draw();
        par0Tessellator.startDrawingQuads();
        par0Tessellator.setNormal(0.0f, -1.0f, 0.0f);
        for (int var10 = 0; var10 < var9; ++var10) {
            final float var11 = var10 / var9;
            final float var12 = par4 + (par2 - par4) * var11 - 0.5f / par6;
            par0Tessellator.addVertexWithUV(1.0, var11, 0.0, par3, var12);
            par0Tessellator.addVertexWithUV(0.0, var11, 0.0, par1, var12);
            par0Tessellator.addVertexWithUV(0.0, var11, 0.0f - par7, par1, var12);
            par0Tessellator.addVertexWithUV(1.0, var11, 0.0f - par7, par3, var12);
        }
        par0Tessellator.draw();
    }
    
    public void renderItemInFirstPerson(final float par1) {
        final float var2 = this.prevEquippedProgress + (this.equippedProgress - this.prevEquippedProgress) * par1;
        final EntityClientPlayerMP var3 = Minecraft.thePlayer;
        final float var4 = var3.prevRotationPitch + (var3.rotationPitch - var3.prevRotationPitch) * par1;
        GL11.glPushMatrix();
        GL11.glRotatef(var4, 1.0f, 0.0f, 0.0f);
        GL11.glRotatef(var3.prevRotationYaw + (var3.rotationYaw - var3.prevRotationYaw) * par1, 0.0f, 1.0f, 0.0f);
        RenderHelper.enableStandardItemLighting();
        GL11.glPopMatrix();
        if (var3 instanceof EntityPlayerSP) {
            final float var5 = var3.prevRenderArmPitch + (var3.renderArmPitch - var3.prevRenderArmPitch) * par1;
            final float var6 = var3.prevRenderArmYaw + (var3.renderArmYaw - var3.prevRenderArmYaw) * par1;
            GL11.glRotatef((var3.rotationPitch - var5) * 0.1f, 1.0f, 0.0f, 0.0f);
            GL11.glRotatef((var3.rotationYaw - var6) * 0.1f, 0.0f, 1.0f, 0.0f);
        }
        final ItemStack var7 = this.itemToRender;
        float var5 = Minecraft.theWorld.getLightBrightness(MathHelper.floor_double(var3.posX), MathHelper.floor_double(var3.posY), MathHelper.floor_double(var3.posZ));
        var5 = 1.0f;
        int var8 = Minecraft.theWorld.getLightBrightnessForSkyBlocks(MathHelper.floor_double(var3.posX), MathHelper.floor_double(var3.posY), MathHelper.floor_double(var3.posZ), 0);
        final int var9 = var8 % 65536;
        int var10 = var8 / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, var9 / 1.0f, var10 / 1.0f);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        if (var7 != null) {
            var8 = Item.itemsList[var7.itemID].getColorFromItemStack(var7, 0);
            final float var11 = (var8 >> 16 & 0xFF) / 255.0f;
            final float var12 = (var8 >> 8 & 0xFF) / 255.0f;
            final float var13 = (var8 & 0xFF) / 255.0f;
            GL11.glColor4f(var5 * var11, var5 * var12, var5 * var13, 1.0f);
        }
        else {
            GL11.glColor4f(var5, var5, var5, 1.0f);
        }
        if (var7 != null && var7.itemID == Item.map.itemID) {
            GL11.glPushMatrix();
            final float var6 = 0.8f;
            float var11 = var3.getSwingProgress(par1);
            float var12 = MathHelper.sin(var11 * 3.1415927f);
            float var13 = MathHelper.sin(MathHelper.sqrt_float(var11) * 3.1415927f);
            GL11.glTranslatef(-var13 * 0.4f, MathHelper.sin(MathHelper.sqrt_float(var11) * 3.1415927f * 2.0f) * 0.2f, -var12 * 0.2f);
            var11 = 1.0f - var4 / 45.0f + 0.1f;
            if (var11 < 0.0f) {
                var11 = 0.0f;
            }
            if (var11 > 1.0f) {
                var11 = 1.0f;
            }
            var11 = -MathHelper.cos(var11 * 3.1415927f) * 0.5f + 0.5f;
            GL11.glTranslatef(0.0f, 0.0f * var6 - (1.0f - var2) * 1.2f - var11 * 0.5f + 0.04f, -0.9f * var6);
            GL11.glRotatef(90.0f, 0.0f, 1.0f, 0.0f);
            GL11.glRotatef(var11 * -85.0f, 0.0f, 0.0f, 1.0f);
            GL11.glEnable(32826);
            GL11.glBindTexture(3553, this.mc.renderEngine.getTextureForDownloadableImage(Minecraft.thePlayer.skinUrl, Minecraft.thePlayer.getTexture()));
            this.mc.renderEngine.resetBoundTexture();
            for (var10 = 0; var10 < 2; ++var10) {
                final int var14 = var10 * 2 - 1;
                GL11.glPushMatrix();
                GL11.glTranslatef(-0.0f, -0.6f, 1.1f * var14);
                GL11.glRotatef(-45 * var14, 1.0f, 0.0f, 0.0f);
                GL11.glRotatef(-90.0f, 0.0f, 0.0f, 1.0f);
                GL11.glRotatef(59.0f, 0.0f, 0.0f, 1.0f);
                GL11.glRotatef(-65 * var14, 0.0f, 1.0f, 0.0f);
                final Render var15 = RenderManager.instance.getEntityRenderObject(Minecraft.thePlayer);
                final RenderPlayer var16 = (RenderPlayer)var15;
                final float var17 = 1.0f;
                GL11.glScalef(var17, var17, var17);
                var16.renderFirstPersonArm(Minecraft.thePlayer);
                GL11.glPopMatrix();
            }
            var12 = var3.getSwingProgress(par1);
            var13 = MathHelper.sin(var12 * var12 * 3.1415927f);
            final float var18 = MathHelper.sin(MathHelper.sqrt_float(var12) * 3.1415927f);
            GL11.glRotatef(-var13 * 20.0f, 0.0f, 1.0f, 0.0f);
            GL11.glRotatef(-var18 * 20.0f, 0.0f, 0.0f, 1.0f);
            GL11.glRotatef(-var18 * 80.0f, 1.0f, 0.0f, 0.0f);
            final float var19 = 0.38f;
            GL11.glScalef(var19, var19, var19);
            GL11.glRotatef(90.0f, 0.0f, 1.0f, 0.0f);
            GL11.glRotatef(180.0f, 0.0f, 0.0f, 1.0f);
            GL11.glTranslatef(-1.0f, -1.0f, 0.0f);
            final float var17 = 0.015625f;
            GL11.glScalef(var17, var17, var17);
            this.mc.renderEngine.bindTexture("/misc/mapbg.png");
            final Tessellator var20 = Tessellator.instance;
            GL11.glNormal3f(0.0f, 0.0f, -1.0f);
            var20.startDrawingQuads();
            final byte var21 = 7;
            var20.addVertexWithUV(0 - var21, 128 + var21, 0.0, 0.0, 1.0);
            var20.addVertexWithUV(128 + var21, 128 + var21, 0.0, 1.0, 1.0);
            var20.addVertexWithUV(128 + var21, 0 - var21, 0.0, 1.0, 0.0);
            var20.addVertexWithUV(0 - var21, 0 - var21, 0.0, 0.0, 0.0);
            var20.draw();
            final MapData var22 = Item.map.getMapData(var7, Minecraft.theWorld);
            if (var22 != null) {
                this.mapItemRenderer.renderMap(Minecraft.thePlayer, this.mc.renderEngine, var22);
            }
            GL11.glPopMatrix();
        }
        else if (var7 != null) {
            GL11.glPushMatrix();
            final float var6 = 0.8f;
            if (var3.getItemInUseCount() > 0) {
                final EnumAction var23 = var7.getItemUseAction();
                if (var23 == EnumAction.eat || var23 == EnumAction.drink) {
                    final float var12 = var3.getItemInUseCount() - par1 + 1.0f;
                    final float var13 = 1.0f - var12 / var7.getMaxItemUseDuration();
                    float var18 = 1.0f - var13;
                    var18 *= var18 * var18;
                    var18 *= var18 * var18;
                    var18 *= var18 * var18;
                    final float var19 = 1.0f - var18;
                    GL11.glTranslatef(0.0f, MathHelper.abs(MathHelper.cos(var12 / 4.0f * 3.1415927f) * 0.1f) * (float)((var13 > 0.2) ? 1 : 0), 0.0f);
                    GL11.glTranslatef(var19 * 0.6f, -var19 * 0.5f, 0.0f);
                    GL11.glRotatef(var19 * 90.0f, 0.0f, 1.0f, 0.0f);
                    GL11.glRotatef(var19 * 10.0f, 1.0f, 0.0f, 0.0f);
                    GL11.glRotatef(var19 * 30.0f, 0.0f, 0.0f, 1.0f);
                }
            }
            else {
                final float var11 = var3.getSwingProgress(par1);
                final float var12 = MathHelper.sin(var11 * 3.1415927f);
                final float var13 = MathHelper.sin(MathHelper.sqrt_float(var11) * 3.1415927f);
                GL11.glTranslatef(-var13 * 0.4f, MathHelper.sin(MathHelper.sqrt_float(var11) * 3.1415927f * 2.0f) * 0.2f, -var12 * 0.2f);
            }
            GL11.glTranslatef(0.7f * var6, -0.65f * var6 - (1.0f - var2) * 0.6f, -0.9f * var6);
            GL11.glRotatef(45.0f, 0.0f, 1.0f, 0.0f);
            GL11.glEnable(32826);
            final float var11 = var3.getSwingProgress(par1);
            final float var12 = MathHelper.sin(var11 * var11 * 3.1415927f);
            final float var13 = MathHelper.sin(MathHelper.sqrt_float(var11) * 3.1415927f);
            GL11.glRotatef(-var12 * 20.0f, 0.0f, 1.0f, 0.0f);
            GL11.glRotatef(-var13 * 20.0f, 0.0f, 0.0f, 1.0f);
            GL11.glRotatef(-var13 * 80.0f, 1.0f, 0.0f, 0.0f);
            float var18 = 0.4f;
            GL11.glScalef(var18, var18, var18);
            if (var3.getItemInUseCount() > 0) {
                final EnumAction var24 = var7.getItemUseAction();
                if (var24 == EnumAction.block) {
                    GL11.glTranslatef(-0.5f, 0.2f, 0.0f);
                    GL11.glRotatef(30.0f, 0.0f, 1.0f, 0.0f);
                    GL11.glRotatef(-80.0f, 1.0f, 0.0f, 0.0f);
                    GL11.glRotatef(60.0f, 0.0f, 1.0f, 0.0f);
                }
                else if (var24 == EnumAction.bow) {
                    GL11.glRotatef(-18.0f, 0.0f, 0.0f, 1.0f);
                    GL11.glRotatef(-12.0f, 0.0f, 1.0f, 0.0f);
                    GL11.glRotatef(-8.0f, 1.0f, 0.0f, 0.0f);
                    GL11.glTranslatef(-0.9f, 0.2f, 0.0f);
                    final float var17 = var7.getMaxItemUseDuration() - (var3.getItemInUseCount() - par1 + 1.0f);
                    float var25 = var17 / 20.0f;
                    var25 = (var25 * var25 + var25 * 2.0f) / 3.0f;
                    if (var25 > 1.0f) {
                        var25 = 1.0f;
                    }
                    if (var25 > 0.1f) {
                        GL11.glTranslatef(0.0f, MathHelper.sin((var17 - 0.1f) * 1.3f) * 0.01f * (var25 - 0.1f), 0.0f);
                    }
                    GL11.glTranslatef(0.0f, 0.0f, var25 * 0.1f);
                    GL11.glRotatef(-335.0f, 0.0f, 0.0f, 1.0f);
                    GL11.glRotatef(-50.0f, 0.0f, 1.0f, 0.0f);
                    GL11.glTranslatef(0.0f, 0.5f, 0.0f);
                    final float var26 = 1.0f + var25 * 0.2f;
                    GL11.glScalef(1.0f, 1.0f, var26);
                    GL11.glTranslatef(0.0f, -0.5f, 0.0f);
                    GL11.glRotatef(50.0f, 0.0f, 1.0f, 0.0f);
                    GL11.glRotatef(335.0f, 0.0f, 0.0f, 1.0f);
                }
            }
            if (var7.getItem().shouldRotateAroundWhenRendering()) {
                GL11.glRotatef(180.0f, 0.0f, 1.0f, 0.0f);
            }
            if (var7.getItem().requiresMultipleRenderPasses()) {
                this.renderItem(var3, var7, 0);
                final int var27 = Item.itemsList[var7.itemID].getColorFromItemStack(var7, 1);
                final float var17 = (var27 >> 16 & 0xFF) / 255.0f;
                final float var25 = (var27 >> 8 & 0xFF) / 255.0f;
                final float var26 = (var27 & 0xFF) / 255.0f;
                GL11.glColor4f(var5 * var17, var5 * var25, var5 * var26, 1.0f);
                this.renderItem(var3, var7, 1);
            }
            else {
                this.renderItem(var3, var7, 0);
            }
            GL11.glPopMatrix();
        }
        else if (!var3.isInvisible()) {
            GL11.glPushMatrix();
            final float var6 = 0.8f;
            float var11 = var3.getSwingProgress(par1);
            float var12 = MathHelper.sin(var11 * 3.1415927f);
            float var13 = MathHelper.sin(MathHelper.sqrt_float(var11) * 3.1415927f);
            GL11.glTranslatef(-var13 * 0.3f, MathHelper.sin(MathHelper.sqrt_float(var11) * 3.1415927f * 2.0f) * 0.4f, -var12 * 0.4f);
            GL11.glTranslatef(0.8f * var6, -0.75f * var6 - (1.0f - var2) * 0.6f, -0.9f * var6);
            GL11.glRotatef(45.0f, 0.0f, 1.0f, 0.0f);
            GL11.glEnable(32826);
            var11 = var3.getSwingProgress(par1);
            var12 = MathHelper.sin(var11 * var11 * 3.1415927f);
            var13 = MathHelper.sin(MathHelper.sqrt_float(var11) * 3.1415927f);
            GL11.glRotatef(var13 * 70.0f, 0.0f, 1.0f, 0.0f);
            GL11.glRotatef(-var12 * 20.0f, 0.0f, 0.0f, 1.0f);
            GL11.glBindTexture(3553, this.mc.renderEngine.getTextureForDownloadableImage(Minecraft.thePlayer.skinUrl, Minecraft.thePlayer.getTexture()));
            this.mc.renderEngine.resetBoundTexture();
            GL11.glTranslatef(-1.0f, 3.6f, 3.5f);
            GL11.glRotatef(120.0f, 0.0f, 0.0f, 1.0f);
            GL11.glRotatef(200.0f, 1.0f, 0.0f, 0.0f);
            GL11.glRotatef(-135.0f, 0.0f, 1.0f, 0.0f);
            GL11.glScalef(1.0f, 1.0f, 1.0f);
            GL11.glTranslatef(5.6f, 0.0f, 0.0f);
            final Render var15 = RenderManager.instance.getEntityRenderObject(Minecraft.thePlayer);
            final RenderPlayer var16 = (RenderPlayer)var15;
            final float var17 = 1.0f;
            GL11.glScalef(var17, var17, var17);
            var16.renderFirstPersonArm(Minecraft.thePlayer);
            GL11.glPopMatrix();
        }
        GL11.glDisable(32826);
        RenderHelper.disableStandardItemLighting();
    }
    
    public void renderOverlays(final float par1) {
        GL11.glDisable(3008);
        if (Minecraft.thePlayer.isBurning()) {
            this.mc.renderEngine.bindTexture("/terrain.png");
            this.renderFireInFirstPerson(par1);
        }
        if (Minecraft.thePlayer.isEntityInsideOpaqueBlock()) {
            final int var2 = MathHelper.floor_double(Minecraft.thePlayer.posX);
            final int var3 = MathHelper.floor_double(Minecraft.thePlayer.posY);
            final int var4 = MathHelper.floor_double(Minecraft.thePlayer.posZ);
            this.mc.renderEngine.bindTexture("/terrain.png");
            int var5 = Minecraft.theWorld.getBlockId(var2, var3, var4);
            if (Minecraft.theWorld.isBlockNormalCube(var2, var3, var4)) {
                this.renderInsideOfBlock(par1, Block.blocksList[var5].getBlockTextureFromSide(2));
            }
            else {
                for (int var6 = 0; var6 < 8; ++var6) {
                    final float var7 = ((var6 >> 0) % 2 - 0.5f) * Minecraft.thePlayer.width * 0.9f;
                    final float var8 = ((var6 >> 1) % 2 - 0.5f) * Minecraft.thePlayer.height * 0.2f;
                    final float var9 = ((var6 >> 2) % 2 - 0.5f) * Minecraft.thePlayer.width * 0.9f;
                    final int var10 = MathHelper.floor_float(var2 + var7);
                    final int var11 = MathHelper.floor_float(var3 + var8);
                    final int var12 = MathHelper.floor_float(var4 + var9);
                    if (Minecraft.theWorld.isBlockNormalCube(var10, var11, var12)) {
                        var5 = Minecraft.theWorld.getBlockId(var10, var11, var12);
                    }
                }
            }
            if (Block.blocksList[var5] != null) {
                this.renderInsideOfBlock(par1, Block.blocksList[var5].getBlockTextureFromSide(2));
            }
        }
        if (Minecraft.thePlayer.isInsideOfMaterial(Material.water)) {
            this.mc.renderEngine.bindTexture("/misc/water.png");
            this.renderWarpedTextureOverlay(par1);
        }
        GL11.glEnable(3008);
    }
    
    private void renderInsideOfBlock(final float par1, final Icon par2Icon) {
        final Tessellator var3 = Tessellator.instance;
        final float var4 = 0.1f;
        GL11.glColor4f(var4, var4, var4, 0.5f);
        GL11.glPushMatrix();
        final float var5 = -1.0f;
        final float var6 = 1.0f;
        final float var7 = -1.0f;
        final float var8 = 1.0f;
        final float var9 = -0.5f;
        final float var10 = par2Icon.getMinU();
        final float var11 = par2Icon.getMaxU();
        final float var12 = par2Icon.getMinV();
        final float var13 = par2Icon.getMaxV();
        var3.startDrawingQuads();
        var3.addVertexWithUV(var5, var7, var9, var11, var13);
        var3.addVertexWithUV(var6, var7, var9, var10, var13);
        var3.addVertexWithUV(var6, var8, var9, var10, var12);
        var3.addVertexWithUV(var5, var8, var9, var11, var12);
        var3.draw();
        GL11.glPopMatrix();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    private void renderWarpedTextureOverlay(final float par1) {
        final Tessellator var2 = Tessellator.instance;
        final float var3 = Minecraft.thePlayer.getBrightness(par1);
        GL11.glColor4f(var3, var3, var3, 0.5f);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glPushMatrix();
        final float var4 = 4.0f;
        final float var5 = -1.0f;
        final float var6 = 1.0f;
        final float var7 = -1.0f;
        final float var8 = 1.0f;
        final float var9 = -0.5f;
        final float var10 = -Minecraft.thePlayer.rotationYaw / 64.0f;
        final float var11 = Minecraft.thePlayer.rotationPitch / 64.0f;
        var2.startDrawingQuads();
        var2.addVertexWithUV(var5, var7, var9, var4 + var10, var4 + var11);
        var2.addVertexWithUV(var6, var7, var9, 0.0f + var10, var4 + var11);
        var2.addVertexWithUV(var6, var8, var9, 0.0f + var10, 0.0f + var11);
        var2.addVertexWithUV(var5, var8, var9, var4 + var10, 0.0f + var11);
        var2.draw();
        GL11.glPopMatrix();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glDisable(3042);
    }
    
    private void renderFireInFirstPerson(final float par1) {
        final Tessellator var2 = Tessellator.instance;
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.9f);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        final float var3 = 1.0f;
        for (int var4 = 0; var4 < 2; ++var4) {
            GL11.glPushMatrix();
            final Icon var5 = Block.fire.func_94438_c(1);
            final float var6 = var5.getMinU();
            final float var7 = var5.getMaxU();
            final float var8 = var5.getMinV();
            final float var9 = var5.getMaxV();
            final float var10 = (0.0f - var3) / 2.0f;
            final float var11 = var10 + var3;
            final float var12 = 0.0f - var3 / 2.0f;
            final float var13 = var12 + var3;
            final float var14 = -0.5f;
            GL11.glTranslatef(-(var4 * 2 - 1) * 0.24f, -0.3f, 0.0f);
            GL11.glRotatef((var4 * 2 - 1) * 10.0f, 0.0f, 1.0f, 0.0f);
            var2.startDrawingQuads();
            var2.addVertexWithUV(var10, var12, var14, var7, var9);
            var2.addVertexWithUV(var11, var12, var14, var6, var9);
            var2.addVertexWithUV(var11, var13, var14, var6, var8);
            var2.addVertexWithUV(var10, var13, var14, var7, var8);
            var2.draw();
            GL11.glPopMatrix();
        }
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glDisable(3042);
    }
    
    public void updateEquippedItem() {
        this.prevEquippedProgress = this.equippedProgress;
        final EntityClientPlayerMP var1 = Minecraft.thePlayer;
        final ItemStack var2 = var1.inventory.getCurrentItem();
        boolean var3 = this.equippedItemSlot == var1.inventory.currentItem && var2 == this.itemToRender;
        if (this.itemToRender == null && var2 == null) {
            var3 = true;
        }
        if (var2 != null && this.itemToRender != null && var2 != this.itemToRender && var2.itemID == this.itemToRender.itemID && var2.getItemDamage() == this.itemToRender.getItemDamage()) {
            this.itemToRender = var2;
            var3 = true;
        }
        final float var4 = 0.4f;
        final float var5 = var3 ? 1.0f : 0.0f;
        float var6 = var5 - this.equippedProgress;
        if (var6 < -var4) {
            var6 = -var4;
        }
        if (var6 > var4) {
            var6 = var4;
        }
        this.equippedProgress += var6;
        if (this.equippedProgress < 0.1f) {
            this.itemToRender = var2;
            this.equippedItemSlot = var1.inventory.currentItem;
        }
    }
    
    public void resetEquippedProgress() {
        this.equippedProgress = 0.0f;
    }
    
    public void resetEquippedProgress2() {
        this.equippedProgress = 0.0f;
    }
}
