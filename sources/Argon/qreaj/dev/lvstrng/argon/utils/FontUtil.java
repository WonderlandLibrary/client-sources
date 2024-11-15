// 
// Decompiled by the rizzer xd
// 

package dev.lvstrng.argon.utils;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;

import java.awt.*;
import java.io.IOException;
import java.util.Random;

@SuppressWarnings("all")
public final class FontUtil {
    private final int[] field620;
    private final Font field625;
    private final Font field626;
    private final Font field627;
    private final Font field628;
    public Random field617;
    private float field618;
    private float field619;
    private boolean field621;
    private boolean field622;
    private boolean field623;
    private boolean field624;

    public FontUtil(final Font regularGlyphPage, final Font boldGlyphPage, final Font italicGlyphPage, final Font boldItalicGlyphPage) {
        this.field617 = new Random();
        this.field620 = new int[32];
        this.field625 = regularGlyphPage;
        this.field626 = boldGlyphPage;
        this.field627 = italicGlyphPage;
        this.field628 = boldItalicGlyphPage;
        for (int i = 0; i < 32; ++i) {
            final int n = (i >> 3 & 0x1) * 85;
            int n2 = (i >> 2 & 0x1) * 170 + n;
            int n3 = (i >> 1 & 0x1) * 170 + n;
            int n4 = (i & 0x1) * 170 + n;
            if (i == 6) {
                n2 += 85;
            }
            if (i >= 16) {
                n2 /= 4;
                n3 /= 4;
                n4 /= 4;
            }
            this.field620[i] = ((n2 & 0xFF) << 16 | (n3 & 0xFF) << 8 | (n4 & 0xFF));
        }
    }

    public static FontUtil method563(final CharSequence fontName, final int size, final boolean bold, final boolean italic, final boolean boldItalic) throws IOException {
        final int n = 0;
        final char[] array = new char[256];
        final int n2 = n;
        int i = 0;
        while (i < array.length) {
            array[i] = (char) i;
            ++i;
            if (n2 != 0) {
                break;
            }
        }
        final Font regularGlyphPage = new Font(new java.awt.Font(fontName.toString(), 0, size), true, true);
        regularGlyphPage.method553(array);
        regularGlyphPage.method554();
        Font boldGlyphPage = regularGlyphPage;
        Font italicGlyphPage = regularGlyphPage;
        Font boldItalicGlyphPage = regularGlyphPage;
        boolean b = bold;
        boolean b2 = bold;
        if (n2 == 0) {
            if (bold) {
                boldGlyphPage = new Font(new java.awt.Font(fontName.toString(), 1, size), true, true);
                boldGlyphPage.method553(array);
                boldGlyphPage.method554();
            }
            b = italic;
            b2 = italic;
        }
        if (n2 == 0) {
            if (b2) {
                italicGlyphPage = new Font(new java.awt.Font(fontName.toString(), 2, size), true, true);
                italicGlyphPage.method553(array);
                italicGlyphPage.method554();
            }
            b = boldItalic;
        }
        if (b) {
            boldItalicGlyphPage = new Font(new java.awt.Font(fontName.toString(), 3, size), true, true);
            boldItalicGlyphPage.method553(array);
            boldItalicGlyphPage.method554();
        }
        return new FontUtil(regularGlyphPage, boldGlyphPage, italicGlyphPage, boldItalicGlyphPage);
    }

