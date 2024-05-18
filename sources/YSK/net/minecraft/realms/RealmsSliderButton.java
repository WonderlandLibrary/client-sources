package net.minecraft.realms;

import net.minecraft.util.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;

public class RealmsSliderButton extends RealmsButton
{
    private static final String[] I;
    public boolean sliding;
    private final float minValue;
    public float value;
    private int steps;
    private final float maxValue;
    
    public RealmsSliderButton(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        this(n, n2, n3, n4, n6, "".length(), 1.0f, n5);
    }
    
    public void clicked(final float n) {
    }
    
    @Override
    public int getYImage(final boolean b) {
        return "".length();
    }
    
    protected float clampSteps(float n) {
        if (this.steps > 0) {
            n = this.steps * Math.round(n / this.steps);
        }
        return n;
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
            if (-1 == 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void released(final int n, final int n2) {
        this.sliding = ("".length() != 0);
    }
    
    public String getMessage() {
        return RealmsSliderButton.I[" ".length()];
    }
    
    private static void I() {
        (I = new String["  ".length()])["".length()] = I("", "XCRKW");
        RealmsSliderButton.I[" ".length()] = I("", "PgeHV");
    }
    
    public float clamp(float clampSteps) {
        clampSteps = this.clampSteps(clampSteps);
        return MathHelper.clamp_float(clampSteps, this.minValue, this.maxValue);
    }
    
    public RealmsSliderButton(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final float minValue, final float maxValue) {
        super(n, n2, n3, n4, 0x12 ^ 0x6, RealmsSliderButton.I["".length()]);
        this.value = 1.0f;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.value = this.toPct(n6);
        this.getProxy().displayString = this.getMessage();
    }
    
    public float toValue(final float n) {
        return this.clamp(this.minValue + (this.maxValue - this.minValue) * MathHelper.clamp_float(n, 0.0f, 1.0f));
    }
    
    public float toPct(final float n) {
        return MathHelper.clamp_float((this.clamp(n) - this.minValue) / (this.maxValue - this.minValue), 0.0f, 1.0f);
    }
    
    static {
        I();
    }
    
    @Override
    public void renderBg(final int n, final int n2) {
        if (this.getProxy().visible) {
            if (this.sliding) {
                this.value = (n - (this.getProxy().xPosition + (0x92 ^ 0x96))) / (this.getProxy().getButtonWidth() - (0x2F ^ 0x27));
                this.value = MathHelper.clamp_float(this.value, 0.0f, 1.0f);
                final float value = this.toValue(this.value);
                this.clicked(value);
                this.value = this.toPct(value);
                this.getProxy().displayString = this.getMessage();
            }
            Minecraft.getMinecraft().getTextureManager().bindTexture(RealmsSliderButton.WIDGETS_LOCATION);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            this.blit(this.getProxy().xPosition + (int)(this.value * (this.getProxy().getButtonWidth() - (0x2A ^ 0x22))), this.getProxy().yPosition, "".length(), 0x13 ^ 0x51, 0x1C ^ 0x18, 0x69 ^ 0x7D);
            this.blit(this.getProxy().xPosition + (int)(this.value * (this.getProxy().getButtonWidth() - (0x53 ^ 0x5B))) + (0x14 ^ 0x10), this.getProxy().yPosition, 107 + 182 - 97 + 4, 0x5 ^ 0x47, 0xA2 ^ 0xA6, 0x3C ^ 0x28);
        }
    }
    
    @Override
    public void clicked(final int n, final int n2) {
        this.value = (n - (this.getProxy().xPosition + (0x36 ^ 0x32))) / (this.getProxy().getButtonWidth() - (0x74 ^ 0x7C));
        this.value = MathHelper.clamp_float(this.value, 0.0f, 1.0f);
        this.clicked(this.toValue(this.value));
        this.getProxy().displayString = this.getMessage();
        this.sliding = (" ".length() != 0);
    }
}
