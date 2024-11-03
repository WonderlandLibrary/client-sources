package net.silentclient.client.gui.silentmainmenu;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.GuiButton;
import net.silentclient.client.Client;
import net.silentclient.client.gui.font.SilentFontRenderer;
import net.silentclient.client.gui.util.RenderUtil;
import net.silentclient.client.mods.settings.GeneralMod;
import net.silentclient.client.utils.TimerUtils;

import java.awt.*;

public class MenuButton extends GuiButton {
    private int animatedOpcaity = 0;
    private TimerUtils animateTimer = new TimerUtils();

    public MenuButton(int buttonId, int x, int y, String buttonText) {
        super(buttonId, x, y, 80, 20, buttonText);
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;

        RenderUtil.drawRoundedRect(this.xPosition, this.yPosition, 80, 20, 1, new Color(20, 20, 20, 102).getRGB());
        RenderUtil.drawRoundedRect(xPosition, yPosition, width, height, 1, new Color(255, 255, 255, animatedOpcaity).getRGB());
        RenderUtil.drawRoundedOutline(this.xPosition, this.yPosition, 80, 20, 1, 1, new Color(214, 213, 210).getRGB());
        Client.getInstance().getSilentFontRenderer().drawCenteredString(this.displayString, this.xPosition + 40, this.yPosition + 3, 14, SilentFontRenderer.FontType.TITLE, 70);

        if (this.hovered && this.enabled) {
            if (this.animatedOpcaity < 75 && animateTimer.delay(30)) {
                this.animatedOpcaity += 15;
                animateTimer.reset();
            }
        } else {
            if (this.animatedOpcaity != 0 && animateTimer.delay(30)) {
                this.animatedOpcaity -= 15;
                animateTimer.reset();
            }
        }
    }

    @Override
    public void playPressSound(SoundHandler soundHandlerIn) {
        if(!Client.getInstance().getSettingsManager().getSettingByClass(GeneralMod.class, "Silent Button Sounds").getValBoolean()) {
            return;
        }
        super.playPressSound(soundHandlerIn);
    }
}
