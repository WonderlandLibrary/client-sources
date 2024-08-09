package ez.h.managers.altmanager;

import ez.h.ui.mainmenu.*;
import ez.h.*;
import java.io.*;
import ez.h.ui.fonts.*;
import org.lwjgl.input.*;
import ez.h.utils.*;
import java.awt.*;
import org.lwjgl.opengl.*;
import ez.h.animengine.*;
import java.util.*;

public class AltManagerGUI extends blk
{
    float scrollFactor;
    bja add;
    HashMap<int[], Alt> altMap;
    bja back;
    bje password;
    public static String status;
    Alt selectedAlt;
    boolean isAddWindow;
    float scaleFactor;
    float scrollVelocity;
    bje login;
    
    public boolean isHovered(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        return n >= n3 && n <= n3 + n5 && n2 >= n4 && n2 <= n4 + n6;
    }
    
    public AltManagerGUI() {
        this.altMap = new HashMap<int[], Alt>();
        this.isAddWindow = false;
        this.scaleFactor = 0.0f;
        this.scrollVelocity = 0.0f;
    }
    
    public void b() {
        final bit bit = new bit(this.j);
        this.add = new GuiMainMenuButton(0x59 ^ 0x53, bit.a() / 2 - (0x40 ^ 0x7C), bit.b() / 2 + (0xA9 ^ 0xB7), 0x2B ^ 0x53, 0x5C ^ 0x48, "login");
        this.back = new GuiMainMenuButton(0x7D ^ 0x76, bit.a() / 2 - (0x3F ^ 0x3), bit.b() / 2 + (0x32 ^ 0xE), 0x73 ^ 0xB, 0x72 ^ 0x66, "back");
        this.login = new bje(2, this.j.k, bit.a() / 2 - (0xB2 ^ 0x8E), bit.b() / 2 - (0x56 ^ 0x7E), 0x37 ^ 0x4F, 0xA6 ^ 0xB2);
        this.password = new bje(2, this.j.k, bit.a() / 2 - (0xB9 ^ 0x85), bit.b() / 2 - (0x85 ^ 0x8F), 0xCA ^ 0xB2, 0x4C ^ 0x58);
        this.n.add(this.add);
        this.n.add(this.back);
        this.scaleFactor = 0.0f;
        this.scrollFactor = 0.0f;
        AltManagerGUI.status = "WILD Alt manager";
        this.selectedAlt = null;
        this.addButtons();
    }
    
    public void a(final bja bja) throws IOException {
        if (bja.k == 3 && this.selectedAlt != null) {
            Main.alts.remove(this.selectedAlt);
        }
        if (bja.k == 2) {
            this.isAddWindow = true;
        }
        if (bja.k == 1) {
            this.j.a((blk)new AltManagerDirect());
        }
        if (bja.k == 0) {
            if (this.selectedAlt != null) {
                new AuthenticatorThread(this.selectedAlt.getName(), this.selectedAlt.getPassword()).start();
            }
            this.j.a((blk)null);
        }
        if (bja.k == 4) {
            this.j.a((blk)null);
        }
        if (bja.k == (0x10 ^ 0x1D)) {
            final Alt alt = new Alt("WILD" + net.minecraft.client.main.Main.getHash(), "", Calendar.getInstance().get(5) + "/" + Calendar.getInstance().get(2) + "/" + Calendar.getInstance().get(1));
            if (!alt.name.isEmpty()) {
                new AuthenticatorThread(alt.getName(), alt.getPassword()).start();
            }
        }
        if (bja.k == (0x30 ^ 0x3A)) {
            final Alt alt2 = new Alt(this.login.b(), this.password.b().isEmpty() ? "" : this.password.b(), Calendar.getInstance().get(5) + "/" + Calendar.getInstance().get(2) + "/" + Calendar.getInstance().get(1));
            if (!alt2.name.isEmpty()) {
                new AuthenticatorThread(alt2.getName(), alt2.getPassword()).start();
            }
            Main.alts.add(alt2);
            this.j.a((blk)new AltManagerGUI());
        }
        if (bja.k == (0x3F ^ 0x34)) {
            this.j.a((blk)new AltManagerGUI());
        }
    }
    
