package net.minecraft.entity;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ReportedException;
import net.minecraft.util.Rotations;
import org.apache.commons.lang3.ObjectUtils;

public class DataWatcher {
   private ReadWriteLock lock = new ReentrantReadWriteLock();
   private boolean objectChanged;
   private final Entity owner;
   private final Map watchedObjects = Maps.newHashMap();
   private boolean isBlank = true;
   private static final String __OBFID = "CL_00001559";
   private static final Map dataTypes = Maps.newHashMap();

   public DataWatcher(Entity var1) {
      this.owner = var1;
   }

   public void updateObject(int var1, Object var2) {
      DataWatcher.WatchableObject var3 = this.getWatchedObject(var1);
      if (ObjectUtils.notEqual(var2, var3.getObject())) {
         var3.setObject(var2);
         this.owner.func_145781_i(var1);
         var3.setWatched(true);
         this.objectChanged = true;
      }

   }

   static {
      dataTypes.put(Byte.class, 0);
      dataTypes.put(Short.class, 1);
      dataTypes.put(Integer.class, 2);
      dataTypes.put(Float.class, 3);
      dataTypes.put(String.class, 4);
      dataTypes.put(ItemStack.class, 5);
      dataTypes.put(BlockPos.class, 6);
      dataTypes.put(Rotations.class, 7);
   }

   public void addObject(int var1, Object var2) {
      Integer var3 = (Integer)dataTypes.get(var2.getClass());
      if (var3 == null) {
         throw new IllegalArgumentException(String.valueOf((new StringBuilder("Unknown data type: ")).append(var2.getClass())));
      } else if (var1 > 31) {
         throw new IllegalArgumentException(String.valueOf((new StringBuilder("Data value id is too big with ")).append(var1).append("! (Max is ").append(31).append(")")));
      } else if (this.watchedObjects.containsKey(var1)) {
         throw new IllegalArgumentException(String.valueOf((new StringBuilder("Duplicate id value for ")).append(var1).append("!")));
      } else {
         DataWatcher.WatchableObject var4 = new DataWatcher.WatchableObject(var3, var1, var2);
         this.lock.writeLock().lock();
         this.watchedObjects.put(var1, var4);
         this.lock.writeLock().unlock();
         this.isBlank = false;
      }
   }

   public boolean getIsBlank() {
      return this.isBlank;
   }

   private DataWatcher.WatchableObject getWatchedObject(int var1) {
      this.lock.readLock().lock();

      DataWatcher.WatchableObject var2;
      try {
         var2 = (DataWatcher.WatchableObject)this.watchedObjects.get(var1);
      } catch (Throwable var6) {
         CrashReport var4 = CrashReport.makeCrashReport(var6, "Getting synched entity data");
         CrashReportCategory var5 = var4.makeCategory("Synched entity data");
         var5.addCrashSection("Data ID", var1);
         throw new ReportedException(var4);
      }

      this.lock.readLock().unlock();
      return var2;
   }

   public void addObjectByDataType(int var1, int var2) {
      DataWatcher.WatchableObject var3 = new DataWatcher.WatchableObject(var2, var1, (Object)null);
      this.lock.writeLock().lock();
      this.watchedObjects.put(var1, var3);
      this.lock.writeLock().unlock();
      this.isBlank = false;
   }

   public float getWatchableObjectFloat(int var1) {
      return (Float)this.getWatchedObject(var1).getObject();
   }

   public List getAllWatched() {
      ArrayList var1 = null;
      this.lock.readLock().lock();

      DataWatcher.WatchableObject var2;
      for(Iterator var3 = this.watchedObjects.values().iterator(); var3.hasNext(); var1.add(var2)) {
         var2 = (DataWatcher.WatchableObject)var3.next();
         if (var1 == null) {
            var1 = Lists.newArrayList();
         }
      }

      this.lock.readLock().unlock();
      return var1;
   }

   public static List readWatchedListFromPacketBuffer(PacketBuffer var0) throws IOException {
      ArrayList var1 = null;

      for(byte var2 = var0.readByte(); var2 != 127; var2 = var0.readByte()) {
         if (var1 == null) {
            var1 = Lists.newArrayList();
         }

         int var3 = (var2 & 224) >> 5;
         int var4 = var2 & 31;
         DataWatcher.WatchableObject var5 = null;
         switch(var3) {
         case 0:
            var5 = new DataWatcher.WatchableObject(var3, var4, var0.readByte());
            break;
         case 1:
            var5 = new DataWatcher.WatchableObject(var3, var4, var0.readShort());
            break;
         case 2:
            var5 = new DataWatcher.WatchableObject(var3, var4, var0.readInt());
            break;
         case 3:
            var5 = new DataWatcher.WatchableObject(var3, var4, var0.readFloat());
            break;
         case 4:
            var5 = new DataWatcher.WatchableObject(var3, var4, var0.readStringFromBuffer(32767));
            break;
         case 5:
            var5 = new DataWatcher.WatchableObject(var3, var4, var0.readItemStackFromBuffer());
            break;
         case 6:
            int var6 = var0.readInt();
            int var7 = var0.readInt();
            int var8 = var0.readInt();
            var5 = new DataWatcher.WatchableObject(var3, var4, new BlockPos(var6, var7, var8));
            break;
         case 7:
            float var9 = var0.readFloat();
            float var10 = var0.readFloat();
            float var11 = var0.readFloat();
            var5 = new DataWatcher.WatchableObject(var3, var4, new Rotations(var9, var10, var11));
         }

         var1.add(var5);
      }

      return var1;
   }

