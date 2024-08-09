package dev.darkmoon.client.ui.menu.main;

import dev.darkmoon.client.DarkMoon;
import dev.darkmoon.client.ui.menu.altmanager.GuiAltManager;
import dev.darkmoon.client.ui.menu.widgets.CustomButton;
import dev.darkmoon.client.utility.math.AnimBackground;
import dev.darkmoon.client.utility.math.Vec2i;
import dev.darkmoon.client.utility.misc.ImageButton;
import dev.darkmoon.client.utility.render.ColorUtility;
import dev.darkmoon.client.utility.render.RenderUtility;
import dev.darkmoon.client.utility.render.font.Fonts;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

public class GuiMainMenuElement extends GuiScreen {
    protected ArrayList<ImageButton> imageButtons = new ArrayList();
    private double scrollOffset;
    private final AnimBackground backgroundShader;
    public static long initTime = System.currentTimeMillis();
    public GuiMainMenuElement() {
        try {
            this.backgroundShader = new AnimBackground("/noise.fsh");
        } catch (IOException var9) {
            throw new IllegalStateException("Failed to load background shader", var9);
        }
    }

    int offset = 50;
    @Override
    public void initGui() {
        ScaledResolution sr = new ScaledResolution(this.mc);
        int width = sr.getScaledWidth();
        int height = sr.getScaledHeight();
        this.buttonList.add(new CustomButton(0, width / 2 - 65, height / 2 - 40, 130, 15, "SinglePlayer"));
        this.buttonList.add(new CustomButton(1, width / 2 - 65, height / 2 - 12 - 6, 130, 15, "Multiplayer"));
        this.buttonList.add(new CustomButton(2, width / 2 - 65, height / 2 + 16 - 6 * 2, 130, 15, "AltManager"));
        this.buttonList.add(new CustomButton(3, width / 2 - 65, height / 2 + 44 - 6 * 3, 130, 15, "Options"));
        this.buttonList.add(new CustomButton(4, this.width / 2 - 20, (int) (this.height / 2 + 152 / 1.5 - offset), 20, 20, ""));
        this.buttonList.add(new CustomButton(5, this.width / 2 + 3, (int) (this.height / 2 + 152 / 1.5 - offset), 20, 20, ""));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        ScaledResolution sr = new ScaledResolution(this.mc);
        GlStateManager.disableCull();
        int scaledWidth = DarkMoon.getInstance().getScaleMath().calc(sr.getScaledWidth());
        this.backgroundShader.useShader(sr.getScaledWidth() + 800, sr.getScaledHeight(), (float)mouseX, (float)mouseY, (float)(System.currentTimeMillis() - this.initTime) / 1000.0F);
        GL11.glBegin(7);
        GL11.glVertex2f(-1.0F, -1.0F);
        GL11.glVertex2f(-1.0F, 1.0F);
        GL11.glVertex2f(1.0F, 1.0F);
        GL11.glVertex2f(1.0F, -1.0F);
        GL11.glEnd();
        GL20.glUseProgram(0);
        GlStateManager.disableCull();

        GlStateManager.pushMatrix();
        GlStateManager.disableBlend();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        double y = 23.0;
        double x = 8.0;
        double right = x + (double)((int)((float)sr.getScaledWidth() / 4.0f));
        double top = y + (double)(sr.getScaledHeight() - 50);
        Color color11 = DarkMoon.getInstance().getThemeManager().getCurrentStyleTheme().getColors()[0];
        Color color22 = DarkMoon.getInstance().getThemeManager().getCurrentStyleTheme().getColors()[1];
        RenderUtility.applyRound(35, 35, 17, 1, () -> RenderUtility.drawProfile(scaledWidth - 45, 10, 35, 35));
        Fonts.mntsb17.drawString(DarkMoon.getInstance().getUserInfo().getName(),
                scaledWidth - 50 - Fonts.mntsb17.getStringWidth(DarkMoon.getInstance().getUserInfo().getName()), 20, Color.WHITE.getRGB());
        Fonts.mntssb16.drawString("UID: " + DarkMoon.getInstance().getUserInfo().getUid(),
                scaledWidth - 50 - Fonts.mntssb16.getStringWidth("UID: " + DarkMoon.getInstance().getUserInfo().getUid()), 30, Color.WHITE.getRGB());
        Fonts.mntsb30.drawGradientString("DarkMoon", sr.getScaledWidth() / 2 - 42, sr.getScaledHeight() / 2 - 70, color11, color22);
        Fonts.mntsb14.drawGradientString("Best DLC.", sr.getScaledWidth() / 2 - 20, sr.getScaledHeight() / 2 - 52, color11, color22);
        GlStateManager.pushMatrix();
        GL11.glEnable((int)3089);
        RenderUtility.scissorRect((int)x, (int)y - 3, (int)right + sr.getScaledWidth(), top);
        if (RenderUtility.isHovered(x, y, right, top, mouseX, mouseY) && Mouse.hasWheel()) {
            int wheel = Mouse.getDWheel();
            if (wheel < 0) {
                this.scrollOffset += 28.0;
                if (this.scrollOffset < 0.0) {
                    this.scrollOffset = 0.0;
                }
            } else if (wheel > 0) {
                this.scrollOffset -= 28.0;
                if (this.scrollOffset < 0.0) {
                    this.scrollOffset = 0.0;
                }
            }
        }
        GL11.glDisable((int)3089);
        RenderUtility.drawImage(new ResourceLocation("darkmoon/textures/" + "close" + ".png"), this.width / 2 - 20 + 1, (int) (this.height / 2 + 152 / 1.5 - offset) + 1, 18, 18, Color.WHITE);
        RenderUtility.drawImage(new ResourceLocation("darkmoon/textures/" + "discord" + ".png"), this.width / 2 + 3 + 1, (int) (this.height / 2 + 152 / 1.5 - offset) + 1, 18, 18, Color.WHITE);
        GlStateManager.popMatrix();
        if (!ImageButton.hoverShop) {
            Fonts.mntsb13.drawString2("Welcome back, " + DarkMoon.getInstance().getUserInfo().getName() + "!", (float) 3.0, sr.getScaledHeight() - Fonts.mntsb15.getFontHeight() - 2, -1);
        }
        for (ImageButton imageButton : this.imageButtons) {
            if (imageButton == null) continue;
            imageButton.draw(mouseX, mouseY, Color.WHITE);
            if (!Mouse.isButtonDown((int)0)) continue;
            imageButton.onClick(mouseX, mouseY);
        }
        GlStateManager.popMatrix();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    protected boolean isHovered(int x, int y, int mouseX, int mouseY) {
        return RenderUtility.isHovered(x, y, x + this.width, y + this.height, mouseX, mouseY);
    }
    public static void openLink(String link) {
        try {
            Desktop desktop = Desktop.getDesktop();
            desktop.browse(new URI(link));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void actionPerformed(GuiButton button) throws IOException {
        switch (button.id) {
            case 0: {
                this.mc.displayGuiScreen(new GuiWorldSelection(this));
                break;
            }
            case 1: {
                this.mc.displayGuiScreen(new GuiMultiplayer(this));
                break;
            }
            case 2: {
                this.mc.displayGuiScreen(new GuiAltManager());
                break;
            }
            case 3: {
                this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
                break;
            }
            case 4: {
                this.mc.shutdown();
                break;
            }
            case 5:
                openLink("https://discord.gg/JzggTUk4tS");
                break;
        }
        super.actionPerformed(button);
    }
}