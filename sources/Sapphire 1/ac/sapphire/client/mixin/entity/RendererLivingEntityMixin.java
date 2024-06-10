package ac.sapphire.client.mixin.entity;

import ac.sapphire.client.Sapphire;
import ac.sapphire.client.event.events.render.NametagRenderEvent;
import ac.sapphire.client.module.impl.visual.chams.ChamsModule;
import ac.sapphire.client.module.impl.visual.chams.ColoredChamsMode;
import ac.sapphire.client.module.impl.visual.chams.NormalChamsMode;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.EntityLivingBase;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.minecraft.client.renderer.GlStateManager.*;
import static net.minecraft.client.renderer.OpenGlHelper.setLightmapTextureCoords;
import static org.lwjgl.opengl.GL11.*;

@Mixin(RendererLivingEntity.class)
public abstract class RendererLivingEntityMixin<T extends EntityLivingBase> extends Render<T> {
    @Shadow
    protected ModelBase mainModel;

    protected RendererLivingEntityMixin(RenderManager renderManager) {
        super(renderManager);
    }

    /**
     * @author Starlight Technology LLC
     */
    @Overwrite
    protected void renderModel(T entitylivingbaseIn, float p_77036_2_, float p_77036_3_, float p_77036_4_, float p_77036_5_, float p_77036_6_, float scaleFactor) {
        boolean flag = !entitylivingbaseIn.isInvisible();
        boolean flag1 = !flag && !entitylivingbaseIn.isInvisibleToPlayer(Minecraft.getMinecraft().thePlayer);

        if (flag || flag1) {
            if (!this.bindEntityTexture(entitylivingbaseIn)) {
                return;
            }

            if (flag1) {
                pushMatrix();
                color(1.0F, 1.0F, 1.0F, 0.15F);
                depthMask(false);
                enableBlend();
                blendFunc(770, 771);
                alphaFunc(516, 0.003921569F);
            }

            ChamsModule chams = Sapphire.INSTANCE.getModuleManager().get(ChamsModule.class);
            if (chams.isToggled()) {
                if (chams.getCurrentMode() instanceof ColoredChamsMode) {
                    ColoredChamsMode mode = (ColoredChamsMode) chams.getCurrentMode();

                    glPushAttrib(GL11.GL_ALL_CLIENT_ATTRIB_BITS);
                    enableBlend();
                    glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                    disableTexture2D();
                    if (!mode.getMaterial()) {
                        disableLighting();
                    }

                    int hiddenRgb = mode.getHiddenColor().getRGB();
                    float hiddenR = (hiddenRgb >> 16 & 255) / 255.0F;
                    float hiddenG = (hiddenRgb >> 8 & 255) / 255.0F;
                    float hiddenB = (hiddenRgb & 255) / 255.0F;
                    glColor4f(hiddenR, hiddenG, hiddenB, 1);

                    setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240, 240);
                    disableDepth();
                    this.mainModel.render(entitylivingbaseIn, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, scaleFactor);
                    enableDepth();

                    int visibleRgb = mode.getVisibleColor().getRGB();
                    float visibleR = (visibleRgb >> 16 & 255) / 255.0F;
                    float visibleG = (visibleRgb >> 8 & 255) / 255.0F;
                    float visibleB = (visibleRgb & 255) / 255.0F;
                    glColor4f(visibleR, visibleG, visibleB, 1);
                    this.mainModel.render(entitylivingbaseIn, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, scaleFactor);

                    enableLighting();
                    enableTexture2D();
                    disableBlend();
                    glPopAttrib();
                } else if (chams.getCurrentMode() instanceof NormalChamsMode) {
                    glEnable(GL_POLYGON_OFFSET_FILL);
                    glPolygonOffset(1, -1100000);
                    this.mainModel.render(entitylivingbaseIn, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, scaleFactor);
                    glDisable(GL_POLYGON_OFFSET_FILL);
                    glPolygonOffset(1, 1100000);
                }
            } else {
                this.mainModel.render(entitylivingbaseIn, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, scaleFactor);
            }

            if (flag1) {
                disableBlend();
                GlStateManager.alphaFunc(516, 0.1F);
                GlStateManager.popMatrix();
                GlStateManager.depthMask(true);
            }
        }
    }

    @Inject(method = "renderName(Lnet/minecraft/entity/EntityLivingBase;DDD)V", at = @At("HEAD"), cancellable = true)
    void renderName(T entity, double x, double y, double z, CallbackInfo ci) {
        NametagRenderEvent event = new NametagRenderEvent(entity, x, y, z, false);
        Sapphire.INSTANCE.getEventBus().post(event);
        if (event.getCancelled()) {
            ci.cancel();
        }
    }
}
