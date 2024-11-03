package com.viaversion.viaversion.data.entity;

import com.viaversion.viaversion.api.data.entity.StoredEntityData;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class StoredEntityDataImpl implements StoredEntityData {
   private final Map<Class<?>, Object> storedObjects = new ConcurrentHashMap<>();
   private final EntityType type;

   public StoredEntityDataImpl(EntityType type) {
      this.type = type;
   }

   @Override
   public EntityType type() {
      return this.type;
   }

   @Nullable
   @Override
   public <T> T get(Class<T> objectClass) {
      return (T)this.storedObjects.get(objectClass);
   }

   @Nullable
   @Override
   public <T> T remove(Class<T> objectClass) {
      return (T)this.storedObjects.remove(objectClass);
   }

   @Override
   public boolean has(Class<?> objectClass) {
      return this.storedObjects.containsKey(objectClass);
   }

   @Override
   public void put(Object object) {
      this.storedObjects.put(object.getClass(), object);
   }
}
