package me.superskidder.lune.guis.nullclickgui;

import com.google.common.collect.Lists;

import java.awt.Color;
import java.util.ArrayList;

import me.superskidder.lune.Lune;
import me.superskidder.lune.font.FontLoaders;
import me.superskidder.lune.modules.Mod;
import me.superskidder.lune.modules.render.HUD;
import me.superskidder.lune.utils.render.RenderUtils;
import me.superskidder.lune.values.Value;

public class Button {
    public Mod cheat;
    public Window parent;
    public int x;
    public int y;
    public int index;
    public int remander;
    public double opacity = 0.0;
    public ArrayList<ValueButton> buttons = Lists.newArrayList();
    public boolean expand;

    public Button(Mod cheat, int x, int y) {
        this.cheat = cheat;
        this.x = x;
        this.y = y;
        int y2 = y + 14;
        if (cheat == Lune.INSTANCE.moduleManager.getModByClass(HUD.class)) {
            this.buttons.add(new ColorValueButton(x + 15, y2));
        }
        for (Value v : cheat.getValues()) {
            this.buttons.add(new ValueButton(v, x + 5, y2));
            y2 += 15;
        }
        this.buttons.add(new KeyBindButton(cheat, x + 5, y2));
    }

    public void render(int mouseX, int mouseY) {
        if (this.cheat.getValues().size() + (this.cheat == Lune.INSTANCE.moduleManager.getModByClass(HUD.class) ? 2 : 1) != this.buttons.size()) {
            this.buttons.clear();
            int y2 = this.y + 14;
            if (this.cheat == Lune.INSTANCE.moduleManager.getModByClass(HUD.class)) {
                this.buttons.add(new ColorValueButton(this.x + 15, y2));
            }
            for (Value v : this.cheat.getValues()) {
                this.buttons.add(new ValueButton(v, this.x + 5, y2));
                y2 += 15;
            }
            this.buttons.add(new KeyBindButton(this.cheat, this.x + 5, y2));
        }
        if (this.index != 0) {
            Button b2 = this.parent.buttons.get(this.index - 1);
            this.y = b2.y + 15 + (b2.expand ? 15 * b2.buttons.size() : 0);
        }
        int i = 0;
        while (i < this.buttons.size()) {
            this.buttons.get((int) i).y = this.y + 14 + 15 * i;
            this.buttons.get((int) i).x = this.x + 5;
            ++i;
        }
        this.opacity = mouseX > this.x - 7 && mouseX < this.x + 85 && mouseY > this.y - 6 && mouseY < this.y + FontLoaders.F14.getStringHeight(this.cheat.getName()) + 4 ? (this.opacity + 10.0 < 200.0 ? (this.opacity += 10.0) : 200.0) : (this.opacity - 6.0 > 0.0 ? (this.opacity -= 6.0) : 0.0);
        RenderUtils.drawRect(this.x - 5, this.y - 4 + 15, this.x - 4 + 89, this.y - 4 + (this.expand ? buttons.size() * 15 + 15 : 0), new Color(37, 37, 37).getRGB());
        if (this.cheat.getState()) {
            Color color = new Color(new Color(120, 120, 120).getRGB());
            RenderUtils.drawGradientRect(this.x - 5, this.y + FontLoaders.F14.getStringHeight(this.cheat.getName()) + 5, this.x - 4 + 89, this.y + FontLoaders.F14.getStringHeight(this.cheat.getName()) + 7, new Color(0, 0, 0, 0).getRGB(), new Color(0, 0, 0, 50).getRGB());
            RenderUtils.drawGradientSideways(this.x - 5, this.y - 4, this.x - 4 + 89, this.y + FontLoaders.F14.getStringHeight(this.cheat.getName()) + 5, new Color(new Color(HUD.themeColor).getRed(), (new Color(HUD.themeColor).getGreen() + ((int) this.opacity) / 5) <= 255 ? (new Color(HUD.themeColor).getGreen() + ((int) this.opacity) / 5) : 255, new Color(HUD.themeColor).getBlue()).getRGB(), new Color(new Color(HUD.themeColor).getRed(), (new Color(HUD.themeColor).getGreen() + ((int) this.opacity) / 5) <= 255 ? (new Color(HUD.themeColor).getGreen() + 55) : 255, new Color(HUD.themeColor).getBlue()).getRGB());
            FontLoaders.F14.drawStringWithShadow(this.cheat.getName(), this.x, this.y, -1);
        } else {
            RenderUtils.drawRect(this.x - 5, this.y - 4, this.x - 4 + 89, this.y + FontLoaders.F14.getStringHeight(this.cheat.getName()) + 5, new Color((47 + ((int) opacity)) < 255 ? (47 + ((int) opacity/10)) : 255, (47 + ((int) opacity)) < 255 ? (47 + ((int) opacity/10)) : 255, (47 + ((int) opacity)) < 255 ? (47 + ((int) opacity/10)) : 255).getRGB());
            FontLoaders.F14.drawStringWithShadow(this.cheat.getName(), this.x, this.y, -1);
        }
        if (!this.expand && this.buttons.size() > 1) {
            FontLoaders.F16.drawString("...", this.x + 75, this.y, -1);
            //Gui.drawRect(this.x - 6 + 89, this.y - 4, this.x - 6 + 90, this.y + FontLoaders.F14.getStringHeight(this.cheat.getName()) + 5, new Color(new Color(0, 0, 0).getRGB()).getRGB());
        }
//        Gui.drawRect(this.x - 4, this.y - 4, this.x - 2, this.y + FontLoaders.F14.getStringHeight(this.cheat.getName()) + 5, new Color(255, 255, 255, (int) this.opacity).getRGB());
        if (this.expand) {
            this.buttons.forEach(b -> b.render(mouseX, mouseY));
        }

    }

    public void key(char typedChar, int keyCode) {
        this.buttons.forEach(b -> b.key(typedChar, keyCode));
    }

    public void click(int mouseX, int mouseY, int button) {
        if (mouseX > this.x - 7 && mouseX < this.x + 85 && mouseY > this.y - 6 && mouseY < this.y + FontLoaders.F14.getStringHeight(this.cheat.getName()) + 4) {
            if (button == 0) {
                this.cheat.setStage(!this.cheat.getState());
            }
            if (button == 1 && !this.buttons.isEmpty()) {
                boolean bl = this.expand = !this.expand;
            }
        }
        if (this.expand) {
            this.buttons.forEach(b -> b.click(mouseX, mouseY, button));
        }
    }

    public void setParent(Window parent) {
        this.parent = parent;
        int i = 0;
        while (i < this.parent.buttons.size()) {
            if (this.parent.buttons.get(i) == this) {
                this.index = i;
                this.remander = this.parent.buttons.size() - i;
                break;
            }
            ++i;
        }
    }
}

