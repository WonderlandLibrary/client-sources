package io.github.liticane.clients.feature.module.impl.visual;

import io.github.liticane.clients.Client;
import io.github.liticane.clients.feature.event.api.EventListener;
import io.github.liticane.clients.feature.event.api.annotations.SubscribeEvent;
import io.github.liticane.clients.feature.event.impl.render.Render2DEvent;
import io.github.liticane.clients.feature.module.Module;
import io.github.liticane.clients.feature.module.impl.combat.Aura;
import io.github.liticane.clients.util.render.RoundedUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

import java.awt.*;

@Module.Info(name = "TargetHUD", category = Module.Category.VISUAL)
public class TargetHud extends Module {
    @SubscribeEvent
    private final EventListener<Render2DEvent> onRender2D = e -> {
        if(Aura.target == null) {
            return;
        }
        int x = 300;
        int y = 300;

        int width = 100;
        int width2 = mc.fontRendererObj.getStringWidth(Aura.target.getName() + " Hp: " + Aura.target.getHealth());
        int height = 40;
        RoundedUtil.drawRound(x, y, x + width2 - 240, y + height - 290,4, new Color(0,0,0,200));
        RoundedUtil.drawRound(x, y + 40, x + Aura.target.getHealth(), y + height - 330,4,Color.red);
        int centerX = x + width / 2 - mc.fontRendererObj.getStringWidth(Aura.target.getName() + " Hp: " + Aura.target.getHealth()) / 10;
        int centerY = y + height / 2 - mc.fontRendererObj.FONT_HEIGHT / 2;

        mc.fontRendererObj.drawStringWithShadow(Aura.target.getName() + " Hp: " + Aura.target.getHealth(), centerX + 1, centerY - 3, Client.INSTANCE.getThemeManager().getTheme().getFirstColor().getRGB());
        if(Aura.target instanceof EntityPlayer) {
            drawPlayerHead(Aura.target, x, y, x + width - 360, y + height - 300);
        }
    };
    public void drawPlayerHead(EntityLivingBase player, int x, int y, int width, int height) {
        if(Aura.target != null && Aura.target.hurtTime == 0) {
            GlStateManager.color(255, 255, 255);
        }
      Minecraft.getInstance().getTextureManager().bindTexture(player.getLocationSkin());
        Gui.drawScaledCustomSizeModalRect(x, y, 8, 8, 8, 8, width, height, 64, 64);
    }
}
