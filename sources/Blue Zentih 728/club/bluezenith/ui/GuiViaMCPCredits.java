package club.bluezenith.ui;

import club.bluezenith.core.data.preferences.Preferences;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

import java.awt.*;
import java.io.IOException;

import static club.bluezenith.util.font.FontUtil.vkDemibold;
import static club.bluezenith.util.font.FontUtil.vkMedium35Actual;
import static club.bluezenith.util.render.RenderUtil.blur;
import static club.bluezenith.util.render.RenderUtil.rect;

public class GuiViaMCPCredits extends GuiScreen {

    String[] leCredits = new String[] {
            "Hideri - ViaMCP Reborn maintainer (1.8.x)",
            "ViaForge - github.com/FlorianMichael/ViaForge",
            "Original ViaMCP - github.com/LaVache-FR/ViaMCP"
    };

    public GuiViaMCPCredits(GuiScreen screen) {
       parentScreen = screen;
    }

    @Override
    public void initGui() {
        this.buttonList.add(new GuiButton(1, this.width/2 - 100, height - 25, 200, 20, "Back")
                .onClick(() -> mc.displayGuiScreen(null)));
        parentScreen.setWorldAndResolution(mc, width, height);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        parentScreen.drawScreen(-1, -1, partialTicks);
        float start = (this.height / 2f) - 50;

        if(Preferences.useBlurInMenus) {
            drawCredits(start);
            blur(0, 0, width, height);
        } else {
            rect(0, 0, width, height, new Color(0, 0, 0, 200));
        }

        drawCredits(start);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    private void drawCredits(float start) {
        vkDemibold.drawString("Credits", this.width/2f - vkDemibold.getStringWidthF("Credits")/2f + 0.01F, start, -1);
        start += 35;
        for (String credit : leCredits) {
            vkMedium35Actual.drawString(credit, this.width/2f - vkMedium35Actual.getStringWidthF(credit)/2f + 0.01F, start, -1);
            start += 20;
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        button.runClickCallback();
    }
    
}
