package com.viaversion.viabackwards.api.rewriters;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.api.rewriters.LegacySoundRewriter$SoundData;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap.Entry;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import net.aqu;

/** @deprecated */
@Deprecated
public abstract class LegacySoundRewriter extends aqu {
   protected final Int2ObjectMap a = new Int2ObjectOpenHashMap(64);

   protected LegacySoundRewriter(BackwardsProtocol var1) {
      super(var1);
   }

   public LegacySoundRewriter$SoundData added(int var1, int var2) {
      return this.added(var1, var2, -1.0F);
   }

   public LegacySoundRewriter$SoundData added(int var1, int var2, float var3) {
      LegacySoundRewriter$SoundData var4 = new LegacySoundRewriter$SoundData(var2, true, var3, true);
      this.a.put(var1, var4);
      return var4;
   }

   public LegacySoundRewriter$SoundData removed(int var1) {
      LegacySoundRewriter$SoundData var2 = new LegacySoundRewriter$SoundData(-1, false, -1.0F, false);
      this.a.put(var1, var2);
      return var2;
   }

   public int handleSounds(int var1) {
      int var3 = var1;
      aqu.d();
      LegacySoundRewriter$SoundData var4 = (LegacySoundRewriter$SoundData)this.a.get(var1);
      if(var4 != null) {
         return var4.getReplacementSound();
      } else {
         ObjectIterator var5 = this.a.int2ObjectEntrySet().iterator();
         if(var5.hasNext()) {
            Entry var6 = (Entry)var5.next();
            if(var1 > var6.getIntKey()) {
               if(((LegacySoundRewriter$SoundData)var6.getValue()).isAdded()) {
                  var3 = var1 - 1;
               }

               ++var3;
            }
         }

         return var3;
      }
   }

   public boolean hasPitch(int var1) {
      aqu.d();
      LegacySoundRewriter$SoundData var3 = (LegacySoundRewriter$SoundData)this.a.get(var1);
      return var3 != null && var3.isChangePitch();
   }

   public float handlePitch(int var1) {
      aqu.d();
      LegacySoundRewriter$SoundData var3 = (LegacySoundRewriter$SoundData)this.a.get(var1);
      return var3 != null?var3.getNewPitch():1.0F;
   }
}
