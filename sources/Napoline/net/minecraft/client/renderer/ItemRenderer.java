package net.minecraft.client.renderer;


import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemStack;
import net.minecraft.src.Config;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.MapData;

import net.optifine.DynamicLights;
import net.optifine.reflect.Reflector;
import net.optifine.shaders.Shaders;
import org.lwjgl.opengl.GL11;

import me.napoleon.napoline.Napoline;
import me.napoleon.napoline.manager.ModuleManager;
import me.napoleon.napoline.modules.render.Animation;


public class ItemRenderer {
    private static final ResourceLocation RES_MAP_BACKGROUND = new ResourceLocation("textures/map/map_background.png");
    private static final ResourceLocation RES_UNDERWATER_OVERLAY = new ResourceLocation("textures/misc/underwater.png");

    /**
     * A reference to the Minecraft object.
     */
    private final Minecraft mc;
    private ItemStack itemToRender;

    /**
     * How far the current item has been equipped (0 disequipped and 1 fully up)
     */
    private float equippedProgress;
    private float prevEquippedProgress;
    private final RenderManager renderManager;
    private final RenderItem itemRenderer;

    /**
     * The index of the currently held item (0-8, or -1 if not yet updated)
     */
    private int equippedItemSlot = -1;
    private static final String __OBFID = "CL_00000953";
    private static final int swingProgress = 0;
    private static final float equipProgress = 0;
    private float rot = 0;

    public ItemRenderer(Minecraft mcIn) {
        this.mc = mcIn;
        this.renderManager = mcIn.getRenderManager();
        this.itemRenderer = mcIn.getRenderItem();
    }

