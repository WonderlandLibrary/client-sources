package net.minecraft.client.audio;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.client.audio.SoundEventAccessorComposite;
import net.minecraft.util.RegistrySimple;

public class SoundRegistry extends RegistrySimple {
   private Map soundRegistry;

   protected Map createUnderlyingMap() {
      this.soundRegistry = Maps.newHashMap();
      return this.soundRegistry;
   }

   public void registerSound(SoundEventAccessorComposite var1) {
      this.putObject(var1.getSoundEventLocation(), var1);
   }

   public void clearMap() {
      this.soundRegistry.clear();
   }
}
