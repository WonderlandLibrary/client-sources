package me.finz0.osiris.hud.components;

import de.Hero.clickgui.ClickGUI;
import de.Hero.clickgui.Panel;
import de.Hero.clickgui.util.ColorUtil;
import de.Hero.clickgui.util.FontUtil;
import me.finz0.osiris.AuroraMod;
import me.finz0.osiris.module.ModuleManager;
import me.finz0.osiris.module.modules.gui.Gapples;
import me.finz0.osiris.util.FontUtils;
import me.finz0.osiris.util.Rainbow;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.awt.*;

public class GappsComponent extends Panel {
    public GappsComponent(double ix, double iy, ClickGUI parent) {
        super("Gapples", ix, iy, 10, 10, false, parent);
        this.isHudComponent = true;

    }



    Gapples mod = ((Gapples) ModuleManager.getModuleByName("Gapples"));

    Color c;
    boolean font;
    Color text;
    Color color;
    String gaps;
    int gapples;


    public void drawHud(){
        doStuff();
        if(mod.mode.getValString().equalsIgnoreCase("Item")){
            ItemStack is;
            if(gapples == 0) is = new ItemStack(Items.GOLDEN_APPLE);
            else is = new ItemStack(Items.GOLDEN_APPLE, gapples);
            RenderHelper.enableGUIStandardItemLighting();
            mc.getRenderItem().renderItemAndEffectIntoGUI(is, (int)x, (int)y);
            RenderHelper.disableStandardItemLighting();
            FontUtils.drawStringWithShadow(font, gapples + "", (int)x + 16, (int)y + 9 - (FontUtils.getFontHeight(font) / 2),  text.getRGB());
        } else {
            if (font) AuroraMod.fontRenderer.drawStringWithShadow(gaps, (float) x, (float) y, text.getRGB());
            else mc.fontRenderer.drawStringWithShadow(gaps, (float) x, (float) y, text.getRGB());
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks){
        doStuff();
        double w = mc.fontRenderer.getStringWidth(gaps) + 2;
        c = new Color(50, 50, 50, 100);
        if(isHudComponentPinned) c = new Color(ColorUtil.getClickGUIColor().darker().getRed(), ColorUtil.getClickGUIColor().darker().getGreen(), ColorUtil.getClickGUIColor().darker().getBlue(), 100);
        if (this.dragging) {
            x = x2 + mouseX;
            y = y2 + mouseY;
        }
        if(mod.mode.getValString().equalsIgnoreCase("Item")) w = 18;
        this.width = w;
        this.height = FontUtil.getFontHeight() + 2;
        Gui.drawRect((int)x, (int)y, (int)x + (int)width, (int)y + (int)height, c.getRGB());
        FontUtil.drawStringWithShadow(title, x, y + height / 2 - FontUtil.getFontHeight()/2f, 0xffffffff);

        if(extended) {
            double startY = y + height;
            Gui.drawRect((int) x, (int) startY, (int) x + (int) width, (int) startY + (int) height, c.getRGB());
            if(mod.mode.getValString().equalsIgnoreCase("Item")){
                ItemStack is = new ItemStack(Items.GOLDEN_APPLE, gapples);
                if(gapples == 0) is = new ItemStack(Items.END_CRYSTAL);
                RenderHelper.enableGUIStandardItemLighting();
                mc.getRenderItem().renderItemAndEffectIntoGUI(is, (int)x, (int)startY);
                RenderHelper.disableStandardItemLighting();
                FontUtils.drawStringWithShadow(font, gapples + "", (int)x + 16, (int)startY + 9 - (FontUtils.getFontHeight(font) / 2),  text.getRGB());
            } else {
                if (font) AuroraMod.fontRenderer.drawStringWithShadow(gaps, (float) x, (float) startY, text.getRGB());
                else mc.fontRenderer.drawStringWithShadow(gaps, (float) x, (float) startY, text.getRGB());
            }
        }
    }

    private void doStuff() {
        color = new Color(mod.red.getValInt(), mod.green.getValInt(), mod.blue.getValInt());
        text = mod.rainbow.getValBoolean() ? Rainbow.getColor() : color;
        font = mod.customFont.getValBoolean();
        gapples = mc.player.inventory.mainInventory.stream().filter(itemStack -> itemStack.getItem() == Items.GOLDEN_APPLE).mapToInt(ItemStack::getCount).sum();
        if (mc.player.getHeldItemOffhand().getItem() == Items.GOLDEN_APPLE) gapples += mc.player.getHeldItemOffhand().getCount();
        if(mod.mode.getValString().equalsIgnoreCase("Short")) gaps = gapples + " GAP";
        else gaps = gapples + " Gapples";
    }
}
