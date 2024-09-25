/*
 * Decompiled with CFR 0.150.
 */
package de.gerrygames.viarewind.replacement;

import de.gerrygames.viarewind.replacement.Replacement;
import de.gerrygames.viarewind.storage.BlockState;
import java.util.HashMap;
import us.myles.ViaVersion.api.minecraft.item.Item;

public class ReplacementRegistry {
    private HashMap<Integer, Replacement> itemReplacements = new HashMap();
    private HashMap<Integer, Replacement> blockReplacements = new HashMap();

    public void registerItem(int id, Replacement replacement) {
        this.registerItem(id, -1, replacement);
    }

    public void registerBlock(int id, Replacement replacement) {
        this.registerBlock(id, -1, replacement);
    }

    public void registerItemBlock(int id, Replacement replacement) {
        this.registerItemBlock(id, -1, replacement);
    }

    public void registerItem(int id, int data, Replacement replacement) {
        this.itemReplacements.put(ReplacementRegistry.combine(id, data), replacement);
    }

    public void registerBlock(int id, int data, Replacement replacement) {
        this.blockReplacements.put(ReplacementRegistry.combine(id, data), replacement);
    }

    public void registerItemBlock(int id, int data, Replacement replacement) {
        this.registerItem(id, data, replacement);
        this.registerBlock(id, data, replacement);
    }

    public Item replace(Item item) {
        Replacement replacement = this.itemReplacements.get(ReplacementRegistry.combine(item.getIdentifier(), item.getData()));
        if (replacement == null) {
            replacement = this.itemReplacements.get(ReplacementRegistry.combine(item.getIdentifier(), -1));
        }
        return replacement == null ? item : replacement.replace(item);
    }

    public BlockState replace(BlockState block) {
        Replacement replacement = this.blockReplacements.get(ReplacementRegistry.combine(block.getId(), block.getData()));
        if (replacement == null) {
            replacement = this.blockReplacements.get(ReplacementRegistry.combine(block.getId(), -1));
        }
        return replacement == null ? block : replacement.replace(block);
    }

    private static int combine(int id, int data) {
        return id << 16 | data & 0xFFFF;
    }
}