   public byte getWatchableObjectByte(int var1) {
      return (Byte)this.getWatchedObject(var1).getObject();
   }

   public ItemStack getWatchableObjectItemStack(int var1) {
      return (ItemStack)this.getWatchedObject(var1).getObject();
   }

   public boolean hasObjectChanged() {
      return this.objectChanged;
   }

   public int getWatchableObjectInt(int var1) {
      return (Integer)this.getWatchedObject(var1).getObject();
   }

   public Rotations getWatchableObjectRotations(int var1) {
      return (Rotations)this.getWatchedObject(var1).getObject();
   }

   public void updateWatchedObjectsFromList(List var1) {
      this.lock.writeLock().lock();
      Iterator var2 = var1.iterator();

      while(var2.hasNext()) {
         DataWatcher.WatchableObject var3 = (DataWatcher.WatchableObject)var2.next();
         DataWatcher.WatchableObject var4 = (DataWatcher.WatchableObject)this.watchedObjects.get(var3.getDataValueId());
         if (var4 != null) {
            var4.setObject(var3.getObject());
            this.owner.func_145781_i(var3.getDataValueId());
         }
      }

      this.lock.writeLock().unlock();
      this.objectChanged = true;
   }

   public static void writeWatchedListToPacketBuffer(List var0, PacketBuffer var1) throws IOException {
      if (var0 != null) {
         Iterator var2 = var0.iterator();

         while(var2.hasNext()) {
            DataWatcher.WatchableObject var3 = (DataWatcher.WatchableObject)var2.next();
            writeWatchableObjectToPacketBuffer(var1, var3);
         }
      }

      var1.writeByte(127);
   }

   public void writeTo(PacketBuffer var1) throws IOException {
      this.lock.readLock().lock();
      Iterator var2 = this.watchedObjects.values().iterator();

      while(var2.hasNext()) {
         DataWatcher.WatchableObject var3 = (DataWatcher.WatchableObject)var2.next();
         writeWatchableObjectToPacketBuffer(var1, var3);
      }

      this.lock.readLock().unlock();
      var1.writeByte(127);
   }

   public short getWatchableObjectShort(int var1) {
      return (Short)this.getWatchedObject(var1).getObject();
   }

   public List getChanged() {
      ArrayList var1 = null;
      if (this.objectChanged) {
         this.lock.readLock().lock();
         Iterator var2 = this.watchedObjects.values().iterator();

         while(var2.hasNext()) {
            DataWatcher.WatchableObject var3 = (DataWatcher.WatchableObject)var2.next();
            if (var3.isWatched()) {
               var3.setWatched(false);
               if (var1 == null) {
                  var1 = Lists.newArrayList();
               }

               var1.add(var3);
            }
         }

         this.lock.readLock().unlock();
      }

      this.objectChanged = false;
      return var1;
   }

   public String getWatchableObjectString(int var1) {
      return (String)this.getWatchedObject(var1).getObject();
   }

   public void setObjectWatched(int var1) {
      DataWatcher.WatchableObject.access$0(this.getWatchedObject(var1), true);
      this.objectChanged = true;
   }

   public void func_111144_e() {
      this.objectChanged = false;
   }

   private static void writeWatchableObjectToPacketBuffer(PacketBuffer var0, DataWatcher.WatchableObject var1) throws IOException {
      int var2 = (var1.getObjectType() << 5 | var1.getDataValueId() & 31) & 255;
      var0.writeByte(var2);
      switch(var1.getObjectType()) {
      case 0:
         var0.writeByte((Byte)var1.getObject());
         break;
      case 1:
         var0.writeShort((Short)var1.getObject());
         break;
      case 2:
         var0.writeInt((Integer)var1.getObject());
         break;
      case 3:
         var0.writeFloat((Float)var1.getObject());
         break;
      case 4:
         var0.writeString((String)var1.getObject());
         break;
      case 5:
         ItemStack var3 = (ItemStack)var1.getObject();
         var0.writeItemStackToBuffer(var3);
         break;
      case 6:
         BlockPos var4 = (BlockPos)var1.getObject();
         var0.writeInt(var4.getX());
         var0.writeInt(var4.getY());
         var0.writeInt(var4.getZ());
         break;
      case 7:
         Rotations var5 = (Rotations)var1.getObject();
         var0.writeFloat(var5.func_179415_b());
         var0.writeFloat(var5.func_179416_c());
         var0.writeFloat(var5.func_179413_d());
      }

   }

   public static class WatchableObject {
      private final int objectType;
      private boolean watched;
      private Object watchedObject;
      private final int dataValueId;
      private static final String __OBFID = "CL_00001560";

      public int getDataValueId() {
         return this.dataValueId;
      }

      public boolean isWatched() {
         return this.watched;
      }

      public Object getObject() {
         return this.watchedObject;
      }

      public int getObjectType() {
         return this.objectType;
      }

      public void setObject(Object var1) {
         this.watchedObject = var1;
      }

      public void setWatched(boolean var1) {
         this.watched = var1;
      }

      public WatchableObject(int var1, int var2, Object var3) {
         this.dataValueId = var2;
         this.watchedObject = var3;
         this.objectType = var1;
         this.watched = true;
      }

      static void access$0(DataWatcher.WatchableObject var0, boolean var1) {
         var0.watched = var1;
      }
   }
}
