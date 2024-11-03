package com.viaversion.viarewind.api.rewriter;

import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.model.MetaIndex1_7_6_10To1_8;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.types.metadata.MetaType1_7_6_10;
import com.viaversion.viaversion.api.connection.StoredObject;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.data.entity.ClientEntityIdChangeListener;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_10;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.util.Pair;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReplacementEntityTracker extends StoredObject implements ClientEntityIdChangeListener {
   private final Map<EntityTypes1_10.EntityType, Pair<EntityTypes1_10.EntityType, String>> ENTITY_REPLACEMENTS = new HashMap<>();
   private final Map<Integer, EntityTypes1_10.EntityType> entityMap = new HashMap<>();
   private final Map<Integer, EntityTypes1_10.EntityType> entityReplacementMap = new HashMap<>();
   private int playerId = -1;
   private final ProtocolVersion version;

   public ReplacementEntityTracker(UserConnection user, ProtocolVersion version) {
      super(user);
      this.version = version;
   }

   public void registerEntity(EntityTypes1_10.EntityType oldType, EntityTypes1_10.EntityType newType, String name) {
      this.ENTITY_REPLACEMENTS.put(oldType, new Pair<>(newType, this.version.getName() + " " + name));
   }

   public void addEntity(int entityId, EntityTypes1_10.EntityType type) {
      this.entityMap.put(entityId, type);
   }

   public int replaceEntity(int entityId, EntityTypes1_10.EntityType type) {
      this.entityReplacementMap.put(entityId, type);
      return this.ENTITY_REPLACEMENTS.get(type).key().getId();
   }

   public void removeEntity(int entityId) {
      this.entityMap.remove(entityId);
      this.entityReplacementMap.remove(entityId);
   }

   public void clear() {
      this.entityMap.clear();
      this.entityReplacementMap.clear();
   }

   public boolean isReplaced(EntityTypes1_10.EntityType type) {
      return this.ENTITY_REPLACEMENTS.containsKey(type);
   }

   public void updateMetadata(int entityId, List<Metadata> metadata) throws Exception {
      String name = this.ENTITY_REPLACEMENTS.get(this.entityMap.get(entityId)).value();
      metadata.add(new Metadata(MetaIndex1_7_6_10To1_8.ENTITY_LIVING_NAME_TAG_VISIBILITY.getNewIndex(), MetaType1_7_6_10.Byte, (byte)1));
      metadata.add(new Metadata(MetaIndex1_7_6_10To1_8.ENTITY_LIVING_NAME_TAG.getNewIndex(), MetaType1_7_6_10.String, name));
   }

   @Override
   public void setClientEntityId(int entityId) {
      this.removeEntity(this.playerId);
      this.addEntity(entityId, EntityTypes1_10.EntityType.ENTITY_HUMAN);
      this.playerId = entityId;
   }

   public Map<Integer, EntityTypes1_10.EntityType> getEntityMap() {
      return this.entityMap;
   }

   public Map<Integer, EntityTypes1_10.EntityType> getEntityReplacementMap() {
      return this.entityReplacementMap;
   }

   public int getPlayerId() {
      return this.playerId;
   }
}
