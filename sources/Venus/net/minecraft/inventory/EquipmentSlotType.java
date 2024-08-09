/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.inventory;

public enum EquipmentSlotType {
    MAINHAND(Group.HAND, 0, 0, "mainhand"),
    OFFHAND(Group.HAND, 1, 5, "offhand"),
    FEET(Group.ARMOR, 0, 1, "feet"),
    LEGS(Group.ARMOR, 1, 2, "legs"),
    CHEST(Group.ARMOR, 2, 3, "chest"),
    HEAD(Group.ARMOR, 3, 4, "head");

    private final Group slotType;
    private final int index;
    private final int slotIndex;
    private final String name;

    private EquipmentSlotType(Group group, int n2, int n3, String string2) {
        this.slotType = group;
        this.index = n2;
        this.slotIndex = n3;
        this.name = string2;
    }

    public Group getSlotType() {
        return this.slotType;
    }

    public int getIndex() {
        return this.index;
    }

    public int getSlotIndex() {
        return this.slotIndex;
    }

    public String getName() {
        return this.name;
    }

    public static EquipmentSlotType fromString(String string) {
        for (EquipmentSlotType equipmentSlotType : EquipmentSlotType.values()) {
            if (!equipmentSlotType.getName().equals(string)) continue;
            return equipmentSlotType;
        }
        throw new IllegalArgumentException("Invalid slot '" + string + "'");
    }

    public static EquipmentSlotType fromSlotTypeAndIndex(Group group, int n) {
        for (EquipmentSlotType equipmentSlotType : EquipmentSlotType.values()) {
            if (equipmentSlotType.getSlotType() != group || equipmentSlotType.getIndex() != n) continue;
            return equipmentSlotType;
        }
        throw new IllegalArgumentException("Invalid slot '" + group + "': " + n);
    }

    public static enum Group {
        HAND,
        ARMOR;

    }
}

