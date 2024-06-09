package com.client.glowclient;

import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.nbt.*;
import java.util.*;

public class qC extends Event
{
    public final J A;
    public final NBTTagCompound B;
    private final Map<String, Short> b;
    
    @Deprecated
    public qC(final Map<String, Short> map) {
        this(null, map);
    }
    
    public boolean replaceMapping(final String s, final String s2) throws EB {
        if (this.b.containsKey(s2)) {
            throw new EB(String.format("Could not replace block type %s, the block type %s already exists in the schematic.", s, s2));
        }
        final Short n;
        if ((n = this.b.get(s)) != null) {
            final boolean b = true;
            this.b.remove(s);
            this.b.put(s2, n);
            return b;
        }
        return false;
    }
    
    public qC(final J a, final Map<String, Short> b) {
        super();
        this.A = a;
        this.b = b;
        this.B = new NBTTagCompound();
    }
}
