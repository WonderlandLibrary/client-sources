package net.augustus.material.button;

import net.augustus.material.Main;
import net.augustus.material.Tab;
import net.augustus.settings.Setting;
import net.augustus.utils.skid.tomorrow.AnimationUtils;

public class Button {
    public float x, y;
    public Setting v;
    public float animation;
    public Runnable event;
    public boolean drag;

    public AnimationUtils animationUtils = new AnimationUtils();
    public Tab tab;

    public Button(float x, float y, Setting v, Tab moduleTab) {
        this.x = x;
        this.y = y;
        this.v = v;
        this.tab = moduleTab;
    }


    public void drawButton(float mouseX, float mouseY) {

    }

    public void draw(float mouseX, float mouseY) {
        drawButton(mouseX, mouseY);
    }

    public void mouseClicked(float mouseX, float mouseY) {

    }

    public void mouseClick(float mouseX, float mouseY) {
        if (Main.currentTab != this.tab) {
            return;
        }

        mouseClicked(mouseX, mouseY);
    }
}
