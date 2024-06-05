package me.superskidder.lune.guis.nullclickgui;

import com.google.common.collect.Lists;

import java.awt.Color;
import java.util.ArrayList;

import me.superskidder.lune.Lune;
import me.superskidder.lune.font.FontLoaders;
import me.superskidder.lune.modules.Mod;
import me.superskidder.lune.modules.ModCategory;
import me.superskidder.lune.modules.render.HUD;
import me.superskidder.lune.utils.render.RenderUtil;
import me.superskidder.lune.utils.render.RenderUtils;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.input.Mouse;

public class Window {
    public ModCategory category;
    public ArrayList<Button> buttons = Lists.newArrayList();
    public boolean drag;
    public boolean extended;
    public int x;
    public int y;
    public int expand;
    public int dragX;
    public int dragY;
    public int max;
    public int scroll;
    public int scrollTo;
    public double angel;

    public Window(ModCategory category, int x, int y) {
        this.category = category;
        this.x = x;
        this.y = y;
        this.max = 120;
        int y2 = y + 20;
        for (Mod c : Lune.moduleManager.modList) {
            if (c.getType() != category) continue;
            this.buttons.add(new Button(c, x + 5, y2 + 2));
            y2 += 15;
        }
        for (Button b2 : this.buttons) {
            b2.setParent(this);
        }
    }

    public void render(int mouseX, int mouseY) {
        Gui.drawRect(0.0, 0.0, 0.0, 0.0, 0);
        int current = 0;
        for (Button b3 : this.buttons) {
            if (b3.expand) {
                for (ValueButton v : b3.buttons) {
                    current += 15;
                }
            }
            current += 15;
        }
        int height = 15 + current;
        if (this.extended) {
            this.expand = height;
//            this.angel = this.angel + 20.0 < 180.0 ? (this.angel += 20.0) : 180.0;
        } else {
            this.expand = 0;
//            this.angel = this.angel - 20.0 > 0.0 ? (this.angel -= 20.0) : 0.0;
        }
        RenderUtil.drawRect(this.x, this.y + 2, this.x + 90, this.y + 3 + this.expand, new Color(57, 57, 57).getRGB());
        RenderUtil.drawGradientSideways(this.x, this.y, this.x + 90, this.y + 18, new Color(new Color(HUD.themeColor).getRed(), (new Color(HUD.themeColor).getGreen()) <= 255 ? (new Color(HUD.themeColor).getGreen()) : 255, new Color(HUD.themeColor).getBlue()).getRGB(), new Color(new Color(HUD.themeColor).getRed(), (new Color(HUD.themeColor).getGreen()) <= 255 ? (new Color(HUD.themeColor).getGreen() + 55) : 255, new Color(HUD.themeColor).getBlue()).getRGB());

        FontLoaders.F18.drawStringWithShadow(this.category.name(), this.x + 17, this.y + 6, -1);

        if (this.expand > 0) {
            GlStateManager.pushMatrix();
            this.buttons.forEach(b2 -> b2.render(mouseX, mouseY));
            RenderUtil.post();
            GlStateManager.popMatrix();
        }
        if (this.drag) {
            if (!Mouse.isButtonDown((int) 0)) {
                this.drag = false;
            }
            this.x = mouseX - this.dragX;
            this.y = mouseY - this.dragY;
            this.buttons.get((int) 0).y = this.y + 22 - this.scroll;
            for (Button b4 : this.buttons) {
                b4.x = this.x + 5;
            }
        }
        RenderUtils.drawGradientRect(this.x, this.y + 18, this.x + 90, this.y + 21, new Color(0, 0, 0, 0).getRGB(), new Color(0, 0, 0, 100).getRGB());

    }

    public void key(char typedChar, int keyCode) {
        this.buttons.forEach(b2 -> b2.key(typedChar, keyCode));
    }

    public void mouseScroll(int mouseX, int mouseY, int amount) {
        if (mouseX > this.x - 2 && mouseX < this.x + 92 && mouseY > this.y - 2 && mouseY < this.y + 17 + this.expand) {
            this.scrollTo = (int) ((float) this.scrollTo - (float) (amount / 120 * 28));
        }
    }

    public void click(int mouseX, int mouseY, int button) {
        if (mouseX > this.x - 2 && mouseX < this.x + 92 && mouseY > this.y - 2 && mouseY < this.y + 17) {
            if (button == 1) {
                boolean bl = this.extended = !this.extended;
            }
            if (button == 0) {
                this.drag = true;
                this.dragX = mouseX - this.x;
                this.dragY = mouseY - this.y;
            }
        }
        if (this.extended) {
            this.buttons.stream().filter(b2 -> b2.y < this.y + this.expand).forEach(b2 -> b2.click(mouseX, mouseY, button));
        }
    }
}

