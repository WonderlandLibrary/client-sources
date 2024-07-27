package dev.nexus.modules.impl.render;

import dev.nexus.Nexus;
import dev.nexus.events.bus.Listener;
import dev.nexus.events.bus.annotations.EventLink;
import dev.nexus.events.impl.EventMotionPre;
import dev.nexus.events.impl.EventRender2D;
import dev.nexus.modules.Module;
import dev.nexus.modules.ModuleCategory;
import dev.nexus.modules.impl.combat.KillAura;
import dev.nexus.utils.client.ThemeUtil;
import dev.nexus.utils.game.CombatUtils;
import dev.nexus.utils.render.DrawUtils;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TargetHUD extends Module {
    public TargetHUD() {
        super("TargetHUD", 0, ModuleCategory.RENDER);
    }

    private List<EntityPlayer> otherTargets = new ArrayList<>();
    private final DecimalFormat df = new DecimalFormat("0.0");

    @EventLink
    public final Listener<EventMotionPre> eventMotionPreListener = event -> {
        if (isNull()) {
            return;
        }
        KillAura ka = Nexus.INSTANCE.getModuleManager().getModule(KillAura.class);

        // #TODO: Make ka target always be first
        otherTargets = CombatUtils.getPlayersWithin(5, ka.attackTeamMates.getValue());
        otherTargets.sort(Comparator.comparingDouble(pla -> mc.fontRendererObj.getStringWidth(pla.getName())));
    };

    @EventLink
    public final Listener<EventRender2D> eventRender2DListener = event -> {
        if (isNull()) {
            return;
        }

        int centerScreenX = event.getScaledResolution().getScaledWidth() / 2 + 15;
        int centerScreenY = event.getScaledResolution().getScaledHeight() / 2 + 15;

        if (!otherTargets.isEmpty()) {
            drawTargetHud(otherTargets.get(0), centerScreenX, centerScreenY);

            if (otherTargets.size() > 1 && otherTargets.get(1) != null) {
                String name = "Name > " + otherTargets.get(0).getGameProfile().getName();

                int nameL = mc.fontRendererObj.getStringWidth(name);

                String health = "Health > " + df.format(otherTargets.get(0).getHealth());

                int healthLenght = mc.fontRendererObj.getStringWidth(health);

                int maxxL = Math.max(nameL, healthLenght);

                int headSize = (mc.fontRendererObj.FONT_HEIGHT * 3) + 6;

                drawTargetHud(otherTargets.get(1), centerScreenX + headSize + maxxL + 4 + 5, centerScreenY);

                if (otherTargets.size() > 2 && otherTargets.get(2) != null) {
                    centerScreenX = event.getScaledResolution().getScaledWidth() / 2 + 15;
                    centerScreenY = event.getScaledResolution().getScaledHeight() / 2 + 15 + headSize + 8 + 5;
                    drawTargetHud(otherTargets.get(2), centerScreenX, centerScreenY);
                }
            }
        }
    };

    private void drawTargetHud(EntityPlayer target, int x, int y) {
        String name = "Name > " + target.getGameProfile().getName();

        int nameL = mc.fontRendererObj.getStringWidth(name);

        String health = "Health > " + df.format(target.getHealth());

        int healthLenght = mc.fontRendererObj.getStringWidth(health);

        int maxxL = Math.max(nameL, healthLenght);

        int headSize = (mc.fontRendererObj.FONT_HEIGHT * 3) + 6;

        Gui.drawRect(x - 2, y - 2, x + headSize + maxxL + 4, y - 2 + headSize, new Color(0, 0, 0, 80).getRGB());

        DrawUtils.color(-1);
        mc.getTextureManager().bindTexture(((AbstractClientPlayer) target).getLocationSkin());
        Gui.drawScaledCustomSizeModalRect(x - 2, y - 2, (float) 8.0, (float) 8.0, 8, 8, headSize, headSize, 64.0F, 64.0F);

        mc.fontRendererObj.drawStringWithShadow(name, x + 2 + headSize, y + 2, -1);
        mc.fontRendererObj.drawStringWithShadow(health, x + 2 + headSize, y + 2 + mc.fontRendererObj.FONT_HEIGHT, -1);
        mc.fontRendererObj.drawStringWithShadow(getW_L(target), x + 2 + headSize, y + 2 + (mc.fontRendererObj.FONT_HEIGHT * 2), -1);

        Gui.drawRect(x - 2, y + headSize - 2, x + headSize + maxxL + 4, y + headSize + 8, new Color(0, 0, 0, 80).getRGB());

        DrawUtils.drawGradientRect(x - 2, y + headSize - 2, x + headSize + maxxL + 4, y + headSize + 8, ThemeUtil.getMainColor().getRGB(), Color.white.getRGB());
    }

    private String getW_L(EntityPlayer target) {
        if (target.getHealth() < mc.thePlayer.getHealth()) {
            return "Winning";
        } else if (target.getHealth() > mc.thePlayer.getHealth()) {
            return "Losing";
        } else {
            return "Draw";
        }
    }
}
