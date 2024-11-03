package com.viaversion.viaversion.api.data.entity;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import java.util.Map;
import org.checkerframework.checker.nullness.qual.Nullable;

public interface EntityTracker {
   UserConnection user();

   void addEntity(int var1, EntityType var2);

   boolean hasEntity(int var1);

   @Nullable
   TrackedEntity entity(int var1);

   @Nullable
   EntityType entityType(int var1);

   void removeEntity(int var1);

   void clearEntities();

   @Nullable
   StoredEntityData entityData(int var1);

   @Nullable
   StoredEntityData entityDataIfPresent(int var1);

   int clientEntityId();

   void setClientEntityId(int var1);

   int currentWorldSectionHeight();

   void setCurrentWorldSectionHeight(int var1);

   int currentMinY();

   void setCurrentMinY(int var1);

   @Nullable
   String currentWorld();

   void setCurrentWorld(String var1);

   int biomesSent();

   void setBiomesSent(int var1);

   EntityType playerType();

   @Nullable
   DimensionData dimensionData(String var1);

   void setDimensions(Map<String, DimensionData> var1);

   boolean trackClientEntity();
}
