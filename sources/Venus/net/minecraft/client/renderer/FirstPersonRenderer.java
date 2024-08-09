/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer;

import com.google.common.base.MoreObjects;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import java.util.Objects;
import mpp.venusfr.functions.api.FunctionRegistry;
import mpp.venusfr.functions.impl.combat.KillAura;
import mpp.venusfr.functions.impl.render.SwingAnimation;
import mpp.venusfr.functions.impl.render.ViewModel;
import mpp.venusfr.venusfr;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.FilledMapItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShootableItem;
import net.minecraft.util.Hand;
import net.minecraft.util.HandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.storage.MapData;
import net.optifine.Config;
import net.optifine.CustomItems;
import net.optifine.reflect.Reflector;
import net.optifine.shaders.Shaders;

public class FirstPersonRenderer {
    private static final RenderType MAP_BACKGROUND = RenderType.getText(new ResourceLocation("textures/map/map_background.png"));
    private static final RenderType MAP_BACKGROUND_CHECKERBOARD = RenderType.getText(new ResourceLocation("textures/map/map_background_checkerboard.png"));
    private final Minecraft mc;
    private ItemStack itemStackMainHand = ItemStack.EMPTY;
    private ItemStack itemStackOffHand = ItemStack.EMPTY;
    private float equippedProgressMainHand;
    private float prevEquippedProgressMainHand;
    private float equippedProgressOffHand;
    private float prevEquippedProgressOffHand;
    private final EntityRendererManager renderManager;
    private final ItemRenderer itemRenderer;

    public FirstPersonRenderer(Minecraft minecraft) {
        this.mc = minecraft;
        this.renderManager = minecraft.getRenderManager();
        this.itemRenderer = minecraft.getItemRenderer();
    }

    public void renderItemSide(LivingEntity livingEntity, ItemStack itemStack, ItemCameraTransforms.TransformType transformType, boolean bl, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n) {
        CustomItems.setRenderOffHand(bl);
        if (!itemStack.isEmpty()) {
            this.itemRenderer.renderItem(livingEntity, itemStack, transformType, bl, matrixStack, iRenderTypeBuffer, livingEntity.world, n, OverlayTexture.NO_OVERLAY);
        }
        CustomItems.setRenderOffHand(false);
    }

    private float getMapAngleFromPitch(float f) {
        float f2 = 1.0f - f / 45.0f + 0.1f;
        f2 = MathHelper.clamp(f2, 0.0f, 1.0f);
        return -MathHelper.cos(f2 * (float)Math.PI) * 0.5f + 0.5f;
    }

    private void renderArm(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, HandSide handSide) {
        this.mc.getTextureManager().bindTexture(this.mc.player.getLocationSkin());
        PlayerRenderer playerRenderer = (PlayerRenderer)this.renderManager.getRenderer(this.mc.player);
        matrixStack.push();
        float f = handSide == HandSide.RIGHT ? 1.0f : -1.0f;
        matrixStack.rotate(Vector3f.YP.rotationDegrees(92.0f));
        matrixStack.rotate(Vector3f.XP.rotationDegrees(45.0f));
        matrixStack.rotate(Vector3f.ZP.rotationDegrees(f * -41.0f));
        matrixStack.translate(f * 0.3f, -1.1f, 0.45f);
        if (handSide == HandSide.RIGHT) {
            playerRenderer.renderRightArm(matrixStack, iRenderTypeBuffer, n, this.mc.player);
        } else {
            playerRenderer.renderLeftArm(matrixStack, iRenderTypeBuffer, n, this.mc.player);
        }
        matrixStack.pop();
    }

