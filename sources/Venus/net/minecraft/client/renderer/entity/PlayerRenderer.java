/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.layers.ArrowLayer;
import net.minecraft.client.renderer.entity.layers.BeeStingerLayer;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.client.renderer.entity.layers.CapeLayer;
import net.minecraft.client.renderer.entity.layers.Deadmau5HeadLayer;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.client.renderer.entity.layers.HeadLayer;
import net.minecraft.client.renderer.entity.layers.HeldItemLayer;
import net.minecraft.client.renderer.entity.layers.ParrotVariantLayer;
import net.minecraft.client.renderer.entity.layers.SpinAttackEffectLayer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerModelPart;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.UseAction;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.Hand;
import net.minecraft.util.HandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class PlayerRenderer
extends LivingRenderer<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>> {
    public PlayerRenderer(EntityRendererManager entityRendererManager) {
        this(entityRendererManager, false);
    }

    public PlayerRenderer(EntityRendererManager entityRendererManager, boolean bl) {
        super(entityRendererManager, new PlayerModel(0.0f, bl), 0.5f);
        this.addLayer(new BipedArmorLayer(this, new BipedModel(0.5f), new BipedModel(1.0f)));
        this.addLayer(new HeldItemLayer<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>>(this));
        this.addLayer(new ArrowLayer<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>>(this));
        this.addLayer(new Deadmau5HeadLayer(this));
        this.addLayer(new CapeLayer(this));
        this.addLayer(new HeadLayer<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>>(this));
        this.addLayer(new ElytraLayer<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>>(this));
        this.addLayer(new ParrotVariantLayer<AbstractClientPlayerEntity>(this));
        this.addLayer(new SpinAttackEffectLayer<AbstractClientPlayerEntity>(this));
        this.addLayer(new BeeStingerLayer<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>>(this));
    }

    @Override
    public void render(AbstractClientPlayerEntity abstractClientPlayerEntity, float f, float f2, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n) {
        this.setModelVisibilities(abstractClientPlayerEntity);
        super.render(abstractClientPlayerEntity, f, f2, matrixStack, iRenderTypeBuffer, n);
    }

    @Override
    public Vector3d getRenderOffset(AbstractClientPlayerEntity abstractClientPlayerEntity, float f) {
        return abstractClientPlayerEntity.isCrouching() ? new Vector3d(0.0, -0.125, 0.0) : super.getRenderOffset(abstractClientPlayerEntity, f);
    }

    private void setModelVisibilities(AbstractClientPlayerEntity abstractClientPlayerEntity) {
        PlayerModel playerModel = (PlayerModel)this.getEntityModel();
        if (abstractClientPlayerEntity.isSpectator()) {
            playerModel.setVisible(true);
            playerModel.bipedHead.showModel = true;
            playerModel.bipedHeadwear.showModel = true;
        } else {
            playerModel.setVisible(false);
            playerModel.bipedHeadwear.showModel = abstractClientPlayerEntity.isWearing(PlayerModelPart.HAT);
            playerModel.bipedBodyWear.showModel = abstractClientPlayerEntity.isWearing(PlayerModelPart.JACKET);
            playerModel.bipedLeftLegwear.showModel = abstractClientPlayerEntity.isWearing(PlayerModelPart.LEFT_PANTS_LEG);
            playerModel.bipedRightLegwear.showModel = abstractClientPlayerEntity.isWearing(PlayerModelPart.RIGHT_PANTS_LEG);
            playerModel.bipedLeftArmwear.showModel = abstractClientPlayerEntity.isWearing(PlayerModelPart.LEFT_SLEEVE);
            playerModel.bipedRightArmwear.showModel = abstractClientPlayerEntity.isWearing(PlayerModelPart.RIGHT_SLEEVE);
            playerModel.isSneak = abstractClientPlayerEntity.isCrouching();
            BipedModel.ArmPose armPose = PlayerRenderer.func_241741_a_(abstractClientPlayerEntity, Hand.MAIN_HAND);
            BipedModel.ArmPose armPose2 = PlayerRenderer.func_241741_a_(abstractClientPlayerEntity, Hand.OFF_HAND);
            if (armPose.func_241657_a_()) {
                BipedModel.ArmPose armPose3 = armPose2 = abstractClientPlayerEntity.getHeldItemOffhand().isEmpty() ? BipedModel.ArmPose.EMPTY : BipedModel.ArmPose.ITEM;
            }
            if (abstractClientPlayerEntity.getPrimaryHand() == HandSide.RIGHT) {
                playerModel.rightArmPose = armPose;
                playerModel.leftArmPose = armPose2;
            } else {
                playerModel.rightArmPose = armPose2;
                playerModel.leftArmPose = armPose;
            }
        }
    }

    private static BipedModel.ArmPose func_241741_a_(AbstractClientPlayerEntity abstractClientPlayerEntity, Hand hand) {
        ItemStack itemStack = abstractClientPlayerEntity.getHeldItem(hand);
        if (itemStack.isEmpty()) {
            return BipedModel.ArmPose.EMPTY;
        }
        if (abstractClientPlayerEntity.getActiveHand() == hand && abstractClientPlayerEntity.getItemInUseCount() > 0) {
            UseAction useAction = itemStack.getUseAction();
            if (useAction == UseAction.BLOCK) {
                return BipedModel.ArmPose.BLOCK;
            }
            if (useAction == UseAction.BOW) {
                return BipedModel.ArmPose.BOW_AND_ARROW;
            }
            if (useAction == UseAction.SPEAR) {
                return BipedModel.ArmPose.THROW_SPEAR;
            }
            if (useAction == UseAction.CROSSBOW && hand == abstractClientPlayerEntity.getActiveHand()) {
                return BipedModel.ArmPose.CROSSBOW_CHARGE;
            }
        } else if (!abstractClientPlayerEntity.isSwingInProgress && itemStack.getItem() == Items.CROSSBOW && CrossbowItem.isCharged(itemStack)) {
            return BipedModel.ArmPose.CROSSBOW_HOLD;
        }
        return BipedModel.ArmPose.ITEM;
    }

    @Override
    public ResourceLocation getEntityTexture(AbstractClientPlayerEntity abstractClientPlayerEntity) {
        return abstractClientPlayerEntity.getLocationSkin();
    }

    @Override
    protected void preRenderCallback(AbstractClientPlayerEntity abstractClientPlayerEntity, MatrixStack matrixStack, float f) {
        float f2 = 0.9375f;
        matrixStack.scale(0.9375f, 0.9375f, 0.9375f);
    }

    @Override
    protected void renderName(AbstractClientPlayerEntity abstractClientPlayerEntity, ITextComponent iTextComponent, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n) {
        Scoreboard scoreboard;
        ScoreObjective scoreObjective;
        double d = this.renderManager.squareDistanceTo(abstractClientPlayerEntity);
        matrixStack.push();
        if (d < 100.0 && (scoreObjective = (scoreboard = abstractClientPlayerEntity.getWorldScoreboard()).getObjectiveInDisplaySlot(2)) != null) {
            Score score = scoreboard.getOrCreateScore(abstractClientPlayerEntity.getScoreboardName(), scoreObjective);
            super.renderName(abstractClientPlayerEntity, new StringTextComponent(Integer.toString(score.getScorePoints())).appendString(" ").append(scoreObjective.getDisplayName()), matrixStack, iRenderTypeBuffer, n);
            matrixStack.translate(0.0, 0.25875f, 0.0);
        }
        super.renderName(abstractClientPlayerEntity, iTextComponent, matrixStack, iRenderTypeBuffer, n);
        matrixStack.pop();
    }

    public void renderRightArm(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, AbstractClientPlayerEntity abstractClientPlayerEntity) {
        this.renderItem(matrixStack, iRenderTypeBuffer, n, abstractClientPlayerEntity, ((PlayerModel)this.entityModel).bipedRightArm, ((PlayerModel)this.entityModel).bipedRightArmwear);
    }

    public void renderLeftArm(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, AbstractClientPlayerEntity abstractClientPlayerEntity) {
        this.renderItem(matrixStack, iRenderTypeBuffer, n, abstractClientPlayerEntity, ((PlayerModel)this.entityModel).bipedLeftArm, ((PlayerModel)this.entityModel).bipedLeftArmwear);
    }

    private void renderItem(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, AbstractClientPlayerEntity abstractClientPlayerEntity, ModelRenderer modelRenderer, ModelRenderer modelRenderer2) {
        PlayerModel playerModel = (PlayerModel)this.getEntityModel();
        this.setModelVisibilities(abstractClientPlayerEntity);
        playerModel.swingProgress = 0.0f;
        playerModel.isSneak = false;
        playerModel.swimAnimation = 0.0f;
        playerModel.setRotationAngles(abstractClientPlayerEntity, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f);
        modelRenderer.rotateAngleX = 0.0f;
        modelRenderer.render(matrixStack, iRenderTypeBuffer.getBuffer(RenderType.getEntitySolid(abstractClientPlayerEntity.getLocationSkin())), n, OverlayTexture.NO_OVERLAY);
        modelRenderer2.rotateAngleX = 0.0f;
        modelRenderer2.render(matrixStack, iRenderTypeBuffer.getBuffer(RenderType.getEntityTranslucent(abstractClientPlayerEntity.getLocationSkin())), n, OverlayTexture.NO_OVERLAY);
    }

    @Override
    protected void applyRotations(AbstractClientPlayerEntity abstractClientPlayerEntity, MatrixStack matrixStack, float f, float f2, float f3) {
        float f4 = abstractClientPlayerEntity.getSwimAnimation(f3);
        if (abstractClientPlayerEntity.isElytraFlying()) {
            super.applyRotations(abstractClientPlayerEntity, matrixStack, f, f2, f3);
            float f5 = (float)abstractClientPlayerEntity.getTicksElytraFlying() + f3;
            float f6 = MathHelper.clamp(f5 * f5 / 100.0f, 0.0f, 1.0f);
            if (!abstractClientPlayerEntity.isSpinAttacking()) {
                matrixStack.rotate(Vector3f.XP.rotationDegrees(f6 * (-90.0f - abstractClientPlayerEntity.rotationPitch)));
            }
            Vector3d vector3d = abstractClientPlayerEntity.getLook(f3);
            Vector3d vector3d2 = abstractClientPlayerEntity.getMotion();
            double d = Entity.horizontalMag(vector3d2);
            double d2 = Entity.horizontalMag(vector3d);
            if (d > 0.0 && d2 > 0.0) {
                double d3 = (vector3d2.x * vector3d.x + vector3d2.z * vector3d.z) / Math.sqrt(d * d2);
                double d4 = vector3d2.x * vector3d.z - vector3d2.z * vector3d.x;
                matrixStack.rotate(Vector3f.YP.rotation((float)(Math.signum(d4) * Math.acos(d3))));
            }
        } else if (f4 > 0.0f) {
            super.applyRotations(abstractClientPlayerEntity, matrixStack, f, f2, f3);
            float f7 = abstractClientPlayerEntity.isInWater() ? -90.0f - abstractClientPlayerEntity.rotationPitch : -90.0f;
            float f8 = MathHelper.lerp(f4, 0.0f, f7);
            matrixStack.rotate(Vector3f.XP.rotationDegrees(f8));
            if (abstractClientPlayerEntity.isActualySwimming()) {
                matrixStack.translate(0.0, -1.0, 0.3f);
            }
        } else {
            super.applyRotations(abstractClientPlayerEntity, matrixStack, f, f2, f3);
        }
    }

    @Override
    protected void preRenderCallback(LivingEntity livingEntity, MatrixStack matrixStack, float f) {
        this.preRenderCallback((AbstractClientPlayerEntity)livingEntity, matrixStack, f);
    }

    @Override
    protected void applyRotations(LivingEntity livingEntity, MatrixStack matrixStack, float f, float f2, float f3) {
        this.applyRotations((AbstractClientPlayerEntity)livingEntity, matrixStack, f, f2, f3);
    }

    @Override
    public void render(LivingEntity livingEntity, float f, float f2, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n) {
        this.render((AbstractClientPlayerEntity)livingEntity, f, f2, matrixStack, iRenderTypeBuffer, n);
    }

    @Override
    public ResourceLocation getEntityTexture(Entity entity2) {
        return this.getEntityTexture((AbstractClientPlayerEntity)entity2);
    }

    @Override
    protected void renderName(Entity entity2, ITextComponent iTextComponent, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n) {
        this.renderName((AbstractClientPlayerEntity)entity2, iTextComponent, matrixStack, iRenderTypeBuffer, n);
    }

    @Override
    public void render(Entity entity2, float f, float f2, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n) {
        this.render((AbstractClientPlayerEntity)entity2, f, f2, matrixStack, iRenderTypeBuffer, n);
    }

    @Override
    public Vector3d getRenderOffset(Entity entity2, float f) {
        return this.getRenderOffset((AbstractClientPlayerEntity)entity2, f);
    }
}

