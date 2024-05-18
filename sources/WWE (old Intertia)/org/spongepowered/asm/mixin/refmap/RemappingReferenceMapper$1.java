package org.spongepowered.asm.mixin.refmap;

import com.google.common.io.*;
import java.util.*;
import com.google.common.base.*;
import java.io.*;

static final class RemappingReferenceMapper$1 implements LineProcessor<Object> {
    final Map val$map;
    
    RemappingReferenceMapper$1(final Map val$map) {
        this.val$map = val$map;
        super();
    }
    
    public Object getResult() {
        return null;
    }
    
    public boolean processLine(final String string) throws IOException {
        if (Strings.isNullOrEmpty(string) || string.startsWith("#")) {
            return true;
        }
        final int n = 0;
        int n3;
        final int n2 = string.startsWith("MD: ") ? (n3 = 2) : (string.startsWith("FD: ") ? (n3 = 1) : (n3 = 0));
        final int n4 = n3;
        if (n2 > 0) {
            final String[] split = string.substring(4).split(" ", 4);
            this.val$map.put(split[n].substring(split[n].lastIndexOf(47) + 1), split[n4].substring(split[n4].lastIndexOf(47) + 1));
        }
        return true;
    }
}