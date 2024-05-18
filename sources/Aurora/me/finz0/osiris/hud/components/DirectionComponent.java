package me.finz0.osiris.hud.components;

import com.mojang.realmsclient.gui.ChatFormatting;
import de.Hero.clickgui.ClickGUI;
import de.Hero.clickgui.Panel;
import de.Hero.clickgui.util.ColorUtil;
import de.Hero.clickgui.util.FontUtil;
import me.finz0.osiris.AuroraMod;
import me.finz0.osiris.module.ModuleManager;
import me.finz0.osiris.module.modules.gui.Direction;
import me.finz0.osiris.util.Rainbow;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;

import java.awt.*;

public class DirectionComponent extends Panel {
    public DirectionComponent(double ix, double iy, ClickGUI parent) {
        super("Direction", ix, iy, 10, 10, false, parent);
        this.isHudComponent = true;

    }



    Direction mod = ((Direction) ModuleManager.getModuleByName("Direction"));

    Color c;
    boolean font;
    Color text;
    Color color;
    String direction;


    public void drawHud(){
        doStuff();
        if(font) AuroraMod.fontRenderer.drawStringWithShadow(direction, (float)x, (float)y, text.getRGB());
        else mc.fontRenderer.drawStringWithShadow(direction, (float)x, (float)y, text.getRGB());
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks){
        doStuff();
        double w = mc.fontRenderer.getStringWidth(direction) + 2;
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
            if (font) AuroraMod.fontRenderer.drawStringWithShadow(direction, (float) x, (float) startY, text.getRGB());
            else mc.fontRenderer.drawStringWithShadow(direction, (float) x, (float) startY, text.getRGB());
        }
    }

    private void doStuff() {
        color = mod.color.getValColor();
        text = mod.rainbow.getValBoolean() ? Rainbow.getColor() : color;
        font = mod.customFont.getValBoolean();
        Entity entity = mc.getRenderViewEntity();
        EnumFacing enumfacing = entity.getHorizontalFacing();
        String s = "Invalid";
        boolean xz = mod.mode.getValString().equalsIgnoreCase("XZ");
        boolean nswe = mod.mode.getValString().equalsIgnoreCase("NSWE");
        ChatFormatting gray = ChatFormatting.GRAY;
        ChatFormatting reset = ChatFormatting.RESET;
        String l = ChatFormatting.GRAY + "[" + ChatFormatting.RESET;
        String r = ChatFormatting.GRAY + "]";
        switch (enumfacing) {
            case NORTH: s = xz ? l+"-Z"+r : nswe ? l+"N"+r : "North "+l+"-Z"+r; break;
            case SOUTH: s = xz ? l+"+Z"+r : nswe ? l+"S"+r : "South "+l+"+Z"+r; break;
            case WEST: s = xz ? l+"-X"+r : nswe ? l+"W"+r : "West "+l+"-X"+r; break;
            case EAST: s = xz ? l+"+X"+r : nswe ?l+ "E"+r : "East "+l+"+X"+r;
        }
        direction = s;
    }
}