    public static FontUtil method564(final CharSequence id, final int size, final boolean bold, final boolean italic, final boolean boldItalic) throws IOException, FontFormatException {
        final char[] array = new char[256];
        for (int i = 0; i < array.length; ++i) {
            array[i] = (char) i;
        }
        final Font regularGlyphPage = new Font(java.awt.Font.createFont(0, FontUtil.class.getResourceAsStream(id.toString())).deriveFont(0, (float) size), true, true);
        regularGlyphPage.method553(array);
        regularGlyphPage.method554();
        Font boldGlyphPage = regularGlyphPage;
        Font italicGlyphPage = regularGlyphPage;
        Font boldItalicGlyphPage = regularGlyphPage;
        if (bold) {
            boldGlyphPage = new Font(java.awt.Font.createFont(0, FontUtil.class.getResourceAsStream(id.toString())).deriveFont(1, (float) size), true, true);
            boldGlyphPage.method553(array);
            boldGlyphPage.method554();
        }
        if (italic) {
            italicGlyphPage = new Font(java.awt.Font.createFont(0, FontUtil.class.getResourceAsStream(id.toString())).deriveFont(2, (float) size), true, true);
            italicGlyphPage.method553(array);
            italicGlyphPage.method554();
        }
        if (boldItalic) {
            boldItalicGlyphPage = new Font(java.awt.Font.createFont(0, FontUtil.class.getResourceAsStream(id.toString())).deriveFont(3, (float) size), true, true);
            boldItalicGlyphPage.method553(array);
            boldItalicGlyphPage.method554();
        }
        return new FontUtil(regularGlyphPage, boldGlyphPage, italicGlyphPage, boldItalicGlyphPage);
    }

    public int method565(final MatrixStack matrices, final CharSequence text, final float x, final float y, final int color) {
        return this.method573(matrices, text, x, y, color, true);
    }

    public int method566(final MatrixStack matrices, final CharSequence text, final double x, final double y, final int color) {
        return this.method573(matrices, text, (float) x, (float) y, color, true);
    }

    public int method567(final MatrixStack matrices, final CharSequence text, final float x, final float y, final int color) {
        return this.method573(matrices, text, x, y, color, false);
    }

    public int method568(final MatrixStack matrices, final CharSequence text, final double x, final double y, final int color) {
        return this.method573(matrices, text, (float) x, (float) y, color, false);
    }

    public int method569(final MatrixStack matrices, final CharSequence text, final double x, final double y, final float scale, final int color) {
        return this.method572(matrices, text, (float) x - this.method582(text) / 2, (float) y, scale, color, false);
    }

    public int method570(final MatrixStack matrices, final CharSequence text, final double x, final double y, final int color) {
        return this.method573(matrices, text, (float) x - this.method582(text) / 2, (float) y, color, false);
    }

    public int method571(final MatrixStack matrices, final CharSequence text, final double x, final double y, final int color) {
        return this.method573(matrices, text, (float) x - this.method582(text) / 2, (float) y, color, true);
    }

    public int method572(final MatrixStack matrices, final CharSequence text, final float x, final float y, final float scale, final int color, final boolean dropShadow) {
        final int n = 0;
        this.method580();
        final int n2 = n;
        int method575 = dropShadow ? 1 : 0;
        if (n2 == 0) {
            if (dropShadow) {
                final int max = Math.max(this.method575(matrices, text, x + 1.0f, y + 1.0f, scale, color, true), this.method575(matrices, text, x, y, scale, color, false));
                if (n2 == 0) {
                    return max;
                }
            }
            method575 = this.method575(matrices, text, x, y, scale, color, false);
        }
        return method575;
    }

    public int method573(final MatrixStack matrices, final CharSequence text, final float x, final float y, final int color, final boolean dropShadow) {
        final int n = 0;
        this.method580();
        final int n2 = n;
        int method574 = dropShadow ? 1 : 0;
        if (n2 == 0) {
            if (dropShadow) {
                final int max = Math.max(this.method574(matrices, text, x + 1.0f, y + 1.0f, color, true), this.method574(matrices, text, x, y, color, false));
                if (n2 == 0) {
                    return max;
                }
            }
            method574 = this.method574(matrices, text, x, y, color, false);
        }
        return method574;
    }

    private int method574(final MatrixStack matrixStack, final CharSequence charSequence, final float n, final float n2, int n3, final boolean b) {
        if (charSequence == null) {
            return 0;
        }
        if ((n3 & 0xFC000000) == 0x0) {
            n3 |= 0xFF000000;
        }
        if (b) {
            n3 = ((n3 & 0xFCFCFC) >> 2 | (n3 & 0xFF000000));
        }
        this.field618 = n * 2.0f;
        this.field619 = n2 * 2.0f;
        this.method576(matrixStack, charSequence, b, n3);
        return (int) (this.field618 / 4.0f);
    }

