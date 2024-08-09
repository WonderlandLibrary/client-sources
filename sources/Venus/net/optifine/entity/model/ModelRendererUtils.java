/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.entity.model;

import com.google.common.collect.ImmutableList;
import java.util.Iterator;
import net.minecraft.client.renderer.model.ModelRenderer;

public class ModelRendererUtils {
    public static ModelRenderer getModelRenderer(Iterator<ModelRenderer> iterator2, int n) {
        if (iterator2 == null) {
            return null;
        }
        if (n < 0) {
            return null;
        }
        for (int i = 0; i < n; ++i) {
            if (!iterator2.hasNext()) {
                return null;
            }
            ModelRenderer modelRenderer = iterator2.next();
        }
        return !iterator2.hasNext() ? null : iterator2.next();
    }

    public static ModelRenderer getModelRenderer(ImmutableList<ModelRenderer> immutableList, int n) {
        if (immutableList == null) {
            return null;
        }
        if (n < 0) {
            return null;
        }
        return n >= immutableList.size() ? null : (ModelRenderer)immutableList.get(n);
    }
}

