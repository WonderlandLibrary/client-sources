/*
 * Decompiled with CFR 0.150.
 */
package us.myles.viaversion.libs.opennbt.conversion.builtin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import us.myles.viaversion.libs.opennbt.conversion.ConverterRegistry;
import us.myles.viaversion.libs.opennbt.conversion.TagConverter;
import us.myles.viaversion.libs.opennbt.tag.builtin.ListTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.Tag;

public class ListTagConverter
implements TagConverter<ListTag, List> {
    @Override
    public List convert(ListTag tag) {
        ArrayList ret = new ArrayList();
        Object tags = tag.getValue();
        Iterator iterator = tags.iterator();
        while (iterator.hasNext()) {
            Tag t = (Tag)iterator.next();
            ret.add(ConverterRegistry.convertToValue(t));
        }
        return ret;
    }

    @Override
    public ListTag convert(String name, List value) {
        ArrayList<Tag> tags = new ArrayList<Tag>();
        for (Object o : value) {
            tags.add((Tag)ConverterRegistry.convertToTag("", o));
        }
        return new ListTag(name, tags);
    }
}

