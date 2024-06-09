package com.sun.jna.z.a.c;

import cpw.mods.fml.common.eventhandler.*;
import net.minecraftforge.client.event.*;
import org.lwjgl.input.*;
import com.sun.jna.z.a.d.*;
import com.sun.jna.z.a.*;
import com.sun.jna.z.a.e.a.a.a.e.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.*;

public class a implements IEventListener
{
    boolean a;
    
    public void invoke(final Event a) {
        final int b = com.sun.jna.z.a.c.b.b ? 1 : 0;
        final RenderGameOverlayEvent a2 = (RenderGameOverlayEvent)a;
        final int a3 = b;
        final RenderGameOverlayEvent renderGameOverlayEvent = a2;
        if ((a3 != 0 || renderGameOverlayEvent.isCancelable()) && renderGameOverlayEvent.type == RenderGameOverlayEvent$ElementType.ALL) {
            boolean b3;
            final boolean b2 = b3 = Keyboard.isKeyDown(29);
            Label_0086: {
                if (a3 == 0) {
                    if (!b2) {
                        break Label_0086;
                    }
                    final boolean keyDown;
                    b3 = (keyDown = Keyboard.isKeyDown(199));
                }
                a a4 = null;
                Label_0068: {
                    if (a3 == 0) {
                        if (!b2) {
                            break Label_0086;
                        }
                        a4 = this;
                        if (a3 != 0) {
                            break Label_0068;
                        }
                        b3 = this.a;
                    }
                    if (b3) {
                        break Label_0086;
                    }
                    a4 = this;
                }
                a4.a = true;
                h.d.a.a(d.class).d();
            }
            final com.sun.jna.z.a.e.a.a.a.h c = i.f.c;
            if (a3 == 0) {
                if (c == null) {
                    (i.f.c = new com.sun.jna.z.a.e.a.a.a.h()).a(new com.sun.jna.z.a.e.a.a.a.e.a.i());
                    i.f.c.a();
                    h.d.f.g();
                    if (a3 == 0) {
                        return;
                    }
                }
                GL11.glPushMatrix();
                final com.sun.jna.z.a.e.a.a.a.h c2 = i.f.c;
            }
            c.f();
            GL11.glPopMatrix();
            Label_0199: {
                if (a3 == 0) {
                    if (Minecraft.func_71410_x().field_71462_r == i.f.d()) {
                        break Label_0199;
                    }
                    GL11.glPushMatrix();
                    i.f.c.e();
                }
                GL11.glPopMatrix();
            }
            GL11.glColor3d(1.0, 1.0, 1.0);
        }
    }
}
