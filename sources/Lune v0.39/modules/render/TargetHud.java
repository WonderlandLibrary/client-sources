package me.superskidder.lune.modules.render;

import me.superskidder.lune.modules.Mod;
import me.superskidder.lune.modules.ModCategory;
import me.superskidder.lune.modules.combat.KillAura;
import me.superskidder.lune.events.EventRender2D;
import me.superskidder.lune.font.FontLoaders;
import me.superskidder.lune.manager.event.EventTarget;
import me.superskidder.lune.utils.render.RenderUtil;
import me.superskidder.lune.values.type.Num;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;

public class TargetHud extends Mod {
    public Num<Double> x = new Num<>("X(persent)", 80.0, 0.0, 100.0);
    public Num<Double> y = new Num<>("Y(persent)", 80.0, 0.0, 100.0);

    public TargetHud() {
        super("TargetHUD", ModCategory.Render, "see target's information");
        addValues(x, y);
    }

    float anim = 150;

    @EventTarget
    public void EventRender(EventRender2D e) {
        ScaledResolution sr = new ScaledResolution(mc);
        float targetx = (float) (x.getValue() / 100 * sr.getScaledWidth_double());
        float targety = (float) (y.getValue() / 100 * sr.getScaledHeight_double());

        if (KillAura.target != null) {
            RenderUtil.drawRect(targetx + 35, targety + 40, targetx + 195, targety + 72, new Color(33, 36, 41, 255).getRGB());

            FontLoaders.F18.drawCenteredStringWithShadow(KillAura.target.getName(), (int) targetx + 40 + 75, (int) targety + 45, -1);
            if (anim < 150 * (KillAura.target.getHealth() / KillAura.target.getMaxHealth())) {
                anim = (int) (150 * (KillAura.target.getHealth() / KillAura.target.getMaxHealth()));
            } else if (anim > 150 * (KillAura.target.getHealth() / KillAura.target.getMaxHealth())) {
                anim -= 120f / mc.debugFPS;
            }

            //RenderUtil.drawRect(targetx + 40 - KillAura.target.hurtTime / 2, targety + 60 - KillAura.target.hurtTime / 2, targetx + 40 - KillAura.target.hurtTime / 2 + 150 + KillAura.target.hurtTime, targety + 60 - KillAura.target.hurtTime / 2 + 10 + KillAura.target.hurtTime, new Color(KillAura.target.hurtTime * 10 + 86, 212, 163).getRGB());
            RenderUtil.drawRect(targetx + 40, targety + 60, targetx + 40 + 150, targety + 65, new Color(KillAura.target.hurtTime * 20, 0, 0).getRGB());

            RenderUtil.drawRect(targetx + 40 + 150 * (KillAura.target.getHealth() / KillAura.target.getMaxHealth()), targety + 60, targetx + 40 + anim, targety + 65,new Color(255, 100, 152).getRGB());
            RenderUtil.drawGradientSideways(targetx + 40, targety + 60, targetx + 40 + 150 * (KillAura.target.getHealth() / KillAura.target.getMaxHealth()), targety + 65, new Color(73, 148, 248).getRGB(), new Color(73, 200, 248).getRGB());


        }
    }
}
