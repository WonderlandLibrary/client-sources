/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer;

import com.google.common.base.MoreObjects;
import java.util.Objects;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.storage.MapData;
import optifine.Config;
import optifine.DynamicLights;
import optifine.Reflector;
import optifine.ReflectorForge;
import ru.govno.client.event.events.EventTransformSideFirstPerson;
import ru.govno.client.module.modules.NoRender;
import ru.govno.client.module.modules.ViewModel;
import shadersmod.client.Shaders;

public class ItemRenderer {
    private static final ResourceLocation RES_MAP_BACKGROUND = new ResourceLocation("textures/map/map_background.png");
    private static final ResourceLocation RES_UNDERWATER_OVERLAY = new ResourceLocation("textures/misc/underwater.png");
    private final Minecraft mc;
    private ItemStack itemStackMainHand = ItemStack.field_190927_a;
    private ItemStack itemStackOffHand = ItemStack.field_190927_a;
    public static float equippedProgressMainHand;
    private float prevEquippedProgressMainHand;
    private float equippedProgressOffHand;
    private float prevEquippedProgressOffHand;
    private final RenderManager renderManager;
    private final RenderItem itemRenderer;

    public ItemRenderer(Minecraft mcIn) {
        this.mc = mcIn;
        this.renderManager = mcIn.getRenderManager();
        this.itemRenderer = mcIn.getRenderItem();
    }

    public void renderItem(EntityLivingBase entityIn, ItemStack heldStack, ItemCameraTransforms.TransformType transform) {
        this.renderItemSide(entityIn, heldStack, transform, false);
    }

    public void renderItemSide(EntityLivingBase entitylivingbaseIn, ItemStack heldStack, ItemCameraTransforms.TransformType transform, boolean leftHanded) {
        if (!heldStack.func_190926_b()) {
            boolean flag;
            Item item = heldStack.getItem();
            Block block = Block.getBlockFromItem(item);
            GlStateManager.pushMatrix();
            if (entitylivingbaseIn.isChild() && entitylivingbaseIn.isSneaking() && (this.mc.gameSettings.thirdPersonView != 0 || !(entitylivingbaseIn instanceof EntityPlayerSP))) {
                GlStateManager.translate(0.0, 0.0, 0.2);
            }
            boolean bl = flag = this.itemRenderer.shouldRenderItemIn3D(heldStack) && block.getBlockLayer() == BlockRenderLayer.TRANSLUCENT;
            if (!(!flag || Config.isShaders() && Shaders.renderItemKeepDepthMask)) {
                GlStateManager.depthMask(false);
            }
            GlStateManager.enableDepth();
            ViewModel.setupTransperentModel(entitylivingbaseIn, true);
            this.itemRenderer.renderItem(heldStack, entitylivingbaseIn, transform, leftHanded);
            ViewModel.setupTransperentModel(entitylivingbaseIn, false);
            if (flag) {
                GlStateManager.depthMask(true);
            }
            GlStateManager.popMatrix();
        }
    }

    private void rotateArroundXAndY(float angle, float angleY) {
        GlStateManager.pushMatrix();
        GlStateManager.rotate(angle, 1.0f, 0.0f, 0.0f);
        GlStateManager.rotate(angleY, 0.0f, 1.0f, 0.0f);
        RenderHelper.enableStandardItemLighting();
        GlStateManager.popMatrix();
    }