    private void renderMapFirstPersonSide(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, float f, HandSide handSide, float f2, ItemStack itemStack) {
        float f3 = handSide == HandSide.RIGHT ? 1.0f : -1.0f;
        matrixStack.translate(f3 * 0.125f, -0.125, 0.0);
        if (!this.mc.player.isInvisible()) {
            matrixStack.push();
            matrixStack.rotate(Vector3f.ZP.rotationDegrees(f3 * 10.0f));
            this.renderArmFirstPerson(matrixStack, iRenderTypeBuffer, n, f, f2, handSide);
            matrixStack.pop();
        }
        matrixStack.push();
        matrixStack.translate(f3 * 0.51f, -0.08f + f * -1.2f, -0.75);
        float f4 = MathHelper.sqrt(f2);
        float f5 = MathHelper.sin(f4 * (float)Math.PI);
        float f6 = -0.5f * f5;
        float f7 = 0.4f * MathHelper.sin(f4 * ((float)Math.PI * 2));
        float f8 = -0.3f * MathHelper.sin(f2 * (float)Math.PI);
        matrixStack.translate(f3 * f6, f7 - 0.3f * f5, f8);
        matrixStack.rotate(Vector3f.XP.rotationDegrees(f5 * -45.0f));
        matrixStack.rotate(Vector3f.YP.rotationDegrees(f3 * f5 * -30.0f));
        this.renderMapFirstPerson(matrixStack, iRenderTypeBuffer, n, itemStack);
        matrixStack.pop();
    }

    private void renderMapFirstPerson(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, float f, float f2, float f3) {
        float f4 = MathHelper.sqrt(f3);
        float f5 = -0.2f * MathHelper.sin(f3 * (float)Math.PI);
        float f6 = -0.4f * MathHelper.sin(f4 * (float)Math.PI);
        matrixStack.translate(0.0, -f5 / 2.0f, f6);
        float f7 = this.getMapAngleFromPitch(f);
        matrixStack.translate(0.0, 0.04f + f2 * -1.2f + f7 * -0.5f, -0.72f);
        matrixStack.rotate(Vector3f.XP.rotationDegrees(f7 * -85.0f));
        if (!this.mc.player.isInvisible()) {
            matrixStack.push();
            matrixStack.rotate(Vector3f.YP.rotationDegrees(90.0f));
            this.renderArm(matrixStack, iRenderTypeBuffer, n, HandSide.RIGHT);
            this.renderArm(matrixStack, iRenderTypeBuffer, n, HandSide.LEFT);
            matrixStack.pop();
        }
        float f8 = MathHelper.sin(f4 * (float)Math.PI);
        matrixStack.rotate(Vector3f.XP.rotationDegrees(f8 * 20.0f));
        matrixStack.scale(2.0f, 2.0f, 2.0f);
        this.renderMapFirstPerson(matrixStack, iRenderTypeBuffer, n, this.itemStackMainHand);
    }

    private void renderMapFirstPerson(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, ItemStack itemStack) {
        matrixStack.rotate(Vector3f.YP.rotationDegrees(180.0f));
        matrixStack.rotate(Vector3f.ZP.rotationDegrees(180.0f));
        matrixStack.scale(0.38f, 0.38f, 0.38f);
        matrixStack.translate(-0.5, -0.5, 0.0);
        matrixStack.scale(0.0078125f, 0.0078125f, 0.0078125f);
        MapData mapData = FilledMapItem.getMapData(itemStack, this.mc.world);
        IVertexBuilder iVertexBuilder = iRenderTypeBuffer.getBuffer(mapData == null ? MAP_BACKGROUND : MAP_BACKGROUND_CHECKERBOARD);
        Matrix4f matrix4f = matrixStack.getLast().getMatrix();
        iVertexBuilder.pos(matrix4f, -7.0f, 135.0f, 0.0f).color(255, 255, 255, 255).tex(0.0f, 1.0f).lightmap(n).endVertex();
        iVertexBuilder.pos(matrix4f, 135.0f, 135.0f, 0.0f).color(255, 255, 255, 255).tex(1.0f, 1.0f).lightmap(n).endVertex();
        iVertexBuilder.pos(matrix4f, 135.0f, -7.0f, 0.0f).color(255, 255, 255, 255).tex(1.0f, 0.0f).lightmap(n).endVertex();
        iVertexBuilder.pos(matrix4f, -7.0f, -7.0f, 0.0f).color(255, 255, 255, 255).tex(0.0f, 0.0f).lightmap(n).endVertex();
        if (mapData != null) {
            this.mc.gameRenderer.getMapItemRenderer().renderMap(matrixStack, iRenderTypeBuffer, mapData, false, n);
        }
    }

