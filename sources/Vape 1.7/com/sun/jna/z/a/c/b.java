package com.sun.jna.z.a.c;

import cpw.mods.fml.common.eventhandler.*;
import org.lwjgl.input.*;
import com.sun.jna.z.a.*;
import com.sun.jna.z.a.d.a.*;
import net.minecraftforge.client.event.*;
import com.sun.jna.z.a.d.*;
import java.util.*;

public class b implements IEventListener
{
    public static boolean b;
    
    public void invoke(final Event a) {
        final int a2 = com.sun.jna.z.a.c.b.b ? 1 : 0;
        if (a2 == 0) {
            if (Keyboard.getEventKey() == h.d.a.a(a.class).b() && !Keyboard.isRepeatEvent()) {
                a.a();
            }
            h.d.k.a(null);
        }
        for (final c a3 : h.d.a.a.values()) {
            boolean b;
            final int n = (b = (a3.b() != 0)) ? 1 : 0;
            Label_0126: {
                if (a2 == 0) {
                    if (n == -301) {
                        break Label_0126;
                    }
                    final boolean repeatEvent;
                    b = (repeatEvent = Keyboard.isRepeatEvent());
                }
                if (a2 == 0) {
                    if (n != 0) {
                        break Label_0126;
                    }
                    b = Keyboard.getEventKeyState();
                }
                if (b) {
                    a3.a(Keyboard.getEventKey());
                }
            }
            if (a2 != 0) {
                break;
            }
        }
    }
}
