package tk.rektsky.Guis;

import net.minecraft.client.*;
import net.minecraft.util.*;
import net.minecraft.client.gui.*;
import java.awt.*;
import net.minecraft.client.renderer.*;
import tk.rektsky.*;

public class GuiLoading extends GuiScreen
{
    private static long firstRenderTime;
    private static UnicodeFontRenderer frTitle;
    private static UnicodeFontRenderer frDescription;
    
    @Override
    public void initGui() {
        super.initGui();
        GuiLoading.firstRenderTime = Minecraft.getSystemTime();
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        long renderedTime = 0L;
        if (Minecraft.getSystemTime() - GuiLoading.firstRenderTime >= 5000L) {
            renderedTime = Minecraft.getSystemTime() - GuiLoading.firstRenderTime - 5000L;
        }
        this.drawGradientRect(0, 0, this.width, this.height, 0, Integer.MIN_VALUE);
        this.mc.getTextureManager().bindTexture(new ResourceLocation("rektsky/icons/icon.png"));
        Gui.drawModalRectWithCustomSizedTexture(this.width / 2 - 128, this.height / 2 - 128, 0.0f, 0.0f, 256, 256, 256.0f, 256.0f);
        final UnicodeFontRenderer frDescription = GuiLoading.frDescription;
        final String text = "RektSky, made by fan87 & hackage";
        final float x = this.width / 2.0f - GuiLoading.frDescription.getStringWidth("RektSky, made by fan87 & hackage") / 2.0f;
        final float n = this.height / 2.0f + 198.0f;
        GuiLoading.frTitle.getClass();
        final float n2 = n + 9.0f;
        GuiLoading.frDescription.getClass();
        frDescription.drawString(text, x, n2 + 9.0f, 16777215);
        if (renderedTime >= 9999L) {
            this.mc.displayGuiScreen(new GuiWelcomeScreen());
        }
        if (renderedTime >= 7000L) {
            final Color color = new Color(0.0f, 0.0f, 0.0f, Math.min((renderedTime - 5000.0f) / 3000.0f, 1.0f));
            GlStateManager.color(1.0f, 1.0f, 1.0f, (renderedTime - 8000L) / 3000.0f);
            this.drawGradientRect(0, 0, this.width, this.height, color.getRGB(), color.getRGB());
        }
    }
    
    static {
        GuiLoading.firstRenderTime = 0L;
        GuiLoading.frTitle = Client.getFontWithSize(112);
        GuiLoading.frDescription = Client.getFontWithSize(36);
    }
}
