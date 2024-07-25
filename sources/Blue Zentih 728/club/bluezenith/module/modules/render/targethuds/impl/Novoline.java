package club.bluezenith.module.modules.render.targethuds.impl;

import club.bluezenith.events.impl.Render2DEvent;
import club.bluezenith.module.modules.render.hud.HUD;
import club.bluezenith.module.modules.render.TargetHUD;
import club.bluezenith.module.modules.render.targethuds.ITargetHUD;
import club.bluezenith.util.math.MathUtil;
import club.bluezenith.util.render.RenderUtil;
import fr.lavache.anime.AnimateTarget;
import fr.lavache.anime.Easing;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class Novoline implements ITargetHUD {

    private float targetPrevHealth = 0;
    private final AnimateTarget prevAnim = new AnimateTarget().setEase(Easing.QUAD_IN).setSpeed(50);
    private final AnimateTarget anim = new AnimateTarget().setEase(Easing.QUAD_IN).setSpeed(50);

    @Override
    public void render(Render2DEvent event, EntityPlayer target, TargetHUD targetHUD) {
        final float mainScale = targetHUD.novolineScale.get();
        GlStateManager.pushMatrix();
        final int mainColor = HUD.module.getColor(1);
        EntityPlayer player = target;
        NetworkPlayerInfo info = mc.getNetHandler().getPlayerInfo(player.getUniqueID());
        if (info == null) {
            GlStateManager.popMatrix();
            GlStateManager.popMatrix();
            return;
        }
        GlStateManager.scale(mainScale, mainScale, 1);
        Color color1 = Color.darkGray.darker().darker();
        final float rectWidth = Math.max(100, 29 + mc.fontRendererObj.getStringWidthF(target.getGameProfile().getName()));
        RenderUtil.hollowRect(-0.4F, 0.8f, rectWidth + 0.4F, 32.5F, 3, color1.getRGB());
        //RenderUtil.rect(-1F, -0.2F, rectWidth + 1F, 33.5F, color1.getRGB());
        targetHUD.width = (rectWidth + 1) * mainScale;
        targetHUD.height = 32 * mainScale;
        RenderUtil.rect(0, 1, rectWidth, 32, Color.darkGray.darker().getRGB());
        GL11.glColor4f(1F, 1F, 1F, 1F);
        mc.getTextureManager().bindTexture(info.getLocationSkin());
        Gui.drawScaledCustomSizeModalRect(0, 1, 8F, 8F, 8, 8, 30, 31, 64F, 64F);
        final float progress = 1 - (player.getMaxHealth() - player.getHealth()) / player.getMaxHealth();
        final float healthbarWidth = 63 + (rectWidth - 100);
        final float translateWidth = healthbarWidth * progress;
        final float diff = Math.max(Math.abs(targetPrevHealth - translateWidth), 1);
        final float scale = 0.9f;
        //prevAnim.setValue(0);
        //anim.setValue(0);
        prevAnim.setSpeed(50 / diff);
        anim.setSpeed(100 / diff);
        final float animPrevWidth = prevAnim.setMax(healthbarWidth).setEase(Easing.QUAD_OUT).setTarget(translateWidth).update().getValue();
        final float animWidth = anim.setMax(healthbarWidth).setEase(Easing.QUAD_OUT).setTarget(translateWidth).update().getValue();
        float aids = 4 + mc.fontRendererObj.FONT_HEIGHT;
        RenderUtil.rect(30 + 3, aids, 30 + 2 + healthbarWidth, aids + 7.5f, color1.getRGB());
     //   RenderUtil.drawGradientRectHorizontal(30 + 3, aids, 30 + 2 + animPrevWidth + 0.5f, aids + 7.5f, HUD.module.pulseColor1.getRGB(), HUD.module.pulseColor2.getRGB());
        RenderUtil.rect(30 + 3, aids, 30 + 2 + animPrevWidth, aids + 7.5f, new Color(mainColor).darker().getRGB());
        RenderUtil.rect(30 + 3, aids, 30 + 2 + animWidth, aids + 7.5f, mainColor);
        aids += 7.5f + 3;
        GlStateManager.pushMatrix();
        GlStateManager.scale(scale, scale, 1);
        mc.fontRendererObj.drawString(player.getDisplayName().getUnformattedText(), (30 + 3) / scale, 3 / scale, 0xffffffff);
        mc.fontRendererObj.setGradient((i) -> new Color(HUD.module.getColor(i)).brighter());
        mc.fontRendererObj.drawString(MathUtil.round(player.getHealth()) + " ❤", (30 + 3) / scale, aids / scale, mainColor, false);
        GlStateManager.popMatrix();
        targetPrevHealth = translateWidth;
        GlStateManager.popMatrix();
    }


    @Override
    public ITargetHUD createInstance() {
        return new Novoline();
    }

    private Novoline() {}
    private final static Novoline novolineMode = new Novoline();
    public static ITargetHUD getInstance() {
        return novolineMode;
    }
}
