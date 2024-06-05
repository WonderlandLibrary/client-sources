package net.minecraft.client.audio;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.util.RegistrySimple;

public class SoundRegistry extends RegistrySimple {
   private static final String __OBFID = "CL_00001151";
   private Map field_148764_a;

   public void clearMap() {
      this.field_148764_a.clear();
   }

   protected Map createUnderlyingMap() {
      this.field_148764_a = Maps.newHashMap();
      return this.field_148764_a;
   }

   public void registerSound(SoundEventAccessorComposite var1) {
      this.putObject(var1.getSoundEventLocation(), var1);
   }
}
