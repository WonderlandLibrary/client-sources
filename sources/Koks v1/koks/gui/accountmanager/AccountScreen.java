package koks.gui.accountmanager;

import koks.utilities.CustomFont;
import koks.utilities.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.UnicodeFont;

import java.awt.*;

/**
 * @author avox | lmao | kroko
 * @created on 06.09.2020 : 22:05
 */
public class AccountScreen extends GuiScreen {

    public RenderUtils renderUtils = new RenderUtils();
    public CustomFont customFont = new CustomFont("fonts/Helvetica45Light_0.ttf", 45);

    private final Color BACKGROUND_COLOR = new Color(30, 36, 43, 255);
    private final Color OVERLAY_COLOR = new Color(41, 44, 59, 255);
    private final Color TEXT_COLOR = new Color(212, 212, 212, 225);

    public AccountScreen() {

    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());

        /*
         * Background
         */
        renderUtils.drawRect(7, 0, 0, scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight(), BACKGROUND_COLOR);

        /*
         * Account Information
         */

        renderUtils.drawRect(7, scaledResolution.getScaledWidth() / 2 + 50, 20, scaledResolution.getScaledWidth() - 10, scaledResolution.getScaledHeight() - 20, OVERLAY_COLOR);
        customFont.drawString("Account Information", scaledResolution.getScaledWidth() / 2 + 50 / 25 + (scaledResolution.getScaledWidth() / 2- 10 / 2) / 2 - customFont.getStringWidth("Account Information") / 2, 10, TEXT_COLOR.getRGB());

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

}