    private void renderArmFirstPerson(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, float f, float f2, HandSide handSide) {
        boolean bl = handSide != HandSide.LEFT;
        float f3 = bl ? 1.0f : -1.0f;
        float f4 = MathHelper.sqrt(f2);
        float f5 = -0.3f * MathHelper.sin(f4 * (float)Math.PI);
        float f6 = 0.4f * MathHelper.sin(f4 * ((float)Math.PI * 2));
        float f7 = -0.4f * MathHelper.sin(f2 * (float)Math.PI);
        matrixStack.translate(f3 * (f5 + 0.64000005f), f6 + -0.6f + f * -0.6f, f7 + -0.71999997f);
        matrixStack.rotate(Vector3f.YP.rotationDegrees(f3 * 45.0f));
        float f8 = MathHelper.sin(f2 * f2 * (float)Math.PI);
        float f9 = MathHelper.sin(f4 * (float)Math.PI);
        matrixStack.rotate(Vector3f.YP.rotationDegrees(f3 * f9 * 70.0f));
        matrixStack.rotate(Vector3f.ZP.rotationDegrees(f3 * f8 * -20.0f));
        ClientPlayerEntity clientPlayerEntity = this.mc.player;
        this.mc.getTextureManager().bindTexture(clientPlayerEntity.getLocationSkin());
        matrixStack.translate(f3 * -1.0f, 3.6f, 3.5);
        matrixStack.rotate(Vector3f.ZP.rotationDegrees(f3 * 120.0f));
        matrixStack.rotate(Vector3f.XP.rotationDegrees(200.0f));
        matrixStack.rotate(Vector3f.YP.rotationDegrees(f3 * -135.0f));
        matrixStack.translate(f3 * 5.6f, 0.0, 0.0);
        PlayerRenderer playerRenderer = (PlayerRenderer)this.renderManager.getRenderer(clientPlayerEntity);
        if (bl) {
            playerRenderer.renderRightArm(matrixStack, iRenderTypeBuffer, n, clientPlayerEntity);
        } else {
            playerRenderer.renderLeftArm(matrixStack, iRenderTypeBuffer, n, clientPlayerEntity);
        }
    }

    private void transformEatFirstPerson(MatrixStack matrixStack, float f, HandSide handSide, ItemStack itemStack) {
        float f2;
        float f3 = (float)this.mc.player.getItemInUseCount() - f + 1.0f;
        float f4 = f3 / (float)itemStack.getUseDuration();
        if (f4 < 0.8f) {
            f2 = MathHelper.abs(MathHelper.cos(f3 / 4.0f * (float)Math.PI) * 0.1f);
            matrixStack.translate(0.0, f2, 0.0);
        }
        f2 = 1.0f - (float)Math.pow(f4, 27.0);
        int n = handSide == HandSide.RIGHT ? 1 : -1;
        matrixStack.translate(f2 * 0.6f * (float)n, f2 * -0.5f, f2 * 0.0f);
        matrixStack.rotate(Vector3f.YP.rotationDegrees((float)n * f2 * 90.0f));
        matrixStack.rotate(Vector3f.XP.rotationDegrees(f2 * 10.0f));
        matrixStack.rotate(Vector3f.ZP.rotationDegrees((float)n * f2 * 30.0f));
    }

