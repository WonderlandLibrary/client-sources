package ez.h.utils;

import java.util.stream.*;
import java.io.*;
import javax.sound.sampled.*;
import java.awt.*;
import java.text.*;
import java.math.*;
import ez.h.event.events.*;
import ez.h.*;
import ez.h.event.*;

public class Utils
{
    public static float timer;
    static bib mc;
    public static int packetsCounter;
    public static int rotationCounter;
    
    public static boolean isBlockAboveHead() {
        return Utils.mc.f.a((vg)Utils.mc.h, new bhb(Utils.mc.h.p - 0.3, Utils.mc.h.q + Utils.mc.h.by() + 0.30000001192092896, Utils.mc.h.r + 0.3, Utils.mc.h.p + 0.3, Utils.mc.h.q + (Utils.mc.h.z ? 2.5 : 1.5), Utils.mc.h.r - 0.3)).isEmpty();
    }
    
    public static float getSpeed() {
        return (float)Math.abs(Math.hypot(Utils.mc.h.s, Utils.mc.h.u));
    }
    
    public static int[] range(final int n, final int n2) {
        return IntStream.range(n, n2).toArray();
    }
    
    public static float getDirection() {
        float v = Utils.mc.h.v;
        float n = 0.0f;
        if (Utils.mc.h.e.moveForward > 0.0f) {
            n = 1.0f;
        }
        if (Utils.mc.h.e.moveForward < 0.0f) {
            n = -1.0f;
        }
        if (n == 0.0f) {
            if (Utils.mc.h.e.a > 0.0f) {
                v -= 90.0f;
            }
            if (Utils.mc.h.e.a < 0.0f) {
                v += 90.0f;
            }
        }
        else {
            if (Utils.mc.h.e.a > 0.0f) {
                v -= 45.0f * n;
            }
            if (Utils.mc.h.e.a < 0.0f) {
                v += 45.0f * n;
            }
        }
        if (n < 0.0f) {
            v -= 180.0f;
        }
        return (float)Math.toRadians(v);
    }
    
    public static boolean doesHotbarHaveAxe() {
        for (int i = 0; i < 9; ++i) {
            if (!Utils.mc.h.bv.a(i).isEmpty() && bib.z().h.bv.a(i).c() instanceof agy) {
                return true;
            }
        }
        return false;
    }
    
    public static Color getGradientOffset(final Color color, final Color color2, double n, final int n2) {
        if (n > 1.0) {
            final double n3 = n % 1.0;
            n = (((int)n % 2 == 0) ? n3 : (1.0 - n3));
        }
        final double n4 = 1.0 - n;
        return new Color((int)(color.getRed() * n4 + color2.getRed() * n), (int)(color.getGreen() * n4 + color2.getGreen() * n), (int)(color.getBlue() * n4 + color2.getBlue() * n), n2);
    }
    
    public static String getRimNum(final va va) {
        va.a();
        String s = "";
        if (va.c() == 1) {
            s += "II";
        }
        else if (va.c() == 2) {
            s += "III";
        }
        else if (va.c() == 3) {
            s += "IV";
        }
        else if (va.c() == 4) {
            s += "V";
        }
        else if (va.c() == 5) {
            s += "VI";
        }
        else if (va.c() == 6) {
            s += "VII";
        }
        else if (va.c() == 7) {
            s += "VIII";
        }
        else if (va.c() == 8) {
            s += "IX";
        }
        else if (va.c() == 9) {
            s += "X";
        }
        else if (va.c() > 9) {
            s = s + "" + (va.c() + 1);
        }
        return s;
    }
    
