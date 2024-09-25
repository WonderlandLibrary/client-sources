/*
 * Decompiled with CFR 0.150.
 */
package us.myles.ViaVersion.api.type.types.minecraft;

import java.util.List;
import us.myles.ViaVersion.api.minecraft.metadata.Metadata;
import us.myles.ViaVersion.api.type.Type;

public abstract class MetaListTypeTemplate
extends Type<List<Metadata>> {
    protected MetaListTypeTemplate() {
        super("MetaData List", List.class);
    }

    @Override
    public Class<? extends Type> getBaseClass() {
        return MetaListTypeTemplate.class;
    }
}

