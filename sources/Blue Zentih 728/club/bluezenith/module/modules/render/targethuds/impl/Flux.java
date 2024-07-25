package club.bluezenith.module.modules.render.targethuds.impl;

import club.bluezenith.events.impl.Render2DEvent;
import club.bluezenith.module.modules.combat.Aura;
import club.bluezenith.module.modules.render.TargetHUD;
import club.bluezenith.module.modules.render.targethuds.ITargetHUD;
import fr.lavache.anime.AnimateTarget;
import fr.lavache.anime.Easing;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.player.EntityPlayer;

import java.awt.*;

import static club.bluezenith.module.modules.render.hud.HUD.module;
import static club.bluezenith.util.font.FontUtil.fluxIcons;
import static club.bluezenith.util.font.FontUtil.inter28;
import static club.bluezenith.util.math.MathUtil.round;
import static club.bluezenith.util.render.RenderUtil.*;
import static java.lang.Math.abs;
import static java.lang.Math.max;
import static net.minecraft.client.gui.Gui.drawScaledCustomSizeModalRect;
import static net.minecraft.client.renderer.GlStateManager.*;
import static org.lwjgl.opengl.GL11.glColor4f;

public class Flux implements ITargetHUD {

    private float targetPrevHealth = 0;
    private float targetPrevArmor = 0;
    private final AnimateTarget armorBarAnim = new AnimateTarget().setEase(Easing.QUAD_IN).setSpeed(50);
    private final AnimateTarget anim = new AnimateTarget().setEase(Easing.QUAD_IN).setSpeed(50);
    private final static int BACKGROUND_COLOR = new Color(30, 30, 30, 160).getRGB();

    @Override
    @SuppressWarnings("all")
    public void render(Render2DEvent event, EntityPlayer target, TargetHUD targetHUD) {
            pushMatrix();
            NetworkPlayerInfo info = mc.getNetHandler().getPlayerInfo(target.getUniqueID());

            final int outlineColori = targetHUD.outlineMode.is("Match HUD") ? module.getColor(1) : targetHUD.outlineColor.getRGB();
            final float rectWidth = max(100, 30 + inter28.getStringWidthF(target.getGameProfile().getName()));

            if(targetHUD.fluxBackgrounds.getOptionState("Rect"))
            rect(0, 0, rectWidth + 3, 43, BACKGROUND_COLOR);

            if(targetHUD.fluxBackgrounds.getOptionState("Blur")) {
                final Aura aura = targetHUD.getCastedModule(Aura.class);
                if(aura.mode.is("Single") || aura.getTargetsOrNull().size() == 1) { //cannot move blur with translate() automatically so can't render blur at all
                    final float x = targetHUD.x.get();
                    final float y = targetHUD.y.get();
                    blur(x, y, x + rectWidth + 3, y + targetHUD.height);
                }
            }

            if(targetHUD.outlines.getOptionState("Rect")) {
                hollowRect(0, 0, rectWidth + 3, 43, targetHUD.fluxHollowWidth.get(), outlineColori);
            }

            targetHUD.width = rectWidth + 3;
            targetHUD.height = 43;

            glColor4f(1F, 1F - (target.hurtTime / 15f), 1F - (target.hurtTime / 15f), 1F);
            mc.getTextureManager().bindTexture(info.getLocationSkin());
            drawScaledCustomSizeModalRect(3, 3, 8F, 8F, 8, 8, 20, 20, 64F, 64F);

            inter28.drawString(info.getGameProfile().getName(), 25, 3.5F, -1);
            inter28.drawString(round(target.getHealth(), 1) + " HP", 25, 6.8F + inter28.FONT_HEIGHT, -1);

            fluxIcons.drawString("h", 1.5F, 26.5F, -1);
            fluxIcons.drawString("s", 1.5F, 27.5F + inter28.FONT_HEIGHT, -1);
            if(targetHUD.outlines.getOptionState("Skin")) {
                hollowRect(3, 3, 23, 23, targetHUD.fluxHollowWidth.get(), outlineColori);
            }

            final float iconWidth = 4F + fluxIcons.getStringWidthF("s");
            drawHealthBar:
            {
                final float progress = 1 - (target.getMaxHealth() - (target.capabilities.disableDamage ? 20 : target.getHealth())) / target.getMaxHealth();
                final float healthbarWidth = 87.5F + (rectWidth - 100);
                final float translateWidth = healthbarWidth * progress;
                final float diff = max(abs(targetPrevHealth - translateWidth), 1);
                final boolean back = targetPrevHealth > translateWidth;
                anim.setSpeed((back ? 10 : 20) / diff);
                final float animWidth = anim.setMax(healthbarWidth).setEase(Easing.QUAD_OUT).setTarget(translateWidth).update().getValue();
                float aids = 27.5F;
                final Color healthbarColor = new Color(67, 225, 134);
                rect(iconWidth, aids, healthbarWidth + 11, aids + 2.5f, healthbarColor.darker());
                rect(iconWidth, aids, iconWidth + animWidth, aids + 2.5f, healthbarColor);
                targetPrevHealth = translateWidth;
            }
            drawArmorBar:
            {
                final float maxArmorPoints = targetHUD.getMaxArmorPoints();
                final float progress = 1 - (maxArmorPoints - targetHUD.getArmorPointsForPlayer(target)) / maxArmorPoints;
                final float healthbarWidth = 87.5F + (rectWidth - 100);
                final float translateWidth = healthbarWidth * progress;
                final float diff = max(abs(targetPrevArmor - translateWidth), 1);
                final boolean back = targetPrevArmor > translateWidth;
                armorBarAnim.setSpeed((back ? 40 : 20) / diff);
                final float animWidth = armorBarAnim.setMax(healthbarWidth).setEase(Easing.QUAD_OUT).setTarget(translateWidth).update().getValue();
                float aids = 35.8F;
                final Color healthbarColor = new Color(40, 81, 99);
                rect(iconWidth, aids, healthbarWidth + 11, aids + 2.5F, healthbarColor.darker());
                rect(iconWidth, aids, iconWidth + animWidth, aids + 2.5f, healthbarColor.brighter());
                targetPrevArmor = translateWidth;
            }
            popMatrix();
        }


    @Override
    public ITargetHUD createInstance() {
        return new Flux();
    }

    private Flux() {}
      private static final Flux flux = new Flux();
      public static ITargetHUD getInstance() {
          return flux;
      }
}