    private int method575(final MatrixStack matrixStack, final CharSequence charSequence, final float n, final float n2, final float n3, int n4, final boolean b) {
        if (charSequence == null) {
            return 0;
        }
        if ((n4 & 0xFC000000) == 0x0) {
            n4 |= 0xFF000000;
        }
        if (b) {
            n4 = ((n4 & 0xFCFCFC) >> 2 | (n4 & 0xFF000000));
        }
        this.field618 = n * 2.0f;
        this.field619 = n2 * 2.0f;
        this.method577(matrixStack, charSequence, n3, b, n4);
        return (int) (this.field618 / 4.0f);
    }

    private void method576(final MatrixStack stack, final CharSequence charSequence, final boolean b, final int n) {
        Font class137 = this.method579();
        final int n2 = 0;
        final float alpha = (n >> 24 & 0xFF) / 255.0f;
        float red = (n >> 16 & 0xFF) / 255.0f;
        float green = (n >> 8 & 0xFF) / 255.0f;
        float blue = (n & 0xFF) / 255.0f;
        final int n3 = n2;
        stack.push();
        stack.scale(0.5f, 0.5f, 0.5f);
        GlStateManager._enableBlend();
        GlStateManager._blendFunc(770, 771);
        class137.method555();
        GlStateManager._texParameter(3553, 10240, 9729);
        int i = 0;
        while (i < charSequence.length()) {
            final char char1 = charSequence.charAt(i);
            if (n3 != 0) {
                return;
            }
            Label_0462:
            {
                if (n3 == 0) {
                    Label_0417:
                    {
                        if (char1 == '�') {
                            final int n4 = i + 1;
                            if (n3 == 0) {
                                if (n4 >= charSequence.length()) {
                                    break Label_0417;
                                }
                                "0123456789abcdefklmnor".indexOf(Character.toLowerCase(charSequence.charAt(i + 1)));
                            }
                            int n5 = n4;
                            int n10;
                            int n9;
                            int n8;
                            int n7;
                            final int n6 = n7 = (n8 = (n9 = (n10 = n5)));
                            int n15;
                            int n14;
                            int n13;
                            int n12;
                            final int n11 = n12 = (n13 = (n14 = (n15 = 16)));
                            Label_0409:
                            {
                                if (n3 == 0) {
                                    if (n6 < n11) {
                                        this.field621 = false;
                                        this.field624 = false;
                                        this.field623 = false;
                                        this.field622 = false;
                                        int n17;
                                        final int n16 = n17 = n5;
                                        if (n3 == 0) {
                                            if (n16 < 0) {
                                                n5 = 15;
                                            }
                                            n17 = (b ? 1 : 0);
                                        }
                                        if (n3 == 0) {
                                            if (n16 != 0) {
                                                n5 += 16;
                                            }
                                            n17 = this.field620[n5];
                                        }
                                        final int n18 = n17;
                                        red = (n18 >> 16 & 0xFF) / 255.0f;
                                        green = (n18 >> 8 & 0xFF) / 255.0f;
                                        blue = (n18 & 0xFF) / 255.0f;
                                        if (n3 == 0) {
                                            break Label_0409;
                                        }
                                    }
                                    final int n19;
                                    n7 = (n19 = (n8 = (n9 = (n10 = n5))));
                                    final int n20;
                                    n12 = (n20 = (n13 = (n14 = (n15 = 16))));
                                }
                                if (n3 == 0) {
                                    if (n6 == n11) {
                                        break Label_0409;
                                    }
                                    n8 = (n7 = (n9 = (n10 = n5)));
                                    n13 = (n12 = (n14 = (n15 = 17)));
                                }
                                if (n3 == 0) {
                                    if (n7 == n12) {
                                        this.field621 = true;
                                        if (n3 == 0) {
                                            break Label_0409;
                                        }
                                    }
                                    n9 = (n8 = (n10 = n5));
                                    n14 = (n13 = (n15 = 18));
                                }
                                if (n3 == 0) {
                                    if (n8 == n13) {
                                        this.field624 = true;
                                        if (n3 == 0) {
                                            break Label_0409;
                                        }
                                    }
                                    n10 = (n9 = n5);
                                    n15 = (n14 = 19);
                                }
                                if (n3 == 0) {
                                    if (n9 == n14) {
                                        this.field623 = true;
                                        if (n3 == 0) {
                                            break Label_0409;
                                        }
                                    }
                                    n10 = n5;
                                    n15 = 20;
                                }
                                if (n10 == n15) {
                                    this.field622 = true;
                                    if (n3 == 0) {
                                        break Label_0409;
                                    }
                                }
                                this.field621 = false;
                                this.field624 = false;
                                this.field623 = false;
                                this.field622 = false;
                            }
                            ++i;
                            if (n3 == 0) {
                                break Label_0462;
                            }
                        }
                    }
                    class137 = this.method579();
                    class137.method555();
                }
                this.method578(class137.method557(stack, char1, this.field618, this.field619, red, blue, green, alpha), class137);
            }
            ++i;
            if (n3 != 0) {
                break;
            }
        }
        class137.method556();
        stack.pop();
    }

