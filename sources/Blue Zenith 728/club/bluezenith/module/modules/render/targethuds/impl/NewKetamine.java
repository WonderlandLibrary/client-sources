package club.bluezenith.module.modules.render.targethuds.impl;

import club.bluezenith.events.impl.Render2DEvent;
import club.bluezenith.module.modules.render.TargetHUD;
import club.bluezenith.module.modules.render.hud.HUD;
import club.bluezenith.module.modules.render.targethuds.ITargetHUD;
import club.bluezenith.util.font.FontUtil;
import club.bluezenith.util.font.TFontRenderer;
import club.bluezenith.util.math.MathUtil;
import club.bluezenith.util.render.RenderUtil;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import org.lwjgl.opengl.GL11;

import static club.bluezenith.util.math.MathUtil.round;
import static java.lang.String.format;

public class NewKetamine implements ITargetHUD {
    private static final TFontRenderer bold = FontUtil.createFont("Ketamine-Bold", 34),
                                       info =  FontUtil.createFont("Ketamine-Medium", 29);
    private static final int infoColor = 128 << 16 | 128 << 8 | 128 | 255 << 24;
    float height, width, progress;

    @Override
    public ITargetHUD createInstance() {
        return new NewKetamine();
    }

    @Override
    public void render(Render2DEvent event, EntityPlayer target, TargetHUD targetHUD) {
        targetHUD.width = width = 150;
        targetHUD.height = height = 39;

        final String name = target.getGameProfile().getName();

        float nameWidth = bold.getStringWidthF(name);

        if(nameWidth + 45 > width) {
            width = nameWidth + 45;
        }

        if(targetHUD.newKetamineBlur.get())
            RenderUtil.blur((float) targetHUD.translateX, (float) targetHUD.translateY, (float) (targetHUD.translateX + width), (float) (targetHUD.translateY + height));
        RenderUtil.rect(0, 0, width, height, 0x60 << 24);
        RenderUtil.hollowRectWithGradient(0, 0, width, height, 1.5F, (index) -> HUD.module.getColor(index));

        GlStateManager.translate(1, 1, 0);
        drawHead(target, true, 33, 33);
        GlStateManager.translate(-1, -1, 0);

        final String formatted = format("Distance: %s | Hurt: %s", round(mc.thePlayer.getDistanceToEntity(target), 1), round(target.hurtTime, 1));

        bold.drawString(name, 40, 6, -1, true);

        info.drawString(formatted, 39.5F, 17, infoColor, true);

        float[] absorption = checkAbsorption(target);

        float targetProgress = (target.getHealth() + absorption[1]) / (target.getMaxHealth() + absorption[0]);

        progress = RenderUtil.animate(targetProgress, progress, 0.1F);

        final String health = MathUtil.round(progress * 100, 1) + "%";

        final float strWidth = bold.getStringWidthF(health) + 7;

        float rectX = 40, rectY = 27,
                rectX2 = rectX + (width - strWidth - rectX) * progress, rectY2 = rectY + 8;

        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        RenderUtil.crop(targetHUD.translateX + rectX, targetHUD.translateY + rectY, targetHUD.translateX + rectX2, targetHUD.translateY + rectY2);
        RenderUtil.drawGradientRectHorizontal(rectX, rectY, rectX + width - strWidth, rectY2, HUD.module.getColor(5), HUD.module.getColor(7));
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
        bold.drawString(health, rectX2 + (progress == 0 ? -1 : 2), rectY + 1, -1, true);
    }

    private float[] checkAbsorption(EntityPlayer target) {
        final float[] absorption = {
                0, //max absorption
                0  //current absorption
        };

        final PotionEffect effect = target.getActivePotionEffect(Potion.absorption);

        if(effect == null)
            return absorption; //target has no absorption hearts (or we don't know them), so nothing to calculate

        //max absorption
        absorption[0] = (effect.getAmplifier() + 1) * 4;
        //current absorption
        absorption[1] = target.getAbsorptionAmount();

        return absorption;
    }

    private static final NewKetamine newKetamine = new NewKetamine();

    public static NewKetamine getInstance() {
        return newKetamine;
    }
}