    public void renderItem(EntityLivingBase entityIn, ItemStack heldStack,
                           ItemCameraTransforms.TransformType transform) {
        if (heldStack != null) {
            Item item = heldStack.getItem();
            Block block = Block.getBlockFromItem(item);
            GlStateManager.pushMatrix();

            if (this.itemRenderer.shouldRenderItemIn3D(heldStack)) {
                GlStateManager.scale(2.0F, 2.0F, 2.0F);

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

    /**
     * Returns true if given block is translucent
     */
    private boolean isBlockTranslucent(Block blockIn) {
        return blockIn != null && blockIn.getBlockLayer() == EnumWorldBlockLayer.TRANSLUCENT;
    }

    private void func_178101_a(float angle, float p_178101_2_) {
        GlStateManager.pushMatrix();
        GlStateManager.rotate(angle, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(p_178101_2_, 0.0F, 1.0F, 0.0F);
        RenderHelper.enableStandardItemLighting();
        GlStateManager.popMatrix();
    }

    private void func_178109_a(AbstractClientPlayer clientPlayer) {
        int i = this.mc.theWorld.getCombinedLight(new BlockPos(clientPlayer.posX,
                clientPlayer.posY + (double) clientPlayer.getEyeHeight(), clientPlayer.posZ), 0);

        if (Config.isDynamicLights()) {
            i = DynamicLights.getCombinedLight(this.mc.getRenderViewEntity(), i);
        }

        float f = (float) (i & 65535);
        float f1 = (float) (i >> 16);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, f, f1);
    }

    private void func_178110_a(EntityPlayerSP entityplayerspIn, float partialTicks) {
        float f = entityplayerspIn.prevRenderArmPitch
                + (entityplayerspIn.renderArmPitch - entityplayerspIn.prevRenderArmPitch) * partialTicks;
        float f1 = entityplayerspIn.prevRenderArmYaw
                + (entityplayerspIn.renderArmYaw - entityplayerspIn.prevRenderArmYaw) * partialTicks;
        GlStateManager.rotate((entityplayerspIn.rotationPitch - f) * 0.1F, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate((entityplayerspIn.rotationYaw - f1) * 0.1F, 0.0F, 1.0F, 0.0F);
    }

    private float func_178100_c(float p_178100_1_) {
        float f = 1.0F - p_178100_1_ / 45.0F + 0.1F;
        f = MathHelper.clamp_float(f, 0.0F, 1.0F);
        f = -MathHelper.cos(f * (float) Math.PI) * 0.5F + 0.5F;
        return f;
    }

    private void renderRightArm(RenderPlayer renderPlayerIn) {
        GlStateManager.pushMatrix();
        GlStateManager.rotate(54.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(64.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(-62.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.translate(0.25F, -0.85F, 0.75F);
        renderPlayerIn.renderRightArm(this.mc.thePlayer);
        GlStateManager.popMatrix();
    }

    private void renderLeftArm(RenderPlayer renderPlayerIn) {
        GlStateManager.pushMatrix();
        GlStateManager.rotate(92.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(45.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(41.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.translate(-0.3F, -1.1F, 0.45F);
        renderPlayerIn.renderLeftArm(this.mc.thePlayer);
        GlStateManager.popMatrix();
    }

    private void renderPlayerArms(AbstractClientPlayer clientPlayer) {
        this.mc.getTextureManager().bindTexture(clientPlayer.getLocationSkin());
        Render render = this.renderManager.getEntityRenderObject(this.mc.thePlayer);
        RenderPlayer renderplayer = (RenderPlayer) render;

        if (!clientPlayer.isInvisible()) {
            GlStateManager.disableCull();
            this.renderRightArm(renderplayer);
            this.renderLeftArm(renderplayer);
            GlStateManager.enableCull();
        }
    }

    private void renderItemMap(AbstractClientPlayer clientPlayer, float p_178097_2_, float p_178097_3_,
                               float p_178097_4_) {
        float f = -0.4F * MathHelper.sin(MathHelper.sqrt_float(p_178097_4_) * (float) Math.PI);
        float f1 = 0.2F * MathHelper.sin(MathHelper.sqrt_float(p_178097_4_) * (float) Math.PI * 2.0F);
        float f2 = -0.2F * MathHelper.sin(p_178097_4_ * (float) Math.PI);
        GlStateManager.translate(f, f1, f2);
        float f3 = this.func_178100_c(p_178097_2_);
        GlStateManager.translate(0.0F, 0.04F, -0.72F);
        GlStateManager.translate(0.0F, p_178097_3_ * -1.2F, 0.0F);
        GlStateManager.translate(0.0F, f3 * -0.5F, 0.0F);
        GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(f3 * -85.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.rotate(0.0F, 1.0F, 0.0F, 0.0F);
        this.renderPlayerArms(clientPlayer);
        float f4 = MathHelper.sin(p_178097_4_ * p_178097_4_ * (float) Math.PI);
        float f5 = MathHelper.sin(MathHelper.sqrt_float(p_178097_4_) * (float) Math.PI);
        GlStateManager.rotate(f4 * -20.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(f5 * -20.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.rotate(f5 * -80.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.scale(0.38F, 0.38F, 0.38F);
        GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.rotate(0.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.translate(-1.0F, -1.0F, 0.0F);
        GlStateManager.scale(0.015625F, 0.015625F, 0.015625F);
        this.mc.getTextureManager().bindTexture(RES_MAP_BACKGROUND);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        GL11.glNormal3f(0.0F, 0.0F, -1.0F);
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldrenderer.pos(-7.0D, 135.0D, 0.0D).tex(0.0D, 1.0D).endVertex();
        worldrenderer.pos(135.0D, 135.0D, 0.0D).tex(1.0D, 1.0D).endVertex();
        worldrenderer.pos(135.0D, -7.0D, 0.0D).tex(1.0D, 0.0D).endVertex();
        worldrenderer.pos(-7.0D, -7.0D, 0.0D).tex(0.0D, 0.0D).endVertex();
        tessellator.draw();
        MapData mapdata = Items.filled_map.getMapData(this.itemToRender, this.mc.theWorld);

        if (mapdata != null) {
            this.mc.entityRenderer.getMapItemRenderer().renderMap(mapdata, false);
        }
    }

    private void func_178095_a(AbstractClientPlayer clientPlayer, float p_178095_2_, float p_178095_3_) {
        float f = -0.3F * MathHelper.sin(MathHelper.sqrt_float(p_178095_3_) * (float) Math.PI);
        float f1 = 0.4F * MathHelper.sin(MathHelper.sqrt_float(p_178095_3_) * (float) Math.PI * 2.0F);
        float f2 = -0.4F * MathHelper.sin(p_178095_3_ * (float) Math.PI);
        GlStateManager.translate(f, f1, f2);
        GlStateManager.translate(0.64000005F, -0.6F, -0.71999997F);
        GlStateManager.translate(0.0F, p_178095_2_ * -0.6F, 0.0F);
        GlStateManager.rotate(45.0F, 0.0F, 1.0F, 0.0F);
        float f3 = MathHelper.sin(p_178095_3_ * p_178095_3_ * (float) Math.PI);
        float f4 = MathHelper.sin(MathHelper.sqrt_float(p_178095_3_) * (float) Math.PI);
        GlStateManager.rotate(f4 * 70.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(f3 * -20.0F, 0.0F, 0.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(clientPlayer.getLocationSkin());
        GlStateManager.translate(-1.0F, 3.6F, 3.5F);
        GlStateManager.rotate(120.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.rotate(200.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.scale(1.0F, 1.0F, 1.0F);
        GlStateManager.translate(5.6F, 0.0F, 0.0F);
        Render render = this.renderManager.getEntityRenderObject(this.mc.thePlayer);
        GlStateManager.disableCull();
        RenderPlayer renderplayer = (RenderPlayer) render;
        renderplayer.renderRightArm(this.mc.thePlayer);
        GlStateManager.enableCull();
    }

    private void func_178105_d(float p_178105_1_) {
        if (ModuleManager.getModByClass(Animation.class).getState() && Animation.sanimation.getValue().booleanValue()) {
            float f = -0.15F * MathHelper.sin(MathHelper.sqrt_float(p_178105_1_) * (float) Math.PI);
            float f1 = -0.05F * MathHelper.sin(MathHelper.sqrt_float(p_178105_1_) * (float) Math.PI * 1.5F);
            float f2 = -0.0F * MathHelper.sin(p_178105_1_ * (float) Math.PI);
            GlStateManager.translate(f, f1, f2);
        } else {
            float var2 = -0.4f * MathHelper.sin(MathHelper.sqrt_float(p_178105_1_) * 3.1415927f);
            float var3 = 0.2f * MathHelper.sin(MathHelper.sqrt_float(p_178105_1_) * 3.1415927f * 2.0f);
            float var4 = -0.2f * MathHelper.sin(p_178105_1_ * 3.1415927f);
            GlStateManager.translate(var2, var3, var4);
        }
    }

    private void func_178104_a(AbstractClientPlayer clientPlayer, float p_178104_2_) {
        float f = (float) clientPlayer.getItemInUseCount() - p_178104_2_ + 1.0F;
        float f1 = f / (float) this.itemToRender.getMaxItemUseDuration();
        float f2 = MathHelper.abs(MathHelper.cos(f / 4.0F * (float) Math.PI) * 0.1F);

        if (f1 >= 0.8F) {
            f2 = 0.0F;
        }

        GlStateManager.translate(0.0F, f2, 0.0F);
        float f3 = 1.0F - (float) Math.pow((double) f1, 27.0D);
        GlStateManager.translate(f3 * 0.6F, f3 * -0.5F, f3 * 0.0F);
        GlStateManager.rotate(f3 * 90.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(f3 * 10.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(f3 * 30.0F, 0.0F, 0.0F, 1.0F);
    }

    /**
     * Performs transformations prior to the rendering of a held item in first
     * person.
     */
    private void transformFirstPersonItem(float equipProgress, float swingProgress) {
        GlStateManager.translate(0.56F, -0.47F, -0.73999997F);
        GlStateManager.translate(0.0F, equipProgress * -0.6F, 0.0F);
        GlStateManager.rotate(45.0f, 0.0F, 1.0F, 0.0F);
        float f = MathHelper.sin(swingProgress * swingProgress * 3.1415927F);
        float f1 = MathHelper.sin(MathHelper.sqrt_float(swingProgress) * 3.1415927F);
        GlStateManager.rotate(f * -20.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(f1 * -20.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.rotate(f1 * -80.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.scale(0.4F, 0.4F, 0.4F);
    }

    public boolean block;


    private void func_178098_a(float p_178098_1_, AbstractClientPlayer clientPlayer) {
        GlStateManager.rotate(-18.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.rotate(-12.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-8.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.translate(-0.9F, 0.2F, 0.0F);
        float f = (float) this.itemToRender.getMaxItemUseDuration()
                - ((float) clientPlayer.getItemInUseCount() - p_178098_1_ + 1.0F);
        float f1 = f / 20.0F;
        f1 = (f1 * f1 + f1 * 2.0F) / 3.0F;

        if (f1 > 1.0F) {
            f1 = 1.0F;
        }

        if (f1 > 0.1F) {
            float f2 = MathHelper.sin((f - 0.1F) * 1.3F);
            float f3 = f1 - 0.1F;
            float f4 = f2 * f3;
            GlStateManager.translate(f4 * 0.0F, f4 * 0.01F, f4 * 0.0F);
        }

        GlStateManager.translate(f1 * 0.0F, f1 * 0.0F, f1 * 0.1F);
        GlStateManager.scale(1.0F, 1.0F, 1.0F + f1 * 0.2F);
    }

    private void funce_111888() {
        GlStateManager.translate(-0.5F, 0.7F, 0.0F);
        GlStateManager.rotate(30.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-80.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(60.0F, 0.0F, 1.0F, 0.0F);
    }

    private void func_178103_d() {
        GlStateManager.translate(-0.5F, 0.3F, 0.0F);
        GlStateManager.rotate(30.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-80.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(60.0F, 0.0F, 1.0F, 0.0F);
    }

    private void func_178096_b(float p_178096_1_, float p_178096_2_) {
        GlStateManager.translate((float) 0.56f, (float) -0.42f, (float) -0.71999997f);
        GlStateManager.translate((float) 0.0f, (float) (p_178096_1_ * -0.6f), (float) 0.0f);
        GlStateManager.rotate((float) 45.0f, (float) 0.0f, (float) 1.0f, (float) 0.0f);
        float var3 = MathHelper.sin((float) (p_178096_2_ * p_178096_2_ * 3.1415927f));
        float var4 = MathHelper.sin((float) (MathHelper.sqrt_float((float) p_178096_2_) * 3.1415927f));
        GlStateManager.rotate((float) (var3 * -20.0f), (float) 0.0f, (float) 1.0f, (float) 0.0f);
        GlStateManager.rotate((float) (var4 * -20.0f), (float) 0.0f, (float) 0.0f, (float) 1.0f);
        GlStateManager.rotate((float) (var4 * -80.0f), (float) 1.0f, (float) 0.0f, (float) 0.0f);
        GlStateManager.scale((float) 0.4f, (float) 0.4f, (float) 0.4f);
    }

    public void renderItemInFirstPerson(float partialTicks) {
        GL11.glTranslated((double) ((Double) Animation.translate1.getValue()),
                (double) ((Double) Animation.translate.getValue()),
                (double) ((Double) Animation.translate2.getValue()));
        float var2 = 1.0f
                - (this.prevEquippedProgress + (this.equippedProgress - this.prevEquippedProgress) * partialTicks);
        EntityPlayerSP var3 = this.mc.thePlayer;
        float var4 = mc.thePlayer.getSwingProgress(partialTicks);
        float swingprogress = var3.getSwingProgress(partialTicks);
        float f = 1.0F
                - (this.prevEquippedProgress + (this.equippedProgress - this.prevEquippedProgress) * partialTicks);
        EntityPlayerSP entityplayersp = this.mc.thePlayer;
        float f1 = entityplayersp.getSwingProgress(partialTicks);
        float f2 = entityplayersp.prevRotationPitch
                + (entityplayersp.rotationPitch - entityplayersp.prevRotationPitch) * partialTicks;
        float f3 = entityplayersp.prevRotationYaw
                + (entityplayersp.rotationYaw - entityplayersp.prevRotationYaw) * partialTicks;
        this.func_178101_a(f2, f3);
        this.func_178109_a(entityplayersp);
        this.func_178110_a(entityplayersp, partialTicks);
        GlStateManager.enableRescaleNormal();
        GlStateManager.pushMatrix();
        if (this.itemToRender != null) {
            if (this.itemToRender.getItem() instanceof ItemMap) {
                this.renderItemMap(entityplayersp, f2, f, f1);
            } else if (entityplayersp.getItemInUseCount() > 0) {
                EnumAction enumaction = this.itemToRender.getItemUseAction();

                switch (ItemRenderer.ItemRenderer$1.field_178094_a[enumaction.ordinal()]) {
                    case 1:
                        this.transformFirstPersonItem(f, 0.0F);
                        break;

                    case 2:
                    case 3:
                        this.func_178104_a(entityplayersp, partialTicks);
                        this.transformFirstPersonItem(f, 0.0F);
                        break;

                    case 4:
                        if (Napoline.INSTANCE.moduleManager.getModByClass(Animation.class).getState()) {
                            float var9;
                            float var8;
                            float var14;
                            float var15;
                            float var92 = MathHelper.sin(MathHelper.sqrt_float(f1) * (float) Math.PI);
                            if (Animation.mode.getValue() == Animation.renderMode.ETB) {
                                this.ETB(f, f1);
                                this.func_178103_d();
                            }
                            if (Animation.mode.getValue() == Animation.renderMode.Slide) {
                                this.slide(f, f1);
                                this.funce_111888();

                            }

                            if (Animation.mode.getValue() == Animation.renderMode.Jigsaw) {
                                this.tap(f, f1);
                                this.funce_111888();
                            }
                            if (Animation.mode.getValue() == Animation.renderMode.Jello) {
                                // float var9 = MathHelper.sin(MathHelper.sqrt_float(var4) * 3.1415927f);
                                // this.func_178096_b(var2, 0.0f);
                                // this.func_178103_d();
                                // GlStateManager.translate(-0.3F, 0F, 0.1F);
                                // GlStateManager.rotate(-var9 * (float)40.0, 1.0f, -0.4f,0.4f);
                                this.jello(f, f1);
                                this.func_178103_d();
                            }
                            if (Animation.mode.getValue() == Animation.renderMode.Leain) {
                                var15 = MathHelper.sin((float) (MathHelper.sqrt_float((float) var4) * 3.1415927f));
                                this.func_178096_b(var2 * 0.5f, 0);
                                GlStateManager.rotate(-var15 * 25 / 2.0F, -18.0F, -0.0F, 9.0F);
//                    		GlStateManager.rotate(-var15 * 15, 1.0F, var15 /2, -0F);
                                this.func_178103_d();
                                GL11.glTranslated(1.5, 0.3, 0.5);
                                GL11.glTranslatef(-1, this.mc.thePlayer.isSneaking() ? -0.1F : -0.2F, 0.2F);
//                            GlStateManager.scale(1.2f,1.2f,1.2f);
                            }

                            if (Animation.mode.getValue() == Animation.renderMode.External) {
                                this.transformFirstPersonItem(f, 0.0F);
                                this.func_178103_d();
                                var14 = MathHelper.sin(f1 * f1 * 3.1415927F);
                                var15 = MathHelper.sin(MathHelper.sqrt_float(f1) * 3.1415927F);
                                GlStateManager.translate(-0.3F, 0.2F, 0.2F);
                                GlStateManager.rotate(-var15 * 50.0F, -100.0F, -1000.0F, 10200.0F);
                                GlStateManager.rotate(-var15 * 20.0F, 1.0F, -0.1F, -1.5F);

                            }
                            if (Animation.mode.getValue() == Animation.renderMode.Avatar) {
                                this.avatar(f, f1);
                                this.func_178103_d();
                            }
                            if (Animation.mode.getValue() == Animation.renderMode.Swank) {
                                this.func_178096_b(var2 / 2.0f, var4);
                                var15 = MathHelper.sin((float) (MathHelper.sqrt_float((float) var4) * 3.1415927f));
                                GlStateManager.rotate((float) (var15 * 30.0f), (float) (-var15), (float) -0.0f,
                                        (float) 9.0f);
                                GlStateManager.rotate((float) (var15 * 50.0f), (float) 1.0f, (float) (-var15),
                                        (float) -0.0f);
                                this.func_178103_d();
                            }
                            if (Animation.mode.getValue() == Animation.renderMode.Prohibit) {

                                this.transformFirstPersonItem(f, 0.83F);
                                this.func_178103_d();
                                var14 = MathHelper.sin(f1 * f1 * 1.1415927F);
                                var15 = MathHelper.sin(MathHelper.sqrt_float(f1) * 3.1415927F);
                                GlStateManager.translate(-0.2F, 0.2F, 0.3);
                                GlStateManager.rotate(-var15 * 10.0F, -0.0F, 1.0F, 15.0F);
                                GlStateManager.rotate(-var15 * 5.0F, 1.0F, 2.4F, -0.5F);

                            }
                            if (Animation.mode.getValue() == Animation.renderMode.Exhibition) {
                                GlStateManager.translate(0.76f, -0.42f, -0.81999997f);
                                GlStateManager.translate(0.0f, f * -0.6f, 0.0f);
                                GlStateManager.rotate(20.0f, 0.0f, 1.0f, 0.0f);
                                float var113 = MathHelper.sin(f * f1 * 3.1415927f);
                                float var114 = MathHelper.sin(MathHelper.sqrt_float(f1) * 3.1415927f);
                                GlStateManager.rotate(var113 * -24.0f, 0.0f, 1.0f, 0.2f);
                                GlStateManager.rotate(var114 * -30.7f, 0.2f, 0.1f, 1.0f);
                                GlStateManager.rotate(var114 * -48.6f, 1.3f, 0.1f, 0.2f);
                                float size = 0.45f;
                                GlStateManager.scale(size, size, size);
                                this.func_178103_d();
                            }

                            if (Animation.mode.getValue() == Animation.renderMode.Luna) {
                                this.transformFirstPersonItem(f, 0.0F);
                                this.func_178103_d();
                                float var31 = MathHelper.sin(f1 * f1 * 3.1415927F);
                                float var41 = MathHelper.sin(MathHelper.sqrt_float(f1) * 3.1415927F);
                                GlStateManager.scale(1.0F, 1.0F, 1.0F);
                                GlStateManager.translate(-0.2F, 0.45F, 0.25F);
                                GlStateManager.rotate(-var41 * 20.0F, -5.0F, -5.0F, 9.0F);

                            }
                            if (Animation.mode.getValue() == Animation.renderMode.Swing) {
                                this.transformFirstPersonItem(f / 2.0F, f1);
                                this.func_178103_d();
                            }
                            if (Animation.mode.getValue() == Animation.renderMode.Nov) {
                                this.genCustom(f, f1);
                                this.func_178103_d();
                            }
                            if (Animation.mode.getValue() == Animation.renderMode.OLD) {
                                this.transformFirstPersonItem(f, f1);
                                this.func_178103_d();
                            }
                            if (Animation.mode.getValue() == Animation.renderMode.SLIDE) {
                                this.transformFirstPersonItem(f, 0.0f);
                                this.func_178103_d();
                                GL11.glTranslated((double) -0.3, (double) 0.3, (double) 0.0);
                                GL11.glRotatef((float) (-var92 * 70.0f / 2.0f), (float) -8.0f, (float) 0.0f, (float) 9.0f);
                                GL11.glRotatef((float) (-var92 * 70.0f), (float) 1.0f, (float) -0.4f, (float) -0.0f);
                            }

                            if (Animation.mode.getValue() == Animation.renderMode.Rotation) {
                                this.transformFirstPersonItem(f / 2.0f, 0.0f);
                                if (rot < 360) {
                                    rot += 2;
                                } else {
                                    rot = 0;
                                }
                                GL11.glRotatef(rot, 0, 1,
                                        0);
                                GL11.glRotatef(90, 1, 0,
                                        0);
                                this.func_178103_d();
                            }


                            if (Animation.mode.getValue() == Animation.renderMode.Autumn) {

                                this.transformFirstPersonItem(f / 2.0f, 0.0f);
                                GL11.glRotatef((float) (-var92 * 40.0f / 2.0f), (float) (var92 / 2.0f), (float) -0.0f,
                                        (float) 9.0f);
                                GL11.glRotatef((float) (-var92 * 30.0f), (float) 1.0f, (float) (var92 / 2.0f),
                                        (float) -0.0f);
                                this.func_178103_d();
                            }
                        } else {
                            float var14;
                            float var15;
                            this.func_178096_b(var2 / 2.0f, var4);
                            var15 = MathHelper.sin((float) (MathHelper.sqrt_float((float) var4) * 3.1415927f));
                            GlStateManager.rotate((float) (var15 * 30.0f), (float) (-var15), (float) -0.0f, (float) 9.0f);
                            GlStateManager.rotate((float) (var15 * 50.0f), (float) 1.0f, (float) (-var15), (float) -0.0f);
                            this.func_178103_d();
                        }
                        break;
                    case 5:
                        this.transformFirstPersonItem(f, 0.0F);
                        this.func_178098_a(partialTicks, entityplayersp);
                }
            } else {
                this.func_178105_d(f1);
                this.transformFirstPersonItem(f, f1);
            }

            this.renderItem(entityplayersp, this.itemToRender, ItemCameraTransforms.TransformType.FIRST_PERSON);
        } else if (!entityplayersp.isInvisible()) {
            this.func_178095_a(entityplayersp, f, f1);
        }

        GlStateManager.popMatrix();
        GlStateManager.disableRescaleNormal();
        RenderHelper.disableStandardItemLighting();
    }

    private void sigma2(float var2, float swingProgress) {
        float smooth = (swingProgress * 0.8f - (swingProgress * swingProgress) * 0.8f);
        GlStateManager.translate(0.54F, -0.52F, -0.71999997F);
        GlStateManager.translate(0.0F, var2 * -0.6F, 0.0F);
        GlStateManager.rotate(45.0F, 0.0F, 1.0F, 0.0F);
        float var3 = MathHelper.sin(swingProgress * swingProgress * (float) Math.PI);
        float var4 = MathHelper.sin(MathHelper.sqrt_float(swingProgress) * (float) Math.PI);
        GlStateManager.rotate(var3 * -25, 1.0F, -0.1F, 0.0F);
        GlStateManager.rotate(var4 * -30, 1.0F, -0.1F, 0.0F);
        GlStateManager.rotate(var4 * -20, 0.0F, 0.0F, 0.0F);
        GlStateManager.scale(0.37F, 0.37F, 0.37F);
    }

    private void x3IsBlack(float p_178096_1_, float p_178096_2_) {
        GlStateManager.translate(0.56F, -0.52F, -0.71999997F);
        GlStateManager.translate(0.0F, p_178096_1_ * -0.6F, 0.0F);
        GlStateManager.rotate(45.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(MathHelper.sin(MathHelper.sqrt_float(p_178096_2_) * 3.1415927F) * -35.0F, 1.0F, 0.0F,
                0.0F);
        GlStateManager.scale(0.4F, 0.4F, 0.4F);
    }

    private void genCustom(float p_178096_1_, float p_178096_2_) {
        GlStateManager.translate(0.76f, -0.40f, -1.0f);
        GlStateManager.translate(-0.20f, p_178096_1_ * -1.8f, 0.2f);
        GlStateManager.rotate(45.0f, 0.0f, 0.4f, -0.3f);
        float var3 = MathHelper.sin(p_178096_2_ * p_178096_2_ * 3.15f);
        float var4 = MathHelper.sin(MathHelper.sqrt_float(p_178096_2_) * 3.15f);
        GlStateManager.rotate(var3 * -2.0f, 0.f, 0.0f, -0.1f);
        GlStateManager.rotate(var4 * -2.7f, -0.1f, 0.0f, -0.5f);
        GlStateManager.rotate(var4 * -65f, 0.1f, -0.1f, -0.1f);
        GlStateManager.scale(0.30f, 0.35f, 0.35f);
    }

    private void Hietiens(float p_178096_1_, float p_178096_2_) {

        GlStateManager.translate(0.75F, -0.58F, -0.829999997F);
        GlStateManager.translate(0.0F, p_178096_1_ * -2.6F, 0.0F);
        GlStateManager.rotate(40.0F, 0.0F, 0.6F, 0.0F);
        float var3 = MathHelper.sin(p_178096_2_ * p_178096_2_ * 3.1415927F);
        float var4 = MathHelper.sin(MathHelper.sqrt_float(p_178096_2_) * 3.1415927F);
        GlStateManager.rotate(var4 * -50F, 1.0F, -0.5F, 0.0F);
        GlStateManager.rotate(var4 * -20F, 0.0F, -1.0F, 0.0F);
        GlStateManager.scale(0.4F, 0.4F, 0.4F);
    }

    private void Sigma(float p_178096_1_, float p_178096_2_) {
        GlStateManager.translate(0.64F, -0.65F, -0.70999997F);
        GlStateManager.translate(0.0F, p_178096_1_ * -2.6F, 0.0F);
        GlStateManager.rotate(45.0F, 0.0F, 0.6F, 0.0F);
        float var4 = 0;
        float var15 = MathHelper.sin((float) (MathHelper.sqrt_float((float) var4) * 3.1415927f));
        GlStateManager.rotate(var4 * -50.0F, 0.5F, -1.0F, 1.0F);
        GlStateManager.rotate(var4 * -30.0F, 0.2F, 1.0F, 0.0F);

        GlStateManager.scale(0.4F, 0.4F, 0.4F);
    }

    private void ETB(float p_178096_1_, float p_178096_2_) {
        GlStateManager.translate(0.56f, -0.52f, -0.71999997f);
        GlStateManager.translate(0.0f, p_178096_1_ * -0.6f, 0.0f);
        GlStateManager.rotate(45.0f, 0.0f, 1.0f, 0.0f);
        float var3 = MathHelper.sin(p_178096_2_ * p_178096_2_ * 3.1415927f);
        float var4 = MathHelper.sin(MathHelper.sqrt_float(p_178096_2_) * 3.1415927f);
        GlStateManager.rotate(var3 * -34.0f, 0.0f, 1.0f, 0.2f);
        GlStateManager.rotate(var4 * -20.7f, 0.2f, 0.1f, 1.0f);
        GlStateManager.rotate(var4 * -68.6f, 1.3f, 0.1f, 0.2f);
        GlStateManager.scale(0.4f, 0.4f, 0.4f);
    }

    private void jello(float var2, float swingProgress) {
        float smooth = (swingProgress * 0.8f - (swingProgress * swingProgress) * 0.8f);
        GlStateManager.translate(0.7, -0.4F, -0.8F);
        GlStateManager.rotate(64.0F, -0.1f, 2 + smooth * 5f, smooth * 5);
        GlStateManager.scale(0.4, 0.4, 0.4);
    }

    private void slide(float var2, float swingProgress) {
        float smooth = (swingProgress * 0.8f - (swingProgress * swingProgress) * 0.8f);
        GlStateManager.translate(0.56F, -0.52F, -0.71999997F);
        GlStateManager.translate(0.0F, var2 * -0.6F, 0.0F);
        GlStateManager.rotate(45.0F, 0.0F, 2 + smooth * 0.7f, smooth * 3);
        float var3 = MathHelper.sin(swingProgress * swingProgress * (float) Math.PI);
        float var4 = MathHelper.sin(MathHelper.sqrt_float(swingProgress) * (float) Math.PI);
        GlStateManager.rotate(0f, 0.0F, 1.0F, 0.0F);
        GlStateManager.scale(0.37F, 0.37F, 0.37F);
    }

    private void tap(float var2, float swingProgress) {
        float smooth = (swingProgress * 0.8f - (swingProgress * swingProgress) * 0.8f);
        GlStateManager.translate(0.56F, -0.52F, -0.71999997F);
        GlStateManager.translate(0.0F, var2 * -0.15F, 0.0F);
        GlStateManager.rotate(45.0F, 0.0F, 1.0F, 0.0F);
        float var3 = MathHelper.sin(swingProgress * swingProgress * (float) Math.PI);
        float var4 = MathHelper.sin(MathHelper.sqrt_float(swingProgress) * (float) Math.PI);
        GlStateManager.rotate(smooth * -90.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.scale(0.37F, 0.37F, 0.37F);
    }

    private void avatar(float equipProgress, float swingProgress) {
        GlStateManager.translate(0.56F, -0.52F, -0.71999997F);
        GlStateManager.translate(0.0F, 0, 0.0F);
        GlStateManager.rotate(45.0F, 0.0F, 1.0F, 0.0F);
        float f = MathHelper.sin(swingProgress * swingProgress * (float) Math.PI);
        float f1 = MathHelper.sin(MathHelper.sqrt_float(swingProgress) * (float) Math.PI);
        GlStateManager.rotate(f * -20.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(f1 * -20.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.rotate(f1 * -40.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.scale(0.4F, 0.4F, 0.4F);
    }

    private void Astolfo(float equipProgress, float swingProgress) {
        GlStateManager.translate(0.64F, -0.50F, -0.61999997F);
        GlStateManager.translate(0.0F, 0, 0.0F);
        GlStateManager.rotate(45.0F, 0.0F, 1.0F, 0.0F);
        float f = MathHelper.sin(swingProgress * swingProgress * (float) Math.PI);
        float f1 = MathHelper.sin(MathHelper.sqrt_float(swingProgress) * (float) Math.PI);
        GlStateManager.rotate(f1 * -20.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.rotate(f1 * -20.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.scale(0.4F, 0.4F, 0.4F);
    }

    /**
     * Renders all the overlays that are in first person mode. Args: partialTickTime
     */
    public void renderOverlays(float partialTicks) {
        GlStateManager.disableAlpha();

        if (this.mc.thePlayer.isEntityInsideOpaqueBlock()) {
            IBlockState iblockstate = this.mc.theWorld.getBlockState(new BlockPos(this.mc.thePlayer));
            BlockPos blockpos = new BlockPos(this.mc.thePlayer);
            EntityPlayerSP entityplayersp = this.mc.thePlayer;

            for (int i = 0; i < 8; ++i) {
                double d0 = entityplayersp.posX
                        + (double) (((float) ((i >> 0) % 2) - 0.5F) * entityplayersp.width * 0.8F);
                double d1 = entityplayersp.posY + (double) (((float) ((i >> 1) % 2) - 0.5F) * 0.1F);
                double d2 = entityplayersp.posZ
                        + (double) (((float) ((i >> 2) % 2) - 0.5F) * entityplayersp.width * 0.8F);
                BlockPos blockpos1 = new BlockPos(d0, d1 + (double) entityplayersp.getEyeHeight(), d2);
                IBlockState iblockstate1 = this.mc.theWorld.getBlockState(blockpos1);

                if (iblockstate1.getBlock().isVisuallyOpaque()) {
                    iblockstate = iblockstate1;
                    blockpos = blockpos1;
                }
            }

            if (iblockstate.getBlock().getRenderType() != -1) {
                Object object = Reflector.getFieldValue(Reflector.RenderBlockOverlayEvent_OverlayType_BLOCK);

                if (!Reflector.callBoolean(Reflector.ForgeEventFactory_renderBlockOverlay, new Object[]{
                        this.mc.thePlayer, Float.valueOf(partialTicks), object, iblockstate, blockpos})) {
                    this.func_178108_a(partialTicks,
                            this.mc.getBlockRendererDispatcher().getBlockModelShapes().getTexture(iblockstate));
                }
            }
        }

        if (!this.mc.thePlayer.isSpectator()) {
            if (this.mc.thePlayer.isInsideOfMaterial(Material.water)
                    && !Reflector.callBoolean(Reflector.ForgeEventFactory_renderWaterOverlay,
                    new Object[]{this.mc.thePlayer, Float.valueOf(partialTicks)})) {
                this.renderWaterOverlayTexture(partialTicks);
            }

            if (this.mc.thePlayer.isBurning() && !Reflector.callBoolean(Reflector.ForgeEventFactory_renderFireOverlay,
                    new Object[]{this.mc.thePlayer, Float.valueOf(partialTicks)})) {
                this.renderFireInFirstPerson(partialTicks);
            }
        }

        GlStateManager.enableAlpha();
    }

    private void func_178108_a(float p_178108_1_, TextureAtlasSprite p_178108_2_) {
        this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        float f = 0.1F;
        GlStateManager.color(0.1F, 0.1F, 0.1F, 0.5F);
        GlStateManager.pushMatrix();
        float f1 = -1.0F;
        float f2 = 1.0F;
        float f3 = -1.0F;
        float f4 = 1.0F;
        float f5 = -0.5F;
        float f6 = p_178108_2_.getMinU();
        float f7 = p_178108_2_.getMaxU();
        float f8 = p_178108_2_.getMinV();
        float f9 = p_178108_2_.getMaxV();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldrenderer.pos(-1.0D, -1.0D, -0.5D).tex((double) f7, (double) f9).endVertex();
        worldrenderer.pos(1.0D, -1.0D, -0.5D).tex((double) f6, (double) f9).endVertex();
        worldrenderer.pos(1.0D, 1.0D, -0.5D).tex((double) f6, (double) f8).endVertex();
        worldrenderer.pos(-1.0D, 1.0D, -0.5D).tex((double) f7, (double) f8).endVertex();
        tessellator.draw();
        GlStateManager.popMatrix();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    }

    /**
     * Renders a texture that warps around based on the direction the player is
     * looking. Texture needs to be bound before being called. Used for the water
     * overlay. Args: parialTickTime
     */
    private void renderWaterOverlayTexture(float p_78448_1_) {
        if (!Config.isShaders() || Shaders.isUnderwaterOverlay()) {
            this.mc.getTextureManager().bindTexture(RES_UNDERWATER_OVERLAY);
            Tessellator tessellator = Tessellator.getInstance();
            WorldRenderer worldrenderer = tessellator.getWorldRenderer();
            float f = this.mc.thePlayer.getBrightness(p_78448_1_);
            GlStateManager.color(f, f, f, 0.5F);
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            GlStateManager.pushMatrix();
            float f1 = 4.0F;
            float f2 = -1.0F;
            float f3 = 1.0F;
            float f4 = -1.0F;
            float f5 = 1.0F;
            float f6 = -0.5F;
            float f7 = -this.mc.thePlayer.rotationYaw / 64.0F;
            float f8 = this.mc.thePlayer.rotationPitch / 64.0F;
            worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
            worldrenderer.pos(-1.0D, -1.0D, -0.5D).tex((double) (4.0F + f7), (double) (4.0F + f8)).endVertex();
            worldrenderer.pos(1.0D, -1.0D, -0.5D).tex((double) (0.0F + f7), (double) (4.0F + f8)).endVertex();
            worldrenderer.pos(1.0D, 1.0D, -0.5D).tex((double) (0.0F + f7), (double) (0.0F + f8)).endVertex();
            worldrenderer.pos(-1.0D, 1.0D, -0.5D).tex((double) (4.0F + f7), (double) (0.0F + f8)).endVertex();
            tessellator.draw();
            GlStateManager.popMatrix();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.disableBlend();
        }
    }

    /**
     * Renders the fire on the screen for first person mode. Arg: partialTickTime
     */
    private void renderFireInFirstPerson(float p_78442_1_) {
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 0.9F);
        GlStateManager.depthFunc(519);
        GlStateManager.depthMask(false);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        float f = 1.0F;

        for (int i = 0; i < 2; ++i) {
            GlStateManager.pushMatrix();
            TextureAtlasSprite textureatlassprite = this.mc.getTextureMapBlocks()
                    .getAtlasSprite("minecraft:blocks/fire_layer_1");
            this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
            float f1 = textureatlassprite.getMinU();
            float f2 = textureatlassprite.getMaxU();
            float f3 = textureatlassprite.getMinV();
            float f4 = textureatlassprite.getMaxV();
            float f5 = (0.0F - f) / 2.0F;
            float f6 = f5 + f;
            float f7 = 0.0F - f / 2.0F;
            float f8 = f7 + f;
            float f9 = -0.5F;
            GlStateManager.translate((float) (-(i * 2 - 1)) * 0.24F, -0.3F, 0.0F);
            GlStateManager.rotate((float) (i * 2 - 1) * 10.0F, 0.0F, 1.0F, 0.0F);
            worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
            worldrenderer.pos((double) f5, (double) f7, (double) f9).tex((double) f2, (double) f4).endVertex();
            worldrenderer.pos((double) f6, (double) f7, (double) f9).tex((double) f1, (double) f4).endVertex();
            worldrenderer.pos((double) f6, (double) f8, (double) f9).tex((double) f1, (double) f3).endVertex();
            worldrenderer.pos((double) f5, (double) f8, (double) f9).tex((double) f2, (double) f3).endVertex();
            tessellator.draw();
            GlStateManager.popMatrix();
        }

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.disableBlend();
        GlStateManager.depthMask(true);
        GlStateManager.depthFunc(515);
    }

    public void updateEquippedItem() {
        this.prevEquippedProgress = this.equippedProgress;
        EntityPlayerSP entityplayersp = this.mc.thePlayer;
        ItemStack itemstack = entityplayersp.inventory.getCurrentItem();
        boolean flag = false;

        if (this.itemToRender != null && itemstack != null) {
            if (!this.itemToRender.getIsItemStackEqual(itemstack)) {
                if (Reflector.ForgeItem_shouldCauseReequipAnimation.exists()) {
                    boolean flag1 = Reflector.callBoolean(this.itemToRender.getItem(),
                            Reflector.ForgeItem_shouldCauseReequipAnimation,
                            new Object[]{this.itemToRender, itemstack,
                                    Boolean.valueOf(this.equippedItemSlot != entityplayersp.inventory.currentItem)});

                    if (!flag1) {
                        this.itemToRender = itemstack;
                        this.equippedItemSlot = entityplayersp.inventory.currentItem;
                        return;
                    }
                }

                flag = true;
            }
        } else if (this.itemToRender == null && itemstack == null) {
            flag = false;
        } else {
            flag = true;
        }

        float f2 = 0.4F;
        float f = flag ? 0.0F : 1.0F;
        float f1 = MathHelper.clamp_float(f - this.equippedProgress, -f2, f2);
        this.equippedProgress += f1;

        if (this.equippedProgress < 0.1F) {
            if (Config.isShaders()) {
                Shaders.setItemToRenderMain(itemstack);
            }

            this.itemToRender = itemstack;
            this.equippedItemSlot = entityplayersp.inventory.currentItem;
        }
    }

    /**
     * Resets equippedProgress
     */
    public void resetEquippedProgress() {
        this.equippedProgress = 0.0F;
    }

    /**
     * Resets equippedProgress
     */
    public void resetEquippedProgress2() {
        this.equippedProgress = 0.0F;
    }

    static final class ItemRenderer$1 {
        static final int[] field_178094_a = new int[EnumAction.values().length];
        private static final String __OBFID = "CL_00002537";

        static {
            try {
                field_178094_a[EnumAction.NONE.ordinal()] = 1;
            } catch (NoSuchFieldError var5) {
                ;
            }

            try {
                field_178094_a[EnumAction.EAT.ordinal()] = 2;
            } catch (NoSuchFieldError var4) {
                ;
            }

            try {
                field_178094_a[EnumAction.DRINK.ordinal()] = 3;
            } catch (NoSuchFieldError var3) {
                ;
            }

            try {
                field_178094_a[EnumAction.BLOCK.ordinal()] = 4;
            } catch (NoSuchFieldError var2) {
                ;
            }

            try {
                field_178094_a[EnumAction.BOW.ordinal()] = 5;
            } catch (NoSuchFieldError var1) {
                ;
            }
        }
    }
}
