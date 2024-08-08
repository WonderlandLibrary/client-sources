package lol.point.returnclient.module.impl.client;

import lol.point.Return;
import lol.point.returnclient.events.impl.render.EventRender2D;
import lol.point.returnclient.module.Category;
import lol.point.returnclient.module.Module;
import lol.point.returnclient.module.ModuleInfo;
import lol.point.returnclient.settings.impl.StringSetting;
import lol.point.returnclient.util.render.FastFontRenderer;
import lol.point.returnclient.util.render.shaders.ShaderUtil;
import lol.point.returnclient.util.system.ColorUtil;
import me.zero.alpine.listener.Listener;
import me.zero.alpine.listener.Subscribe;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

import java.awt.*;
import java.text.DecimalFormat;

@ModuleInfo(
        name = "TargetHUD",
        description = "show targets",
        category = Category.CLIENT
)
public class TargetHUD extends Module {

    private final StringSetting design = new StringSetting("Design", "Modern", new String[]{"Simple", "Myau", "Diablo Compact", "Serenium", "Modern"});

    private final FastFontRenderer productSans17 = Return.INSTANCE.fontManager.getFont("ProductSans-Regular 17");
    private final FastFontRenderer productSans18 = Return.INSTANCE.fontManager.getFont("ProductSans-Regular 18");
    private final FastFontRenderer icons = Return.INSTANCE.fontManager.getFont("Return-Icons 28");

    public TargetHUD() {
        addSettings(design);
    }

    public String getSuffix() {
        return design.value;
    }

    private float width, height;

    private final DecimalFormat decimalFormat = new DecimalFormat("0.0");

    @Subscribe
    private final Listener<EventRender2D> onRender2D = new Listener<>(eventRender2D -> {
        if (Return.INSTANCE.moduleManager.targetManager.target != null && mc.currentScreen == null) {
            if (Return.INSTANCE.moduleManager.targetManager.target instanceof EntityPlayer
                    || Return.INSTANCE.moduleManager.targetManager.target instanceof AbstractClientPlayer
            )
                draw(Return.INSTANCE.moduleManager.targetManager.target);
        }
    });

