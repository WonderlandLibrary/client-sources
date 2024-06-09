package net.minecraft.client.audio;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.util.RegistrySimple;

public class SoundRegistry extends RegistrySimple {
   private Map field_148764_a;

   protected Map createUnderlyingMap() {
      this.field_148764_a = Maps.newHashMap();
      return this.field_148764_a;
   }

   public void registerSound(SoundEventAccessorComposite p_148762_1_) {
      this.putObject(p_148762_1_.getSoundEventLocation(), p_148762_1_);
   }

   public void clearMap() {
      this.field_148764_a.clear();
   }
}
