package maxstats.weave.mixin;

import maxstats.weave.event.RenderLivingEvent;
import me.sleepyfish.smok.Smok;
import me.sleepyfish.smok.rats.impl.other.FPS_Boost;
import me.sleepyfish.smok.rats.impl.visual.Nametags;
import me.sleepyfish.smok.utils.entities.Utils;
import me.sleepyfish.smok.utils.entities.role.RoleManager;
import me.sleepyfish.smok.utils.entities.role.RoleUser;
import me.sleepyfish.smok.utils.render.GlUtils;
import me.sleepyfish.smok.utils.render.color.ColorUtils;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Class from SMok Client by SleepyFish
@Mixin({RendererLivingEntity.class})
public abstract class MixinRenderPlayer<T extends EntityLivingBase> extends Render<T> {

    @Shadow
    protected ModelBase mainModel;

    @Shadow
    protected boolean renderOutlines;

    @Shadow
    @Final
    private static Logger logger;

    @Shadow
    protected abstract float getSwingProgress(T var1, float var2);

    @Shadow
    protected abstract float interpolateRotation(float var1, float var2, float var3);

    @Shadow
    protected abstract void renderLivingAt(T var1, double var2, double var4, double var6);

    @Shadow
    protected abstract float handleRotationFloat(T var1, float var2);

    @Shadow
    protected abstract void rotateCorpse(T var1, float var2, float var3, float var4);

    @Shadow
    protected abstract void preRenderCallback(T var1, float var2);

    @Shadow
    protected abstract boolean setScoreTeamColor(T var1);

    @Shadow
    protected abstract void renderModel(T var1, float var2, float var3, float var4, float var5, float var6, float var7);

    @Shadow
    protected abstract void unsetScoreTeamColor();

    @Shadow
    protected abstract boolean setDoRenderBrightness(T var1, float var2);

    @Shadow
    protected abstract void unsetBrightness();

    @Shadow
    protected abstract void renderLayers(T var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8);

    protected MixinRenderPlayer(RenderManager renderManager) {
        super(renderManager);
    }

