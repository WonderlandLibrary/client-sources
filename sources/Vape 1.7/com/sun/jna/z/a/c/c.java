package com.sun.jna.z.a.c;

import cpw.mods.fml.common.eventhandler.*;
import net.minecraftforge.event.entity.living.*;
import com.sun.jna.z.a.*;
import java.util.*;

public class c implements IEventListener
{
    public void invoke(final Event a) {
        final int b = com.sun.jna.z.a.c.b.b ? 1 : 0;
        final LivingEvent$LivingUpdateEvent a2 = (LivingEvent$LivingUpdateEvent)a;
        final int a3 = b;
        for (final com.sun.jna.z.a.d.c c : h.d.a.a.values()) {
            final com.sun.jna.z.a.d.c a4 = c;
            if (a3 != 0 || c.c()) {
                c.a(a2);
            }
            if (a3 != 0) {
                break;
            }
        }
    }
}
