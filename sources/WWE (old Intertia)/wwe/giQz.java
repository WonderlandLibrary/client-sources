package wwe;

import net.minecraft.client.gui.*;
import java.awt.*;
import net.minecraft.client.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.client.resources.*;

public class giQz extends FontRenderer
{
    public final Random Cruh;
    private final Color[] fyGC;
    private final int[] kOye;
    private yfok EHPb;
    private yfok cXAL;
    private yfok MxXf;
    private yfok Ytce;
    private String Pwkr;
    private boolean YRLr;
    
    public giQz(final Font font, final boolean b, final int n) {
        super(Minecraft.getMinecraft().gameSettings, new ResourceLocation("textures/font/ascii.png"), Minecraft.getMinecraft().getTextureManager(), false);
        this.Cruh = new Random();
        this.fyGC = new Color[256];
        this.kOye = new int[32];
        this.Pwkr = "0123456789abcdefklmnor";
        this.hQSM(font, b, n);
        this.fyGC[113] = new Color(0, 90, 163);
        this.Pwkr = this.zbaE();
        this.WGfM();
        this.FONT_HEIGHT = this.cxUJ();
    }
    
    public int ZZpJ(final String s, final float n, final float n2, final int n3) {
        return this.drawString(s, n, n2, n3, false);
    }
    
    public void IVKj(final String s, final int n, final int n2, final int n3) {
        this.drawString(s, (float)n, (float)n2, n3, true);
    }
    
    public void oZcN(final String s, final int n, final int n2, final int n3, final boolean b) {
        if (b) {
            this.IVKj(s, n - this.getStringWidth(s) / 2, n2, n3);
            if (giQz.\u2007\u200f\u2009\u2002\u2008\u2004\u2004\u2007) {
                throw null;
            }
        }
        else {
            this.drawString(s, n - this.getStringWidth(s) / 2, n2, n3);
        }
    }
    
    public void MBvn(final String s, final int n, final int n2, final int n3, final boolean b) {
        this.oZcN(s, n, n2 - this.cxUJ() / 2, n3, b);
    }
    
    public void cECW(final String s, final int n, final int n2, final int n3) {
        this.IVKj(s, n - this.getStringWidth(s) / 2, n2, n3);
    }
    
    public int func_175065_a(final String s, final float n, final float n2, final int n3, final boolean b) {
        int n4 = 0;
        final String[] array = s.split("\n");
        int i = 0;
        while (i < array.length) {
            n4 += this.cBck(array[i], n, n2 + i * this.cxUJ(), n3, b);
            ++i;
            if (giQz.\u2007\u200f\u2009\u2002\u2008\u2004\u2004\u2007) {
                throw null;
            }
        }
        return n4;
    }
    
