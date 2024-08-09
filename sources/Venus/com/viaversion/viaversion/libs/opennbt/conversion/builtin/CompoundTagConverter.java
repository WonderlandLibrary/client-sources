/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.opennbt.conversion.builtin;

import com.viaversion.viaversion.libs.opennbt.conversion.ConverterRegistry;
import com.viaversion.viaversion.libs.opennbt.conversion.TagConverter;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import java.util.HashMap;
import java.util.Map;

public class CompoundTagConverter
implements TagConverter<CompoundTag, Map> {
    @Override
    public Map convert(CompoundTag compoundTag) {
        HashMap hashMap = new HashMap();
        Object object = compoundTag.getValue();
        for (Map.Entry entry : object.entrySet()) {
            hashMap.put((String)entry.getKey(), ConverterRegistry.convertToValue((Tag)entry.getValue()));
        }
        return hashMap;
    }

    @Override
    public CompoundTag convert(Map map) {
        HashMap<String, Tag> hashMap = new HashMap<String, Tag>();
        for (Object k : map.keySet()) {
            String string = (String)k;
            hashMap.put(string, (Tag)ConverterRegistry.convertToTag(map.get(string)));
        }
        return new CompoundTag(hashMap);
    }

    @Override
    public Tag convert(Object object) {
        return this.convert((Map)object);
    }

    @Override
    public Object convert(Tag tag) {
        return this.convert((CompoundTag)tag);
    }
}

