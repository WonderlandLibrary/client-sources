package net.minecraft.client.gui;

import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.resources.*;

public class GuiSlider extends GuiButton
{
    private FormatHelper formatHelper;
    private String name;
    private final GuiPageButtonList.GuiResponder responder;
    private static final String[] I;
    public boolean isMouseDown;
    private final float min;
    private final float max;
    private float sliderPosition;
    
    public float func_175217_d() {
        return this.sliderPosition;
    }
    
    public void func_175219_a(final float sliderPosition) {
        this.sliderPosition = sliderPosition;
        this.displayString = this.getDisplayString();
        this.responder.onTick(this.id, this.func_175220_c());
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (3 <= -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void mouseReleased(final int n, final int n2) {
        this.isMouseDown = ("".length() != 0);
    }
    
    @Override
    protected void mouseDragged(final Minecraft minecraft, final int n, final int n2) {
        if (this.visible) {
            if (this.isMouseDown) {
                this.sliderPosition = (n - (this.xPosition + (0x62 ^ 0x66))) / (this.width - (0x6A ^ 0x62));
                if (this.sliderPosition < 0.0f) {
                    this.sliderPosition = 0.0f;
                }
                if (this.sliderPosition > 1.0f) {
                    this.sliderPosition = 1.0f;
                }
                this.displayString = this.getDisplayString();
                this.responder.onTick(this.id, this.func_175220_c());
            }
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            this.drawTexturedModalRect(this.xPosition + (int)(this.sliderPosition * (this.width - (0x7B ^ 0x73))), this.yPosition, "".length(), 0x16 ^ 0x54, 0xC4 ^ 0xC0, 0x3A ^ 0x2E);
            this.drawTexturedModalRect(this.xPosition + (int)(this.sliderPosition * (this.width - (0xA4 ^ 0xAC))) + (0x39 ^ 0x3D), this.yPosition, 59 + 164 - 47 + 20, 0xE ^ 0x4C, 0x7F ^ 0x7B, 0xA3 ^ 0xB7);
        }
    }
    
    @Override
    public boolean mousePressed(final Minecraft minecraft, final int n, final int n2) {
        if (super.mousePressed(minecraft, n, n2)) {
            this.sliderPosition = (n - (this.xPosition + (0x92 ^ 0x96))) / (this.width - (0x41 ^ 0x49));
            if (this.sliderPosition < 0.0f) {
                this.sliderPosition = 0.0f;
            }
            if (this.sliderPosition > 1.0f) {
                this.sliderPosition = 1.0f;
            }
            this.displayString = this.getDisplayString();
            this.responder.onTick(this.id, this.func_175220_c());
            this.isMouseDown = (" ".length() != 0);
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public void func_175218_a(final float n, final boolean b) {
        this.sliderPosition = (n - this.min) / (this.max - this.min);
        this.displayString = this.getDisplayString();
        if (b) {
            this.responder.onTick(this.id, this.func_175220_c());
        }
    }
    
    public float func_175220_c() {
        return this.min + (this.max - this.min) * this.sliderPosition;
    }
    
    static {
        I();
    }
    
    private static void I() {
        (I = new String["  ".length()])["".length()] = I("", "qTJdA");
        GuiSlider.I[" ".length()] = I("oe", "UEZfT");
    }
    
    private String getDisplayString() {
        String s;
        if (this.formatHelper == null) {
            s = String.valueOf(I18n.format(this.name, new Object["".length()])) + GuiSlider.I[" ".length()] + this.func_175220_c();
            "".length();
            if (4 <= 2) {
                throw null;
            }
        }
        else {
            s = this.formatHelper.getText(this.id, I18n.format(this.name, new Object["".length()]), this.func_175220_c());
        }
        return s;
    }
    
    public GuiSlider(final GuiPageButtonList.GuiResponder responder, final int n, final int n2, final int n3, final String name, final float min, final float max, final float n4, final FormatHelper formatHelper) {
        super(n, n2, n3, 91 + 36 - 56 + 79, 0x22 ^ 0x36, GuiSlider.I["".length()]);
        this.sliderPosition = 1.0f;
        this.name = name;
        this.min = min;
        this.max = max;
        this.sliderPosition = (n4 - min) / (max - min);
        this.formatHelper = formatHelper;
        this.responder = responder;
        this.displayString = this.getDisplayString();
    }
    
    @Override
    protected int getHoverState(final boolean b) {
        return "".length();
    }
    
    public interface FormatHelper
    {
        String getText(final int p0, final String p1, final float p2);
    }
}
