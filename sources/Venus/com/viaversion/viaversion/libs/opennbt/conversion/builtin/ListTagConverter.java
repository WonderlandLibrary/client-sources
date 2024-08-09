/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.opennbt.conversion.builtin;

import com.viaversion.viaversion.libs.opennbt.conversion.ConverterRegistry;
import com.viaversion.viaversion.libs.opennbt.conversion.TagConverter;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ListTagConverter
implements TagConverter<ListTag, List> {
    @Override
    public List convert(ListTag listTag) {
        ArrayList arrayList = new ArrayList();
        Object object = listTag.getValue();
        Iterator iterator2 = object.iterator();
        while (iterator2.hasNext()) {
            Tag tag = (Tag)iterator2.next();
            arrayList.add(ConverterRegistry.convertToValue(tag));
        }
        return arrayList;
    }

    @Override
    public ListTag convert(List list) {
        ArrayList<Tag> arrayList = new ArrayList<Tag>();
        for (Object e : list) {
            arrayList.add((Tag)ConverterRegistry.convertToTag(e));
        }
        return new ListTag(arrayList);
    }

    @Override
    public Tag convert(Object object) {
        return this.convert((List)object);
    }

    @Override
    public Object convert(Tag tag) {
        return this.convert((ListTag)tag);
    }
}

