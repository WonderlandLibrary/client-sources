package club.bluezenith.module.modules.render.targethuds.impl;

import club.bluezenith.events.impl.Render2DEvent;
import club.bluezenith.module.modules.render.TargetHUD;
import club.bluezenith.module.modules.render.targethuds.ITargetHUD;
import club.bluezenith.util.math.MathUtil;
import club.bluezenith.util.render.RenderUtil;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

import java.awt.*;

import static club.bluezenith.util.font.FontUtil.SFLight35;
import static club.bluezenith.util.font.FontUtil.SFLight42;

public class LiquidBounce implements ITargetHUD {

    private EntityLivingBase lastTarget = null;
    private float easingHealth = 0;

    @Override
    public void render(Render2DEvent event, EntityPlayer target, TargetHUD targetHUD) {
        if (target != lastTarget || easingHealth < 0 || easingHealth > target.getMaxHealth() ||
            Math.abs(easingHealth - target.getHealth()) < 0.01) {
            easingHealth = target.getHealth();
        }

        float width = Math.max(118, 40 + SFLight42.getStringWidth(target.getDisplayName().getUnformattedText()));
        targetHUD.width = width;
        targetHUD.height = 36F;

        RenderUtil.rect(0F, 0F, width, 36F, new Color(15, 15, 15, 150));

        if (easingHealth > target.getHealth())
            RenderUtil.rect(0F, 34F, (easingHealth / target.getMaxHealth()) * width, 36F, new Color(252, 185, 65).getRGB());

        RenderUtil.rect(0F, 34F, (target.getHealth() / target.getMaxHealth()) * width, 36F, new Color(252, 96, 66).getRGB()); // new Color(252, 96, 66).getRGB()

        if (easingHealth < target.getHealth())
            RenderUtil.rect((easingHealth / target.getMaxHealth()) * width, 34F, (target.getHealth() / target.getMaxHealth()) * width, 36F, new Color(44, 201, 144).getRGB());

        easingHealth += Math.pow((target.getHealth() - easingHealth) / 2.0F, 10F - 2f) * RenderUtil.delta;

        SFLight42.drawString(target.getDisplayName().getUnformattedText(), 36, 2, 0xffffffff);
        SFLight35.drawString("Distance: " + MathUtil.round(mc.thePlayer.getDistanceToEntity(target)), 36, 14, 0xffffffff);

        NetworkPlayerInfo playerInfo = mc.getNetHandler().getPlayerInfo(target.getUniqueID());

        if (playerInfo != null) {
               SFLight35.drawString("Ping: " + Math.min(playerInfo.getResponseTime(), 0), 36, 23, 0xffffffff);
               drawHead(target, true, 30, 30);
        }
        lastTarget = target;
    }


    @Override
    public ITargetHUD createInstance() {
        return new LiquidBounce();
    }

    private LiquidBounce() {}
    private final static LiquidBounce liquidBounceMode = new LiquidBounce();
    public static ITargetHUD getInstance() {
        return liquidBounceMode;
    }
}

