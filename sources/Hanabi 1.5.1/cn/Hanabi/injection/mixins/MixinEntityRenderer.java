package cn.Hanabi.injection.mixins;

import cn.Hanabi.injection.interfaces.*;
import net.minecraft.client.*;
import net.minecraft.client.shader.*;
import net.minecraft.client.resources.*;
import org.apache.logging.log4j.*;
import net.minecraft.client.renderer.*;
import java.io.*;
import com.google.gson.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import cn.Hanabi.events.*;
import com.darkmagician6.eventapi.*;
import com.darkmagician6.eventapi.events.*;
import org.lwjgl.opengl.*;
import org.spongepowered.asm.mixin.injection.*;
import cn.Hanabi.modules.*;
import cn.Hanabi.modules.Combat.*;
import com.google.common.base.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import java.util.*;
import net.minecraft.util.*;
import org.spongepowered.asm.mixin.*;

@Mixin({ EntityRenderer.class })
public abstract class MixinEntityRenderer implements IEntityRenderer
{
    @Shadow
    private Minecraft mc;
    @Shadow
    private Entity pointedEntity;
    @Shadow
    private int shaderIndex;
    @Shadow
    private boolean useShader;
    @Shadow
    private ShaderGroup theShaderGroup;
    @Shadow
    private IResourceManager resourceManager;
    @Shadow
    private static Logger logger;
    @Shadow
    public static int shaderCount;
    
    @Shadow
    protected abstract void setupCameraTransform(final float p0, final int p1);
    
    @Override
    public void runSetupCameraTransform(final float partialTicks, final int pass) {
        this.setupCameraTransform(partialTicks, pass);
    }
    
    @Override
    public void loadShader2(final ResourceLocation resourceLocationIn) {
        if (OpenGlHelper.isFramebufferEnabled()) {
            try {
                (this.theShaderGroup = new ShaderGroup(this.mc.getTextureManager(), this.resourceManager, this.mc.getFramebuffer(), resourceLocationIn)).createBindFramebuffers(this.mc.displayWidth, this.mc.displayHeight);
                this.useShader = true;
            }
            catch (IOException ioexception) {
                MixinEntityRenderer.logger.warn("Failed to load shader: " + resourceLocationIn, (Throwable)ioexception);
                this.shaderIndex = MixinEntityRenderer.shaderCount;
                this.useShader = false;
            }
            catch (JsonSyntaxException jsonsyntaxexception) {
                MixinEntityRenderer.logger.warn("Failed to load shader: " + resourceLocationIn, (Throwable)jsonsyntaxexception);
                this.shaderIndex = MixinEntityRenderer.shaderCount;
                this.useShader = false;
            }
        }
    }
    
    @Inject(method = { "renderWorldPass" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;disableFog()V", shift = At.Shift.AFTER) })
    private void eventRender3D(final int pass, final float partialTicks, final long finishTimeNano, final CallbackInfo callbackInfo) {
        final EventRender eventRender = new EventRender(pass, partialTicks, finishTimeNano);
        EventManager.call(eventRender);
        GL11.glColor3f(1.0f, 1.0f, 1.0f);
    }
    
    @Overwrite
    public void getMouseOver(final float p_getMouseOver_1_) {
        final Entity entity = this.mc.getRenderViewEntity();
        if (entity != null && this.mc.theWorld != null) {
            this.mc.mcProfiler.startSection("pick");
            this.mc.pointedEntity = null;
            double d0 = ModManager.getModule("Reach").getState() ? Reach.getReach() : this.mc.playerController.getBlockReachDistance();
            this.mc.objectMouseOver = entity.rayTrace(ModManager.getModule("Reach").getState() ? Reach.getReach() : d0, p_getMouseOver_1_);
            double d2 = d0;
            final Vec3 vec3 = entity.getPositionEyes(p_getMouseOver_1_);
            boolean flag = false;
            if (this.mc.playerController.extendedReach()) {
                d0 = 6.0;
                d2 = 6.0;
            }
            else if (d0 > 3.0) {
                flag = true;
            }
            if (this.mc.objectMouseOver != null) {
                d2 = this.mc.objectMouseOver.hitVec.distanceTo(vec3);
            }
            if (ModManager.getModule("Reach").getState()) {
                d2 = Reach.getReach();
                final MovingObjectPosition vec4 = entity.rayTrace(d2, p_getMouseOver_1_);
                if (vec4 != null) {
                    d2 = vec4.hitVec.distanceTo(vec3);
                }
            }
            final Vec3 var24 = entity.getLook(p_getMouseOver_1_);
            final Vec3 vec5 = vec3.addVector(var24.xCoord * d0, var24.yCoord * d0, var24.zCoord * d0);
            this.pointedEntity = null;
            Vec3 vec6 = null;
            final float f = 1.0f;
            final List list = this.mc.theWorld.getEntitiesInAABBexcluding(entity, entity.getEntityBoundingBox().addCoord(var24.xCoord * d0, var24.yCoord * d0, var24.zCoord * d0).expand((double)f, (double)f, (double)f), Predicates.and(EntitySelectors.NOT_SPECTATING, MixinEntityRenderer::lambda$getMouseOver$0));
            double d3 = d2;
            for (int j = 0; j < list.size(); ++j) {
                final Entity entity2 = list.get(j);
                final float f2 = entity2.getCollisionBorderSize();
                final AxisAlignedBB axisalignedbb = entity2.getEntityBoundingBox().expand((double)f2, (double)f2, (double)f2);
                final MovingObjectPosition movingobjectposition = axisalignedbb.calculateIntercept(vec3, vec5);
                if (axisalignedbb.isVecInside(vec3)) {
                    if (d3 >= 0.0) {
                        this.pointedEntity = entity2;
                        vec6 = ((movingobjectposition == null) ? vec3 : movingobjectposition.hitVec);
                        d3 = 0.0;
                    }
                }
                else if (movingobjectposition != null) {
                    final double d4 = vec3.distanceTo(movingobjectposition.hitVec);
                    if (d4 < d3 || d3 == 0.0) {
                        if (entity2 == entity.ridingEntity) {
                            if (d3 == 0.0) {
                                this.pointedEntity = entity2;
                                vec6 = movingobjectposition.hitVec;
                            }
                        }
                        else {
                            this.pointedEntity = entity2;
                            vec6 = movingobjectposition.hitVec;
                            d3 = d4;
                        }
                    }
                }
            }
            if (this.pointedEntity != null && flag && vec3.distanceTo(vec6) > (ModManager.getModule("Reach").getState() ? Reach.getReach() : 3.0)) {
                this.pointedEntity = null;
                this.mc.objectMouseOver = new MovingObjectPosition(MovingObjectPosition.MovingObjectType.MISS, vec6, (EnumFacing)null, new BlockPos(vec6));
            }
            if (this.pointedEntity != null && (d3 < d2 || this.mc.objectMouseOver == null)) {
                this.mc.objectMouseOver = new MovingObjectPosition(this.pointedEntity, vec6);
                if (this.pointedEntity instanceof EntityLivingBase || this.pointedEntity instanceof EntityItemFrame) {
                    this.mc.pointedEntity = this.pointedEntity;
                }
            }
            this.mc.mcProfiler.endSection();
        }
    }
    
    private static boolean lambda$getMouseOver$0(final Entity p_apply_1_) {
        return p_apply_1_.canBeCollidedWith();
    }
}
