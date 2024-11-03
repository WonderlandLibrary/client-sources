package com.viaversion.viarewind.api.rewriter.item;

import com.viaversion.viarewind.api.minecraft.IdDataCombine;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.AbstractProtocol;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.api.rewriter.ItemRewriter;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap;

public abstract class ReplacementItemRewriter<T extends AbstractProtocol<?, ?, ?, ?>> implements ItemRewriter<T> {
   private final Int2ObjectMap<Replacement> ITEM_REPLACEMENTS = new Int2ObjectOpenHashMap<>();
   private final Int2ObjectMap<Replacement> BLOCK_REPLACEMENTS = new Int2ObjectOpenHashMap<>();
   private final T protocol;
   private final ProtocolVersion protocolVersion;

   public ReplacementItemRewriter(T protocol, ProtocolVersion protocolVersion) {
      this.protocol = protocol;
      this.protocolVersion = protocolVersion;
   }

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
      this.ITEM_REPLACEMENTS.put(this.generateTrackingId(id, data), replacement);
      replacement.buildNames(this.protocolVersion.getName());
   }

   public void registerBlock(int id, int data, Replacement replacement) {
      this.BLOCK_REPLACEMENTS.put(this.generateTrackingId(id, data), replacement);
      replacement.buildNames(this.protocolVersion.getName());
   }

   public void registerItemBlock(int id, int data, Replacement replacement) {
      this.registerItem(id, data, replacement);
      this.registerBlock(id, data, replacement);
   }

   public Item replace(Item item) {
      Replacement replacement = this.ITEM_REPLACEMENTS.get(this.generateTrackingId(item.identifier(), item.data()));
      if (replacement == null) {
         replacement = this.ITEM_REPLACEMENTS.get(this.generateTrackingId(item.identifier(), -1));
      }

      return replacement == null ? item : replacement.replace(item);
   }

   public Replacement replace(int id, int data) {
      Replacement replacement = this.BLOCK_REPLACEMENTS.get(this.generateTrackingId(id, data));
      if (replacement == null) {
         replacement = this.BLOCK_REPLACEMENTS.get(this.generateTrackingId(id, -1));
      }

      return replacement;
   }

   public int replace(int combined) {
      int data = IdDataCombine.dataFromCombined(combined);
      Replacement replace = this.replace(IdDataCombine.idFromCombined(combined), data);
      return replace != null ? IdDataCombine.toCombined(replace.getId(), replace.replaceData(data)) : combined;
   }

   private int generateTrackingId(int id, int data) {
      return id << 16 | data & 65535;
   }

   public T protocol() {
      return this.protocol;
   }
}
