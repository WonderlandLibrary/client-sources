package me.sleepyfish.smok.gui.comp.impl;

import me.sleepyfish.smok.gui.comp.IComp;
import me.sleepyfish.smok.rats.impl.visual.Gui;
import me.sleepyfish.smok.rats.settings.ClickSetting;
import me.sleepyfish.smok.utils.misc.ClientUtils;
import me.sleepyfish.smok.utils.misc.FastEditUtils;
import me.sleepyfish.smok.utils.entities.SoundUtils;
import me.sleepyfish.smok.utils.font.FontUtils;
import me.sleepyfish.smok.utils.render.RoundedUtils;
import me.sleepyfish.smok.utils.render.color.ColorUtils;
import org.lwjgl.opengl.GL11;
import java.awt.Color;

// Class from SMok Client by SleepyFish
public class ClickComp implements IComp {

    private final ModuleComp module;
    private final ClickSetting click;
    private int off;
    private int x;
    private int y;

    public ClickComp(ClickSetting bool, ModuleComp modComp, int off) {
        this.click = bool;
        this.module = modComp;
        this.x = modComp.getCategory().getX() + modComp.getCategory().getWidth();
        this.y = modComp.getCategory().getY() + modComp.getOff();
        this.off = off;
    }

    @Override
    public int getY() {
        return this.y;
    }

    @Override
    public void setComponentStartAt(int sk) {
        this.off = sk;
    }

    @Override
    public void keyTyped(char chara, int key) {
    }

    @Override
    public void mouseReleased(int x, int y, int b) {
    }

    @Override
    public int getHeight() {
        return 0;
    }

    @Override
    public void draw() {
        GL11.glPushMatrix();
        GL11.glScaled(0.5, 0.5, 0.5);
        FontUtils.i24.drawString("3",  ((float) this.module.getCategory().getX() + 74.5F) * 2.0F, (float) ((this.module.getCategory().getY() + this.off + 3) * 2), ColorUtils.getFontColor(1).darker().darker());
        FontUtils.r24.drawString(this.click.getName(), ((float) ((this.module.getCategory().getX() + 4) * 2)), ((float) ((this.module.getCategory().getY() + this.off + 3) * 2)), ColorUtils.getFontColor(1).darker().darker());
        GL11.glPopMatrix();
    }

    @Override
    public void update(int x, int y) {
        this.y = this.module.getCategory().getY() + this.off;
        this.x = this.module.getCategory().getX();

        if (this.isHovering(x, y) && this.module.isExpanded() && this.click.getDescription() != "-") {
            if (!Gui.toolTips.isEnabled())
                return;

            Color c = ClientUtils.isSmokTheme() ? ColorUtils.getClientColor(90000) : Color.white;
            RoundedUtils.drawRoundOutline((float) (x + 5), (float) (y + 13), (float) ((int) FontUtils.r16.getStringWidth(this.click.getDescription())), (float) ((int) (FontUtils.r16.getHeight() * 1.2F - 2.0)), 1.0F, 2.2F, ColorUtils.getBackgroundColor(4), ColorUtils.getBackgroundColor(4).brighter());
            FontUtils.r16.drawString(this.click.getDescription(), (x + 5), (y + 13), c);
        }
    }

    @Override
    public void mouseDown(int x, int y, int smok) {
        if (ClientUtils.inClickGui()) {
            if (this.isHovering(x, y) && smok == 0 && this.module.isExpanded()) {
                if (Gui.clientSounds.isEnabled()) {
                    SoundUtils.playEnableSound();
                }

                this.click.toggle();
            }
        }
    }

    public boolean isHovering(int x, int y) {
        return x > this.x && x < this.x + this.module.getCategory().getWidth() && y > this.y && y < this.y + FastEditUtils.settingGap;
    }

}