package net.minecraft.client.renderer.entity;

import dev.excellent.Excellent;
import dev.excellent.api.event.impl.render.RenderNameEvent;
import dev.excellent.client.friend.Friend;
import dev.excellent.client.module.impl.misc.NameProtect;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.culling.ClippingHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.LightType;
import net.mojang.blaze3d.matrix.MatrixStack;
import net.optifine.reflect.Reflector;
import net.optifine.reflect.ReflectorForge;
import net.optifine.util.Either;

import static dev.excellent.api.interfaces.game.IMinecraft.mc;

public abstract class EntityRenderer<T extends Entity> implements net.optifine.entity.model.IEntityRenderer {
    protected final EntityRendererManager renderManager;
    public float shadowSize;
    protected float shadowOpaque = 1.0F;
    private EntityType entityType = null;
    private ResourceLocation locationTextureCustom = null;

    protected EntityRenderer(EntityRendererManager renderManager) {
        this.renderManager = renderManager;
    }

    public final int getPackedLight(T entityIn, float partialTicks) {
        BlockPos blockpos = new BlockPos(entityIn.func_241842_k(partialTicks));
        return LightTexture.packLight(this.getBlockLight(entityIn, blockpos), this.func_239381_b_(entityIn, blockpos));
    }

    protected int func_239381_b_(T p_239381_1_, BlockPos p_239381_2_) {
        return p_239381_1_.world.getLightFor(LightType.SKY, p_239381_2_);
    }

    protected int getBlockLight(T entityIn, BlockPos partialTicks) {
        return entityIn.isBurning() ? 15 : entityIn.world.getLightFor(LightType.BLOCK, partialTicks);
    }

    public boolean shouldRender(T livingEntityIn, ClippingHelper camera, double camX, double camY, double camZ) {
        if (!livingEntityIn.isInRangeToRender3d(camX, camY, camZ)) {
            return false;
        } else if (livingEntityIn.ignoreFrustumCheck) {
            return true;
        } else {
            AxisAlignedBB axisalignedbb = livingEntityIn.getRenderBoundingBox().grow(0.5D);

            if (axisalignedbb.hasNaN() || axisalignedbb.getAverageEdgeLength() == 0.0D) {
                axisalignedbb = new AxisAlignedBB(livingEntityIn.getPosX() - 2.0D, livingEntityIn.getPosY() - 2.0D, livingEntityIn.getPosZ() - 2.0D, livingEntityIn.getPosX() + 2.0D, livingEntityIn.getPosY() + 2.0D, livingEntityIn.getPosZ() + 2.0D);
            }

            return camera.isBoundingBoxInFrustum(axisalignedbb);
        }
    }

    public Vector3d getRenderOffset(T entityIn, float partialTicks) {
        return Vector3d.ZERO;
    }

    @Getter
    @Setter
    protected boolean renderName = true;

    public void render(T entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        final RenderNameEvent event = new RenderNameEvent(entityIn);
        Excellent.getInst().getEventBus().handle(event);

        if (event.isCancelled()) {
            return;
        }
        if (!Reflector.RenderNameplateEvent_Constructor.exists()) {
            if (this.canRenderName(entityIn) && renderName) {
                this.renderName(entityIn, entityIn.getDisplayName(), matrixStackIn, bufferIn, packedLightIn);
            }
        } else {
            Object object = Reflector.newInstance(Reflector.RenderNameplateEvent_Constructor, entityIn, entityIn.getDisplayName(), this, matrixStackIn, bufferIn, packedLightIn, partialTicks);
            Reflector.postForgeBusEvent(object);
            Object object1 = Reflector.call(object, Reflector.Event_getResult);

            if (object1 != ReflectorForge.EVENT_RESULT_DENY && (object1 == ReflectorForge.EVENT_RESULT_ALLOW || this.canRenderName(entityIn))) {
                ITextComponent itextcomponent = (ITextComponent) Reflector.call(object, Reflector.RenderNameplateEvent_getContent);
                this.renderName(entityIn, itextcomponent, matrixStackIn, bufferIn, packedLightIn);
            }
        }
    }


    protected boolean canRenderName(T entity) {
        return entity.getAlwaysRenderNameTagForRender() && entity.hasCustomName();
    }

    /**
     * Returns the location of an entity's texture.
     */
    public abstract ResourceLocation getEntityTexture(T entity);

    /**
     * Returns the font renderer from the set render manager
     */
    public FontRenderer getFontRendererFromRenderManager() {
        return this.renderManager.getFontRenderer();
    }

