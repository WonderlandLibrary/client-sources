package me.sleepyfish.smok.gui.comp.impl;

import java.awt.Color;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import me.sleepyfish.smok.gui.comp.IComp;
import me.sleepyfish.smok.rats.impl.visual.Gui;
import me.sleepyfish.smok.rats.settings.DoubleSetting;
import me.sleepyfish.smok.utils.misc.ClientUtils;
import me.sleepyfish.smok.utils.misc.FastEditUtils;
import me.sleepyfish.smok.utils.font.FontUtils;
import me.sleepyfish.smok.utils.render.RoundedUtils;
import me.sleepyfish.smok.utils.render.color.ColorUtils;
import org.lwjgl.opengl.GL11;

// Class from SMok Client by SleepyFish
public class SliderComp implements IComp {

    private final ModuleComp module;
    private final DoubleSetting slider;
    public boolean sliding = false;
    private int y;
    private int x;
    private int offset;
    private double value;
    int guiSize;

    public SliderComp(DoubleSetting slider, ModuleComp modComp, int o) {
        this.slider = slider;
        this.module = modComp;
        this.x = modComp.getCategory().getX() + modComp.getCategory().getWidth();
        this.y = modComp.getCategory().getY() + modComp.getOff();
        this.offset = o;
    }

    @Override
    public void setComponentStartAt(int n) {
        this.offset = n;
    }

    @Override
    public int getHeight() {
        return 0;
    }

    @Override
    public void mouseReleased(int x, int y, int m) {
        this.sliding = false;
    }

    @Override
    public void keyTyped(char chara, int key) {
        if (key == 1) {
            this.sliding = false;
        }
    }

    @Override
    public int getY() {
        return this.y;
    }

    @Override
    public void draw() {
        int l = this.module.getCategory().getX() + 4;
        RoundedUtils.drawRound(l, (this.module.getCategory().getY() + this.offset + 11), (this.module.getCategory().getWidth() - 8), 2.0F, 1.0F, ColorUtils.getBackgroundColor(4).brighter());
        RoundedUtils.drawRound((float) l, (float) (this.module.getCategory().getY() + this.offset + 11), (float) ((int) this.value), 2.0F, 1.0F, ColorUtils.getClientColor(6969));

        GL11.glPushMatrix();
        GL11.glScaled(0.5, 0.5, 0.5);
        FontUtils.r24.drawStringWithShadow(this.slider.getName(), ((float) ((int) ((float) l * 2.0F))), (double) ((float) ((int) ((float) (this.module.getCategory().getY() + this.offset + 3) * 2.0F))), ColorUtils.getFontColor(1).darker().darker());
        String val = new DecimalFormat("#.##").format(this.slider.getValue());
        double valr = FontUtils.r24.getStringWidth(val) / 2.0;
        FontUtils.r24.drawStringWithShadow(val, ((int) ((float) ((double) (this.module.getCategory().getX() + 78) - valr) * 2.0F)), ((int) ((float) (this.module.getCategory().getY() + this.offset + 3) * 2.0F)), ColorUtils.getFontColor(1).darker());
        GL11.glPopMatrix();
    }

    @Override
    public void update(int x, int y) {
        if (!this.overSliderLeft(x, y) && !this.overSliderRight(x, y)) {
            this.guiSize = 4;
        } else {
            this.guiSize = 5;
        }

        this.y = this.module.getCategory().getY() + this.offset;
        this.x = this.module.getCategory().getX();
        double d = Math.min(this.module.getCategory().getWidth() - 8, Math.max(0, x - this.x));
        this.value = (double) (this.module.getCategory().getWidth() - 8) * (this.slider.getValue() - this.slider.getMin()) / (this.slider.getMax() - this.slider.getMin());
        if (this.sliding) {
            if (d == 0.0) {
                this.slider.setValue(this.slider.getMin());
            } else {
                this.slider.setValue(round(d / (double) (this.module.getCategory().getWidth() - 8) * (this.slider.getMax() - this.slider.getMin()) + this.slider.getMin()));
            }
        }

        if ((this.overSliderLeft(x, y) || this.overSliderRight(x, y)) && this.module.isExpanded() && this.slider.getDescription() != "-") {
            if (!Gui.toolTips.isEnabled())
                return;

            Color c = ClientUtils.isSmokTheme() ? ColorUtils.getClientColor(90000) : Color.white;
            RoundedUtils.drawRoundOutline((float) (x + 5), (float) (y + 13), (float) ((int) FontUtils.r16.getStringWidth(this.slider.getDescription())), (float) ((int) (FontUtils.r16.getHeight() * 1.2F - 2.0)), 1.0F, 2.2F, ColorUtils.getBackgroundColor(4), ColorUtils.getBackgroundColor(4).brighter());
            FontUtils.r16.drawString(this.slider.getDescription(), (x + 5), (y + 13), c);
        }
    }

    private static double round(double v) {
        BigDecimal bd = new BigDecimal(v);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    @Override
    public void mouseDown(int x, int y, int b) {
        if (this.overSliderRight(x, y) && b == 0 && this.module.isExpanded()) {
            this.sliding = true;
        }

        if (this.overSliderLeft(x, y) && b == 0 && this.module.isExpanded()) {
            this.sliding = true;
        }
    }

    public boolean overSliderRight(int x, int y) {
        return x > this.x && x < this.x + this.module.getCategory().getWidth() / 2 + 1 && y > this.y && y < this.y + FastEditUtils.ratGap;
    }

    public boolean overSliderLeft(int x, int y) {
        return x > this.x + this.module.getCategory().getWidth() / 2 && x < this.x + this.module.getCategory().getWidth() && y > this.y && y < this.y + FastEditUtils.ratGap;
    }

}