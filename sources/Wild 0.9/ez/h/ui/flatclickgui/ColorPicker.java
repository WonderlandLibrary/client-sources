package ez.h.ui.flatclickgui;

import ez.h.ui.clickgui.options.*;
import java.awt.*;
import org.lwjgl.*;
import org.lwjgl.input.*;
import org.lwjgl.opengl.*;
import java.nio.*;
import ez.h.ui.fonts.*;
import ez.h.utils.*;

public class ColorPicker
{
    public static OptionColor currentOption;
    int y;
    int currentValue;
    float translateAnimation;
    String hex;
    int color;
    boolean typing;
    public static boolean isOpen;
    public static int fadeCounter;
    int width;
    int x;
    public static bje alphaField;
    Color currentColor;
    final Counter timeHelper;
    int height;
    
    public ColorPicker() {
        this.timeHelper = new Counter();
        this.color = Color.red.getRGB();
    }
    
    private Color getHoverColor() {
        final ByteBuffer byteBuffer = BufferUtils.createByteBuffer(0xD2 ^ 0xB6);
        GL11.glReadPixels(Mouse.getX(), Mouse.getY(), 1, 1, 6280 + 3832 - 8926 + 5221, 1534 + 4617 - 5425 + 4395, byteBuffer);
        return new Color(byteBuffer.get(0) & 98 + 142 - 40 + 55, byteBuffer.get(1) & 162 + 151 - 227 + 169, byteBuffer.get(2) & 174 + 88 - 77 + 70);
    }
    
    public void handleInput(final char c, final int n) {
        try {
            if (this.typing) {
                final int digit = Character.digit(c, 0x1 ^ 0x11);
                if (this.hex.length() > 0 && this.hex.charAt(0) == (0x4E ^ 0x63)) {}
                switch (n) {
                    case 14: {
                        if (this.hex.length() <= 0) {
                            break;
                        }
                        this.hex = this.hex.substring(0, this.hex.length() - 1);
                        if (this.hex.replace("-", "").length() > 0) {
                            this.currentValue = Integer.parseInt(this.hex, 0x6E ^ 0x7E);
                            break;
                        }
                        break;
                    }
                    case 28:
                    case 207: {
                        this.typing = false;
                        if (this.hex.replace("-", "").length() > 0) {
                            this.currentValue = Integer.parseInt(this.hex, 0x8C ^ 0x9C);
                            break;
                        }
                        break;
                    }
                    default: {
                        if (c != (0x4D ^ 0x60) && digit < 0) {
                            break;
                        }
                        this.hex += c;
                        if (this.hex.replace("-", "").length() > 0) {
                            this.currentValue = Integer.parseInt(this.hex, 0x30 ^ 0x20);
                            break;
                        }
                        break;
                    }
                }
            }
        }
        catch (NumberFormatException ex) {
            if (this.hex.length() > 0) {
                this.hex = this.hex.substring(0, this.hex.length() - 1);
            }
        }
    }
    
    public boolean isHover(final int n, final int n2) {
        return n > this.x + (0x25 ^ 0x2F) && n < this.x + this.width - (0xCD ^ 0xC7) && n2 > this.y + (0x42 ^ 0x48) && n2 < this.y + this.height - (0x1 ^ 0xB);
    }
    
    public void draw(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final Color color) {
        this.draw(n, n2, n3, n4, n5, n6, color, true);
    }
    
    public void handleClick(final int n, final int n2, final int n3) {
        if (n3 == 0) {
            final int n4 = this.x + this.width + (0x93 ^ 0x88) + CFontManager.manropesmall.getStringWidth("#");
            final int n5 = this.y + (0xD0 ^ 0xC5) - CFontManager.manropesmall.getStringHeight("#") / 2;
            this.typing = (n >= n4 && n <= n4 + CFontManager.manropesmall.getStringWidth(this.hex) && n2 >= n5 && n2 <= n5 + CFontManager.manropesmall.getStringHeight("#" + this.hex));
        }
    }
    