    private int cBck(final String s, final float n, final float n2, int n3, final boolean b) {
        if (s == null) {
            return 0;
        }
        GL11.glPushMatrix();
        GL11.glTranslated(n - 1.5, n2 + 0.5, 0.0);
        GL11.glGetBoolean(3042);
        GlStateManager.enableAlpha();
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3553);
        if ((n3 & 0xFC000000) == 0x0) {
            n3 |= 0xFF000000;
        }
        final Color color = new Color((n3 >> 16 & 0xFF) / 255.0f, (n3 >> 8 & 0xFF) / 255.0f, (n3 & 0xFF) / 255.0f, (n3 >> 24 & 0xFF) / 255.0f);
        if (s.contains("§")) {
            final String[] array = s.split("§");
            final Color color2 = color;
            final yfok ehPb = this.EHPb;
            int n4 = 0;
            int i = 0;
            while (i < array.length) {
                if (array[i].length() <= 0) {
                    if (giQz.\u2007\u200f\u2009\u2002\u2008\u2004\u2004\u2007) {
                        throw null;
                    }
                }
                else {
                    ehPb.RhUE(array[i], n4, 0.0, color2, b);
                    n4 += ehPb.JuED(array[i]);
                    if (giQz.\u2007\u200f\u2009\u2002\u2008\u2004\u2004\u2007) {
                        throw null;
                    }
                }
                ++i;
                if (giQz.\u2007\u200f\u2009\u2002\u2008\u2004\u2004\u2007) {
                    throw null;
                }
            }
            if (giQz.\u2007\u200f\u2009\u2002\u2008\u2004\u2004\u2007) {
                throw null;
            }
        }
        else {
            this.EHPb.RhUE(s, 0.0, 0.0, color, b);
        }
        GL11.glDisable(3042);
        GL11.glPopMatrix();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        return (int)(n + this.getStringWidth(s));
    }
    
    private String Mror(final yfok yfok, final String s) {
        String string = "";
        final char[] array = s.toCharArray();
        final int length = array.length;
        int i = 0;
        while (i < length) {
            if (ChatAllowedCharacters.isAllowedCharacter(array[i])) {
                string += "\u00c0\u00c1\u00c2\u00c8\u00ca\u00cb\u00cd\u00d3\u00d4\u00d5\u00da\u00df\u00e3\u00f5\u011f\u0130\u0131\u0152\u0153\u015e\u015f\u0174\u0175\u017e\u0207\u0000\u0000\u0000\u0000\u0000\u0000\u0000 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u0000\u00c7\u00fc\u00e9\u00e2\u00e4\u00e0\u00e5\u00e7\u00ea\u00eb\u00e8\u00ef\u00ee\u00ec\u00c4\u00c5\u00c9\u00e6\u00c6\u00f4\u00f6\u00f2\u00fb\u00f9\u00ff\u00d6\u00dc\u00f8£\u00d8\u00d7\u0192\u00e1\u00ed\u00f3\u00fa\u00f1\u00d1ªº¿®¬½¼¡«»\u2591\u2592\u2593\u2502\u2524\u2561\u2562\u2556\u2555\u2563\u2551\u2557\u255d\u255c\u255b\u2510\u2514\u2534\u252c\u251c\u2500\u253c\u255e\u255f\u255a\u2554\u2569\u2566\u2560\u2550\u256c\u2567\u2568\u2564\u2565\u2559\u2558\u2552\u2553\u256b\u256a\u2518\u250c\u2588\u2584\u258c\u2590\u2580\u03b1\u03b2\u0393\u03c0\u03a3\u03c3\u03bc\u03c4\u03a6\u0398\u03a9\u03b4\u221e\u2205\u2208\u2229\u2261±\u2265\u2264\u2320\u2321\u00f7\u2248°\u2219·\u221a\u207f²\u25a0\u0000".toCharArray()[this.Cruh.nextInt("\u00c0\u00c1\u00c2\u00c8\u00ca\u00cb\u00cd\u00d3\u00d4\u00d5\u00da\u00df\u00e3\u00f5\u011f\u0130\u0131\u0152\u0153\u015e\u015f\u0174\u0175\u017e\u0207\u0000\u0000\u0000\u0000\u0000\u0000\u0000 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u0000\u00c7\u00fc\u00e9\u00e2\u00e4\u00e0\u00e5\u00e7\u00ea\u00eb\u00e8\u00ef\u00ee\u00ec\u00c4\u00c5\u00c9\u00e6\u00c6\u00f4\u00f6\u00f2\u00fb\u00f9\u00ff\u00d6\u00dc\u00f8£\u00d8\u00d7\u0192\u00e1\u00ed\u00f3\u00fa\u00f1\u00d1ªº¿®¬½¼¡«»\u2591\u2592\u2593\u2502\u2524\u2561\u2562\u2556\u2555\u2563\u2551\u2557\u255d\u255c\u255b\u2510\u2514\u2534\u252c\u251c\u2500\u253c\u255e\u255f\u255a\u2554\u2569\u2566\u2560\u2550\u256c\u2567\u2568\u2564\u2565\u2559\u2558\u2552\u2553\u256b\u256a\u2518\u250c\u2588\u2584\u258c\u2590\u2580\u03b1\u03b2\u0393\u03c0\u03a3\u03c3\u03bc\u03c4\u03a6\u0398\u03a9\u03b4\u221e\u2205\u2208\u2229\u2261±\u2265\u2264\u2320\u2321\u00f7\u2248°\u2219·\u221a\u207f²\u25a0\u0000".length())];
            }
            ++i;
            if (giQz.\u2007\u200f\u2009\u2002\u2008\u2004\u2004\u2007) {
                throw null;
            }
        }
        return string;
    }
    
    public int qaVV(final String s) {
        if (s == null) {
            return 0;
        }
        return this.EHPb.IJUi(s) / 2;
    }
    
    public int cxUJ() {
        return this.EHPb.McJQ() / 2;
    }
    
    public static String BukF(final String s) {
        String s2 = "";
        int index = -1;
        final int length = s.length();
        while ((index = s.indexOf(167, index + 1)) != -1) {
            if (index < length - 1) {
                final char char1 = s.charAt(index + 1);
                if (zRDT(char1)) {
                    s2 = "§" + char1;
                    if (giQz.\u2007\u200f\u2009\u2002\u2008\u2004\u2004\u2007) {
                        throw null;
                    }
                }
                else if (uUuv(char1)) {
                    s2 = s2 + "§" + char1;
                }
                if (giQz.\u2007\u200f\u2009\u2002\u2008\u2004\u2004\u2007) {
                    throw null;
                }
                continue;
            }
        }
        return s2;
    }
    
    private static boolean uUuv(final char c) {
        boolean b;
        if ((c >= 'k' && c <= 'o') || (c >= 'K' && c <= 'O') || c == 'r' || c == 'R') {
            b = true;
            if (giQz.\u2007\u200f\u2009\u2002\u2008\u2004\u2004\u2007) {
                throw null;
            }
        }
        else {
            b = false;
        }
        return b;
    }
    
    public int func_175064_b(final char c) {
        return this.kOye["0123456789abcdef".indexOf(c)];
    }
    
    public void func_78275_b(final boolean yrLr) {
        this.YRLr = yrLr;
    }
    
    public boolean func_78260_a() {
        return this.YRLr;
    }
    
    private int EMec(final String s, final int n) {
        final int length = s.length();
        int n2 = 0;
        int i = 0;
        int n3 = -1;
        while (i < length) {
            final char char1 = s.charAt(i);
            Label_0222: {
                switch (char1) {
                    case 10: {
                        --i;
                        if (giQz.\u2007\u200f\u2009\u2002\u2008\u2004\u2004\u2007) {
                            throw null;
                        }
                        break Label_0222;
                    }
                    case 167: {
                        if (i >= length - 1) {
                            break Label_0222;
                        }
                        ++i;
                        final char char2 = s.charAt(i);
                        if (char2 != 'l' && char2 != 'L') {
                            if ((char2 == 'r' || char2 == 'R' || zRDT(char2)) && giQz.\u2007\u200f\u2009\u2002\u2008\u2004\u2004\u2007) {
                                throw null;
                            }
                        }
                        if (giQz.\u2007\u200f\u2009\u2002\u2008\u2004\u2004\u2007) {
                            throw null;
                        }
                        break Label_0222;
                    }
                    case 32: {
                        n3 = i;
                        break;
                    }
                }
                n2 += this.getStringWidth(Character.toString(char1));
            }
            if (char1 == '\n') {
                n3 = ++i;
                if (giQz.\u2007\u200f\u2009\u2002\u2008\u2004\u2004\u2007) {
                    throw null;
                }
                break;
            }
            else if (n2 > n) {
                if (giQz.\u2007\u200f\u2009\u2002\u2008\u2004\u2004\u2007) {
                    throw null;
                }
                break;
            }
            else {
                ++i;
                if (giQz.\u2007\u200f\u2009\u2002\u2008\u2004\u2004\u2007) {
                    throw null;
                }
                continue;
            }
        }
        int n4;
        if (i != length && n3 != -1 && n3 < i) {
            n4 = n3;
            if (giQz.\u2007\u200f\u2009\u2002\u2008\u2004\u2004\u2007) {
                throw null;
            }
        }
        else {
            n4 = i;
        }
        return n4;
    }
    
    private static boolean zRDT(final char c) {
        boolean b;
        if ((c >= '0' && c <= '9') || (c >= 'a' && c <= 'f') || (c >= 'A' && c <= 'F')) {
            b = true;
            if (giQz.\u2007\u200f\u2009\u2002\u2008\u2004\u2004\u2007) {
                throw null;
            }
        }
        else {
            b = false;
        }
        return b;
    }
    
    public int func_78263_a(final char c) {
        return this.getStringWidth(Character.toString(c));
    }
    
    public int func_78256_a(final String s) {
        if (s == null) {
            return 0;
        }
        if (s.contains("§")) {
            final String[] array = s.split("§");
            final yfok ehPb = this.EHPb;
            int n = 0;
            int i = 0;
            while (i < array.length) {
                if (array[i].length() <= 0) {
                    if (giQz.\u2007\u200f\u2009\u2002\u2008\u2004\u2004\u2007) {
                        throw null;
                    }
                }
                else {
                    n += ehPb.JuED(array[i]);
                    if (giQz.\u2007\u200f\u2009\u2002\u2008\u2004\u2004\u2007) {
                        throw null;
                    }
                }
                ++i;
                if (giQz.\u2007\u200f\u2009\u2002\u2008\u2004\u2004\u2007) {
                    throw null;
                }
            }
            return n / 2;
        }
        return this.EHPb.JuED(s) / 2;
    }
    
    public void hQSM(final Font font, final boolean b, final int n) {
        synchronized (this) {
            this.EHPb = new yfok(font, b, n);
            this.cXAL = new yfok(font.deriveFont(1), b, n);
            this.MxXf = new yfok(font.deriveFont(2), b, n);
            this.Ytce = new yfok(font.deriveFont(3), b, n);
            this.FONT_HEIGHT = this.cxUJ();
            // monitorexit(this)
            if (giQz.\u2007\u200f\u2009\u2002\u2008\u2004\u2004\u2007) {
                throw null;
            }
        }
    }
    
    public yfok iCuI() {
        return this.EHPb;
    }
    
    public String KtJZ() {
        return this.EHPb.HtRp().getFontName();
    }
    
    public int LOZm() {
        return this.EHPb.HtRp().getSize();
    }
    
    public List<String> GWXr(final String s, final double n) {
        final ArrayList<String> list = new ArrayList<String>();
        if (this.getStringWidth(s) > n) {
            final String[] array = s.split(" ");
            String string = "";
            char c = '\uffff';
            final String[] array2 = array;
            final int length = array2.length;
            int i = 0;
            while (i < length) {
                final String s2 = array2[i];
                int j = 0;
                while (j < s2.toCharArray().length) {
                    if (s2.toCharArray()[j] == '§' && j < s2.toCharArray().length - 1) {
                        c = s2.toCharArray()[j + 1];
                    }
                    ++j;
                    if (giQz.\u2007\u200f\u2009\u2002\u2008\u2004\u2004\u2007) {
                        throw null;
                    }
                }
                if (this.getStringWidth(string + s2 + " ") < n) {
                    string = string + s2 + " ";
                    if (giQz.\u2007\u200f\u2009\u2002\u2008\u2004\u2004\u2007) {
                        throw null;
                    }
                }
                else {
                    list.add(string);
                    String s3;
                    if (c == -1) {
                        s3 = s2 + " ";
                        if (giQz.\u2007\u200f\u2009\u2002\u2008\u2004\u2004\u2007) {
                            throw null;
                        }
                    }
                    else {
                        s3 = "§" + c + s2 + " ";
                    }
                    string = s3;
                }
                ++i;
                if (giQz.\u2007\u200f\u2009\u2002\u2008\u2004\u2004\u2007) {
                    throw null;
                }
            }
            if (!string.equals("")) {
                if (this.getStringWidth(string) < n) {
                    final ArrayList<String> list2 = list;
                    String s4;
                    if (c == -1) {
                        s4 = string + " ";
                        if (giQz.\u2007\u200f\u2009\u2002\u2008\u2004\u2004\u2007) {
                            throw null;
                        }
                    }
                    else {
                        s4 = "§" + c + string + " ";
                    }
                    list2.add(s4);
                    if (giQz.\u2007\u200f\u2009\u2002\u2008\u2004\u2004\u2007) {
                        throw null;
                    }
                }
                else {
                    final Iterator<String> iterator = this.MiES(string, n).iterator();
                    while (iterator.hasNext()) {
                        list.add(iterator.next());
                        if (giQz.\u2007\u200f\u2009\u2002\u2008\u2004\u2004\u2007) {
                            throw null;
                        }
                    }
                }
            }
            if (giQz.\u2007\u200f\u2009\u2002\u2008\u2004\u2004\u2007) {
                throw null;
            }
        }
        else {
            list.add(s);
        }
        return list;
    }
    
    public List<String> MiES(final String s, final double n) {
        final ArrayList<String> list = new ArrayList<String>();
        String string = "";
        char c = '\uffff';
        int i = 0;
        while (i < s.toCharArray().length) {
            final char c2 = s.toCharArray()[i];
            if (c2 == '§' && i < s.toCharArray().length - 1) {
                c = s.toCharArray()[i + 1];
            }
            if (this.getStringWidth(string + c2) < n) {
                string += c2;
                if (giQz.\u2007\u200f\u2009\u2002\u2008\u2004\u2004\u2007) {
                    throw null;
                }
            }
            else {
                list.add(string);
                String s2;
                if (c == -1) {
                    s2 = String.valueOf(c2);
                    if (giQz.\u2007\u200f\u2009\u2002\u2008\u2004\u2004\u2007) {
                        throw null;
                    }
                }
                else {
                    s2 = "§" + c + String.valueOf(c2);
                }
                string = s2;
            }
            ++i;
            if (giQz.\u2007\u200f\u2009\u2002\u2008\u2004\u2004\u2007) {
                throw null;
            }
        }
        if (!string.equals("")) {
            list.add(string);
        }
        return list;
    }
    
    private void EZFD(final double n, final double n2, final double n3, final double n4, final float n5) {
        GL11.glDisable(3553);
        GL11.glLineWidth(n5);
        GL11.glBegin(1);
        GL11.glVertex2d(n, n2);
        GL11.glVertex2d(n3, n4);
        GL11.glEnd();
        GL11.glEnable(3553);
    }
    
    public boolean JkrA() {
        boolean b;
        if (this.EHPb.xTYq() && this.cXAL.xTYq() && this.MxXf.xTYq() && this.Ytce.xTYq()) {
            b = true;
            if (giQz.\u2007\u200f\u2009\u2002\u2008\u2004\u2004\u2007) {
                throw null;
            }
        }
        else {
            b = false;
        }
        return b;
    }
    
    public void DhTH(final boolean b) {
        this.EHPb.gVYg(b);
        this.cXAL.gVYg(b);
        this.MxXf.gVYg(b);
        this.Ytce.gVYg(b);
    }
    
    private void WGfM() {
        int i = 0;
        while (i < 32) {
            final int n = (i >> 3 & 0x1) * 85;
            int n2 = (i >> 2 & 0x1) * 170 + n;
            int n3 = (i >> 1 & 0x1) * 170 + n;
            int n4 = (i >> 0 & 0x1) * 170 + n;
            if (i == 6) {
                n2 += 85;
            }
            if (i >= 16) {
                n2 /= 4;
                n3 /= 4;
                n4 /= 4;
            }
            this.kOye[i] = ((n2 & 0xFF) << 16 | (n3 & 0xFF) << 8 | (n4 & 0xFF));
            ++i;
            if (giQz.\u2007\u200f\u2009\u2002\u2008\u2004\u2004\u2007) {
                throw null;
            }
        }
    }
    
    public String func_78269_a(final String s, final int n) {
        return this.trimStringToWidth(s, n, false);
    }
    
    public String func_78262_a(final String s, final int n, final boolean b) {
        final StringBuilder sb = new StringBuilder();
        int n2 = 0;
        int n3;
        if (b) {
            n3 = s.length() - 1;
            if (giQz.\u2007\u200f\u2009\u2002\u2008\u2004\u2004\u2007) {
                throw null;
            }
        }
        else {
            n3 = 0;
        }
        final int n4 = n3;
        int n5;
        if (b) {
            n5 = -1;
            if (giQz.\u2007\u200f\u2009\u2002\u2008\u2004\u2004\u2007) {
                throw null;
            }
        }
        else {
            n5 = 1;
        }
        final int n6 = n5;
        int n7 = n4;
        while (n7 >= 0 && n7 < s.length() && n2 < n) {
            final char char1 = s.charAt(n7);
            final int stringWidth = this.getStringWidth(Character.toString(char1));
            if (stringWidth < 0) {
                if (giQz.\u2007\u200f\u2009\u2002\u2008\u2004\u2004\u2007) {
                    throw null;
                }
            }
            else {
                n2 += stringWidth;
            }
            if (n2 > n) {
                if (giQz.\u2007\u200f\u2009\u2002\u2008\u2004\u2004\u2007) {
                    throw null;
                }
                break;
            }
            else {
                if (b) {
                    sb.insert(0, char1);
                    if (giQz.\u2007\u200f\u2009\u2002\u2008\u2004\u2004\u2007) {
                        throw null;
                    }
                }
                else {
                    sb.append(char1);
                }
                n7 += n6;
                if (giQz.\u2007\u200f\u2009\u2002\u2008\u2004\u2004\u2007) {
                    throw null;
                }
                continue;
            }
        }
        return sb.toString();
    }
    
    public List<String> func_78271_c(final String s, final int n) {
        return Arrays.asList((String[])this.wrapFormattedStringToWidth(s, n).split("\n"));
    }
    
    protected String func_78280_d(final String s, final int n) {
        final int eMec = this.EMec(s, n);
        if (s.length() <= eMec) {
            return s;
        }
        final String substring = s.substring(0, eMec);
        final char char1 = s.charAt(eMec);
        boolean b;
        if (char1 == ' ' || char1 == '\n') {
            b = true;
            if (giQz.\u2007\u200f\u2009\u2002\u2008\u2004\u2004\u2007) {
                throw null;
            }
        }
        else {
            b = false;
        }
        final boolean b2 = b;
        final StringBuilder append = new StringBuilder().append(BukF(substring));
        final int n2 = eMec;
        int n3;
        if (b2) {
            n3 = 1;
            if (giQz.\u2007\u200f\u2009\u2002\u2008\u2004\u2004\u2007) {
                throw null;
            }
        }
        else {
            n3 = 0;
        }
        return substring + "\n" + this.wrapFormattedStringToWidth(append.append(s.substring(n2 + n3)).toString(), n);
    }
    
    public Color uXrE(final int n, final float n2) {
        return new Color((n >> 16) / 255.0f, (n >> 8 & 0xFF) / 255.0f, (n & 0xFF) / 255.0f, n2);
    }
    
    private String zbaE() {
        String string = "0123456789abcdefklmnor";
        int i = 0;
        while (i < this.fyGC.length) {
            if (this.fyGC[i] != null) {
                string += (char)i;
            }
            ++i;
            if (giQz.\u2007\u200f\u2009\u2002\u2008\u2004\u2004\u2007) {
                throw null;
            }
        }
        return string;
    }
    
    public void func_110549_a(final IResourceManager resourceManager) {
    }
}
