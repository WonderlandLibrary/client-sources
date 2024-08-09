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
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import org.jetbrains.annotations.Nullable;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class ListTag
extends Tag
implements Iterable<Tag> {
    public static final int ID = 9;
    private final List<Tag> value;
    private Class<? extends Tag> type;

    public ListTag() {
        this.value = new ArrayList<Tag>();
    }

    public ListTag(@Nullable Class<? extends Tag> clazz) {
        this.type = clazz;
        this.value = new ArrayList<Tag>();
    }

    public ListTag(List<Tag> list) throws IllegalArgumentException {
        this.value = new ArrayList<Tag>(list.size());
        this.setValue(list);
    }

    @Override
    public List<Tag> getValue() {
        return this.value;
    }

    public void setValue(List<Tag> list) throws IllegalArgumentException {
        if (list == null) {
            throw new NullPointerException("value cannot be null");
        }
        this.type = null;
        this.value.clear();
        for (Tag tag : list) {
            this.add(tag);
        }
    }

    public Class<? extends Tag> getElementType() {
        return this.type;
    }

    public boolean add(Tag tag) throws IllegalArgumentException {
        if (tag == null) {
            throw new NullPointerException("tag cannot be null");
        }
        if (this.type == null) {
            this.type = tag.getClass();
        } else if (tag.getClass() != this.type) {
            throw new IllegalArgumentException("Tag type cannot differ from ListTag type.");
        }
        return this.value.add(tag);
    }

    public boolean remove(Tag tag) {
        return this.value.remove(tag);
    }

    public <T extends Tag> T get(int n) {
        return (T)this.value.get(n);
    }

    public int size() {
        return this.value.size();
    }

    @Override
    public Iterator<Tag> iterator() {
        return this.value.iterator();
    }

    @Override
    public void read(DataInput dataInput, TagLimiter tagLimiter, int n) throws IOException {
        this.type = null;
        tagLimiter.checkLevel(n);
        tagLimiter.countBytes(5);
        byte by = dataInput.readByte();
        if (by != 0) {
            this.type = TagRegistry.getClassFor(by);
            if (this.type == null) {
                throw new IOException("Unknown tag ID in ListTag: " + by);
            }
        }
        int n2 = dataInput.readInt();
        int n3 = n + 1;
        for (int i = 0; i < n2; ++i) {
            Tag tag;
            try {
                tag = TagRegistry.createInstance(by);
            } catch (TagCreateException tagCreateException) {
                throw new IOException("Failed to create tag.", tagCreateException);
            }
            tag.read(dataInput, tagLimiter, n3);
            this.add(tag);
        }
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        if (this.type == null) {
            dataOutput.writeByte(0);
        } else {
            int n = TagRegistry.getIdFor(this.type);
            if (n == -1) {
                throw new IOException("ListTag contains unregistered tag class.");
            }
            dataOutput.writeByte(n);
        }
        dataOutput.writeInt(this.value.size());
        for (Tag tag : this.value) {
            tag.write(dataOutput);
        }
    }

    @Override
    public final ListTag clone() {
        ArrayList<Tag> arrayList = new ArrayList<Tag>();
        for (Tag tag : this.value) {
            arrayList.add(tag.clone());
        }
        return new ListTag(arrayList);
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return true;
        }
        ListTag listTag = (ListTag)object;
        if (!Objects.equals(this.type, listTag.type)) {
            return true;
        }
        return this.value.equals(listTag.value);
    }

    public int hashCode() {
        int n = this.type != null ? this.type.hashCode() : 0;
        n = 31 * n + this.value.hashCode();
        return n;
    }

    @Override
    public int getTagId() {
        return 0;
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

