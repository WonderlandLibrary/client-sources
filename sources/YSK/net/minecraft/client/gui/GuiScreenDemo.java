package net.minecraft.client.gui;

import net.minecraft.util.*;
import net.minecraft.client.renderer.*;
import org.apache.logging.log4j.*;
import net.minecraft.client.resources.*;
import net.minecraft.client.settings.*;
import java.net.*;
import java.lang.reflect.*;
import java.io.*;

public class GuiScreenDemo extends GuiScreen
{
    private static final String[] I;
    private static final Logger logger;
    private static final ResourceLocation field_146348_f;
    
    @Override
    public void drawDefaultBackground() {
        super.drawDefaultBackground();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(GuiScreenDemo.field_146348_f);
        this.drawTexturedModalRect((this.width - (191 + 188 - 235 + 104)) / "  ".length(), (this.height - (129 + 92 - 103 + 48)) / "  ".length(), "".length(), "".length(), 240 + 231 - 293 + 70, 71 + 148 - 58 + 5);
    }
    
    @Override
    public void updateScreen() {
        super.updateScreen();
    }
    
    static {
        I();
        logger = LogManager.getLogger();
        field_146348_f = new ResourceLocation(GuiScreenDemo.I["".length()]);
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
            if (4 == 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void initGui() {
        this.buttonList.clear();
        final int n = -(0x6C ^ 0x7C);
        this.buttonList.add(new GuiButton(" ".length(), this.width / "  ".length() - (0xEA ^ 0x9E), this.height / "  ".length() + (0x5 ^ 0x3B) + n, 0xB1 ^ 0xC3, 0x71 ^ 0x65, I18n.format(GuiScreenDemo.I[" ".length()], new Object["".length()])));
        this.buttonList.add(new GuiButton("  ".length(), this.width / "  ".length() + "  ".length(), this.height / "  ".length() + (0x79 ^ 0x47) + n, 0x7F ^ 0xD, 0x80 ^ 0x94, I18n.format(GuiScreenDemo.I["  ".length()], new Object["".length()])));
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        final int n4 = (this.width - (163 + 144 - 300 + 241)) / "  ".length() + (0x83 ^ 0x89);
        int n5 = (this.height - (77 + 157 - 126 + 58)) / "  ".length() + (0xA5 ^ 0xAD);
        this.fontRendererObj.drawString(I18n.format(GuiScreenDemo.I[0xAD ^ 0xA5], new Object["".length()]), n4, n5, 106898 + 679635 - 511991 + 1765041);
        n5 += 12;
        final GameSettings gameSettings = this.mc.gameSettings;
        final FontRenderer fontRendererObj = this.fontRendererObj;
        final String s = GuiScreenDemo.I[0x9C ^ 0x95];
        final Object[] array = new Object[0x4F ^ 0x4B];
        array["".length()] = GameSettings.getKeyDisplayString(gameSettings.keyBindForward.getKeyCode());
        array[" ".length()] = GameSettings.getKeyDisplayString(gameSettings.keyBindLeft.getKeyCode());
        array["  ".length()] = GameSettings.getKeyDisplayString(gameSettings.keyBindBack.getKeyCode());
        array["   ".length()] = GameSettings.getKeyDisplayString(gameSettings.keyBindRight.getKeyCode());
        fontRendererObj.drawString(I18n.format(s, array), n4, n5, 261889 + 2962028 + 583038 + 1390692);
        this.fontRendererObj.drawString(I18n.format(GuiScreenDemo.I[0xB8 ^ 0xB2], new Object["".length()]), n4, n5 + (0x13 ^ 0x1F), 889073 + 1483425 - 1820963 + 4646112);
        final FontRenderer fontRendererObj2 = this.fontRendererObj;
        final String s2 = GuiScreenDemo.I[0x1E ^ 0x15];
        final Object[] array2 = new Object[" ".length()];
        array2["".length()] = GameSettings.getKeyDisplayString(gameSettings.keyBindJump.getKeyCode());
        fontRendererObj2.drawString(I18n.format(s2, array2), n4, n5 + (0xB9 ^ 0xA1), 1349551 + 2470922 - 3424037 + 4801211);
        final FontRenderer fontRendererObj3 = this.fontRendererObj;
        final String s3 = GuiScreenDemo.I[0x6A ^ 0x66];
        final Object[] array3 = new Object[" ".length()];
        array3["".length()] = GameSettings.getKeyDisplayString(gameSettings.keyBindInventory.getKeyCode());
        fontRendererObj3.drawString(I18n.format(s3, array3), n4, n5 + (0xAC ^ 0x88), 2259413 + 4289262 - 4777219 + 3426191);
        this.fontRendererObj.drawSplitString(I18n.format(GuiScreenDemo.I[0x52 ^ 0x5F], new Object["".length()]), n4, n5 + (0x65 ^ 0x21), 140 + 142 - 145 + 81, 1712043 + 1196932 - 1455777 + 586385);
        super.drawScreen(n, n2, n3);
    }
    
    private static void I() {
        (I = new String[0x60 ^ 0x6E])["".length()] = I("\u001a\u000b0\u001a\u001c\u001c\u000b;A\u000e\u001b\u0007g\n\f\u0003\u0001\u0017\f\b\r\u0005/\u001c\u0006\u001b\u0000,@\u0019\u0000\t", "nnHni");
        GuiScreenDemo.I[" ".length()] = I("\f<\u0006-O\u0000<\u00072O\n,\u0012", "hYkBa");
        GuiScreenDemo.I["  ".length()] = I("\r\u0007 \u0007i\u0001\u0007!\u0018i\u0005\u00039\r5", "ibMhG");
        GuiScreenDemo.I["   ".length()] = I(">\u001b3)@5\r1f*1\t.<\u0001$", "TzEHn");
        GuiScreenDemo.I[0x63 ^ 0x67] = I("/\t7\t\";\u00077\"7", "HlCMG");
        GuiScreenDemo.I[0x9C ^ 0x99] = I("3\u0001\u001c5\u00164", "QssBe");
        GuiScreenDemo.I[0x51 ^ 0x57] = I(";\u001d&\nk|F%\r&}\u0004;\u001440\u001b3\u001c%}\u00077\u000e~ \u001d=\b4l\u001a=\u000f#0\fo\u001e4>\u0006", "SiRzQ");
        GuiScreenDemo.I[0x57 ^ 0x50] = I("\b\u0000\u0010\u000b\u001d%H\u0011G\u0016;\n\u000bG\u0015\"\u0001\u000e", "Koegy");
        GuiScreenDemo.I[0x82 ^ 0x8A] = I("#\u00115${/\u00114;{3\u001d,'0", "GtXKU");
        GuiScreenDemo.I[0x5C ^ 0x55] = I("\u000f\u0004\u0005\u0004C\u0003\u0004\u0004\u001bC\u0006\u000e\u001e\u000e\u0000\u000e\u000f\u001c8\u0005\u0004\u0013\u001c", "kahkm");
        GuiScreenDemo.I[0x67 ^ 0x6D] = I("\t\t=?]\u0005\t< ]\u0000\u0003&5\u001e\b\u0002$\u001d\u001c\u0018\u001f5", "mlPPs");
        GuiScreenDemo.I[0xA ^ 0x1] = I("\b7+9}\u00047*&}\u0006'+&", "lRFVS");
        GuiScreenDemo.I[0x18 ^ 0x14] = I("\u0006\u001c\u000b7i\n\u001c\n(i\u000b\u0017\u0010=)\u0016\u0016\u0014!", "byfXG");
        GuiScreenDemo.I[0x42 ^ 0x4F] = I("1\u001f9#M=\u001f8<M3\u000f8 4'\u001b$<\u00061", "UzTLc");
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        switch (guiButton.id) {
            case 1: {
                guiButton.enabled = ("".length() != 0);
                try {
                    final Class<?> forName = Class.forName(GuiScreenDemo.I["   ".length()]);
                    final Object invoke = forName.getMethod(GuiScreenDemo.I[0xA3 ^ 0xA7], (Class<?>[])new Class["".length()]).invoke(null, new Object["".length()]);
                    final Class<?> clazz = forName;
                    final String s = GuiScreenDemo.I[0xC3 ^ 0xC6];
                    final Class[] array = new Class[" ".length()];
                    array["".length()] = URI.class;
                    final Method method = clazz.getMethod(s, (Class<?>[])array);
                    final Object o = invoke;
                    final Object[] array2 = new Object[" ".length()];
                    array2["".length()] = new URI(GuiScreenDemo.I[0x9A ^ 0x9C]);
                    method.invoke(o, array2);
                    "".length();
                    if (-1 >= 3) {
                        throw null;
                    }
                    break;
                }
                catch (Throwable t) {
                    GuiScreenDemo.logger.error(GuiScreenDemo.I[0xD ^ 0xA], t);
                    "".length();
                    if (-1 >= 2) {
                        throw null;
                    }
                    break;
                }
            }
            case 2: {
                this.mc.displayGuiScreen(null);
                this.mc.setIngameFocus();
                break;
            }
        }
    }
}
