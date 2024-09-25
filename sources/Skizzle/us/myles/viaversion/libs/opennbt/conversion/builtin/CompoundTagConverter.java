/*
 * Decompiled with CFR 0.150.
 */
package us.myles.viaversion.libs.opennbt.conversion.builtin;

import java.util.HashMap;
import java.util.Map;
import us.myles.viaversion.libs.opennbt.conversion.ConverterRegistry;
import us.myles.viaversion.libs.opennbt.conversion.TagConverter;
import us.myles.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.Tag;

public class CompoundTagConverter
implements TagConverter<CompoundTag, Map> {
    @Override
    public Map convert(CompoundTag tag) {
        HashMap ret = new HashMap();
        Object tags = tag.getValue();
        for (String name : tags.keySet()) {
            Tag t = (Tag)tags.get(name);
            ret.put(t.getName(), ConverterRegistry.convertToValue(t));
        }
        return ret;
    }

    @Override
    public CompoundTag convert(String name, Map value) {
        HashMap<String, Tag> tags = new HashMap<String, Tag>();
        for (Object na : value.keySet()) {
            String n = (String)na;
            tags.put(n, (Tag)ConverterRegistry.convertToTag(n, value.get(n)));
        }
        return new CompoundTag(name, tags);
    }
}