    public void a(final boolean b, final int n) {
        super.a(b, n);
    }
    
    public String getPassword(final String s) {
        final StringBuilder sb = new StringBuilder();
        if (s.length() >= 5) {
            sb.append(s, 0, 4);
            for (int i = 4; i < s.length(); ++i) {
                if (i == (0x8 ^ 0x18)) {
                    sb.append("...");
                    break;
                }
                sb.append("*");
            }
        }
        else {
            sb.append(s);
        }
        return sb.toString();
    }
    
    public void a(final int n, final int n2, final int n3) throws IOException {
        if (this.isAddWindow) {
            this.login.a(n, n2, n3);
            this.password.a(n, n2, n3);
        }
        super.a(n, n2, n3);
    }
    
    public void a(int n, int n2, final float n3) {
        final bit bit = new bit(this.j);
        this.add.m = (this.isAddWindow && this.scaleFactor > 0.6);
        this.back.m = (this.isAddWindow && this.scaleFactor > 0.6);
        this.login.a(false);
        this.password.a(false);
        this.login.j = (0xB8 ^ 0xB7);
        this.password.j = (0x57 ^ 0x58);
        this.login.i = 68 + 62 + 19 + 56;
        this.password.i = 169 + 1 - 154 + 189;
        this.add.h = bit.a() / 2 - (0x21 ^ 0x45);
        this.add.i = bit.b() / 2 + (0x4 ^ 0x2C);
        this.back.h = bit.a() / 2 + (0x49 ^ 0x15) - CFontManager.montserrat.getStringWidth("back");
        this.back.i = bit.b() / 2 + (0x63 ^ 0x4B);
        this.login.f = bit.b() / 2 - (0x88 ^ 0xA8);
        this.password.f = bit.b() / 2 + (0xB0 ^ 0xBC);
        this.login.a = bit.a() / 2 - (0x65 ^ 0x4);
        this.password.a = bit.a() / 2 - (0xC8 ^ 0xA9);
        if (!this.isAddWindow) {
            this.scrollVelocity += Mouse.getDWheel() / 8.0f;
            this.scrollFactor += this.scrollVelocity;
        }
        n = (int)ScaleUtils.getScale(n);
        n2 = (int)ScaleUtils.getScale(n2);
        RenderUtils.drawImg(new nf("wild/bgmainmenu.png"), 0.0, 0.0, this.l, this.m);
        final float n4 = bit.b() / 15.0f;
        CFontManager.montserratbig.drawString("WILD", 20.0f, n4, -1);
        final float n5 = n4 + CFontManager.montserratbig.FONT_HEIGHT * 1.2f;
        CFontManager.montserrat.drawString("altmanager", 20.0f, n5, new Color(152 + 158 - 207 + 152, 116 + 250 - 164 + 53, 95 + 226 - 299 + 233, 0x56 ^ 0x2C).getRGB());
        final float n6 = n5 + CFontManager.montserrat.FONT_HEIGHT * 1.2f;
        CFontManager.montserrat.drawString(Main.version, 20.0f, n6, new Color(115 + 35 - 11 + 116, 188 + 67 - 136 + 136, 186 + 217 - 384 + 236, 0x7F ^ 0x5).getRGB());
        CFontManager.montserrat.drawString(a.h + "User " + a.p + this.j.af.c(), 20.0f, n6 + CFontManager.montserrat.FONT_HEIGHT * 1.8f, new Color(185 + 64 - 132 + 138, 74 + 208 - 141 + 114, 103 + 159 - 47 + 40, 0x69 ^ 0x13).getRGB());
        GL11.glPushMatrix();
        ScaleUtils.scale_pre();
        final float scale = ScaleUtils.getScale(bit.a());
        ScaleUtils.getScale(bit.b());
        int n7 = 0x13 ^ 0x55;
        for (final Alt selectedAlt : Main.alts) {
            this.altMap.put(new int[] { (int)(scale / 2.0f - 110.0f), (int)(n7 + this.scrollFactor) }, selectedAlt);
            int rgb = Integer.MIN_VALUE;
            if (!this.isAddWindow && (this.isHovered(n, n2, (int)(scale / 2.0f - 110.0f), (int)(n7 + this.scrollFactor), 1 + 155 + 23 + 36, 0x62 ^ 0x5E) || this.selectedAlt == selectedAlt)) {
                if (Mouse.isButtonDown(0)) {
                    this.selectedAlt = selectedAlt;
                }
                if (Mouse.isButtonDown(0) && selectedAlt == this.selectedAlt && !selectedAlt.name.isEmpty()) {
                    new AuthenticatorThread(selectedAlt.getName(), selectedAlt.getPassword()).start();
                }
                rgb = new Color(783996876 + 13070827 + 539957590 + 183845785, true).getRGB();
            }
            RenderUtils.drawRectWH(scale / 2.0f - 110.0f, n7 + this.scrollFactor, 215.0f, 60.0f, rgb);
            RenderUtils.drawImg(new nf("wild/steve.png"), scale / 2.0f - 105.0f, n7 + this.scrollFactor + 7.5f, 45.0, 45.0);
            CFontManager.montserratmedium.drawString(selectedAlt.name, scale / 2.0f - 50.0f, n7 + 5 + this.scrollFactor, -1);
            CFontManager.montserratmedium.drawString(this.getPassword(selectedAlt.password), scale / 2.0f - 50.0f, n7 + (0x85 ^ 0x91) + this.scrollFactor, -1);
            CFontManager.montserratmedium.drawString(selectedAlt.created, scale / 2.0f - 50.0f, n7 + (0xF ^ 0x27) + this.scrollFactor, -1);
            n7 += 70;
        }
        ScaleUtils.scale_post();
        GL11.glPopMatrix();
        this.scrollVelocity *= 0.56f;
        if (this.isAddWindow) {
            if (this.scaleFactor < 1.0f) {
                this.scaleFactor += 0.05f;
            }
            else {
                this.scaleFactor = 1.0f;
            }
            final float easeInOutQuart = Easings.easeInOutQuart(this.scaleFactor);
            GL11.glPushMatrix();
            GL11.glScaled((double)easeInOutQuart, (double)easeInOutQuart, 1.0);
            GL11.glTranslatef((1.0f - easeInOutQuart) * bit.a() / 2.0f, (1.0f - easeInOutQuart) * bit.b(), 0.0f);
            ScaleUtils.scale_pre();
            GL11.glEnable(2298 + 1062 - 2642 + 2324);
            GL11.glBlendFunc(450 + 266 - 401 + 455, 345 + 76 + 4 + 346);
            RenderUtils.drawRectWH(0.0f, 0.0f, (float)bit.a(), (float)bit.b(), new Color(0x23 ^ 0x29, 0x60 ^ 0x6A, 0x96 ^ 0x9C, 57 + 95 - 151 + 159).getRGB());
            RenderUtils.drawBlurredShadow((float)(bit.a() / 2 - (0x62 ^ 0xC)), (float)(bit.b() / 2 - (0x2C ^ 0x6A)), 220.0f, 140.0f, 8, new Color(Integer.MIN_VALUE));
            RenderUtils.drawRect((float)(bit.a() / 2 - (0x27 ^ 0x49)), (float)(bit.b() / 2 - (0x29 ^ 0x6F)), (float)(bit.a() / 2 + (0x50 ^ 0x3E)), (float)(bit.b() / 2 + (0x38 ^ 0x7E)), new Color(0xAE ^ 0xB7, 0x86 ^ 0x9F, 0x3D ^ 0x24, 231 + 208 - 196 + 12).getRGB());
            CFontManager.montserrat.drawCenteredString("add new alt", (float)(bit.a() / 2 - 3), (float)(bit.b() / 2 - (0x2 ^ 0x43)), -1);
            CFontManager.montserrat.drawString("name" + a.h + " or mail", (float)(bit.a() / 2 - (0x11 ^ 0x77)), (float)(bit.b() / 2 - (0x57 ^ 0x65)), -1);
            RenderUtils.drawRectWH((float)(bit.a() / 2 - (0x55 ^ 0x33)), (float)(bit.b() / 2 - (0x4B ^ 0x68)), (float)this.login.i, (float)this.login.j, new Color(162476 + 1493338 - 1316007 + 1897155).getRGB());
            CFontManager.montserrat.drawString("password", (float)(bit.a() / 2 - (0x10 ^ 0x76)), (float)(bit.b() / 2 - 6), -1);
            RenderUtils.drawRectWH((float)(bit.a() / 2 - (0xA4 ^ 0xC2)), (float)(bit.b() / 2 + (0x75 ^ 0x7F)), (float)this.password.i, (float)this.password.j, new Color(345408 + 369643 - 682002 + 2203913).getRGB());
            this.login.g();
            this.password.g();
            GL11.glDisable(2255 + 2418 - 3297 + 1666);
            ScaleUtils.scale_post();
            GL11.glPopMatrix();
        }
        super.a(n, n2, n3);
    }
    
