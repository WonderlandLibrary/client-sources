/*
 * Decompiled with CFR 0.143.
 */
package org.reflections.scanners;

import com.google.common.collect.Multimap;
import java.util.List;
import org.reflections.adapters.MetadataAdapter;
import org.reflections.scanners.AbstractScanner;

public class MethodParameterScanner
extends AbstractScanner {
    public void scan(Object cls) {
        MetadataAdapter md = this.getMetadataAdapter();
        for (Object method : md.getMethods(cls)) {
            String returnTypeName;
            String signature = md.getParameterNames(method).toString();
            if (this.acceptResult(signature)) {
                this.getStore().put((Object)signature, (Object)md.getMethodFullKey(cls, method));
            }
            if (this.acceptResult(returnTypeName = md.getReturnTypeName(method))) {
                this.getStore().put((Object)returnTypeName, (Object)md.getMethodFullKey(cls, method));
            }
            List<String> parameterNames = md.getParameterNames(method);
            for (int i = 0; i < parameterNames.size(); ++i) {
                for (String paramAnnotation : md.getParameterAnnotationNames(method, i)) {
                    if (!this.acceptResult(paramAnnotation)) continue;
                    this.getStore().put((Object)paramAnnotation, (Object)md.getMethodFullKey(cls, method));
                }
            }
        }
    }
}

