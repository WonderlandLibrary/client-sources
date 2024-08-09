/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.opennbt.tag.builtin;

import com.viaversion.viaversion.libs.opennbt.tag.TagCreateException;
import com.viaversion.viaversion.libs.opennbt.tag.TagRegistry;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.libs.opennbt.tag.limiter.TagLimiter;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.EOFException;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import org.jetbrains.annotations.Nullable;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class CompoundTag
extends Tag
implements Iterable<Map.Entry<String, Tag>> {
    public static final int ID = 10;
    private Map<String, Tag> value;

    public CompoundTag() {
        this(new LinkedHashMap<String, Tag>());
    }

    public CompoundTag(Map<String, Tag> map) {
        this.value = new LinkedHashMap<String, Tag>(map);
    }

    public CompoundTag(LinkedHashMap<String, Tag> linkedHashMap) {
        if (linkedHashMap == null) {
            throw new NullPointerException("value cannot be null");
        }
        this.value = linkedHashMap;
    }

    @Override
    public Map<String, Tag> getValue() {
        return this.value;
    }

    public void setValue(Map<String, Tag> map) {
        if (map == null) {
            throw new NullPointerException("value cannot be null");
        }
        this.value = new LinkedHashMap<String, Tag>(map);
    }

    public void setValue(LinkedHashMap<String, Tag> linkedHashMap) {
        if (linkedHashMap == null) {
            throw new NullPointerException("value cannot be null");
        }
        this.value = linkedHashMap;
    }

    public boolean isEmpty() {
        return this.value.isEmpty();
    }

    public boolean contains(String string) {
        return this.value.containsKey(string);
    }

    @Nullable
    public <T extends Tag> T get(String string) {
        return (T)this.value.get(string);
    }

    @Nullable
    public <T extends Tag> T put(String string, T t) {
        return (T)this.value.put(string, t);
    }

    @Nullable
    public <T extends Tag> T remove(String string) {
        return (T)this.value.remove(string);
    }

    public Set<String> keySet() {
        return this.value.keySet();
    }

    public Collection<Tag> values() {
        return this.value.values();
    }

    public Set<Map.Entry<String, Tag>> entrySet() {
        return this.value.entrySet();
    }

    public int size() {
        return this.value.size();
    }

    public void clear() {
        this.value.clear();
    }

    @Override
    public Iterator<Map.Entry<String, Tag>> iterator() {
        return this.value.entrySet().iterator();
    }

    @Override
    public void read(DataInput dataInput, TagLimiter tagLimiter, int n) throws IOException {
        try {
            tagLimiter.checkLevel(n);
            int n2 = n + 1;
            while (true) {
                tagLimiter.countByte();
                byte by = dataInput.readByte();
                if (by != 0) {
                    String string = dataInput.readUTF();
                    tagLimiter.countBytes(2 * string.length());
                    Tag tag = TagRegistry.createInstance(by);
                    tag.read(dataInput, tagLimiter, n2);
                    this.value.put(string, tag);
                    continue;
                }
                break;
            }
        } catch (TagCreateException tagCreateException) {
            throw new IOException("Failed to create tag.", tagCreateException);
        } catch (EOFException eOFException) {
            throw new IOException("Closing tag was not found!");
        }
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        for (Map.Entry<String, Tag> entry : this.value.entrySet()) {
            Tag tag = entry.getValue();
            dataOutput.writeByte(tag.getTagId());
            dataOutput.writeUTF(entry.getKey());
            tag.write(dataOutput);
        }
        dataOutput.writeByte(0);
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return true;
        }
        CompoundTag compoundTag = (CompoundTag)object;
        return this.value.equals(compoundTag.value);
    }

    public int hashCode() {
        return this.value.hashCode();
    }

    @Override
    public final CompoundTag clone() {
        LinkedHashMap<String, Tag> linkedHashMap = new LinkedHashMap<String, Tag>();
        for (Map.Entry<String, Tag> entry : this.value.entrySet()) {
            linkedHashMap.put(entry.getKey(), entry.getValue().clone());
        }
        return new CompoundTag(linkedHashMap);
    }

    @Override
    public int getTagId() {
        return 1;
    }

    @Override
    public Tag clone() {
        return this.clone();
    }

    @Override
    public Object getValue() {
        return this.getValue();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return this.clone();
    }
}

