//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.minecraft.client.renderer;

import com.google.common.base.MoreObjects;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import src.Wiksi.Wiksi;
import src.Wiksi.functions.api.FunctionRegistry;
import src.Wiksi.functions.impl.combat.KillAura;
import src.Wiksi.functions.impl.render.SwingAnimation;
import src.Wiksi.functions.impl.render.ViewModel;
import java.util.Objects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
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
    private ItemStack itemStackMainHand;
    private ItemStack itemStackOffHand;
    private float equippedProgressMainHand;
    private float prevEquippedProgressMainHand;
    private float equippedProgressOffHand;
    private float prevEquippedProgressOffHand;
    private final EntityRendererManager renderManager;
    private final ItemRenderer itemRenderer;

    public FirstPersonRenderer(Minecraft mcIn) {
        this.itemStackMainHand = ItemStack.EMPTY;
        this.itemStackOffHand = ItemStack.EMPTY;
        this.mc = mcIn;
        this.renderManager = mcIn.getRenderManager();
        this.itemRenderer = mcIn.getItemRenderer();
    }

    public void renderItemSide(LivingEntity livingEntityIn, ItemStack itemStackIn, ItemCameraTransforms.TransformType transformTypeIn, boolean leftHand, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn) {
        CustomItems.setRenderOffHand(leftHand);
        if (!itemStackIn.isEmpty()) {
            this.itemRenderer.renderItem(livingEntityIn, itemStackIn, transformTypeIn, leftHand, matrixStackIn, bufferIn, livingEntityIn.world, combinedLightIn, OverlayTexture.NO_OVERLAY);
        }

        CustomItems.setRenderOffHand(false);
    }

    private float getMapAngleFromPitch(float pitch) {
        float f = 1.0F - pitch / 45.0F + 0.1F;
        f = MathHelper.clamp(f, 0.0F, 1.0F);
        return -MathHelper.cos(f * 3.1415927F) * 0.5F + 0.5F;
    }

    private void renderArm(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, HandSide side) {
        this.mc.getTextureManager().bindTexture(this.mc.player.getLocationSkin());
        PlayerRenderer playerrenderer = (PlayerRenderer)this.renderManager.getRenderer(this.mc.player);
        matrixStackIn.push();
        float f = side == HandSide.RIGHT ? 1.0F : -1.0F;
        matrixStackIn.rotate(Vector3f.YP.rotationDegrees(92.0F));
        matrixStackIn.rotate(Vector3f.XP.rotationDegrees(45.0F));
        matrixStackIn.rotate(Vector3f.ZP.rotationDegrees(f * -41.0F));
        matrixStackIn.translate((double)(f * 0.3F), -1.100000023841858, 0.44999998807907104);
        if (side == HandSide.RIGHT) {
            playerrenderer.renderRightArm(matrixStackIn, bufferIn, combinedLightIn, this.mc.player);
        } else {
            playerrenderer.renderLeftArm(matrixStackIn, bufferIn, combinedLightIn, this.mc.player);
        }

        matrixStackIn.pop();
    }

    private void renderMapFirstPersonSide(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, float equippedProgress, HandSide handIn, float swingProgress, ItemStack stack) {
        float f = handIn == HandSide.RIGHT ? 1.0F : -1.0F;
        matrixStackIn.translate((double)(f * 0.125F), -0.125, 0.0);
        if (!this.mc.player.isInvisible()) {
            matrixStackIn.push();
            matrixStackIn.rotate(Vector3f.ZP.rotationDegrees(f * 10.0F));
            this.renderArmFirstPerson(matrixStackIn, bufferIn, combinedLightIn, equippedProgress, swingProgress, handIn);
            matrixStackIn.pop();
        }

        matrixStackIn.push();
        matrixStackIn.translate((double)(f * 0.51F), (double)(-0.08F + equippedProgress * -1.2F), -0.75);
        float f1 = MathHelper.sqrt(swingProgress);
        float f2 = MathHelper.sin(f1 * 3.1415927F);
        float f3 = -0.5F * f2;
        float f4 = 0.4F * MathHelper.sin(f1 * 6.2831855F);
        float f5 = -0.3F * MathHelper.sin(swingProgress * 3.1415927F);
        matrixStackIn.translate((double)(f * f3), (double)(f4 - 0.3F * f2), (double)f5);
        matrixStackIn.rotate(Vector3f.XP.rotationDegrees(f2 * -45.0F));
        matrixStackIn.rotate(Vector3f.YP.rotationDegrees(f * f2 * -30.0F));
        this.renderMapFirstPerson(matrixStackIn, bufferIn, combinedLightIn, stack);
        matrixStackIn.pop();
    }

    private void renderMapFirstPerson(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, float pitch, float equippedProgress, float swingProgress) {
        float f = MathHelper.sqrt(swingProgress);
        float f1 = -0.2F * MathHelper.sin(swingProgress * 3.1415927F);
        float f2 = -0.4F * MathHelper.sin(f * 3.1415927F);
        matrixStackIn.translate(0.0, (double)(-f1 / 2.0F), (double)f2);
        float f3 = this.getMapAngleFromPitch(pitch);
        matrixStackIn.translate(0.0, (double)(0.04F + equippedProgress * -1.2F + f3 * -0.5F), -0.7200000286102295);
        matrixStackIn.rotate(Vector3f.XP.rotationDegrees(f3 * -85.0F));
        if (!this.mc.player.isInvisible()) {
            matrixStackIn.push();
            matrixStackIn.rotate(Vector3f.YP.rotationDegrees(90.0F));
            this.renderArm(matrixStackIn, bufferIn, combinedLightIn, HandSide.RIGHT);
            this.renderArm(matrixStackIn, bufferIn, combinedLightIn, HandSide.LEFT);
            matrixStackIn.pop();
        }

        float f4 = MathHelper.sin(f * 3.1415927F);
        matrixStackIn.rotate(Vector3f.XP.rotationDegrees(f4 * 20.0F));
        matrixStackIn.scale(2.0F, 2.0F, 2.0F);
        this.renderMapFirstPerson(matrixStackIn, bufferIn, combinedLightIn, this.itemStackMainHand);
    }

    private void renderMapFirstPerson(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, ItemStack stack) {
        matrixStackIn.rotate(Vector3f.YP.rotationDegrees(180.0F));
        matrixStackIn.rotate(Vector3f.ZP.rotationDegrees(180.0F));
        matrixStackIn.scale(0.38F, 0.38F, 0.38F);
        matrixStackIn.translate(-0.5, -0.5, 0.0);
        matrixStackIn.scale(0.0078125F, 0.0078125F, 0.0078125F);
        MapData mapdata = FilledMapItem.getMapData(stack, this.mc.world);
        IVertexBuilder ivertexbuilder = bufferIn.getBuffer(mapdata == null ? MAP_BACKGROUND : MAP_BACKGROUND_CHECKERBOARD);
        Matrix4f matrix4f = matrixStackIn.getLast().getMatrix();
        ivertexbuilder.pos(matrix4f, -7.0F, 135.0F, 0.0F).color(255, 255, 255, 255).tex(0.0F, 1.0F).lightmap(combinedLightIn).endVertex();
        ivertexbuilder.pos(matrix4f, 135.0F, 135.0F, 0.0F).color(255, 255, 255, 255).tex(1.0F, 1.0F).lightmap(combinedLightIn).endVertex();
        ivertexbuilder.pos(matrix4f, 135.0F, -7.0F, 0.0F).color(255, 255, 255, 255).tex(1.0F, 0.0F).lightmap(combinedLightIn).endVertex();
        ivertexbuilder.pos(matrix4f, -7.0F, -7.0F, 0.0F).color(255, 255, 255, 255).tex(0.0F, 0.0F).lightmap(combinedLightIn).endVertex();
        if (mapdata != null) {
            this.mc.gameRenderer.getMapItemRenderer().renderMap(matrixStackIn, bufferIn, mapdata, false, combinedLightIn);
        }

    }

    private void renderArmFirstPerson(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, float equippedProgress, float swingProgress, HandSide side) {
        boolean flag = side != HandSide.LEFT;
        float f = flag ? 1.0F : -1.0F;
        float f1 = MathHelper.sqrt(swingProgress);
        float f2 = -0.3F * MathHelper.sin(f1 * 3.1415927F);
        float f3 = 0.4F * MathHelper.sin(f1 * 6.2831855F);
        float f4 = -0.4F * MathHelper.sin(swingProgress * 3.1415927F);
        matrixStackIn.translate((double)(f * (f2 + 0.64000005F)), (double)(f3 + -0.6F + equippedProgress * -0.6F), (double)(f4 + -0.71999997F));
        matrixStackIn.rotate(Vector3f.YP.rotationDegrees(f * 45.0F));
        float f5 = MathHelper.sin(swingProgress * swingProgress * 3.1415927F);
        float f6 = MathHelper.sin(f1 * 3.1415927F);
        matrixStackIn.rotate(Vector3f.YP.rotationDegrees(f * f6 * 70.0F));
        matrixStackIn.rotate(Vector3f.ZP.rotationDegrees(f * f5 * -20.0F));
        AbstractClientPlayerEntity abstractclientplayerentity = this.mc.player;
        this.mc.getTextureManager().bindTexture(abstractclientplayerentity.getLocationSkin());
        matrixStackIn.translate((double)(f * -1.0F), 3.5999999046325684, 3.5);
        matrixStackIn.rotate(Vector3f.ZP.rotationDegrees(f * 120.0F));
        matrixStackIn.rotate(Vector3f.XP.rotationDegrees(200.0F));
        matrixStackIn.rotate(Vector3f.YP.rotationDegrees(f * -135.0F));
        matrixStackIn.translate((double)(f * 5.6F), 0.0, 0.0);
        PlayerRenderer playerrenderer = (PlayerRenderer)this.renderManager.getRenderer(abstractclientplayerentity);
        if (flag) {
            playerrenderer.renderRightArm(matrixStackIn, bufferIn, combinedLightIn, abstractclientplayerentity);
        } else {
            playerrenderer.renderLeftArm(matrixStackIn, bufferIn, combinedLightIn, abstractclientplayerentity);
        }

    }

    private void transformEatFirstPerson(MatrixStack matrixStackIn, float partialTicks, HandSide handIn, ItemStack stack) {
        float f = (float)this.mc.player.getItemInUseCount() - partialTicks + 1.0F;
        float f1 = f / (float)stack.getUseDuration();
        float f3;
        if (f1 < 0.8F) {
            f3 = MathHelper.abs(MathHelper.cos(f / 4.0F * 3.1415927F) * 0.1F);
            matrixStackIn.translate(0.0, (double)f3, 0.0);
        }

        f3 = 1.0F - (float)Math.pow((double)f1, 27.0);
        int i = handIn == HandSide.RIGHT ? 1 : -1;
        matrixStackIn.translate((double)(f3 * 0.6F * (float)i), (double)(f3 * -0.5F), (double)(f3 * 0.0F));
        matrixStackIn.rotate(Vector3f.YP.rotationDegrees((float)i * f3 * 90.0F));
        matrixStackIn.rotate(Vector3f.XP.rotationDegrees(f3 * 10.0F));
        matrixStackIn.rotate(Vector3f.ZP.rotationDegrees((float)i * f3 * 30.0F));
    }

    private void transformFirstPerson(MatrixStack matrixStackIn, HandSide handIn, float swingProgress) {
        FunctionRegistry functionRegistry = Wiksi.getInstance().getFunctionRegistry();
        SwingAnimation swingAnimation = functionRegistry.getSwingAnimation();
        int i;
        float power;
        float f;
        if (swingAnimation.isState()) {
            i = handIn == HandSide.RIGHT ? 1 : -1;
            power = (Float)swingAnimation.swingPower.get() * 10.0F;
            f = MathHelper.sin(swingProgress * swingProgress * 3.1415927F);
            matrixStackIn.rotate(Vector3f.YP.rotationDegrees((float)i * (45.0F + f * (-power / 4.0F))));
            float f1 = MathHelper.sin(MathHelper.sqrt(swingProgress) * 3.1415927F);
            matrixStackIn.rotate(Vector3f.ZP.rotationDegrees((float)i * f1 * -(power / 4.0F)));
            matrixStackIn.rotate(Vector3f.XP.rotationDegrees(f1 * -power));
            matrixStackIn.rotate(Vector3f.YP.rotationDegrees((float)i * -45.0F));
        } else {
            i = handIn == HandSide.RIGHT ? 1 : -1;
            power = MathHelper.sin(swingProgress * swingProgress * 3.1415927F);
            matrixStackIn.rotate(Vector3f.YP.rotationDegrees((float)i * (45.0F + power * -20.0F)));
            f = MathHelper.sin(MathHelper.sqrt(swingProgress) * 3.1415927F);
            matrixStackIn.rotate(Vector3f.ZP.rotationDegrees((float)i * f * -20.0F));
            matrixStackIn.rotate(Vector3f.XP.rotationDegrees(f * -80.0F));
            matrixStackIn.rotate(Vector3f.YP.rotationDegrees((float)i * -45.0F));
        }

    }

    private void transformSideFirstPerson(MatrixStack matrixStackIn, HandSide handIn, float equippedProg) {
        FunctionRegistry functionRegistry = Wiksi.getInstance().getFunctionRegistry();
        SwingAnimation swingAnimation = functionRegistry.getSwingAnimation();
        KillAura killAura = functionRegistry.getKillAura();
        int i = handIn == HandSide.RIGHT ? 1 : -1;
        if ((Boolean)swingAnimation.onlyAura.get() && killAura.getTarget() == null) {
            matrixStackIn.translate((double)((float)i * 0.56F), (double)(-0.52F + equippedProg * -0.6F), -0.7200000286102295);
        } else {
            matrixStackIn.translate((double)((float)i * 0.56F), (double)(-0.52F + equippedProg * (swingAnimation.isState() ? 0.0F : -0.6F)), -0.7200000286102295);
        }
    }

    public void renderItemInFirstPerson(float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer.Impl bufferIn, ClientPlayerEntity playerEntityIn, int combinedLightIn) {
        float f = playerEntityIn.getSwingProgress(partialTicks);
        Hand hand = (Hand)MoreObjects.firstNonNull(playerEntityIn.swingingHand, Hand.MAIN_HAND);
        float f1 = MathHelper.lerp(partialTicks, playerEntityIn.prevRotationPitch, playerEntityIn.rotationPitch);
        boolean flag = true;
        boolean flag1 = true;
        ItemStack itemstack2;
        if (playerEntityIn.isHandActive()) {
            itemstack2 = playerEntityIn.getActiveItemStack();
            if (itemstack2.getItem() instanceof ShootableItem) {
                flag = playerEntityIn.getActiveHand() == Hand.MAIN_HAND;
                flag1 = !flag;
            }

            Hand hand1 = playerEntityIn.getActiveHand();
            if (hand1 == Hand.MAIN_HAND) {
                ItemStack itemstack1 = playerEntityIn.getHeldItemOffhand();
                if (itemstack1.getItem() instanceof CrossbowItem && CrossbowItem.isCharged(itemstack1)) {
                    flag1 = false;
                }
            }
        } else {
            itemstack2 = playerEntityIn.getHeldItemMainhand();
            ItemStack itemstack3 = playerEntityIn.getHeldItemOffhand();
            if (itemstack2.getItem() instanceof CrossbowItem && CrossbowItem.isCharged(itemstack2)) {
                flag1 = !flag;
            }

            if (itemstack3.getItem() instanceof CrossbowItem && CrossbowItem.isCharged(itemstack3)) {
                flag = !itemstack2.isEmpty();
                flag1 = !flag;
            }
        }

        float f3 = MathHelper.lerp(partialTicks, playerEntityIn.prevRenderArmPitch, playerEntityIn.renderArmPitch);
        float f4 = MathHelper.lerp(partialTicks, playerEntityIn.prevRenderArmYaw, playerEntityIn.renderArmYaw);
        matrixStackIn.rotate(Vector3f.XP.rotationDegrees((playerEntityIn.getPitch(partialTicks) - f3) * 0.1F));
        matrixStackIn.rotate(Vector3f.YP.rotationDegrees((playerEntityIn.getYaw(partialTicks) - f4) * 0.1F));
        float f7;
        float f6;
        if (flag) {
            f6 = hand == Hand.MAIN_HAND ? f : 0.0F;
            f7 = 1.0F - MathHelper.lerp(partialTicks, this.prevEquippedProgressMainHand, this.equippedProgressMainHand);
            if (!Reflector.ForgeHooksClient_renderSpecificFirstPersonHand.exists() || !Reflector.callBoolean(Reflector.ForgeHooksClient_renderSpecificFirstPersonHand, new Object[]{Hand.MAIN_HAND, matrixStackIn, bufferIn, combinedLightIn, partialTicks, f1, f6, f7, this.itemStackMainHand})) {
                this.renderItemInFirstPerson(playerEntityIn, partialTicks, f1, Hand.MAIN_HAND, f6, this.itemStackMainHand, f7, matrixStackIn, bufferIn, combinedLightIn);
            }
        }

        if (flag1) {
            f6 = hand == Hand.OFF_HAND ? f : 0.0F;
            f7 = 1.0F - MathHelper.lerp(partialTicks, this.prevEquippedProgressOffHand, this.equippedProgressOffHand);
            if (!Reflector.ForgeHooksClient_renderSpecificFirstPersonHand.exists() || !Reflector.callBoolean(Reflector.ForgeHooksClient_renderSpecificFirstPersonHand, new Object[]{Hand.OFF_HAND, matrixStackIn, bufferIn, combinedLightIn, partialTicks, f1, f6, f7, this.itemStackOffHand})) {
                this.renderItemInFirstPerson(playerEntityIn, partialTicks, f1, Hand.OFF_HAND, f6, this.itemStackOffHand, f7, matrixStackIn, bufferIn, combinedLightIn);
            }
        }

        bufferIn.finish();
    }

    private void renderItemInFirstPerson(AbstractClientPlayerEntity player, float partialTicks, float pitch, Hand handIn, float swingProgress, ItemStack stack, float equippedProgress, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn) {
        if (!Config.isShaders() || !Shaders.isSkipRenderHand(handIn)) {
            boolean isMainHand = handIn == Hand.MAIN_HAND;
            HandSide handSide = isMainHand ? player.getPrimaryHand() : player.getPrimaryHand().opposite();
            matrixStackIn.push();
            FunctionRegistry functionRegistry = Wiksi.getInstance().getFunctionRegistry();
            ViewModel viewModel = functionRegistry.getViewModel();
            boolean isViewModelActive = viewModel.isState();
            if (isViewModelActive) {
                if (handSide == HandSide.RIGHT) {
                    matrixStackIn.translate((double)(Float)viewModel.right_x.get(), (double)(Float)viewModel.right_y.get(), (double)(Float)viewModel.right_z.get());
                } else {
                    matrixStackIn.translate((double)(Float)viewModel.left_x.get(), (double)(Float)viewModel.left_y.get(), (double)(Float)viewModel.left_z.get());
                }
            }

            if (stack.isEmpty()) {
                if (isMainHand && !player.isInvisible()) {
                    this.renderEmptyHand(matrixStackIn, bufferIn, combinedLightIn, equippedProgress, swingProgress, handSide, isViewModelActive);
                }
            } else {
                this.renderItemWithTransformations(player, partialTicks, pitch, handIn, swingProgress, stack, equippedProgress, matrixStackIn, bufferIn, combinedLightIn, handSide, isMainHand, isViewModelActive);
            }

            if (!isViewModelActive) {
                this.applyDefaultHandTransformations(matrixStackIn, handSide, equippedProgress, swingProgress);
            }

            matrixStackIn.pop();
        }

    }

    private void renderEmptyHand(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, float equippedProgress, float swingProgress, HandSide handSide, boolean isViewModelActive) {
        if (isViewModelActive) {
            this.applyHandTransformations(matrixStackIn, handSide, equippedProgress, swingProgress);
        }

        this.renderArmFirstPerson(matrixStackIn, bufferIn, combinedLightIn, equippedProgress, swingProgress, handSide);
    }

    private void applyDefaultHandTransformations(MatrixStack matrixStackIn, HandSide handSide, float equippedProgress, float swingProgress) {
        int sideMultiplier = handSide == HandSide.RIGHT ? 1 : -1;
        matrixStackIn.translate((double)((float)sideMultiplier * 0.56F), -0.52 + (double)(equippedProgress * -0.6F), -0.72);
        matrixStackIn.rotate(Vector3f.XP.rotationDegrees(-90.0F));
    }

    private void applyHandTransformations(MatrixStack matrixStackIn, HandSide handSide, float equippedProgress, float swingProgress) {
        int sideMultiplier = handSide == HandSide.RIGHT ? 1 : -1;
        float offsetX = (float)sideMultiplier * 0.15F;
        float offsetY = 0.15F;
        float offsetZ = 0.15F;
        matrixStackIn.translate((double)offsetX, (double)offsetY, (double)offsetZ);
    }

    private void renderItemWithTransformations(AbstractClientPlayerEntity player, float partialTicks, float pitch, Hand handIn, float swingProgress, ItemStack stack, float equippedProgress, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, HandSide handSide, boolean isMainHand, boolean isViewModelActive) {
        if (stack.getItem() instanceof FilledMapItem) {
            if (isMainHand && this.itemStackOffHand.isEmpty()) {
                this.renderMapFirstPerson(matrixStackIn, bufferIn, combinedLightIn, pitch, equippedProgress, swingProgress);
            } else {
                this.renderMapFirstPersonSide(matrixStackIn, bufferIn, combinedLightIn, equippedProgress, handSide, swingProgress, stack);
            }
        } else if (stack.getItem() instanceof CrossbowItem) {
            this.renderCrossbow(player, partialTicks, handIn, swingProgress, stack, equippedProgress, matrixStackIn, bufferIn, combinedLightIn, handSide, isViewModelActive);
        } else {
            this.renderGeneralItem(player, partialTicks, handIn, swingProgress, stack, equippedProgress, matrixStackIn, bufferIn, combinedLightIn, handSide, isMainHand, isViewModelActive);
        }

    }

    private void renderCrossbow(AbstractClientPlayerEntity player, float partialTicks, Hand handIn, float swingProgress, ItemStack stack, float equippedProgress, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, HandSide handSide, boolean isViewModelActive) {
        boolean isCharged = CrossbowItem.isCharged(stack);
        boolean isRightHand = handSide == HandSide.RIGHT;
        int sideMultiplier = isRightHand ? 1 : -1;
        float useDuration;
        float chargeProgress;
        float f15;
        if (player.isHandActive() && player.getItemInUseCount() > 0 && player.getActiveHand() == handIn) {
            this.transformSideFirstPerson(matrixStackIn, handSide, equippedProgress);
            matrixStackIn.translate((double)((float)sideMultiplier * -0.4785682F), -0.0943870022892952, 0.05731530860066414);
            matrixStackIn.rotate(Vector3f.XP.rotationDegrees(-11.935F));
            matrixStackIn.rotate(Vector3f.YP.rotationDegrees((float)sideMultiplier * 65.3F));
            matrixStackIn.rotate(Vector3f.ZP.rotationDegrees((float)sideMultiplier * -9.785F));
            useDuration = (float)stack.getUseDuration() - ((float)this.mc.player.getItemInUseCount() - partialTicks + 1.0F);
            chargeProgress = useDuration / (float)CrossbowItem.getChargeTime(stack);
            if (chargeProgress > 1.0F) {
                chargeProgress = 1.0F;
            }

            if (chargeProgress > 0.1F) {
                f15 = MathHelper.sin((useDuration - 0.1F) * 1.3F);
                float f3 = chargeProgress - 0.1F;
                float f4 = f15 * f3;
                matrixStackIn.translate((double)(f4 * 0.0F), (double)(f4 * 0.004F), (double)(f4 * 0.0F));
            }

            matrixStackIn.translate((double)(chargeProgress * 0.0F), (double)(chargeProgress * 0.0F), (double)(chargeProgress * 0.04F));
            matrixStackIn.scale(1.0F, 1.0F, 1.0F + chargeProgress * 0.2F);
            matrixStackIn.rotate(Vector3f.YN.rotationDegrees((float)sideMultiplier * 45.0F));
        } else {
            useDuration = -0.4F * MathHelper.sin(MathHelper.sqrt(swingProgress) * 3.1415927F);
            chargeProgress = 0.2F * MathHelper.sin(MathHelper.sqrt(swingProgress) * 6.2831855F);
            f15 = -0.2F * MathHelper.sin(swingProgress * 3.1415927F);
            matrixStackIn.translate((double)((float)sideMultiplier * useDuration), (double)chargeProgress, (double)f15);
            this.transformSideFirstPerson(matrixStackIn, handSide, equippedProgress);
            this.transformFirstPerson(matrixStackIn, handSide, swingProgress);
            if (isCharged && swingProgress < 0.001F) {
                matrixStackIn.translate((double)((float)sideMultiplier * -0.641864F), 0.0, 0.0);
                matrixStackIn.rotate(Vector3f.YP.rotationDegrees((float)sideMultiplier * 10.0F));
            }
        }

        this.renderItemSide(player, stack, isRightHand ? TransformType.FIRST_PERSON_RIGHT_HAND : TransformType.FIRST_PERSON_LEFT_HAND, !isRightHand, matrixStackIn, bufferIn, combinedLightIn);
    }

    private void renderGeneralItem(AbstractClientPlayerEntity player, float partialTicks, Hand handIn, float swingProgress, ItemStack stack, float equippedProgress, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, HandSide handSide, boolean isMainHand, boolean isViewModelActive) {
        FunctionRegistry functionRegistry = Wiksi.getInstance().getFunctionRegistry();
        ViewModel viewModel = functionRegistry.getViewModel();
        boolean isRightHand = handSide == HandSide.RIGHT;
        if (isViewModelActive) {
            if (isRightHand) {
                matrixStackIn.translate((double)(Float)viewModel.right_x.get(), (double)(Float)viewModel.right_y.get(), (double)(Float)viewModel.right_z.get());
            } else {
                matrixStackIn.translate((double)(Float)viewModel.left_x.get(), (double)(Float)viewModel.left_y.get(), (double)(Float)viewModel.left_z.get());
            }
        }

        int sideMultiplier;
        float f8;
        float f11;
        float f13;
        if (player.isHandActive() && player.getItemInUseCount() > 0 && player.getActiveHand() == handIn) {
            sideMultiplier = isRightHand ? 1 : -1;
            float f18;
            float f16;
            switch (stack.getUseAction()) {
                case NONE:
                    this.transformSideFirstPerson(matrixStackIn, handSide, equippedProgress);
                    break;
                case EAT:
                case DRINK:
                    this.transformEatFirstPerson(matrixStackIn, partialTicks, handSide, stack);
                    this.transformSideFirstPerson(matrixStackIn, handSide, equippedProgress);
                    break;
                case BLOCK:
                    this.transformSideFirstPerson(matrixStackIn, handSide, equippedProgress);
                    break;
                case BOW:
                    this.transformSideFirstPerson(matrixStackIn, handSide, equippedProgress);
                    matrixStackIn.translate((double)((float)sideMultiplier * -0.2785682F), 0.18344387412071228, 0.15731531381607056);
                    matrixStackIn.rotate(Vector3f.XP.rotationDegrees(-13.935F));
                    matrixStackIn.rotate(Vector3f.YP.rotationDegrees((float)sideMultiplier * 35.3F));
                    matrixStackIn.rotate(Vector3f.ZP.rotationDegrees((float)sideMultiplier * -9.785F));
                    f8 = (float)stack.getUseDuration() - ((float)this.mc.player.getItemInUseCount() - partialTicks + 1.0F);
                    f11 = f8 / 20.0F;
                    f11 = (f11 * f11 + f11 * 2.0F) / 3.0F;
                    if (f11 > 1.0F) {
                        f11 = 1.0F;
                    }

                    if (f11 > 0.1F) {
                        f13 = MathHelper.sin((f8 - 0.1F) * 1.3F);
                        f16 = f11 - 0.1F;
                        f18 = f13 * f16;
                        matrixStackIn.translate((double)(f18 * 0.0F), (double)(f18 * 0.004F), (double)(f18 * 0.0F));
                    }

                    matrixStackIn.translate((double)(f11 * 0.0F), (double)(f11 * 0.0F), (double)(f11 * 0.04F));
                    matrixStackIn.scale(1.0F, 1.0F, 1.0F + f11 * 0.2F);
                    matrixStackIn.rotate(Vector3f.YN.rotationDegrees((float)sideMultiplier * 45.0F));
                    break;
                case SPEAR:
                    this.transformSideFirstPerson(matrixStackIn, handSide, equippedProgress);
                    matrixStackIn.translate((double)((float)sideMultiplier * -0.5F), 0.699999988079071, 0.10000000149011612);
                    matrixStackIn.rotate(Vector3f.XP.rotationDegrees(-55.0F));
                    matrixStackIn.rotate(Vector3f.YP.rotationDegrees((float)sideMultiplier * 35.3F));
                    matrixStackIn.rotate(Vector3f.ZP.rotationDegrees((float)sideMultiplier * -9.785F));
                    f13 = (float)stack.getUseDuration() - ((float)this.mc.player.getItemInUseCount() - partialTicks + 1.0F);
                    f16 = f13 / 10.0F;
                    if (f16 > 1.0F) {
                        f16 = 1.0F;
                    }

                    if (f16 > 0.1F) {
                        f18 = MathHelper.sin((f13 - 0.1F) * 1.3F);
                        float f20 = f16 - 0.1F;
                        float f5 = f18 * f20;
                        matrixStackIn.translate((double)(f5 * 0.0F), (double)(f5 * 0.004F), (double)(f5 * 0.0F));
                    }

                    matrixStackIn.translate(0.0, 0.0, (double)(f16 * 0.2F));
                    matrixStackIn.scale(1.0F, 1.0F, 1.0F + f16 * 0.2F);
                    matrixStackIn.rotate(Vector3f.YN.rotationDegrees((float)sideMultiplier * 45.0F));
            }
        } else if (player.isSpinAttacking()) {
            this.transformSideFirstPerson(matrixStackIn, handSide, equippedProgress);
            sideMultiplier = isRightHand ? 1 : -1;
            matrixStackIn.translate((double)((float)sideMultiplier * -0.4F), 0.800000011920929, 0.30000001192092896);
            matrixStackIn.rotate(Vector3f.YP.rotationDegrees((float)sideMultiplier * 65.0F));
            matrixStackIn.rotate(Vector3f.ZP.rotationDegrees((float)sideMultiplier * -85.0F));
        } else {
            SwingAnimation swingAnimation = functionRegistry.getSwingAnimation();
            f8 = -0.4F * MathHelper.sin(MathHelper.sqrt(swingProgress) * 3.1415927F);
            f11 = 0.2F * MathHelper.sin(MathHelper.sqrt(swingProgress) * 6.2831855F);
            f13 = -0.2F * MathHelper.sin(swingProgress * 3.1415927F);
            sideMultiplier = isRightHand ? 1 : -1;
            if (!swingAnimation.isState()) {
                matrixStackIn.translate((double)((float)sideMultiplier * f8), (double)f11, (double)f13);
            }

            this.transformSideFirstPerson(matrixStackIn, handSide, equippedProgress);
            if (swingAnimation.isState() && handSide == HandSide.RIGHT) {
                swingAnimation.animationProcess(matrixStackIn, swingProgress, () -> {
                    this.transformFirstPerson(matrixStackIn, handSide, swingProgress);
                });
            } else {
                this.transformFirstPerson(matrixStackIn, handSide, swingProgress);
            }
        }

        this.renderItemSide(player, stack, isRightHand ? TransformType.FIRST_PERSON_RIGHT_HAND : TransformType.FIRST_PERSON_LEFT_HAND, !isRightHand, matrixStackIn, bufferIn, combinedLightIn);
    }

    public void tick() {
        this.prevEquippedProgressMainHand = this.equippedProgressMainHand;
        this.prevEquippedProgressOffHand = this.equippedProgressOffHand;
        ClientPlayerEntity clientplayerentity = this.mc.player;
        ItemStack itemstack = clientplayerentity.getHeldItemMainhand();
        ItemStack itemstack1 = clientplayerentity.getHeldItemOffhand();
        if (ItemStack.areItemStacksEqual(this.itemStackMainHand, itemstack)) {
            this.itemStackMainHand = itemstack;
        }

        if (ItemStack.areItemStacksEqual(this.itemStackOffHand, itemstack1)) {
            this.itemStackOffHand = itemstack1;
        }

        if (clientplayerentity.isRowingBoat()) {
            this.equippedProgressMainHand = MathHelper.clamp(this.equippedProgressMainHand - 0.4F, 0.0F, 1.0F);
            this.equippedProgressOffHand = MathHelper.clamp(this.equippedProgressOffHand - 0.4F, 0.0F, 1.0F);
        } else {
            float f = clientplayerentity.getCooledAttackStrength(1.0F);
            if (Reflector.ForgeHooksClient_shouldCauseReequipAnimation.exists()) {
                boolean flag = Reflector.callBoolean(Reflector.ForgeHooksClient_shouldCauseReequipAnimation, new Object[]{this.itemStackMainHand, itemstack, clientplayerentity.inventory.currentItem});
                boolean flag1 = Reflector.callBoolean(Reflector.ForgeHooksClient_shouldCauseReequipAnimation, new Object[]{this.itemStackOffHand, itemstack1, -1});
                if (!flag && !Objects.equals(this.itemStackMainHand, itemstack)) {
                    this.itemStackMainHand = itemstack;
                }

                if (!flag1 && !Objects.equals(this.itemStackOffHand, itemstack1)) {
                    this.itemStackOffHand = itemstack1;
                }
            }

            this.equippedProgressMainHand += MathHelper.clamp((this.itemStackMainHand == itemstack ? f * f * f : 0.0F) - this.equippedProgressMainHand, -0.4F, 0.4F);
            this.equippedProgressOffHand += MathHelper.clamp((float)(this.itemStackOffHand == itemstack1 ? 1 : 0) - this.equippedProgressOffHand, -0.4F, 0.4F);
        }

        if (this.equippedProgressMainHand < 0.1F) {
            this.itemStackMainHand = itemstack;
            if (Config.isShaders()) {
                Shaders.setItemToRenderMain(this.itemStackMainHand);
            }
        }

        if (this.equippedProgressOffHand < 0.1F) {
            this.itemStackOffHand = itemstack1;
            if (Config.isShaders()) {
                Shaders.setItemToRenderOff(this.itemStackOffHand);
            }
        }

    }

    public void resetEquippedProgress(Hand hand) {
        if (hand == Hand.MAIN_HAND) {
            this.equippedProgressMainHand = 0.0F;
        } else {
            this.equippedProgressOffHand = 0.0F;
        }

    }
}
