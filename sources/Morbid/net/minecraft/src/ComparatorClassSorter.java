package net.minecraft.src;

import java.util.*;

class ComparatorClassSorter implements Comparator
{
    final CallableSuspiciousClasses theSuspiciousClasses;
    
    ComparatorClassSorter(final CallableSuspiciousClasses par1CallableSuspiciousClasses) {
        this.theSuspiciousClasses = par1CallableSuspiciousClasses;
    }
    
    public int func_85081_a(final Class par1Class, final Class par2Class) {
        final String var3 = (par1Class.getPackage() == null) ? "" : par1Class.getPackage().getName();
        final String var4 = (par2Class.getPackage() == null) ? "" : par2Class.getPackage().getName();
        return var3.compareTo(var4);
    }
    
    @Override
    public int compare(final Object par1Obj, final Object par2Obj) {
        return this.func_85081_a((Class)par1Obj, (Class)par2Obj);
    }
}
