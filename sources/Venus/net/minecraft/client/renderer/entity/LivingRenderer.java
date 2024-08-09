/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerModelPart;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.TextFormatting;
import net.optifine.Config;
import net.optifine.entity.model.CustomEntityModels;
import net.optifine.reflect.Reflector;
import net.optifine.shaders.Shaders;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class LivingRenderer<T extends LivingEntity, M extends EntityModel<T>>
extends EntityRenderer<T>
implements IEntityRenderer<T, M> {
    private static final Logger LOGGER = LogManager.getLogger();
    public M entityModel;
    protected final List<LayerRenderer<T, M>> layerRenderers = Lists.newArrayList();
    public LivingEntity renderEntity;
    public float renderLimbSwing;
    public float renderLimbSwingAmount;
    public float renderAgeInTicks;
    public float renderHeadYaw;
    public float renderHeadPitch;
    public float renderPartialTicks;
    public static final boolean animateModelLiving = Boolean.getBoolean("animate.model.living");

    public LivingRenderer(EntityRendererManager entityRendererManager, M m, float f) {
        super(entityRendererManager);
        this.entityModel = m;
        this.shadowSize = f;
    }

    public final boolean addLayer(LayerRenderer<T, M> layerRenderer) {
        return this.layerRenderers.add(layerRenderer);
    }

    @Override
    public M getEntityModel() {
        return this.entityModel;
    }

    @Override
    public void render(T t, float f, float f2, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n) {
        if (!Reflector.RenderLivingEvent_Pre_Constructor.exists() || !Reflector.postForgeBusEvent(Reflector.RenderLivingEvent_Pre_Constructor, t, this, Float.valueOf(f2), matrixStack, iRenderTypeBuffer, n)) {
            boolean bl;
            boolean bl2;
            float f3;
            Direction direction;
            if (animateModelLiving) {
                ((LivingEntity)t).limbSwingAmount = 1.0f;
            }
            matrixStack.push();
            ((EntityModel)this.entityModel).swingProgress = this.getSwingProgress(t, f2);
            ((EntityModel)this.entityModel).isSitting = ((Entity)t).isPassenger();
            if (Reflector.IForgeEntity_shouldRiderSit.exists()) {
                ((EntityModel)this.entityModel).isSitting = ((Entity)t).isPassenger() && ((Entity)t).getRidingEntity() != null && Reflector.callBoolean(((Entity)t).getRidingEntity(), Reflector.IForgeEntity_shouldRiderSit, new Object[0]);
            }
            ((EntityModel)this.entityModel).isChild = ((LivingEntity)t).isChild();
            float f4 = MathHelper.interpolateAngle(f2, ((LivingEntity)t).prevRenderYawOffset, ((LivingEntity)t).renderYawOffset);
            float f5 = MathHelper.interpolateAngle(f2, ((LivingEntity)t).prevRotationYawHead, ((LivingEntity)t).rotationYawHead);
            float f6 = f5 - f4;
            if (((EntityModel)this.entityModel).isSitting && ((Entity)t).getRidingEntity() instanceof LivingEntity) {
                LivingEntity livingEntity = (LivingEntity)((Entity)t).getRidingEntity();
                f4 = MathHelper.interpolateAngle(f2, livingEntity.prevRenderYawOffset, livingEntity.renderYawOffset);
                f6 = f5 - f4;
                float f7 = MathHelper.wrapDegrees(f6);
                if (f7 < -85.0f) {
                    f7 = -85.0f;
                }
                if (f7 >= 85.0f) {
                    f7 = 85.0f;
                }
                f4 = f5 - f7;
                if (f7 * f7 > 2500.0f) {
                    f4 += f7 * 0.2f;
                }
                f6 = f5 - f4;
            }
            float f8 = t == Minecraft.getInstance().getRenderViewEntity() ? MathHelper.lerp(f2, ((LivingEntity)t).prevRotationPitchHead, ((LivingEntity)t).rotationPitchHead) : MathHelper.lerp(f2, ((LivingEntity)t).prevRotationPitch, ((LivingEntity)t).rotationPitch);
            if (((Entity)t).getPose() == Pose.SLEEPING && (direction = ((LivingEntity)t).getBedDirection()) != null) {
                f3 = ((Entity)t).getEyeHeight(Pose.STANDING) - 0.1f;
                matrixStack.translate((float)(-direction.getXOffset()) * f3, 0.0, (float)(-direction.getZOffset()) * f3);
            }
            float f9 = this.handleRotationFloat(t, f2);
            this.applyRotations(t, matrixStack, f9, f4, f2);
            matrixStack.scale(-1.0f, -1.0f, 1.0f);
            this.preRenderCallback(t, matrixStack, f2);
            matrixStack.translate(0.0, -1.501f, 0.0);
            f3 = 0.0f;
            float f10 = 0.0f;
            if (!((Entity)t).isPassenger() && ((LivingEntity)t).isAlive()) {
                f3 = MathHelper.lerp(f2, ((LivingEntity)t).prevLimbSwingAmount, ((LivingEntity)t).limbSwingAmount);
                f10 = ((LivingEntity)t).limbSwing - ((LivingEntity)t).limbSwingAmount * (1.0f - f2);
                if (((LivingEntity)t).isChild()) {
                    f10 *= 3.0f;
                }
                if (f3 > 1.0f) {
                    f3 = 1.0f;
                }
            }
            ((EntityModel)this.entityModel).setLivingAnimations(t, f10, f3, f2);
            ((EntityModel)this.entityModel).setRotationAngles(t, f10, f3, f9, f6, f8);
            if (CustomEntityModels.isActive()) {
                this.renderEntity = t;
                this.renderLimbSwing = f10;
                this.renderLimbSwingAmount = f3;
                this.renderAgeInTicks = f9;
                this.renderHeadYaw = f6;
                this.renderHeadPitch = f8;
                this.renderPartialTicks = f2;
            }
            boolean bl3 = Config.isShaders();
            Minecraft minecraft = Minecraft.getInstance();
            boolean bl4 = !(bl2 = this.isVisible(t)) && !((Entity)t).isInvisibleToPlayer(minecraft.player);
            RenderType renderType = this.func_230496_a_(t, bl2, bl4, bl = minecraft.isEntityGlowing((Entity)t));
            if (renderType != null) {
                IVertexBuilder iVertexBuilder = iRenderTypeBuffer.getBuffer(renderType);
                float f11 = this.getOverlayProgress(t, f2);
                if (bl3) {
                    if (((LivingEntity)t).hurtTime > 0 || ((LivingEntity)t).deathTime > 0) {
                        Shaders.setEntityColor(1.0f, 0.0f, 0.0f, 0.3f);
                    }
                    if (f11 > 0.0f) {
                        Shaders.setEntityColor(f11, f11, f11, 0.5f);
                    }
                }
                int n2 = LivingRenderer.getPackedOverlay(t, f11);
                ((Model)this.entityModel).render(matrixStack, iVertexBuilder, n, n2, 1.0f, 1.0f, 1.0f, bl4 ? 0.15f : 1.0f);
            }
            if (!((Entity)t).isSpectator()) {
                for (LayerRenderer layerRenderer : this.layerRenderers) {
                    layerRenderer.render(matrixStack, iRenderTypeBuffer, n, t, f10, f3, f2, f9, f6, f8);
                }
            }
            if (Config.isShaders()) {
                Shaders.setEntityColor(0.0f, 0.0f, 0.0f, 0.0f);
            }
            if (CustomEntityModels.isActive()) {
                this.renderEntity = null;
            }
            matrixStack.pop();
            super.render(t, f, f2, matrixStack, iRenderTypeBuffer, n);
            if (Reflector.RenderLivingEvent_Post_Constructor.exists()) {
                Reflector.postForgeBusEvent(Reflector.RenderLivingEvent_Post_Constructor, t, this, Float.valueOf(f2), matrixStack, iRenderTypeBuffer, n);
            }
        }
    }

    @Nullable
    protected RenderType func_230496_a_(T t, boolean bl, boolean bl2, boolean bl3) {
        ResourceLocation resourceLocation = this.getEntityTexture(t);
        if (this.getLocationTextureCustom() != null) {
            resourceLocation = this.getLocationTextureCustom();
        }
        if (bl2) {
            return RenderType.getItemEntityTranslucentCull(resourceLocation);
        }
        if (bl) {
            return ((Model)this.entityModel).getRenderType(resourceLocation);
        }
        if (((Entity)t).isGlowing() && !Config.getMinecraft().worldRenderer.isRenderEntityOutlines()) {
            return ((Model)this.entityModel).getRenderType(resourceLocation);
        }
        return bl3 ? RenderType.getOutline(resourceLocation) : null;
    }

    public static int getPackedOverlay(LivingEntity livingEntity, float f) {
        return OverlayTexture.getPackedUV(OverlayTexture.getU(f), OverlayTexture.getV(livingEntity.hurtTime > 0 || livingEntity.deathTime > 0));
    }

    protected boolean isVisible(T t) {
        return !((Entity)t).isInvisible();
    }

    private static float getFacingAngle(Direction direction) {
        switch (direction) {
            case SOUTH: {
                return 90.0f;
            }
            case WEST: {
                return 0.0f;
            }
            case NORTH: {
                return 270.0f;
            }
            case EAST: {
                return 180.0f;
            }
        }
        return 0.0f;
    }

    protected boolean func_230495_a_(T t) {
        return true;
    }

    protected void applyRotations(T t, MatrixStack matrixStack, float f, float f2, float f3) {
        String string;
        Pose pose;
        if (this.func_230495_a_(t)) {
            f2 += (float)(Math.cos((double)((LivingEntity)t).ticksExisted * 3.25) * Math.PI * (double)0.4f);
        }
        if ((pose = ((Entity)t).getPose()) != Pose.SLEEPING) {
            matrixStack.rotate(Vector3f.YP.rotationDegrees(180.0f - f2));
        }
        if (((LivingEntity)t).deathTime > 0) {
            float f4 = ((float)((LivingEntity)t).deathTime + f3 - 1.0f) / 20.0f * 1.6f;
            if ((f4 = MathHelper.sqrt(f4)) > 1.0f) {
                f4 = 1.0f;
            }
            matrixStack.rotate(Vector3f.ZP.rotationDegrees(f4 * this.getDeathMaxRotation(t)));
        } else if (((LivingEntity)t).isSpinAttacking()) {
            matrixStack.rotate(Vector3f.XP.rotationDegrees(-90.0f - ((LivingEntity)t).rotationPitch));
            matrixStack.rotate(Vector3f.YP.rotationDegrees(((float)((LivingEntity)t).ticksExisted + f3) * -75.0f));
        } else if (pose == Pose.SLEEPING) {
            Direction direction = ((LivingEntity)t).getBedDirection();
            float f5 = direction != null ? LivingRenderer.getFacingAngle(direction) : f2;
            matrixStack.rotate(Vector3f.YP.rotationDegrees(f5));
            matrixStack.rotate(Vector3f.ZP.rotationDegrees(this.getDeathMaxRotation(t)));
            matrixStack.rotate(Vector3f.YP.rotationDegrees(270.0f));
        } else if ((((Entity)t).hasCustomName() || t instanceof PlayerEntity) && ("Dinnerbone".equals(string = TextFormatting.getTextWithoutFormattingCodes(((Entity)t).getName().getString())) || "Grumm".equals(string)) && (!(t instanceof PlayerEntity) || ((PlayerEntity)t).isWearing(PlayerModelPart.CAPE))) {
            matrixStack.translate(0.0, ((Entity)t).getHeight() + 0.1f, 0.0);
            matrixStack.rotate(Vector3f.ZP.rotationDegrees(180.0f));
        }
    }

    protected float getSwingProgress(T t, float f) {
        return ((LivingEntity)t).getSwingProgress(f);
    }

    protected float handleRotationFloat(T t, float f) {
        return (float)((LivingEntity)t).ticksExisted + f;
    }

    protected float getDeathMaxRotation(T t) {
        return 90.0f;
    }

    protected float getOverlayProgress(T t, float f) {
        return 0.0f;
    }

    protected void preRenderCallback(T t, MatrixStack matrixStack, float f) {
    }

    @Override
    protected boolean canRenderName(T t) {
        boolean bl;
        float f;
        double d = this.renderManager.squareDistanceTo((Entity)t);
        float f2 = f = ((Entity)t).isDiscrete() ? 32.0f : 64.0f;
        if (d >= (double)(f * f)) {
            return true;
        }
        Minecraft minecraft = Minecraft.getInstance();
        ClientPlayerEntity clientPlayerEntity = minecraft.player;
        boolean bl2 = bl = !((Entity)t).isInvisibleToPlayer(clientPlayerEntity);
        if (t != clientPlayerEntity) {
            Team team = ((Entity)t).getTeam();
            Team team2 = clientPlayerEntity.getTeam();
            if (team != null) {
                Team.Visible visible = team.getNameTagVisibility();
                switch (visible) {
                    case ALWAYS: {
                        return bl;
                    }
                    case NEVER: {
                        return true;
                    }
                    case HIDE_FOR_OTHER_TEAMS: {
                        return team2 == null ? bl : team.isSameTeam(team2) && (team.getSeeFriendlyInvisiblesEnabled() || bl);
                    }
                    case HIDE_FOR_OWN_TEAM: {
                        return team2 == null ? bl : !team.isSameTeam(team2) && bl;
                    }
                }
                return false;
            }
        }
        return Minecraft.isGuiEnabled() && t != minecraft.getRenderViewEntity() && bl && !((Entity)t).isBeingRidden();
    }

    public List<LayerRenderer<T, M>> getLayerRenderers() {
        return this.layerRenderers;
    }

    @Override
    protected boolean canRenderName(Entity entity2) {
        return this.canRenderName((T)((LivingEntity)entity2));
    }

    @Override
    public void render(Entity entity2, float f, float f2, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n) {
        this.render((T)((LivingEntity)entity2), f, f2, matrixStack, iRenderTypeBuffer, n);
    }
}

