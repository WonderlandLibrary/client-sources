package net.minecraft.client.audio;

import com.google.common.collect.Lists;
import java.util.List;

public class SoundList {
   private static final String __OBFID = "CL_00001121";
   private boolean replaceExisting;
   private final List soundList = Lists.newArrayList();
   private SoundCategory category;

   public void setSoundCategory(SoundCategory var1) {
      this.category = var1;
   }

   public boolean canReplaceExisting() {
      return this.replaceExisting;
   }

   public void setReplaceExisting(boolean var1) {
      this.replaceExisting = var1;
   }

   public SoundCategory getSoundCategory() {
      return this.category;
   }

   public List getSoundList() {
      return this.soundList;
   }

   public static class SoundEntry {
      private SoundList.SoundEntry.Type field_148566_e;
      private float volume = 1.0F;
      private float pitch = 1.0F;
      private String name;
      private int field_148565_d = 1;
      private static final String __OBFID = "CL_00001122";
      private boolean field_148564_f;

      public void setSoundEntryType(SoundList.SoundEntry.Type var1) {
         this.field_148566_e = var1;
      }

      public void setSoundEntryVolume(float var1) {
         this.volume = var1;
      }

      public int getSoundEntryWeight() {
         return this.field_148565_d;
      }

      public boolean isStreaming() {
         return this.field_148564_f;
      }

      public void setSoundEntryPitch(float var1) {
         this.pitch = var1;
      }

      public SoundEntry() {
         this.field_148566_e = SoundList.SoundEntry.Type.FILE;
         this.field_148564_f = false;
      }

      public SoundList.SoundEntry.Type getSoundEntryType() {
         return this.field_148566_e;
      }

      public void setSoundEntryName(String var1) {
         this.name = var1;
      }

      public float getSoundEntryVolume() {
         return this.volume;
      }

      public void setStreaming(boolean var1) {
         this.field_148564_f = var1;
      }

      public String getSoundEntryName() {
         return this.name;
      }

      public void setSoundEntryWeight(int var1) {
         this.field_148565_d = var1;
      }

      public float getSoundEntryPitch() {
         return this.pitch;
      }

      public static enum Type {
         private static final String __OBFID = "CL_00001123";
         FILE("FILE", 0, "file"),
         SOUND_EVENT("SOUND_EVENT", 1, "event");

         private static final SoundList.SoundEntry.Type[] $VALUES = new SoundList.SoundEntry.Type[]{FILE, SOUND_EVENT};
         private static final SoundList.SoundEntry.Type[] ENUM$VALUES = new SoundList.SoundEntry.Type[]{FILE, SOUND_EVENT};
         private final String field_148583_c;

         private Type(String var3, int var4, String var5) {
            this.field_148583_c = var5;
         }

         public static SoundList.SoundEntry.Type getType(String var0) {
            SoundList.SoundEntry.Type[] var1 = values();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
               SoundList.SoundEntry.Type var4 = var1[var3];
               if (var4.field_148583_c.equals(var0)) {
                  return var4;
               }
            }

            return null;
         }
      }
   }
}
