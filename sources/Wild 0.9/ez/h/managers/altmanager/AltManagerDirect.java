package ez.h.managers.altmanager;

import java.io.*;
import java.awt.*;
import ez.h.ui.fonts.*;

public class AltManagerDirect extends blk
{
    bje password;
    bja add;
    bje login;
    int mode;
    
    protected void a(final bja bja) throws IOException {
        if (bja.k == 0) {
            new AuthenticatorThread(this.login.b(), this.password.b()).start();
            this.j.a((blk)new AltManagerGUI());
        }
        if (bja.k == 1) {
            this.j.a((blk)new AltManagerGUI());
        }
        super.a(bja);
    }
    
    protected void a(final char c, final int n) throws IOException {
        this.login.a(c, n);
        this.password.a(c, n);
        super.a(c, n);
    }
    
    protected void a(final int n, final int n2, final int n3) throws IOException {
        this.login.a(n, n2, n3);
        this.password.a(n, n2, n3);
        super.a(n, n2, n3);
    }
    
    protected void a(final int n, final int n2, final int n3, final long n4) {
        super.a(n, n2, n3, n4);
    }
    
    public void a(final boolean b, final int n) {
        super.a(b, n);
    }
    
    public void a(final int n, final int n2, final float n3) {
        final bit bit = new bit(this.j);
        this.c();
        bir.drawRect((float)(bit.a() / 2 - (0x7C ^ 0x2C)), (float)(bit.b() / 2 - (0x53 ^ 0x37)), (float)(bit.a() / 2 + (0x10 ^ 0x40)), (float)(bit.b() / 2 + (0x84 ^ 0xC2)), new Color(0.0f, 0.0f, 0.0f, 0.6f).hashCode());
        CFontManager.rany.drawCenteredString(AltManagerGUI.status, (float)(bit.a() / 2 - 1), (float)(bit.b() / 2 - (0xC5 ^ 0x9A)), -1);
        this.login.g();
        this.password.g();
        if (!this.login.m() && this.login.b().isEmpty()) {
            CFontManager.rany.drawCenteredString("Login", (float)(bit.a() / 2), (float)(bit.b() / 2 - (0x2 ^ 0x20)), Color.DARK_GRAY.hashCode());
        }
        if (!this.password.m() && this.password.b().isEmpty()) {
            CFontManager.rany.drawCenteredString("Password", (float)(bit.a() / 2), (float)(bit.b() / 2 - 4), Color.DARK_GRAY.hashCode());
        }
        super.a(n, n2, n3);
    }
    
    protected void b(final int n, final int n2, final int n3) {
        super.b(n, n2, n3);
    }
    
    public void b() {
        AltManagerGUI.status = "Direct Login";
        final bit bit = new bit(this.j);
        this.add = new bja(0, bit.a() / 2 - (0x4 ^ 0x38), bit.b() / 2 + (0x2F ^ 0x31), 0xF6 ^ 0x8E, 0x87 ^ 0x93, "Login");
        this.login = new bje(2, this.j.k, bit.a() / 2 - (0x2E ^ 0x12), bit.b() / 2 - (0x40 ^ 0x68), 0xED ^ 0x95, 0xD ^ 0x19);
        this.password = new bje(2, this.j.k, bit.a() / 2 - (0x7E ^ 0x42), bit.b() / 2 - (0x11 ^ 0x1B), 0xC2 ^ 0xBA, 0x6A ^ 0x7E);
        this.n.add(this.add);
        super.b();
    }
}
