package org.spongepowered.asm.mixin.transformer;

import net.minecraft.launchwrapper.*;
import org.spongepowered.asm.service.*;
import org.apache.logging.log4j.*;
import java.util.*;

public final class Proxy implements IClassTransformer, ILegacyClassTransformer
{
    private static List<Proxy> proxies;
    private static MixinTransformer transformer;
    private boolean isActive;
    
    public Proxy() {
        super();
        this.isActive = true;
        final Iterator<Proxy> iterator = Proxy.proxies.iterator();
        while (iterator.hasNext()) {
            iterator.next().isActive = false;
        }
        Proxy.proxies.add(this);
        LogManager.getLogger("mixin").debug("Adding new mixin transformer proxy #{}", new Object[] { Proxy.proxies.size() });
    }
    
    public byte[] transform(final String s, final String s2, final byte[] array) {
        if (this.isActive) {
            return Proxy.transformer.transformClassBytes(s, s2, array);
        }
        return array;
    }
    
    public String getName() {
        return this.getClass().getName();
    }
    
    public boolean isDelegationExcluded() {
        return true;
    }
    
    public byte[] transformClassBytes(final String s, final String s2, final byte[] array) {
        if (this.isActive) {
            return Proxy.transformer.transformClassBytes(s, s2, array);
        }
        return array;
    }
    
    static {
        Proxy.proxies = new ArrayList<Proxy>();
        Proxy.transformer = new MixinTransformer();
    }
}
