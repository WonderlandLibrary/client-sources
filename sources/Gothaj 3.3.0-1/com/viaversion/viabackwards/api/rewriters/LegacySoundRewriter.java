package com.viaversion.viabackwards.api.rewriters;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viaversion.api.rewriter.RewriterBase;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap;

@Deprecated
public abstract class LegacySoundRewriter<T extends BackwardsProtocol<?, ?, ?, ?>> extends RewriterBase<T> {
   protected final Int2ObjectMap<LegacySoundRewriter.SoundData> soundRewrites = new Int2ObjectOpenHashMap<>(64);

   protected LegacySoundRewriter(T protocol) {
      super(protocol);
   }

   public LegacySoundRewriter.SoundData added(int id, int replacement) {
      return this.added(id, replacement, -1.0F);
   }

   public LegacySoundRewriter.SoundData added(int id, int replacement, float newPitch) {
      LegacySoundRewriter.SoundData data = new LegacySoundRewriter.SoundData(replacement, true, newPitch, true);
      this.soundRewrites.put(id, data);
      return data;
   }

   public LegacySoundRewriter.SoundData removed(int id) {
      LegacySoundRewriter.SoundData data = new LegacySoundRewriter.SoundData(-1, false, -1.0F, false);
      this.soundRewrites.put(id, data);
      return data;
   }

   public int handleSounds(int soundId) {
      int newSoundId = soundId;
      LegacySoundRewriter.SoundData data = this.soundRewrites.get(soundId);
      if (data != null) {
         return data.getReplacementSound();
      } else {
         for (Int2ObjectMap.Entry<LegacySoundRewriter.SoundData> entry : this.soundRewrites.int2ObjectEntrySet()) {
            if (soundId > entry.getIntKey()) {
               if (entry.getValue().isAdded()) {
                  newSoundId--;
               } else {
                  newSoundId++;
               }
            }
         }

         return newSoundId;
      }
   }

   public boolean hasPitch(int soundId) {
      LegacySoundRewriter.SoundData data = this.soundRewrites.get(soundId);
      return data != null && data.isChangePitch();
   }

   public float handlePitch(int soundId) {
      LegacySoundRewriter.SoundData data = this.soundRewrites.get(soundId);
      return data != null ? data.getNewPitch() : 1.0F;
   }

   public static final class SoundData {
      private final int replacementSound;
      private final boolean changePitch;
      private final float newPitch;
      private final boolean added;

      public SoundData(int replacementSound, boolean changePitch, float newPitch, boolean added) {
         this.replacementSound = replacementSound;
         this.changePitch = changePitch;
         this.newPitch = newPitch;
         this.added = added;
      }

      public int getReplacementSound() {
         return this.replacementSound;
      }

      public boolean isChangePitch() {
         return this.changePitch;
      }

      public float getNewPitch() {
         return this.newPitch;
      }

      public boolean isAdded() {
         return this.added;
      }
   }
}
