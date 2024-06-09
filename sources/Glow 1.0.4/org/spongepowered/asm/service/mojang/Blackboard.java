package org.spongepowered.asm.service.mojang;

import org.spongepowered.asm.service.*;
import net.minecraft.launchwrapper.*;

public class Blackboard implements IGlobalPropertyService
{
    public Blackboard() {
        super();
    }
    
    @Override
    public final <T> T getProperty(final String s) {
        return Launch.blackboard.get(s);
    }
    
    @Override
    public final void setProperty(final String s, final Object o) {
        Launch.blackboard.put(s, o);
    }
    
    @Override
    public final <T> T getProperty(final String s, final T t) {
        final T value = Launch.blackboard.get(s);
        return (value != null) ? value : t;
    }
    
    @Override
    public final String getPropertyString(final String s, final String s2) {
        final Object value = Launch.blackboard.get(s);
        return (value != null) ? value.toString() : s2;
    }
}
