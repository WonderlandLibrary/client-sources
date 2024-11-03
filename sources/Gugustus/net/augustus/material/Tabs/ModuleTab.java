package net.augustus.material.Tabs;

import net.augustus.Augustus;
import net.augustus.font.UnicodeFontRenderer;
import net.augustus.material.Main;
import net.augustus.material.Tab;
import net.augustus.material.button.Button;
import net.augustus.material.button.values.BMode;
import net.augustus.material.button.values.BNumbers;
import net.augustus.material.button.values.BOption;
import net.augustus.modules.Module;
import net.augustus.settings.BooleanValue;
import net.augustus.settings.DoubleValue;
import net.augustus.settings.Setting;
import net.augustus.settings.StringValue;

import java.awt.*;
import java.util.ArrayList;

public class ModuleTab extends Tab {
    private static UnicodeFontRenderer arial18;
    private static UnicodeFontRenderer arial16;
    static {
        try {
            arial18 = new UnicodeFontRenderer(Font.createFont(0, ModuleTab.class.getResourceAsStream("/ressources/arial.ttf")).deriveFont(18F));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            arial16 = new UnicodeFontRenderer(Font.createFont(0, ModuleTab.class.getResourceAsStream("/ressources/arial.ttf")).deriveFont(16F));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public Module module;
    private ArrayList<Button> btns = new ArrayList<>();

    public ModuleTab(Module m) {
        this.module = m;
        name = m.getName();
        for (Setting v : Augustus.getInstance().getSettingsManager().getSettingsByMod(m)) {
            if (v instanceof BooleanValue) {
                Button value = new BOption(startX, startY, v,this);
                btns.add(value);

            } else if (v instanceof DoubleValue) {
                Button value = new BNumbers(startX, startY, v,this);
                btns.add(value);

            } else if (v instanceof StringValue) {
                Button value = new BMode(startX, startY, v,this);
                btns.add(value);

            }
        }
    }


    float startX = Main.windowX + 20;
    float startY = Main.windowY + 70;

    public void render(float mouseX, float mouseY) {
        startX = Main.windowX + 20 + Main.animListX;
        startY = Main.windowY + 70;
        for (Button v : btns) {
            if(!v.v.isVisible())
                continue;
            v.x = startX;
            v.y = startY;
            v.draw(mouseX, mouseY);
            if (startX + 100 + arial18.getStringWidth(v.v.getName()) < Main.windowX + Main.windowWidth) {
                if (v instanceof BOption) {
                    startX += 40 + arial18.getStringWidth(v.v.getName());
                } else {
                    startX += 80;
                }
            } else {
                startX = Main.windowX + 20 + Main.animListX;
                startY += 30;
            }
        }
    }

    @Override
    public void mouseClicked(float mouseX, float mouseY) {
        super.mouseClicked(mouseX, mouseY);
        startX = Main.windowX + 20 + Main.animListX;
        startY = Main.windowY + 70;
        for (Button v : btns) {
            if(!v.v.isVisible())
                continue;
            v.mouseClick(mouseX, mouseY);
        }
    }
}
