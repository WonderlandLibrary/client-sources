package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.entity.layers.*;
import java.nio.*;
import net.minecraft.entity.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.gui.*;
import java.util.*;
import net.minecraft.client.*;
import net.minecraft.client.entity.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.client.renderer.*;
import com.google.common.collect.*;
import net.minecraft.scoreboard.*;
import org.apache.logging.log4j.*;

public abstract class RendererLivingEntity<T extends EntityLivingBase> extends Render<T>
{
    private static final DynamicTexture field_177096_e;
    private static int[] $SWITCH_TABLE$net$minecraft$scoreboard$Team$EnumVisible;
    protected boolean renderOutlines;
    protected ModelBase mainModel;
    private static final String[] I;
    protected List<LayerRenderer<T>> layerRenderers;
    protected FloatBuffer brightnessBuffer;
    private static final Logger logger;
    
    protected float getSwingProgress(final T t, final float n) {
        return t.getSwingProgress(n);
    }
    
    public void renderName(final Entity entity, final double n, final double n2, final double n3) {
        this.renderName((EntityLivingBase)entity, n, n2, n3);
    }
    
    public void renderName(final T t, final double n, final double n2, final double n3) {
        if (this.canRenderName(t)) {
            final double distanceSqToEntity = t.getDistanceSqToEntity(this.renderManager.livingPlayer);
            float n4;
            if (t.isSneaking()) {
                n4 = 32.0f;
                "".length();
                if (2 <= 0) {
                    throw null;
                }
            }
            else {
                n4 = 64.0f;
            }
            final float n5 = n4;
            if (distanceSqToEntity < n5 * n5) {
                final String formattedText = t.getDisplayName().getFormattedText();
                GlStateManager.alphaFunc(51 + 490 - 138 + 113, 0.1f);
                if (t.isSneaking()) {
                    final FontRenderer fontRendererFromRenderManager = this.getFontRendererFromRenderManager();
                    GlStateManager.pushMatrix();
                    final float n6 = (float)n;
                    final float n7 = (float)n2 + t.height + 0.5f;
                    float n8;
                    if (t.isChild()) {
                        n8 = t.height / 2.0f;
                        "".length();
                        if (-1 >= 3) {
                            throw null;
                        }
                    }
                    else {
                        n8 = 0.0f;
                    }
                    GlStateManager.translate(n6, n7 - n8, (float)n3);
                    GL11.glNormal3f(0.0f, 1.0f, 0.0f);
                    GlStateManager.rotate(-this.renderManager.playerViewY, 0.0f, 1.0f, 0.0f);
                    GlStateManager.rotate(this.renderManager.playerViewX, 1.0f, 0.0f, 0.0f);
                    GlStateManager.scale(-0.02666667f, -0.02666667f, 0.02666667f);
                    GlStateManager.translate(0.0f, 9.374999f, 0.0f);
                    GlStateManager.disableLighting();
                    GlStateManager.depthMask("".length() != 0);
                    GlStateManager.enableBlend();
                    GlStateManager.disableTexture2D();
                    GlStateManager.tryBlendFuncSeparate(307 + 108 + 106 + 249, 464 + 671 - 654 + 290, " ".length(), "".length());
                    final int n9 = fontRendererFromRenderManager.getStringWidth(formattedText) / "  ".length();
                    final Tessellator instance = Tessellator.getInstance();
                    final WorldRenderer worldRenderer = instance.getWorldRenderer();
                    worldRenderer.begin(0x63 ^ 0x64, DefaultVertexFormats.POSITION_COLOR);
                    worldRenderer.pos(-n9 - " ".length(), -1.0, 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex();
                    worldRenderer.pos(-n9 - " ".length(), 8.0, 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex();
                    worldRenderer.pos(n9 + " ".length(), 8.0, 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex();
                    worldRenderer.pos(n9 + " ".length(), -1.0, 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex();
                    instance.draw();
                    GlStateManager.enableTexture2D();
                    GlStateManager.depthMask(" ".length() != 0);
                    fontRendererFromRenderManager.drawString(formattedText, -fontRendererFromRenderManager.getStringWidth(formattedText) / "  ".length(), "".length(), 482088551 + 469526142 - 813711366 + 415744800);
                    GlStateManager.enableLighting();
                    GlStateManager.disableBlend();
                    GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                    GlStateManager.popMatrix();
                    "".length();
                    if (0 >= 1) {
                        throw null;
                    }
                }
                else {
                    double n10;
                    if (t.isChild()) {
                        n10 = t.height / 2.0f;
                        "".length();
                        if (3 <= -1) {
                            throw null;
                        }
                    }
                    else {
                        n10 = 0.0;
                    }
                    this.renderOffsetLivingLabel(t, n, n2 - n10, n3, formattedText, 0.02666667f, distanceSqToEntity);
                }
            }
        }
    }
    
    protected void renderLayers(final T t, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7) {
        final Iterator<LayerRenderer<T>> iterator = this.layerRenderers.iterator();
        "".length();
        if (false == true) {
            throw null;
        }
        while (iterator.hasNext()) {
            final LayerRenderer<T> layerRenderer = iterator.next();
            final boolean setBrightness = this.setBrightness(t, n3, layerRenderer.shouldCombineTextures());
            layerRenderer.doRenderLayer(t, n, n2, n3, n4, n5, n6, n7);
            if (setBrightness) {
                this.unsetBrightness();
            }
        }
    }
    
    protected float handleRotationFloat(final T t, final float n) {
        return t.ticksExisted + n;
    }
    
    private static void I() {
        (I = new String["   ".length()])["".length()] = I("\u0004\b\u0005>\u0001)@\u0004r\u0017\"\t\u00147\u0017g\u0002\u001e&\f3\u001e", "GgpRe");
        RendererLivingEntity.I[" ".length()] = I("<9\u0004$3\n2\u0005$3", "xPjJV");
        RendererLivingEntity.I["  ".length()] = I("\u0014\u0010\r\u00199", "SbxtT");
    }
    
    @Override
    protected boolean canRenderName(final T t) {
        final EntityPlayerSP thePlayer = Minecraft.getMinecraft().thePlayer;
        if (t instanceof EntityPlayer && t != thePlayer) {
            final Team team = t.getTeam();
            final Team team2 = thePlayer.getTeam();
            if (team != null) {
                switch ($SWITCH_TABLE$net$minecraft$scoreboard$Team$EnumVisible()[team.getNameTagVisibility().ordinal()]) {
                    case 1: {
                        return " ".length() != 0;
                    }
                    case 2: {
                        return "".length() != 0;
                    }
                    case 3: {
                        if (team2 != null && !team.isSameTeam(team2)) {
                            return "".length() != 0;
                        }
                        return " ".length() != 0;
                    }
                    case 4: {
                        if (team2 != null && team.isSameTeam(team2)) {
                            return "".length() != 0;
                        }
                        return " ".length() != 0;
                    }
                    default: {
                        return " ".length() != 0;
                    }
                }
            }
        }
        if (Minecraft.isGuiEnabled() && t != this.renderManager.livingPlayer && !t.isInvisibleToPlayer(thePlayer) && t.riddenByEntity == null) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    protected void renderModel(final T t, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        int n7;
        if (t.isInvisible()) {
            n7 = "".length();
            "".length();
            if (3 <= -1) {
                throw null;
            }
        }
        else {
            n7 = " ".length();
        }
        final int n8 = n7;
        int n9;
        if (n8 == 0 && !t.isInvisibleToPlayer(Minecraft.getMinecraft().thePlayer)) {
            n9 = " ".length();
            "".length();
            if (-1 == 0) {
                throw null;
            }
        }
        else {
            n9 = "".length();
        }
        final int n10 = n9;
        if (n8 != 0 || n10 != 0) {
            if (!this.bindEntityTexture(t)) {
                return;
            }
            if (n10 != 0) {
                GlStateManager.pushMatrix();
                GlStateManager.color(1.0f, 1.0f, 1.0f, 0.15f);
                GlStateManager.depthMask("".length() != 0);
                GlStateManager.enableBlend();
                GlStateManager.blendFunc(16 + 295 + 269 + 190, 169 + 174 + 227 + 201);
                GlStateManager.alphaFunc(34 + 488 - 274 + 268, 0.003921569f);
            }
            this.mainModel.render(t, n, n2, n3, n4, n5, n6);
            if (n10 != 0) {
                GlStateManager.disableBlend();
                GlStateManager.alphaFunc(77 + 419 - 288 + 308, 0.1f);
                GlStateManager.popMatrix();
                GlStateManager.depthMask(" ".length() != 0);
            }
        }
    }
    
    public void setRenderOutlines(final boolean renderOutlines) {
        this.renderOutlines = renderOutlines;
    }
    
    protected <V extends EntityLivingBase, U extends LayerRenderer<V>> boolean addLayer(final U u) {
        return this.layerRenderers.add((LayerRenderer<T>)u);
    }
    
    protected void renderLivingAt(final T t, final double n, final double n2, final double n3) {
        GlStateManager.translate((float)n, (float)n2, (float)n3);
    }
    
    protected void unsetScoreTeamColor() {
        GlStateManager.enableLighting();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
        GlStateManager.enableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.enableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }
    
    protected void rotateCorpse(final T t, final float n, final float n2, final float n3) {
        GlStateManager.rotate(180.0f - n2, 0.0f, 1.0f, 0.0f);
        if (t.deathTime > 0) {
            float sqrt_float = MathHelper.sqrt_float((t.deathTime + n3 - 1.0f) / 20.0f * 1.6f);
            if (sqrt_float > 1.0f) {
                sqrt_float = 1.0f;
            }
            GlStateManager.rotate(sqrt_float * this.getDeathMaxRotation(t), 0.0f, 0.0f, 1.0f);
            "".length();
            if (3 >= 4) {
                throw null;
            }
        }
        else {
            final String textWithoutFormattingCodes = EnumChatFormatting.getTextWithoutFormattingCodes(t.getName());
            if (textWithoutFormattingCodes != null && (textWithoutFormattingCodes.equals(RendererLivingEntity.I[" ".length()]) || textWithoutFormattingCodes.equals(RendererLivingEntity.I["  ".length()])) && (!(t instanceof EntityPlayer) || ((EntityPlayer)t).isWearing(EnumPlayerModelParts.CAPE))) {
                GlStateManager.translate(0.0f, t.height + 0.1f, 0.0f);
                GlStateManager.rotate(180.0f, 0.0f, 0.0f, 1.0f);
            }
        }
    }
    
    protected float getDeathMaxRotation(final T t) {
        return 90.0f;
    }
    
    protected void preRenderCallback(final T t, final float n) {
    }
    
    protected float interpolateRotation(final float n, final float n2, final float n3) {
        float n4 = n2 - n;
        "".length();
        if (1 < 0) {
            throw null;
        }
        while (n4 < -180.0f) {
            n4 += 360.0f;
        }
        "".length();
        if (3 != 3) {
            throw null;
        }
        while (n4 >= 180.0f) {
            n4 -= 360.0f;
        }
        return n + n3 * n4;
    }
    
    protected <V extends EntityLivingBase, U extends LayerRenderer<V>> boolean removeLayer(final U u) {
        return this.layerRenderers.remove(u);
    }
    
    @Override
    public void doRender(final T scoreTeamColor, final double n, final double n2, final double n3, final float n4, final float n5) {
        GlStateManager.pushMatrix();
        GlStateManager.disableCull();
        this.mainModel.swingProgress = this.getSwingProgress(scoreTeamColor, n5);
        this.mainModel.isRiding = scoreTeamColor.isRiding();
        this.mainModel.isChild = scoreTeamColor.isChild();
        try {
            float interpolateRotation = this.interpolateRotation(scoreTeamColor.prevRenderYawOffset, scoreTeamColor.renderYawOffset, n5);
            final float interpolateRotation2 = this.interpolateRotation(scoreTeamColor.prevRotationYawHead, scoreTeamColor.rotationYawHead, n5);
            float n6 = interpolateRotation2 - interpolateRotation;
            if (scoreTeamColor.isRiding() && scoreTeamColor.ridingEntity instanceof EntityLivingBase) {
                final EntityLivingBase entityLivingBase = (EntityLivingBase)scoreTeamColor.ridingEntity;
                n6 = interpolateRotation2 - this.interpolateRotation(entityLivingBase.prevRenderYawOffset, entityLivingBase.renderYawOffset, n5);
                float wrapAngleTo180_float = MathHelper.wrapAngleTo180_float(n6);
                if (wrapAngleTo180_float < -85.0f) {
                    wrapAngleTo180_float = -85.0f;
                }
                if (wrapAngleTo180_float >= 85.0f) {
                    wrapAngleTo180_float = 85.0f;
                }
                interpolateRotation = interpolateRotation2 - wrapAngleTo180_float;
                if (wrapAngleTo180_float * wrapAngleTo180_float > 2500.0f) {
                    interpolateRotation += wrapAngleTo180_float * 0.2f;
                }
            }
            final float n7 = scoreTeamColor.prevRotationPitch + (scoreTeamColor.rotationPitch - scoreTeamColor.prevRotationPitch) * n5;
            this.renderLivingAt(scoreTeamColor, n, n2, n3);
            final float handleRotationFloat = this.handleRotationFloat(scoreTeamColor, n5);
            this.rotateCorpse(scoreTeamColor, handleRotationFloat, interpolateRotation, n5);
            GlStateManager.enableRescaleNormal();
            GlStateManager.scale(-1.0f, -1.0f, 1.0f);
            this.preRenderCallback(scoreTeamColor, n5);
            GlStateManager.translate(0.0f, -1.5078125f, 0.0f);
            float n8 = scoreTeamColor.prevLimbSwingAmount + (scoreTeamColor.limbSwingAmount - scoreTeamColor.prevLimbSwingAmount) * n5;
            float n9 = scoreTeamColor.limbSwing - scoreTeamColor.limbSwingAmount * (1.0f - n5);
            if (scoreTeamColor.isChild()) {
                n9 *= 3.0f;
            }
            if (n8 > 1.0f) {
                n8 = 1.0f;
            }
            GlStateManager.enableAlpha();
            this.mainModel.setLivingAnimations(scoreTeamColor, n9, n8, n5);
            this.mainModel.setRotationAngles(n9, n8, handleRotationFloat, n6, n7, 0.0625f, scoreTeamColor);
            if (this.renderOutlines) {
                final boolean setScoreTeamColor = this.setScoreTeamColor(scoreTeamColor);
                this.renderModel(scoreTeamColor, n9, n8, handleRotationFloat, n6, n7, 0.0625f);
                if (setScoreTeamColor) {
                    this.unsetScoreTeamColor();
                    "".length();
                    if (1 <= 0) {
                        throw null;
                    }
                }
            }
            else {
                final boolean setDoRenderBrightness = this.setDoRenderBrightness(scoreTeamColor, n5);
                this.renderModel(scoreTeamColor, n9, n8, handleRotationFloat, n6, n7, 0.0625f);
                if (setDoRenderBrightness) {
                    this.unsetBrightness();
                }
                GlStateManager.depthMask(" ".length() != 0);
                if (!(scoreTeamColor instanceof EntityPlayer) || !((EntityPlayer)scoreTeamColor).isSpectator()) {
                    this.renderLayers(scoreTeamColor, n9, n8, n5, handleRotationFloat, n6, n7, 0.0625f);
                }
            }
            GlStateManager.disableRescaleNormal();
            "".length();
            if (3 != 3) {
                throw null;
            }
        }
        catch (Exception ex) {
            RendererLivingEntity.logger.error(RendererLivingEntity.I["".length()], (Throwable)ex);
        }
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.enableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
        GlStateManager.enableCull();
        GlStateManager.popMatrix();
        if (!this.renderOutlines) {
            super.doRender(scoreTeamColor, n, n2, n3, n4, n5);
        }
    }
    
    protected int getColorMultiplier(final T t, final float n, final float n2) {
        return "".length();
    }
    
    public RendererLivingEntity(final RenderManager renderManager, final ModelBase mainModel, final float shadowSize) {
        super(renderManager);
        this.brightnessBuffer = GLAllocation.createDirectFloatBuffer(0xBC ^ 0xB8);
        this.layerRenderers = (List<LayerRenderer<T>>)Lists.newArrayList();
        this.renderOutlines = ("".length() != 0);
        this.mainModel = mainModel;
        this.shadowSize = shadowSize;
    }
    
    public ModelBase getMainModel() {
        return this.mainModel;
    }
    
    @Override
    protected boolean canRenderName(final Entity entity) {
        return this.canRenderName((EntityLivingBase)entity);
    }
    
    protected boolean setBrightness(final T t, final float n, final boolean b) {
        final int colorMultiplier = this.getColorMultiplier(t, t.getBrightness(n), n);
        int n2;
        if ((colorMultiplier >> (0xB1 ^ 0xA9) & 218 + 137 - 228 + 128) > 0) {
            n2 = " ".length();
            "".length();
            if (1 >= 2) {
                throw null;
            }
        }
        else {
            n2 = "".length();
        }
        final int n3 = n2;
        int n4;
        if (t.hurtTime <= 0 && t.deathTime <= 0) {
            n4 = "".length();
            "".length();
            if (1 < -1) {
                throw null;
            }
        }
        else {
            n4 = " ".length();
        }
        final int n5 = n4;
        if (n3 == 0 && n5 == 0) {
            return "".length() != 0;
        }
        if (n3 == 0 && !b) {
            return "".length() != 0;
        }
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
        GlStateManager.enableTexture2D();
        GL11.glTexEnvi(1096 + 3553 - 2357 + 6668, 908 + 470 + 3876 + 3450, OpenGlHelper.GL_COMBINE);
        GL11.glTexEnvi(7036 + 7846 - 14136 + 8214, OpenGlHelper.GL_COMBINE_RGB, 2472 + 1417 + 4276 + 283);
        GL11.glTexEnvi(3101 + 460 + 5269 + 130, OpenGlHelper.GL_SOURCE0_RGB, OpenGlHelper.defaultTexUnit);
        GL11.glTexEnvi(1883 + 4265 - 712 + 3524, OpenGlHelper.GL_SOURCE1_RGB, OpenGlHelper.GL_PRIMARY_COLOR);
        GL11.glTexEnvi(6169 + 3277 - 7236 + 6750, OpenGlHelper.GL_OPERAND0_RGB, 514 + 119 + 15 + 120);
        GL11.glTexEnvi(1835 + 3913 - 2013 + 5225, OpenGlHelper.GL_OPERAND1_RGB, 583 + 204 - 23 + 4);
        GL11.glTexEnvi(7607 + 3580 - 6427 + 4200, OpenGlHelper.GL_COMBINE_ALPHA, 4337 + 5202 - 3167 + 1309);
        GL11.glTexEnvi(6746 + 6738 - 9695 + 5171, OpenGlHelper.GL_SOURCE0_ALPHA, OpenGlHelper.defaultTexUnit);
        GL11.glTexEnvi(2578 + 3341 - 2071 + 5112, OpenGlHelper.GL_OPERAND0_ALPHA, 71 + 399 + 122 + 178);
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.enableTexture2D();
        GL11.glTexEnvi(841 + 426 - 1089 + 8782, 8681 + 3880 - 10185 + 6328, OpenGlHelper.GL_COMBINE);
        GL11.glTexEnvi(2126 + 5365 - 1952 + 3421, OpenGlHelper.GL_COMBINE_RGB, OpenGlHelper.GL_INTERPOLATE);
        GL11.glTexEnvi(6641 + 5357 - 7180 + 4142, OpenGlHelper.GL_SOURCE0_RGB, OpenGlHelper.GL_CONSTANT);
        GL11.glTexEnvi(7247 + 8036 - 11129 + 4806, OpenGlHelper.GL_SOURCE1_RGB, OpenGlHelper.GL_PREVIOUS);
        GL11.glTexEnvi(3318 + 5726 - 4736 + 4652, OpenGlHelper.GL_SOURCE2_RGB, OpenGlHelper.GL_CONSTANT);
        GL11.glTexEnvi(6167 + 5296 - 9894 + 7391, OpenGlHelper.GL_OPERAND0_RGB, 474 + 586 - 674 + 382);
        GL11.glTexEnvi(693 + 4369 - 4580 + 8478, OpenGlHelper.GL_OPERAND1_RGB, 36 + 392 - 221 + 561);
        GL11.glTexEnvi(6775 + 1803 - 5159 + 5541, OpenGlHelper.GL_OPERAND2_RGB, 42 + 306 + 400 + 22);
        GL11.glTexEnvi(1650 + 7028 - 7388 + 7670, OpenGlHelper.GL_COMBINE_ALPHA, 7461 + 2387 - 9584 + 7417);
        GL11.glTexEnvi(349 + 7332 - 640 + 1919, OpenGlHelper.GL_SOURCE0_ALPHA, OpenGlHelper.GL_PREVIOUS);
        GL11.glTexEnvi(5343 + 4538 - 9045 + 8124, OpenGlHelper.GL_OPERAND0_ALPHA, 627 + 133 - 651 + 661);
        this.brightnessBuffer.position("".length());
        if (n5 != 0) {
            this.brightnessBuffer.put(1.0f);
            this.brightnessBuffer.put(0.0f);
            this.brightnessBuffer.put(0.0f);
            this.brightnessBuffer.put(0.3f);
            "".length();
            if (2 < -1) {
                throw null;
            }
        }
        else {
            final float n6 = (colorMultiplier >> (0x54 ^ 0x4C) & 199 + 249 - 311 + 118) / 255.0f;
            final float n7 = (colorMultiplier >> (0x56 ^ 0x46) & 184 + 93 - 126 + 104) / 255.0f;
            final float n8 = (colorMultiplier >> (0x59 ^ 0x51) & 24 + 58 + 22 + 151) / 255.0f;
            final float n9 = (colorMultiplier & 135 + 179 - 127 + 68) / 255.0f;
            this.brightnessBuffer.put(n7);
            this.brightnessBuffer.put(n8);
            this.brightnessBuffer.put(n9);
            this.brightnessBuffer.put(1.0f - n6);
        }
        this.brightnessBuffer.flip();
        GL11.glTexEnv(7008 + 5818 - 9103 + 5237, 4014 + 1462 - 1465 + 4694, this.brightnessBuffer);
        GlStateManager.setActiveTexture(OpenGlHelper.GL_TEXTURE2);
        GlStateManager.enableTexture2D();
        GlStateManager.bindTexture(RendererLivingEntity.field_177096_e.getGlTextureId());
        GL11.glTexEnvi(4515 + 7015 - 10756 + 8186, 4784 + 6702 - 6777 + 3995, OpenGlHelper.GL_COMBINE);
        GL11.glTexEnvi(1175 + 8066 - 7162 + 6881, OpenGlHelper.GL_COMBINE_RGB, 1385 + 3911 + 1288 + 1864);
        GL11.glTexEnvi(8731 + 8102 - 9295 + 1422, OpenGlHelper.GL_SOURCE0_RGB, OpenGlHelper.GL_PREVIOUS);
        GL11.glTexEnvi(1079 + 4455 - 1268 + 4694, OpenGlHelper.GL_SOURCE1_RGB, OpenGlHelper.lightmapTexUnit);
        GL11.glTexEnvi(4839 + 582 - 488 + 4027, OpenGlHelper.GL_OPERAND0_RGB, 603 + 141 - 279 + 303);
        GL11.glTexEnvi(5962 + 8264 - 5752 + 486, OpenGlHelper.GL_OPERAND1_RGB, 403 + 518 - 749 + 596);
        GL11.glTexEnvi(3469 + 4154 - 4989 + 6326, OpenGlHelper.GL_COMBINE_ALPHA, 3760 + 1566 - 3602 + 5957);
        GL11.glTexEnvi(7087 + 3678 - 5597 + 3792, OpenGlHelper.GL_SOURCE0_ALPHA, OpenGlHelper.GL_PREVIOUS);
        GL11.glTexEnvi(7595 + 5426 - 10006 + 5945, OpenGlHelper.GL_OPERAND0_ALPHA, 624 + 696 - 704 + 154);
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
        return " ".length() != 0;
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (3 <= 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public void transformHeldFull3DItemLayer() {
    }
    
    protected void unsetBrightness() {
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
        GlStateManager.enableTexture2D();
        GL11.glTexEnvi(693 + 7973 - 8559 + 8853, 2272 + 4804 - 5807 + 7435, OpenGlHelper.GL_COMBINE);
        GL11.glTexEnvi(4249 + 3077 - 6120 + 7754, OpenGlHelper.GL_COMBINE_RGB, 6432 + 3856 - 3819 + 1979);
        GL11.glTexEnvi(6636 + 7174 - 6789 + 1939, OpenGlHelper.GL_SOURCE0_RGB, OpenGlHelper.defaultTexUnit);
        GL11.glTexEnvi(4344 + 3328 - 348 + 1636, OpenGlHelper.GL_SOURCE1_RGB, OpenGlHelper.GL_PRIMARY_COLOR);
        GL11.glTexEnvi(372 + 8920 - 6128 + 5796, OpenGlHelper.GL_OPERAND0_RGB, 657 + 287 - 262 + 86);
        GL11.glTexEnvi(2794 + 7899 - 4448 + 2715, OpenGlHelper.GL_OPERAND1_RGB, 423 + 561 - 331 + 115);
        GL11.glTexEnvi(6192 + 7988 - 7411 + 2191, OpenGlHelper.GL_COMBINE_ALPHA, 7417 + 5155 - 11342 + 7218);
        GL11.glTexEnvi(1642 + 7328 - 6311 + 6301, OpenGlHelper.GL_SOURCE0_ALPHA, OpenGlHelper.defaultTexUnit);
        GL11.glTexEnvi(479 + 3366 - 259 + 5374, OpenGlHelper.GL_SOURCE1_ALPHA, OpenGlHelper.GL_PRIMARY_COLOR);
        GL11.glTexEnvi(2986 + 7770 - 10492 + 8696, OpenGlHelper.GL_OPERAND0_ALPHA, 433 + 556 - 551 + 332);
        GL11.glTexEnvi(6106 + 5939 - 6941 + 3856, OpenGlHelper.GL_OPERAND1_ALPHA, 309 + 107 + 205 + 149);
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GL11.glTexEnvi(2248 + 7032 - 2630 + 2310, 8233 + 6328 - 14007 + 8150, OpenGlHelper.GL_COMBINE);
        GL11.glTexEnvi(3923 + 3338 - 1337 + 3036, OpenGlHelper.GL_COMBINE_RGB, 358 + 4984 + 1827 + 1279);
        GL11.glTexEnvi(4038 + 6002 - 9476 + 8396, OpenGlHelper.GL_OPERAND0_RGB, 729 + 118 - 492 + 413);
        GL11.glTexEnvi(8626 + 2715 - 4782 + 2401, OpenGlHelper.GL_OPERAND1_RGB, 445 + 114 - 272 + 481);
        GL11.glTexEnvi(3220 + 592 + 2552 + 2596, OpenGlHelper.GL_SOURCE0_RGB, 4331 + 4480 - 6934 + 4013);
        GL11.glTexEnvi(712 + 3919 - 4528 + 8857, OpenGlHelper.GL_SOURCE1_RGB, OpenGlHelper.GL_PREVIOUS);
        GL11.glTexEnvi(1641 + 7027 - 4621 + 4913, OpenGlHelper.GL_COMBINE_ALPHA, 4451 + 338 - 2649 + 6308);
        GL11.glTexEnvi(5948 + 3720 - 8335 + 7627, OpenGlHelper.GL_OPERAND0_ALPHA, 223 + 465 - 479 + 561);
        GL11.glTexEnvi(5556 + 365 - 2315 + 5354, OpenGlHelper.GL_SOURCE0_ALPHA, 1295 + 5277 - 4762 + 4080);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.setActiveTexture(OpenGlHelper.GL_TEXTURE2);
        GlStateManager.disableTexture2D();
        GlStateManager.bindTexture("".length());
        GL11.glTexEnvi(8042 + 8602 - 8315 + 631, 1371 + 7172 - 7741 + 7902, OpenGlHelper.GL_COMBINE);
        GL11.glTexEnvi(4959 + 8228 - 4874 + 647, OpenGlHelper.GL_COMBINE_RGB, 4095 + 4456 - 1058 + 955);
        GL11.glTexEnvi(8160 + 4533 - 9744 + 6011, OpenGlHelper.GL_OPERAND0_RGB, 503 + 613 - 606 + 258);
        GL11.glTexEnvi(7342 + 1064 - 7060 + 7614, OpenGlHelper.GL_OPERAND1_RGB, 217 + 567 - 665 + 649);
        GL11.glTexEnvi(1136 + 3801 - 4692 + 8715, OpenGlHelper.GL_SOURCE0_RGB, 2368 + 2127 + 881 + 514);
        GL11.glTexEnvi(1816 + 8426 - 5858 + 4576, OpenGlHelper.GL_SOURCE1_RGB, OpenGlHelper.GL_PREVIOUS);
        GL11.glTexEnvi(6073 + 6427 - 11150 + 7610, OpenGlHelper.GL_COMBINE_ALPHA, 6544 + 3293 - 7563 + 6174);
        GL11.glTexEnvi(1309 + 4852 - 5932 + 8731, OpenGlHelper.GL_OPERAND0_ALPHA, 352 + 423 - 532 + 527);
        GL11.glTexEnvi(1344 + 6661 - 7578 + 8533, OpenGlHelper.GL_SOURCE0_ALPHA, 2481 + 221 + 2961 + 227);
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }
    
    @Override
    public void doRender(final Entity entity, final double n, final double n2, final double n3, final float n4, final float n5) {
        this.doRender((EntityLivingBase)entity, n, n2, n3, n4, n5);
    }
    
    protected boolean setScoreTeamColor(final T t) {
        int colorCode = 1141763 + 13737963 + 787940 + 1109549;
        if (t instanceof EntityPlayer) {
            final ScorePlayerTeam scorePlayerTeam = (ScorePlayerTeam)t.getTeam();
            if (scorePlayerTeam != null) {
                final String formatFromString = FontRenderer.getFormatFromString(scorePlayerTeam.getColorPrefix());
                if (formatFromString.length() >= "  ".length()) {
                    colorCode = this.getFontRendererFromRenderManager().getColorCode(formatFromString.charAt(" ".length()));
                }
            }
        }
        final float n = (colorCode >> (0x57 ^ 0x47) & 31 + 181 - 137 + 180) / 255.0f;
        final float n2 = (colorCode >> (0x7C ^ 0x74) & 181 + 118 - 117 + 73) / 255.0f;
        final float n3 = (colorCode & 170 + 253 - 339 + 171) / 255.0f;
        GlStateManager.disableLighting();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
        GlStateManager.color(n, n2, n3, 1.0f);
        GlStateManager.disableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.disableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
        return " ".length() != 0;
    }
    
    static {
        I();
        logger = LogManager.getLogger();
        field_177096_e = new DynamicTexture(0x99 ^ 0x89, 0x72 ^ 0x62);
        final int[] textureData = RendererLivingEntity.field_177096_e.getTextureData();
        int i = "".length();
        "".length();
        if (3 != 3) {
            throw null;
        }
        while (i < 107 + 97 + 29 + 23) {
            textureData[i] = -" ".length();
            ++i;
        }
        RendererLivingEntity.field_177096_e.updateDynamicTexture();
    }
    
    static int[] $SWITCH_TABLE$net$minecraft$scoreboard$Team$EnumVisible() {
        final int[] $switch_TABLE$net$minecraft$scoreboard$Team$EnumVisible = RendererLivingEntity.$SWITCH_TABLE$net$minecraft$scoreboard$Team$EnumVisible;
        if ($switch_TABLE$net$minecraft$scoreboard$Team$EnumVisible != null) {
            return $switch_TABLE$net$minecraft$scoreboard$Team$EnumVisible;
        }
        final int[] $switch_TABLE$net$minecraft$scoreboard$Team$EnumVisible2 = new int[Team.EnumVisible.values().length];
        try {
            $switch_TABLE$net$minecraft$scoreboard$Team$EnumVisible2[Team.EnumVisible.ALWAYS.ordinal()] = " ".length();
            "".length();
            if (3 < 2) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            $switch_TABLE$net$minecraft$scoreboard$Team$EnumVisible2[Team.EnumVisible.HIDE_FOR_OTHER_TEAMS.ordinal()] = "   ".length();
            "".length();
            if (4 <= 1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            $switch_TABLE$net$minecraft$scoreboard$Team$EnumVisible2[Team.EnumVisible.HIDE_FOR_OWN_TEAM.ordinal()] = (0xAC ^ 0xA8);
            "".length();
            if (true != true) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
        try {
            $switch_TABLE$net$minecraft$scoreboard$Team$EnumVisible2[Team.EnumVisible.NEVER.ordinal()] = "  ".length();
            "".length();
            if (0 >= 1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError4) {}
        return RendererLivingEntity.$SWITCH_TABLE$net$minecraft$scoreboard$Team$EnumVisible = $switch_TABLE$net$minecraft$scoreboard$Team$EnumVisible2;
    }
    
    protected boolean setDoRenderBrightness(final T t, final float n) {
        return this.setBrightness(t, n, " ".length() != 0);
    }
}
