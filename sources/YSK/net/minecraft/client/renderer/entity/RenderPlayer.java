package net.minecraft.client.renderer.entity;

import net.minecraft.scoreboard.*;
import net.minecraft.client.entity.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.client.model.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.entity.layers.*;

public class RenderPlayer extends RendererLivingEntity<AbstractClientPlayer>
{
    private boolean smallArms;
    private static final String[] I;
    
    @Override
    protected void renderLivingAt(final EntityLivingBase entityLivingBase, final double n, final double n2, final double n3) {
        this.renderLivingAt((AbstractClientPlayer)entityLivingBase, n, n2, n3);
    }
    
    @Override
    protected void rotateCorpse(final EntityLivingBase entityLivingBase, final float n, final float n2, final float n3) {
        this.rotateCorpse((AbstractClientPlayer)entityLivingBase, n, n2, n3);
    }
    
    @Override
    protected void renderOffsetLivingLabel(final AbstractClientPlayer abstractClientPlayer, final double n, double n2, final double n3, final String s, final float n4, final double n5) {
        if (n5 < 100.0) {
            final Scoreboard worldScoreboard = abstractClientPlayer.getWorldScoreboard();
            final ScoreObjective objectiveInDisplaySlot = worldScoreboard.getObjectiveInDisplaySlot("  ".length());
            if (objectiveInDisplaySlot != null) {
                this.renderLivingLabel(abstractClientPlayer, String.valueOf(worldScoreboard.getValueFromObjective(abstractClientPlayer.getName(), objectiveInDisplaySlot).getScorePoints()) + RenderPlayer.I["".length()] + objectiveInDisplaySlot.getDisplayName(), n, n2, n3, 0x12 ^ 0x52);
                n2 += this.getFontRendererFromRenderManager().FONT_HEIGHT * 1.15f * n4;
            }
        }
        super.renderOffsetLivingLabel(abstractClientPlayer, n, n2, n3, s, n4, n5);
    }
    
    @Override
    public void doRender(final AbstractClientPlayer modelVisibilities, final double n, final double n2, final double n3, final float n4, final float n5) {
        if (!modelVisibilities.isUser() || this.renderManager.livingPlayer == modelVisibilities) {
            double n6 = n2;
            if (modelVisibilities.isSneaking() && !(modelVisibilities instanceof EntityPlayerSP)) {
                n6 = n2 - 0.125;
            }
            this.setModelVisibilities(modelVisibilities);
            super.doRender(modelVisibilities, n, n6, n3, n4, n5);
        }
    }
    
    public void renderRightArm(final AbstractClientPlayer modelVisibilities) {
        final float n = 1.0f;
        GlStateManager.color(n, n, n);
        final ModelPlayer mainModel = this.getMainModel();
        this.setModelVisibilities(modelVisibilities);
        mainModel.swingProgress = 0.0f;
        mainModel.isSneak = ("".length() != 0);
        mainModel.setRotationAngles(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f, modelVisibilities);
        mainModel.renderRightArm();
    }
    
    private void setModelVisibilities(final AbstractClientPlayer abstractClientPlayer) {
        final ModelPlayer mainModel = this.getMainModel();
        if (abstractClientPlayer.isSpectator()) {
            mainModel.setInvisible("".length() != 0);
            mainModel.bipedHead.showModel = (" ".length() != 0);
            mainModel.bipedHeadwear.showModel = (" ".length() != 0);
            "".length();
            if (1 < 1) {
                throw null;
            }
        }
        else {
            final ItemStack currentItem = abstractClientPlayer.inventory.getCurrentItem();
            mainModel.setInvisible(" ".length() != 0);
            mainModel.bipedHeadwear.showModel = abstractClientPlayer.isWearing(EnumPlayerModelParts.HAT);
            mainModel.bipedBodyWear.showModel = abstractClientPlayer.isWearing(EnumPlayerModelParts.JACKET);
            mainModel.bipedLeftLegwear.showModel = abstractClientPlayer.isWearing(EnumPlayerModelParts.LEFT_PANTS_LEG);
            mainModel.bipedRightLegwear.showModel = abstractClientPlayer.isWearing(EnumPlayerModelParts.RIGHT_PANTS_LEG);
            mainModel.bipedLeftArmwear.showModel = abstractClientPlayer.isWearing(EnumPlayerModelParts.LEFT_SLEEVE);
            mainModel.bipedRightArmwear.showModel = abstractClientPlayer.isWearing(EnumPlayerModelParts.RIGHT_SLEEVE);
            mainModel.heldItemLeft = "".length();
            mainModel.aimedBow = ("".length() != 0);
            mainModel.isSneak = abstractClientPlayer.isSneaking();
            if (currentItem == null) {
                mainModel.heldItemRight = "".length();
                "".length();
                if (-1 != -1) {
                    throw null;
                }
            }
            else {
                mainModel.heldItemRight = " ".length();
                if (abstractClientPlayer.getItemInUseCount() > 0) {
                    final EnumAction itemUseAction = currentItem.getItemUseAction();
                    if (itemUseAction == EnumAction.BLOCK) {
                        mainModel.heldItemRight = "   ".length();
                        "".length();
                        if (0 == -1) {
                            throw null;
                        }
                    }
                    else if (itemUseAction == EnumAction.BOW) {
                        mainModel.aimedBow = (" ".length() != 0);
                    }
                }
            }
        }
    }
    