    /**
     * @author sleepy fish
     * @reason pitch head rotations
     */
    @Overwrite
    public void doRender(T player, double posX, double posY, double posZ, float yaw, float partialTicks) {
        if (player == Smok.inst.mc.thePlayer) {

            GlStateManager.pushMatrix();
            GlStateManager.disableCull();
            this.mainModel.swingProgress = this.getSwingProgress(player, partialTicks);
            this.mainModel.isRiding = player.isRiding();
            this.mainModel.isChild = player.isChild();

            try {
                float var10 = this.interpolateRotation(player.prevRenderYawOffset, player.renderYawOffset, partialTicks);
                float var11 = this.interpolateRotation(player.prevRotationYawHead, player.rotationYawHead, partialTicks);
                float var12 = var11 - var10;

                if (player.isRiding() && player.ridingEntity instanceof EntityLivingBase) {
                    EntityLivingBase var13 = (EntityLivingBase) player.ridingEntity;
                    var10 = this.interpolateRotation(var13.prevRenderYawOffset, var13.renderYawOffset, partialTicks);
                    var12 = var11 - var10;
                    float var14 = MathHelper.wrapAngleTo180_float(var12);

                    if (var14 < -85.0F) {
                        var14 = -85.0F;
                    }

                    if (var14 >= 85.0F) {
                        var14 = 85.0F;
                    }

                    var10 = var11 - var14;
                    if (var14 * var14 > 2500.0F) {
                        var10 += var14 * 0.2F;
                    }
                }

                float pitch = player.rotationPitch;
                float prevPitch = player.prevRotationPitch;

                // SMok: Client sided rotation ...
                if (Smok.inst.rotManager.isRotating()) {
                    prevPitch = Smok.inst.rotManager.pitch;
                    pitch = prevPitch;
                }

                float var20 = prevPitch + (pitch - prevPitch) * partialTicks;
                this.renderLivingAt(player, posX, posY, posZ);
                float var14 = this.handleRotationFloat(player, partialTicks);
                this.rotateCorpse(player, var14, var10, partialTicks);
                GlStateManager.enableRescaleNormal();
                GlStateManager.scale(-1.0F, -1.0F, 1.0F);
                this.preRenderCallback(player, partialTicks);
                GlStateManager.translate(0.0F, -1.5078125F, 0.0F);
                float var16 = player.prevLimbSwingAmount + (player.limbSwingAmount - player.prevLimbSwingAmount) * partialTicks;
                float var17 = player.limbSwing - player.limbSwingAmount * (1.0F - partialTicks);

                if (player.isChild()) {
                    var17 *= 3.0F;
                }

                if (var16 > 1.0F) {
                    var16 = 1.0F;
                }

                GlStateManager.enableAlpha();
                this.mainModel.setLivingAnimations(player, var17, var16, partialTicks);
                this.mainModel.setRotationAngles(var17, var16, var14, var12, var20, 0.0625F, player);

                if (this.renderOutlines) {
                    boolean var18 = this.setScoreTeamColor(player);
                    this.renderModel(player, var17, var16, var14, var12, var20, 0.0625F);
                    if (var18) {
                        this.unsetScoreTeamColor();
                    }
                } else {
                    boolean var18 = this.setDoRenderBrightness(player, partialTicks);
                    this.renderModel(player, var17, var16, var14, var12, var20, 0.0625F);
                    if (var18) {
                        this.unsetBrightness();
                    }

                    GlStateManager.depthMask(true);
                    if (!((EntityPlayer) player).isSpectator()) {
                        this.renderLayers(player, var17, var16, partialTicks, var14, var12, var20, 0.0625F);
                    }
                }

                GlStateManager.disableRescaleNormal();
            } catch (Exception var201) {
                logger.error("Couldn't render entity", var201);
            }

            GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
            GlStateManager.enableTexture2D();
            GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
            GlStateManager.enableCull();
            GlStateManager.popMatrix();

            if (!this.renderOutlines) {
                super.doRender(player, posX, posY, posZ, -yaw, partialTicks);
            }
        } else {
            if (Smok.inst.ratManager.getRatByClass(FPS_Boost.class).isEnabled()) {
                if (FPS_Boost.removeEntities.isEnabled()) {
                    return;
                }
            }

            GlStateManager.pushMatrix();
            GlStateManager.disableCull();

            this.mainModel.swingProgress = this.getSwingProgress(player, partialTicks);
            this.mainModel.isRiding = player.isRiding();
            this.mainModel.isChild = player.isChild();

            try {
                float var10 = this.interpolateRotation(player.prevRenderYawOffset, player.renderYawOffset, partialTicks);
                float var11 = this.interpolateRotation(player.prevRotationYawHead, player.rotationYawHead, partialTicks);
                float var12 = var11 - var10;

                if (player.isRiding() && player.ridingEntity instanceof EntityLivingBase) {
                    EntityLivingBase var13 = (EntityLivingBase) player.ridingEntity;
                    var10 = this.interpolateRotation(var13.prevRenderYawOffset, var13.renderYawOffset, partialTicks);
                    var12 = var11 - var10;
                    float var14 = MathHelper.wrapAngleTo180_float(var12);

                    if (var14 < -85.0F) {
                        var14 = -85.0F;
                    }

                    if (var14 >= 85.0F) {
                        var14 = 85.0F;
                    }

                    var10 = var11 - var14;
                    if (var14 * var14 > 2500.0F) {
                        var10 += var14 * 0.2F;
                    }
                }

                float var20 = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * partialTicks;
                this.renderLivingAt(player, posX, posY, posZ);
                float var14 = this.handleRotationFloat(player, partialTicks);
                this.rotateCorpse(player, var14, var10, partialTicks);

                GlStateManager.enableRescaleNormal();
                GlStateManager.scale(-1.0F, -1.0F, 1.0F);
                this.preRenderCallback(player, partialTicks);
                GlStateManager.translate(0.0F, -1.5078125F, 0.0F);
                float var16 = player.prevLimbSwingAmount + (player.limbSwingAmount - player.prevLimbSwingAmount) * partialTicks;
                float var17 = player.limbSwing - player.limbSwingAmount * (1.0F - partialTicks);

                if (player.isChild()) {
                    var17 *= 3.0F;
                }

                if (var16 > 1.0F) {
                    var16 = 1.0F;
                }

                GlStateManager.enableAlpha();
                this.mainModel.setLivingAnimations(player, var17, var16, partialTicks);
                this.mainModel.setRotationAngles(var17, var16, var14, var12, var20, 0.0625F, player);

                if (this.renderOutlines) {
                    boolean var18 = this.setScoreTeamColor(player);
                    this.renderModel(player, var17, var16, var14, var12, var20, 0.0625F);

                    if (var18) {
                        this.unsetScoreTeamColor();
                    }
                } else {
                    boolean var18 = this.setDoRenderBrightness(player, partialTicks);
                    this.renderModel(player, var17, var16, var14, var12, var20, 0.0625F);

                    if (var18) {
                        this.unsetBrightness();
                    }

                    GlStateManager.depthMask(true);
                    if (!(player instanceof EntityPlayer) || !((EntityPlayer) player).isSpectator()) {
                        this.renderLayers(player, var17, var16, partialTicks, var14, var12, var20, 0.0625F);
                    }
                }

                GlStateManager.disableRescaleNormal();
            } catch (Exception var21) {
                logger.error("Couldn't render entity", var21);
            }

            GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
            GlStateManager.enableTexture2D();
            GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
            GlStateManager.enableCull();
            GlStateManager.popMatrix();

            if (!this.renderOutlines) {
                super.doRender(player, posX, posY, posZ, yaw, partialTicks);
            }
        }

        // RenderLiving event
        if (!Utils.inInv()) {
            RenderLivingEvent event = new RenderLivingEvent((RendererLivingEntity<EntityLivingBase>) (Object) this, player, posX, posY, posZ, partialTicks);

            if (!event.isCancelled()) {
                event.call();
            }

            ColorUtils.setColor(Smok.inst.colManager.getColorMode().getColor1());
            ColorUtils.clearColor();
        }
    }

