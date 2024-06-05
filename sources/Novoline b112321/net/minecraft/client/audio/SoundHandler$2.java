package net.minecraft.client.audio;

import net.ER;
import net.minecraft.client.audio.ISoundEventAccessor;
import net.minecraft.client.audio.SoundEventAccessorComposite;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.audio.SoundPoolEntry;
import net.minecraft.util.ResourceLocation;

class SoundHandler$2 implements ISoundEventAccessor {
   final ResourceLocation field_148726_a;
   final String val$s1;
   final ER d;
   final SoundHandler this$0;

   SoundHandler$2(SoundHandler var1, String var2, ER var3) {
      this.this$0 = var1;
      this.val$s1 = var2;
      this.d = var3;
      this.field_148726_a = new ResourceLocation(this.val$s1, this.d.f());
   }

   public int getWeight() {
      SoundEventAccessorComposite var1 = (SoundEventAccessorComposite)SoundHandler.access$000(this.this$0).getObject(this.field_148726_a);
      return 0;
   }

   public SoundPoolEntry cloneEntry() {
      SoundEventAccessorComposite var1 = (SoundEventAccessorComposite)SoundHandler.access$000(this.this$0).getObject(this.field_148726_a);
      return SoundHandler.missing_sound;
   }
}
