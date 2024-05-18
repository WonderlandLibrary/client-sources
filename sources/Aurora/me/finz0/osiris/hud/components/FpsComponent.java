package me.finz0.osiris.hud.components;

import com.mojang.realmsclient.gui.ChatFormatting;
import de.Hero.clickgui.ClickGUI;
import de.Hero.clickgui.Panel;
import de.Hero.clickgui.util.ColorUtil;
import de.Hero.clickgui.util.FontUtil;
import me.finz0.osiris.AuroraMod;
import me.finz0.osiris.module.ModuleManager;
import me.finz0.osiris.module.modules.gui.Fps;
import me.finz0.osiris.util.Rainbow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.math.MathHelper;

import java.awt.*;
import java.text.DecimalFormat;

public class FpsComponent extends Panel {
    public FpsComponent(double ix, double iy, ClickGUI parent) {
        super("FPS", ix, iy, 10, 10, false, parent);
        this.isHudComponent = true;

    }



    Fps mod = ((Fps) ModuleManager.getModuleByName("FPS"));

    Color c;
    boolean font;
    Color text;
    Color color;

    public void drawHud(){
        doStuff();
        final String fps =   "\u00A78Fps " + ChatFormatting.RESET + Minecraft.getDebugFPS();
        if(font) AuroraMod.fontRenderer.drawStringWithShadow(fps, (float)x, (float)y, text.getRGB());
        else mc.fontRenderer.drawStringWithShadow(fps, (float)x, (float)y, text.getRGB());
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks){
        doStuff();
        final String fps =   "\u00A78Fps " + ChatFormatting.RESET + Minecraft.getDebugFPS();
        double w = mc.fontRenderer.getStringWidth(fps) + 2;
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
            if (font) AuroraMod.fontRenderer.drawStringWithShadow(fps, (float) x, (float) startY, text.getRGB());
            else mc.fontRenderer.drawStringWithShadow(fps, (float) x, (float) startY, text.getRGB());
        }
    }

    private void doStuff() {
        color = new Color(mod.red.getValInt(), mod.green.getValInt(), mod.blue.getValInt());
        text = mod.rainbow.getValBoolean() ? Rainbow.getColor() : color;
        font = mod.customFont.getValBoolean();
    }
}