    /*
    @Inject(method = "renderName(Lnet/minecraft/entity/EntityLivingBase;DDD)V", at = @At("HEAD"), cancellable = true)
    public void renderNameHead(T entity, double x, double y, double z, CallbackInfo ci) {
        if (Smok.inst.ratManager.getRatByClass(Nametags.class).isEnabled()) {
            if (Nametags.autoScale.isEnabled()) {
                double scale = Smok.inst.mc.thePlayer.getDistanceSqToEntity(entity);
                GlUtils.startScale((float) scale / 0.5F);
            } else {
                GlUtils.startScale(Nametags.scale.getValueToFloat());
            }

            if (Nametags.throughWalls.isEnabled()) {
                GlUtils.enableChamsSee();
            }

            if (Nametags.otherSmokUsers.isEnabled()) {
                String s = entity.getName();

                for (RoleUser user : Smok.inst.roleManager.getPlayersWithRole()) {
                    if (entity.getName().equalsIgnoreCase(user.getName())) {
                        if (Smok.inst.mc.thePlayer.getName().equalsIgnoreCase(user.getName())) {
                            ci.cancel();
                            return;
                        }

                        if (this.canRenderName(entity)) {
                            double distance = entity.getDistanceSqToEntity(this.renderManager.livingPlayer);
                            float sneakingAlpha = entity.isSneaking() ? 32.0F : 64.0F;

                            if (distance < (double) (sneakingAlpha * sneakingAlpha)) {
                                if (user.getName().equalsIgnoreCase(entity.getName())) {
                                    if (user.getRole() == RoleManager.Role.YouTuber) {
                                        s = EnumChatFormatting.GRAY + "[" + EnumChatFormatting.RED + "You" + EnumChatFormatting.WHITE + "Tube" + EnumChatFormatting.GRAY + "]" + EnumChatFormatting.RESET + " " + entity.getDisplayName().getFormattedText();
                                    }

                                    if (user.getRole() == RoleManager.Role.User) {
                                        s = EnumChatFormatting.GRAY + "[" + EnumChatFormatting.GOLD + "Smok User" + EnumChatFormatting.GRAY + "]" + EnumChatFormatting.RESET + " " + entity.getDisplayName().getFormattedText();
                                    }

                                    if (user.getRole() == RoleManager.Role.Developer) {
                                        s = EnumChatFormatting.GRAY + "[" + EnumChatFormatting.DARK_PURPLE + "Smok Dev" + EnumChatFormatting.GRAY + "]" + EnumChatFormatting.RESET + " " + entity.getDisplayName().getFormattedText();
                                    }
                                }

                                if (Nametags.distance.isEnabled()) {
                                    s += " | " + entity.getHealth();
                                }

                                if (Nametags.health.isEnabled()) {
                                    s = Math.round(distance) + " | " + s;
                                }

                                GlStateManager.alphaFunc(516, 0.1F);
                                float scaleFactor = 0.02666667F;

                                if (entity.isSneaking()) {
                                    if (Nametags.sneakNormal.isEnabled()) {
                                        this.renderOffsetLivingLabel((T) entity, x, y - (entity.isChild() ? (double) (entity.height / 2.0F) : 0.0D), z, s, scaleFactor, distance);
                                        return;
                                    }

                                    FontRenderer fontrenderer = this.getFontRendererFromRenderManager();
                                    GlStateManager.pushMatrix();
                                    GlStateManager.translate((float) x, (float) y + entity.height + 0.5F - (entity.isChild() ? entity.height / 2.0F : 0.0F), (float) z);
                                    GL11.glNormal3f(0.0F, 1.0F, 0.0F);
                                    GlStateManager.rotate(-this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
                                    GlStateManager.rotate(this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
                                    GlStateManager.scale(-scaleFactor, -scaleFactor, scaleFactor);
                                    GlStateManager.translate(0.0F, 9.374999F, 0.0F);
                                    GlStateManager.disableLighting();
                                    GlStateManager.depthMask(false);
                                    GlStateManager.enableBlend();
                                    GlStateManager.disableTexture2D();
                                    GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
                                    int i = fontrenderer.getStringWidth(s) / 2;
                                    Tessellator tessellator = Tessellator.getInstance();
                                    WorldRenderer worldrenderer = tessellator.getWorldRenderer();
                                    worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
                                    worldrenderer.pos(-i - 1, -1.0D, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
                                    worldrenderer.pos(-i - 1, 8.0D, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
                                    worldrenderer.pos(i + 1, 8.0D, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
                                    worldrenderer.pos(i + 1, -1.0D, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
                                    tessellator.draw();
                                    GlStateManager.enableTexture2D();
                                    GlStateManager.depthMask(true);

                                    fontrenderer.drawString(s, -fontrenderer.getStringWidth(s) / 2, 0, 553648127);

                                    GlStateManager.enableLighting();
                                    GlStateManager.disableBlend();
                                    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                                    GlStateManager.popMatrix();
                                } else {
                                    this.renderOffsetLivingLabel((T) entity, x, y - (entity.isChild() ? (double) (entity.height / 2.0F) : 0.0D), z, s, scaleFactor, distance);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
     */