    @Override
    public ModelBase getMainModel() {
        return this.getMainModel();
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
            if (0 <= -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static {
        I();
    }
    
    @Override
    protected void preRenderCallback(final AbstractClientPlayer abstractClientPlayer, final float n) {
        final float n2 = 0.9375f;
        GlStateManager.scale(n2, n2, n2);
    }
    
    @Override
    protected void renderLivingAt(final AbstractClientPlayer abstractClientPlayer, final double n, final double n2, final double n3) {
        if (abstractClientPlayer.isEntityAlive() && abstractClientPlayer.isPlayerSleeping()) {
            super.renderLivingAt(abstractClientPlayer, n + abstractClientPlayer.renderOffsetX, n2 + abstractClientPlayer.renderOffsetY, n3 + abstractClientPlayer.renderOffsetZ);
            "".length();
            if (1 <= 0) {
                throw null;
            }
        }
        else {
            super.renderLivingAt(abstractClientPlayer, n, n2, n3);
        }
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("D", "dlydR");
    }
    
    @Override
    public void doRender(final EntityLivingBase entityLivingBase, final double n, final double n2, final double n3, final float n4, final float n5) {
        this.doRender((AbstractClientPlayer)entityLivingBase, n, n2, n3, n4, n5);
    }
    
    @Override
    public void transformHeldFull3DItemLayer() {
        GlStateManager.translate(0.0f, 0.1875f, 0.0f);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final AbstractClientPlayer abstractClientPlayer) {
        return abstractClientPlayer.getLocationSkin();
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.getEntityTexture((AbstractClientPlayer)entity);
    }
    
    public void renderLeftArm(final AbstractClientPlayer modelVisibilities) {
        final float n = 1.0f;
        GlStateManager.color(n, n, n);
        final ModelPlayer mainModel = this.getMainModel();
        this.setModelVisibilities(modelVisibilities);
        mainModel.isSneak = ("".length() != 0);
        mainModel.setRotationAngles(mainModel.swingProgress = 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f, modelVisibilities);
        mainModel.renderLeftArm();
    }
    
    public RenderPlayer(final RenderManager renderManager, final boolean smallArms) {
        super(renderManager, new ModelPlayer(0.0f, smallArms), 0.5f);
        this.smallArms = smallArms;
        ((RendererLivingEntity<EntityLivingBase>)this).addLayer(new LayerBipedArmor(this));
        ((RendererLivingEntity<EntityLivingBase>)this).addLayer(new LayerHeldItem(this));
        ((RendererLivingEntity<EntityLivingBase>)this).addLayer(new LayerArrow(this));
        ((RendererLivingEntity<EntityLivingBase>)this).addLayer(new LayerDeadmau5Head(this));
        ((RendererLivingEntity<EntityLivingBase>)this).addLayer(new LayerCape(this));
        ((RendererLivingEntity<EntityLivingBase>)this).addLayer(new LayerCustomHead(this.getMainModel().bipedHead));
    }
    
    public RenderPlayer(final RenderManager renderManager) {
        this(renderManager, "".length() != 0);
    }
    
    @Override
    public ModelPlayer getMainModel() {
        return (ModelPlayer)super.getMainModel();
    }
    
    @Override
    protected void rotateCorpse(final AbstractClientPlayer abstractClientPlayer, final float n, final float n2, final float n3) {
        if (abstractClientPlayer.isEntityAlive() && abstractClientPlayer.isPlayerSleeping()) {
            GlStateManager.rotate(abstractClientPlayer.getBedOrientationInDegrees(), 0.0f, 1.0f, 0.0f);
            GlStateManager.rotate(this.getDeathMaxRotation(abstractClientPlayer), 0.0f, 0.0f, 1.0f);
            GlStateManager.rotate(270.0f, 0.0f, 1.0f, 0.0f);
            "".length();
            if (0 >= 4) {
                throw null;
            }
        }
        else {
            super.rotateCorpse(abstractClientPlayer, n, n2, n3);
        }
    }
    
    @Override
    protected void renderOffsetLivingLabel(final Entity entity, final double n, final double n2, final double n3, final String s, final float n4, final double n5) {
        this.renderOffsetLivingLabel((AbstractClientPlayer)entity, n, n2, n3, s, n4, n5);
    }
    
    @Override
    protected void preRenderCallback(final EntityLivingBase entityLivingBase, final float n) {
        this.preRenderCallback((AbstractClientPlayer)entityLivingBase, n);
    }
}
