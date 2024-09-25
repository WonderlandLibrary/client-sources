/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package us.myles.ViaVersion.api.minecraft.item;

import java.util.Objects;
import org.jetbrains.annotations.Nullable;
import us.myles.viaversion.libs.gson.annotations.SerializedName;
import us.myles.viaversion.libs.opennbt.tag.builtin.CompoundTag;

public class Item {
    @SerializedName(value="identifier", alternate={"id"})
    private int identifier;
    private byte amount;
    private short data;
    private CompoundTag tag;

    public Item() {
    }

    public Item(int identifier, byte amount, short data, @Nullable CompoundTag tag) {
        this.identifier = identifier;
        this.amount = amount;
        this.data = data;
        this.tag = tag;
    }

    public Item(Item toCopy) {
        this(toCopy.getIdentifier(), toCopy.getAmount(), toCopy.getData(), toCopy.getTag());
    }

    public int getIdentifier() {
        return this.identifier;
    }

    public void setIdentifier(int identifier) {
        this.identifier = identifier;
    }

    public byte getAmount() {
        return this.amount;
    }

    public void setAmount(byte amount) {
        this.amount = amount;
    }

    public short getData() {
        return this.data;
    }

    public void setData(short data) {
        this.data = data;
    }

    @Nullable
    public CompoundTag getTag() {
        return this.tag;
    }

    public void setTag(@Nullable CompoundTag tag) {
        this.tag = tag;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        Item item = (Item)o;
        if (this.identifier != item.identifier) {
            return false;
        }
        if (this.amount != item.amount) {
            return false;
        }
        if (this.data != item.data) {
            return false;
        }
        return Objects.equals(this.tag, item.tag);
    }

    public int hashCode() {
        int result = this.identifier;
        result = 31 * result + this.amount;
        result = 31 * result + this.data;
        result = 31 * result + (this.tag != null ? this.tag.hashCode() : 0);
        return result;
    }

    public String toString() {
        return "Item{identifier=" + this.identifier + ", amount=" + this.amount + ", data=" + this.data + ", tag=" + this.tag + '}';
    }
}

