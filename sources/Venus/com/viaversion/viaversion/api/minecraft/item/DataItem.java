/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.minecraft.item;

import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.libs.gson.annotations.SerializedName;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

public class DataItem
implements Item {
    @SerializedName(value="identifier", alternate={"id"})
    private int identifier;
    private byte amount;
    private short data;
    private CompoundTag tag;

    public DataItem() {
    }

    public DataItem(int n, byte by, short s, @Nullable CompoundTag compoundTag) {
        this.identifier = n;
        this.amount = by;
        this.data = s;
        this.tag = compoundTag;
    }

    public DataItem(Item item) {
        this(item.identifier(), (byte)item.amount(), item.data(), item.tag());
    }

    @Override
    public int identifier() {
        return this.identifier;
    }

    @Override
    public void setIdentifier(int n) {
        this.identifier = n;
    }

    @Override
    public int amount() {
        return this.amount;
    }

    @Override
    public void setAmount(int n) {
        if (n > 127 || n < -128) {
            throw new IllegalArgumentException("Invalid item amount: " + n);
        }
        this.amount = (byte)n;
    }

    @Override
    public short data() {
        return this.data;
    }

    @Override
    public void setData(short s) {
        this.data = s;
    }

    @Override
    public @Nullable CompoundTag tag() {
        return this.tag;
    }

    @Override
    public void setTag(@Nullable CompoundTag compoundTag) {
        this.tag = compoundTag;
    }

    @Override
    public Item copy() {
        return new DataItem(this.identifier, this.amount, this.data, this.tag);
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return true;
        }
        DataItem dataItem = (DataItem)object;
        if (this.identifier != dataItem.identifier) {
            return true;
        }
        if (this.amount != dataItem.amount) {
            return true;
        }
        if (this.data != dataItem.data) {
            return true;
        }
        return Objects.equals(this.tag, dataItem.tag);
    }

    public int hashCode() {
        int n = this.identifier;
        n = 31 * n + this.amount;
        n = 31 * n + this.data;
        n = 31 * n + (this.tag != null ? this.tag.hashCode() : 0);
        return n;
    }

    public String toString() {
        return "Item{identifier=" + this.identifier + ", amount=" + this.amount + ", data=" + this.data + ", tag=" + this.tag + '}';
    }
}