    private void transformFirstPerson(MatrixStack matrixStack, HandSide handSide, float f) {
        FunctionRegistry functionRegistry = venusfr.getInstance().getFunctionRegistry();
        SwingAnimation swingAnimation = functionRegistry.getSwingAnimation();
        if (swingAnimation.isState()) {
            int n = handSide == HandSide.RIGHT ? 1 : -1;
            float f2 = ((Float)swingAnimation.swingPower.get()).floatValue() * 10.0f;
            float f3 = MathHelper.sin(f * f * (float)Math.PI);
            matrixStack.rotate(Vector3f.YP.rotationDegrees((float)n * (45.0f + f3 * (-f2 / 4.0f))));
            float f4 = MathHelper.sin(MathHelper.sqrt(f) * (float)Math.PI);
            matrixStack.rotate(Vector3f.ZP.rotationDegrees((float)n * f4 * -(f2 / 4.0f)));
            matrixStack.rotate(Vector3f.XP.rotationDegrees(f4 * -f2));
            matrixStack.rotate(Vector3f.YP.rotationDegrees((float)n * -45.0f));
        } else {
            int n = handSide == HandSide.RIGHT ? 1 : -1;
            float f5 = MathHelper.sin(f * f * (float)Math.PI);
            matrixStack.rotate(Vector3f.YP.rotationDegrees((float)n * (45.0f + f5 * -20.0f)));
            float f6 = MathHelper.sin(MathHelper.sqrt(f) * (float)Math.PI);
            matrixStack.rotate(Vector3f.ZP.rotationDegrees((float)n * f6 * -20.0f));
            matrixStack.rotate(Vector3f.XP.rotationDegrees(f6 * -80.0f));
            matrixStack.rotate(Vector3f.YP.rotationDegrees((float)n * -45.0f));
        }
    }

    private void transformSideFirstPerson(MatrixStack matrixStack, HandSide handSide, float f) {
        int n;
        FunctionRegistry functionRegistry = venusfr.getInstance().getFunctionRegistry();
        SwingAnimation swingAnimation = functionRegistry.getSwingAnimation();
        KillAura killAura = functionRegistry.getKillAura();
        int n2 = n = handSide == HandSide.RIGHT ? 1 : -1;
        if (((Boolean)swingAnimation.onlyAura.get()).booleanValue() && killAura.getTarget() == null) {
            matrixStack.translate((float)n * 0.56f, -0.52f + f * -0.6f, -0.72f);
            return;
        }
        matrixStack.translate((float)n * 0.56f, -0.52f + f * (swingAnimation.isState() ? 0.0f : -0.6f), -0.72f);
    }

