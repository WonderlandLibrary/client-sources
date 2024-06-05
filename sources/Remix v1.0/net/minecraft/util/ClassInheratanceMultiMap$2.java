package net.minecraft.util;

import com.google.common.collect.*;
import java.util.*;

class ClassInheratanceMultiMap$2 extends AbstractIterator {
    final /* synthetic */ Iterator val$var1;
    
    protected Object computeNext() {
        return this.val$var1.hasNext() ? this.val$var1.next() : this.endOfData();
    }
}