package net.minecraft.client.gui;

import net.minecraft.client.settings.*;
import java.io.*;
import net.minecraft.client.resources.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;
import net.minecraft.client.audio.*;

public class GuiScreenOptionsSounds extends GuiScreen
{
    private final GameSettings game_settings_4;
    private static final String[] I;
    private String field_146508_h;
    private final GuiScreen field_146505_f;
    protected String field_146507_a;
    
    static GameSettings access$0(final GuiScreenOptionsSounds guiScreenOptionsSounds) {
        return guiScreenOptionsSounds.game_settings_4;
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, this.field_146507_a, this.width / "  ".length(), 0xB9 ^ 0xB6, 8021802 + 13193328 - 8607518 + 4169603);
        super.drawScreen(n, n2, n3);
    }
    
    private static void I() {
        (I = new String[0x20 ^ 0x25])["".length()] = I("\u0006\u001a0\u000e$'\u0019", "IjDgK");
        GuiScreenOptionsSounds.I[" ".length()] = I("\"\u001c-\u0019\u0001#\u001fw\u0003\u00018\u0002=\u0003@9\u0005-\u001c\u000b", "MlYpn");
        GuiScreenOptionsSounds.I["  ".length()] = I("\u001a\u001f:.8\u001b\u001c`(1\u0013", "uoNGW");
        GuiScreenOptionsSounds.I["   ".length()] = I("+'1},#<=", "LRXSH");
        GuiScreenOptionsSounds.I[0x98 ^ 0x9C] = I("o", "JkGmd");
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
            if (3 < -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (guiButton.enabled && guiButton.id == 114 + 107 - 56 + 35) {
            this.mc.gameSettings.saveOptions();
            this.mc.displayGuiScreen(this.field_146505_f);
        }
    }
    
    public GuiScreenOptionsSounds(final GuiScreen field_146505_f, final GameSettings game_settings_4) {
        this.field_146507_a = GuiScreenOptionsSounds.I["".length()];
        this.field_146505_f = field_146505_f;
        this.game_settings_4 = game_settings_4;
    }
    
    static {
        I();
    }
    
    protected String getSoundVolume(final SoundCategory soundCategory) {
        final float soundLevel = this.game_settings_4.getSoundLevel(soundCategory);
        String s;
        if (soundLevel == 0.0f) {
            s = this.field_146508_h;
            "".length();
            if (2 == -1) {
                throw null;
            }
        }
        else {
            s = String.valueOf((int)(soundLevel * 100.0f)) + GuiScreenOptionsSounds.I[0x92 ^ 0x96];
        }
        return s;
    }
    
    @Override
    public void initGui() {
        int length = "".length();
        this.field_146507_a = I18n.format(GuiScreenOptionsSounds.I[" ".length()], new Object["".length()]);
        this.field_146508_h = I18n.format(GuiScreenOptionsSounds.I["  ".length()], new Object["".length()]);
        this.buttonList.add(new Button(SoundCategory.MASTER.getCategoryId(), this.width / "  ".length() - (48 + 63 + 43 + 1) + length % "  ".length() * (24 + 119 - 117 + 134), this.height / (0xC0 ^ 0xC6) - (0x1A ^ 0x16) + (0x6C ^ 0x74) * (length >> " ".length()), SoundCategory.MASTER, (boolean)(" ".length() != 0)));
        length += 2;
        final SoundCategory[] values;
        final int length2 = (values = SoundCategory.values()).length;
        int i = "".length();
        "".length();
        if (3 == 2) {
            throw null;
        }
        while (i < length2) {
            final SoundCategory soundCategory = values[i];
            if (soundCategory != SoundCategory.MASTER) {
                this.buttonList.add(new Button(soundCategory.getCategoryId(), this.width / "  ".length() - (89 + 116 - 190 + 140) + length % "  ".length() * (72 + 14 - 60 + 134), this.height / (0x31 ^ 0x37) - (0x32 ^ 0x3E) + (0xF ^ 0x17) * (length >> " ".length()), soundCategory, (boolean)("".length() != 0)));
                ++length;
            }
            ++i;
        }
        this.buttonList.add(new GuiButton(92 + 98 - 152 + 162, this.width / "  ".length() - (0xD ^ 0x69), this.height / (0x14 ^ 0x12) + (147 + 48 - 157 + 130), I18n.format(GuiScreenOptionsSounds.I["   ".length()], new Object["".length()])));
    }
    
    class Button extends GuiButton
    {
        private final String field_146152_s;
        private static final String[] I;
        public boolean field_146155_p;
        public float field_146156_o;
        private final SoundCategory field_146153_r;
        final GuiScreenOptionsSounds this$0;
        
        @Override
        protected void mouseDragged(final Minecraft minecraft, final int n, final int n2) {
            if (this.visible) {
                if (this.field_146155_p) {
                    this.field_146156_o = (n - (this.xPosition + (0xB9 ^ 0xBD))) / (this.width - (0x4C ^ 0x44));
                    this.field_146156_o = MathHelper.clamp_float(this.field_146156_o, 0.0f, 1.0f);
                    minecraft.gameSettings.setSoundLevel(this.field_146153_r, this.field_146156_o);
                    minecraft.gameSettings.saveOptions();
                    this.displayString = String.valueOf(this.field_146152_s) + Button.I["   ".length()] + this.this$0.getSoundVolume(this.field_146153_r);
                }
                GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                this.drawTexturedModalRect(this.xPosition + (int)(this.field_146156_o * (this.width - (0x2A ^ 0x22))), this.yPosition, "".length(), 0xF3 ^ 0xB1, 0x83 ^ 0x87, 0x21 ^ 0x35);
                this.drawTexturedModalRect(this.xPosition + (int)(this.field_146156_o * (this.width - (0x74 ^ 0x7C))) + (0xA2 ^ 0xA6), this.yPosition, 14 + 110 + 45 + 27, 0xEF ^ 0xAD, 0x30 ^ 0x34, 0xB9 ^ 0xAD);
            }
        }
        
        @Override
        public void mouseReleased(final int n, final int n2) {
            if (this.field_146155_p) {
                if (this.field_146153_r == SoundCategory.MASTER) {
                    "".length();
                    if (0 <= -1) {
                        throw null;
                    }
                }
                else {
                    GuiScreenOptionsSounds.access$0(this.this$0).getSoundLevel(this.field_146153_r);
                }
                this.this$0.mc.getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation(Button.I[0x7C ^ 0x79]), 1.0f));
            }
            this.field_146155_p = ("".length() != 0);
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
                if (0 >= 1) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        @Override
        public void playPressSound(final SoundHandler soundHandler) {
        }
        
        @Override
        public boolean mousePressed(final Minecraft minecraft, final int n, final int n2) {
            if (super.mousePressed(minecraft, n, n2)) {
                this.field_146156_o = (n - (this.xPosition + (0x6B ^ 0x6F))) / (this.width - (0x6B ^ 0x63));
                this.field_146156_o = MathHelper.clamp_float(this.field_146156_o, 0.0f, 1.0f);
                minecraft.gameSettings.setSoundLevel(this.field_146153_r, this.field_146156_o);
                minecraft.gameSettings.saveOptions();
                this.displayString = String.valueOf(this.field_146152_s) + Button.I[0x29 ^ 0x2D] + this.this$0.getSoundVolume(this.field_146153_r);
                this.field_146155_p = (" ".length() != 0);
                return " ".length() != 0;
            }
            return "".length() != 0;
        }
        
        private static void I() {
            (I = new String[0x1C ^ 0x1A])["".length()] = I("", "YvqZv");
            Button.I[" ".length()] = I(">9\u001b<)\u000e7\u001a7*\"$\u0017|", "MVnRM");
            Button.I["  ".length()] = I("Uh", "oHckm");
            Button.I["   ".length()] = I("qS", "Ksvmv");
            Button.I[0xB8 ^ 0xBC] = I("hf", "RFieQ");
            Button.I[0x76 ^ 0x73] = I("\u0000\u0005(^\b\u0012\u00045\u001f\u0004I\u00003\u0015\u0019\u0014", "gpApj");
        }
        
        @Override
        protected int getHoverState(final boolean b) {
            return "".length();
        }
        
        static {
            I();
        }
        
        public Button(final GuiScreenOptionsSounds this$0, final int n, final int n2, final int n3, final SoundCategory field_146153_r, final boolean b) {
            this.this$0 = this$0;
            int n4;
            if (b) {
                n4 = 17 + 88 + 200 + 5;
                "".length();
                if (2 < 0) {
                    throw null;
                }
            }
            else {
                n4 = 50 + 23 + 60 + 17;
            }
            super(n, n2, n3, n4, 0x74 ^ 0x60, Button.I["".length()]);
            this.field_146156_o = 1.0f;
            this.field_146153_r = field_146153_r;
            this.field_146152_s = I18n.format(Button.I[" ".length()] + field_146153_r.getCategoryName(), new Object["".length()]);
            this.displayString = String.valueOf(this.field_146152_s) + Button.I["  ".length()] + this$0.getSoundVolume(field_146153_r);
            this.field_146156_o = GuiScreenOptionsSounds.access$0(this$0).getSoundLevel(field_146153_r);
        }
    }
}
