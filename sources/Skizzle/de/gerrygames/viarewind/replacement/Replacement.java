/*
 * Decompiled with CFR 0.150.
 */
package de.gerrygames.viarewind.replacement;

import de.gerrygames.viarewind.storage.BlockState;
import us.myles.ViaVersion.api.minecraft.item.Item;
import us.myles.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.StringTag;

public class Replacement {
    private int id;
    private int data;
    private String name;
    private String resetName;
    private String bracketName;

    public Replacement(int id) {
        this(id, -1);
    }

    public Replacement(int id, int data) {
        this(id, data, null);
    }

    public Replacement(int id, String name) {
        this(id, -1, name);
    }

    public Replacement(int id, int data, String name) {
        this.id = id;
        this.data = data;
        this.name = name;
        if (name != null) {
            this.resetName = "\u00a7r" + name;
            this.bracketName = " \u00a7r\u00a77(" + name + "\u00a7r\u00a77)";
        }
    }

    public int getId() {
        return this.id;
    }

    public int getData() {
        return this.data;
    }

    public String getName() {
        return this.name;
    }

    public Item replace(Item item) {
        item.setIdentifier(this.id);
        if (this.data != -1) {
            item.setData((short)this.data);
        }
        if (this.name != null) {
            CompoundTag display;
            CompoundTag compoundTag;
            CompoundTag compoundTag2 = compoundTag = item.getTag() == null ? new CompoundTag("") : item.getTag();
            if (!compoundTag.contains("display")) {
                compoundTag.put(new CompoundTag("display"));
            }
            if ((display = (CompoundTag)compoundTag.get("display")).contains("Name")) {
                StringTag name = (StringTag)display.get("Name");
                if (!name.getValue().equals(this.resetName) && !name.getValue().endsWith(this.bracketName)) {
                    name.setValue(name.getValue() + this.bracketName);
                }
            } else {
                display.put(new StringTag("Name", this.resetName));
            }
            item.setTag(compoundTag);
        }
        return item;
    }

    public BlockState replace(BlockState block) {
        return new BlockState(this.id, this.data == -1 ? block.getData() : this.data);
    }
}

