package net.minecraft.src;

import java.util.*;

public class RegistrySimple implements IRegistry
{
    protected final Map registryObjects;
    
    public RegistrySimple() {
        this.registryObjects = new HashMap();
    }
    
    @Override
    public Object func_82594_a(final Object par1Obj) {
        return this.registryObjects.get(par1Obj);
    }
    
    @Override
    public void putObject(final Object par1Obj, final Object par2Obj) {
        this.registryObjects.put(par1Obj, par2Obj);
    }
}
