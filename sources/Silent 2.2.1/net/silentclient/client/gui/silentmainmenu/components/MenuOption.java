package net.silentclient.client.gui.silentmainmenu.components;

import net.minecraft.client.Minecraft;
import net.silentclient.client.Client;
import net.silentclient.client.gui.animation.SimpleAnimation;
import net.silentclient.client.gui.elements.Button;
import net.silentclient.client.gui.font.SilentFontRenderer;
import net.silentclient.client.gui.lite.clickgui.utils.RenderUtils;
import net.silentclient.client.utils.ColorUtils;

import java.awt.*;

public class MenuOption extends Button {
    private SimpleAnimation animation;

    public MenuOption(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
        super(buttonId, x, y, widthIn, heightIn, buttonText);
        animation = new SimpleAnimation(0);
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        if (this.visible) {
            this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;

            RenderUtils.drawRect(this.xPosition, this.yPosition, animation.getValue(), this.height, new Color(255, 255, 255, 76).getRGB());
            if(this.displayString.equalsIgnoreCase("store")) {
                ColorUtils.setColor(new Color(255, 230, 0).getRGB());
            }
            Client.getInstance().getSilentFontRenderer().drawString(this.displayString.toUpperCase(), this.xPosition + 3, this.yPosition + (this.height / 2) - 7, 14, SilentFontRenderer.FontType.TITLE);

            animation.setAnimation(this.hovered && this.enabled ? this.width : 0, 9);
        } else {
            animation.setValue(0);
        }
    }
}
