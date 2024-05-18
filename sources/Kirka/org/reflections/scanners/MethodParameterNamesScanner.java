/*
 * Decompiled with CFR 0.143.
 */
package org.reflections.scanners;

import com.google.common.base.Joiner;
import com.google.common.collect.Multimap;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import javassist.bytecode.AttributeInfo;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.ConstPool;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;
import org.reflections.adapters.MetadataAdapter;
import org.reflections.scanners.AbstractScanner;

public class MethodParameterNamesScanner
extends AbstractScanner {
    public void scan(Object cls) {
        MetadataAdapter md = this.getMetadataAdapter();
        for (Object method : md.getMethods(cls)) {
            String key = md.getMethodFullKey(cls, method);
            if (!this.acceptResult(key)) continue;
            LocalVariableAttribute table = (LocalVariableAttribute)((MethodInfo)method).getCodeAttribute().getAttribute("LocalVariableTable");
            int length = table.tableLength();
            int i = Modifier.isStatic(((MethodInfo)method).getAccessFlags()) ? 0 : 1;
            if (i >= length) continue;
            ArrayList<String> names = new ArrayList<String>(length - i);
            while (i < length) {
                names.add(((MethodInfo)method).getConstPool().getUtf8Info(table.nameIndex(i++)));
            }
            this.getStore().put((Object)key, (Object)Joiner.on((String)", ").join(names));
        }
    }
}

