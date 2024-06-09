package me.finz0.osiris.hud.components;

import de.Hero.clickgui.ClickGUI;
import de.Hero.clickgui.Panel;
import de.Hero.clickgui.util.ColorUtil;
import de.Hero.clickgui.util.FontUtil;
import me.finz0.osiris.module.ModuleManager;
import me.finz0.osiris.module.modules.gui.ArmorHUD;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ArmorComponent extends Panel {
    public ArmorComponent(double ix, double iy, ClickGUI parent) {
        super("Armor", ix, iy, 10, 10, false, parent);
        this.isHudComponent = true;

    }



    ArmorHUD mod = ((ArmorHUD) ModuleManager.getModuleByName("ArmorHUD"));


    boolean vertical;
    boolean reverse;
    Color c;


    public void drawHud(){
        drawArmor((int)x, (int)y);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks){
        c = new Color(50, 50, 50, 100);
        if(isHudComponentPinned) c = new Color(ColorUtil.getClickGUIColor().darker().getRed(), ColorUtil.getClickGUIColor().darker().getGreen(), ColorUtil.getClickGUIColor().darker().getBlue(), 100);
        if (this.dragging) {
            x = x2 + mouseX;
            y = y2 + mouseY;
        }
        Gui.drawRect((int)x, (int)y, (int)x + (int)width, (int)y + (int)height, c.getRGB());
        if(!extended) FontUtil.drawStringWithShadow(title, x, y + 5 - FontUtil.getFontHeight()/2f, 0xffffffff);
        width = FontUtil.getStringWidth(title);
        height = 20;

        if(extended) {
            double startY = y + 15;
            //Gui.drawRect((int) x, (int) startY, (int) x + (int) width, (int) startY + (int) height, c.getRGB());
            if(vertical){
                width = 20;
                height = 18 * 4;
            } else width = 18 * 4;
            drawArmor((int)x, (int)y);
        }
    }

    private void drawArmor(int x, int y){
        vertical = mod.vertical.getValBoolean();
        reverse = mod.reverse.getValBoolean();
        int i = 0;
        List<ItemStack> armor = new ArrayList<>();
        for(ItemStack is : mc.player.getArmorInventoryList()) armor.add(is);
        if(reverse) Collections.reverse(armor);
        for(ItemStack is : armor){
            int yy = y;
            int xx = x + i;
            if(vertical){
                yy = y + i;
                xx = x;
            }
            RenderHelper.enableGUIStandardItemLighting();
            mc.getRenderItem().renderItemAndEffectIntoGUI(is, xx, yy);
            mc.getRenderItem().renderItemOverlays(mc.fontRenderer, is, xx, yy);
            RenderHelper.disableStandardItemLighting();
            i += 18;
        }
    }
}
