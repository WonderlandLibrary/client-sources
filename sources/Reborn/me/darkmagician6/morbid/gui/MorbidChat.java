package me.darkmagician6.morbid.gui;

import net.minecraft.client.*;
import org.lwjgl.opengl.*;
import me.darkmagician6.morbid.util.*;
import me.darkmagician6.morbid.util.font.*;
import me.darkmagician6.morbid.*;
import java.util.*;

public class MorbidChat extends awh
{
    private final Minecraft mc;
    private final List sentMessages;
    private final List chatLines;
    private final List field_96134_d;
    private int field_73768_d;
    private boolean field_73769_e;
    private int height;
    
    public MorbidChat(final Minecraft par1Minecraft) {
        super(par1Minecraft);
        this.sentMessages = new ArrayList();
        this.chatLines = new ArrayList();
        this.field_96134_d = new ArrayList();
        this.field_73768_d = 0;
        this.field_73769_e = false;
        this.height = 0;
        this.mc = par1Minecraft;
    }
    
    @Override
    public void a(final int par1) {
        if (this.mc.z.n != 2) {
            final int var2 = this.i();
            boolean var3 = false;
            int var4 = 0;
            final int var5 = this.field_96134_d.size();
            final float var6 = this.mc.z.r * 0.9f + 0.1f;
            if (var5 > 0) {
                if (this.e()) {
                    var3 = true;
                }
                final float var7 = this.h();
                final int var8 = kx.f(this.f() / var7);
                GL11.glPushMatrix();
                GL11.glTranslatef(2.0f, 20.0f, 0.0f);
                GL11.glScalef(var7, var7, 1.0f);
                if (this.height > 0) {
                    GLHelper.drawBorderedRect(-1, 1, var8 - 29, -this.height * 9 - 1, 1.0f, -16777216);
                    GLHelper.drawBorderedRect(-1, -14 - this.height * 9, var8 - 29, -this.height * 9 - 3, 1.0f, -16777216);
                    awx.a(0, -13 - this.height * 9, var8 - 30, -this.height * 9 - 4, -2013265920);
                    GL11.glEnable(3042);
                    TTF.drawTTFString("MorbidChat", 1.0, -15 - this.height * 9, 16729088);
                    GL11.glDisable(3042);
                }
                this.height = 0;
                for (int var9 = 0; var9 + this.field_73768_d < this.field_96134_d.size() && var9 < var2; ++var9) {
                    final auz var10 = this.field_96134_d.get(var9 + this.field_73768_d);
                    if (var10 != null) {
                        final int var11 = par1 - var10.b();
                        if (var11 < 200 || var3) {
                            double var12 = var11 / 200.0;
                            var12 = 1.0 - var12;
                            var12 *= 10.0;
                            if (var12 < 0.0) {
                                var12 = 0.0;
                            }
                            if (var12 > 1.0) {
                                var12 = 1.0;
                            }
                            var12 *= var12;
                            int var13 = (int)(255.0 * var12);
                            if (var3) {
                                var13 = 255;
                            }
                            var13 *= (int)var6;
                            ++var4;
                            if (var13 > 3) {
                                final byte var14 = 0;
                                final int var15 = -var9 * 9;
                                String var16 = var10.a();
                                final boolean isMe = var16.contains(lf.a(MorbidWrapper.getPlayer().bS));
                                awx.a(var14, var15 - 9, var14 + var8 - 30, var15, isMe ? -1996536832 : -2013265920);
                                GL11.glEnable(3042);
                                if (!this.mc.z.o) {
                                    var16 = lf.a(var16);
                                }
                                var16 = Morbid.getFriends().replaceNameWithColor(var16);
                                if (var16.contains(lf.a(MorbidWrapper.getPlayer().bS))) {
                                    final String s = MorbidWrapper.getPlayer().bS;
                                    var16 = var16.replaceAll(s, "§9" + s + "§r");
                                }
                                TTF.drawTTFString(var16, var14 + 1, var15 - 11, 16777215 + (var13 << 24));
                                ++this.height;
                            }
                        }
                    }
                }
                if (var3) {
                    final int var9 = this.mc.q.a;
                    GL11.glTranslatef(-3.0f, 0.0f, 0.0f);
                    final int var17 = var5 * var9 + var5;
                    final int var11 = var4 * var9 + var4;
                    final int var18 = this.field_73768_d * var11 / var5;
                    final int var19 = var11 * var11 / var17;
                    if (var17 != var11) {
                        final int var13 = (var18 > 0) ? 170 : 96;
                        final int var20 = this.field_73769_e ? 13382451 : 3355562;
                        awx.a(0, -var18, 2, -var18 - var19, var20 + (var13 << 24));
                        awx.a(2, -var18, 1, -var18 - var19, 13421772 + (var13 << 24));
                    }
                }
                GL11.glPopMatrix();
            }
        }
    }
    
