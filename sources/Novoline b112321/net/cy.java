package net;

import com.github.steveice10.opennbt.tag.builtin.ByteTag;
import com.github.steveice10.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.util.Pair;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import net.bgR;
import net.cA;
import net.cq;

public class cy extends cA {
   private final Map c = new ConcurrentHashMap();
   private boolean d = false;

   public cy(bgR var1) {
      super(var1);
   }

   public void a(int var1, int var2) {
      Pair var3 = new Pair(Integer.valueOf(var1), Integer.valueOf(var2));
      this.c.remove(var3);
   }

   public void a(Position var1, CompoundTag var2) {
      cq.c();
      Pair var4 = this.a(var1);
      if(!this.c.containsKey(var4)) {
         this.c.put(var4, new ConcurrentHashMap());
      }

      Map var5 = (Map)this.c.get(var4);
      if(!var5.containsKey(var1) || !((CompoundTag)var5.get(var1)).equals(var2)) {
         var5.put(var1, var2);
      }
   }

   private Pair a(Position var1) {
      int var2 = Math.floorDiv(var1.getX(), 16);
      int var3 = Math.floorDiv(var1.getZ(), 16);
      return new Pair(Integer.valueOf(var2), Integer.valueOf(var3));
   }

   public Optional b(Position var1) {
      cq.c();
      Pair var3 = this.a(var1);
      Map var4 = (Map)this.c.get(var3);
      if(var4 == null) {
         return Optional.empty();
      } else {
         CompoundTag var5 = (CompoundTag)var4.get(var1);
         if(var5 == null) {
            return Optional.empty();
         } else {
            var5 = var5.clone();
            var5.put(new ByteTag("powered", (byte)0));
            var5.put(new ByteTag("auto", (byte)0));
            var5.put(new ByteTag("conditionMet", (byte)0));
            return Optional.of(var5);
         }
      }
   }

   public void a() {
      this.c.clear();
   }

   public boolean d() {
      return this.d;
   }

   public void a(boolean var1) {
      this.d = var1;
   }
}
