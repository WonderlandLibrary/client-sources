package com.sun.jna.z.a.f;

import java.util.*;
import java.awt.*;
import org.lwjgl.opengl.*;
import com.sun.jna.z.a.e.a.a.a.f.*;
import java.util.zip.*;

public class e
{
    public HashMap<String, l> a;
    private static final String[] b;
    
    public e() {
        this.a = new HashMap<String, l>();
    }
    
    public void a(final Color a, final float a, final float a, final String a, final float a) {
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glEnable(3553);
        GL11.glScaled((double)a, (double)a, (double)a);
        final l a2 = this.a(a);
        a.a(new Color(0, 0, 0, 150));
        l.a(a2, a + 1.0f, a + 1.0f, 32.0f, 32.0f);
        a.a(a);
        l.a(a2, a, a, 32.0f, 32.0f);
        GL11.glScaled(1.0, 1.0, 1.0);
        GL11.glDisable(3553);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }
    
    public void a(final float a, final float a, final float a, final float a, final float a, final float a, final String a, final float a) {
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glScaled((double)a, (double)a, (double)a);
        final l a2 = this.a(a);
        GL11.glColor4f(a, a, a, a);
        l.a(a2, a, a, 32.0f, 32.0f);
        GL11.glDisable(3042);
        GL11.glEnable(3553);
        GL11.glPopMatrix();
    }
    
    public l a(String a) {
        final int b = f.b ? 1 : 0;
        a = a.toLowerCase();
        final int a2 = b;
        Label_0044: {
            HashMap<String, l> hashMap2 = null;
            String s2 = null;
            Label_0028: {
                HashMap<String, l> hashMap;
                String s;
                try {
                    hashMap = (hashMap2 = this.a);
                    s = (s2 = a);
                    if (a2 != 0) {
                        return hashMap2.get(s2);
                    }
                    final boolean b2 = hashMap.containsKey(s);
                    if (b2) {
                        break Label_0028;
                    }
                    break Label_0044;
                }
                catch (Exception ex) {
                    throw ex;
                }
                try {
                    final boolean b2 = hashMap.containsKey(s);
                    if (!b2) {
                        break Label_0044;
                    }
                    hashMap2 = this.a;
                    s2 = a;
                }
                catch (Exception ex2) {
                    throw ex2;
                }
            }
            return hashMap2.get(s2);
        }
        l a3 = null;
        try {
            final ZipFile zipFile2;
            final ZipFile zipFile;
            final ZipFile a4 = zipFile = (zipFile2 = new ZipFile(e.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()));
            final StringBuilder sb = new StringBuilder();
            final String[] a5 = e.b;
            a3 = new l(zipFile2.getInputStream(zipFile.getEntry(sb.append(a5[0]).append(a).append(a5[1]).toString())));
            a4.close();
        }
        catch (Exception a6) {
            a6.printStackTrace();
        }
        l a7 = null;
        Label_0150: {
            l l;
            try {
                l = (a7 = a3);
                if (a2 != 0) {
                    return a7;
                }
                if (l != null) {
                    break Label_0150;
                }
                return null;
            }
            catch (Exception ex3) {
                throw ex3;
            }
            try {
                if (l == null) {
                    return null;
                }
                this.a.put(a, a3);
                a7 = this.a(a);
            }
            catch (Exception ex4) {
                throw ex4;
            }
        }
        return a7;
    }
    
    static {
        final String[] b2 = new String[2];
        int n = 0;
        final String s;
        final int length = (s = "\u0000v5²\u0004Zo6\u00fa").length();
        int char1 = 4;
        int n2 = -1;
        Label_0021: {
            break Label_0021;
            do {
                char1 = s.charAt(n2);
                ++n2;
                final String s2 = s;
                final int n3 = n2;
                final char[] charArray = s2.substring(n3, n3 + char1).toCharArray();
                int length2;
                int n5;
                final int n4 = n5 = (length2 = charArray.length);
                int n6 = 0;
                while (true) {
                    Label_0186: {
                        if (n4 > 1) {
                            break Label_0186;
                        }
                        length2 = (n5 = n6);
                        do {
                            final char c = charArray[n5];
                            char c2 = '\0';
                            switch (n6 % 7) {
                                case 0: {
                                    c2 = 't';
                                    break;
                                }
                                case 1: {
                                    c2 = '\u001f';
                                    break;
                                }
                                case 2: {
                                    c2 = 'X';
                                    break;
                                }
                                case 3: {
                                    c2 = '\u009d';
                                    break;
                                }
                                case 4: {
                                    c2 = '\u00cb';
                                    break;
                                }
                                case 5: {
                                    c2 = '\u001a';
                                    break;
                                }
                                default: {
                                    c2 = 'B';
                                    break;
                                }
                            }
                            charArray[length2] = (char)(c ^ c2);
                            ++n6;
                        } while (n4 == 0);
                    }
                    if (n4 > n6) {
                        continue;
                    }
                    break;
                }
                b2[n++] = new String(charArray).intern();
            } while ((n2 += char1) < length);
        }
        b = b2;
    }
}