    @Inject(method = "renderName(Lnet/minecraft/entity/EntityLivingBase;DDD)V", at = @At("HEAD"), cancellable = true)
    public void renderNameHead(T entity, double x, double y, double z, CallbackInfo ci) {
        // if nametags is enabled
        if (Smok.inst.ratManager.getRatByClass(Nametags.class).isEnabled()) {

            // Auto scale
            if (Nametags.autoScale.isEnabled()) {
                double scale = Smok.inst.mc.thePlayer.getDistanceSqToEntity(entity);
                GlUtils.startScale((float) scale / 0.5F);
            } else {
                GlUtils.startScale(Nametags.scale.getValueToFloat());
            }

            // chams visibility
            if (Nametags.throughWalls.isEnabled()) {
                GlUtils.enableChamsSee();
            }


            if (Nametags.otherSmokUsers.isEnabled()) {
                String s = entity.getName();

                for (RoleUser user : Smok.inst.roleManager.getPlayersWithRole()) {
                    if (entity.getName().equalsIgnoreCase(user.getName())) {
                        if (Smok.inst.mc.thePlayer.getName().equalsIgnoreCase(user.getName())) {
                            ci.cancel();
                            return;
                        }

                        if (this.canRenderName(entity)) {
                            double distance = entity.getDistanceSqToEntity(this.renderManager.livingPlayer);
                            float sneakingAlpha = entity.isSneaking() ? 32.0F : 64.0F;

                            if (distance < (double) (sneakingAlpha * sneakingAlpha)) {
                                if (user.getName().equalsIgnoreCase(entity.getName())) {
                                    if (user.getRole() == RoleManager.Role.YouTuber) {
                                        s = EnumChatFormatting.GRAY + "[" + EnumChatFormatting.RED + "You" + EnumChatFormatting.WHITE + "Tube" + EnumChatFormatting.GRAY + "]" + EnumChatFormatting.RESET + " " + entity.getDisplayName().getFormattedText();
                                    }

                                    if (user.getRole() == RoleManager.Role.User) {
                                        s = EnumChatFormatting.GRAY + "[" + EnumChatFormatting.GOLD + "Smok User" + EnumChatFormatting.GRAY + "]" + EnumChatFormatting.RESET + " " + entity.getDisplayName().getFormattedText();
                                    }

                                    if (user.getRole() == RoleManager.Role.Developer) {
                                        s = EnumChatFormatting.GRAY + "[" + EnumChatFormatting.DARK_PURPLE + "Smok Dev" + EnumChatFormatting.GRAY + "]" + EnumChatFormatting.RESET + " " + entity.getDisplayName().getFormattedText();
                                    }
                                }

                                if (Nametags.distance.isEnabled()) {
                                    s += " | " + entity.getHealth();
                                }

                                if (Nametags.health.isEnabled()) {
                                    s = Math.round(distance) + " | " + s;
                                }

                                GlStateManager.alphaFunc(516, 0.1F);
                                float scaleFactor = 0.02666667F;

                                if (entity.isSneaking()) {
                                    if (Nametags.sneakNormal.isEnabled()) {
                                        this.renderOffsetLivingLabel((T) entity, x, y - (entity.isChild() ? (double) (entity.height / 2.0F) : 0.0D), z, s, scaleFactor, distance);
                                        return;
                                    }

                                    FontRenderer fontrenderer = this.getFontRendererFromRenderManager();
                                    GlStateManager.pushMatrix();
                                    GlStateManager.translate((float) x, (float) y + entity.height + 0.5F - (entity.isChild() ? entity.height / 2.0F : 0.0F), (float) z);
                                    GL11.glNormal3f(0.0F, 1.0F, 0.0F);
                                    GlStateManager.rotate(-this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
                                    GlStateManager.rotate(this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
                                    GlStateManager.scale(-scaleFactor, -scaleFactor, scaleFactor);
                                    GlStateManager.translate(0.0F, 9.374999F, 0.0F);
                                    GlStateManager.disableLighting();
                                    GlStateManager.depthMask(false);
                                    GlStateManager.enableBlend();
                                    GlStateManager.disableTexture2D();
                                    GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
                                    int i = fontrenderer.getStringWidth(s) / 2;
                                    Tessellator tessellator = Tessellator.getInstance();
                                    WorldRenderer worldrenderer = tessellator.getWorldRenderer();
                                    worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
                                    worldrenderer.pos(-i - 1, -1.0D, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
                                    worldrenderer.pos(-i - 1, 8.0D, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
                                    worldrenderer.pos(i + 1, 8.0D, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
                                    worldrenderer.pos(i + 1, -1.0D, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
                                    tessellator.draw();
                                    GlStateManager.enableTexture2D();
                                    GlStateManager.depthMask(true);

                                    fontrenderer.drawString(s, -fontrenderer.getStringWidth(s) / 2, 0, 553648127);

                                    GlStateManager.enableLighting();
                                    GlStateManager.disableBlend();
                                    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                                    GlStateManager.popMatrix();
                                } else {
                                    this.renderOffsetLivingLabel((T) entity, x, y - (entity.isChild() ? (double) (entity.height / 2.0F) : 0.0D), z, s, scaleFactor, distance);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Inject(method = "renderName(Lnet/minecraft/entity/EntityLivingBase;DDD)V", at = @At("TAIL"))
    public void renderNameTail(T entity, double x, double y, double z, CallbackInfo ci) {
        if (Smok.inst.ratManager.getRatByClass(Nametags.class).isEnabled()) {
            GlUtils.stopScale();

            if (Nametags.throughWalls.isEnabled()) {
                GlUtils.disableChamsSee();
            }
        }
    }

}