    @Override
    public void a() {
        this.field_96134_d.clear();
        this.chatLines.clear();
        this.sentMessages.clear();
    }
    
    @Override
    public void a(final String par1Str) {
        this.a(par1Str, 0);
    }
    
    @Override
    public void a(final String par1Str, final int par2) {
        this.func_96129_a(par1Str, par2, this.mc.w.c(), false);
    }
    
    private void func_96129_a(final String par1Str, final int par2, final int par3, final boolean par4) {
        final boolean var5 = this.e();
        boolean var6 = true;
        if (par2 != 0) {
            this.c(par2);
        }
        for (String var8 : this.mc.q.c(par1Str, kx.d(this.f() / this.h()))) {
            if (var5 && this.field_73768_d > 0) {
                this.field_73769_e = true;
                this.b(1);
            }
            if (!var6) {
                var8 = " " + var8;
            }
            var6 = false;
            this.field_96134_d.add(0, new auz(par3, var8, par2));
        }
        while (this.field_96134_d.size() > 100) {
            this.field_96134_d.remove(this.field_96134_d.size() - 1);
        }
        if (!par4) {
            this.chatLines.add(0, new auz(par3, par1Str.trim(), par2));
            while (this.chatLines.size() > 100) {
                this.chatLines.remove(this.chatLines.size() - 1);
            }
        }
    }
    
    @Override
    public void b() {
        this.field_96134_d.clear();
        this.d();
        for (int var1 = this.chatLines.size() - 1; var1 >= 0; --var1) {
            final auz var2 = this.chatLines.get(var1);
            this.func_96129_a(var2.a(), var2.c(), var2.b(), true);
        }
    }
    
    @Override
    public List c() {
        return this.sentMessages;
    }
    
    @Override
    public void b(final String par1Str) {
        if (this.sentMessages.isEmpty() || !this.sentMessages.get(this.sentMessages.size() - 1).equals(par1Str)) {
            this.sentMessages.add(par1Str);
        }
    }
    
    @Override
    public void d() {
        this.field_73768_d = 0;
        this.field_73769_e = false;
    }
    
    @Override
    public void b(final int par1) {
        this.field_73768_d += par1;
        final int var2 = this.field_96134_d.size();
        if (this.field_73768_d > var2 - this.i()) {
            this.field_73768_d = var2 - this.i();
        }
        if (this.field_73768_d <= 0) {
            this.field_73768_d = 0;
            this.field_73769_e = false;
        }
    }
    
    @Override
    public awy a(final int par1, final int par2) {
        if (!this.e()) {
            return null;
        }
        final axs var3 = new axs(this.mc.z, this.mc.c, this.mc.d);
        final int var4 = var3.e();
        final float var5 = this.h();
        int var6 = par1 / var4 - 3;
        int var7 = par2 / var4 - 25;
        var6 = kx.d(var6 / var5);
        var7 = kx.d(var7 / var5);
        if (var6 < 0 || var7 < 0) {
            return null;
        }
        final int var8 = Math.min(this.i(), this.field_96134_d.size());
        if (var6 <= kx.d(this.f() / this.h()) && var7 < this.mc.q.a * var8 + var8) {
            final int var9 = var7 / (this.mc.q.a + 1) + this.field_73768_d;
            return new awy(this.mc.q, this.field_96134_d.get(var9), var6, var7 - (var9 - this.field_73768_d) * this.mc.q.a + var9);
        }
        return null;
    }
    
    @Override
    public void a(final String par1Str, final Object... par2ArrayOfObj) {
        this.a(bp.a().a(par1Str, par2ArrayOfObj));
    }
    
    @Override
    public boolean e() {
        return this.mc.s instanceof awj;
    }
    
    @Override
    public void c(final int par1) {
        Iterator var2 = this.field_96134_d.iterator();
        while (var2.hasNext()) {
            final auz var3 = var2.next();
            if (var3.c() == par1) {
                var2.remove();
                return;
            }
        }
        var2 = this.chatLines.iterator();
        while (var2.hasNext()) {
            final auz var3 = var2.next();
            if (var3.c() == par1) {
                var2.remove();
            }
        }
    }
    
    @Override
    public int f() {
        return func_96128_a(this.mc.z.F);
    }
    
    @Override
    public int g() {
        return func_96130_b(this.e() ? this.mc.z.H : this.mc.z.G);
    }
    
    @Override
    public float h() {
        return this.mc.z.E;
    }
    
    public static final int func_96128_a(final float par0) {
        final short var1 = 320;
        final byte var2 = 40;
        return kx.d(par0 * (var1 - var2) + var2);
    }
    
    public static final int func_96130_b(final float par0) {
        final short var1 = 180;
        final byte var2 = 20;
        return kx.d(par0 * (var1 - var2) + var2);
    }
    
    @Override
    public int i() {
        return this.g() / 9;
    }
}
