package net.futureclient.client;

import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;
import java.util.Collection;
import java.util.ArrayList;
import java.util.List;
import java.awt.Font;
import net.minecraft.client.renderer.texture.DynamicTexture;

public class KH extends PG
{
    public DynamicTexture A;
    private String j;
    public DynamicTexture K;
    public DynamicTexture M;
    private int[] d;
    public dG[] a;
    public dG[] D;
    public dG[] k;
    
    public KH(final Font font, final boolean b, final boolean b2) {
        final int n = 256;
        super(font, b, b2);
        this.k = new dG[n];
        this.a = new dG[256];
        this.D = new dG[256];
        final String j = "0123456789abcdefklmnor";
        this.d = new int[32];
        this.j = j;
        this.e();
        this.M();
    }
    
    private void e() {
        int i = 0;
        int n = 0;
        while (i < 32) {
            final int n2 = (n >> 3 & 0x1) * 85;
            int n3 = (n >> 2 & 0x1) * 170 + n2;
            int n4 = (n >> 1 & 0x1) * 170 + n2;
            int n5 = (n & 0x1) * 170 + n2;
            if (n == 6) {
                n3 += 85;
            }
            if (n >= 16) {
                n3 /= 4;
                n4 /= 4;
                n5 /= 4;
            }
            final int[] d = this.d;
            final int n6 = n;
            final int n7 = (n3 & 0xFF) << 16 | (n4 & 0xFF) << 8 | (n5 & 0xFF);
            ++n;
            d[n6] = n7;
            i = n;
        }
    }
    
    public float e(final String s, final float n, final float n2, final int n3) {
        return this.M(s, n - this.M(s) / 2.0f, n2, n3);
    }
    
    @Override
    public void e(final boolean b) {
        super.e(b);
        this.M();
    }
    
    public List<String> e(final String s, final double n) {
        final ArrayList<String> list = new ArrayList<String>();
        StringBuilder sb = new StringBuilder();
        char c = '\uffff';
        final char[] charArray = s.toCharArray();
        int i = 0;
        int n2 = 0;
        while (i < charArray.length) {
            final char c2;
            if ((c2 = charArray[n2]) == '§' && n2 < charArray.length - 1) {
                c = charArray[n2 + 1];
            }
            if (this.M(new StringBuilder().insert(0, sb.toString()).append(c2).toString()) < n) {
                sb.append(c2);
            }
            else {
                list.add(sb.toString());
                sb = new StringBuilder(new StringBuilder().insert(0, "§").append(c).append(String.valueOf(c2)).toString());
            }
            i = ++n2;
        }
        if (sb.length() > 0) {
            list.add(sb.toString());
        }
        return list;
    }
    
    @Override
    public void M(final Font font) {
        super.M(font);
        this.M();
    }
    
    public List<String> M(final String s, final double n) {
        final ArrayList<String> list = new ArrayList<String>();
        if (this.M(s) > n) {
            final String[] split = s.split(" ");
            String s2 = "";
            char c = '\uffff';
            final String[] array;
            final int length = (array = split).length;
            int i = 0;
            int n2 = 0;
            while (i < length) {
                final String s3 = array[n2];
                int j = 0;
                int n3 = 0;
                while (j < s3.toCharArray().length) {
                    if (s3.toCharArray()[n3] == '§' && n3 < s3.toCharArray().length - 1) {
                        c = s3.toCharArray()[n3 + 1];
                    }
                    j = ++n3;
                }
                if (this.M(new StringBuilder().insert(0, s2).append(s3).append(" ").toString()) < n) {
                    s2 = new StringBuilder().insert(0, s2).append(s3).append(" ").toString();
                }
                else {
                    list.add(s2);
                    s2 = new StringBuilder().insert(0, "§").append(c).append(s3).append(" ").toString();
                }
                i = ++n2;
            }
            if (s2.length() > 0) {
                final double n4 = dcmpg((double)this.M(s2), n);
                final ArrayList<String> list2 = list;
                if (n4 < 0) {
                    list2.add("§" + c + s2 + " ");
                }
                else {
                    list2.addAll((Collection<?>)this.e(s2, n));
                }
            }
        }
        else {
            list.add(s);
        }
        return list;
    }
    
    public float M(final String s, final float n, final float n2, final int n3) {
        return this.M(s, n, n2, n3, false);
    }
    
    private void M() {
        this.M = this.M(this.j.deriveFont(1), this.D, this.k, this.k);
        this.A = this.M(this.j.deriveFont(2), this.D, this.k, this.a);
        this.K = this.M(this.j.deriveFont(3), this.D, this.k, this.D);
    }
    
    @Override
    public void M(final boolean b) {
        super.M(b);
        this.M();
    }
    
    public float M(final String s, final double n, final double n2, final int n3) {
        return Math.max(this.M(s, n + 1.0, n2 + 1.0, n3, true), this.M(s, n, n2, n3, false));
    }
    
