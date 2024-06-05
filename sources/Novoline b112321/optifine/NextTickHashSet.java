package optifine;

import com.google.common.collect.Iterators;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import net.minecraft.util.BlockPos;
import net.minecraft.util.LongHashMap;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.NextTickListEntry;
import optifine.MatchBlock;

public class NextTickHashSet extends TreeSet {
   private LongHashMap longHashMap = new LongHashMap();
   private int minX = Integer.MIN_VALUE;
   private int minZ = Integer.MIN_VALUE;
   private int maxX;
   private int maxZ;
   private static final int UNDEFINED = Integer.MIN_VALUE;

   public NextTickHashSet(Set var1) {
      MatchBlock.b();
      this.maxX = Integer.MIN_VALUE;
      this.maxZ = Integer.MIN_VALUE;
      Iterator var3 = var1.iterator();
      if(var3.hasNext()) {
         Object var4 = var3.next();
         this.add(var4);
      }

   }

   public boolean contains(Object var1) {
      PacketRemapper[] var2 = MatchBlock.b();
      if(!(var1 instanceof NextTickListEntry)) {
         return false;
      } else {
         NextTickListEntry var3 = (NextTickListEntry)var1;
         Set var4 = this.getSubSet(var3, false);
         return var4 == null?false:var4.contains(var3);
      }
   }

   public boolean add(Object var1) {
      PacketRemapper[] var2 = MatchBlock.b();
      if(!(var1 instanceof NextTickListEntry)) {
         return false;
      } else {
         NextTickListEntry var3 = (NextTickListEntry)var1;
         return false;
      }
   }

   public boolean remove(Object var1) {
      PacketRemapper[] var2 = MatchBlock.b();
      if(!(var1 instanceof NextTickListEntry)) {
         return false;
      } else {
         NextTickListEntry var3 = (NextTickListEntry)var1;
         Set var4 = this.getSubSet(var3, false);
         if(var4 == null) {
            return false;
         } else {
            boolean var5 = var4.remove(var3);
            boolean var6 = super.remove(var3);
            if(var5 != var6) {
               throw new IllegalStateException("Added: " + var5 + ", addedParent: " + var6);
            } else {
               return var6;
            }
         }
      }
   }

   private Set getSubSet(NextTickListEntry var1, boolean var2) {
      PacketRemapper[] var3 = MatchBlock.b();
      if(var1 == null) {
         return null;
      } else {
         BlockPos var4 = var1.position;
         int var5 = var4.getX() >> 4;
         int var6 = var4.getZ() >> 4;
         return this.getSubSet(var5, var6, var2);
      }
   }

   private Set getSubSet(int var1, int var2, boolean var3) {
      MatchBlock.b();
      long var5 = ChunkCoordIntPair.chunkXZ2Int(var1, var2);
      HashSet var7 = (HashSet)this.longHashMap.getValueByKey(var5);
      if(var7 == null) {
         var7 = new HashSet();
         this.longHashMap.add(var5, var7);
      }

      return var7;
   }

   public Iterator iterator() {
      PacketRemapper[] var1 = MatchBlock.b();
      if(this.minX == Integer.MIN_VALUE) {
         return super.iterator();
      } else if(this.size() <= 0) {
         return Iterators.emptyIterator();
      } else {
         int var2 = this.minX >> 4;
         int var3 = this.minZ >> 4;
         int var4 = this.maxX >> 4;
         int var5 = this.maxZ >> 4;
         ArrayList var6 = new ArrayList();
         if(var2 <= var4) {
            if(var3 <= var5) {
               Set var9 = this.getSubSet(var2, var3, false);
               if(var9 != null) {
                  var6.add(var9.iterator());
               }

               int var8 = var3 + 1;
            }

            int var7 = var2 + 1;
         }

         return (Iterator)(var6.size() <= 0?Iterators.emptyIterator():(var6.size() == 1?(Iterator)var6.get(0):Iterators.concat(var6.iterator())));
      }
   }

   public void setIteratorLimits(int var1, int var2, int var3, int var4) {
      this.minX = Math.min(var1, var3);
      this.minZ = Math.min(var2, var4);
      this.maxX = Math.max(var1, var3);
      this.maxZ = Math.max(var2, var4);
   }

   public void clearIteratorLimits() {
      this.minX = Integer.MIN_VALUE;
      this.minZ = Integer.MIN_VALUE;
      this.maxX = Integer.MIN_VALUE;
      this.maxZ = Integer.MIN_VALUE;
   }

   private static IllegalStateException a(IllegalStateException var0) {
      return var0;
   }
}