    public static String readFile(final File file) {
        final StringBuilder sb = new StringBuilder();
        try {
            String line;
            while ((line = new BufferedReader(new InputStreamReader(new FileInputStream(file))).readLine()) != null) {
                sb.append(line).append((char)(0xAA ^ 0xA0));
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return sb.toString();
    }
    
    public static synchronized void playSound(final String s, final float value, final boolean b) {
        final Clip clip;
        new Thread(() -> {
            try {
                AudioSystem.getClip();
                clip.open(AudioSystem.getAudioInputStream(new BufferedInputStream(Utils.class.getResourceAsStream("/assets/minecraft/wild/sounds/" + s))));
                ((FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN)).setValue(value);
                clip.start();
                if (b) {
                    clip.stop();
                }
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }).start();
    }
    
    public static float getDirection(final float n) {
        float n2 = n;
        float n3 = 0.0f;
        if (Utils.mc.h.e.moveForward > 0.0f) {
            n3 = 1.0f;
        }
        if (Utils.mc.h.e.moveForward < 0.0f) {
            n3 = -1.0f;
        }
        if (n3 == 0.0f) {
            if (Utils.mc.h.e.a > 0.0f) {
                n2 -= 90.0f;
            }
            if (Utils.mc.h.e.a < 0.0f) {
                n2 += 90.0f;
            }
        }
        else {
            if (Utils.mc.h.e.a > 0.0f) {
                n2 -= 45.0f * n3;
            }
            if (Utils.mc.h.e.a < 0.0f) {
                n2 += 45.0f * n3;
            }
        }
        return (float)Math.toRadians(n2);
    }
    
    public static int getSwordAtHotbar() {
        for (int i = 0; i < 9; ++i) {
            if (bib.z().h.bv.a(i).c() instanceof ajy) {
                return i;
            }
        }
        return 1;
    }
    
    public static boolean canRotate() {
        return Utils.rotationCounter == 0;
    }
    
    public static Font getFontFromTTF(final nf nf, final float n, final int n2) {
        try {
            return Font.createFont(n2, bib.z().O().a(nf).b()).deriveFont(n);
        }
        catch (Exception ex) {
            return null;
        }
    }
    
    public static boolean isAimAtMe(final vg vg, final float n) {
        return Math.abs(rk.g(getYawToEntity(vg, (vg)bib.z().h)) - rk.g(vg.v + (vg.bc() - vg.v))) <= n;
    }
    
    public static String format(final String s, final double n) {
        final DecimalFormat decimalFormat = new DecimalFormat(s);
        decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
        return decimalFormat.format(n);
    }
    
    public static float getYawToEntity(final vg vg, final vg vg2) {
        return (float)(Math.toDegrees(Math.atan2(vg.r - vg2.r, vg.p - vg2.p)) + 90.0);
    }
    
    public static Color liquidSlowly(final long n, final int n2, final float n3, final float n4) {
        final Color color = new Color(Color.HSBtoRGB((n + n2 * -3000000.0f) / 2.0f / 1.0E9f, n3, n4));
        return new Color(color.getRed() / 255.0f * 1.0f, color.getGreen() / 255.0f * 1.0f, color.getBlue() / 255.0f * 1.0f, color.getAlpha() / 255.0f);
    }
    
    public static boolean isBlockEdge(final vp vp) {
        return Utils.mc.f.a((vg)vp, vp.bw().d(0.0, -0.5, 0.0).c(0.001, 0.0, 0.001)).isEmpty() && vp.z;
    }
    
    public static boolean isCyrillic(final String s) {
        final char[] charArray = s.toCharArray();
        for (int length = charArray.length, i = 0; i < length; ++i) {
            if (Character.UnicodeBlock.of(charArray[i]) == Character.UnicodeBlock.CYRILLIC) {
                return true;
            }
        }
        return false;
    }
    
    public static void setTimer(final float speed) {
        Utils.timer = ((speed == 0.0f) ? 50.0f : (50.0f / speed));
        Utils.mc.Y.speed = speed;
    }
    
    static {
        Utils.packetsCounter = 0;
        Utils.timer = 1.0f;
        Utils.mc = bib.z();
    }
    
    @EventTarget
    public void onPacketSent(final EventPacketSend eventPacketSend) {
        if (eventPacketSend.getPacket() instanceof lk) {
            if (Main.getFeatureByName("Timer").isEnabled()) {
                if (Utils.packetsCounter <= (0x33 ^ 0xF)) {
                    ++Utils.packetsCounter;
                }
            }
            else if (Utils.packetsCounter > 0) {
                --Utils.packetsCounter;
            }
        }
    }
    
    public static int getAxeH() {
        for (int i = 0; i < 9; ++i) {
            if (Utils.mc.h.bv.a(i).c() instanceof agy) {
                return i;
            }
        }
        return Utils.mc.h.bv.d;
    }
    
    public static Color skyRainbow(final int n, final float n2, final float n3) {
        final double n4;
        return Color.getHSBColor(((float)((n4 = Math.ceil((double)(Main.millis + (long)(n * 109.0f))) / 5.0 % 360.0) / 360.0) < 0.5) ? (-(float)(n4 / 360.0)) : ((float)(n4 / 360.0)), n2, n3);
    }
    
    public static void setMotion(final double n) {
        Utils.mc.h.s = -Math.sin(getDirection()) * n;
        Utils.mc.h.u = Math.cos(getDirection()) * n;
    }
    
    public static String readInputStream(final InputStream inputStream) {
        final StringBuilder sb = new StringBuilder();
        try {
            String line;
            while ((line = new BufferedReader(new InputStreamReader(inputStream)).readLine()) != null) {
                sb.append(line).append((char)(0x2 ^ 0x8));
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return sb.toString();
    }
}