    private void draw(EntityLivingBase target) {
        String formattedHealth = String.format("%.1f", target.getHealth() / 2);

        Color gradient1, gradient2;
        gradient1 = Return.INSTANCE.themeManager.getThemeByName(Return.INSTANCE.moduleManager.getByClass(Theme.class).theme.value).gradient1;
        gradient2 = Return.INSTANCE.themeManager.getThemeByName(Return.INSTANCE.moduleManager.getByClass(Theme.class).theme.value).gradient2;

        ScaledResolution sr = new ScaledResolution(mc);

        float x = ((float) sr.getScaledWidth() / 2) - (width / 2) + width - 40;
        float y = ((float) sr.getScaledHeight() / 2) - (height / 2) + 40;

        String finalFormattedHealth = formattedHealth;
        String finalFormattedHealth1 = formattedHealth;
        String finalFormattedHealth2 = formattedHealth;
        switch (design.value) {
            case "Simple" -> {
                width = 150;
                height = 25;
                String name = "Name: " + target.getCommandSenderName();
                String health = "Health: " + decimalFormat.format(target.getHealth());

                int nameWidth = mc.fontRendererObj.getStringWidth(name);
                int healthWidth = mc.fontRendererObj.getStringWidth(health);
                int maxWidth = Math.max(nameWidth, healthWidth);
                int headSize = (mc.fontRendererObj.FONT_HEIGHT * 3) + 6;
                int diff = (int) (mc.thePlayer.getHealth() - target.getHealth());

                Gui.drawRect((int) (x - 2), (int) (y - 2), (int) (x + headSize + maxWidth + 4), (int) (y - 2 + headSize), new Color(0, 0, 0, 80).getRGB());

                ShaderUtil.renderGlow(() -> {
                    mc.fontRendererObj.drawStringWithShadow(name, x + 2 + headSize, y + 2, -1);
                    mc.fontRendererObj.drawStringWithShadow(health, x + 2 + headSize, y + 2 + mc.fontRendererObj.FONT_HEIGHT, -1);
                    mc.fontRendererObj.drawStringWithShadow((diff > 1 ? "Winning" : diff >= -1 ? "Draw" : "Losing"), x + 2 + headSize, y + 2 + (mc.fontRendererObj.FONT_HEIGHT * 2), -1);
                }, 2, 2, 0.86f);

                mc.fontRendererObj.drawStringWithShadow(name, x + 2 + headSize, y + 2, -1);
                mc.fontRendererObj.drawStringWithShadow(health, x + 2 + headSize, y + 2 + mc.fontRendererObj.FONT_HEIGHT, -1);
                mc.fontRendererObj.drawStringWithShadow((diff > 1 ? "Winning" : diff >= -1 ? "Draw" : "Losing"), x + 2 + headSize, y + 2 + (mc.fontRendererObj.FONT_HEIGHT * 2), -1);
                RenderUtil.draw_head(target, x - 2, y - 2, headSize);

                Gui.drawRect((int) (x - 2), (int) (y + headSize - 2), (int) (x + headSize + maxWidth + 4), (int) (y + headSize - 2), new Color(0, 0, 0, 80).getRGB());
            }
            case "Modern" -> {
                width = 160;
                height = 50;

                int diff = (int) (mc.thePlayer.getHealth() - target.getHealth());
                String status = diff > 1 ? "k" : diff >= -1 ? "j" : "e";

                RenderUtil.gradientH(x, y, width, 20, gradient1, gradient2);
                RenderUtil.rectangle(x, y + 20, width, height - 20, new Color(22,22,22));

                ShaderUtil.renderGlow(() -> {
                    RenderUtil.gradientH(x, y, width, 20, gradient1, gradient2);
                    productSans18.drawStringWithShadow(target.getCommandSenderName(), x + 5, y + 5, -1);
                    icons.drawStringWithShadow("T", x + width - icons.getWidth("T") - 4, y + 4.5f, -1);
                    icons.drawStringWithShadow(status, x + width - icons.getWidth("T") - 4 - icons.getWidth(status) - 2, (diff > 1) ? y + 6 : y + 4.5f, -1);
                }, 2, 2, 0.86f);

                productSans18.drawStringWithShadow(target.getCommandSenderName(), x + 5, y + 5, -1);
                icons.drawStringWithShadow("T", x + width - icons.getWidth("T") - 4, y + 4.5f, -1);

                icons.drawStringWithShadow(status, x + width - icons.getWidth("T") - 4 - icons.getWidth(status) - 2, (diff > 1) ? y + 6 : y + 4.5f, -1);

                icons.drawStringWithShadow("P", x + 5, y + 29, -1);
                productSans18.drawStringWithShadow(String.valueOf((int) target.getHealth()), x + 25, y + 30, -1);
                String dist = String.valueOf(Math.floor(mc.thePlayer.getDistanceToEntity(target) * 10) / 10);
                productSans18.drawStringWithShadow(dist, x + width - productSans18.getWidth(dist) - 5, y + 30, -1);
                icons.drawStringWithShadow("a", x + width - productSans18.getWidth(dist) - 25, y + 29, -1);
            }
            case "Serenium" -> {
                width = 160;
                height = 48;

                double percentageHealth = (target.getHealth() * 100) / target.getMaxHealth();
                int diff = (int) (mc.thePlayer.getHealth() - target.getHealth());

                RenderUtil.rectangle(x, y, width, height, new Color(0,0,0,100));
                RenderUtil.draw_head(target, x, y, 39);
                RenderUtil.rectangle(x, y + 39, width, 9, new Color(0,0,0, 120));
                RenderUtil.gradientH(x, y + 39, ((percentageHealth * 95) / 65) + 14, 9, gradient1, Color.WHITE);

                ShaderUtil.renderGlow(() -> {
                    RenderUtil.gradientH(x, y + 39, ((percentageHealth * 95) / 65) + 14, 9, gradient1, Color.WHITE);
                    productSans17.drawStringWithShadow("Name > " + target.getCommandSenderName(), x + 44, y + 7, -1);
                    productSans17.drawStringWithShadow("Health > " + String.format("%.1f", target.getHealth()), x + 44, y + 7 + 9, -1);
                    productSans17.drawStringWithShadow("Status > " + (diff > 1 ? "Winning" : diff >= -1 ? "Draw" : "Losing"), x + 44, y + 7 + 9 + 9, -1);
                }, 2, 2, 0.86f);

                productSans17.drawStringWithShadow("Name > " + target.getCommandSenderName(), x + 44, y + 7, -1);
                productSans17.drawStringWithShadow("Health > " + String.format("%.1f", target.getHealth()), x + 44, y + 7 + 9, -1);
                productSans17.drawStringWithShadow("Status > " + (diff > 1 ? "Winning" : diff >= -1 ? "Draw" : "Losing"), x + 44, y + 7 + 9 + 9, -1);

            }
            case "Diablo Compact" -> {
                width = 160;
                height = 45;
                double percentageHealth = (target.getHealth() * 100) / target.getMaxHealth();
                RenderUtil.rectangle(x, y, width, height, new Color(28, 28, 28, 144));

                RenderUtil.draw_head(target, x + 4, y + 4, 37);
                RenderUtil.rectangle(x + 47, y + 31, 109, 10, new Color(41, 41, 41));
                RenderUtil.rectangle(x + 47, y + 31, ((percentageHealth * 95) / 100) + 14, 10, gradient1);

                formattedHealth = (int) percentageHealth + "%";

                ShaderUtil.renderGlow(() -> {
                    mc.fontRendererObj.drawStringWithShadow(target.getCommandSenderName(), x + 47, y + 5, -1);
                    mc.fontRendererObj.drawString((int) Math.floor(mc.thePlayer.getDistanceToEntity(target) * 10) / 10 + " blocks", (int) (x + 47), (int) (y + 18), new Color(186, 186, 186));
                    mc.fontRendererObj.drawString(finalFormattedHealth, (int) (x + 109 - mc.fontRendererObj.getStringWidth(finalFormattedHealth1)), (int) (y + 32.5f), -1);
                    RenderUtil.rectangle(x + 47, y + 31, ((percentageHealth * 95) / 100) + 14, 10, gradient1);
                }, 2, 2, 0.86f);

                mc.fontRendererObj.drawStringWithShadow(target.getCommandSenderName(), x + 47, y + 5, -1);
                mc.fontRendererObj.drawString((int) Math.floor(mc.thePlayer.getDistanceToEntity(target) * 10) / 10 + " blocks", (int) (x + 47), (int) (y + 18), new Color(186, 186, 186));
                mc.fontRendererObj.drawString(formattedHealth, (int) (x + 109 - mc.fontRendererObj.getStringWidth(formattedHealth)), (int) (y + 32.5f), -1);
            }
            case "Myau" -> {
                width = 40 + mc.fontRendererObj.getStringWidth(target.getCommandSenderName()) + 2;
                height = 30;

                int maxWidth = (int) (width - 4);
                int currentWidth = (int) ((target.getHealth() / target.getMaxHealth()) * maxWidth);
                currentWidth = Math.max(0, Math.min(maxWidth, currentWidth));

                RenderUtil.rectangle(x, y, width, height, false, new Color(255, 85, 85));
                RenderUtil.rectangle(x, y, width, height, true, new Color(0, 0, 0, 60));

                RenderUtil.draw_head(target, x + 2, y + 2, 20);

                RenderUtil.rectangle(x + 2, y + height - 6, width - 4, 4, new Color(ColorUtil.blendHealthColours(target.getHealth() / target.getMaxHealth())).darker().darker().darker().darker());
                RenderUtil.rectangle(x + 2, y + height - 6, currentWidth, 4, ColorUtil.blendHealthColours(target.getHealth() / target.getMaxHealth()));

                int finalCurrentWidth = currentWidth;
                ShaderUtil.renderGlow(() -> {
                    RenderUtil.rectangle(x, y, width, height, false, new Color(255, 85, 85));
                    mc.fontRendererObj.drawStringWithShadow(target.getCommandSenderName(), x + 2 + 20 + 2, y + 2, new Color(255, 85, 85));
                    mc.fontRendererObj.drawStringWithShadow(finalFormattedHealth2 + "§c❤", x + 2 + 20 + 2, y + 12, -1);
                    RenderUtil.rectangle(x + 2, y + height - 6, width - 4, 4, new Color(ColorUtil.blendHealthColours(target.getHealth() / target.getMaxHealth())).darker().darker().darker().darker());
                    RenderUtil.rectangle(x + 2, y + height - 6, finalCurrentWidth, 4, ColorUtil.blendHealthColours(target.getHealth() / target.getMaxHealth()));
                }, 2, 2, 0.86f);

                mc.fontRendererObj.drawStringWithShadow(target.getCommandSenderName(), x + 2 + 20 + 2, y + 2, new Color(255, 85, 85));
                mc.fontRendererObj.drawStringWithShadow(formattedHealth + "§c❤", x + 2 + 20 + 2, y + 12, -1);
            }
        }
    }
}