    protected void renderName(T entityIn, ITextComponent displayNameIn, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        double d0 = this.renderManager.squareDistanceTo(entityIn);
        boolean flag = !(d0 > 4096.0D);

        if (Reflector.ForgeHooksClient_isNameplateInRenderDistance.exists()) {
            flag = Reflector.ForgeHooksClient_isNameplateInRenderDistance.callBoolean(entityIn, d0);
        }

        if (flag) {
            boolean flag1 = !entityIn.isDiscrete();
            float f = entityIn.getHeight() + 0.5F;
            int i = "deadmau5".equals(displayNameIn.getString()) ? -10 : 0;
            matrixStackIn.push();
            matrixStackIn.translate(0.0D, f, 0.0D);
            matrixStackIn.rotate(this.renderManager.getCameraOrientation());
            matrixStackIn.scale(-0.025F, -0.025F, 0.025F);

            Matrix4f matrix4f = matrixStackIn.getLast().getMatrix();
            float f1 = Minecraft.getInstance().gameSettings.getTextBackgroundOpacity(0.25F);
            int j = (int) (f1 * 255.0F) << 24;
            FontRenderer fontrenderer = this.getFontRendererFromRenderManager();
            float f2 = (float) (-fontrenderer.getStringPropertyWidth(displayNameIn) / 2);

            //
            if (Excellent.getInst().getFriendManager() != null) {

                for (Friend friend : Excellent.getInst().getFriendManager()) {

                    if (NameProtect.singleton.get().isEnabled() && displayNameIn.getString().contains(friend.getName())) {
                        ITextComponent chatComponent = displayNameIn;
                        ITextComponent newComponent = new StringTextComponent("");
                        if (!chatComponent.getSiblings().isEmpty()) {
                            int ixx = 0;
                            for (ITextComponent iTextComponent : chatComponent.getSiblings()) {
                                if (iTextComponent.getString().contains(friend.getName())) {
                                    newComponent.getSiblings()
                                            .add(new StringTextComponent(chatComponent.getSiblings().get(ixx).getString().replaceAll(friend.getName(), NameProtect.singleton.get().name.getValue()))
                                                    .mergeStyle(chatComponent.getSiblings().get(ixx).getStyle()));
                                } else {
                                    newComponent.getSiblings().add(iTextComponent);
                                }
                                ixx++;
                            }
                        } else {
                            newComponent = newComponent.deepCopy().appendString(chatComponent.getString().replaceAll(friend.getName(), NameProtect.singleton.get().name.getValue()));
                        }
                        fontrenderer.func_243247_a(newComponent, f2, (float) i, 553648127, false, matrix4f, bufferIn, flag1, j, packedLightIn);

                        if (flag1) {
                            fontrenderer.func_243247_a(newComponent, f2, (float) i, -1, false, matrix4f, bufferIn, false, 0, packedLightIn);
                        }
                    }
                }
            }
            //

            if (NameProtect.singleton.get().isEnabled() && displayNameIn.getString().contains(mc.player.getNameClear())) {
                ITextComponent chatComponent = displayNameIn;
                ITextComponent newComponent = new StringTextComponent("");
                if (!chatComponent.getSiblings().isEmpty()) {
                    int ixx = 0;
                    for (ITextComponent iTextComponent : chatComponent.getSiblings()) {
                        if (iTextComponent.getString().contains(mc.player.getNameClear())) {
                            newComponent.getSiblings()
                                    .add(new StringTextComponent(chatComponent.getSiblings().get(ixx).getString().replaceAll(mc.player.getNameClear(), NameProtect.singleton.get().name.getValue()))
                                            .mergeStyle(chatComponent.getSiblings().get(ixx).getStyle()));
                        } else {
                            newComponent.getSiblings().add(iTextComponent);
                        }
                        ixx++;
                    }
                } else {
                    newComponent = newComponent.deepCopy().appendString(chatComponent.getString().replaceAll(mc.player.getNameClear(), NameProtect.singleton.get().name.getValue()));
                }
                fontrenderer.func_243247_a(newComponent, f2, (float) i, 553648127, false, matrix4f, bufferIn, flag1, j, packedLightIn);

                if (flag1) {
                    fontrenderer.func_243247_a(newComponent, f2, (float) i, -1, false, matrix4f, bufferIn, false, 0, packedLightIn);
                }
            } else if (Excellent.getInst().getFriendManager().stream().noneMatch(x -> TextFormatting.getTextWithoutFormattingCodes(displayNameIn.getString()).toLowerCase().contains(x.getName()))) {

                fontrenderer.func_243247_a(displayNameIn, f2, (float) i, 553648127, false, matrix4f, bufferIn, flag1, j, packedLightIn);

                if (flag1) {
                    fontrenderer.func_243247_a(displayNameIn, f2, (float) i, -1, false, matrix4f, bufferIn, false, 0, packedLightIn);
                }
            }
            matrixStackIn.pop();
        }
    }

    public EntityRendererManager getRenderManager() {
        return this.renderManager;
    }

    public Either<EntityType, TileEntityType> getType() {
        return this.entityType == null ? null : Either.makeLeft(this.entityType);
    }

    public void setType(Either<EntityType, TileEntityType> p_setType_1_) {
        this.entityType = p_setType_1_.getLeft().get();
    }

    public ResourceLocation getLocationTextureCustom() {
        return this.locationTextureCustom;
    }

    public void setLocationTextureCustom(ResourceLocation p_setLocationTextureCustom_1_) {
        this.locationTextureCustom = p_setLocationTextureCustom_1_;
    }
}
