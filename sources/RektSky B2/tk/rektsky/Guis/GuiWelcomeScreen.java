package tk.rektsky.Guis;

import net.minecraft.util.*;
import net.minecraft.client.*;
import tk.rektsky.*;
import java.awt.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.gui.*;

public class GuiWelcomeScreen extends GuiScreen
{
    private long firstRenderTime;
    private boolean setupDiscord;
    public static ResourceLocation avatar;
    private static UnicodeFontRenderer frTitle;
    private static UnicodeFontRenderer frDescription;
    
    public GuiWelcomeScreen() {
        this.firstRenderTime = 0L;
        this.setupDiscord = false;
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        final long renderedTime = Minecraft.getSystemTime() - this.firstRenderTime;
        this.drawGradientRect(0, 0, this.width, this.height, 0, Integer.MIN_VALUE);
        this.mc.getTextureManager().bindTexture(new ResourceLocation("rektsky/images/background.png"));
        Gui.drawModalRectWithCustomSizedTexture(0, 0, 0.0f, 0.0f, this.width, this.height, (float)this.width, (float)this.height);
        if (GuiWelcomeScreen.avatar != null || this.setupDiscord) {}
        if (this.setupDiscord) {
            final UnicodeFontRenderer frTitle = GuiWelcomeScreen.frTitle;
            final String text = "Welcome back!";
            final float x = this.width / 2.0f - GuiWelcomeScreen.frTitle.getStringWidth("Welcome back!") / 2.0f;
            final float n = this.height / 2.0f;
            GuiWelcomeScreen.frTitle.getClass();
            frTitle.drawString(text, x, n - 9 * 3, 16777215);
            final UnicodeFontRenderer frDescription = GuiWelcomeScreen.frDescription;
            final String string = "Logged in as " + Client.userName;
            final float x2 = this.width / 2.0f - GuiWelcomeScreen.frDescription.getStringWidth("Logged in as " + Client.userName) / 2.0f;
            final float n2 = this.height / 2.0f;
            GuiWelcomeScreen.frTitle.getClass();
            frDescription.drawString(string, x2, n2 + 9 * 5, 16777215);
        }
        else {
            final UnicodeFontRenderer frTitle2 = GuiWelcomeScreen.frTitle;
            final String text2 = "Sorry : (";
            final float x3 = this.width / 2.0f - GuiWelcomeScreen.frTitle.getStringWidth("Sorry : (") / 2.0f;
            final float n3 = this.height / 2.0f;
            GuiWelcomeScreen.frTitle.getClass();
            frTitle2.drawString(text2, x3, n3 - 9.0f / 2.0f, 16777215);
            final UnicodeFontRenderer frDescription2 = GuiWelcomeScreen.frDescription;
            final String text3 = "But please open your discord to launch the client!";
            final float x4 = this.width / 2.0f - GuiWelcomeScreen.frDescription.getStringWidth("But please open your discord to launch the client!");
            final float n4 = this.height / 2.0f;
            GuiWelcomeScreen.frTitle.getClass();
            frDescription2.drawString(text3, x4, n4 + 9 * 5, 16777215);
        }
        if (renderedTime <= 3000L) {
            final Color color = new Color(0.0f, 0.0f, 0.0f, 1.0f - renderedTime / 3000.0f);
            GlStateManager.color(1.0f, 1.0f, 1.0f, renderedTime / 3000.0f);
            this.drawGradientRect(0, 0, this.width, this.height, color.getRGB(), color.getRGB());
        }
        if (!this.setupDiscord) {
            return;
        }
        if (renderedTime >= 7999L) {
            this.mc.displayGuiScreen(new GuiMainMenu());
        }
        if (renderedTime >= 5000L) {
            final Color color = new Color(0.0f, 0.0f, 0.0f, Math.min((renderedTime - 5000.0f) / 3000.0f, 1.0f));
            GlStateManager.color(1.0f, 1.0f, 1.0f, (renderedTime - 8000L) / 3000.0f);
            this.drawGradientRect(0, 0, this.width, this.height, color.getRGB(), color.getRGB());
        }
    }
    
    @Override
    public void initGui() {
        this.firstRenderTime = Minecraft.getSystemTime();
        this.setupDiscord = true;
    }
    
    static {
        GuiWelcomeScreen.frTitle = Client.getFontWithSize(112);
        GuiWelcomeScreen.frDescription = Client.getFontWithSize(36);
    }
}