    public void m() {
        this.isAddWindow = false;
        this.scaleFactor = 0.0f;
        Main.configManager.saveAlts();
        super.m();
    }
    
    public void addButtons() {
        final bit bit = new bit(bib.z());
        final int n = (int)(bit.b() / 2.0f - CFontManager.montserrat.FONT_HEIGHT * 6.9f);
        this.n.add(new GuiMainMenuButton(0, 0x36 ^ 0x22, n, 0x28 ^ 0x6E, 0x96 ^ 0x82, "Login"));
        final int n2 = (int)(n + CFontManager.montserrat.FONT_HEIGHT * 2.3f);
        this.n.add(new GuiMainMenuButton(1, 0x41 ^ 0x55, n2, 0x37 ^ 0x71, 0x4D ^ 0x59, "Direct Login"));
        final int n3 = (int)(n2 + CFontManager.montserrat.FONT_HEIGHT * 2.3f);
        this.n.add(new GuiMainMenuButton(2, 0x62 ^ 0x76, n3, 0x2A ^ 0x6C, 0x5 ^ 0x11, "Add"));
        final int n4 = (int)(n3 + CFontManager.montserrat.FONT_HEIGHT * 2.3f);
        this.n.add(new GuiMainMenuButton(3, 0x51 ^ 0x45, n4, 0x28 ^ 0x6E, 0xB3 ^ 0xA7, "Delete"));
        this.n.add(new GuiMainMenuButton(0x9E ^ 0x93, 0x31 ^ 0x25, (int)(n4 + CFontManager.montserrat.FONT_HEIGHT * 2.3f), 0xD1 ^ 0x97, 0x24 ^ 0x30, "Random"));
        this.n.add(new GuiMainMenuButton(4, 0xD5 ^ 0xC1, bit.b() - bit.b() / (0x20 ^ 0x2F), 120 + 68 - 29 + 6, 0x76 ^ 0x62, "back to main menu"));
    }
    
    public void a(final int n, final int n2, final int n3, final long n4) {
    }
    
    public void a(final char c, final int n) throws IOException {
        if (this.isAddWindow) {
            this.login.a(c, n);
            this.password.a(c, n);
        }
    }
    
    public void b(final int n, final int n2, final int n3) {
        super.b(n, n2, n3);
    }
}
