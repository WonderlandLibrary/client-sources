package club.bluezenith.module.modules.render.targethuds.impl;

import club.bluezenith.events.impl.Render2DEvent;
import club.bluezenith.module.modules.render.TargetHUD;
import club.bluezenith.module.modules.render.targethuds.ITargetHUD;
import club.bluezenith.util.math.MathUtil;
import club.bluezenith.util.font.FontUtil;
import club.bluezenith.util.render.RenderUtil;
import fr.lavache.anime.AnimateTarget;
import fr.lavache.anime.Easing;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Weird implements ITargetHUD {
    private float targetPrevHealth = 0;
    private float bgWidth = 0;
    private final AnimateTarget armorBarAnim = new AnimateTarget().setEase(Easing.QUAD_IN).setSpeed(50);
    private final AnimateTarget anim = new AnimateTarget().setEase(Easing.QUAD_IN).setSpeed(50);

    @Override
    public void render(Render2DEvent event, EntityPlayer target, TargetHUD targetHUD) {
        GlStateManager.pushMatrix();
        int skinPos = 0;
        float realSkinOffset = 2.5F;
        GlStateManager.translate(4.5F, 12, 0);
        NetworkPlayerInfo info = mc.getNetHandler().getPlayerInfo(target.getUniqueID());
        final float rectWidth = 5 + FontUtil.inter28.getStringWidthF(target.getGameProfile().getName());
      //  RenderUtil.rect(0, 0, rectWidth + 3, 43, new Color(30, 30, 30, 180).getRGB());
        //RenderUtil.rect(-3, -11.5F, bgWidth + 2, 24F, new Color(1, 1, 1, 150));
        GL11.glColor4f(1F, 1F - (target.hurtTime / 20f), 1F - (target.hurtTime / 20f), 1F);
        mc.getTextureManager().bindTexture(info.getLocationSkin());
        Gui.drawScaledCustomSizeModalRect((skinPos - realSkinOffset) + 0.3f, skinPos - 9.5F, 8F, 8F, 8, 8, 20, 20, 64F, 64F);
        RenderUtil.rect(skinPos + 20, skinPos - 10, skinPos + 20 + rectWidth, skinPos, new Color(1, 1, 1, 150));
        RenderUtil.hollowRect(skinPos + 19.6F, skinPos - 10.6F, skinPos + 20.6F + rectWidth, skinPos + 0.6F, 0.7F, Color.BLACK.getRGB());
        RenderUtil.hollowRect(skinPos + 20, skinPos - 10, skinPos + 20 + rectWidth, skinPos, 0.7F, -1);
        FontUtil.inter28.drawString(info.getGameProfile().getName(), 22, skinPos - 8.5F, -1);

        RenderUtil.hollowRect(skinPos - realSkinOffset - 0.5f, skinPos - realSkinOffset - 7.5F, (21F - realSkinOffset) + skinPos, (8 + realSkinOffset) + skinPos, 0.5F, -1);
        RenderUtil.hollowRect(skinPos - realSkinOffset - 1F, skinPos - realSkinOffset - 8.3F, (21.5F - realSkinOffset) + skinPos, (9F + realSkinOffset) + skinPos, 0.5F, Color.BLACK.getRGB());
        final float iconWidth = 4F + FontUtil.fluxIcons.getStringWidthF("s");
        drawHealthBar:
        {
            final float barWidth = Math.max(100, rectWidth);
            final float progress = 1 - (target.getMaxHealth() - (target.capabilities.disableDamage ? 20 : target.getHealth())) / target.getMaxHealth();
            final String healthString = targetHUD.trashHPMode.is("Percentage") ? MathUtil.round(100 * progress, 1) + "%" : String.valueOf(MathUtil.round(target.getHealth(), 1));
            final float healthbarWidth = (skinPos + 20) + (barWidth - 10.5F);
            final float hStringWidth = FontUtil.inter28.getStringWidthF(healthString);
            final float translateWidth = healthbarWidth * progress;
            final float diff = Math.max(Math.abs(targetPrevHealth - translateWidth), 1);
            final boolean back = targetPrevHealth > translateWidth;
            anim.setSpeed((back ? 10 : 20) / diff);
            final float animWidth = anim.setMax(healthbarWidth).setEase(Easing.QUAD_OUT).setTarget(translateWidth).update().getValue();
            float aids = 27.5F;
            final int healthbarColor = targetHUD.getColorForHealth(target.getMaxHealth(), target.getHealth());// new Color(HUD.module.getColor(1));//new Color(41, 238, 49);
            RenderUtil.hollowRect(skinPos + 19F + 0.5F, skinPos + 11.2F, healthbarWidth + 11 + 0.2F, skinPos + 0.8F, 0.5F, Color.BLACK.getRGB());
            RenderUtil.hollowRect(skinPos + 19.6F + 0.5F, skinPos + 10.6F, healthbarWidth + 11.5F, skinPos + 1.4F, 0.5F, Color.white.getRGB());
            RenderUtil.rect(skinPos + 20.5F, skinPos + 10, healthbarWidth + 10.5F, skinPos + 2F, new Color(1, 1, 1, 150));
            final float maxX = Math.max(20.5F, iconWidth + animWidth);
            final float strMax = Math.max(20.5F, maxX - hStringWidth - 2);
            RenderUtil.rect(skinPos + 20.5F, skinPos + 10, Math.max(maxX - 1, maxX - hStringWidth - 1) + 0.8f, skinPos + 2F, healthbarColor);
            targetHUD.width = healthbarWidth + 17;
            targetHUD.height = skinPos + 36;
            bgWidth = healthbarWidth + 10;
            FontUtil.inter28.drawString(healthString, strMax, skinPos - 4.5F + FontUtil.inter28.FONT_HEIGHT, -1, true);
            targetPrevHealth = translateWidth;
            RenderUtil.hollowRect(-3.5F, 12F, 19F, 23F, 1F, Color.BLACK.getRGB());
            RenderUtil.hollowRect(-3F, 12.5F, 18.5F, 22.5F, 0.5F, -1);
            RenderUtil.rect(-2.5F, 13F, 18F, 22F, new Color(1, 1, 1, 150));
            final String hurtTime = String.valueOf(target.hurtTime);
            FontUtil.interMedium28.drawString(hurtTime, 5F, skinPos + 14.5F, -1);
        }
        drawArmorBar:
        {
            final float maxArmorPoints = targetHUD.getMaxArmorPoints();
            final float progress = 1 - (maxArmorPoints - targetHUD.getArmorPointsForPlayer(target)) / maxArmorPoints;
            final String armorString = BigDecimal.valueOf(100 * progress).setScale(2, RoundingMode.HALF_UP).doubleValue() + "% ";
            final float fluxIconWidth = FontUtil.fluxIcons.getStringWidthF("s");
            final float percentWidth = 21F + fluxIconWidth + FontUtil.inter28.getStringWidthF(armorString);
            RenderUtil.rect(20.5F, skinPos + 13F, percentWidth, skinPos + 22F, new Color(1, 1, 1, 150));
            RenderUtil.hollowRect(20F, skinPos + 12.6F, percentWidth + 0.5F, skinPos + 22.5F, 0.7F, -1);
            RenderUtil.hollowRect(19.5F, skinPos + 12F, percentWidth + 1F, skinPos + 23F, 0.7F, Color.BLACK.getRGB());
            FontUtil.interMedium28.drawString(armorString, 21.5F + FontUtil.fluxIcons.getStringWidthF("s"), skinPos + 14.5F, -1);
            FontUtil.fluxIcons.drawString("s", 20.5F, skinPos + 15, Color.GRAY.getRGB());
        }
        GlStateManager.popMatrix();
    }

    @Override
    public ITargetHUD createInstance() {
        return new Weird();
    }

    private Weird() {}
    private static final Weird flux = new Weird();
    public static ITargetHUD getInstance() {
        return flux;
    }
}
