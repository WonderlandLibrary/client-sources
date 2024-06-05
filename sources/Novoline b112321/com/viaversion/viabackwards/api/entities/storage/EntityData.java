package com.viaversion.viabackwards.api.entities.storage;

import com.viaversion.viabackwards.api.entities.storage.EntityData$MetaCreator;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ChatRewriter;
import org.jetbrains.annotations.Nullable;

public class EntityData {
   private final int id;
   private final int replacementId;
   private Object mobName;
   private EntityData$MetaCreator defaultMeta;

   public EntityData(int var1, int var2) {
      this.id = var1;
      this.replacementId = var2;
   }

   public EntityData jsonName(String var1) {
      this.mobName = ChatRewriter.a(var1);
      return this;
   }

   public EntityData mobName(String var1) {
      this.mobName = var1;
      return this;
   }

   public EntityData spawnMetadata(EntityData$MetaCreator var1) {
      this.defaultMeta = var1;
      return this;
   }

   public boolean hasBaseMeta() {
      return this.defaultMeta != null;
   }

   public int typeId() {
      return this.id;
   }

   @Nullable
   public Object mobName() {
      return this.mobName;
   }

   public int replacementId() {
      return this.replacementId;
   }

   @Nullable
   public EntityData$MetaCreator defaultMeta() {
      return this.defaultMeta;
   }

   public boolean isObjectType() {
      return false;
   }

   public int objectData() {
      return -1;
   }

   public String toString() {
      return "EntityData{id=" + this.id + ", mobName=\'" + this.mobName + '\'' + ", replacementId=" + this.replacementId + ", defaultMeta=" + this.defaultMeta + '}';
   }
}
