package ez.h.managers.altmanager;

import java.awt.*;
import ez.h.ui.fonts.*;
import java.io.*;
import ez.h.*;
import java.util.*;

public class AltManagerCreateAltGui extends blk
{
    bja back;
    bja add;
    bje password;
    bje login;
    
    public void a(final int n, final int n2, final float n3) {
        final bit bit = new bit(this.j);
        this.c();
        bir.drawRect((float)(bit.a() / 2 - (0x7D ^ 0x2D)), (float)(bit.b() / 2 - (0x56 ^ 0x32)), (float)(bit.a() / 2 + (0x15 ^ 0x45)), (float)(bit.b() / 2 + (0x28 ^ 0x4C)), new Color(0.0f, 0.0f, 0.0f, 0.6f).hashCode());
        CFontManager.rany.drawCenteredString("Add alt", (float)(bit.a() / 2 - 1), (float)(bit.b() / 2 - (0x99 ^ 0xC6)), -1);
        this.login.g();
        this.password.g();
        if (!this.login.m() && this.login.b().isEmpty()) {
            CFontManager.rany.drawCenteredString("Login", (float)(bit.a() / 2), (float)(bit.b() / 2 - (0x2E ^ 0xC)), Color.DARK_GRAY.hashCode());
        }
        if (!this.password.m() && this.password.b().isEmpty()) {
            CFontManager.rany.drawCenteredString("Password", (float)(bit.a() / 2), (float)(bit.b() / 2 - 4), Color.DARK_GRAY.hashCode());
        }
        super.a(n, n2, n3);
    }
    
    protected void a(final char c, final int n) throws IOException {
        this.login.a(c, n);
        this.password.a(c, n);
        super.a(c, n);
    }
    
    protected void a(final int n, final int n2, final int n3, final long n4) {
        super.a(n, n2, n3, n4);
    }
    
    protected void a(final bja bja) throws IOException {
        if (bja.k == 0) {
            Main.alts.add(new Alt(this.login.b(), this.password.b().isEmpty() ? "" : this.password.b(), Calendar.getInstance().get(5) + "/" + Calendar.getInstance().get(2) + "/" + Calendar.getInstance().get(1)));
            this.j.a((blk)new AltManagerGUI());
        }
        if (bja.k == 1) {
            this.j.a((blk)new AltManagerGUI());
        }
        super.a(bja);
    }
    
    public void m() {
        Main.configManager.saveAlts();
        super.m();
    }
    
    public void b() {
        final bit bit = new bit(this.j);
        this.add = new bja(0, bit.a() / 2 - (0x1F ^ 0x23), bit.b() / 2 + (0x3F ^ 0x21), 0xE6 ^ 0x9E, 0x1A ^ 0xE, "Add");
        this.back = new bja(1, bit.a() / 2 - (0xA2 ^ 0x9E), bit.b() / 2 + (0x30 ^ 0xC), 0xDF ^ 0xA7, 0xB ^ 0x1F, "Back");
        this.login = new bje(2, this.j.k, bit.a() / 2 - (0x30 ^ 0xC), bit.b() / 2 - (0xEB ^ 0xC3), 0xFE ^ 0x86, 0x1 ^ 0x15);
        this.password = new bje(2, this.j.k, bit.a() / 2 - (0x2 ^ 0x3E), bit.b() / 2 - (0x3 ^ 0x9), 0xD7 ^ 0xAF, 0x8 ^ 0x1C);
        this.n.add(this.add);
        this.n.add(this.back);
        super.b();
    }
    
    public void a(final boolean b, final int n) {
        super.a(b, n);
    }
    
    protected void a(final int n, final int n2, final int n3) throws IOException {
        this.login.a(n, n2, n3);
        this.password.a(n, n2, n3);
        super.a(n, n2, n3);
    }
    
    protected void b(final int n, final int n2, final int n3) {
        super.b(n, n2, n3);
    }
}
