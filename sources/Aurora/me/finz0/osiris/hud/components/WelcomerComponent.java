package me.finz0.osiris.hud.components;

import de.Hero.clickgui.ClickGUI;
import de.Hero.clickgui.Panel;
import de.Hero.clickgui.util.ColorUtil;
import de.Hero.clickgui.util.FontUtil;
import me.finz0.osiris.AuroraMod;
import me.finz0.osiris.module.ModuleManager;
import me.finz0.osiris.module.modules.gui.WelcomerGui;
import me.finz0.osiris.util.Rainbow;
import net.minecraft.client.gui.Gui;

import java.awt.*;

public class WelcomerComponent extends Panel {
    public WelcomerComponent(double ix, double iy, ClickGUI parent) {
        super("Welcome", ix, iy, 10, 10, false, parent);
        this.isHudComponent = true;

    }



    WelcomerGui mod = ((WelcomerGui) ModuleManager.getModuleByName("Welcome"));

    Color c;
    boolean font;
    Color text;
    Color color;
    String msg;


    public void drawHud(){
        doStuff();
        if(font) AuroraMod.fontRenderer.drawStringWithShadow(msg, (float)x, (float)y, text.getRGB());
        else mc.fontRenderer.drawStringWithShadow(msg, (float)x, (float)y, text.getRGB());
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks){
        doStuff();
        double w = mc.fontRenderer.getStringWidth(msg) + 2;
        c = new Color(50, 50, 50, 100);
        if(isHudComponentPinned) c = new Color(ColorUtil.getClickGUIColor().darker().getRed(), ColorUtil.getClickGUIColor().darker().getGreen(), ColorUtil.getClickGUIColor().darker().getBlue(), 100);
        if (this.dragging) {
            x = x2 + mouseX;
            y = y2 + mouseY;
        }
        this.width = w;
        this.height = FontUtil.getFontHeight() + 2;
        Gui.drawRect((int)x, (int)y, (int)x + (int)width, (int)y + (int)height, c.getRGB());
        FontUtil.drawStringWithShadow(title, x, y + height / 2 - FontUtil.getFontHeight()/2f, 0xffffffff);

        if(extended) {
            double startY = y + height;
            Gui.drawRect((int) x, (int) startY, (int) x + (int) width, (int) startY + (int) height, c.getRGB());
            if (font) AuroraMod.fontRenderer.drawStringWithShadow(msg, (float) x, (float) startY, text.getRGB());
            else mc.fontRenderer.drawStringWithShadow(msg, (float) x, (float) startY, text.getRGB());
        }
    }

    private void doStuff() {
        color = new Color(mod.red.getValInt(), mod.green.getValInt(), mod.blue.getValInt());
        text = mod.rainbow.getValBoolean() ? Rainbow.getColor() : color;
        font = mod.customFont.getValBoolean();
        if(mod.message.getValString().equalsIgnoreCase("Welcome1"))
            msg = "Welcome, " + mc.player.getName();
        if(mod.message.getValString().equalsIgnoreCase("Welcome2"))
            msg = "Welcome, " + mc.player.getName() + " (;";
        if(mod.message.getValString().equalsIgnoreCase("Hello1"))
            msg = "Hello " + mc.player.getName();
        if(mod.message.getValString().equalsIgnoreCase("Hello2"))
            msg = "Hello " + mc.player.getName() + " (:";
    }
}
