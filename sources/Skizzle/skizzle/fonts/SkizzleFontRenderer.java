/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package skizzle.fonts;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import skizzle.fonts.SkizzleFont;
import skizzle.friends.Friend;
import skizzle.friends.FriendManager;
import skizzle.modules.ModuleManager;

public class SkizzleFontRenderer
extends FontRenderer {
    public String colorcodeIdentifiers;
    public SkizzleFont font;
    public SkizzleFont italicFont;
    public Random fontRandom = new Random();
    public int[] colorCode;
    public SkizzleFont boldFont;
    public boolean bidi;
    public Color[] customColorCodes = new Color[256];
    public SkizzleFont boldItalicFont;

    public int drawString(String Nigga, float Nigga2, float Nigga3, int Nigga4) {
        SkizzleFontRenderer Nigga5;
        return Nigga5.drawString(Nigga, Nigga2, Nigga3, Nigga4, false);
    }

    public SkizzleFontRenderer(Font Nigga, boolean Nigga2, int Nigga3) {
        super(Minecraft.getMinecraft().gameSettings, new ResourceLocation(Qprot0.0("\u0b7a\u71ce\u301a\ua7f0\u22a8\u8ec6\u8c2a\u6728\u574d\u89f1\u04f6\uaf02\u89f4\u7202\u5685\u9778\u42ea\ubd10\u1709\u3db2\uf2b8\u01c4\u1b2c")), Minecraft.getMinecraft().getTextureManager(), false);
        SkizzleFontRenderer Nigga4;
        Nigga4.colorCode = new int[32];
        Nigga4.colorcodeIdentifiers = Qprot0.0("\u0b3e\u719a\u3050\ua7b7\u22e9\u8e81\u8c79\u676c\u575a\u89ae\u04f8\uaf0e\u89e3\u7249\u5681\u976d\u42e2\ubd15\u170d\u3df2\uf2a7\u01d8");
        Nigga4.setFont(Nigga, Nigga2, Nigga3);
        Nigga4.customColorCodes[113] = new Color(0, 90, 163);
        Nigga4.colorcodeIdentifiers = Nigga4.setupColorcodeIdentifier();
        Nigga4.setupMinecraftColorcodes();
        Nigga4.FONT_HEIGHT = Nigga4.getHeight();
    }

    public void drawCenteredString(String Nigga, int Nigga2, int Nigga3, int Nigga4, boolean Nigga5) {
        SkizzleFontRenderer Nigga6;
        if (Nigga5) {
            Nigga6.drawStringWithShadow(Nigga, Nigga2 - Nigga6.getStringWidth(Nigga) / 2, Nigga3, Nigga4);
        } else {
            Nigga6.drawString(Nigga, Nigga2 - Nigga6.getStringWidth(Nigga) / 2, Nigga3, Nigga4);
        }
    }

    public String toRandom(SkizzleFont Nigga, String Nigga2) {
        String Nigga3 = "";
        String Nigga4 = Qprot0.0("\u0bce\u716a\u30a0\u7220\uff7b\u8e7f\u8c82\u6788\u82da\u542e\u0443\uafb3\u8963\ua7b4\u8a97\u963b\u43b8\ubc2b\uc35f\ue1ae\uf397\u00de\u1a3e\u3c1e\u3a11\ub609\u2f7e\ude30\u975d\u752f\ueaef\u8875\u86c9\u7807\ue55e\u2913\uddfb\u61af\u9fde\udb13\u083a\u3c14\u13f5\uecee\u1f0e\uad17\ua100\u38c5\uc512\ubc0d\u27b8\uf40d\ub60d\ue582\uaa34\u2e30\u6913\u479c\u6087\u5ac1\ua17d\u09cf\u6946\uebc4\u8f78\u1a48\u4aba\u547c\uaf59\u54c9\u8243\ub0d4\u9cfc\u490a\u19aa\uf25f\u46a7\u4d63\ue77f\u226d\u4dc6\ud40d\ubdc8\u0d07\u5336\u2071\u709e\u7716\u1c04\u5d91\uef57\uc344\uc25c\ub896\ud3ae\ud594\u947e\ua479\uf1c1\u5629\u284b\uc342\u5498\u5a8e\udc0f\u0b65\u2d96\u71da\u85f6\u4f98\ue687\u196a\u148c\uee93\u2a44\ua5b9\ubf43\uebe5\u3672\u6e80\u8b66\u7a35\u41d7\u7b46\u326d\u2c68\u22eb\uaed6\ueba9\uf132\u7956\uc514\u49ec\ud5c6\ud009\u98ba\ub749\u3ade\u11d8\u752b\u05a0\u9c7a\u1316\ub2eb\uffbd\u50b8\u1283\u33b3\ua4e6\uf60b\ucdb0\ue320\ub6b2\u543e\u7963\uad6a\ucda7\u3dd3\u81fd\uc365\uee79\uf0ae\u791e\u5a64\u5a0a\u1422\u3862\u9f09\u174d\u2a44\ue227\ue9ef\u317d\u2c91\u95c3\u7087\u3c77\ufe36\u78e3\uddd8\uf18c\uf65c\uefce\u768c\u12a6\u421c\u432d\u4b6c\uf8bf\u2869\u1525\u2574\u0247\u7d6c\ud5fd\u425b\ud582\ua925\uf72b\uf6ad\uacc6\uf099\u7603\u6481\u1b77\u9835\u95ec\u1d75\u014c\u5850\u3e32\u6ab5\uc5ac\u45b7\u76b3\ufb47\uc0bb\u88d5\u190f\ud163\ua371\u2f26\u180e\uda5c\ua2f0\u6224\ueee7\u0016\udd5d\u4360\u107f\u9d8d\uc466\u784e\ude19\u3b42\u95b0\u46db\ud36e\uaa89\u9444\u978a\u5085\uda8f\u3695\u6498\u12c6\ua050\u771d\u5775\u69d3\ubb6d\u77a5\ucc57\u13c6\u5423");
        for (char Nigga5 : Nigga2.toCharArray()) {
            SkizzleFontRenderer Nigga6;
            if (!ChatAllowedCharacters.isAllowedCharacter(Nigga5)) continue;
            int Nigga7 = Nigga6.fontRandom.nextInt(Qprot0.0("\u0bce\u716a\u30a0\u7220\uff7b\u8e7f\u8c82\u6788\u82da\u542e\u0443\uafb3\u8963\ua7b4\u8a97\u963b\u43b8\ubc2b\uc35f\ue1ae\uf397\u00de\u1a3e\u3c1e\u3a11\ub609\u2f7e\ude30\u975d\u752f\ueaef\u8875\u86c9\u7807\ue55e\u2913\uddfb\u61af\u9fde\udb13\u083a\u3c14\u13f5\uecee\u1f0e\uad17\ua100\u38c5\uc512\ubc0d\u27b8\uf40d\ub60d\ue582\uaa34\u2e30\u6913\u479c\u6087\u5ac1\ua17d\u09cf\u6946\uebc4\u8f78\u1a48\u4aba\u547c\uaf59\u54c9\u8243\ub0d4\u9cfc\u490a\u19aa\uf25f\u46a7\u4d63\ue77f\u226d\u4dc6\ud40d\ubdc8\u0d07\u5336\u2071\u709e\u7716\u1c04\u5d91\uef57\uc344\uc25c\ub896\ud3ae\ud594\u947e\ua479\uf1c1\u5629\u284b\uc342\u5498\u5a8e\udc0f\u0b65\u2d96\u71da\u85f6\u4f98\ue687\u196a\u148c\uee93\u2a44\ua5b9\ubf43\uebe5\u3672\u6e80\u8b66\u7a35\u41d7\u7b46\u326d\u2c68\u22eb\uaed6\ueba9\uf132\u7956\uc514\u49ec\ud5c6\ud009\u98ba\ub749\u3ade\u11d8\u752b\u05a0\u9c7a\u1316\ub2eb\uffbd\u50b8\u1283\u33b3\ua4e6\uf60b\ucdb0\ue320\ub6b2\u543e\u7963\uad6a\ucda7\u3dd3\u81fd\uc365\uee79\uf0ae\u791e\u5a64\u5a0a\u1422\u3862\u9f09\u174d\u2a44\ue227\ue9ef\u317d\u2c91\u95c3\u7087\u3c77\ufe36\u78e3\uddd8\uf18c\uf65c\uefce\u768c\u12a6\u421c\u432d\u4b6c\uf8bf\u2869\u1525\u2574\u0247\u7d6c\ud5fd\u425b\ud582\ua925\uf72b\uf6ad\uacc6\uf099\u7603\u6481\u1b77\u9835\u95ec\u1d75\u014c\u5850\u3e32\u6ab5\uc5ac\u45b7\u76b3\ufb47\uc0bb\u88d5\u190f\ud163\ua371\u2f26\u180e\uda5c\ua2f0\u6224\ueee7\u0016\udd5d\u4360\u107f\u9d8d\uc466\u784e\ude19\u3b42\u95b0\u46db\ud36e\uaa89\u9444\u978a\u5085\uda8f\u3695\u6498\u12c6\ua050\u771d\u5775\u69d3\ubb6d\u77a5\ucc57\u13c6\u5423").length());
            Nigga3 = String.valueOf(Nigga3) + Qprot0.0("\u0bce\u716a\u30a0\u7220\uff7b\u8e7f\u8c82\u6788\u82da\u542e\u0443\uafb3\u8963\ua7b4\u8a97\u963b\u43b8\ubc2b\uc35f\ue1ae\uf397\u00de\u1a3e\u3c1e\u3a11\ub609\u2f7e\ude30\u975d\u752f\ueaef\u8875\u86c9\u7807\ue55e\u2913\uddfb\u61af\u9fde\udb13\u083a\u3c14\u13f5\uecee\u1f0e\uad17\ua100\u38c5\uc512\ubc0d\u27b8\uf40d\ub60d\ue582\uaa34\u2e30\u6913\u479c\u6087\u5ac1\ua17d\u09cf\u6946\uebc4\u8f78\u1a48\u4aba\u547c\uaf59\u54c9\u8243\ub0d4\u9cfc\u490a\u19aa\uf25f\u46a7\u4d63\ue77f\u226d\u4dc6\ud40d\ubdc8\u0d07\u5336\u2071\u709e\u7716\u1c04\u5d91\uef57\uc344\uc25c\ub896\ud3ae\ud594\u947e\ua479\uf1c1\u5629\u284b\uc342\u5498\u5a8e\udc0f\u0b65\u2d96\u71da\u85f6\u4f98\ue687\u196a\u148c\uee93\u2a44\ua5b9\ubf43\uebe5\u3672\u6e80\u8b66\u7a35\u41d7\u7b46\u326d\u2c68\u22eb\uaed6\ueba9\uf132\u7956\uc514\u49ec\ud5c6\ud009\u98ba\ub749\u3ade\u11d8\u752b\u05a0\u9c7a\u1316\ub2eb\uffbd\u50b8\u1283\u33b3\ua4e6\uf60b\ucdb0\ue320\ub6b2\u543e\u7963\uad6a\ucda7\u3dd3\u81fd\uc365\uee79\uf0ae\u791e\u5a64\u5a0a\u1422\u3862\u9f09\u174d\u2a44\ue227\ue9ef\u317d\u2c91\u95c3\u7087\u3c77\ufe36\u78e3\uddd8\uf18c\uf65c\uefce\u768c\u12a6\u421c\u432d\u4b6c\uf8bf\u2869\u1525\u2574\u0247\u7d6c\ud5fd\u425b\ud582\ua925\uf72b\uf6ad\uacc6\uf099\u7603\u6481\u1b77\u9835\u95ec\u1d75\u014c\u5850\u3e32\u6ab5\uc5ac\u45b7\u76b3\ufb47\uc0bb\u88d5\u190f\ud163\ua371\u2f26\u180e\uda5c\ua2f0\u6224\ueee7\u0016\udd5d\u4360\u107f\u9d8d\uc466\u784e\ude19\u3b42\u95b0\u46db\ud36e\uaa89\u9444\u978a\u5085\uda8f\u3695\u6498\u12c6\ua050\u771d\u5775\u69d3\ubb6d\u77a5\ucc57\u13c6\u5423").toCharArray()[Nigga7];
        }
        return Nigga3;
    }

    @Override
    public boolean getBidiFlag() {
        SkizzleFontRenderer Nigga;
        return Nigga.bidi;
    }

    public void setAntiAliasing(boolean Nigga) {
        SkizzleFontRenderer Nigga2;
        Nigga2.font.setAntiAlias(Nigga);
        Nigga2.boldFont.setAntiAlias(Nigga);
        Nigga2.italicFont.setAntiAlias(Nigga);
        Nigga2.boldItalicFont.setAntiAlias(Nigga);
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

    public int getSize() {
        SkizzleFontRenderer Nigga;
        return Nigga.font.getFont().getSize();
    }

    public SkizzleFont getFont() {
        SkizzleFontRenderer Nigga;
        return Nigga.font;
    }

    public int getColorCode(char Nigga) {
        SkizzleFontRenderer Nigga2;
        return Nigga2.colorCode[Qprot0.0("\u0b3e\u719a\u3050\ufcff\u7da1\u8e81\u8c79\u676c\u0c12\ud6e6\u04f8\uaf0e\u89e3\u2901\u09c9\u976d").indexOf(Nigga)];
    }

    public boolean isAntiAliasing() {
        SkizzleFontRenderer Nigga;
        return Nigga.font.isAntiAlias() && Nigga.boldFont.isAntiAlias() && Nigga.italicFont.isAntiAlias() && Nigga.boldItalicFont.isAntiAlias();
    }

    public List<String> formatString(String Nigga, double Nigga2) {
        ArrayList<String> Nigga3 = new ArrayList<String>();
        String Nigga4 = "";
        char Nigga5 = '\uffff';
        for (int Nigga6 = 0; Nigga6 < Nigga.toCharArray().length; ++Nigga6) {
            SkizzleFontRenderer Nigga7;
            char Nigga8 = Nigga.toCharArray()[Nigga6];
            if (Nigga8 == '\u00a7' && Nigga6 < Nigga.toCharArray().length - 1) {
                Nigga5 = Nigga.toCharArray()[Nigga6 + 1];
            }
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(Nigga4));
            if ((double)Nigga7.getStringWidth(stringBuilder.append(Nigga8).toString()) < Nigga2) {
                Nigga4 = String.valueOf(Nigga4) + Nigga8;
                continue;
            }
            Nigga3.add(Nigga4);
            Nigga4 = Nigga5 == '\uffffffff' ? String.valueOf(Nigga8) : "\u00a7" + Nigga5 + String.valueOf(Nigga8);
        }
        if (!Nigga4.equals("")) {
            Nigga3.add(Nigga4);
        }
        return Nigga3;
    }

    public String wrapFormattedStringToWidth(String Nigga, int Nigga2) {
        SkizzleFontRenderer Nigga3;
        int Nigga4 = Nigga3.sizeStringToWidth(Nigga, Nigga2);
        if (Nigga.length() <= Nigga4) {
            return Nigga;
        }
        String Nigga5 = Nigga.substring(0, Nigga4);
        char Nigga6 = Nigga.charAt(Nigga4);
        boolean Nigga7 = Nigga6 == ' ' || Nigga6 == '\n';
        String Nigga8 = String.valueOf(SkizzleFontRenderer.getFormatFromString(Nigga5)) + Nigga.substring(Nigga4 + (Nigga7 ? 1 : 0));
        return String.valueOf(Nigga5) + "\n" + Nigga3.wrapFormattedStringToWidth(Nigga8, Nigga2);
    }

    @Override
    public String trimStringToWidth(String Nigga, int Nigga2) {
        SkizzleFontRenderer Nigga3;
        return Nigga3.trimStringToWidth(Nigga, Nigga2, false);
    }

    @Override
    public int getCharWidth(char Nigga) {
        SkizzleFontRenderer Nigga2;
        return Nigga2.getStringWidth(Character.toString(Nigga));
    }

    @Override
    public List<String> listFormattedStringToWidth(String Nigga, int Nigga2) {
        SkizzleFontRenderer Nigga3;
        return Arrays.asList(Nigga3.wrapFormattedStringToWidth(Nigga, Nigga2).split("\n"));
    }

    public static {
        throw throwable;
    }

    public int drawLine(String Nigga, float Nigga2, float Nigga3, int Nigga4, boolean Nigga5) {
        SkizzleFontRenderer Nigga6;
        if (Nigga == null) {
            return 0;
        }
        GL11.glPushMatrix();
        GL11.glTranslated((double)((double)Nigga2 - 1.5), (double)((double)Nigga3 + 0.0), (double)0.0);
        boolean Nigga7 = GL11.glGetBoolean((int)3042);
        GlStateManager.enableAlpha();
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)3553);
        if ((Nigga4 & 0xFC000000) == 0) {
            Nigga4 |= 0xFF000000;
        }
        if (Nigga5) {
            Nigga4 = (Nigga4 & 0xFCFCFC) >> 2 | Nigga4 & 0xFF000000;
        }
        float Nigga8 = (float)(Nigga4 >> 16 & 0xFF) / Float.intBitsToFloat(1.014016E9f ^ 0x7F0FA7E7);
        float Nigga9 = (float)(Nigga4 >> 8 & 0xFF) / Float.intBitsToFloat(1.03700736E9f ^ 0x7EB079EF);
        float Nigga10 = (float)(Nigga4 & 0xFF) / Float.intBitsToFloat(1.02982182E9f ^ 0x7E1ED56F);
        float Nigga11 = (float)(Nigga4 >> 24 & 0xFF) / Float.intBitsToFloat(1.02975277E9f ^ 0x7E1FC7AF);
        Color Nigga12 = new Color(Nigga8, Nigga9, Nigga10, Nigga11);
        if (Nigga.contains("\u00a7")) {
            String[] Nigga13 = Nigga.split("\u00a7");
            Color Nigga14 = Nigga12;
            SkizzleFont Nigga15 = Nigga6.font;
            int Nigga16 = 0;
            boolean Nigga17 = false;
            boolean Nigga18 = false;
            boolean Nigga19 = false;
            boolean Nigga20 = false;
            boolean Nigga21 = false;
            for (int Nigga22 = 0; Nigga22 < Nigga13.length; ++Nigga22) {
                if (Nigga13[Nigga22].length() <= 0) continue;
                if (Nigga22 == 0) {
                    Nigga15.drawString(Nigga13[Nigga22], Nigga16, 0.0, Nigga14, Nigga5);
                    Nigga16 += Nigga15.getStringWidth(Nigga13[Nigga22]);
                    continue;
                }
                String Nigga23 = Nigga13[Nigga22].substring(1);
                char Nigga24 = Nigga13[Nigga22].charAt(0);
                int Nigga25 = Nigga6.colorcodeIdentifiers.indexOf(Nigga24);
                if (Nigga25 != -1) {
                    if (Nigga25 < 16) {
                        int Nigga26 = Nigga6.colorCode[Nigga25];
                        Nigga14 = Nigga6.getColor(Nigga26, Nigga11);
                        Nigga18 = false;
                        Nigga19 = false;
                        Nigga17 = false;
                        Nigga21 = false;
                        Nigga20 = false;
                    } else if (Nigga25 == 16) {
                        Nigga17 = true;
                    } else if (Nigga25 == 17) {
                        Nigga18 = true;
                    } else if (Nigga25 == 18) {
                        Nigga20 = true;
                    } else if (Nigga25 == 19) {
                        Nigga21 = true;
                    } else if (Nigga25 == 20) {
                        Nigga19 = true;
                    } else if (Nigga25 == 21) {
                        Nigga18 = false;
                        Nigga19 = false;
                        Nigga17 = false;
                        Nigga21 = false;
                        Nigga20 = false;
                        Nigga14 = Nigga12;
                    } else if (Nigga25 > 21) {
                        Color Nigga27 = Nigga6.customColorCodes[Nigga24];
                        Nigga14 = new Color((float)Nigga27.getRed() / Float.intBitsToFloat(1.03468538E9f ^ 0x7ED30BAB), (float)Nigga27.getGreen() / Float.intBitsToFloat(1.03519411E9f ^ 0x7ECCCEE9), (float)Nigga27.getBlue() / Float.intBitsToFloat(1.00985152E9f ^ 0x7F4E1C75), Nigga11);
                    }
                }
                if (Nigga18 && Nigga19) {
                    Nigga6.boldItalicFont.drawString(Nigga17 ? Nigga6.toRandom(Nigga15, Nigga23) : Nigga23, Nigga16, 0.0, Nigga14, Nigga5);
                    Nigga15 = Nigga6.boldItalicFont;
                } else if (Nigga18) {
                    Nigga6.boldFont.drawString(Nigga17 ? Nigga6.toRandom(Nigga15, Nigga23) : Nigga23, Nigga16, 0.0, Nigga14, Nigga5);
                    Nigga15 = Nigga6.boldFont;
                } else if (Nigga19) {
                    Nigga6.italicFont.drawString(Nigga17 ? Nigga6.toRandom(Nigga15, Nigga23) : Nigga23, Nigga16, 0.0, Nigga14, Nigga5);
                    Nigga15 = Nigga6.italicFont;
                } else {
                    Nigga6.font.drawString(Nigga17 ? Nigga6.toRandom(Nigga15, Nigga23) : Nigga23, Nigga16, 0.0, Nigga14, Nigga5);
                    Nigga15 = Nigga6.font;
                }
                float Nigga28 = (float)Nigga6.font.getHeight() / Float.intBitsToFloat(1.05652518E9f ^ 0x7F794B71);
                int Nigga29 = Nigga15.getStringHeight(Nigga23);
                if (Nigga20) {
                    Nigga6.drawLine((double)Nigga16 / 2.0 + 1.0, (double)(Nigga29 / 3), (double)(Nigga16 + Nigga15.getStringWidth(Nigga23)) / 2.0 + 1.0, (double)(Nigga29 / 3), Nigga28);
                }
                if (Nigga21) {
                    Nigga6.drawLine((double)Nigga16 / 2.0 + 1.0, (double)(Nigga29 / 2), (double)(Nigga16 + Nigga15.getStringWidth(Nigga23)) / 2.0 + 1.0, (double)(Nigga29 / 2), Nigga28);
                }
                Nigga16 += Nigga15.getStringWidth(Nigga23);
            }
        } else {
            Nigga6.font.drawString(Nigga, 0.0, 0.0, Nigga12, Nigga5);
        }
        if (!Nigga7) {
            GL11.glDisable((int)3042);
        }
        GL11.glPopMatrix();
        GL11.glColor4f((float)Float.intBitsToFloat(1.1271241E9f ^ 0x7CAE8C7F), (float)Float.intBitsToFloat(1.10570496E9f ^ 0x7E67B80B), (float)Float.intBitsToFloat(1.14272858E9f ^ 0x7B9CA77F), (float)Float.intBitsToFloat(1.08290227E9f ^ 0x7F0BC732));
        return (int)(Nigga2 + (float)Nigga6.getStringWidth(Nigga));
    }

    public String getFontName() {
        SkizzleFontRenderer Nigga;
        return Nigga.font.getFont().getFontName();
    }

    public List<String> wrapWords(String Nigga, double Nigga2) {
        SkizzleFontRenderer Nigga3;
        ArrayList<String> Nigga4 = new ArrayList<String>();
        if ((double)Nigga3.getStringWidth(Nigga) > Nigga2) {
            String[] Nigga5 = Nigga.split(" ");
            String Nigga6 = "";
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
                StringBuilder stringBuilder = new StringBuilder(String.valueOf(Nigga6));
                if ((double)Nigga3.getStringWidth(stringBuilder.append(Nigga8).append(" ").toString()) < Nigga2) {
                    Nigga6 = String.valueOf(Nigga6) + Nigga8 + " ";
                    continue;
                }
                Nigga4.add(Nigga6);
                Nigga6 = Nigga7 == '\uffffffff' ? String.valueOf(Nigga8) + " " : "\u00a7" + Nigga7 + Nigga8 + " ";
            }
            if (!Nigga6.equals("")) {
                if ((double)Nigga3.getStringWidth(Nigga6) < Nigga2) {
                    Nigga4.add(Nigga7 == '\uffffffff' ? String.valueOf(Nigga6) + " " : "\u00a7" + Nigga7 + Nigga6 + " ");
                    Nigga6 = "";
                } else {
                    for (String Nigga8 : Nigga3.formatString(Nigga6, Nigga2)) {
                        Nigga4.add(Nigga8);
                    }
                }
            }
        } else {
            Nigga4.add(Nigga);
        }
        return Nigga4;
    }

    @Override
    public String trimStringToWidth(String Nigga, int Nigga2, boolean Nigga3) {
        StringBuilder Nigga4 = new StringBuilder();
        int Nigga5 = 0;
        int Nigga6 = Nigga3 ? Nigga.length() - 1 : 0;
        int Nigga7 = Nigga3 ? -1 : 1;
        boolean Nigga8 = false;
        boolean Nigga9 = false;
        for (int Nigga10 = Nigga6; Nigga10 >= 0 && Nigga10 < Nigga.length() && Nigga5 < Nigga2; Nigga10 += Nigga7) {
            SkizzleFontRenderer Nigga11;
            char Nigga12 = Nigga.charAt(Nigga10);
            int Nigga13 = Nigga11.getStringWidth(Character.toString(Nigga12));
            if (Nigga8) {
                Nigga8 = false;
                if (Nigga12 != 'l' && Nigga12 != 'L') {
                    if (Nigga12 == 'r' || Nigga12 == 'R') {
                        Nigga9 = false;
                    }
                } else {
                    Nigga9 = true;
                }
            } else if (Nigga13 < 0) {
                Nigga8 = true;
            } else {
                Nigga5 += Nigga13;
                if (Nigga9) {
                    ++Nigga5;
                }
            }
            if (Nigga5 > Nigga2) break;
            if (Nigga3) {
                Nigga4.insert(0, Nigga12);
                continue;
            }
            Nigga4.append(Nigga12);
        }
        return Nigga4.toString();
    }

    public int getHeight() {
        SkizzleFontRenderer Nigga;
        return Nigga.font.getHeight() / 2;
    }

    public int getStringHeight(String Nigga) {
        SkizzleFontRenderer Nigga2;
        if (Nigga == null) {
            return 0;
        }
        return Nigga2.font.getStringHeight(Nigga) / 2;
    }

    public static boolean isFormatColor(char Nigga) {
        return Nigga >= '0' && Nigga <= '9' || Nigga >= 'a' && Nigga <= 'f' || Nigga >= 'A' && Nigga <= 'F';
    }

    @Override
    public void onResourceManagerReload(IResourceManager Nigga) {
    }

    public int sizeStringToWidth(String Nigga, int Nigga2) {
        int Nigga3;
        int Nigga4 = Nigga.length();
        int Nigga5 = 0;
        int Nigga6 = -1;
        boolean Nigga7 = false;
        for (Nigga3 = 0; Nigga3 < Nigga4; ++Nigga3) {
            char Nigga8 = Nigga.charAt(Nigga3);
            switch (Nigga8) {
                case '\n': {
                    --Nigga3;
                    break;
                }
                case '\u00a7': {
                    char Nigga9;
                    if (Nigga3 >= Nigga4 - 1) break;
                    if ((Nigga9 = Nigga.charAt(++Nigga3)) != 'l' && Nigga9 != 'L') {
                        if (Nigga9 != 'r' && Nigga9 != 'R' && !SkizzleFontRenderer.isFormatColor(Nigga9)) break;
                        Nigga7 = false;
                        break;
                    }
                    Nigga7 = true;
                    break;
                }
                case ' ': {
                    Nigga6 = Nigga3;
                }
                default: {
                    SkizzleFontRenderer Nigga10;
                    Nigga5 += Nigga10.getStringWidth(Character.toString(Nigga8));
                    if (!Nigga7) break;
                    ++Nigga5;
                }
            }
            if (Nigga8 == '\n') {
                Nigga6 = ++Nigga3;
                break;
            }
            if (Nigga5 > Nigga2) break;
        }
        return Nigga3 != Nigga4 && Nigga6 != -1 && Nigga6 < Nigga3 ? Nigga6 : Nigga3;
    }

    @Override
    public void setBidiFlag(boolean Nigga) {
        Nigga.bidi = Nigga;
    }

    public void setupMinecraftColorcodes() {
        for (int Nigga = 0; Nigga < 32; ++Nigga) {
            int Nigga2 = (Nigga >> 3 & 1) * 85;
            int Nigga3 = (Nigga >> 2 & 1) * 170 + Nigga2;
            int Nigga4 = (Nigga >> 1 & 1) * 170 + Nigga2;
            int Nigga5 = (Nigga >> 0 & 1) * 170 + Nigga2;
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

    public static boolean isFormatSpecial(char Nigga) {
        return Nigga >= 'k' && Nigga <= 'o' || Nigga >= 'K' && Nigga <= 'O' || Nigga == 'r' || Nigga == 'R';
    }

    @Override
    public int getStringWidth(String Nigga) {
        SkizzleFontRenderer Nigga2;
        for (Friend friend : FriendManager.friends) {
            Nigga = Nigga.replace(friend.getName(), (Object)((Object)EnumChatFormatting.BLUE) + friend.getNickname());
        }
        if (Nigga == null) {
            return 0;
        }
        if (Nigga.contains("\u00a7")) {
            String[] arrstring = Nigga.split("\u00a7");
            SkizzleFont Nigga4 = Nigga2.font;
            int Nigga5 = 0;
            boolean Nigga6 = false;
            boolean Nigga7 = false;
            for (int Nigga8 = 0; Nigga8 < arrstring.length; ++Nigga8) {
                if (arrstring[Nigga8].length() <= 0) continue;
                if (Nigga8 == 0) {
                    Nigga5 += Nigga4.getStringWidth(arrstring[Nigga8]);
                    continue;
                }
                String Nigga9 = arrstring[Nigga8].substring(1);
                char Nigga10 = arrstring[Nigga8].charAt(0);
                int Nigga11 = Nigga2.colorcodeIdentifiers.indexOf(Nigga10);
                if (Nigga11 != -1) {
                    if (Nigga11 < 16) {
                        Nigga6 = false;
                        Nigga7 = false;
                    } else if (Nigga11 != 16) {
                        if (Nigga11 == 17) {
                            Nigga6 = true;
                        } else if (Nigga11 != 18 && Nigga11 != 19) {
                            if (Nigga11 == 20) {
                                Nigga7 = true;
                            } else if (Nigga11 == 21) {
                                Nigga6 = false;
                                Nigga7 = false;
                            }
                        }
                    }
                }
                Nigga4 = Nigga6 && Nigga7 ? Nigga2.boldItalicFont : (Nigga6 ? Nigga2.boldFont : (Nigga7 ? Nigga2.italicFont : Nigga2.font));
                Nigga5 += Nigga4.getStringWidth(Nigga9);
            }
            return Nigga5 / 2;
        }
        return Nigga2.font.getStringWidth(Nigga) / 2;
    }

    @Override
    public int drawStringWithShadow(String Nigga, float Nigga2, float Nigga3, int Nigga4) {
        SkizzleFontRenderer Nigga5;
        return Nigga5.drawString(Nigga, Nigga2, Nigga3, Nigga4, false);
    }

    public void drawCenteredStringXY(String Nigga, int Nigga2, int Nigga3, int Nigga4, boolean Nigga5) {
        SkizzleFontRenderer Nigga6;
        String Nigga7 = Nigga;
        if (ModuleManager.nameProtect.isEnabled()) {
            Nigga7 = Nigga7.replace(Minecraft.getMinecraft().getSession().getUsername(), (Object)((Object)EnumChatFormatting.GREEN) + Qprot0.0("\u0b5b\u71d8\u3007\u398e"));
        }
        Nigga6.drawCenteredString(Nigga, Nigga2, Nigga3 - Nigga6.getHeight() / 2, Nigga4, Nigga5);
    }

    public void drawCenteredString(String Nigga, int Nigga2, int Nigga3, int Nigga4) {
        SkizzleFontRenderer Nigga5;
        String Nigga6 = Nigga;
        if (ModuleManager.nameProtect.isEnabled()) {
            Nigga6 = Nigga6.replace(Minecraft.getMinecraft().getSession().getUsername(), (Object)((Object)EnumChatFormatting.GREEN) + Qprot0.0("\u0b5b\u71d8\u3007\u9acd"));
        }
        Nigga5.drawStringWithShadow(Nigga, Nigga2 - Nigga5.getStringWidth(Nigga) / 2, Nigga3, Nigga4);
    }

    public String setupColorcodeIdentifier() {
        SkizzleFontRenderer Nigga;
        String Nigga2 = Qprot0.0("\u0b3e\u719a\u3050\ud719\u5243\u8e81\u8c79\u676c\u27f4\uf904\u04f8\uaf0e\u89e3\u02e7\u262b\u976d\u42e2\ubd15\u67a3\u4d58\uf2a7\u01d8");
        for (int Nigga3 = 0; Nigga3 < Nigga.customColorCodes.length; ++Nigga3) {
            if (Nigga.customColorCodes[Nigga3] == null) continue;
            Nigga2 = String.valueOf(Nigga2) + (char)Nigga3;
        }
        return Nigga2;
    }

    public static String getFormatFromString(String Nigga) {
        String Nigga2 = "";
        int Nigga3 = -1;
        int Nigga4 = Nigga.length();
        while ((Nigga3 = Nigga.indexOf(167, Nigga3 + 1)) != -1) {
            if (Nigga3 >= Nigga4 - 1) continue;
            char Nigga5 = Nigga.charAt(Nigga3 + 1);
            if (SkizzleFontRenderer.isFormatColor(Nigga5)) {
                Nigga2 = "\u00a7" + Nigga5;
                continue;
            }
            if (!SkizzleFontRenderer.isFormatSpecial(Nigga5)) continue;
            Nigga2 = String.valueOf(Nigga2) + "\u00a7" + Nigga5;
        }
        return Nigga2;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void setFont(Font Nigga, boolean Nigga2, int Nigga3) {
        SkizzleFontRenderer Nigga4;
        SkizzleFontRenderer skizzleFontRenderer = Nigga4;
        synchronized (skizzleFontRenderer) {
            Nigga4.font = new SkizzleFont(Nigga, Nigga2, Nigga3);
            Nigga4.boldFont = new SkizzleFont(Nigga.deriveFont(1), Nigga2, Nigga3);
            Nigga4.italicFont = new SkizzleFont(Nigga.deriveFont(2), Nigga2, Nigga3);
            Nigga4.boldItalicFont = new SkizzleFont(Nigga.deriveFont(3), Nigga2, Nigga3);
            Nigga4.FONT_HEIGHT = Nigga4.getHeight();
        }
    }

    @Override
    public int drawString(String Nigga, float Nigga2, float Nigga3, int Nigga4, boolean Nigga5) {
        int Nigga7 = 2;
        for (Friend friend : FriendManager.friends) {
            Nigga = Nigga.replace(friend.getName(), (Object)((Object)EnumChatFormatting.BLUE) + friend.getNickname());
        }
        String[] arrstring = Nigga.split("\n");
        for (int Nigga8 = 0; Nigga8 < arrstring.length; ++Nigga8) {
            SkizzleFontRenderer Nigga9;
            Nigga7 = Nigga9.drawLine(arrstring[Nigga8], Nigga2, Nigga3 + (float)(Nigga8 * Nigga9.getHeight()), Nigga4, Nigga5);
        }
        return Nigga7;
    }

    public Color getColor(int Nigga, float Nigga2) {
        return new Color((float)(Nigga >> 16) / Float.intBitsToFloat(1.01007046E9f ^ 0x7F4B73CB), (float)(Nigga >> 8 & 0xFF) / Float.intBitsToFloat(1.00809069E9f ^ 0x7F693E58), (float)(Nigga & 0xFF) / Float.intBitsToFloat(1.00747821E9f ^ 0x7F73E5A9), Nigga2);
    }
}