    private Color getColor(final int n, final int n2) {
        final bit bit = new bit(bib.z());
        final int a = bit.a();
        final int b = bit.b();
        final int n3 = n * bib.z().d / a;
        final int n4 = (n2 * bib.z().e / b - bib.z().e) * -1;
        final ByteBuffer byteBuffer = BufferUtils.createByteBuffer(0x59 ^ 0x3D);
        GL11.glReadPixels(n3, n4, 1, 1, 1950 + 6258 - 2825 + 1024, 1113 + 310 + 1537 + 2161, byteBuffer);
        return new Color(byteBuffer.get(0) & 196 + 68 - 53 + 44, byteBuffer.get(1) & 24 + 33 + 47 + 151, byteBuffer.get(2) & 62 + 123 + 42 + 28);
    }
    
    public void draw(final int x, final int y, final int width, final int height, final int n, final int n2, final Color currentColor, final boolean b) {
        if (ColorPicker.alphaField == null) {
            (ColorPicker.alphaField = new bje(1, bib.z().k, 0, 0, 0x39 ^ 0x69, 0x95 ^ 0x8C)).a("255");
        }
        GL11.glPushMatrix();
        GL11.glEnable(1592 + 1120 - 2461 + 2838);
        RenderUtils.setupScissor(FlatGuiScreen.x + FlatGuiScreen.width, FlatGuiScreen.y, FlatGuiScreen.x + FlatGuiScreen.width + width, FlatGuiScreen.y + FlatGuiScreen.height);
        this.currentColor = currentColor;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        final float n3 = (this.color >> (0xD7 ^ 0xC7) & 26 + 37 + 185 + 7) / 255.0f;
        final float n4 = (this.color >> 8 & 115 + 87 - 175 + 228) / 255.0f;
        final float n5 = (this.color & 230 + 136 - 114 + 3) / 255.0f;
        if (ColorPicker.fadeCounter != 0 && !FlatElement.isHover((float)n, (float)n2, (float)x, (float)y, (float)(width + (0xE4 ^ 0xA2)), (float)(height + (0xBF ^ 0xB5)))) {
            --ColorPicker.fadeCounter;
        }
        GL11.glTranslatef(this.translateAnimation = MathUtils.lerp(this.translateAnimation, (ColorPicker.fadeCounter > 0) ? 0.0f : ((float)(-width - (0x46 ^ 0x16))), 0.1f), 0.0f, 0.0f);
        RenderUtils.drawBlurredShadow((float)(x - 2), (float)(y - 2), (float)(width + (0xCF ^ 0x8E) + 4), (float)(height + 4), 0x52 ^ 0x58, new Color(1602049523 + 924420064 - 1071696252 + 273279913, true));
        RenderUtils.drawRectWH((float)x, (float)y, (float)(width + (0x66 ^ 0x27)), (float)height, new Color(-1743711983, true).getRGB());
        for (int i = 5; i < height - 5; ++i) {
            RenderUtils.drawRect(x + width, y + 1.0 * i, x + width + (0x79 ^ 0x73), y + 1.0 * (i + 1), Color.HSBtoRGB(i / (float)height, 1.0f, 1.0f));
            if (b && Mouse.isButtonDown(0) && n >= x + width && n <= x + width + (0xBB ^ 0xB1) && n2 >= y + 1.0 * i - 10.0 && n2 <= y + 1.0 * (i + 1) + 10.0) {
                this.color = Color.HSBtoRGB((i - 5) / (float)(height + 5), 1.0f, 1.0f);
            }
        }
        for (int j = 5; j < height - 5; ++j) {
            if (this.color == Color.HSBtoRGB((j - 5) / (float)(height + 5), 1.0f, 1.0f)) {
                RenderUtils.drawRect(x + width, y + 1.0 * j + 0.5, x + width + (0x8B ^ 0x81), y + 1.0 * (j + 1) + 1.0, Color.white.getRGB());
                RenderUtils.drawRect(x + width, y + 1.0 * j - 1.0, x + width + (0x76 ^ 0x7C), y + 1.0 * (j + 1) - 0.5, Color.white.getRGB());
            }
        }
        GL11.glEnable(311 + 620 - 845 + 2956);
        GL11.glShadeModel(3759 + 5357 - 4024 + 2333);
        GL11.glBlendFunc(309 + 33 - 231 + 659, 211 + 439 - 427 + 548);
        GL11.glDisable(1145 + 1327 - 888 + 1969);
        GL11.glBegin(7);
        RenderUtils.color(new Color(n3, n4, n5).getRGB());
        GL11.glVertex2d((double)(x + width - (0x54 ^ 0x5E)), (double)(y + (0x14 ^ 0x1E)));
        RenderUtils.color(Color.white.getRGB());
        GL11.glVertex2d((double)((0x23 ^ 0x29) + x), (double)(y + (0x85 ^ 0x8F)));
        RenderUtils.color(Color.black.getRGB());
        GL11.glVertex2d((double)((0xF ^ 0x5) + x), (double)(y + height - (0x40 ^ 0x4A)));
        GL11.glVertex2d((double)(x + width - (0xB4 ^ 0xBE)), (double)(y + height - (0x55 ^ 0x5F)));
        GL11.glEnd();
        GL11.glEnable(427 + 636 + 241 + 2249);
        if (b && Mouse.isButtonDown(0) && this.isHover(n, n2)) {
            final int rgb = this.getHoverColor().getRGB();
            this.currentValue = rgb;
            this.hex = Integer.toHexString(rgb).substring(2);
            RenderUtils.drawCircleOut(n, n2, 3.0, 0.0, 360.0f, 1.0f, -1);
            ColorPicker.currentOption.setColor(this.getColor(n, n2));
            ColorPicker.fadeCounter = bib.af() * 5;
        }
        if (!ColorPicker.alphaField.m()) {
            ColorPicker.alphaField.a(ColorPicker.currentOption.alpha + "");
        }
        else {
            ColorPicker.currentOption.alpha = rk.a(Integer.parseInt(ColorPicker.alphaField.b().isEmpty() ? "255" : ColorPicker.alphaField.b()), 0, 161 + 149 - 306 + 251);
        }
        ColorPicker.alphaField.a = x + width + (0xBD ^ 0x9D);
        ColorPicker.alphaField.f = y + height / 2 - (0xB9 ^ 0xA7);
        ColorPicker.alphaField.a(false);
        ColorPicker.alphaField.i = (0x27 ^ 0x3B);
        ColorPicker.alphaField.j = (0x42 ^ 0x4F);
        ColorPicker.alphaField.f(3);
        ColorPicker.alphaField.g();
        RenderUtils.drawRectWH((float)(x + width + (0x88 ^ 0x91)), (float)(y + height / 2), 32.0f, 6.0f, ColorPicker.currentOption.getColor().getRGB());
        RenderUtils.drawBlurredShadow((float)(x + width + (0x4E ^ 0x57) - 2), (float)(y + height / 2 - 2), 36.0f, 10.0f, 0x72 ^ 0x78, ColorPicker.currentOption.getColor());
        CFontManager.manropesmall.drawScaledString("#" + Integer.toHexString(ColorPicker.currentOption.getColor().getRGB()).toUpperCase(), (float)(x + width + (0x94 ^ 0x8D) - 2), (float)(y + height / 2 + (0x2 ^ 0x8)), -1, 0.85f, false);
        GL11.glDisable(355 + 236 - 103 + 2601);
        GL11.glPopMatrix();
    }
    
    public enum Type
    {
        QUAD, 
        SHADER;
    }
}
