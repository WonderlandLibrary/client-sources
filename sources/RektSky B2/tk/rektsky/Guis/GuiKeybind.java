package tk.rektsky.Guis;

import tk.rektsky.Module.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.*;
import tk.rektsky.Utils.Display.*;
import tk.rektsky.Module.Render.*;
import tk.rektsky.*;
import net.minecraft.client.*;
import org.lwjgl.input.*;
import java.io.*;

public class GuiKeybind extends GuiScreen
{
    private GuiScreen previoudScreen;
    private Module module;
    private final FontRenderer font;
    
    @Override
    public void initGui() {
        super.initGui();
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        this.drawDefaultBackground();
        GlStateManager.pushMatrix();
        GlStateManager.translate(this.width / 2.0f, this.height / 2.0f, 0.0f);
        GlStateManager.scale(4.0f, 4.0f, 0.0f);
        this.font.drawString("Press a key to bind " + this.module.name, -this.font.getStringWidth("Press a key to bind " + this.module.name) / 2, -this.font.FONT_HEIGHT / 2, 16777215);
        GlStateManager.popMatrix();
        this.font.drawString("Press a DEL to delete or ESC to cancel", this.width / 2 - this.font.getStringWidth("Press a DEL to delete or ESC to cancel") / 2, this.height / 2 + this.font.FONT_HEIGHT * 2 + 10, 16777215);
    }
    
    @Override
    protected void keyTyped(final char typedChar, final int keyCode) throws IOException {
        if (keyCode == 1) {
            Client.notify(new Notification.PopupMessage("Keybind", "Canceled binding " + this.module.name + " !", ColorUtil.NotificationColors.RED, 20));
            Minecraft.getMinecraft().displayGuiScreen(this.previoudScreen);
            return;
        }
        if (keyCode == 211) {
            Client.notify(new Notification.PopupMessage("Keybind", "Unbound " + this.module.name + " !", ColorUtil.NotificationColors.GREEN, 20));
            this.module.keyCode = 0;
            Minecraft.getMinecraft().displayGuiScreen(this.previoudScreen);
            return;
        }
        Client.notify(new Notification.PopupMessage("Keybind", "Bound " + this.module.name + " to " + Keyboard.getKeyName(keyCode) + " !", ColorUtil.NotificationColors.GREEN, 20));
        this.module.keyCode = keyCode;
        Minecraft.getMinecraft().displayGuiScreen(this.previoudScreen);
    }
    
    public GuiKeybind(final GuiScreen previoudScreen, final Module module) {
        this.font = Minecraft.getMinecraft().fontRendererObj;
        this.previoudScreen = previoudScreen;
        this.module = module;
    }
}
