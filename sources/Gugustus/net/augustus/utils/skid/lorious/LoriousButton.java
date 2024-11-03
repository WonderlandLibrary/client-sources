package net.augustus.utils.skid.lorious;

import java.awt.Color;

import net.augustus.Augustus;
import net.augustus.ui.altlogin.AltLogin;
import net.augustus.ui.augustusmanager.AugustusOptions;
import net.augustus.utils.skid.lorious.ColorUtils;
import net.augustus.utils.skid.lorious.MouseUtil;
import net.augustus.utils.skid.lorious.RectUtils;
import net.augustus.utils.skid.lorious.anims.ColorAnimation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.util.Session;
import org.lwjgl.input.Mouse;

public class LoriousButton extends GuiButton {
    public ColorAnimation backgroundColor = new ColorAnimation();

    public int initX;

    public int initY;

    public LoriousButton(int buttonId, int x, int y, String buttonText) {
        super(buttonId, x, y, buttonText);
        this.initX = x;
        this.initY = y;
    }

    public void draw(int mouseX, int mouseY, int x, int y) {
        this.xPosition = x;
        this.yPosition = y;
        if (isHovered(mouseX, mouseY)) {
            if (!this.backgroundColor.getTarget().equals(new Color(0, 0, 0, 175)))
                this.backgroundColor.animate(300.0D, new Color(0, 0, 0, 175));
        } else if (!this.backgroundColor.getTarget().equals(new Color(15, 15, 15, 100))) {
            this.backgroundColor.animate(300.0D, new Color(15, 15, 15, 100));
        }
        RectUtils.drawRoundedRect(x + this.initX, y + this.initY, x + this.initX + this.width, y + this.initY + this.height, 3, this.backgroundColor.getColor().getRGB());
        Augustus.getInstance().getLoriousFontService().getComfortaa18().drawCenteredString(this.displayString, (x + this.initX + (float) this.width / 2), (y + this.initY + (float) this.height / 2 - (float) Augustus.getInstance().getLoriousFontService().getComfortaa18().getHeight() / 2), -1, isHovered(mouseX, mouseY), ColorUtils.getRainbow(4.0F, 0.5F, 1.0F), new Color(255, 255, 255));
    }

    public void onClick(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0 && Mouse.isButtonDown(mouseButton) && isHovered(mouseX, mouseY)) {
            if (this.id == 0)
                Minecraft.getMinecraft().displayGuiScreen(new GuiSelectWorld(new GuiMainMenu()));
            if (this.id == 1)
                Minecraft.getMinecraft().displayGuiScreen(new GuiMultiplayer(new GuiMainMenu()));
            if (this.id == 2)
                Minecraft.getMinecraft().displayGuiScreen(new GuiOptions(new GuiMainMenu(), (Minecraft.getMinecraft()).gameSettings));
            if (this.id == 3)
                Minecraft.getMinecraft().shutdown();
            if (this.id == 666)
                Minecraft.getMinecraft().displayGuiScreen(new AugustusOptions(new GuiMainMenu()));
            if (this.id == 420)
                Minecraft.getMinecraft().displayGuiScreen(new AltLogin(new GuiMainMenu()));

        }
    }

    public boolean isHovered(int mouseX, int mouseY) {
        return MouseUtil.isHovered(mouseX, mouseY, (this.xPosition + this.initX), (this.yPosition + this.initY), this.width, this.height);
    }
}