    private void method577(final MatrixStack stack, final CharSequence charSequence, final float n, final boolean b, final int n2) {
        final int n3 = 0;
        Font class137 = this.method579();
        final int n4 = n3;
        final float alpha = (n2 >> 24 & 0xFF) / 255.0f;
        float red = (n2 >> 16 & 0xFF) / 255.0f;
        float green = (n2 >> 8 & 0xFF) / 255.0f;
        float blue = (n2 & 0xFF) / 255.0f;
        stack.push();
        stack.scale(n, n, n);
        GlStateManager._enableBlend();
        GlStateManager._blendFunc(770, 771);
        class137.method555();
        GlStateManager._texParameter(3553, 10240, 9729);
        int i = 0;
        while (i < charSequence.length()) {
            final char char1 = charSequence.charAt(i);
            if (n4 != 0) {
                return;
            }
            Label_0460:
            {
                if (n4 == 0) {
                    Label_0415:
                    {
                        if (char1 == '�') {
                            final int n5 = i + 1;
                            if (n4 == 0) {
                                if (n5 >= charSequence.length()) {
                                    break Label_0415;
                                }
                                "0123456789abcdefklmnor".indexOf(Character.toLowerCase(charSequence.charAt(i + 1)));
                            }
                            int n6 = n5;
                            int n11;
                            int n10;
                            int n9;
                            int n8;
                            final int n7 = n8 = (n9 = (n10 = (n11 = n6)));
                            int n16;
                            int n15;
                            int n14;
                            int n13;
                            final int n12 = n13 = (n14 = (n15 = (n16 = 16)));
                            Label_0407:
                            {
                                if (n4 == 0) {
                                    if (n7 < n12) {
                                        this.field621 = false;
                                        this.field624 = false;
                                        this.field623 = false;
                                        this.field622 = false;
                                        int n18;
                                        final int n17 = n18 = n6;
                                        if (n4 == 0) {
                                            if (n17 < 0) {
                                                n6 = 15;
                                            }
                                            n18 = (b ? 1 : 0);
                                        }
                                        if (n4 == 0) {
                                            if (n17 != 0) {
                                                n6 += 16;
                                            }
                                            n18 = this.field620[n6];
                                        }
                                        final int n19 = n18;
                                        red = (n19 >> 16 & 0xFF) / 255.0f;
                                        green = (n19 >> 8 & 0xFF) / 255.0f;
                                        blue = (n19 & 0xFF) / 255.0f;
                                        if (n4 == 0) {
                                            break Label_0407;
                                        }
                                    }
                                    final int n20;
                                    n8 = (n20 = (n9 = (n10 = (n11 = n6))));
                                    final int n21;
                                    n13 = (n21 = (n14 = (n15 = (n16 = 16))));
                                }
                                if (n4 == 0) {
                                    if (n7 == n12) {
                                        break Label_0407;
                                    }
                                    n9 = (n8 = (n10 = (n11 = n6)));
                                    n14 = (n13 = (n15 = (n16 = 17)));
                                }
                                if (n4 == 0) {
                                    if (n8 == n13) {
                                        this.field621 = true;
                                        if (n4 == 0) {
                                            break Label_0407;
                                        }
                                    }
                                    n10 = (n9 = (n11 = n6));
                                    n15 = (n14 = (n16 = 18));
                                }
                                if (n4 == 0) {
                                    if (n9 == n14) {
                                        this.field624 = true;
                                        if (n4 == 0) {
                                            break Label_0407;
                                        }
                                    }
                                    n11 = (n10 = n6);
                                    n16 = (n15 = 19);
                                }
                                if (n4 == 0) {
                                    if (n10 == n15) {
                                        this.field623 = true;
                                        if (n4 == 0) {
                                            break Label_0407;
                                        }
                                    }
                                    n11 = n6;
                                    n16 = 20;
                                }
                                if (n11 == n16) {
                                    this.field622 = true;
                                    if (n4 == 0) {
                                        break Label_0407;
                                    }
                                }
                                this.field621 = false;
                                this.field624 = false;
                                this.field623 = false;
                                this.field622 = false;
                            }
                            ++i;
                            if (n4 == 0) {
                                break Label_0460;
                            }
                        }
                    }
                    class137 = this.method579();
                    class137.method555();
                }
                this.method578(class137.method557(stack, char1, this.field618, this.field619, red, blue, green, alpha), class137);
            }
            ++i;
            if (n4 != 0) {
                break;
            }
        }
        class137.method556();
        stack.pop();
    }