    private void setLightmap() {
        EntityPlayerSP abstractclientplayer = Minecraft.player;
        int i = this.mc.world.getCombinedLight(new BlockPos(abstractclientplayer.posX, abstractclientplayer.posY + (double)abstractclientplayer.getEyeHeight(), abstractclientplayer.posZ), 0);
        if (Config.isDynamicLights()) {
            i = DynamicLights.getCombinedLight(this.mc.getRenderViewEntity(), i);
        }
        float f = i & 0xFFFF;
        float f1 = i >> 16;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, f, f1);
    }

    private void rotateArm(float p_187458_1_) {
        EntityPlayerSP entityplayersp = Minecraft.player;
        float f = entityplayersp.prevRenderArmPitch + (entityplayersp.renderArmPitch - entityplayersp.prevRenderArmPitch) * p_187458_1_;
        float f1 = entityplayersp.prevRenderArmYaw + (entityplayersp.renderArmYaw - entityplayersp.prevRenderArmYaw) * p_187458_1_;
        GlStateManager.rotate((entityplayersp.rotationPitch - f) * 0.1f, 1.0f, 0.0f, 0.0f);
        GlStateManager.rotate((entityplayersp.rotationYaw - f1) * 0.1f, 0.0f, 1.0f, 0.0f);
    }

    private float getMapAngleFromPitch(float pitch) {
        float f = 1.0f - pitch / 45.0f + 0.1f;
        f = MathHelper.clamp(f, 0.0f, 1.0f);
        f = -MathHelper.cos(f * (float)Math.PI) * 0.5f + 0.5f;
        return f;
    }

    private void renderArms() {
        if (!Minecraft.player.isInvisible()) {
            GlStateManager.disableCull();
            GlStateManager.pushMatrix();
            GlStateManager.rotate(90.0f, 0.0f, 1.0f, 0.0f);
            this.renderArm(EnumHandSide.RIGHT);
            this.renderArm(EnumHandSide.LEFT);
            GlStateManager.popMatrix();
            GlStateManager.enableCull();
        }
    }

    private void renderArm(EnumHandSide p_187455_1_) {
        this.mc.getTextureManager().bindTexture(Minecraft.player.getLocationSkin());
        Render render = this.renderManager.getEntityRenderObject(Minecraft.player);
        RenderPlayer renderplayer = (RenderPlayer)render;
        GlStateManager.pushMatrix();
        float f = p_187455_1_ == EnumHandSide.RIGHT ? 1.0f : -1.0f;
        GlStateManager.rotate(92.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(45.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.rotate(f * -41.0f, 0.0f, 0.0f, 1.0f);
        GlStateManager.translate(f * 0.3f, -1.1f, 0.45f);
        if (p_187455_1_ == EnumHandSide.RIGHT) {
            renderplayer.renderRightArm(Minecraft.player);
        } else {
            renderplayer.renderLeftArm(Minecraft.player);
        }
        GlStateManager.popMatrix();
    }

    private void renderMapFirstPersonSide(float p_187465_1_, EnumHandSide p_187465_2_, float p_187465_3_, ItemStack p_187465_4_) {
        float f = p_187465_2_ == EnumHandSide.RIGHT ? 1.0f : -1.0f;
        GlStateManager.translate(f * 0.125f, -0.125f, 0.0f);
        if (!Minecraft.player.isInvisible()) {
            GlStateManager.pushMatrix();
            GlStateManager.rotate(f * 10.0f, 0.0f, 0.0f, 1.0f);
            this.renderArmFirstPerson(p_187465_1_, p_187465_3_, p_187465_2_);
            GlStateManager.popMatrix();
        }
        GlStateManager.pushMatrix();
        GlStateManager.translate(f * 0.51f, -0.08f + p_187465_1_ * -1.2f, -0.75f);
        float f1 = MathHelper.sqrt(p_187465_3_);
        float f2 = MathHelper.sin(f1 * (float)Math.PI);
        float f3 = -0.5f * f2;
        float f4 = 0.4f * MathHelper.sin(f1 * ((float)Math.PI * 2));
        float f5 = -0.3f * MathHelper.sin(p_187465_3_ * (float)Math.PI);
        GlStateManager.translate(f * f3, f4 - 0.3f * f2, f5);
        GlStateManager.rotate(f2 * -45.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.rotate(f * f2 * -30.0f, 0.0f, 1.0f, 0.0f);
        this.renderMapFirstPerson(p_187465_4_);
        GlStateManager.popMatrix();
    }

    private void renderMapFirstPerson(float p_187463_1_, float p_187463_2_, float p_187463_3_) {
        float f = MathHelper.sqrt(p_187463_3_);
        float f1 = -0.2f * MathHelper.sin(p_187463_3_ * (float)Math.PI);
        float f2 = -0.4f * MathHelper.sin(f * (float)Math.PI);
        GlStateManager.translate(0.0f, -f1 / 2.0f, f2);
        float f3 = this.getMapAngleFromPitch(p_187463_1_);
        GlStateManager.translate(0.0f, 0.04f + p_187463_2_ * -1.2f + f3 * -0.5f, -0.72f);
        GlStateManager.rotate(f3 * -85.0f, 1.0f, 0.0f, 0.0f);
        this.renderArms();
        float f4 = MathHelper.sin(f * (float)Math.PI);
        GlStateManager.rotate(f4 * 20.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.scale(2.0f, 2.0f, 2.0f);
        this.renderMapFirstPerson(this.itemStackMainHand);
    }

    private void renderMapFirstPerson(ItemStack stack) {
        GlStateManager.rotate(180.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(180.0f, 0.0f, 0.0f, 1.0f);
        GlStateManager.scale(0.38f, 0.38f, 0.38f);
        GlStateManager.disableLighting();
        this.mc.getTextureManager().bindTexture(RES_MAP_BACKGROUND);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        GlStateManager.translate(-0.5f, -0.5f, 0.0f);
        GlStateManager.scale(0.0078125f, 0.0078125f, 0.0078125f);
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos(-7.0, 135.0, 0.0).tex(0.0, 1.0).endVertex();
        bufferbuilder.pos(135.0, 135.0, 0.0).tex(1.0, 1.0).endVertex();
        bufferbuilder.pos(135.0, -7.0, 0.0).tex(1.0, 0.0).endVertex();
        bufferbuilder.pos(-7.0, -7.0, 0.0).tex(0.0, 0.0).endVertex();
        tessellator.draw();
        MapData mapdata = ReflectorForge.getMapData(Items.FILLED_MAP, stack, this.mc.world);
        if (mapdata != null) {
            this.mc.entityRenderer.getMapItemRenderer().renderMap(mapdata, false);
        }
        GlStateManager.enableLighting();
    }

    private void renderArmFirstPerson(float p_187456_1_, float p_187456_2_, EnumHandSide p_187456_3_) {
        boolean flag = p_187456_3_ != EnumHandSide.LEFT;
        float f = flag ? 1.0f : -1.0f;
        float f1 = MathHelper.sqrt(p_187456_2_);
        float f2 = -0.3f * MathHelper.sin(f1 * (float)Math.PI);
        float f3 = 0.4f * MathHelper.sin(f1 * ((float)Math.PI * 2));
        float f4 = -0.4f * MathHelper.sin(p_187456_2_ * (float)Math.PI);
        GlStateManager.translate(f * (f2 + 0.64000005f), f3 + -0.6f + p_187456_1_ * -0.6f, f4 + -0.71999997f);
        GlStateManager.rotate(f * 45.0f, 0.0f, 1.0f, 0.0f);
        float f5 = MathHelper.sin(p_187456_2_ * p_187456_2_ * (float)Math.PI);
        float f6 = MathHelper.sin(f1 * (float)Math.PI);
        GlStateManager.rotate(f * f6 * 70.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(f * f5 * -20.0f, 0.0f, 0.0f, 1.0f);
        EntityPlayerSP abstractclientplayer = Minecraft.player;
        this.mc.getTextureManager().bindTexture(abstractclientplayer.getLocationSkin());
        GlStateManager.translate(f * -1.0f, 3.6f, 3.5f);
        GlStateManager.rotate(f * 120.0f, 0.0f, 0.0f, 1.0f);
        GlStateManager.rotate(200.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.rotate(f * -135.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.translate(f * 5.6f, 0.0f, 0.0f);
        RenderPlayer renderplayer = (RenderPlayer)this.renderManager.getEntityRenderObject(abstractclientplayer);
        GlStateManager.disableCull();
        if (flag) {
            renderplayer.renderRightArm(abstractclientplayer);
        } else {
            renderplayer.renderLeftArm(abstractclientplayer);
        }
        GlStateManager.enableCull();
    }

    private void transformEatFirstPerson(float p_187454_1_, EnumHandSide p_187454_2_, ItemStack p_187454_3_) {
        float f = (float)Minecraft.player.getItemInUseCount() - p_187454_1_ + 1.0f;
        float f1 = f / (float)p_187454_3_.getMaxItemUseDuration();
        if (f1 < 0.8f) {
            float f2 = MathHelper.abs(MathHelper.cos(f / 4.0f * (float)Math.PI) * 0.1f);
            GlStateManager.translate(0.0f, f2, 0.0f);
        }
        float f3 = 1.0f - (float)Math.pow(f1, 27.0);
        int i = p_187454_2_ == EnumHandSide.RIGHT ? 1 : -1;
        GlStateManager.translate(f3 * 0.6f * (float)i, f3 * -0.5f, f3 * 0.0f);
        GlStateManager.rotate((float)i * f3 * 90.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(f3 * 10.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.rotate((float)i * f3 * 30.0f, 0.0f, 0.0f, 1.0f);
    }

    public void transformFirstPerson(EnumHandSide p_187453_1_, float p_187453_2_) {
        EnumHand eHand;
        EventTransformSideFirstPerson event = new EventTransformSideFirstPerson(p_187453_1_);
        event.call();
        float f = MathHelper.sin(p_187453_2_ * p_187453_2_ * (float)Math.PI);
        float f1 = MathHelper.sin(MathHelper.sqrt(p_187453_2_) * (float)Math.PI);
        int i = p_187453_1_ == EnumHandSide.RIGHT ? 1 : -1;
        EnumHand enumHand = eHand = Minecraft.player.getPrimaryHand() == EnumHandSide.RIGHT ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND;
        if (!ViewModel.animate(false, f, f1, i)) {
            GlStateManager.rotate((float)i * (45.0f + f * -20.0f), 0.0f, 1.0f, 0.0f);
            GlStateManager.rotate((float)i * f1 * -20.0f, 0.0f, 0.0f, 1.0f);
            GlStateManager.rotate(f1 * -80.0f, 1.0f, 0.0f, 0.0f);
            GlStateManager.rotate((float)i * -45.0f, 0.0f, 1.0f, 0.0f);
        }
    }

    private void transformSideFirstPerson(EnumHandSide p_187459_1_, float p_187459_2_) {
        int i = p_187459_1_ == EnumHandSide.RIGHT ? 1 : -1;
        int i2 = p_187459_1_ == EnumHandSide.LEFT ? 1 : -1;
        GlStateManager.translate((float)i * 0.56f, -0.52f + p_187459_2_ * -0.6f * (float)(!ViewModel.animate(true, 0.0f, 0.0f, 0) ? 1 : 0), -0.72f);
    }

    public void renderItemInFirstPerson(float partialTicks) {
        ItemStack itemstack;
        EntityPlayerSP abstractclientplayer = Minecraft.player;
        float f = abstractclientplayer.getSwingProgress(partialTicks);
        EnumHand enumhand = MoreObjects.firstNonNull(abstractclientplayer.swingingHand, EnumHand.MAIN_HAND);
        float f1 = abstractclientplayer.prevRotationPitch + (abstractclientplayer.rotationPitch - abstractclientplayer.prevRotationPitch) * partialTicks;
        float f2 = abstractclientplayer.prevRotationYaw + (abstractclientplayer.rotationYaw - abstractclientplayer.prevRotationYaw) * partialTicks;
        boolean flag = true;
        boolean flag1 = true;
        if (((EntityLivingBase)abstractclientplayer).isHandActive() && !(itemstack = abstractclientplayer.getActiveItemStack()).func_190926_b() && itemstack.getItem() == Items.BOW) {
            EnumHand enumhand1 = ((EntityLivingBase)abstractclientplayer).getActiveHand();
            flag = enumhand1 == EnumHand.MAIN_HAND;
            flag1 = !flag;
        }
        this.rotateArroundXAndY(f1, f2);
        this.setLightmap();
        if (!NoRender.get.actived || !NoRender.get.HandShake.bValue) {
            this.rotateArm(partialTicks);
        }
        GlStateManager.enableRescaleNormal();
        if (flag) {
            float f3 = enumhand == EnumHand.MAIN_HAND ? f : 0.0f;
            float f5 = 1.0f - (this.prevEquippedProgressMainHand + (equippedProgressMainHand - this.prevEquippedProgressMainHand) * partialTicks);
            if (!Reflector.ForgeHooksClient_renderSpecificFirstPersonHand.exists() || !Reflector.callBoolean(Reflector.ForgeHooksClient_renderSpecificFirstPersonHand, new Object[]{EnumHand.MAIN_HAND, Float.valueOf(partialTicks), Float.valueOf(f1), Float.valueOf(f3), Float.valueOf(f5), this.itemStackMainHand})) {
                this.renderItemInFirstPerson(abstractclientplayer, partialTicks, f1, EnumHand.MAIN_HAND, f3, this.itemStackMainHand, f5);
            }
        }
        if (flag1) {
            float f4 = enumhand == EnumHand.OFF_HAND ? f : 0.0f;
            float f6 = 1.0f - (this.prevEquippedProgressOffHand + (this.equippedProgressOffHand - this.prevEquippedProgressOffHand) * partialTicks);
            if (!Reflector.ForgeHooksClient_renderSpecificFirstPersonHand.exists() || !Reflector.callBoolean(Reflector.ForgeHooksClient_renderSpecificFirstPersonHand, new Object[]{EnumHand.OFF_HAND, Float.valueOf(partialTicks), Float.valueOf(f1), Float.valueOf(f4), Float.valueOf(f6), this.itemStackOffHand})) {
                this.renderItemInFirstPerson(abstractclientplayer, partialTicks, f1, EnumHand.OFF_HAND, f4, this.itemStackOffHand, f6);
            }
        }
        boolean nullate = false;
        if (ViewModel.readRenderHandStart(abstractclientplayer, EnumHand.MAIN_HAND)) {
            GlStateManager.pushMatrix();
            float f3 = enumhand == EnumHand.MAIN_HAND ? f : 0.0f;
            float f5 = 1.0f - (this.prevEquippedProgressMainHand + (equippedProgressMainHand - this.prevEquippedProgressMainHand) * partialTicks);
            if (!Reflector.ForgeHooksClient_renderSpecificFirstPersonHand.exists() || !Reflector.callBoolean(Reflector.ForgeHooksClient_renderSpecificFirstPersonHand, new Object[]{EnumHand.MAIN_HAND, Float.valueOf(partialTicks), Float.valueOf(f1), Float.valueOf(f3), Float.valueOf(f5), this.itemStackMainHand})) {
                this.renderItemInFirstPerson(abstractclientplayer, partialTicks, f1, EnumHand.MAIN_HAND, f3, this.itemStackMainHand, f5);
            }
            GlStateManager.popMatrix();
            ViewModel.readRenderHandStop(EnumHand.MAIN_HAND);
            nullate = true;
        }
        if (ViewModel.readRenderHandStart(abstractclientplayer, EnumHand.OFF_HAND)) {
            GlStateManager.pushMatrix();
            float f4 = enumhand == EnumHand.OFF_HAND ? f : 0.0f;
            float f6 = 1.0f - (this.prevEquippedProgressOffHand + (this.equippedProgressOffHand - this.prevEquippedProgressOffHand) * partialTicks);
            if (!Reflector.ForgeHooksClient_renderSpecificFirstPersonHand.exists() || !Reflector.callBoolean(Reflector.ForgeHooksClient_renderSpecificFirstPersonHand, new Object[]{EnumHand.OFF_HAND, Float.valueOf(partialTicks), Float.valueOf(f1), Float.valueOf(f4), Float.valueOf(f6), this.itemStackOffHand})) {
                this.renderItemInFirstPerson(abstractclientplayer, partialTicks, f1, EnumHand.OFF_HAND, f4, this.itemStackOffHand, f6);
            }
            GlStateManager.popMatrix();
            ViewModel.readRenderHandStop(EnumHand.OFF_HAND);
            nullate = true;
        }
        if (nullate) {
            ViewModel.readRenderHandNullateOnEmpty(enumhand, false);
        }
        GlStateManager.disableRescaleNormal();
        RenderHelper.disableStandardItemLighting();
    }

    public void renderItemInFirstPerson(AbstractClientPlayer p_187457_1_, float p_187457_2_, float p_187457_3_, EnumHand p_187457_4_, float p_187457_5_, ItemStack p_187457_6_, float p_187457_7_) {
        if (!Config.isShaders() || !Shaders.isSkipRenderHand(p_187457_4_)) {
            boolean flag = p_187457_4_ == EnumHand.MAIN_HAND;
            EnumHandSide enumhandside = flag ? p_187457_1_.getPrimaryHand() : p_187457_1_.getPrimaryHand().opposite();
            GlStateManager.pushMatrix();
            if (p_187457_6_.func_190926_b()) {
                if (flag && !p_187457_1_.isInvisible()) {
                    this.renderArmFirstPerson(p_187457_7_, p_187457_5_, enumhandside);
                }
            } else if (p_187457_6_.getItem() instanceof ItemMap) {
                if (flag && this.itemStackOffHand.func_190926_b()) {
                    this.renderMapFirstPerson(p_187457_3_, p_187457_7_, p_187457_5_);
                } else {
                    this.renderMapFirstPersonSide(p_187457_7_, enumhandside, p_187457_5_, p_187457_6_);
                }
            } else {
                boolean flag1;
                boolean bl = flag1 = enumhandside == EnumHandSide.RIGHT;
                if (p_187457_1_.isHandActive() && p_187457_1_.getItemInUseCount() > 0 && p_187457_1_.getActiveHand() == p_187457_4_) {
                    int j = flag1 ? 1 : -1;
                    switch (p_187457_6_.getItemUseAction()) {
                        case NONE: {
                            this.transformSideFirstPerson(enumhandside, p_187457_7_);
                            break;
                        }
                        case EAT: 
                        case DRINK: {
                            this.transformEatFirstPerson(p_187457_2_, enumhandside, p_187457_6_);
                            this.transformSideFirstPerson(enumhandside, p_187457_7_);
                            break;
                        }
                        case BLOCK: {
                            this.transformSideFirstPerson(enumhandside, p_187457_7_);
                            break;
                        }
                        case BOW: {
                            this.transformSideFirstPerson(enumhandside, p_187457_7_);
                            GlStateManager.translate((float)j * -0.2785682f, 0.18344387f, 0.15731531f);
                            GlStateManager.rotate(-13.935f, 1.0f, 0.0f, 0.0f);
                            GlStateManager.rotate((float)j * 35.3f, 0.0f, 1.0f, 0.0f);
                            GlStateManager.rotate((float)j * -9.785f, 0.0f, 0.0f, 1.0f);
                            float f5 = (float)p_187457_6_.getMaxItemUseDuration() - ((float)Minecraft.player.getItemInUseCount() - p_187457_2_ + 1.0f);
                            float f6 = f5 / 20.0f;
                            f6 = (f6 * f6 + f6 * 2.0f) / 3.0f;
                            if (f6 > 1.0f) {
                                f6 = 1.0f;
                            }
                            if (f6 > 0.1f) {
                                float f7 = MathHelper.sin((f5 - 0.1f) * 1.3f);
                                float f3 = f6 - 0.1f;
                                float f4 = f7 * f3;
                                GlStateManager.translate(f4 * 0.0f, f4 * 0.004f, f4 * 0.0f);
                            }
                            GlStateManager.translate(f6 * 0.0f, f6 * 0.0f, f6 * 0.04f);
                            GlStateManager.scale(1.0f, 1.0f, 1.0f + f6 * 0.2f);
                            GlStateManager.rotate((float)j * 45.0f, 0.0f, -1.0f, 0.0f);
                        }
                    }
                } else {
                    int i;
                    float f = -0.4f * MathHelper.sin(MathHelper.sqrt(p_187457_5_) * (float)Math.PI);
                    float f1 = 0.2f * MathHelper.sin(MathHelper.sqrt(p_187457_5_) * ((float)Math.PI * 2));
                    float f2 = -0.2f * MathHelper.sin(p_187457_5_ * (float)Math.PI);
                    int n = i = flag1 ? 1 : -1;
                    if (!ViewModel.animate(true, 0.0f, 0.0f, 0)) {
                        GlStateManager.translate((float)i * f, f1, f2);
                    }
                    this.transformSideFirstPerson(enumhandside, p_187457_7_);
                    this.transformFirstPerson(enumhandside, p_187457_5_);
                }
                this.renderItemSide(p_187457_1_, p_187457_6_, flag1 ? ItemCameraTransforms.TransformType.FIRST_PERSON_RIGHT_HAND : ItemCameraTransforms.TransformType.FIRST_PERSON_LEFT_HAND, !flag1);
            }
            GlStateManager.popMatrix();
        }
    }

    public void renderOverlays(float partialTicks) {
        GlStateManager.disableAlpha();
        if (Minecraft.player.isEntityInsideOpaqueBlock()) {
            IBlockState iblockstate = this.mc.world.getBlockState(new BlockPos(Minecraft.player));
            BlockPos blockpos = new BlockPos(Minecraft.player);
            EntityPlayerSP entityplayer = Minecraft.player;
            for (int i = 0; i < 8; ++i) {
                double d0 = entityplayer.posX + (double)(((float)((i >> 0) % 2) - 0.5f) * entityplayer.width * 0.8f);
                double d1 = entityplayer.posY + (double)(((float)((i >> 1) % 2) - 0.5f) * 0.1f);
                double d2 = entityplayer.posZ + (double)(((float)((i >> 2) % 2) - 0.5f) * entityplayer.width * 0.8f);
                BlockPos blockpos1 = new BlockPos(d0, d1 + (double)entityplayer.getEyeHeight(), d2);
                IBlockState iblockstate1 = this.mc.world.getBlockState(blockpos1);
                if (!iblockstate1.func_191058_s()) continue;
                iblockstate = iblockstate1;
                blockpos = blockpos1;
            }
            if (iblockstate.getRenderType() != EnumBlockRenderType.INVISIBLE) {
                Object object = Reflector.getFieldValue(Reflector.RenderBlockOverlayEvent_OverlayType_BLOCK);
                if (!Reflector.callBoolean(Reflector.ForgeEventFactory_renderBlockOverlay, Minecraft.player, Float.valueOf(partialTicks), object, iblockstate, blockpos)) {
                    this.renderBlockInHand(this.mc.getBlockRendererDispatcher().getBlockModelShapes().getTexture(iblockstate));
                }
            }
        }
        if (!Minecraft.player.isSpectator()) {
            if (!(!Minecraft.player.isInsideOfMaterial(Material.WATER) || Reflector.callBoolean(Reflector.ForgeEventFactory_renderWaterOverlay, Minecraft.player, Float.valueOf(partialTicks)) || NoRender.get.actived && NoRender.get.LiquidOverlay.bValue)) {
                this.renderWaterOverlayTexture(partialTicks);
            }
            if (!(!Minecraft.player.isBurning() || Reflector.callBoolean(Reflector.ForgeEventFactory_renderFireOverlay, Minecraft.player, Float.valueOf(partialTicks)) || NoRender.get.actived && NoRender.get.Fire.bValue)) {
                this.renderFireInFirstPerson();
            }
        }
        GlStateManager.enableAlpha();
    }

    private void renderBlockInHand(TextureAtlasSprite partialTicks) {
        this.mc.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        float f = 0.1f;
        GlStateManager.color(0.1f, 0.1f, 0.1f, 0.5f);
        GlStateManager.pushMatrix();
        float f1 = -1.0f;
        float f2 = 1.0f;
        float f3 = -1.0f;
        float f4 = 1.0f;
        float f5 = -0.5f;
        float f6 = partialTicks.getMinU();
        float f7 = partialTicks.getMaxU();
        float f8 = partialTicks.getMinV();
        float f9 = partialTicks.getMaxV();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos(-1.0, -1.0, -0.5).tex(f7, f9).endVertex();
        bufferbuilder.pos(1.0, -1.0, -0.5).tex(f6, f9).endVertex();
        bufferbuilder.pos(1.0, 1.0, -0.5).tex(f6, f8).endVertex();
        bufferbuilder.pos(-1.0, 1.0, -0.5).tex(f7, f8).endVertex();
        tessellator.draw();
        GlStateManager.popMatrix();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
    }

    private void renderWaterOverlayTexture(float partialTicks) {
        if (NoRender.get.actived && NoRender.get.LiquidOverlay.bValue) {
            return;
        }
        if (!Config.isShaders() || Shaders.isUnderwaterOverlay()) {
            this.mc.getTextureManager().bindTexture(RES_UNDERWATER_OVERLAY);
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder bufferbuilder = tessellator.getBuffer();
            float f = Minecraft.player.getBrightness();
            GlStateManager.color(f, f, f, 0.5f);
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            GlStateManager.pushMatrix();
            float f1 = 4.0f;
            float f2 = -1.0f;
            float f3 = 1.0f;
            float f4 = -1.0f;
            float f5 = 1.0f;
            float f6 = -0.5f;
            float f7 = -Minecraft.player.rotationYaw / 64.0f;
            float f8 = Minecraft.player.rotationPitch / 64.0f;
            bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
            bufferbuilder.pos(-1.0, -1.0, -0.5).tex(4.0f + f7, 4.0f + f8).endVertex();
            bufferbuilder.pos(1.0, -1.0, -0.5).tex(0.0f + f7, 4.0f + f8).endVertex();
            bufferbuilder.pos(1.0, 1.0, -0.5).tex(0.0f + f7, 0.0f + f8).endVertex();
            bufferbuilder.pos(-1.0, 1.0, -0.5).tex(4.0f + f7, 0.0f + f8).endVertex();
            tessellator.draw();
            GlStateManager.popMatrix();
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.disableBlend();
        }
    }

    private void renderFireInFirstPerson() {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 0.9f);
        GlStateManager.depthFunc(519);
        GlStateManager.depthMask(false);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        float f = 1.0f;
        for (int i = 0; i < 2; ++i) {
            GlStateManager.pushMatrix();
            TextureAtlasSprite textureatlassprite = this.mc.getTextureMapBlocks().getAtlasSprite("minecraft:blocks/fire_layer_1");
            this.mc.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
            float f1 = textureatlassprite.getMinU();
            float f2 = textureatlassprite.getMaxU();
            float f3 = textureatlassprite.getMinV();
            float f4 = textureatlassprite.getMaxV();
            float f5 = -0.5f;
            float f6 = 0.5f;
            float f7 = -0.5f;
            float f8 = 0.5f;
            float f9 = -0.5f;
            GlStateManager.translate((float)(-(i * 2 - 1)) * 0.24f, -0.3f, 0.0f);
            GlStateManager.rotate((float)(i * 2 - 1) * 10.0f, 0.0f, 1.0f, 0.0f);
            bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
            bufferbuilder.pos(-0.5, -0.5, -0.5).tex(f2, f4).endVertex();
            bufferbuilder.pos(0.5, -0.5, -0.5).tex(f1, f4).endVertex();
            bufferbuilder.pos(0.5, 0.5, -0.5).tex(f1, f3).endVertex();
            bufferbuilder.pos(-0.5, 0.5, -0.5).tex(f2, f3).endVertex();
            tessellator.draw();
            GlStateManager.popMatrix();
        }
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.disableBlend();
        GlStateManager.depthMask(true);
        GlStateManager.depthFunc(515);
    }

    public void updateEquippedItem() {
        this.prevEquippedProgressMainHand = equippedProgressMainHand;
        this.prevEquippedProgressOffHand = this.equippedProgressOffHand;
        EntityPlayerSP entityplayersp = Minecraft.player;
        ItemStack itemstack = entityplayersp.getHeldItemMainhand();
        ItemStack itemstack1 = entityplayersp.getHeldItemOffhand();
        if (entityplayersp.isRowingBoat()) {
            equippedProgressMainHand = MathHelper.clamp(equippedProgressMainHand - 0.4f, 0.0f, 1.0f);
            this.equippedProgressOffHand = MathHelper.clamp(this.equippedProgressOffHand - 0.4f, 0.0f, 1.0f);
        } else {
            float f = entityplayersp.getCooledAttackStrength(1.0f);
            if (Reflector.ForgeHooksClient_shouldCauseReequipAnimation.exists()) {
                boolean flag = Reflector.callBoolean(Reflector.ForgeHooksClient_shouldCauseReequipAnimation, this.itemStackMainHand, itemstack, entityplayersp.inventory.currentItem);
                boolean flag1 = Reflector.callBoolean(Reflector.ForgeHooksClient_shouldCauseReequipAnimation, this.itemStackOffHand, itemstack1, -1);
                if (!flag && !Objects.equals(this.itemStackMainHand, itemstack)) {
                    this.itemStackMainHand = itemstack;
                }
                if (!flag && !Objects.equals(this.itemStackOffHand, itemstack1)) {
                    this.itemStackOffHand = itemstack1;
                }
                equippedProgressMainHand += MathHelper.clamp((!flag ? f * f * f : 0.0f) - equippedProgressMainHand, -0.4f, 0.4f);
                this.equippedProgressOffHand += MathHelper.clamp((float)(!flag1 ? 1 : 0) - this.equippedProgressOffHand, -0.4f, 0.4f);
            } else {
                equippedProgressMainHand += MathHelper.clamp((Objects.equals(this.itemStackMainHand, itemstack) ? f * f * f : 0.0f) - equippedProgressMainHand, -0.4f, 0.4f);
                this.equippedProgressOffHand += MathHelper.clamp((float)(Objects.equals(this.itemStackOffHand, itemstack1) ? 1 : 0) - this.equippedProgressOffHand, -0.4f, 0.4f);
            }
        }
        if (equippedProgressMainHand < 0.1f) {
            this.itemStackMainHand = itemstack;
            if (Config.isShaders()) {
                Shaders.setItemToRenderMain(this.itemStackMainHand);
            }
        }
        if (this.equippedProgressOffHand < 0.1f) {
            this.itemStackOffHand = itemstack1;
            if (Config.isShaders()) {
                Shaders.setItemToRenderOff(this.itemStackOffHand);
            }
        }
    }

    public void resetEquippedProgress(EnumHand hand) {
        if (hand == EnumHand.MAIN_HAND) {
            equippedProgressMainHand = 0.0f;
        } else {
            this.equippedProgressOffHand = 0.0f;
        }
    }
}

