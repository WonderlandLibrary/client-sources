package me.finz0.osiris.hud.components;

import de.Hero.clickgui.ClickGUI;
import de.Hero.clickgui.Panel;
import de.Hero.clickgui.util.ColorUtil;
import de.Hero.clickgui.util.FontUtil;
import me.finz0.osiris.AuroraMod;
import me.finz0.osiris.module.ModuleManager;
import me.finz0.osiris.module.modules.gui.Watermark;
import me.finz0.osiris.util.Rainbow;
import net.minecraft.client.gui.Gui;

import java.awt.*;

public class WatermarkComponent extends Panel {
    public WatermarkComponent(double ix, double iy, ClickGUI parent) {
        super("Watermark", ix, iy, 10, 10, true, parent);
        this.isHudComponent = true;

    }



    Watermark mod = ((Watermark) ModuleManager.getModuleByName("Watermark"));

    Color c;
    boolean font;
    Color text;
    Color color;


    public void drawHud(){
        doStuff();
        String s =  "Aurora ";
        if(mod.version.getValBoolean()) s += AuroraMod.MODVER;
        if(font) AuroraMod.fontRenderer.drawStringWithShadow(s, (float)x, (float)y, text.getRGB());
        else mc.fontRenderer.drawStringWithShadow(s, (float)x, (float)y, text.getRGB());
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks){
        doStuff();
        String s =  "Aurora ";
        if(mod.version.getValBoolean()) s += AuroraMod.MODVER;
        double w = mc.fontRenderer.getStringWidth(s) + 2;
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
            if (font) AuroraMod.fontRenderer.drawStringWithShadow(s, (float) x, (float) startY, text.getRGB());
            else mc.fontRenderer.drawStringWithShadow(s, (float) x, (float) startY, text.getRGB());
        }
    }

    private void doStuff() {
        color = new Color(mod.red.getValInt(), mod.green.getValInt(), mod.blue.getValInt());
        text = mod.rainbow.getValBoolean() ? Rainbow.getColor() : color;
        font = mod.customFont.getValBoolean();
    }
}
