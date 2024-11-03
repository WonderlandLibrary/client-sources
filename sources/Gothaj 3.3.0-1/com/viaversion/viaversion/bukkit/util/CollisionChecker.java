package com.viaversion.viaversion.bukkit.util;

import com.viaversion.viaversion.api.Via;
import java.lang.reflect.Method;
import java.util.AbstractList;
import java.util.List;
import java.util.logging.Level;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;

public class CollisionChecker {
   private static final CollisionChecker INSTANCE;
   private final Method GET_ENTITY_HANDLE;
   private final Method GET_ENTITY_BB;
   private final Method GET_BLOCK_BY_ID;
   private final Method GET_WORLD_HANDLE;
   private final Method GET_BLOCK_TYPE;
   private final Method GET_COLLISIONS;
   private final Method SET_POSITION;
   private final Object BLOCK_POSITION;

   private CollisionChecker() throws ReflectiveOperationException {
      Class<?> blockPosition = NMSUtil.nms("BlockPosition");
      Class<?> mutableBlockPosition = NMSUtil.nms("BlockPosition$MutableBlockPosition");
      Class<?> world = NMSUtil.nms("World");
      this.GET_ENTITY_HANDLE = NMSUtil.obc("entity.CraftEntity").getDeclaredMethod("getHandle");
      this.GET_ENTITY_BB = this.GET_ENTITY_HANDLE.getReturnType().getDeclaredMethod("getBoundingBox");
      this.GET_WORLD_HANDLE = NMSUtil.obc("CraftWorld").getDeclaredMethod("getHandle");
      this.GET_BLOCK_TYPE = world.getDeclaredMethod("getType", blockPosition);
      this.GET_BLOCK_BY_ID = NMSUtil.nms("Block").getDeclaredMethod("getById", int.class);
      this.GET_COLLISIONS = this.GET_BLOCK_BY_ID
         .getReturnType()
         .getDeclaredMethod(
            "a",
            world,
            blockPosition,
            this.GET_BLOCK_TYPE.getReturnType(),
            this.GET_ENTITY_BB.getReturnType(),
            List.class,
            this.GET_ENTITY_HANDLE.getReturnType()
         );
      this.SET_POSITION = mutableBlockPosition.getDeclaredMethod("c", int.class, int.class, int.class);
      this.BLOCK_POSITION = mutableBlockPosition.getConstructor().newInstance();
   }

   public static CollisionChecker getInstance() {
      return INSTANCE;
   }

   public Boolean intersects(Block block, Entity entity) {
      try {
         Object nmsPlayer = this.GET_ENTITY_HANDLE.invoke(entity);
         Object nmsBlock = this.GET_BLOCK_BY_ID.invoke(null, block.getType().getId());
         Object nmsWorld = this.GET_WORLD_HANDLE.invoke(block.getWorld());
         this.SET_POSITION.invoke(this.BLOCK_POSITION, block.getX(), block.getY(), block.getZ());
         List<?> collisions = new CollisionChecker.DummyList();
         this.GET_COLLISIONS
            .invoke(
               nmsBlock,
               nmsWorld,
               this.BLOCK_POSITION,
               this.GET_BLOCK_TYPE.invoke(nmsWorld, this.BLOCK_POSITION),
               this.GET_ENTITY_BB.invoke(nmsPlayer),
               collisions,
               nmsPlayer
            );
         return !collisions.isEmpty();
      } catch (ReflectiveOperationException var7) {
         return null;
      }
   }

   static {
      CollisionChecker instance = null;

      try {
         instance = new CollisionChecker();
      } catch (ReflectiveOperationException var2) {
         Via.getPlatform()
            .getLogger()
            .log(
               Level.WARNING,
               "Couldn't find reflection methods/fields to calculate bounding boxes.\nPlacing non-full blocks where the player stands may fail.",
               (Throwable)var2
            );
      }

      INSTANCE = instance;
   }

   private static class DummyList<T> extends AbstractList<T> {
      private boolean any = false;

      private DummyList() {
      }

      @Override
      public T get(int index) {
         throw new UnsupportedOperationException();
      }

      @Override
      public void add(int idx, T el) {
         this.any = true;
      }

      @Override
      public int size() {
         return this.any ? 1 : 0;
      }
   }
}