    public void renderItemInFirstPerson(float f, MatrixStack matrixStack, IRenderTypeBuffer.Impl impl, ClientPlayerEntity clientPlayerEntity, int n) {
        float f2;
        float f3 = clientPlayerEntity.getSwingProgress(f);
        Hand hand = MoreObjects.firstNonNull(clientPlayerEntity.swingingHand, Hand.MAIN_HAND);
        float f4 = MathHelper.lerp(f, clientPlayerEntity.prevRotationPitch, clientPlayerEntity.rotationPitch);
        boolean bl = true;
        boolean bl2 = true;
        if (clientPlayerEntity.isHandActive()) {
            ItemStack itemStack;
            var11_11 = clientPlayerEntity.getActiveItemStack();
            if (var11_11.getItem() instanceof ShootableItem) {
                bl = clientPlayerEntity.getActiveHand() == Hand.MAIN_HAND;
                boolean bl3 = bl2 = !bl;
            }
            if ((var12_13 = clientPlayerEntity.getActiveHand()) == Hand.MAIN_HAND && (itemStack = clientPlayerEntity.getHeldItemOffhand()).getItem() instanceof CrossbowItem && CrossbowItem.isCharged(itemStack)) {
                bl2 = false;
            }
        } else {
            var11_11 = clientPlayerEntity.getHeldItemMainhand();
            var12_13 = clientPlayerEntity.getHeldItemOffhand();
            if (var11_11.getItem() instanceof CrossbowItem && CrossbowItem.isCharged(var11_11)) {
                boolean bl4 = bl2 = !bl;
            }
            if (((ItemStack)var12_13).getItem() instanceof CrossbowItem && CrossbowItem.isCharged((ItemStack)var12_13)) {
                bl = !var11_11.isEmpty();
                bl2 = !bl;
            }
        }
        float f5 = MathHelper.lerp(f, clientPlayerEntity.prevRenderArmPitch, clientPlayerEntity.renderArmPitch);
        float f6 = MathHelper.lerp(f, clientPlayerEntity.prevRenderArmYaw, clientPlayerEntity.renderArmYaw);
        matrixStack.rotate(Vector3f.XP.rotationDegrees((clientPlayerEntity.getPitch(f) - f5) * 0.1f));
        matrixStack.rotate(Vector3f.YP.rotationDegrees((clientPlayerEntity.getYaw(f) - f6) * 0.1f));
        if (bl) {
            float f7 = hand == Hand.MAIN_HAND ? f3 : 0.0f;
            f2 = 1.0f - MathHelper.lerp(f, this.prevEquippedProgressMainHand, this.equippedProgressMainHand);
            if (!Reflector.ForgeHooksClient_renderSpecificFirstPersonHand.exists() || !Reflector.callBoolean(Reflector.ForgeHooksClient_renderSpecificFirstPersonHand, new Object[]{Hand.MAIN_HAND, matrixStack, impl, n, Float.valueOf(f), Float.valueOf(f4), Float.valueOf(f7), Float.valueOf(f2), this.itemStackMainHand})) {
                this.renderItemInFirstPerson(clientPlayerEntity, f, f4, Hand.MAIN_HAND, f7, this.itemStackMainHand, f2, matrixStack, impl, n);
            }
        }
        if (bl2) {
            float f8 = hand == Hand.OFF_HAND ? f3 : 0.0f;
            f2 = 1.0f - MathHelper.lerp(f, this.prevEquippedProgressOffHand, this.equippedProgressOffHand);
            if (!Reflector.ForgeHooksClient_renderSpecificFirstPersonHand.exists() || !Reflector.callBoolean(Reflector.ForgeHooksClient_renderSpecificFirstPersonHand, new Object[]{Hand.OFF_HAND, matrixStack, impl, n, Float.valueOf(f), Float.valueOf(f4), Float.valueOf(f8), Float.valueOf(f2), this.itemStackOffHand})) {
                this.renderItemInFirstPerson(clientPlayerEntity, f, f4, Hand.OFF_HAND, f8, this.itemStackOffHand, f2, matrixStack, impl, n);
            }
        }
        impl.finish();
    }

