package net.minecraft.client.gui;

import net.minecraft.client.settings.*;
import net.minecraft.client.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.*;

public class GuiOptionSlider extends GuiButton
{
    private static final String[] I;
    private GameSettings.Options options;
    private float sliderValue;
    private final float field_146131_s;
    public boolean dragging;
    private final float field_146132_r;
    
    @Override
    public void mouseReleased(final int n, final int n2) {
        this.dragging = ("".length() != 0);
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
            if (2 != 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static {
        I();
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("", "EVqPB");
    }
    
    @Override
    protected void mouseDragged(final Minecraft minecraft, final int n, final int n2) {
        if (this.visible) {
            if (this.dragging) {
                this.sliderValue = (n - (this.xPosition + (0x4C ^ 0x48))) / (this.width - (0x17 ^ 0x1F));
                this.sliderValue = MathHelper.clamp_float(this.sliderValue, 0.0f, 1.0f);
                final float denormalizeValue = this.options.denormalizeValue(this.sliderValue);
                minecraft.gameSettings.setOptionFloatValue(this.options, denormalizeValue);
                this.sliderValue = this.options.normalizeValue(denormalizeValue);
                this.displayString = minecraft.gameSettings.getKeyBinding(this.options);
            }
            minecraft.getTextureManager().bindTexture(GuiOptionSlider.buttonTextures);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            this.drawTexturedModalRect(this.xPosition + (int)(this.sliderValue * (this.width - (0x86 ^ 0x8E))), this.yPosition, "".length(), 0xC5 ^ 0x87, 0xC6 ^ 0xC2, 0xE ^ 0x1A);
            this.drawTexturedModalRect(this.xPosition + (int)(this.sliderValue * (this.width - (0x53 ^ 0x5B))) + (0x48 ^ 0x4C), this.yPosition, 106 + 191 - 213 + 112, 0xC6 ^ 0x84, 0x97 ^ 0x93, 0xB8 ^ 0xAC);
        }
    }
    
    public GuiOptionSlider(final int n, final int n2, final int n3, final GameSettings.Options options) {
        this(n, n2, n3, options, 0.0f, 1.0f);
    }
    
    public GuiOptionSlider(final int n, final int n2, final int n3, final GameSettings.Options options, final float field_146132_r, final float field_146131_s) {
        super(n, n2, n3, 1 + 38 + 100 + 11, 0xB ^ 0x1F, GuiOptionSlider.I["".length()]);
        this.sliderValue = 1.0f;
        this.options = options;
        this.field_146132_r = field_146132_r;
        this.field_146131_s = field_146131_s;
        final Minecraft minecraft = Minecraft.getMinecraft();
        this.sliderValue = options.normalizeValue(minecraft.gameSettings.getOptionFloatValue(options));
        this.displayString = minecraft.gameSettings.getKeyBinding(options);
    }
    
    @Override
    protected int getHoverState(final boolean b) {
        return "".length();
    }
    
    @Override
    public boolean mousePressed(final Minecraft minecraft, final int n, final int n2) {
        if (super.mousePressed(minecraft, n, n2)) {
            this.sliderValue = (n - (this.xPosition + (0x20 ^ 0x24))) / (this.width - (0x13 ^ 0x1B));
            this.sliderValue = MathHelper.clamp_float(this.sliderValue, 0.0f, 1.0f);
            minecraft.gameSettings.setOptionFloatValue(this.options, this.options.denormalizeValue(this.sliderValue));
            this.displayString = minecraft.gameSettings.getKeyBinding(this.options);
            this.dragging = (" ".length() != 0);
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
}