    private void method578(final float n, final Font class137) {
        if (this.field624) {
            final BufferBuilder buffer = Tessellator.getInstance().getBuffer();
            buffer.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION);
            buffer.vertex(this.field618, this.field619 + class137.method561() / 2, 0.0).next();
            buffer.vertex(this.field618 + n, this.field619 + class137.method561() / 2, 0.0).next();
            buffer.vertex(this.field618 + n, this.field619 + class137.method561() / 2 - 1.0f, 0.0).next();
            buffer.vertex(this.field618, this.field619 + class137.method561() / 2 - 1.0f, 0.0).next();
            BufferRenderer.drawWithGlobalProgram(buffer.end());
        }
        if (this.field623) {
            final BufferBuilder buffer2 = Tessellator.getInstance().getBuffer();
            buffer2.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION);
            final int n2 = this.field623 ? -1 : 0;
            buffer2.vertex(this.field618 + n2, this.field619 + class137.method561(), 0.0).next();
            buffer2.vertex(this.field618 + n, this.field619 + class137.method561(), 0.0).next();
            buffer2.vertex(this.field618 + n, this.field619 + class137.method561() - 1.0f, 0.0).next();
            buffer2.vertex(this.field618 + n2, this.field619 + class137.method561() - 1.0f, 0.0).next();
            buffer2.end();
            BufferRenderer.drawWithGlobalProgram(buffer2.end());
        }
        this.field618 += n;
    }

    private Font method579() {
        if (this.field621 && this.field622) {
            return this.field628;
        }
        if (this.field621) {
            return this.field626;
        }
        if (this.field622) {
            return this.field627;
        }
        return this.field625;
    }

    private void method580() {
        this.field621 = false;
        this.field622 = false;
        this.field623 = false;
        this.field624 = false;
    }

    public int method581() {
        return this.field625.method561() / 2;
    }

    public int method582(final CharSequence text) {
        if (text == null) {
            return 0;
        }
        int n = 0;
        final int length = text.length();
        int n2 = 0;
        for (int i = 0; i < length; ++i) {
            final char char1 = text.charAt(i);
            if (char1 == '\ufffd') {
                n2 = 1;
            } else if (n2 != 0 && char1 >= '0' && char1 <= 'r') {
                final int index = "0123456789abcdefklmnor".indexOf(char1);
                if (index < 16) {
                    this.field621 = false;
                    this.field622 = false;
                } else if (index == 17) {
                    this.field621 = true;
                } else if (index == 20) {
                    this.field622 = true;
                } else if (index == 21) {
                    this.field621 = false;
                    this.field622 = false;
                }
                ++i;
                n2 = 0;
            } else {
                if (n2 != 0) {
                    --i;
                }
                n += (int) (this.method579().method558(text.charAt(i)) - 8.0f);
            }
        }
        return n / 2;
    }

    public CharSequence method583(final CharSequence text, final int width) {
        return this.method584(text, width, false);
    }

    public CharSequence method584(final CharSequence text, final int maxWidth, final boolean reverse) {
        final int n = 0;
        final StringBuilder sb = new StringBuilder();
        final int n2 = n;
        int n3 = reverse ? 1 : 0;
        if (n2 == 0) {
            if (reverse) {
                n3 = text.length() - 1;
            } else {
                n3 = 0;
            }
        }
        final int n4 = n3;
        int n5 = reverse ? 1 : 0;
        if (n2 == 0) {
            if (reverse) {
                n5 = -1;
            } else {
                n5 = 1;
            }
        }
        final int n6 = n5;
        int n7 = 0;
        int i = n4;
        while (i >= 0) {
            int n9;
            final int n8 = n9 = i;
            final int length = text.length();
            int char1 = 0;
            Label_0102:
            {
                if (n2 == 0) {
                    if (n8 >= length) {
                        break;
                    }
                    char1 = (n9 = i);
                    if (n2 != 0) {
                        break Label_0102;
                    }
                }
                if (n9 >= length) {
                    break;
                }
                char1 = text.charAt(i);
            }
            int n10 = char1;
            int n13;
            final int n12;
            final int n11 = n12 = (n13 = n10);
            Label_0324:
            {
                if (n2 != 0 || (n11 != 65533 || n2 != 0)) {
                    char c = '\0';
                    int n14 = 0;
                    if (n2 == 0) {
                        if (n11 != 0) {
                            c = (char) (n13 = n10);
                            if (n2 == 0) {
                                if (c >= '0') {
                                    n14 = n10;
                                    if (n2 == 0) {
                                        if (n14 <= 114) {
                                            final int index = "0123456789abcdefklmnor".indexOf(n10);
                                            int n18;
                                            int n17;
                                            int n16;
                                            final int n15 = n16 = (n17 = (n18 = index));
                                            int n21;
                                            int n20;
                                            final int n19 = n20 = (n21 = 16);
                                            Label_0268:
                                            {
                                                Label_0264:
                                                {
                                                    if (n2 == 0) {
                                                        if (n15 < n19) {
                                                            this.field621 = false;
                                                            this.field622 = false;
                                                            if (n2 == 0) {
                                                                break Label_0264;
                                                            }
                                                        }
                                                        n17 = (n16 = (n18 = index));
                                                        final int n22;
                                                        n20 = (n22 = (n21 = 17));
                                                    }
                                                    if (n2 == 0) {
                                                        if (n16 == n19) {
                                                            this.field621 = true;
                                                            if (n2 == 0) {
                                                                break Label_0264;
                                                            }
                                                        }
                                                        n18 = (n17 = index);
                                                        n21 = (n20 = 20);
                                                    }
                                                    if (n2 == 0) {
                                                        if (n17 == n20) {
                                                            this.field622 = true;
                                                            if (n2 == 0) {
                                                                break Label_0264;
                                                            }
                                                        }
                                                        n18 = index;
                                                        if (n2 != 0) {
                                                            break Label_0268;
                                                        }
                                                        n21 = 21;
                                                    }
                                                    if (n18 == n21) {
                                                        this.field621 = false;
                                                        this.field622 = false;
                                                    }
                                                }
                                                ++i;
                                            }
                                            if (n2 == 0) {
                                                break Label_0324;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (n2 == 0) {
                        if (c != '\0') {
                            --i;
                        }
                        text.charAt(i);
                    }
                    n10 = n14;
                    n7 += (int) ((this.method579().method558((char) n10) - 8.0f) / 2.0f);
                }
            }
            int n24;
            final int n23 = n24 = i;
            if (n2 == 0) {
                if (n23 > n7) {
                    break;
                }
                n24 = (reverse ? 1 : 0);
            }
            Label_0365:
            {
                if (n24 != 0) {
                    sb.insert(0, (char) n10);
                    if (n2 == 0) {
                        break Label_0365;
                    }
                }
                sb.append((char) n10);
            }
            i += n6;
            if (n2 != 0) {
                break;
            }
        }
        return StringHider.of(sb.toString());
    }
}
