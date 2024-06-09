package com.sun.jna.z.a;

import com.sun.jna.z.a.b.*;
import com.sun.jna.z.a.f.*;
import net.minecraft.client.network.*;
import com.sun.jna.z.a.d.*;
import md.*;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.client.event.*;
import cpw.mods.fml.common.gameevent.*;
import com.sun.jna.z.*;
import cpw.mods.fml.common.eventhandler.*;
import java.lang.reflect.*;
import java.util.*;

public class h
{
    public e a;
    public com.sun.jna.z.a.f.e b;
    public com.sun.jna.z.a.b.e c;
    public static h d;
    public boolean e;
    public f f;
    public double g;
    public boolean h;
    public i i;
    public k j;
    public g k;
    public NetHandlerPlayClient l;
    Map<IEventListener, Event> m;
    public static int n;
    private static final String o;
    
    public h() {
        this.g = 2.06;
        final int n = com.sun.jna.z.a.h.n;
        this.m = new HashMap<IEventListener, Event>();
        com.sun.jna.z.a.h.d = this;
        this.i = new i();
        int a = n;
        this.j = new k();
        this.c = new com.sun.jna.z.a.b.e();
        boolean d;
        final boolean b = d = this.c.d;
        Label_0545: {
            Label_0529: {
                if (a == 0) {
                    if (!b) {
                        break Label_0529;
                    }
                    d = false;
                }
                final int a2 = d ? 1 : 0;
                if (a == 0) {
                    if (a2 > 0) {
                        this.h = true;
                        com.sun.jna.z.a.i.a(com.sun.jna.z.a.h.o);
                    }
                    this.a = new e();
                }
                com.sun.jna.z.a.h.d.a.a(new m());
                com.sun.jna.z.a.h.d.a.a(new md.k());
                com.sun.jna.z.a.h.d.a.a(new md.i());
                com.sun.jna.z.a.h.d.a.a(new a());
                com.sun.jna.z.a.h.d.a.a(new l());
                com.sun.jna.z.a.h.d.a.a(new md.f());
                com.sun.jna.z.a.h.d.a.a(new md.h());
                com.sun.jna.z.a.h.d.a.a(new md.g());
                com.sun.jna.z.a.h.d.a.a(new b());
                com.sun.jna.z.a.h.d.a.a(new j());
                com.sun.jna.z.a.h.d.a.a(new n());
                com.sun.jna.z.a.h.d.a.a(new md.e());
                com.sun.jna.z.a.h.d.a.a(new d());
                com.sun.jna.z.a.h.d.a.a(new md.c());
                new com.sun.jna.z.a.e.a.a.a.d(com.sun.jna.z.a.h.d);
                this.a.a();
                this.k = new g();
                new com.sun.jna.z.a.i();
                (this.f = new f(this.c)).g();
                this.b = new com.sun.jna.z.a.f.e();
                this.a(RenderGameOverlayEvent$Post.class, (IEventListener)new com.sun.jna.z.a.c.a(), 0);
                this.a(MouseEvent.class, (IEventListener)new com.sun.jna.z.a.c.d(), 0);
                this.a(RenderHandEvent.class, (IEventListener)new com.sun.jna.z.a.c.g(), 0);
                this.a(LivingEvent$LivingUpdateEvent.class, (IEventListener)new com.sun.jna.z.a.c.c(), 0);
                this.a(RenderLivingEvent$Specials$Pre.class, (IEventListener)new com.sun.jna.z.a.c.e(), 0);
                this.a(InputEvent$KeyInputEvent.class, (IEventListener)new com.sun.jna.z.a.c.b(), 3);
                this.a(TickEvent$PlayerTickEvent.class, (IEventListener)new com.sun.jna.z.a.c.f(), 3);
                if (a == 0) {
                    break Label_0545;
                }
            }
            new Thread(Main.c.b).start();
        }
        if (com.sun.jna.z.a.i.g != 0) {
            com.sun.jna.z.a.h.n = ++a;
        }
    }
    
    public void a(final Class a, final IEventListener a, final int a) {
        try {
            final Constructor a2 = a.getConstructor((Class[])new Class[0]);
            a2.setAccessible(true);
            final Event a3 = a2.newInstance(new Object[0]);
            a3.getListenerList().register(a, EventPriority.NORMAL, a);
            this.m.put(a, a3);
        }
        catch (Exception a4) {
            a4.printStackTrace();
        }
    }
    
    public void a() {
        final int n = com.sun.jna.z.a.h.n;
        Iterator<IEventListener> iterator = this.m.keySet().iterator();
        final int a = n;
        while (true) {
            while (iterator.hasNext()) {
                IEventListener a2 = iterator.next();
                Event a3 = this.m.get(a2);
                a3.getListenerList().unregister(3, a2);
                if (a == 0) {
                    if (a != 0) {
                        break;
                    }
                    continue;
                }
                else {
                Label_0159_Outer:
                    while (true) {
                        while (true) {
                            while (iterator.hasNext()) {
                                a2 = iterator.next();
                                a3 = this.m.get(a2);
                                a3.getListenerList().unregister(0, a2);
                                if (a != 0) {
                                    h h = this;
                                    final h h2 = this;
                                    if (a == 0) {
                                        if (h2.a != null) {
                                            this.a.a.clear();
                                        }
                                        h = this;
                                    }
                                    final com.sun.jna.z.a.f.e b = h.b;
                                    Label_0202: {
                                        if (a == 0) {
                                            if (b == null) {
                                                break Label_0202;
                                            }
                                            final com.sun.jna.z.a.f.e b2 = this.b;
                                        }
                                        b.a.clear();
                                    }
                                    com.sun.jna.z.a.i.f = null;
                                    com.sun.jna.z.a.h.d = null;
                                    return;
                                }
                                if (a != 0) {
                                    break;
                                }
                            }
                            h h = this;
                            final h h2 = this;
                            if (a != 0) {
                                continue;
                            }
                            break;
                        }
                        if (this.m != null) {
                            this.m.clear();
                        }
                        continue Label_0159_Outer;
                    }
                }
            }
            iterator = this.m.keySet().iterator();
            continue;
        }
    }
    
    static {
        final char[] charArray = "\u00134\u00ef²\u0011\t]11\u00fe£T\u0002".toCharArray();
        int length;
        int n2;
        final int n = n2 = (length = charArray.length);
        int n3 = 0;
        while (true) {
            Label_0126: {
                if (n > 1) {
                    break Label_0126;
                }
                length = (n2 = n3);
                do {
                    final char c = charArray[n2];
                    char c2 = '\0';
                    switch (n3 % 7) {
                        case 0: {
                            c2 = 'E';
                            break;
                        }
                        case 1: {
                            c2 = 'U';
                            break;
                        }
                        case 2: {
                            c2 = '\u009f';
                            break;
                        }
                        case 3: {
                            c2 = '\u00d7';
                            break;
                        }
                        case 4: {
                            c2 = '1';
                            break;
                        }
                        case 5: {
                            c2 = 'f';
                            break;
                        }
                        default: {
                            c2 = '(';
                            break;
                        }
                    }
                    charArray[length] = (char)(c ^ c2);
                    ++n3;
                } while (n == 0);
            }
            if (n <= n3) {
                o = new String(charArray).intern();
                return;
            }
            continue;
        }
    }
}
