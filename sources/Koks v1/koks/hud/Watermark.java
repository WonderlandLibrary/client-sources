package koks.hud;

import koks.Koks;
import koks.modules.impl.utilities.HUD;
import koks.utilities.ColorUtil;
import koks.utilities.DeltaTime;
import koks.utilities.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.core.helpers.Clock;
import org.apache.logging.log4j.core.helpers.SystemClock;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author avox | lmao | kroko
 * @created on 02.09.2020 : 23:06
 */
public class Watermark {

    private final Minecraft mc = Minecraft.getMinecraft();
    private final FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;
    private final ResourceLocation resourceLocation = new ResourceLocation("client/generals/watermark.png");

    private final RenderUtils renderUtils = new RenderUtils();
    private ColorUtil colorUtil;
    private float animationX;
    private boolean reverse;

    public void drawWatermark() {
        ScaledResolution sr = new ScaledResolution(mc);

        if (Koks.getKoks().moduleManager.getModule(HUD.class).isToggled()) {

            if (Koks.getKoks().moduleManager.getModule(HUD.class).watermarkStyle.getSelectedMode().equals("Mario Kart")) {
                String name = Koks.getKoks().CLIENT_NAME;
                DateFormat dateFormat = new SimpleDateFormat("HH:mm");
                String time = dateFormat.format(Calendar.getInstance().getTime());

                String render = name + " §7(" + time + ")";
                fr.drawStringWithShadow(render, animationX, reverse ? sr.getScaledHeight() - 30 : 1, Koks.getKoks().client_color.getRGB());
                if (!reverse && animationX < sr.getScaledWidth() + fr.getStringWidth(render)) {
                    animationX += 0.2 * DeltaTime.getDeltaTime();
                    if (animationX > sr.getScaledWidth() - 1)
                        reverse = true;
                }

                if (reverse && animationX > -fr.getStringWidth(render)) {
                    animationX -= 0.2 * DeltaTime.getDeltaTime();
                    if (animationX < -fr.getStringWidth(render))
                        reverse = false;
                }
            } else if (Koks.getKoks().moduleManager.getModule(HUD.class).watermarkStyle.getSelectedMode().equals("Simple")) {
                String name = Koks.getKoks().CLIENT_NAME;
                DateFormat dateFormat = new SimpleDateFormat("HH:mm");
                String time = dateFormat.format(Calendar.getInstance().getTime());

                String render = name + " §7(" + time + ")";
                fr.drawStringWithShadow(render, 82 / 2 - fr.getStringWidth(render) / 2, 8, Koks.getKoks().client_color.getRGB());
            } else if (Koks.getKoks().moduleManager.getModule(HUD.class).watermarkStyle.getSelectedMode().equals("Custom with Shadow")) {

                GL11.glPushMatrix();
                GlStateManager.disableAlpha();
                GlStateManager.enableBlend();
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glColor4f(1, 1, 1, 1);
                renderUtils.drawImage(resourceLocation, -3, -5, 80, 80, false);
                GL11.glDisable(GL11.GL_BLEND);
                GlStateManager.enableAlpha();
                GlStateManager.disableBlend();
                GL11.glPopMatrix();
            }
        }
    }

}