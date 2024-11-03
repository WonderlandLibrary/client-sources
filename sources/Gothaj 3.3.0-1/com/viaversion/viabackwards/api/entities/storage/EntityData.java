package com.viaversion.viabackwards.api.entities.storage;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.util.ComponentUtil;
import java.util.Locale;
import org.checkerframework.checker.nullness.qual.Nullable;

public class EntityData {
   private final BackwardsProtocol<?, ?, ?, ?> protocol;
   private final int id;
   private final int replacementId;
   private final String key;
   private EntityData.ComponentType componentType = EntityData.ComponentType.NONE;
   private EntityData.MetaCreator defaultMeta;

   public EntityData(BackwardsProtocol<?, ?, ?, ?> protocol, EntityType type, int replacementId) {
      this(protocol, type.name(), type.getId(), replacementId);
   }

   public EntityData(BackwardsProtocol<?, ?, ?, ?> protocol, String key, int id, int replacementId) {
      this.protocol = protocol;
      this.id = id;
      this.replacementId = replacementId;
      this.key = key.toLowerCase(Locale.ROOT);
   }

   public EntityData jsonName() {
      this.componentType = EntityData.ComponentType.JSON;
      return this;
   }

   public EntityData tagName() {
      this.componentType = EntityData.ComponentType.TAG;
      return this;
   }

   public EntityData plainName() {
      this.componentType = EntityData.ComponentType.PLAIN;
      return this;
   }

   public EntityData spawnMetadata(EntityData.MetaCreator handler) {
      this.defaultMeta = handler;
      return this;
   }

   public boolean hasBaseMeta() {
      return this.defaultMeta != null;
   }

   public int typeId() {
      return this.id;
   }

   @Nullable
   public Object entityName() {
      if (this.componentType == EntityData.ComponentType.NONE) {
         return null;
      } else {
         String name = this.protocol.getMappingData().mappedEntityName(this.key);
         if (name == null) {
            return null;
         } else if (this.componentType == EntityData.ComponentType.JSON) {
            return ComponentUtil.legacyToJson(name);
         } else {
            return this.componentType == EntityData.ComponentType.TAG ? new StringTag(name) : name;
         }
      }
   }

   public int replacementId() {
      return this.replacementId;
   }

   @Nullable
   public EntityData.MetaCreator defaultMeta() {
      return this.defaultMeta;
   }

   public boolean isObjectType() {
      return false;
   }

   public int objectData() {
      return -1;
   }

   @Override
   public String toString() {
      return "EntityData{id=" + this.id + ", mobName='" + this.key + '\'' + ", replacementId=" + this.replacementId + ", defaultMeta=" + this.defaultMeta + '}';
   }

   private static enum ComponentType {
      PLAIN,
      JSON,
      TAG,
      NONE;
   }

   @FunctionalInterface
   public interface MetaCreator {
      void createMeta(WrappedMetadata var1);
   }
}
