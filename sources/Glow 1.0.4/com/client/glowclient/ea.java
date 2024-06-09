package com.client.glowclient;

import com.google.common.collect.*;
import net.minecraft.nbt.*;
import java.util.*;

public class Ea
{
    public static List<X> D(final NBTTagList list) {
        if (list == null) {
            return null;
        }
        final ArrayList<X> arrayList = Lists.newArrayList();
        int n;
        int i = n = 0;
        while (i < list.tagCount()) {
            final ArrayList<X> list2 = arrayList;
            final short short1 = list.getCompoundTagAt(n).getShort("id");
            final NBTTagCompound compoundTag = list.getCompoundTagAt(n);
            final String s = "lvl";
            ++n;
            list2.add(new X(short1, compoundTag.getShort(s)));
            i = n;
        }
        return arrayList;
    }
    
    public static List<X> M(final NBTTagList list) {
        final List<X> d;
        if ((d = D(list)) != null) {
            Collections.sort((List<Object>)d, (Comparator<? super Object>)new Z());
        }
        return d;
    }
    
    public Ea() {
        super();
    }
}