    private void renderItemInFirstPerson(AbstractClientPlayerEntity abstractClientPlayerEntity, float f, float f2, Hand hand, float f3, ItemStack itemStack, float f4, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n) {
        if (!Config.isShaders() || !Shaders.isSkipRenderHand(hand)) {
            boolean bl = hand == Hand.MAIN_HAND;
            HandSide handSide = bl ? abstractClientPlayerEntity.getPrimaryHand() : abstractClientPlayerEntity.getPrimaryHand().opposite();
            matrixStack.push();
            if (itemStack.isEmpty()) {
                if (bl && !abstractClientPlayerEntity.isInvisible()) {
                    this.renderArmFirstPerson(matrixStack, iRenderTypeBuffer, n, f4, f3, handSide);
                }
            } else if (itemStack.getItem() instanceof FilledMapItem) {
                if (bl && this.itemStackOffHand.isEmpty()) {
                    this.renderMapFirstPerson(matrixStack, iRenderTypeBuffer, n, f2, f4, f3);
                } else {
                    this.renderMapFirstPersonSide(matrixStack, iRenderTypeBuffer, n, f4, handSide, f3, itemStack);
                }
            } else if (itemStack.getItem() instanceof CrossbowItem) {
                int n2;
                boolean bl2 = CrossbowItem.isCharged(itemStack);
                boolean bl3 = handSide == HandSide.RIGHT;
                int n3 = n2 = bl3 ? 1 : -1;
                if (abstractClientPlayerEntity.isHandActive() && abstractClientPlayerEntity.getItemInUseCount() > 0 && abstractClientPlayerEntity.getActiveHand() == hand) {
                    this.transformSideFirstPerson(matrixStack, handSide, f4);
                    matrixStack.translate((float)n2 * -0.4785682f, -0.094387f, 0.05731530860066414);
                    matrixStack.rotate(Vector3f.XP.rotationDegrees(-11.935f));
                    matrixStack.rotate(Vector3f.YP.rotationDegrees((float)n2 * 65.3f));
                    matrixStack.rotate(Vector3f.ZP.rotationDegrees((float)n2 * -9.785f));
                    float f5 = (float)itemStack.getUseDuration() - ((float)this.mc.player.getItemInUseCount() - f + 1.0f);
                    float f6 = f5 / (float)CrossbowItem.getChargeTime(itemStack);
                    if (f6 > 1.0f) {
                        f6 = 1.0f;
                    }
                    if (f6 > 0.1f) {
                        float f7 = MathHelper.sin((f5 - 0.1f) * 1.3f);
                        float f8 = f6 - 0.1f;
                        float f9 = f7 * f8;
                        matrixStack.translate(f9 * 0.0f, f9 * 0.004f, f9 * 0.0f);
                    }
                    matrixStack.translate(f6 * 0.0f, f6 * 0.0f, f6 * 0.04f);
                    matrixStack.scale(1.0f, 1.0f, 1.0f + f6 * 0.2f);
                    matrixStack.rotate(Vector3f.YN.rotationDegrees((float)n2 * 45.0f));
                } else {
                    float f10 = -0.4f * MathHelper.sin(MathHelper.sqrt(f3) * (float)Math.PI);
                    float f11 = 0.2f * MathHelper.sin(MathHelper.sqrt(f3) * ((float)Math.PI * 2));
                    float f12 = -0.2f * MathHelper.sin(f3 * (float)Math.PI);
                    matrixStack.translate((float)n2 * f10, f11, f12);
                    this.transformSideFirstPerson(matrixStack, handSide, f4);
                    this.transformFirstPerson(matrixStack, handSide, f3);
                    if (bl2 && f3 < 0.001f) {
                        matrixStack.translate((float)n2 * -0.641864f, 0.0, 0.0);
                        matrixStack.rotate(Vector3f.YP.rotationDegrees((float)n2 * 10.0f));
                    }
                }
                this.renderItemSide(abstractClientPlayerEntity, itemStack, bl3 ? ItemCameraTransforms.TransformType.FIRST_PERSON_RIGHT_HAND : ItemCameraTransforms.TransformType.FIRST_PERSON_LEFT_HAND, !bl3, matrixStack, iRenderTypeBuffer, n);
            } else {
                boolean bl4;
                FunctionRegistry functionRegistry = venusfr.getInstance().getFunctionRegistry();
                ViewModel viewModel = functionRegistry.getViewModel();
                boolean bl5 = bl4 = handSide == HandSide.RIGHT;
                if (viewModel.isState()) {
                    if (bl4) {
                        matrixStack.translate(((Float)viewModel.right_x.get()).floatValue(), ((Float)viewModel.right_y.get()).floatValue(), ((Float)viewModel.right_z.get()).floatValue());
                    } else {
                        matrixStack.translate(((Float)viewModel.left_x.get()).floatValue(), ((Float)viewModel.left_y.get()).floatValue(), ((Float)viewModel.left_z.get()).floatValue());
                    }
                }
                if (abstractClientPlayerEntity.isHandActive() && abstractClientPlayerEntity.getItemInUseCount() > 0 && abstractClientPlayerEntity.getActiveHand() == hand) {
                    int n4 = bl4 ? 1 : -1;
                    switch (1.$SwitchMap$net$minecraft$item$UseAction[itemStack.getUseAction().ordinal()]) {
                        case 1: {
                            this.transformSideFirstPerson(matrixStack, handSide, f4);
                            break;
                        }
                        case 2: 
                        case 3: {
                            this.transformEatFirstPerson(matrixStack, f, handSide, itemStack);
                            this.transformSideFirstPerson(matrixStack, handSide, f4);
                            break;
                        }
                        case 4: {
                            this.transformSideFirstPerson(matrixStack, handSide, f4);
                            break;
                        }
                        case 5: {
                            this.transformSideFirstPerson(matrixStack, handSide, f4);
                            matrixStack.translate((float)n4 * -0.2785682f, 0.18344387412071228, 0.15731531381607056);
                            matrixStack.rotate(Vector3f.XP.rotationDegrees(-13.935f));
                            matrixStack.rotate(Vector3f.YP.rotationDegrees((float)n4 * 35.3f));
                            matrixStack.rotate(Vector3f.ZP.rotationDegrees((float)n4 * -9.785f));
                            float f13 = (float)itemStack.getUseDuration() - ((float)this.mc.player.getItemInUseCount() - f + 1.0f);
                            float f14 = f13 / 20.0f;
                            f14 = (f14 * f14 + f14 * 2.0f) / 3.0f;
                            if (f14 > 1.0f) {
                                f14 = 1.0f;
                            }
                            if (f14 > 0.1f) {
                                float f15 = MathHelper.sin((f13 - 0.1f) * 1.3f);
                                float f16 = f14 - 0.1f;
                                float f17 = f15 * f16;
                                matrixStack.translate(f17 * 0.0f, f17 * 0.004f, f17 * 0.0f);
                            }
                            matrixStack.translate(f14 * 0.0f, f14 * 0.0f, f14 * 0.04f);
                            matrixStack.scale(1.0f, 1.0f, 1.0f + f14 * 0.2f);
                            matrixStack.rotate(Vector3f.YN.rotationDegrees((float)n4 * 45.0f));
                            break;
                        }
                        case 6: {
                            this.transformSideFirstPerson(matrixStack, handSide, f4);
                            matrixStack.translate((float)n4 * -0.5f, 0.7f, 0.1f);
                            matrixStack.rotate(Vector3f.XP.rotationDegrees(-55.0f));
                            matrixStack.rotate(Vector3f.YP.rotationDegrees((float)n4 * 35.3f));
                            matrixStack.rotate(Vector3f.ZP.rotationDegrees((float)n4 * -9.785f));
                            float f18 = (float)itemStack.getUseDuration() - ((float)this.mc.player.getItemInUseCount() - f + 1.0f);
                            float f19 = f18 / 10.0f;
                            if (f19 > 1.0f) {
                                f19 = 1.0f;
                            }
                            if (f19 > 0.1f) {
                                float f20 = MathHelper.sin((f18 - 0.1f) * 1.3f);
                                float f21 = f19 - 0.1f;
                                float f22 = f20 * f21;
                                matrixStack.translate(f22 * 0.0f, f22 * 0.004f, f22 * 0.0f);
                            }
                            matrixStack.translate(0.0, 0.0, f19 * 0.2f);
                            matrixStack.scale(1.0f, 1.0f, 1.0f + f19 * 0.2f);
                            matrixStack.rotate(Vector3f.YN.rotationDegrees((float)n4 * 45.0f));
                        }
                    }
                } else if (abstractClientPlayerEntity.isSpinAttacking()) {
                    this.transformSideFirstPerson(matrixStack, handSide, f4);
                    int n5 = bl4 ? 1 : -1;
                    matrixStack.translate((float)n5 * -0.4f, 0.8f, 0.3f);
                    matrixStack.rotate(Vector3f.YP.rotationDegrees((float)n5 * 65.0f));
                    matrixStack.rotate(Vector3f.ZP.rotationDegrees((float)n5 * -85.0f));
                } else {
                    int n6;
                    SwingAnimation swingAnimation = functionRegistry.getSwingAnimation();
                    float f23 = -0.4f * MathHelper.sin(MathHelper.sqrt(f3) * (float)Math.PI);
                    float f24 = 0.2f * MathHelper.sin(MathHelper.sqrt(f3) * ((float)Math.PI * 2));
                    float f25 = -0.2f * MathHelper.sin(f3 * (float)Math.PI);
                    int n7 = n6 = bl4 ? 1 : -1;
                    if (!swingAnimation.isState()) {
                        matrixStack.translate((float)n6 * f23, f24, f25);
                    }
                    this.transformSideFirstPerson(matrixStack, handSide, f4);
                    if (swingAnimation.isState() && handSide == HandSide.RIGHT) {
                        swingAnimation.animationProcess(matrixStack, f3, () -> this.lambda$renderItemInFirstPerson$0(matrixStack, handSide, f3));
                    } else {
                        this.transformFirstPerson(matrixStack, handSide, f3);
                    }
                }
                this.renderItemSide(abstractClientPlayerEntity, itemStack, bl4 ? ItemCameraTransforms.TransformType.FIRST_PERSON_RIGHT_HAND : ItemCameraTransforms.TransformType.FIRST_PERSON_LEFT_HAND, !bl4, matrixStack, iRenderTypeBuffer, n);
            }
            matrixStack.pop();
        }
    }