    public float M(final String s, double n, double n2, int n3, final boolean b) {
        --n;
        if (s == null) {
            return 0.0f;
        }
        if (n3 == 553648127) {
            n3 = 16777215;
        }
        if ((n3 & 0xFC000000) == 0x0) {
            n3 |= 0xFF000000;
        }
        if (b) {
            n3 = ((n3 & 0xFCFCFC) >> 2 | (n3 & 0xFF000000));
        }
        Object array = this.K;
        final float n4 = (n3 >> 24 & 0xFF) / 255.0f;
        int n5 = 0;
        int n6 = 0;
        n *= 0.0;
        n2 = (n2 - 0.0) * 0.0;
        GL11.glPushMatrix();
        final double n7 = 0.0;
        GlStateManager.scale(n7, n7, n7);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 771);
        GlStateManager.color((n3 >> 16 & 0xFF) / 255.0f, (n3 >> 8 & 0xFF) / 255.0f, (n3 & 0xFF) / 255.0f, n4);
        final int length = s.length();
        GlStateManager.enableTexture2D();
        GlStateManager.bindTexture(this.d.getGlTextureId());
        GL11.glBindTexture(3553, this.d.getGlTextureId());
        int i = 0;
        int n8 = 0;
        while (i < length) {
            final char char1;
            if ((char1 = s.charAt(n8)) == '§') {
                int n9 = 21;
                int index;
                try {
                    n9 = (index = "0123456789abcdefklmnor".indexOf(s.charAt(n8 + 1)));
                }
                catch (Exception ex) {
                    index = n9;
                    ex.printStackTrace();
                }
                if (index < 16) {
                    n5 = 0;
                    n6 = 0;
                    GlStateManager.bindTexture(this.d.getGlTextureId());
                    array = this.K;
                    if (n9 < 0 || n9 > 15) {
                        n9 = 15;
                    }
                    if (b) {
                        n9 += 16;
                    }
                    final int n10;
                    GlStateManager.color(((n10 = this.d[n9]) >> 16 & 0xFF) / 255.0f, (n10 >> 8 & 0xFF) / 255.0f, (n10 & 0xFF) / 255.0f, n4);
                }
                else if (n9 != 16) {
                    if (n9 == 17) {
                        n5 = 1;
                        if (n6 != 0) {
                            GlStateManager.bindTexture(this.K.getGlTextureId());
                            array = this.D;
                        }
                        else {
                            GlStateManager.bindTexture(this.M.getGlTextureId());
                            array = this.k;
                        }
                    }
                    else if (n9 != 18) {
                        if (n9 != 19) {
                            if (n9 == 20) {
                                n6 = 1;
                                if (n5 != 0) {
                                    GlStateManager.bindTexture(this.K.getGlTextureId());
                                    array = this.D;
                                }
                                else {
                                    GlStateManager.bindTexture(this.A.getGlTextureId());
                                    array = this.a;
                                }
                            }
                            else if (n9 == 21) {
                                n5 = 0;
                                n6 = 0;
                                GlStateManager.color((n3 >> 16 & 0xFF) / 255.0f, (n3 >> 8 & 0xFF) / 255.0f, (n3 & 0xFF) / 255.0f, n4);
                                GlStateManager.bindTexture(this.d.getGlTextureId());
                                array = this.K;
                            }
                        }
                    }
                }
                ++n8;
            }
            else if (char1 < array.length && char1 > '\0') {
                GL11.glBegin(4);
                this.M(array, char1, (float)n, (float)n2);
                GL11.glEnd();
                n += array[char1].M - 8 + this.M;
            }
            i = ++n8;
        }
        GL11.glHint(3155, 4352);
        GL11.glPopMatrix();
        return (float)n / 2.0f;
    }
    
    @Override
    public int M(final String s) {
        if (s == null) {
            return 0;
        }
        int n = 0;
        Object array = this.K;
        int n2 = 0;
        int i = 0;
        int n3 = 0;
        while (i < s.length()) {
            final char char1;
            if ((char1 = s.charAt(n3)) == '§') {
                if (s.length() > n3 + 1) {
                    switch (Character.toLowerCase(s.charAt(n3 + 1))) {
                        case 'l':
                            n2 = 1;
                            array = this.k;
                            break;
                        case 'o':
                            if (n2 != 0) {
                                array = this.D;
                                break;
                            }
                            array = this.a;
                            break;
                        case 'r':
                            n2 = 0;
                            array = this.K;
                            break;
                    }
                }
                ++n3;
            }
            else if (char1 < array.length) {
                n += array[char1].M - 8 + this.M;
            }
            i = ++n3;
        }
        return n / 2;
    }
    
    private void M(final double n, final double n2, final double n3, final double n4, final float n5) {
        GL11.glDisable(3553);
        GL11.glLineWidth(n5);
        GL11.glBegin(1);
        GL11.glVertex2d(n, n2);
        GL11.glVertex2d(n3, n4);
        GL11.glEnd();
        GL11.glEnable(3553);
    }
}
