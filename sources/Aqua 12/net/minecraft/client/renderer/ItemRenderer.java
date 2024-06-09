// 
// Decompiled by Procyon v0.5.36
// 

package net.minecraft.client.renderer;

import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.material.Material;
import net.optifine.reflect.Reflector;
import net.minecraft.item.EnumAction;
import intent.AquaDev.aqua.modules.combat.Killaura;
import net.minecraft.item.ItemMap;
import intent.AquaDev.aqua.modules.visual.HeldItem;
import intent.AquaDev.aqua.Aqua;
import net.minecraft.world.storage.MapData;
import net.minecraft.world.World;
import net.minecraft.init.Items;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.client.entity.EntityPlayerSP;
import net.optifine.DynamicLights;
import net.minecraft.util.BlockPos;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.item.Item;
import net.optifine.shaders.Shaders;
import net.minecraft.src.Config;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.item.ItemStack;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class ItemRenderer
{
    private static final ResourceLocation RES_MAP_BACKGROUND;
    private static final ResourceLocation RES_UNDERWATER_OVERLAY;
    private final Minecraft mc;
    private ItemStack itemToRender;
    private float equippedProgress;
    private float prevEquippedProgress;
    private final RenderManager renderManager;
    private final RenderItem itemRenderer;
    private int equippedItemSlot;
    
    public ItemRenderer(final Minecraft mcIn) {
        this.equippedItemSlot = -1;
        this.mc = mcIn;
        this.renderManager = mcIn.getRenderManager();
        this.itemRenderer = mcIn.getRenderItem();
    }
    
    public void renderItem(final EntityLivingBase entityIn, final ItemStack heldStack, final ItemCameraTransforms.TransformType transform) {
        if (heldStack != null) {
            final Item item = heldStack.getItem();
            final Block block = Block.getBlockFromItem(item);
            GlStateManager.pushMatrix();
            if (this.itemRenderer.shouldRenderItemIn3D(heldStack)) {
                GlStateManager.scale(2.0f, 2.0f, 2.0f);
                if (this.isBlockTranslucent(block) && (!Config.isShaders() || !Shaders.renderItemKeepDepthMask)) {
                    GlStateManager.depthMask(false);
                }
            }
            this.itemRenderer.renderItemModelForEntity(heldStack, entityIn, transform);
            if (this.isBlockTranslucent(block)) {
                GlStateManager.depthMask(true);
            }
            GlStateManager.popMatrix();
        }
    }
    
    private boolean isBlockTranslucent(final Block blockIn) {
        return blockIn != null && blockIn.getBlockLayer() == EnumWorldBlockLayer.TRANSLUCENT;
    }
    
    private void rotateArroundXAndY(final float angle, final float angleY) {
        GlStateManager.pushMatrix();
        GlStateManager.rotate(angle, 1.0f, 0.0f, 0.0f);
        GlStateManager.rotate(angleY, 0.0f, 1.0f, 0.0f);
        RenderHelper.enableStandardItemLighting();
        GlStateManager.popMatrix();
    }
    
    private void setLightMapFromPlayer(final AbstractClientPlayer clientPlayer) {
        int i = this.mc.theWorld.getCombinedLight(new BlockPos(clientPlayer.posX, clientPlayer.posY + clientPlayer.getEyeHeight(), clientPlayer.posZ), 0);
        if (Config.isDynamicLights()) {
            i = DynamicLights.getCombinedLight(this.mc.getRenderViewEntity(), i);
        }
        final float f = (float)(i & 0xFFFF);
        final float f2 = (float)(i >> 16);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, f, f2);
    }
    
    private void rotateWithPlayerRotations(final EntityPlayerSP entityplayerspIn, final float partialTicks) {
        final float f = entityplayerspIn.prevRenderArmPitch + (entityplayerspIn.renderArmPitch - entityplayerspIn.prevRenderArmPitch) * partialTicks;
        final float f2 = entityplayerspIn.prevRenderArmYaw + (entityplayerspIn.renderArmYaw - entityplayerspIn.prevRenderArmYaw) * partialTicks;
        GlStateManager.rotate((entityplayerspIn.rotationPitch - f) * 0.1f, 1.0f, 0.0f, 0.0f);
        GlStateManager.rotate((entityplayerspIn.rotationYaw - f2) * 0.1f, 0.0f, 1.0f, 0.0f);
    }
    
    private float getMapAngleFromPitch(final float pitch) {
        float f = 1.0f - pitch / 45.0f + 0.1f;
        f = MathHelper.clamp_float(f, 0.0f, 1.0f);
        f = -MathHelper.cos(f * 3.1415927f) * 0.5f + 0.5f;
        return f;
    }
    
    private void renderRightArm(final RenderPlayer renderPlayerIn) {
        GlStateManager.pushMatrix();
        GlStateManager.rotate(54.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(64.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.rotate(-62.0f, 0.0f, 0.0f, 1.0f);
        GlStateManager.translate(0.25f, -0.85f, 0.75f);
        renderPlayerIn.renderRightArm(this.mc.thePlayer);
        GlStateManager.popMatrix();
    }
    
    private void renderLeftArm(final RenderPlayer renderPlayerIn) {
        GlStateManager.pushMatrix();
        GlStateManager.rotate(92.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(45.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.rotate(41.0f, 0.0f, 0.0f, 1.0f);
        GlStateManager.translate(-0.3f, -1.1f, 0.45f);
        renderPlayerIn.renderLeftArm(this.mc.thePlayer);
        GlStateManager.popMatrix();
    }
    
    private void renderPlayerArms(final AbstractClientPlayer clientPlayer) {
        this.mc.getTextureManager().bindTexture(clientPlayer.getLocationSkin());
        final Render<AbstractClientPlayer> render = this.renderManager.getEntityRenderObject(this.mc.thePlayer);
        final RenderPlayer renderplayer = (RenderPlayer)render;
        if (!clientPlayer.isInvisible()) {
            GlStateManager.disableCull();
            this.renderRightArm(renderplayer);
            this.renderLeftArm(renderplayer);
            GlStateManager.enableCull();
        }
    }
    
    private void renderItemMap(final AbstractClientPlayer clientPlayer, final float pitch, final float equipmentProgress, final float swingProgress) {
        final float f = -0.4f * MathHelper.sin(MathHelper.sqrt_float(swingProgress) * 3.1415927f);
        final float f2 = 0.2f * MathHelper.sin(MathHelper.sqrt_float(swingProgress) * 3.1415927f * 2.0f);
        final float f3 = -0.2f * MathHelper.sin(swingProgress * 3.1415927f);
        GlStateManager.translate(f, f2, f3);
        final float f4 = this.getMapAngleFromPitch(pitch);
        GlStateManager.translate(0.0f, 0.04f, -0.72f);
        GlStateManager.translate(0.0f, equipmentProgress * -1.2f, 0.0f);
        GlStateManager.translate(0.0f, f4 * -0.5f, 0.0f);
        GlStateManager.rotate(90.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(f4 * -85.0f, 0.0f, 0.0f, 1.0f);
        GlStateManager.rotate(0.0f, 1.0f, 0.0f, 0.0f);
        this.renderPlayerArms(clientPlayer);
        final float f5 = MathHelper.sin(swingProgress * swingProgress * 3.1415927f);
        final float f6 = MathHelper.sin(MathHelper.sqrt_float(swingProgress) * 3.1415927f);
        GlStateManager.rotate(f5 * -20.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(f6 * -20.0f, 0.0f, 0.0f, 1.0f);
        GlStateManager.rotate(f6 * -80.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.scale(0.38f, 0.38f, 0.38f);
        GlStateManager.rotate(90.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(180.0f, 0.0f, 0.0f, 1.0f);
        GlStateManager.rotate(0.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.translate(-1.0f, -1.0f, 0.0f);
        GlStateManager.scale(0.015625f, 0.015625f, 0.015625f);
        this.mc.getTextureManager().bindTexture(ItemRenderer.RES_MAP_BACKGROUND);
        final Tessellator tessellator = Tessellator.getInstance();
        final WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        GL11.glNormal3f(0.0f, 0.0f, -1.0f);
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldrenderer.pos(-7.0, 135.0, 0.0).tex(0.0, 1.0).endVertex();
        worldrenderer.pos(135.0, 135.0, 0.0).tex(1.0, 1.0).endVertex();
        worldrenderer.pos(135.0, -7.0, 0.0).tex(1.0, 0.0).endVertex();
        worldrenderer.pos(-7.0, -7.0, 0.0).tex(0.0, 0.0).endVertex();
        tessellator.draw();
        final MapData mapdata = Items.filled_map.getMapData(this.itemToRender, this.mc.theWorld);
        if (mapdata != null) {
            this.mc.entityRenderer.getMapItemRenderer().renderMap(mapdata, false);
        }
    }
    
    private void renderPlayerArm(final AbstractClientPlayer clientPlayer, final float equipProgress, final float swingProgress) {
        final float f = -0.3f * MathHelper.sin(MathHelper.sqrt_float(swingProgress) * 3.1415927f);
        final float f2 = 0.4f * MathHelper.sin(MathHelper.sqrt_float(swingProgress) * 3.1415927f * 2.0f);
        final float f3 = -0.4f * MathHelper.sin(swingProgress * 3.1415927f);
        GlStateManager.translate(f, f2, f3);
        GlStateManager.translate(0.64000005f, -0.6f, -0.71999997f);
        GlStateManager.translate(0.0f, equipProgress * -0.6f, 0.0f);
        GlStateManager.rotate(45.0f, 0.0f, 1.0f, 0.0f);
        final float f4 = MathHelper.sin(swingProgress * swingProgress * 3.1415927f);
        final float f5 = MathHelper.sin(MathHelper.sqrt_float(swingProgress) * 3.1415927f);
        GlStateManager.rotate(f5 * 70.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(f4 * -20.0f, 0.0f, 0.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(clientPlayer.getLocationSkin());
        GlStateManager.translate(-1.0f, 3.6f, 3.5f);
        GlStateManager.rotate(120.0f, 0.0f, 0.0f, 1.0f);
        GlStateManager.rotate(200.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.rotate(-135.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.scale(1.0f, 1.0f, 1.0f);
        GlStateManager.translate(5.6f, 0.0f, 0.0f);
        final Render<AbstractClientPlayer> render = this.renderManager.getEntityRenderObject(this.mc.thePlayer);
        GlStateManager.disableCull();
        final RenderPlayer renderplayer = (RenderPlayer)render;
        renderplayer.renderRightArm(this.mc.thePlayer);
        GlStateManager.enableCull();
    }
    
    private void doItemUsedTransformations(final float swingProgress) {
        HeldItem.x = (float)Aqua.setmgr.getSetting("HeldItemX").getCurrentNumber();
        HeldItem.y = (float)Aqua.setmgr.getSetting("HeldItemY").getCurrentNumber();
        HeldItem.z = (float)Aqua.setmgr.getSetting("HeldItemZ").getCurrentNumber();
        final float f = -0.4f * MathHelper.sin(MathHelper.sqrt_float(swingProgress) * 3.1415927f);
        final float f2 = 0.2f * MathHelper.sin(MathHelper.sqrt_float(swingProgress) * 3.1415927f * 2.0f);
        final float f3 = -0.2f * MathHelper.sin(swingProgress * 3.1415927f);
        if (Aqua.moduleManager.getModuleByName("HeldItem").isToggled()) {
            GlStateManager.translate(HeldItem.x, HeldItem.y, HeldItem.z);
        }
        else {
            GlStateManager.translate(f, f2, f3);
        }
    }
    
    private void performDrinking(final AbstractClientPlayer clientPlayer, final float partialTicks) {
        final float f = clientPlayer.getItemInUseCount() - partialTicks + 1.0f;
        final float f2 = f / this.itemToRender.getMaxItemUseDuration();
        float f3 = MathHelper.abs(MathHelper.cos(f / 4.0f * 3.1415927f) * 0.1f);
        if (f2 >= 0.8f) {
            f3 = 0.0f;
        }
        GlStateManager.translate(0.0f, f3, 0.0f);
        final float f4 = 1.0f - (float)Math.pow(f2, 27.0);
        GlStateManager.translate(f4 * 0.6f, f4 * -0.5f, f4 * 0.0f);
        GlStateManager.rotate(f4 * 90.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(f4 * 10.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.rotate(f4 * 30.0f, 0.0f, 0.0f, 1.0f);
    }
    
    private void transformFirstPersonItem(final float equipProgress, final float swingProgress) {
        GlStateManager.translate(0.56f, -0.52f, -0.71999997f);
        GlStateManager.translate(0.0f, equipProgress * -0.6f, 0.0f);
        GlStateManager.rotate(45.0f, 0.0f, 1.0f, 0.0f);
        final float f = MathHelper.sin(swingProgress * swingProgress * 3.1415927f);
        final float f2 = MathHelper.sin(MathHelper.sqrt_float(swingProgress) * 3.1415927f);
        GlStateManager.rotate(f * -20.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(f2 * -20.0f, 0.0f, 0.0f, 1.0f);
        GlStateManager.rotate(f2 * -80.0f, 1.0f, 0.0f, 0.0f);
        HeldItem.scale = (float)Aqua.setmgr.getSetting("HeldItemScale").getCurrentNumber();
        if (Aqua.moduleManager.getModuleByName("HeldItem").isToggled()) {
            GlStateManager.scale(HeldItem.scale, HeldItem.scale, HeldItem.scale);
        }
        else {
            GlStateManager.scale(0.4, 0.4, 0.4);
        }
    }
    
    private void doBowTransformations(final float partialTicks, final AbstractClientPlayer clientPlayer) {
        GlStateManager.rotate(-18.0f, 0.0f, 0.0f, 1.0f);
        GlStateManager.rotate(-12.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(-8.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.translate(-0.9f, 0.2f, 0.0f);
        final float f = this.itemToRender.getMaxItemUseDuration() - (clientPlayer.getItemInUseCount() - partialTicks + 1.0f);
        float f2 = f / 20.0f;
        f2 = (f2 * f2 + f2 * 2.0f) / 3.0f;
        if (f2 > 1.0f) {
            f2 = 1.0f;
        }
        if (f2 > 0.1f) {
            final float f3 = MathHelper.sin((f - 0.1f) * 1.3f);
            final float f4 = f2 - 0.1f;
            final float f5 = f3 * f4;
            GlStateManager.translate(f5 * 0.0f, f5 * 0.01f, f5 * 0.0f);
        }
        GlStateManager.translate(f2 * 0.0f, f2 * 0.0f, f2 * 0.1f);
        GlStateManager.scale(1.0f, 1.0f, 1.0f + f2 * 0.2f);
    }
    
    private void doBlockTransformations() {
        GlStateManager.translate(-0.5f, 0.2f, 0.0f);
        GlStateManager.rotate(30.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(-80.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.rotate(60.0f, 0.0f, 1.0f, 0.0f);
    }
    
    private void func_178103_d() {
        GlStateManager.translate(-0.5f, 0.2f, 0.0f);
        GlStateManager.rotate(30.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(-80.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.rotate(60.0f, 0.0f, 1.0f, 0.0f);
    }
    
    private void transformFirstPersonItem3(final float equipProgress, final float swingProgress) {
        GlStateManager.translate(0.56f, -0.52f, -0.71999997f);
        GlStateManager.translate(0.0f, equipProgress * -0.6f, 0.0f);
        GlStateManager.rotate(45.0f, 0.0f, 1.0f, 0.0f);
        final float f = MathHelper.sin(swingProgress * swingProgress * 3.1415927f);
        final float f2 = MathHelper.sin(MathHelper.sqrt_float(swingProgress) * 3.1415927f);
        GlStateManager.rotate(f * -20.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(f2 * -20.0f, 0.0f, 0.0f, 1.0f);
        GlStateManager.rotate(f2 * -80.0f, 1.0f, 0.0f, 0.0f);
        if (Aqua.moduleManager.getModuleByName("HeldItem").isToggled()) {
            final float size = (float)Aqua.setmgr.getSetting("HeldItemScale").getCurrentNumber();
            GlStateManager.scale(size, size, size);
        }
        else {
            GlStateManager.scale(0.4f, 0.4f, 0.4f);
        }
    }
    
    public void renderItemInFirstPerson(final float partialTicks) {
        if (!Config.isShaders() || !Shaders.isSkipRenderHand()) {
            final float f = 1.0f - (this.prevEquippedProgress + (this.equippedProgress - this.prevEquippedProgress) * partialTicks);
            final AbstractClientPlayer abstractclientplayer = this.mc.thePlayer;
            final float f2 = abstractclientplayer.getSwingProgress(partialTicks);
            final float f3 = abstractclientplayer.prevRotationPitch + (abstractclientplayer.rotationPitch - abstractclientplayer.prevRotationPitch) * partialTicks;
            final float f4 = abstractclientplayer.prevRotationYaw + (abstractclientplayer.rotationYaw - abstractclientplayer.prevRotationYaw) * partialTicks;
            this.rotateArroundXAndY(f3, f4);
            this.setLightMapFromPlayer(abstractclientplayer);
            this.rotateWithPlayerRotations((EntityPlayerSP)abstractclientplayer, partialTicks);
            GlStateManager.enableRescaleNormal();
            GlStateManager.pushMatrix();
            if (this.itemToRender != null) {
                if (this.itemToRender.getItem() instanceof ItemMap) {
                    this.renderItemMap(abstractclientplayer, f3, f, f2);
                }
                else if (abstractclientplayer.getItemInUseCount() > 0) {
                    final EnumAction enumaction = this.itemToRender.getItemUseAction();
                    switch (enumaction) {
                        case NONE: {
                            this.transformFirstPersonItem(f, 0.0f);
                            break;
                        }
                        case EAT:
                        case DRINK: {
                            this.performDrinking(abstractclientplayer, partialTicks);
                            this.transformFirstPersonItem(f, 0.0f);
                            break;
                        }
                        case BLOCK: {
                            if (!Aqua.moduleManager.getModuleByName("Animations").isToggled()) {
                                this.transformFirstPersonItem(f, 0.0f);
                                this.doBlockTransformations();
                                break;
                            }
                            if (Aqua.setmgr.getSetting("AnimationsMode").getCurrentMode().equalsIgnoreCase("1.7")) {
                                if (Aqua.moduleManager.getModuleByName("HeldItem").isToggled()) {
                                    GlStateManager.translate(0.0f, (float)Aqua.setmgr.getSetting("HeldItemY").getCurrentNumber(), 0.0f);
                                }
                                this.transformFirstPersonItem(f, f2 + f / 3.0f);
                                this.func_178103_d();
                                GlStateManager.translate(0.1, 0.0, 0.0);
                            }
                            if (Aqua.setmgr.getSetting("AnimationsMode").getCurrentMode().equalsIgnoreCase("Own")) {
                                GlStateManager.translate(0.0f, (float)Aqua.setmgr.getSetting("HeldItemY").getCurrentNumber(), 0.0f);
                                this.transformFirstPersonItem(f / 3.0f, 0.0f);
                                GlStateManager.translate(0.0f, (float)Aqua.setmgr.getSetting("HeldItemY").getCurrentNumber(), 0.0f);
                                final float rot = MathHelper.sin(MathHelper.sqrt_float(f2) * 3.1415927f);
                                GlStateManager.rotate(-rot * 36.0f, 2.09f, 0.0f, 0.0f);
                                GlStateManager.rotate(-rot * 25.0f, 2.09f, 0.0f, 15.0f);
                                GlStateManager.rotate(-rot * 16.0f, 12.09f, 3.0f, 0.0f);
                                GlStateManager.rotate(5.0f, 0.0f, -2.0f, 12.0f);
                                this.func_178103_d();
                            }
                            if (Aqua.setmgr.getSetting("AnimationsMode").getCurrentMode().equalsIgnoreCase("Snip")) {
                                this.transformFirstPersonItem(f / 3.0f, 0.0f);
                                if (Aqua.moduleManager.getModuleByName("HeldItem").isToggled()) {
                                    GlStateManager.translate(0.0f, (float)Aqua.setmgr.getSetting("HeldItemY").getCurrentNumber(), 0.0f);
                                }
                                else {
                                    GlStateManager.translate(0.0, 0.3, 0.0);
                                }
                                final float rot = MathHelper.sin(MathHelper.sqrt_float(f2) * 3.1415927f);
                                GlStateManager.rotate(-rot, 0.0f, 0.0f, rot);
                                GlStateManager.rotate(-rot * 2.0f, rot * 7.0f, 0.0f, 0.0f);
                                GlStateManager.rotate(-rot * 2.0f, 16.0f, 0.0f, 15.0f);
                                this.doBlockTransformations();
                            }
                            if (Aqua.setmgr.getSetting("AnimationsMode").getCurrentMode().equalsIgnoreCase("Jello")) {
                                final float funny = MathHelper.sin(MathHelper.sqrt_float(f2) * 3.1415927f);
                                if (Aqua.moduleManager.getModuleByName("HeldItem").isToggled()) {
                                    GlStateManager.translate(0.0f, (float)Aqua.setmgr.getSetting("HeldItemY").getCurrentNumber(), 0.0f);
                                }
                                this.transformFirstPersonItem(0.0f, 0.0f);
                                GlStateManager.scale(0.8f, 0.8f, 0.8f);
                                GlStateManager.translate(0.0f, 0.1f, 0.0f);
                                this.func_178103_d();
                                GlStateManager.rotate(funny * 35.0f / 2.0f, 0.0f, 1.0f, 1.5f);
                                GlStateManager.rotate(-funny * 190.0f / 4.0f, 1.0f, 0.5f, 0.0f);
                            }
                            if (Aqua.setmgr.getSetting("AnimationsMode").getCurrentMode().equalsIgnoreCase("High1.7")) {
                                if (Aqua.moduleManager.getModuleByName("HeldItem").isToggled()) {
                                    GlStateManager.translate(0.0f, (float)Aqua.setmgr.getSetting("HeldItemY").getCurrentNumber(), 0.0f);
                                }
                                GlStateManager.translate(0.0, 0.1, 0.0);
                                this.transformFirstPersonItem(f / 2.0f, f2);
                                this.func_178103_d();
                            }
                            if (Aqua.setmgr.getSetting("AnimationsMode").getCurrentMode().equalsIgnoreCase("Whack")) {
                                if (Aqua.moduleManager.getModuleByName("HeldItem").isToggled()) {
                                    GlStateManager.translate(0.0f, (float)Aqua.setmgr.getSetting("HeldItemY").getCurrentNumber(), 0.0f);
                                }
                                this.transformFirstPersonItem(f / 3.0f, 0.0f);
                                final float swing = MathHelper.sin((float)(MathHelper.sqrt_float(f2) * 3.141592653589793));
                                GL11.glRotatef(-swing * 80.0f / 5.0f, swing / 3.0f, -0.0f, 9.0f);
                                GL11.glRotatef(-swing * 40.0f, 8.0f, swing / 9.0f, -0.1f);
                                this.func_178103_d();
                            }
                            if (Aqua.setmgr.getSetting("AnimationsMode").getCurrentMode().equalsIgnoreCase("Skidding")) {
                                if (Aqua.moduleManager.getModuleByName("HeldItem").isToggled()) {
                                    GlStateManager.translate(0.0f, (float)Aqua.setmgr.getSetting("HeldItemY").getCurrentNumber(), 0.0f);
                                }
                                final float funny = MathHelper.sin(MathHelper.sqrt_float(f) * 3.1415927f);
                                GlStateManager.translate(0.0f, (float)Aqua.setmgr.getSetting("HeldItemY").getCurrentNumber(), 0.0f);
                                GlStateManager.translate(0.56f - funny / 15.0f, -0.4f + funny / 15.0f, -0.71999997f);
                                GlStateManager.translate(0.0f, f * -0.6f, 0.0f);
                                GlStateManager.rotate(40.0f, 0.0f, 1.0f, 0.0f);
                                final float f5 = MathHelper.sin(f2 * f2 * 3.1415927f);
                                final float f6 = MathHelper.sin(MathHelper.sqrt_float(f2) * 3.1415927f);
                                GlStateManager.rotate(f5 * -30.0f, 0.0f, 1.0f, 0.0f);
                                GlStateManager.rotate(f6 * -20.0f, 0.0f, 0.0f, 1.0f);
                                GlStateManager.rotate(f6 * -85.0f, 1.0f, 0.0f, 0.0f);
                                final float size = (float)Aqua.setmgr.getSetting("HeldItemScale").getCurrentNumber();
                                GlStateManager.scale(size, size, size);
                                this.func_178103_d();
                            }
                            if (Aqua.setmgr.getSetting("AnimationsMode").getCurrentMode().equalsIgnoreCase("Exhibition")) {
                                if (Aqua.moduleManager.getModuleByName("HeldItem").isToggled()) {
                                    GlStateManager.translate(0.0f, (float)Aqua.setmgr.getSetting("HeldItemY").getCurrentNumber(), 0.0f);
                                }
                                this.transformFirstPersonItem(f / 3.0f, 0.0f);
                                GlStateManager.translate(0.0, 0.3, 0.0);
                                final float rot = MathHelper.sin(MathHelper.sqrt_float(f2) * 3.1415927f);
                                GlStateManager.rotate(-rot * 36.0f, 2.09f, 0.0f, 0.0f);
                                GlStateManager.rotate(-rot * 25.0f, 2.09f, 0.0f, 15.0f);
                                this.func_178103_d();
                            }
                            if (Aqua.setmgr.getSetting("AnimationsMode").getCurrentMode().equalsIgnoreCase("Butter")) {
                                if (Aqua.moduleManager.getModuleByName("HeldItem").isToggled()) {
                                    GlStateManager.translate(0.0f, (float)Aqua.setmgr.getSetting("HeldItemY").getCurrentNumber(), 0.0f);
                                }
                                GlStateManager.translate(0.56f, -0.52f, -0.71999997f);
                                GlStateManager.translate(0.0f, f * -0.6f, 0.0f);
                                GlStateManager.rotate(45.0f, 0.0f, 1.0f, 0.0f);
                                final float f7 = MathHelper.sin(f2 * f2 * 3.1415927f);
                                final float f8 = MathHelper.sin(MathHelper.sqrt_float(f2) * 3.1415927f);
                                GlStateManager.rotate(f7 * -20.0f, 0.0f, 1.0f, 0.0f);
                                GlStateManager.rotate(f8 * -20.0f, 0.0f, 0.0f, 1.0f);
                                GlStateManager.rotate(f8 * -20.0f, 1.0f, 0.0f, 0.0f);
                                final float size2 = (float)Aqua.setmgr.getSetting("HeldItemScale").getCurrentNumber();
                                GlStateManager.scale(size2, size2, size2);
                                this.func_178103_d();
                            }
                            if (Aqua.setmgr.getSetting("AnimationsMode").getCurrentMode().equalsIgnoreCase("Aqua")) {
                                if (Aqua.moduleManager.getModuleByName("HeldItem").isToggled()) {
                                    GlStateManager.translate(0.0f, (float)Aqua.setmgr.getSetting("HeldItemY").getCurrentNumber(), 0.0f);
                                }
                                this.transformFirstPersonItem(f / 3.0f, 0.0f);
                                final float animation = MathHelper.sin(MathHelper.sqrt_float(f) * 3.1415927f);
                                GlStateManager.translate(0.56f - animation / 15.0f, -0.4f + animation / 15.0f, -0.71999997f);
                                GlStateManager.translate(0.0f, f * -0.6f, 0.0f);
                                GlStateManager.rotate(40.0f, 0.0f, 1.0f, 0.0f);
                                final float f5 = MathHelper.sin(f2 * f2 * 3.1415927f);
                                final float f6 = MathHelper.sin(MathHelper.sqrt_float(f2) * 3.1415927f);
                                GlStateManager.rotate(f5 * -30.0f, 0.0f, 1.0f, 0.0f);
                                GlStateManager.rotate(f6 * -20.0f, 0.0f, 0.0f, 1.0f);
                                GlStateManager.rotate(f6 * -85.0f, 1.0f, 0.0f, 0.0f);
                                if (Aqua.moduleManager.getModuleByName("HeldItem").isToggled()) {
                                    GlStateManager.scale(0.8, 0.8, 0.8);
                                }
                                else {
                                    GlStateManager.scale(0.8, 0.8, 0.8);
                                }
                                this.func_178103_d();
                                break;
                            }
                            break;
                        }
                        case BOW: {
                            this.transformFirstPersonItem(f, 0.0f);
                            this.doBowTransformations(partialTicks, abstractclientplayer);
                            break;
                        }
                    }
                }
                else if (Aqua.moduleManager.getModuleByName("FakeBlock").isToggled() && Killaura.target != null) {
                    if (Aqua.setmgr.getSetting("AnimationsMode").getCurrentMode().equalsIgnoreCase("1.7")) {
                        if (Aqua.moduleManager.getModuleByName("HeldItem").isToggled()) {
                            GlStateManager.translate(0.0f, (float)Aqua.setmgr.getSetting("HeldItemY").getCurrentNumber(), 0.0f);
                        }
                        this.transformFirstPersonItem(f, f2 + f / 3.0f);
                        this.func_178103_d();
                        GlStateManager.translate(0.1, 0.0, 0.0);
                    }
                    if (Aqua.setmgr.getSetting("AnimationsMode").getCurrentMode().equalsIgnoreCase("Snip")) {
                        this.transformFirstPersonItem(f / 3.0f, 0.0f);
                        if (Aqua.moduleManager.getModuleByName("HeldItem").isToggled()) {
                            GlStateManager.translate(0.0f, (float)Aqua.setmgr.getSetting("HeldItemY").getCurrentNumber(), 0.0f);
                        }
                        else {
                            GlStateManager.translate(0.0, 0.3, 0.0);
                        }
                        final float rot2 = MathHelper.sin(MathHelper.sqrt_float(f2) * 3.1415927f);
                        GlStateManager.rotate(-rot2, 0.0f, 0.0f, rot2);
                        GlStateManager.rotate(-rot2 * 2.0f, rot2 * 7.0f, 0.0f, 0.0f);
                        GlStateManager.rotate(-rot2 * 2.0f, 16.0f, 0.0f, 15.0f);
                        this.doBlockTransformations();
                    }
                    if (Aqua.setmgr.getSetting("AnimationsMode").getCurrentMode().equalsIgnoreCase("Own")) {
                        GlStateManager.translate(0.0f, (float)Aqua.setmgr.getSetting("HeldItemY").getCurrentNumber(), 0.0f);
                        this.transformFirstPersonItem(f / 3.0f, 0.0f);
                        GlStateManager.translate(0.0f, (float)Aqua.setmgr.getSetting("HeldItemY").getCurrentNumber(), 0.0f);
                        final float rot2 = MathHelper.sin(MathHelper.sqrt_float(f2) * 3.1415927f);
                        GlStateManager.rotate(-rot2 * 36.0f, 2.09f, 0.0f, 0.0f);
                        GlStateManager.rotate(-rot2 * 25.0f, 2.09f, 0.0f, 15.0f);
                        GlStateManager.rotate(-rot2 * 16.0f, 12.09f, 3.0f, 0.0f);
                        GlStateManager.rotate(5.0f, 0.0f, -2.0f, 12.0f);
                        this.func_178103_d();
                    }
                    if (Aqua.setmgr.getSetting("AnimationsMode").getCurrentMode().equalsIgnoreCase("Jello")) {
                        final float funny2 = MathHelper.sin(MathHelper.sqrt_float(f2) * 3.1415927f);
                        if (Aqua.moduleManager.getModuleByName("HeldItem").isToggled()) {
                            GlStateManager.translate(0.0f, (float)Aqua.setmgr.getSetting("HeldItemY").getCurrentNumber(), 0.0f);
                        }
                        this.transformFirstPersonItem(0.0f, 0.0f);
                        GlStateManager.scale(0.8f, 0.8f, 0.8f);
                        GlStateManager.translate(0.0f, 0.1f, 0.0f);
                        this.func_178103_d();
                        GlStateManager.rotate(funny2 * 35.0f / 2.0f, 0.0f, 1.0f, 1.5f);
                        GlStateManager.rotate(-funny2 * 190.0f / 4.0f, 1.0f, 0.5f, 0.0f);
                    }
                    if (Aqua.setmgr.getSetting("AnimationsMode").getCurrentMode().equalsIgnoreCase("High1.7")) {
                        if (Aqua.moduleManager.getModuleByName("HeldItem").isToggled()) {
                            GlStateManager.translate(0.0f, (float)Aqua.setmgr.getSetting("HeldItemY").getCurrentNumber(), 0.0f);
                        }
                        GlStateManager.translate(0.0, 0.1, 0.0);
                        this.transformFirstPersonItem(f / 2.0f, f2);
                        this.func_178103_d();
                    }
                    if (Aqua.setmgr.getSetting("AnimationsMode").getCurrentMode().equalsIgnoreCase("Whack")) {
                        if (Aqua.moduleManager.getModuleByName("HeldItem").isToggled()) {
                            GlStateManager.translate(0.0f, (float)Aqua.setmgr.getSetting("HeldItemY").getCurrentNumber(), 0.0f);
                        }
                        this.transformFirstPersonItem(f / 3.0f, 0.0f);
                        final float swing2 = MathHelper.sin((float)(MathHelper.sqrt_float(f2) * 3.141592653589793));
                        GL11.glRotatef(-swing2 * 80.0f / 5.0f, swing2 / 3.0f, -0.0f, 9.0f);
                        GL11.glRotatef(-swing2 * 40.0f, 8.0f, swing2 / 9.0f, -0.1f);
                        this.func_178103_d();
                    }
                    if (Aqua.setmgr.getSetting("AnimationsMode").getCurrentMode().equalsIgnoreCase("Skidding")) {
                        if (Aqua.moduleManager.getModuleByName("HeldItem").isToggled()) {
                            GlStateManager.translate(0.0f, (float)Aqua.setmgr.getSetting("HeldItemY").getCurrentNumber(), 0.0f);
                        }
                        final float funny2 = MathHelper.sin(MathHelper.sqrt_float(f) * 3.1415927f);
                        GlStateManager.translate(0.56f - funny2 / 15.0f, -0.4f + funny2 / 15.0f, -0.71999997f);
                        GlStateManager.translate(0.0f, f * -0.6f, 0.0f);
                        GlStateManager.rotate(40.0f, 0.0f, 1.0f, 0.0f);
                        final float f9 = MathHelper.sin(f2 * f2 * 3.1415927f);
                        final float f10 = MathHelper.sin(MathHelper.sqrt_float(f2) * 3.1415927f);
                        GlStateManager.rotate(f9 * -30.0f, 0.0f, 1.0f, 0.0f);
                        GlStateManager.rotate(f10 * -20.0f, 0.0f, 0.0f, 1.0f);
                        GlStateManager.rotate(f10 * -85.0f, 1.0f, 0.0f, 0.0f);
                        final float size2 = (float)Aqua.setmgr.getSetting("HeldItemScale").getCurrentNumber();
                        GlStateManager.scale(size2, size2, size2);
                        this.func_178103_d();
                    }
                    if (Aqua.setmgr.getSetting("AnimationsMode").getCurrentMode().equalsIgnoreCase("Exhibition")) {
                        if (Aqua.moduleManager.getModuleByName("HeldItem").isToggled()) {
                            GlStateManager.translate(0.0f, (float)Aqua.setmgr.getSetting("HeldItemY").getCurrentNumber(), 0.0f);
                        }
                        this.transformFirstPersonItem(f / 3.0f, 0.0f);
                        GlStateManager.translate(0.0, 0.3, 0.0);
                        final float rot2 = MathHelper.sin(MathHelper.sqrt_float(f2) * 3.1415927f);
                        GlStateManager.rotate(-rot2 * 36.0f, 2.09f, 0.0f, 0.0f);
                        GlStateManager.rotate(-rot2 * 25.0f, 2.09f, 0.0f, 15.0f);
                        this.func_178103_d();
                    }
                    if (Aqua.setmgr.getSetting("AnimationsMode").getCurrentMode().equalsIgnoreCase("Butter")) {
                        if (Aqua.moduleManager.getModuleByName("HeldItem").isToggled()) {
                            GlStateManager.translate(0.0f, (float)Aqua.setmgr.getSetting("HeldItemY").getCurrentNumber(), 0.0f);
                        }
                        GlStateManager.translate(0.56f, -0.52f, -0.71999997f);
                        GlStateManager.translate(0.0f, f * -0.6f, 0.0f);
                        GlStateManager.rotate(45.0f, 0.0f, 1.0f, 0.0f);
                        final float f11 = MathHelper.sin(f2 * f2 * 3.1415927f);
                        final float f12 = MathHelper.sin(MathHelper.sqrt_float(f2) * 3.1415927f);
                        GlStateManager.rotate(f11 * -20.0f, 0.0f, 1.0f, 0.0f);
                        GlStateManager.rotate(f12 * -20.0f, 0.0f, 0.0f, 1.0f);
                        GlStateManager.rotate(f12 * -20.0f, 1.0f, 0.0f, 0.0f);
                        final float size3 = (float)Aqua.setmgr.getSetting("HeldItemScale").getCurrentNumber();
                        GlStateManager.scale(size3, size3, size3);
                        this.func_178103_d();
                    }
                    if (Aqua.setmgr.getSetting("AnimationsMode").getCurrentMode().equalsIgnoreCase("Aqua")) {
                        if (Aqua.moduleManager.getModuleByName("HeldItem").isToggled()) {
                            GlStateManager.translate(0.0f, (float)Aqua.setmgr.getSetting("HeldItemY").getCurrentNumber(), 0.0f);
                        }
                        this.transformFirstPersonItem(f / 3.0f, 0.0f);
                        final float animation2 = MathHelper.sin(MathHelper.sqrt_float(f) * 3.1415927f);
                        GlStateManager.translate(0.56f - animation2 / 15.0f, -0.4f + animation2 / 15.0f, -0.71999997f);
                        GlStateManager.translate(0.0f, f * -0.6f, 0.0f);
                        GlStateManager.rotate(40.0f, 0.0f, 1.0f, 0.0f);
                        final float f9 = MathHelper.sin(f2 * f2 * 3.1415927f);
                        final float f10 = MathHelper.sin(MathHelper.sqrt_float(f2) * 3.1415927f);
                        GlStateManager.rotate(f9 * -30.0f, 0.0f, 1.0f, 0.0f);
                        GlStateManager.rotate(f10 * -20.0f, 0.0f, 0.0f, 1.0f);
                        GlStateManager.rotate(f10 * -85.0f, 1.0f, 0.0f, 0.0f);
                        if (Aqua.moduleManager.getModuleByName("HeldItem").isToggled()) {
                            GlStateManager.scale(0.8, 0.8, 0.8);
                        }
                        else {
                            GlStateManager.scale(0.8, 0.8, 0.8);
                        }
                        this.func_178103_d();
                    }
                }
                else {
                    if (!this.mc.isSingleplayer() && this.mc.getCurrentServerData().serverIP.equalsIgnoreCase("hypixel.net") && Killaura.target != null) {
                        if (Aqua.setmgr.getSetting("AnimationsMode").getCurrentMode().equalsIgnoreCase("1.7")) {
                            if (Aqua.moduleManager.getModuleByName("HeldItem").isToggled()) {
                                GlStateManager.translate(0.0f, (float)Aqua.setmgr.getSetting("HeldItemY").getCurrentNumber(), 0.0f);
                            }
                            this.transformFirstPersonItem(f, f2 + f / 3.0f);
                            this.func_178103_d();
                            GlStateManager.translate(0.1, 0.0, 0.0);
                        }
                        if (Aqua.setmgr.getSetting("AnimationsMode").getCurrentMode().equalsIgnoreCase("Own")) {
                            GlStateManager.translate(0.0f, (float)Aqua.setmgr.getSetting("HeldItemY").getCurrentNumber(), 0.0f);
                            this.transformFirstPersonItem(f / 3.0f, 0.0f);
                            GlStateManager.translate(0.0f, (float)Aqua.setmgr.getSetting("HeldItemY").getCurrentNumber(), 0.0f);
                            final float rot2 = MathHelper.sin(MathHelper.sqrt_float(f2) * 3.1415927f);
                            GlStateManager.rotate(-rot2 * 36.0f, 2.09f, 0.0f, 0.0f);
                            GlStateManager.rotate(-rot2 * 25.0f, 2.09f, 0.0f, 15.0f);
                            GlStateManager.rotate(-rot2 * 16.0f, 12.09f, 3.0f, 0.0f);
                            GlStateManager.rotate(5.0f, 0.0f, -2.0f, 12.0f);
                            this.func_178103_d();
                        }
                        if (Aqua.setmgr.getSetting("AnimationsMode").getCurrentMode().equalsIgnoreCase("Jello")) {
                            final float funny2 = MathHelper.sin(MathHelper.sqrt_float(f2) * 3.1415927f);
                            if (Aqua.moduleManager.getModuleByName("HeldItem").isToggled()) {
                                GlStateManager.translate(0.0f, (float)Aqua.setmgr.getSetting("HeldItemY").getCurrentNumber(), 0.0f);
                            }
                            this.transformFirstPersonItem(0.0f, 0.0f);
                            GlStateManager.scale(0.8f, 0.8f, 0.8f);
                            GlStateManager.translate(0.0f, 0.1f, 0.0f);
                            this.func_178103_d();
                            GlStateManager.rotate(funny2 * 35.0f / 2.0f, 0.0f, 1.0f, 1.5f);
                            GlStateManager.rotate(-funny2 * 190.0f / 4.0f, 1.0f, 0.5f, 0.0f);
                        }
                        if (Aqua.setmgr.getSetting("AnimationsMode").getCurrentMode().equalsIgnoreCase("Snip")) {
                            this.transformFirstPersonItem(f / 3.0f, 0.0f);
                            if (Aqua.moduleManager.getModuleByName("HeldItem").isToggled()) {
                                GlStateManager.translate(0.0f, (float)Aqua.setmgr.getSetting("HeldItemY").getCurrentNumber(), 0.0f);
                            }
                            else {
                                GlStateManager.translate(0.0, 0.3, 0.0);
                            }
                            final float rot2 = MathHelper.sin(MathHelper.sqrt_float(f2) * 3.1415927f);
                            GlStateManager.rotate(-rot2, 0.0f, 0.0f, rot2);
                            GlStateManager.rotate(-rot2 * 2.0f, rot2 * 7.0f, 0.0f, 0.0f);
                            GlStateManager.rotate(-rot2 * 2.0f, 16.0f, 0.0f, 15.0f);
                            this.doBlockTransformations();
                        }
                        if (Aqua.setmgr.getSetting("AnimationsMode").getCurrentMode().equalsIgnoreCase("High1.7")) {
                            if (Aqua.moduleManager.getModuleByName("HeldItem").isToggled()) {
                                GlStateManager.translate(0.0f, (float)Aqua.setmgr.getSetting("HeldItemY").getCurrentNumber(), 0.0f);
                            }
                            GlStateManager.translate(0.0, 0.1, 0.0);
                            this.transformFirstPersonItem(f / 2.0f, f2);
                            this.func_178103_d();
                        }
                        if (Aqua.setmgr.getSetting("AnimationsMode").getCurrentMode().equalsIgnoreCase("Whack")) {
                            if (Aqua.moduleManager.getModuleByName("HeldItem").isToggled()) {
                                GlStateManager.translate(0.0f, (float)Aqua.setmgr.getSetting("HeldItemY").getCurrentNumber(), 0.0f);
                            }
                            this.transformFirstPersonItem(f / 3.0f, 0.0f);
                            final float swing2 = MathHelper.sin((float)(MathHelper.sqrt_float(f2) * 3.141592653589793));
                            GL11.glRotatef(-swing2 * 80.0f / 5.0f, swing2 / 3.0f, -0.0f, 9.0f);
                            GL11.glRotatef(-swing2 * 40.0f, 8.0f, swing2 / 9.0f, -0.1f);
                            this.func_178103_d();
                        }
                        if (Aqua.setmgr.getSetting("AnimationsMode").getCurrentMode().equalsIgnoreCase("Skidding")) {
                            if (Aqua.moduleManager.getModuleByName("HeldItem").isToggled()) {
                                GlStateManager.translate(0.0f, (float)Aqua.setmgr.getSetting("HeldItemY").getCurrentNumber(), 0.0f);
                            }
                            final float funny2 = MathHelper.sin(MathHelper.sqrt_float(f) * 3.1415927f);
                            GlStateManager.translate(0.0f, (float)Aqua.setmgr.getSetting("HeldItemY").getCurrentNumber(), 0.0f);
                            GlStateManager.translate(0.56f - funny2 / 15.0f, -0.4f + funny2 / 15.0f, -0.71999997f);
                            GlStateManager.translate(0.0f, f * -0.6f, 0.0f);
                            GlStateManager.rotate(40.0f, 0.0f, 1.0f, 0.0f);
                            final float f9 = MathHelper.sin(f2 * f2 * 3.1415927f);
                            final float f10 = MathHelper.sin(MathHelper.sqrt_float(f2) * 3.1415927f);
                            GlStateManager.rotate(f9 * -30.0f, 0.0f, 1.0f, 0.0f);
                            GlStateManager.rotate(f10 * -20.0f, 0.0f, 0.0f, 1.0f);
                            GlStateManager.rotate(f10 * -85.0f, 1.0f, 0.0f, 0.0f);
                            final float size2 = (float)Aqua.setmgr.getSetting("HeldItemScale").getCurrentNumber();
                            GlStateManager.scale(size2, size2, size2);
                            this.func_178103_d();
                        }
                        if (Aqua.setmgr.getSetting("AnimationsMode").getCurrentMode().equalsIgnoreCase("Exhibition")) {
                            if (Aqua.moduleManager.getModuleByName("HeldItem").isToggled()) {
                                GlStateManager.translate(0.0f, (float)Aqua.setmgr.getSetting("HeldItemY").getCurrentNumber(), 0.0f);
                            }
                            this.transformFirstPersonItem(f / 3.0f, 0.0f);
                            GlStateManager.translate(0.0, 0.3, 0.0);
                            final float rot2 = MathHelper.sin(MathHelper.sqrt_float(f2) * 3.1415927f);
                            GlStateManager.rotate(-rot2 * 36.0f, 2.09f, 0.0f, 0.0f);
                            GlStateManager.rotate(-rot2 * 25.0f, 2.09f, 0.0f, 15.0f);
                            this.func_178103_d();
                        }
                        if (Aqua.setmgr.getSetting("AnimationsMode").getCurrentMode().equalsIgnoreCase("Butter")) {
                            if (Aqua.moduleManager.getModuleByName("HeldItem").isToggled()) {
                                GlStateManager.translate(0.0f, (float)Aqua.setmgr.getSetting("HeldItemY").getCurrentNumber(), 0.0f);
                            }
                            GlStateManager.translate(0.56f, -0.52f, -0.71999997f);
                            GlStateManager.translate(0.0f, f * -0.6f, 0.0f);
                            GlStateManager.rotate(45.0f, 0.0f, 1.0f, 0.0f);
                            final float f11 = MathHelper.sin(f2 * f2 * 3.1415927f);
                            final float f12 = MathHelper.sin(MathHelper.sqrt_float(f2) * 3.1415927f);
                            GlStateManager.rotate(f11 * -20.0f, 0.0f, 1.0f, 0.0f);
                            GlStateManager.rotate(f12 * -20.0f, 0.0f, 0.0f, 1.0f);
                            GlStateManager.rotate(f12 * -20.0f, 1.0f, 0.0f, 0.0f);
                            final float size3 = (float)Aqua.setmgr.getSetting("HeldItemScale").getCurrentNumber();
                            GlStateManager.scale(size3, size3, size3);
                            this.func_178103_d();
                        }
                        if (Aqua.setmgr.getSetting("AnimationsMode").getCurrentMode().equalsIgnoreCase("Aqua")) {
                            if (Aqua.moduleManager.getModuleByName("HeldItem").isToggled()) {
                                GlStateManager.translate(0.0f, (float)Aqua.setmgr.getSetting("HeldItemY").getCurrentNumber(), 0.0f);
                            }
                            this.transformFirstPersonItem(f / 3.0f, 0.0f);
                            final float animation2 = MathHelper.sin(MathHelper.sqrt_float(f) * 3.1415927f);
                            GlStateManager.translate(0.56f - animation2 / 15.0f, -0.4f + animation2 / 15.0f, -0.71999997f);
                            GlStateManager.translate(0.0f, f * -0.6f, 0.0f);
                            GlStateManager.rotate(40.0f, 0.0f, 1.0f, 0.0f);
                            final float f9 = MathHelper.sin(f2 * f2 * 3.1415927f);
                            final float f10 = MathHelper.sin(MathHelper.sqrt_float(f2) * 3.1415927f);
                            GlStateManager.rotate(f9 * -30.0f, 0.0f, 1.0f, 0.0f);
                            GlStateManager.rotate(f10 * -20.0f, 0.0f, 0.0f, 1.0f);
                            GlStateManager.rotate(f10 * -85.0f, 1.0f, 0.0f, 0.0f);
                            if (Aqua.moduleManager.getModuleByName("HeldItem").isToggled()) {
                                GlStateManager.scale(0.8, 0.8, 0.8);
                            }
                            else {
                                GlStateManager.scale(0.8, 0.8, 0.8);
                            }
                            this.func_178103_d();
                        }
                        else {
                            this.doItemUsedTransformations(f2);
                            this.transformFirstPersonItem(f, f2);
                        }
                    }
                    this.doItemUsedTransformations(f2);
                    this.transformFirstPersonItem(f, f2);
                }
                this.renderItem(abstractclientplayer, this.itemToRender, ItemCameraTransforms.TransformType.FIRST_PERSON);
            }
            else if (!abstractclientplayer.isInvisible()) {
                this.renderPlayerArm(abstractclientplayer, f, f2);
            }
            GlStateManager.popMatrix();
            GlStateManager.disableRescaleNormal();
            RenderHelper.disableStandardItemLighting();
        }
    }
    
    public void renderOverlays(final float partialTicks) {
        GlStateManager.disableAlpha();
        if (this.mc.thePlayer.isEntityInsideOpaqueBlock()) {
            IBlockState iblockstate = this.mc.theWorld.getBlockState(new BlockPos(this.mc.thePlayer));
            BlockPos blockpos = new BlockPos(this.mc.thePlayer);
            final EntityPlayer entityplayer = this.mc.thePlayer;
            for (int i = 0; i < 8; ++i) {
                final double d0 = entityplayer.posX + ((i >> 0) % 2 - 0.5f) * entityplayer.width * 0.8f;
                final double d2 = entityplayer.posY + ((i >> 1) % 2 - 0.5f) * 0.1f;
                final double d3 = entityplayer.posZ + ((i >> 2) % 2 - 0.5f) * entityplayer.width * 0.8f;
                final BlockPos blockpos2 = new BlockPos(d0, d2 + entityplayer.getEyeHeight(), d3);
                final IBlockState iblockstate2 = this.mc.theWorld.getBlockState(blockpos2);
                if (iblockstate2.getBlock().isVisuallyOpaque()) {
                    iblockstate = iblockstate2;
                    blockpos = blockpos2;
                }
            }
            if (iblockstate.getBlock().getRenderType() != -1) {
                final Object object = Reflector.getFieldValue(Reflector.RenderBlockOverlayEvent_OverlayType_BLOCK);
                if (!Reflector.callBoolean(Reflector.ForgeEventFactory_renderBlockOverlay, this.mc.thePlayer, partialTicks, object, iblockstate, blockpos)) {
                    this.renderBlockInHand(partialTicks, this.mc.getBlockRendererDispatcher().getBlockModelShapes().getTexture(iblockstate));
                }
            }
        }
        if (!this.mc.thePlayer.isSpectator()) {
            if (this.mc.thePlayer.isInsideOfMaterial(Material.water) && !Reflector.callBoolean(Reflector.ForgeEventFactory_renderWaterOverlay, this.mc.thePlayer, partialTicks)) {
                this.renderWaterOverlayTexture(partialTicks);
            }
            if (this.mc.thePlayer.isBurning() && !Reflector.callBoolean(Reflector.ForgeEventFactory_renderFireOverlay, this.mc.thePlayer, partialTicks)) {
                this.renderFireInFirstPerson(partialTicks);
            }
        }
        GlStateManager.enableAlpha();
    }
    
    private void renderBlockInHand(final float partialTicks, final TextureAtlasSprite atlas) {
        this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
        final Tessellator tessellator = Tessellator.getInstance();
        final WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        final float f = 0.1f;
        GlStateManager.color(0.1f, 0.1f, 0.1f, 0.5f);
        GlStateManager.pushMatrix();
        final float f2 = -1.0f;
        final float f3 = 1.0f;
        final float f4 = -1.0f;
        final float f5 = 1.0f;
        final float f6 = -0.5f;
        final float f7 = atlas.getMinU();
        final float f8 = atlas.getMaxU();
        final float f9 = atlas.getMinV();
        final float f10 = atlas.getMaxV();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldrenderer.pos(-1.0, -1.0, -0.5).tex(f8, f10).endVertex();
        worldrenderer.pos(1.0, -1.0, -0.5).tex(f7, f10).endVertex();
        worldrenderer.pos(1.0, 1.0, -0.5).tex(f7, f9).endVertex();
        worldrenderer.pos(-1.0, 1.0, -0.5).tex(f8, f9).endVertex();
        tessellator.draw();
        GlStateManager.popMatrix();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    private void renderWaterOverlayTexture(final float partialTicks) {
        if (!Config.isShaders() || Shaders.isUnderwaterOverlay()) {
            this.mc.getTextureManager().bindTexture(ItemRenderer.RES_UNDERWATER_OVERLAY);
            final Tessellator tessellator = Tessellator.getInstance();
            final WorldRenderer worldrenderer = tessellator.getWorldRenderer();
            final float f = this.mc.thePlayer.getBrightness(partialTicks);
            GlStateManager.color(f, f, f, 0.5f);
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            GlStateManager.pushMatrix();
            final float f2 = 4.0f;
            final float f3 = -1.0f;
            final float f4 = 1.0f;
            final float f5 = -1.0f;
            final float f6 = 1.0f;
            final float f7 = -0.5f;
            final float f8 = -this.mc.thePlayer.rotationYaw / 64.0f;
            final float f9 = this.mc.thePlayer.rotationPitch / 64.0f;
            worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
            worldrenderer.pos(-1.0, -1.0, -0.5).tex(4.0f + f8, 4.0f + f9).endVertex();
            worldrenderer.pos(1.0, -1.0, -0.5).tex(0.0f + f8, 4.0f + f9).endVertex();
            worldrenderer.pos(1.0, 1.0, -0.5).tex(0.0f + f8, 0.0f + f9).endVertex();
            worldrenderer.pos(-1.0, 1.0, -0.5).tex(4.0f + f8, 0.0f + f9).endVertex();
            tessellator.draw();
            GlStateManager.popMatrix();
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.disableBlend();
        }
    }
    
    private void renderFireInFirstPerson(final float partialTicks) {
        final Tessellator tessellator = Tessellator.getInstance();
        final WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 0.9f);
        GlStateManager.depthFunc(519);
        GlStateManager.depthMask(false);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        final float f = 1.0f;
        for (int i = 0; i < 2; ++i) {
            GlStateManager.pushMatrix();
            final TextureAtlasSprite textureatlassprite = this.mc.getTextureMapBlocks().getAtlasSprite("minecraft:blocks/fire_layer_1");
            this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
            final float f2 = textureatlassprite.getMinU();
            final float f3 = textureatlassprite.getMaxU();
            final float f4 = textureatlassprite.getMinV();
            final float f5 = textureatlassprite.getMaxV();
            final float f6 = -0.5f;
            final float f7 = 0.5f;
            final float f8 = -0.5f;
            final float f9 = 0.5f;
            final float f10 = -0.5f;
            GlStateManager.translate(-(i * 2 - 1) * 0.24f, -0.3f, 0.0f);
            GlStateManager.rotate((i * 2 - 1) * 10.0f, 0.0f, 1.0f, 0.0f);
            worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
            worldrenderer.setSprite(textureatlassprite);
            worldrenderer.pos(-0.5, -0.5, -0.5).tex(f3, f5).endVertex();
            worldrenderer.pos(0.5, -0.5, -0.5).tex(f2, f5).endVertex();
            worldrenderer.pos(0.5, 0.5, -0.5).tex(f2, f4).endVertex();
            worldrenderer.pos(-0.5, 0.5, -0.5).tex(f3, f4).endVertex();
            tessellator.draw();
            GlStateManager.popMatrix();
        }
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.disableBlend();
        GlStateManager.depthMask(true);
        GlStateManager.depthFunc(515);
    }
    
    public void updateEquippedItem() {
        this.prevEquippedProgress = this.equippedProgress;
        final EntityPlayer entityplayer = this.mc.thePlayer;
        final ItemStack itemstack = entityplayer.inventory.getCurrentItem();
        boolean flag = false;
        if (this.itemToRender != null && itemstack != null) {
            if (!this.itemToRender.getIsItemStackEqual(itemstack)) {
                if (Reflector.ForgeItem_shouldCauseReequipAnimation.exists()) {
                    final boolean flag2 = Reflector.callBoolean(this.itemToRender.getItem(), Reflector.ForgeItem_shouldCauseReequipAnimation, this.itemToRender, itemstack, this.equippedItemSlot != entityplayer.inventory.currentItem);
                    if (!flag2) {
                        this.itemToRender = itemstack;
                        this.equippedItemSlot = entityplayer.inventory.currentItem;
                        return;
                    }
                }
                flag = true;
            }
        }
        else {
            flag = (this.itemToRender != null || itemstack != null);
        }
        final float f2 = 0.4f;
        final float f3 = flag ? 0.0f : 1.0f;
        final float f4 = MathHelper.clamp_float(f3 - this.equippedProgress, -0.4f, 0.4f);
        this.equippedProgress += f4;
        if (this.equippedProgress < 0.1f) {
            this.itemToRender = itemstack;
            this.equippedItemSlot = entityplayer.inventory.currentItem;
            if (Config.isShaders()) {
                Shaders.setItemToRenderMain(itemstack);
            }
        }
    }
    
    public void resetEquippedProgress() {
        this.equippedProgress = 0.0f;
    }
    
    public void resetEquippedProgress2() {
        this.equippedProgress = 0.0f;
    }
    
    static {
        RES_MAP_BACKGROUND = new ResourceLocation("textures/map/map_background.png");
        RES_UNDERWATER_OVERLAY = new ResourceLocation("textures/misc/underwater.png");
    }
}