    public void tick() {
        this.prevEquippedProgressMainHand = this.equippedProgressMainHand;
        this.prevEquippedProgressOffHand = this.equippedProgressOffHand;
        ClientPlayerEntity clientPlayerEntity = this.mc.player;
        ItemStack itemStack = clientPlayerEntity.getHeldItemMainhand();
        ItemStack itemStack2 = clientPlayerEntity.getHeldItemOffhand();
        if (ItemStack.areItemStacksEqual(this.itemStackMainHand, itemStack)) {
            this.itemStackMainHand = itemStack;
        }
        if (ItemStack.areItemStacksEqual(this.itemStackOffHand, itemStack2)) {
            this.itemStackOffHand = itemStack2;
        }
        if (clientPlayerEntity.isRowingBoat()) {
            this.equippedProgressMainHand = MathHelper.clamp(this.equippedProgressMainHand - 0.4f, 0.0f, 1.0f);
            this.equippedProgressOffHand = MathHelper.clamp(this.equippedProgressOffHand - 0.4f, 0.0f, 1.0f);
        } else {
            float f = clientPlayerEntity.getCooledAttackStrength(1.0f);
            if (Reflector.ForgeHooksClient_shouldCauseReequipAnimation.exists()) {
                boolean bl = Reflector.callBoolean(Reflector.ForgeHooksClient_shouldCauseReequipAnimation, this.itemStackMainHand, itemStack, clientPlayerEntity.inventory.currentItem);
                boolean bl2 = Reflector.callBoolean(Reflector.ForgeHooksClient_shouldCauseReequipAnimation, this.itemStackOffHand, itemStack2, -1);
                if (!bl && !Objects.equals(this.itemStackMainHand, itemStack)) {
                    this.itemStackMainHand = itemStack;
                }
                if (!bl2 && !Objects.equals(this.itemStackOffHand, itemStack2)) {
                    this.itemStackOffHand = itemStack2;
                }
            }
            this.equippedProgressMainHand += MathHelper.clamp((this.itemStackMainHand == itemStack ? f * f * f : 0.0f) - this.equippedProgressMainHand, -0.4f, 0.4f);
            this.equippedProgressOffHand += MathHelper.clamp((float)(this.itemStackOffHand == itemStack2 ? 1 : 0) - this.equippedProgressOffHand, -0.4f, 0.4f);
        }
        if (this.equippedProgressMainHand < 0.1f) {
            this.itemStackMainHand = itemStack;
            if (Config.isShaders()) {
                Shaders.setItemToRenderMain(this.itemStackMainHand);
            }
        }
        if (this.equippedProgressOffHand < 0.1f) {
            this.itemStackOffHand = itemStack2;
            if (Config.isShaders()) {
                Shaders.setItemToRenderOff(this.itemStackOffHand);
            }
        }
    }

    public void resetEquippedProgress(Hand hand) {
        if (hand == Hand.MAIN_HAND) {
            this.equippedProgressMainHand = 0.0f;
        } else {
            this.equippedProgressOffHand = 0.0f;
        }
    }

    private void lambda$renderItemInFirstPerson$0(MatrixStack matrixStack, HandSide handSide, float f) {
        this.transformFirstPerson(matrixStack, handSide, f);
    }
}

