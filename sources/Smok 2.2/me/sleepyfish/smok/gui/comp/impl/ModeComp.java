package me.sleepyfish.smok.gui.comp.impl;

import java.awt.Color;

import me.sleepyfish.smok.gui.comp.IComp;
import me.sleepyfish.smok.rats.impl.visual.Gui;
import me.sleepyfish.smok.rats.settings.ModeSetting;
import me.sleepyfish.smok.utils.misc.ClientUtils;
import me.sleepyfish.smok.utils.misc.FastEditUtils;
import me.sleepyfish.smok.utils.font.FontUtils;
import me.sleepyfish.smok.utils.render.RoundedUtils;
import me.sleepyfish.smok.utils.render.color.ColorUtils;
import org.lwjgl.opengl.GL11;

// Class from SMok Client by SleepyFish
public class ModeComp implements IComp {

    private final ModuleComp module;
    private final ModeSetting<Enum<?>> mode;
    private int x;
    private int y;
    private int o;

    public ModeComp(ModeSetting<Enum<?>> desc, ModuleComp b, int o) {
        this.mode = desc;
        this.module = b;
        this.x = b.getCategory().getX() + b.getCategory().getWidth();
        this.y = b.getCategory().getY() + b.getOff();
        this.o = o;
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
        return this.y;
    }

    @Override
    public void mouseReleased(int x, int y, int m) {
    }

    @Override
    public void keyTyped(char chara, int key) {
    }

    @Override
    public void draw() {
        GL11.glPushMatrix();
        GL11.glScaled(0.5, 0.5, 0.5);
        int modeWidth = (int) (FontUtils.r24.getStringWidth(this.mode.getName() + ": ") * 0.5);
        FontUtils.r24.drawString(this.mode.getName() + ": ", ((float) ((this.module.getCategory().getX() + 4) * 2)), ((float) ((this.module.getCategory().getY() + this.o + 4) * 2)), ColorUtils.getFontColor(1).darker().darker());
        FontUtils.r24.drawString(String.valueOf(this.mode.getMode()), ((float) ((this.module.getCategory().getX() + modeWidth + 4) * 2)),((float) ((this.module.getCategory().getY() + this.o + 4) * 2)), ColorUtils.getFontColor(1).darker());
        GL11.glPopMatrix();
    }

    @Override
    public void update(int x, int y) {
        this.y = this.module.getCategory().getY() + this.o;
        this.x = this.module.getCategory().getX();

        if (this.isHovering(x, y) && this.module.isExpanded() && this.mode.getDescription() != "-") {
            if (!Gui.toolTips.isEnabled())
                return;

            Color c = ClientUtils.isSmokTheme() ? ColorUtils.getClientColor(90000) : Color.white;
            RoundedUtils.drawRoundOutline((float) (x + 5), (float) (y + 13), (float) ((int) FontUtils.r16.getStringWidth(this.mode.getDescription())), (float) ((int) (FontUtils.r16.getHeight() * 1.2F - 2.0)), 1.0F, 2.2F, ColorUtils.getBackgroundColor(4), ColorUtils.getBackgroundColor(4).brighter());
            FontUtils.r16.drawString(this.mode.getDescription(), (x + 5), (y + 13), c);
        }
    }

    @Override
    public void mouseDown(int x, int y, int b) {
        if (ClientUtils.inClickGui()) {
            if (this.isHovering(x, y)) {
                if (b == 1) {
                    this.mode.nextMode();
                }

                if (b == 0) {
                    this.mode.prevMode();
                }
            }
        }
    }

    private boolean isHovering(int x, int y) {
        if (this.module.isExpanded() && ClientUtils.inClickGui()) {
            return x > this.x && x < this.x + this.module.getCategory().getWidth() && y > this.y && y < this.y + FastEditUtils.settingGap;
        } else {
            return false;
        }
    }

}