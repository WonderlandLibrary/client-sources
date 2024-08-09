/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import mpp.venusfr.functions.api.FunctionRegistry;
import mpp.venusfr.functions.impl.render.ESP;
import mpp.venusfr.venusfr;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.culling.ClippingHelper;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.LightType;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.reflect.Reflector;
import net.optifine.reflect.ReflectorForge;
import net.optifine.util.Either;

public abstract class EntityRenderer<T extends Entity>
implements IEntityRenderer {
    protected final EntityRendererManager renderManager;
    public float shadowSize;
    protected float shadowOpaque = 1.0f;
    private EntityType entityType = null;
    private ResourceLocation locationTextureCustom = null;

    protected EntityRenderer(EntityRendererManager entityRendererManager) {
        this.renderManager = entityRendererManager;
    }

    public final int getPackedLight(T t, float f) {
        BlockPos blockPos = new BlockPos(((Entity)t).func_241842_k(f));
        return LightTexture.packLight(this.getBlockLight(t, blockPos), this.func_239381_b_(t, blockPos));
    }

    protected int func_239381_b_(T t, BlockPos blockPos) {
        return ((Entity)t).world.getLightFor(LightType.SKY, blockPos);
    }

    protected int getBlockLight(T t, BlockPos blockPos) {
        return ((Entity)t).isBurning() ? 15 : ((Entity)t).world.getLightFor(LightType.BLOCK, blockPos);
    }

    public boolean shouldRender(T t, ClippingHelper clippingHelper, double d, double d2, double d3) {
        if (!((Entity)t).isInRangeToRender3d(d, d2, d3)) {
            return true;
        }
        if (((Entity)t).ignoreFrustumCheck) {
            return false;
        }
        AxisAlignedBB axisAlignedBB = ((Entity)t).getRenderBoundingBox().grow(0.5);
        if (axisAlignedBB.hasNaN() || axisAlignedBB.getAverageEdgeLength() == 0.0) {
            axisAlignedBB = new AxisAlignedBB(((Entity)t).getPosX() - 2.0, ((Entity)t).getPosY() - 2.0, ((Entity)t).getPosZ() - 2.0, ((Entity)t).getPosX() + 2.0, ((Entity)t).getPosY() + 2.0, ((Entity)t).getPosZ() + 2.0);
        }
        return clippingHelper.isBoundingBoxInFrustum(axisAlignedBB);
    }

    public Vector3d getRenderOffset(T t, float f) {
        return Vector3d.ZERO;
    }

    public void render(T t, float f, float f2, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n) {
        if (!Reflector.RenderNameplateEvent_Constructor.exists()) {
            if (this.canRenderName(t)) {
                this.renderName(t, ((Entity)t).getDisplayName(), matrixStack, iRenderTypeBuffer, n);
            }
        } else {
            Object object = Reflector.newInstance(Reflector.RenderNameplateEvent_Constructor, t, ((Entity)t).getDisplayName(), this, matrixStack, iRenderTypeBuffer, n, Float.valueOf(f2));
            Reflector.postForgeBusEvent(object);
            Object object2 = Reflector.call(object, Reflector.Event_getResult, new Object[0]);
            if (object2 != ReflectorForge.EVENT_RESULT_DENY && (object2 == ReflectorForge.EVENT_RESULT_ALLOW || this.canRenderName(t))) {
                ITextComponent iTextComponent = (ITextComponent)Reflector.call(object, Reflector.RenderNameplateEvent_getContent, new Object[0]);
                this.renderName(t, iTextComponent, matrixStack, iRenderTypeBuffer, n);
            }
        }
    }

    protected boolean canRenderName(T t) {
        return ((Entity)t).getAlwaysRenderNameTagForRender() && ((Entity)t).hasCustomName();
    }

    public abstract ResourceLocation getEntityTexture(T var1);

    public FontRenderer getFontRendererFromRenderManager() {
        return this.renderManager.getFontRenderer();
    }

    protected void renderName(T t, ITextComponent iTextComponent, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n) {
        PlayerEntity playerEntity;
        double d = this.renderManager.squareDistanceTo((Entity)t);
        boolean bl = !(d > 4096.0);
        FunctionRegistry functionRegistry = venusfr.getInstance().getFunctionRegistry();
        ESP eSP = functionRegistry.getEsp();
        if (eSP.isState() && t instanceof PlayerEntity && eSP.isValid(playerEntity = (PlayerEntity)t)) {
            return;
        }
        if (Reflector.ForgeHooksClient_isNameplateInRenderDistance.exists()) {
            bl = Reflector.ForgeHooksClient_isNameplateInRenderDistance.callBoolean(t, d);
        }
        if (bl) {
            boolean bl2 = !((Entity)t).isDiscrete();
            float f = ((Entity)t).getHeight() + 0.5f;
            int n2 = "deadmau5".equals(iTextComponent.getString()) ? -10 : 0;
            matrixStack.push();
            matrixStack.translate(0.0, f, 0.0);
            matrixStack.rotate(this.renderManager.getCameraOrientation());
            matrixStack.scale(-0.025f, -0.025f, 0.025f);
            Matrix4f matrix4f = matrixStack.getLast().getMatrix();
            float f2 = Minecraft.getInstance().gameSettings.getTextBackgroundOpacity(0.25f);
            int n3 = (int)(f2 * 255.0f) << 24;
            FontRenderer fontRenderer = this.getFontRendererFromRenderManager();
            float f3 = -fontRenderer.getStringPropertyWidth(iTextComponent) / 2;
            fontRenderer.func_243247_a(iTextComponent, f3, n2, 0x20FFFFFF, false, matrix4f, iRenderTypeBuffer, bl2, n3, n);
            if (bl2) {
                fontRenderer.func_243247_a(iTextComponent, f3, n2, -1, false, matrix4f, iRenderTypeBuffer, false, 0, n);
            }
            matrixStack.pop();
        }
    }

    public EntityRendererManager getRenderManager() {
        return this.renderManager;
    }

    @Override
    public Either<EntityType, TileEntityType> getType() {
        return this.entityType == null ? null : Either.makeLeft(this.entityType);
    }

    @Override
    public void setType(Either<EntityType, TileEntityType> either) {
        this.entityType = either.getLeft().get();
    }

    @Override
    public ResourceLocation getLocationTextureCustom() {
        return this.locationTextureCustom;
    }

    @Override
    public void setLocationTextureCustom(ResourceLocation resourceLocation) {
        this.locationTextureCustom = resourceLocation;
    }
}

