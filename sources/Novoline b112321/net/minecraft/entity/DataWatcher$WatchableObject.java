package net.minecraft.entity;

public class DataWatcher$WatchableObject {
   private final int objectType;
   private final int dataValueId;
   private Object watchedObject;
   private boolean watched;

   public DataWatcher$WatchableObject(int var1, int var2, Object var3) {
      this.dataValueId = var2;
      this.watchedObject = var3;
      this.objectType = var1;
      this.watched = true;
   }

   public int getDataValueId() {
      return this.dataValueId;
   }

   public void setObject(Object var1) {
      this.watchedObject = var1;
   }

   public Object getObject() {
      return this.watchedObject;
   }

   public int getObjectType() {
      return this.objectType;
   }

   public boolean isWatched() {
      return this.watched;
   }

   public void setWatched(boolean var1) {
      this.watched = var1;
   }

   static boolean access$002(DataWatcher$WatchableObject var0, boolean var1) {
      return var0.watched = var1;
   }
}
