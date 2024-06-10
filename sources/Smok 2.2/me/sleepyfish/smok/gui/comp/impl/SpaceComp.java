package me.sleepyfish.smok.gui.comp.impl;

import me.sleepyfish.smok.gui.comp.IComp;
import me.sleepyfish.smok.utils.render.RoundedUtils;
import me.sleepyfish.smok.utils.render.color.ColorUtils;

// Class from SMok Client by SleepyFish
public class SpaceComp implements IComp {

    private final ModuleComp p;
    private int o;

    public SpaceComp(ModuleComp b, int o) {
        this.p = b;
        this.o = o;
    }

    @Override
    public void update(int x, int y) {
    }

    @Override
    public void mouseDown(int x, int y, int b) {
    }

    @Override
    public void mouseReleased(int x, int y, int m) {
    }

    @Override
    public void keyTyped(char chara, int key) {
    }

    @Override
    public void setComponentStartAt(int n) {
        this.o = n;
    }

    @Override
    public int getHeight() {
        return 0;
    }

    @Override
    public int getY() {
        return this.p.getCategory().getY() + this.o;
    }

    @Override
    public void draw() {
        RoundedUtils.drawRound((float) this.p.getCategory().getX() + 8.0F, (float) this.getY() + 5.0F, 65.0F, 1.0F, 2.0F, ColorUtils.getBackgroundColor(4).brighter());
    }

}