/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package skizzle.newFont;

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.opengl.GL11;
import skizzle.modules.ModuleManager;
import skizzle.newFont.CFont;
import skizzle.util.RenderUtil;

public class MinecraftFontRenderer
extends CFont {
    public DynamicTexture texBold;
    public DynamicTexture texItalic;
    public int[] colorCode;
    public String colorcodeIdentifiers;
    public CFont.CharData[] boldItalicChars;
    public DynamicTexture texItalicBold;
    public CFont.CharData[] boldChars = new CFont.CharData[256];
    public CFont.CharData[] italicChars = new CFont.CharData[256];

    public double getPasswordWidth(String Nigga) {
        MinecraftFontRenderer Nigga2;
        return Nigga2.getStringWidth(Nigga.replaceAll(".", "."), Float.intBitsToFloat(1.06224717E9f ^ 0x7E509B03));
    }

    public float drawNoBSString(String Nigga, double Nigga2, double Nigga3, int Nigga4, boolean Nigga5) {
        MinecraftFontRenderer Nigga6;
        Nigga2 -= 1.0;
        if (Nigga == null) {
            return Float.intBitsToFloat(2.13842995E9f ^ 0x7F75D9CA);
        }
        CFont.CharData[] Nigga7 = Nigga6.charData;
        float Nigga8 = (float)(Nigga4 >> 24 & 0xFF) / Float.intBitsToFloat(1.04017613E9f ^ 0x7E80D417);
        boolean Nigga9 = false;
        boolean Nigga10 = false;
        boolean Nigga11 = false;
        boolean Nigga12 = false;
        Nigga2 *= 2.0;
        Nigga3 = (Nigga3 - 3.0) * 2.0;
        GL11.glPushMatrix();
        GlStateManager.scale(0.0, 0.0, 0.0);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 771);
        GlStateManager.color((float)(Nigga4 >> 16 & 0xFF) / Float.intBitsToFloat(1.03454566E9f ^ 0x7ED6E9FB), (float)(Nigga4 >> 8 & 0xFF) / Float.intBitsToFloat(1.01339712E9f ^ 0x7F183670), (float)(Nigga4 & 0xFF) / Float.intBitsToFloat(1.0143632E9f ^ 0x7F0AF449), Nigga8);
        GlStateManager.enableTexture2D();
        GlStateManager.bindTexture(Nigga6.tex.getGlTextureId());
        GL11.glBindTexture((int)3553, (int)Nigga6.tex.getGlTextureId());
        GL11.glTexParameteri((int)3553, (int)10241, (int)9728);
        GL11.glTexParameteri((int)3553, (int)10240, (int)9728);
        for (int Nigga13 = 0; Nigga13 < Nigga.length(); ++Nigga13) {
            char Nigga14 = Nigga.charAt(Nigga13);
            if (Nigga14 == '\u00a7') {
                int Nigga15 = 21;
                try {
                    Nigga15 = Qprot0.0("\ueeec\u719a\ud5a2\ub122\udf8a\u6b53\u8c79\u829e\u41cf\u74cd\ue12a\uaf0e\u6c11\u64dc\uabe2\u72bf\u42e2\u58e7\u0198\uc091\u1775\u01d8").indexOf(Nigga.charAt(Nigga13 + 1));
                }
                catch (Exception Nigga16) {
                    Nigga16.printStackTrace();
                }
                if (Nigga15 < 16) {
                    Nigga9 = false;
                    Nigga10 = false;
                    Nigga12 = false;
                    Nigga11 = false;
                    GlStateManager.bindTexture(Nigga6.tex.getGlTextureId());
                    Nigga7 = Nigga6.charData;
                    if (Nigga15 < 0) {
                        Nigga15 = 15;
                    }
                    if (Nigga5) {
                        Nigga15 += 16;
                    }
                    int Nigga17 = Nigga6.colorCode[Nigga15];
                    GlStateManager.color((float)(Nigga17 >> 16 & 0xFF) / Float.intBitsToFloat(1.00893587E9f ^ 0x7F5C23AF), (float)(Nigga17 >> 8 & 0xFF) / Float.intBitsToFloat(1.0113072E9f ^ 0x7F3852DB), (float)(Nigga17 & 0xFF) / Float.intBitsToFloat(1.00709069E9f ^ 0x7F79FBFD), Nigga8);
                } else if (Nigga15 != 16) {
                    if (Nigga15 == 17) {
                        Nigga9 = true;
                        if (Nigga10) {
                            GlStateManager.bindTexture(Nigga6.texItalicBold.getGlTextureId());
                            Nigga7 = Nigga6.boldItalicChars;
                        } else {
                            GlStateManager.bindTexture(Nigga6.texBold.getGlTextureId());
                            Nigga7 = Nigga6.boldChars;
                        }
                    } else if (Nigga15 == 18) {
                        Nigga11 = true;
                    } else if (Nigga15 == 19) {
                        Nigga12 = true;
                    } else if (Nigga15 == 20) {
                        Nigga10 = true;
                        if (Nigga9) {
                            GlStateManager.bindTexture(Nigga6.texItalicBold.getGlTextureId());
                            Nigga7 = Nigga6.boldItalicChars;
                        } else {
                            GlStateManager.bindTexture(Nigga6.texItalic.getGlTextureId());
                            Nigga7 = Nigga6.italicChars;
                        }
                    } else {
                        Nigga9 = false;
                        Nigga10 = false;
                        Nigga12 = false;
                        Nigga11 = false;
                        GlStateManager.color((float)(Nigga4 >> 16 & 0xFF) / Float.intBitsToFloat(1.05072147E9f ^ 0x7DDFBCCF), (float)(Nigga4 >> 8 & 0xFF) / Float.intBitsToFloat(1.0361049E9f ^ 0x7EBEB4C7), (float)(Nigga4 & 0xFF) / Float.intBitsToFloat(1.01057261E9f ^ 0x7F431D5C), Nigga8);
                        GlStateManager.bindTexture(Nigga6.tex.getGlTextureId());
                        Nigga7 = Nigga6.charData;
                    }
                }
                ++Nigga13;
                continue;
            }
            if (Nigga14 >= Nigga7.length) continue;
            GL11.glBegin((int)4);
            Nigga6.drawChar(Nigga7, Nigga14, (float)Nigga2, (float)Nigga3);
            GL11.glEnd();
            if (Nigga11) {
                Nigga6.drawLine(Nigga2, Nigga3 + (double)(Nigga7[Nigga14].height / 2), Nigga2 + (double)Nigga7[Nigga14].width - 8.0, Nigga3 + (double)(Nigga7[Nigga14].height / 2), Float.intBitsToFloat(1.0926208E9f ^ 0x7EA01201));
            }
            if (Nigga12) {
                Nigga6.drawLine(Nigga2, Nigga3 + (double)Nigga7[Nigga14].height - 2.0, Nigga2 + (double)Nigga7[Nigga14].width - 8.0, Nigga3 + (double)Nigga7[Nigga14].height - 2.0, Float.intBitsToFloat(1.10351539E9f ^ 0x7E464F03));
            }
            Nigga2 += (double)((float)Nigga7[Nigga14].width - Float.intBitsToFloat(1.04091046E9f ^ 0x7F0FC4AC) + (float)Nigga6.charOffset);
        }
        GL11.glHint((int)3155, (int)4352);
        GL11.glPopMatrix();
        GL11.glColor4f((float)Float.intBitsToFloat(1.08725402E9f ^ 0x7F4E2DC3), (float)Float.intBitsToFloat(1.08890842E9f ^ 0x7F676CA8), (float)Float.intBitsToFloat(1.09250982E9f ^ 0x7E9E6097), (float)Float.intBitsToFloat(1.08706381E9f ^ 0x7F4B46D6));
        return (float)Nigga2 / Float.intBitsToFloat(1.05401242E9f ^ 0x7ED2F41F);
    }

    public int drawNoBSString(String Nigga, double Nigga2, float Nigga3, int Nigga4) {
        MinecraftFontRenderer Nigga5;
        return (int)Nigga5.drawNoBSString(Nigga, Nigga2, Nigga3, Nigga4, false);
    }

    public float drawSmoothString(String Nigga, double Nigga2, double Nigga3, int Nigga4, boolean Nigga5) {
        MinecraftFontRenderer Nigga6;
        Nigga2 -= 1.0;
        if (Nigga == null) {
            return Float.intBitsToFloat(2.13632909E9f ^ 0x7F55CBAD);
        }
        CFont.CharData[] Nigga7 = Nigga6.charData;
        float Nigga8 = (float)(Nigga4 >> 24 & 0xFF) / Float.intBitsToFloat(1.03857907E9f ^ 0x7E987565);
        boolean Nigga9 = false;
        boolean Nigga10 = false;
        boolean Nigga11 = false;
        boolean Nigga12 = false;
        Nigga2 *= 2.0;
        Nigga3 = (Nigga3 - 3.0) * 2.0;
        GL11.glPushMatrix();
        GlStateManager.scale(0.0, 0.0, 0.0);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 771);
        GlStateManager.color((float)(Nigga4 >> 16 & 0xFF) / Float.intBitsToFloat(1.03428326E9f ^ 0x7EDAE8E9), (float)(Nigga4 >> 8 & 0xFF) / Float.intBitsToFloat(1.03879072E9f ^ 0x7E95B057), (float)(Nigga4 & 0xFF) / Float.intBitsToFloat(1.05281293E9f ^ 0x7DBFA67F), Nigga8);
        GlStateManager.enableTexture2D();
        GlStateManager.bindTexture(Nigga6.tex.getGlTextureId());
        GL11.glBindTexture((int)3553, (int)Nigga6.tex.getGlTextureId());
        GL11.glTexParameteri((int)3553, (int)10241, (int)9729);
        GL11.glTexParameteri((int)3553, (int)10240, (int)9729);
        for (int Nigga13 = 0; Nigga13 < Nigga.length(); ++Nigga13) {
            char Nigga14 = Nigga.charAt(Nigga13);
            if (Nigga14 == '\u00a7') {
                int Nigga15 = 21;
                try {
                    Nigga15 = Qprot0.0("\ueeec\u719a\ud5a2\ua706\uc056\u6b53\u8c79\u829e\u57eb\u6b11\ue12a\uaf0e\u6c11\u72f8\ub43e\u72bf\u42e2\u58e7\u17bc\udf4d\u1775\u01d8").indexOf(Nigga.charAt(Nigga13 + 1));
                }
                catch (Exception Nigga16) {
                    Nigga16.printStackTrace();
                }
                if (Nigga15 < 16) {
                    Nigga9 = false;
                    Nigga10 = false;
                    Nigga12 = false;
                    Nigga11 = false;
                    GlStateManager.bindTexture(Nigga6.tex.getGlTextureId());
                    Nigga7 = Nigga6.charData;
                    if (Nigga15 < 0) {
                        Nigga15 = 15;
                    }
                    if (Nigga5) {
                        Nigga15 += 16;
                    }
                    int Nigga17 = Nigga6.colorCode[Nigga15];
                    GlStateManager.color((float)(Nigga17 >> 16 & 0xFF) / Float.intBitsToFloat(1.00783469E9f ^ 0x7F6D563E), (float)(Nigga17 >> 8 & 0xFF) / Float.intBitsToFloat(1.05530445E9f ^ 0x7D99AB0F), (float)(Nigga17 & 0xFF) / Float.intBitsToFloat(1.03378726E9f ^ 0x7EE15765), Nigga8);
                } else if (Nigga15 != 16) {
                    if (Nigga15 == 17) {
                        Nigga9 = true;
                        if (Nigga10) {
                            GlStateManager.bindTexture(Nigga6.texItalicBold.getGlTextureId());
                            Nigga7 = Nigga6.boldItalicChars;
                        } else {
                            GlStateManager.bindTexture(Nigga6.texBold.getGlTextureId());
                            Nigga7 = Nigga6.boldChars;
                        }
                    } else if (Nigga15 == 18) {
                        Nigga11 = true;
                    } else if (Nigga15 == 19) {
                        Nigga12 = true;
                    } else if (Nigga15 == 20) {
                        Nigga10 = true;
                        if (Nigga9) {
                            GlStateManager.bindTexture(Nigga6.texItalicBold.getGlTextureId());
                            Nigga7 = Nigga6.boldItalicChars;
                        } else {
                            GlStateManager.bindTexture(Nigga6.texItalic.getGlTextureId());
                            Nigga7 = Nigga6.italicChars;
                        }
                    } else {
                        Nigga9 = false;
                        Nigga10 = false;
                        Nigga12 = false;
                        Nigga11 = false;
                        GlStateManager.color((float)(Nigga4 >> 16 & 0xFF) / Float.intBitsToFloat(1.04557018E9f ^ 0x7D2D226F), (float)(Nigga4 >> 8 & 0xFF) / Float.intBitsToFloat(1.00765152E9f ^ 0x7F708AC4), (float)(Nigga4 & 0xFF) / Float.intBitsToFloat(1.01420941E9f ^ 0x7F0C9B64), Nigga8);
                        GlStateManager.bindTexture(Nigga6.tex.getGlTextureId());
                        Nigga7 = Nigga6.charData;
                    }
                }
                ++Nigga13;
                continue;
            }
            if (Nigga14 >= Nigga7.length) continue;
            GL11.glBegin((int)4);
            Nigga6.drawChar(Nigga7, Nigga14, (float)Nigga2, (float)Nigga3);
            GL11.glEnd();
            if (Nigga11) {
                Nigga6.drawLine(Nigga2, Nigga3 + (double)(Nigga7[Nigga14].height / 2), Nigga2 + (double)Nigga7[Nigga14].width - 8.0, Nigga3 + (double)(Nigga7[Nigga14].height / 2), Float.intBitsToFloat(1.09569446E9f ^ 0x7ECEF865));
            }
            if (Nigga12) {
                Nigga6.drawLine(Nigga2, Nigga3 + (double)Nigga7[Nigga14].height - 2.0, Nigga2 + (double)Nigga7[Nigga14].width - 8.0, Nigga3 + (double)Nigga7[Nigga14].height - 2.0, Float.intBitsToFloat(1.08466291E9f ^ 0x7F26A4B3));
            }
            Nigga2 += (double)((float)Nigga7[Nigga14].width - Float.intBitsToFloat(1.04485139E9f ^ 0x7F43E663) + (float)Nigga6.charOffset);
        }
        GL11.glHint((int)3155, (int)4352);
        GL11.glPopMatrix();
        GL11.glColor4f((float)Float.intBitsToFloat(1.08998157E9f ^ 0x7F77CCAB), (float)Float.intBitsToFloat(1.09035546E9f ^ 0x7F7D8118), (float)Float.intBitsToFloat(1.10383987E9f ^ 0x7E4B427B), (float)Float.intBitsToFloat(1.12809664E9f ^ 0x7CBD63BF));
        return (float)Nigga2 / Float.intBitsToFloat(1.05732179E9f ^ 0x7F057355);
    }

    public void setupMinecraftColorcodes() {
        for (int Nigga = 0; Nigga < 32; ++Nigga) {
            int Nigga2 = (Nigga >> 3 & 1) * 85;
            int Nigga3 = (Nigga >> 2 & 1) * 170 + Nigga2;
            int Nigga4 = (Nigga >> 1 & 1) * 170 + Nigga2;
            int Nigga5 = (Nigga & 1) * 170 + Nigga2;
            if (Nigga == 6) {
                Nigga3 += 85;
            }
            if (Nigga >= 16) {
                Nigga3 /= 4;
                Nigga4 /= 4;
                Nigga5 /= 4;
            }
            Nigga.colorCode[Nigga] = (Nigga3 & 0xFF) << 16 | (Nigga4 & 0xFF) << 8 | Nigga5 & 0xFF;
        }
    }

    public float drawNoBSCenteredString(String Nigga, float Nigga2, float Nigga3, int Nigga4) {
        MinecraftFontRenderer Nigga5;
        return Nigga5.drawNoBSString(Nigga, Nigga2 - (float)(Nigga5.getStringWidth(Nigga) / 2), Nigga3, Nigga4);
    }

    public void drawLine(double Nigga, double Nigga2, double Nigga3, double Nigga4, float Nigga5) {
        GL11.glDisable((int)3553);
        GL11.glLineWidth((float)Nigga5);
        GL11.glBegin((int)1);
        GL11.glVertex2d((double)Nigga, (double)Nigga2);
        GL11.glVertex2d((double)Nigga3, (double)Nigga4);
        GL11.glEnd();
        GL11.glEnable((int)3553);
    }

    public List<String> wrapWords(String Nigga, double Nigga2) {
        MinecraftFontRenderer Nigga3;
        ArrayList<String> Nigga4 = new ArrayList<String>();
        if ((double)Nigga3.getStringWidth(Nigga) > Nigga2) {
            String[] Nigga5 = Nigga.split(" ");
            StringBuilder Nigga6 = new StringBuilder();
            char Nigga7 = '\uffff';
            String[] arrstring = Nigga5;
            int n = Nigga5.length;
            for (int i = 0; i < n; ++i) {
                String Nigga8 = arrstring[i];
                for (int Nigga9 = 0; Nigga9 < Nigga8.toCharArray().length; ++Nigga9) {
                    char Nigga10 = Nigga8.toCharArray()[Nigga9];
                    if (Nigga10 != '\u00a7' || Nigga9 >= Nigga8.toCharArray().length - 1) continue;
                    Nigga7 = Nigga8.toCharArray()[Nigga9 + 1];
                }
                StringBuilder stringBuilder = new StringBuilder();
                if ((double)Nigga3.getStringWidth(stringBuilder.append((Object)Nigga6).append(Nigga8).append(" ").toString()) < Nigga2) {
                    Nigga6.append(Nigga8).append(" ");
                    continue;
                }
                Nigga4.add(Nigga6.toString());
                Nigga6 = new StringBuilder("\u00a7" + Nigga7 + Nigga8 + " ");
            }
            if (Nigga6.length() > 0) {
                if ((double)Nigga3.getStringWidth(Nigga6.toString()) < Nigga2) {
                    Nigga4.add("\u00a7" + Nigga7 + Nigga6 + " ");
                    Nigga6 = new StringBuilder();
                } else {
                    Nigga4.addAll(Nigga3.formatString(Nigga6.toString(), Nigga2));
                }
            }
        } else {
            Nigga4.add(Nigga);
        }
        return Nigga4;
    }

    public int getStringWidth(String Nigga) {
        MinecraftFontRenderer Nigga2;
        if (Nigga == null) {
            return 0;
        }
        float Nigga3 = Float.intBitsToFloat(2.13559885E9f ^ 0x7F4AA6EB);
        CFont.CharData[] Nigga4 = Nigga2.charData;
        for (int Nigga5 = 0; Nigga5 < Nigga.length(); ++Nigga5) {
            char Nigga6 = Nigga.charAt(Nigga5);
            if (Nigga6 == '\u00a7') {
                Qprot0.0("\ueeec\u719a\ud5a2\u233a\u4d92\u6b53\u8c79\u829e\ud3d7\ue6d5\ue12a\uaf0e\u6c11\uf6c4\u39fa\u72bf\u42e2\u58e7\u9380\u5289\u1775\u01d8").indexOf(Nigga6);
                ++Nigga5;
                continue;
            }
            if (Nigga6 >= Nigga4.length) continue;
            Nigga3 += (float)Nigga4[Nigga6].width - Float.intBitsToFloat(1.06420947E9f ^ 0x7E6A40EB) + (float)Nigga2.charOffset;
        }
        return (int)(Nigga3 / Float.intBitsToFloat(1.06126938E9f ^ 0x7F41AF93));
    }

    public int drawStringWithShadow(String Nigga, double Nigga2, float Nigga3, int Nigga4) {
        MinecraftFontRenderer Nigga5;
        float Nigga6 = Nigga5.drawString(Nigga, Nigga2 + 0.0, (double)(Nigga3 += Float.intBitsToFloat(1.05787808E9f ^ 0x7F4DF058)) + 0.0, Nigga4, true, Float.intBitsToFloat(1.04256013E9f ^ 0x7F20F843));
        return (int)Math.max(Nigga6, Nigga5.drawString(Nigga, Nigga2, Nigga3, Nigga4, false, Float.intBitsToFloat(1.04771277E9f ^ 0x7F761F27)));
    }

    public int getHeight() {
        MinecraftFontRenderer Nigga;
        return (Nigga.fontHeight - 8) / 2;
    }

    public MinecraftFontRenderer(Font Nigga, boolean Nigga2, boolean Nigga3) {
        super(Nigga, Nigga2, Nigga3);
        MinecraftFontRenderer Nigga4;
        Nigga4.boldItalicChars = new CFont.CharData[256];
        Nigga4.colorCode = new int[32];
        Nigga4.colorcodeIdentifiers = Qprot0.0("\ueeec\u719a\ud5a2\ua7b7\uc007\u6b53\u8c79\u829e\u575a\u6b40\ue12a\uaf0e\u6c11\u7249\ub46f\u72bf\u42e2\u58e7\u170d\udf1c\u1775\u01d8");
        Nigga4.setupMinecraftColorcodes();
        Nigga4.setupBoldItalicIDs();
    }

    public int drawSmoothStringWithShadow(String Nigga, double Nigga2, float Nigga3, int Nigga4) {
        MinecraftFontRenderer Nigga5;
        float Nigga6 = Nigga5.drawSmoothString(Nigga, Nigga2 + 0.0, (double)(Nigga3 += Float.intBitsToFloat(1.05735469E9f ^ 0x7F45F3D4)) + 0.0, -1879048192, true);
        return (int)Math.max(Nigga6, Nigga5.drawSmoothString(Nigga, Nigga2, Nigga3, Nigga4, false));
    }

    @Override
    public void setAntiAlias(boolean Nigga) {
        MinecraftFontRenderer Nigga2;
        super.setAntiAlias(Nigga);
        Nigga2.setupBoldItalicIDs();
    }

    public int drawString(String Nigga, double Nigga2, double Nigga3, int Nigga4) {
        MinecraftFontRenderer Nigga5;
        return (int)Nigga5.drawString(Nigga, Nigga2, Nigga3 + 2.5, Nigga4, false, Float.intBitsToFloat(1.04560474E9f ^ 0x7F566552));
    }

    public static {
        throw throwable;
    }

    public void setupBoldItalicIDs() {
        MinecraftFontRenderer Nigga;
        Nigga.texBold = Nigga.setupTexture(Nigga.font.deriveFont(1), Nigga.antiAlias, Nigga.fractionalMetrics, Nigga.boldChars);
        Nigga.texItalic = Nigga.setupTexture(Nigga.font.deriveFont(2), Nigga.antiAlias, Nigga.fractionalMetrics, Nigga.italicChars);
        Nigga.texItalicBold = Nigga.setupTexture(Nigga.font.deriveFont(3), Nigga.antiAlias, Nigga.fractionalMetrics, Nigga.boldItalicChars);
    }

    @Override
    public void setFractionalMetrics(boolean Nigga) {
        MinecraftFontRenderer Nigga2;
        super.setFractionalMetrics(Nigga);
        Nigga2.setupBoldItalicIDs();
    }

    public String trimStringToWidth(String Nigga, int Nigga2) {
        MinecraftFontRenderer Nigga3;
        return Nigga3.trimStringToWidth(Nigga, Nigga2, false);
    }

    public List<String> formatString(String Nigga, double Nigga2) {
        ArrayList<String> Nigga3 = new ArrayList<String>();
        StringBuilder Nigga4 = new StringBuilder();
        char Nigga5 = '\uffff';
        char[] Nigga6 = Nigga.toCharArray();
        for (int Nigga7 = 0; Nigga7 < Nigga6.length; ++Nigga7) {
            MinecraftFontRenderer Nigga8;
            char Nigga9 = Nigga6[Nigga7];
            if (Nigga9 == '\u00a7' && Nigga7 < Nigga6.length - 1) {
                Nigga5 = Nigga6[Nigga7 + 1];
            }
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(Nigga4.toString()));
            if ((double)Nigga8.getStringWidth(stringBuilder.append(Nigga9).toString()) < Nigga2) {
                Nigga4.append(Nigga9);
                continue;
            }
            Nigga3.add(Nigga4.toString());
            Nigga4 = new StringBuilder("\u00a7" + Nigga5 + Nigga9);
        }
        if (Nigga4.length() > 0) {
            Nigga3.add(Nigga4.toString());
        }
        return Nigga3;
    }

    @Override
    public void setFont(Font Nigga) {
        MinecraftFontRenderer Nigga2;
        super.setFont(Nigga);
        Nigga2.setupBoldItalicIDs();
    }

    public double getStringWidth(String Nigga, float Nigga2) {
        MinecraftFontRenderer Nigga3;
        if (Nigga == null) {
            return 0.0;
        }
        float Nigga4 = Float.intBitsToFloat(2.12389197E9f ^ 0x7E9804D9);
        CFont.CharData[] Nigga5 = Nigga3.charData;
        for (int Nigga6 = 0; Nigga6 < Nigga.length(); ++Nigga6) {
            char Nigga7 = Nigga.charAt(Nigga6);
            if (Nigga7 == '\u00a7') {
                Qprot0.0("\ueeec\u719a\ud5a2\u233a\u4d92\u6b53\u8c79\u829e\ud3d7\ue6d5\ue12a\uaf0e\u6c11\uf6c4\u39fa\u72bf\u42e2\u58e7\u9380\u5289\u1775\u01d8").indexOf(Nigga7);
                ++Nigga6;
                continue;
            }
            if (Nigga7 >= Nigga5.length) continue;
            Nigga4 += (float)Nigga5[Nigga7].width - Nigga2 + (float)Nigga3.charOffset;
        }
        return Nigga4 / Float.intBitsToFloat(1.0596985E9f ^ 0x7F29B75E);
    }

    public float drawString(String Nigga, double Nigga2, double Nigga3, int Nigga4, boolean Nigga5, float Nigga6) {
        MinecraftFontRenderer Nigga7;
        if (ModuleManager.nameProtect.isEnabled()) {
            Nigga = Nigga.replace(Minecraft.getMinecraft().getSession().getUsername(), (Object)((Object)EnumChatFormatting.GREEN) + Qprot0.0("\uee89\u71d8\ud5f5\ud3f1") + (Object)((Object)EnumChatFormatting.RESET));
        }
        Nigga2 -= 1.0;
        if (Nigga == null) {
            return Float.intBitsToFloat(2.13610829E9f ^ 0x7F526CEF);
        }
        if (Nigga4 == 0x20FFFFFF) {
            Nigga4 = 0xFFFFFF;
        }
        if ((Nigga4 & 0xFC000000) == 0) {
            Nigga4 |= 0xFF000000;
        }
        if (Nigga5) {
            Nigga4 = (Nigga4 & 0xFCFCFC) >> 2 | Nigga4 & 0xFF000000;
        }
        CFont.CharData[] Nigga8 = Nigga7.charData;
        float Nigga9 = (float)(Nigga4 >> 24 & 0xFF) / Float.intBitsToFloat(1.03863718E9f ^ 0x7E97589D);
        boolean Nigga10 = false;
        boolean Nigga11 = false;
        boolean Nigga12 = false;
        boolean Nigga13 = false;
        Nigga2 *= 2.0;
        Nigga3 = (Nigga3 - 3.0) * 2.0;
        GL11.glPushMatrix();
        GlStateManager.scale(0.0, 0.0, 0.0);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 771);
        RenderUtil.glColor(Nigga4);
        GlStateManager.enableTexture2D();
        GlStateManager.bindTexture(Nigga7.tex.getGlTextureId());
        GL11.glBindTexture((int)3553, (int)Nigga7.tex.getGlTextureId());
        for (int Nigga14 = 0; Nigga14 < Nigga.length(); ++Nigga14) {
            char Nigga15 = Nigga.charAt(Nigga14);
            if (Nigga15 == '\u00a7') {
                int Nigga16 = 21;
                try {
                    Nigga16 = Qprot0.0("\ueeec\u719a\ud5a2\ud3b0\u7c18\u6b53\u8c79\u829e\u235d\ud75f\ue12a\uaf0e\u6c11\u064e\u0870\u72bf\u42e2\u58e7\u630a\u6303\u1775\u01d8").indexOf(Nigga.charAt(Nigga14 + 1));
                }
                catch (Exception Nigga17) {
                    Nigga17.printStackTrace();
                }
                if (Nigga16 < 16) {
                    Nigga10 = false;
                    Nigga11 = false;
                    Nigga13 = false;
                    Nigga12 = false;
                    GlStateManager.bindTexture(Nigga7.tex.getGlTextureId());
                    Nigga8 = Nigga7.charData;
                    if (Nigga16 < 0) {
                        Nigga16 = 15;
                    }
                    if (Nigga5) {
                        Nigga16 += 16;
                    }
                    int Nigga18 = Nigga7.colorCode[Nigga16];
                    GL11.glColor4d((double)((float)(Nigga18 >> 16 & 0xFF) / Float.intBitsToFloat(1.00966739E9f ^ 0x7F514D37)), (double)((float)(Nigga18 >> 8 & 0xFF) / Float.intBitsToFloat(1.03310176E9f ^ 0x7EECE1A1)), (double)((float)(Nigga18 & 0xFF) / Float.intBitsToFloat(1.0339792E9f ^ 0x7EDE4551)), (double)Nigga9);
                } else if (Nigga16 != 16) {
                    if (Nigga16 == 17) {
                        Nigga10 = true;
                        if (Nigga11) {
                            GlStateManager.bindTexture(Nigga7.texItalicBold.getGlTextureId());
                            Nigga8 = Nigga7.boldItalicChars;
                        } else {
                            GlStateManager.bindTexture(Nigga7.texBold.getGlTextureId());
                            Nigga8 = Nigga7.boldChars;
                        }
                    } else if (Nigga16 == 18) {
                        Nigga12 = true;
                    } else if (Nigga16 == 19) {
                        Nigga13 = true;
                    } else if (Nigga16 == 20) {
                        Nigga11 = true;
                        if (Nigga10) {
                            GlStateManager.bindTexture(Nigga7.texItalicBold.getGlTextureId());
                            Nigga8 = Nigga7.boldItalicChars;
                        } else {
                            GlStateManager.bindTexture(Nigga7.texItalic.getGlTextureId());
                            Nigga8 = Nigga7.italicChars;
                        }
                    } else {
                        Nigga10 = false;
                        Nigga11 = false;
                        Nigga13 = false;
                        Nigga12 = false;
                        GlStateManager.color((float)(Nigga4 >> 16 & 0xFF) / Float.intBitsToFloat(1.0110455E9f ^ 0x7F3C547D), (float)(Nigga4 >> 8 & 0xFF) / Float.intBitsToFloat(1.03584704E9f ^ 0x7EC2C575), (float)(Nigga4 & 0xFF) / Float.intBitsToFloat(1.02428755E9f ^ 0x7E726327), Nigga9);
                        GlStateManager.bindTexture(Nigga7.tex.getGlTextureId());
                        Nigga8 = Nigga7.charData;
                    }
                }
                ++Nigga14;
                continue;
            }
            if (Nigga15 >= Nigga8.length) continue;
            GL11.glBegin((int)4);
            Nigga7.drawChar(Nigga8, Nigga15, (float)Nigga2, (float)Nigga3);
            GL11.glEnd();
            if (Nigga12) {
                Nigga7.drawLine(Nigga2, Nigga3 + (double)(Nigga8[Nigga15].height / 2), Nigga2 + (double)Nigga8[Nigga15].width - 8.0, Nigga3 + (double)(Nigga8[Nigga15].height / 2), Float.intBitsToFloat(1.10520525E9f ^ 0x7E60180F));
            }
            if (Nigga13) {
                Nigga7.drawLine(Nigga2, Nigga3 + (double)Nigga8[Nigga15].height - 2.0, Nigga2 + (double)Nigga8[Nigga15].width - 8.0, Nigga3 + (double)Nigga8[Nigga15].height - 2.0, Float.intBitsToFloat(1.08214029E9f ^ 0x7F002687));
            }
            Nigga2 += (double)((float)Nigga8[Nigga15].width - Nigga6 + (float)Nigga7.charOffset);
        }
        GL11.glHint((int)3155, (int)4352);
        GL11.glPopMatrix();
        GL11.glColor4f((float)Float.intBitsToFloat(1.09070285E9f ^ 0x7E82CDCF), (float)Float.intBitsToFloat(1.10576922E9f ^ 0x7E68B33B), (float)Float.intBitsToFloat(1.08572314E9f ^ 0x7F36D1C6), (float)Float.intBitsToFloat(1.09068134E9f ^ 0x7E827A2B));
        return (float)Nigga2 / Float.intBitsToFloat(1.06363072E9f ^ 0x7F65B77C);
    }

    public int drawPassword(String Nigga, double Nigga2, float Nigga3, int Nigga4) {
        MinecraftFontRenderer Nigga5;
        return (int)Nigga5.drawString(Nigga.replaceAll(".", "."), Nigga2, Nigga3, Nigga4, false, Float.intBitsToFloat(1.04852179E9f ^ 0x7F7F2C3C));
    }

    public String trimStringToWidth(String Nigga, int Nigga2, boolean Nigga3) {
        StringBuilder Nigga4 = new StringBuilder();
        float Nigga5 = Float.intBitsToFloat(2.12431437E9f ^ 0x7E9E7711);
        int Nigga6 = Nigga3 ? Nigga.length() - 1 : 0;
        int Nigga7 = Nigga3 ? -1 : 1;
        boolean Nigga8 = false;
        boolean Nigga9 = false;
        for (int Nigga10 = Nigga6; Nigga10 >= 0 && Nigga10 < Nigga.length() && Nigga5 < (float)Nigga2; Nigga10 += Nigga7) {
            MinecraftFontRenderer Nigga11;
            char Nigga12 = Nigga.charAt(Nigga10);
            float Nigga13 = Nigga11.getCharWidthFloat(Nigga12);
            if (Nigga8) {
                Nigga8 = false;
                if (Nigga12 != 'l' && Nigga12 != 'L') {
                    if (Nigga12 == 'r' || Nigga12 == 'R') {
                        Nigga9 = false;
                    }
                } else {
                    Nigga9 = true;
                }
            } else if (Nigga13 < Float.intBitsToFloat(2.13164787E9f ^ 0x7F0E5D85)) {
                Nigga8 = true;
            } else {
                Nigga5 += Nigga13;
                if (Nigga9) {
                    Nigga5 += Float.intBitsToFloat(1.08708762E9f ^ 0x7F4BA3D3);
                }
            }
            if (Nigga5 > (float)Nigga2) break;
            if (Nigga3) {
                Nigga4.insert(0, Nigga12);
                continue;
            }
            Nigga4.append(Nigga12);
        }
        return Nigga4.toString();
    }

    public float drawCenteredString(String Nigga, float Nigga2, float Nigga3, int Nigga4) {
        MinecraftFontRenderer Nigga5;
        return Nigga5.drawString(Nigga, Nigga2 - (float)(Nigga5.getStringWidth(Nigga) / 2), Nigga3, Nigga4);
    }

    public float getCharWidthFloat(char Nigga) {
        MinecraftFontRenderer Nigga2;
        if (Nigga == '\u00a7') {
            return Float.intBitsToFloat(-1.06227789E9f ^ 0x7F2EECEE);
        }
        if (Nigga == ' ') {
            return Float.intBitsToFloat(1.06522835E9f ^ 0x7F7E1845);
        }
        int Nigga3 = Qprot0.0("\uee1c\u716a\ud552\u9c1c\u3b49\u6bad\u8c82\u827a\u6ce6\u901c\ue191\uafb3\u6c91\u4988\u4ea5\u73e9\u43b8\u59d9\u2d63\u259c\u1645\u00de\uffcc\ud222\ufe23\u53db\u2f7e\u3bc2\u7961\ub11d\u0f3d\u8875\u633b\u963b\u216c\uccc1\uddfb\u845d\u71e2\u1f21\uede8\u3c14\uf607\u02d2\udb3c\u48c5\ua100\udd37\u2b2e\u783f\uc26a\uf40d\u53ff\u0bbe\u6e06\ucbe2\u6913\ua26e\u8ebb\u9ef3\u44af\u09cf\u8cb4\u05f8\u4b4a\uff9a\u4aba\ub18e\u4165\u90fb\u6791\ub0d4\u790e\ua736\udd98\u178d\u46a7\ua891\u0943\ue65f\ua814\ud40d\u583a\ue33b\u9704\uc5a3\u709e\u92e4\uf238\u99a3\u0a85\uc344\u27ae\u56aa\u179c\u3046\u947e\u418b\u1ffd\u921b\ucd99\uc342\ub16a\ub4b2\u183d\ueeb7\u2d96\u9428\u6bca\u8baa\u0355\u196a\uf17e\u00af\uee76\u406b\ubf43\u0e17\ud84e\uaab2\u6eb4\u7a35\ua425\u957a\uf65f\uc9ba\u22eb\u4b24\u0595\u3500\u9c84\uc514\uac1e\u3bfa\u143b\u7d68\ub749\udf2c\uffe4\ub119\ue072\u9c7a\uf6e4\u5cd7\u3b8f\ub56a\u1283\ud641\u4ada\u3239\u2862\ue320\u5340\uba02\ubd51\u48b8\ucda7\ud821\u6fc1\u0757\u0bab\uf0ae\u9cec\ub458\u9e38\uf1f0\u3862\u7afb\uf971\uee76\u07f5\ue9ef\ud48f\uc2ad\u51f1\u9555\u3c77\u1bc4\u96df\u19ea\u145e\uf65c\u0a3c\u98b0\ud694\ua7ce\u432d\uae9e\u1683\uec5b\uf0f7\u2574\ue7b5\u9350\u11cf\ua789\ud582\u4cd7\u1917\u329f\u4914\uf099\u93f1\u8abd\udf45\u7de7\u95ec\uf887\uef70\u9c62\udbe0\u6ab5\u205e\uab8b\ub281\u1e95\uc0bb\u6d27\uf733\u1551\u46a3\u2f26\ufdfc\u3460\u66c2\u87f6\ueee7\ue5e4\u3361\u8752\uf5ad\u9d8d\u2194\u9672\u1a2b\ude90\u95b0\ua329\u3d52\u6ebb\u7196\u978a\ub577\u34b3\uf2a7\u814a\u12c6\u45a2\u9921\u9347\u8c01\ubb6d\u9257\u226b\ud7f4\ub1f1").indexOf(Nigga);
        if (Nigga > '\u0000' && Nigga3 != -1) {
            return (float)Nigga2.charData[Nigga3].width / Float.intBitsToFloat(1.01592646E9f ^ 0x7C8DCEDF) - Float.intBitsToFloat(1.04280979E9f ^ 0x7EA803D5);
        }
        if ((float)Nigga2.charData[Nigga].width / Float.intBitsToFloat(1.01615066E9f ^ 0x7C913A7F) - Float.intBitsToFloat(1.07186285E9f ^ 0x7F635429) != Float.intBitsToFloat(2.13599974E9f ^ 0x7F50C4F6)) {
            int Nigga4 = (int)((float)Nigga2.charData[Nigga].width / Float.intBitsToFloat(1.04356826E9f ^ 0x7E33967B) - Float.intBitsToFloat(1.05184659E9f ^ 0x7E31E7BB)) >>> 4;
            int Nigga5 = (int)((float)Nigga2.charData[Nigga].width / Float.intBitsToFloat(1.05162304E9f ^ 0x7EAE7E99) - Float.intBitsToFloat(1.04541222E9f ^ 0x7ECFB967)) & 0xF;
            return (++Nigga5 - (Nigga4 &= 0xF)) / 2 + 1;
        }
        return Float.intBitsToFloat(2.12932531E9f ^ 0x7EEAECF1);
    }

    public String trimStringToWidthPassword(String Nigga, int Nigga2, boolean Nigga3) {
        MinecraftFontRenderer Nigga4;
        Nigga = Nigga.replaceAll(".", ".");
        return Nigga4.trimStringToWidth(Nigga, Nigga2, Nigga3);
    }

    public float drawCenteredStringWithShadow(String Nigga, float Nigga2, float Nigga3, int Nigga4) {
        MinecraftFontRenderer Nigga5;
        return Nigga5.drawStringWithShadow(Nigga, Nigga2 - (float)(Nigga5.getStringWidth(Nigga) / 2), Nigga3, Nigga4);
    }

    public int drawSmoothString(String Nigga, double Nigga2, float Nigga3, int Nigga4) {
        MinecraftFontRenderer Nigga5;
        return (int)Nigga5.drawSmoothString(Nigga, Nigga2, Nigga3, Nigga4, false);
    }
}

