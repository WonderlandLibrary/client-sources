package com.viaversion.viabackwards.api.data;

import com.viaversion.viabackwards.utils.Block;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import org.checkerframework.checker.nullness.qual.Nullable;

public class MappedLegacyBlockItem {
   private final int id;
   private final short data;
   private final String name;
   private final Block block;
   private MappedLegacyBlockItem.BlockEntityHandler blockEntityHandler;

   public MappedLegacyBlockItem(int id, short data, @Nullable String name, boolean block) {
      this.id = id;
      this.data = data;
      this.name = name != null ? "§f" + name : null;
      this.block = block ? new Block(id, data) : null;
   }

   public int getId() {
      return this.id;
   }

   public short getData() {
      return this.data;
   }

   public String getName() {
      return this.name;
   }

   public boolean isBlock() {
      return this.block != null;
   }

   public Block getBlock() {
      return this.block;
   }

   public boolean hasBlockEntityHandler() {
      return this.blockEntityHandler != null;
   }

   @Nullable
   public MappedLegacyBlockItem.BlockEntityHandler getBlockEntityHandler() {
      return this.blockEntityHandler;
   }

   public void setBlockEntityHandler(@Nullable MappedLegacyBlockItem.BlockEntityHandler blockEntityHandler) {
      this.blockEntityHandler = blockEntityHandler;
   }

   @FunctionalInterface
   public interface BlockEntityHandler {
      CompoundTag handleOrNewCompoundTag(int var1, CompoundTag var2);
   }
}
